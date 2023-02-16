> 注：在本文中，所有PluginMain无特殊说明均指代继承了KotlinPlugin抽象类的子类，也即该插件的主类。

首先我们要达成一个共识，即直接都用mybatis官方的配置文件直接进行配置是可行的，但我们开发插件是为了给别人用，因此不能在mybatis-config.xml文件中把数据库链接的配置写死，但是在我探索出来的方法中，使用mybatis-config.xml进行配置是必须的，否则无法绑定Mapper.xml文件。



因此对于在mybatis-config.xml文件中把数据库链接的配置写死和自行修改数据库链接配置中的不同点在于，拦截官方样例中如下方式

```java
String resource = "org/mybatis/example/mybatis-config.xml";
InputStream inputStream = Resources.getResourceAsStream(resource);
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
```

通过输入流得到的configuration对象实例，修改其中的数据库链接配置，然后在将修改后的configuration移交给SqlSessionFactoryBuilder().build，即

```java
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
```



首先我们要根据官方样例，生成一个具有正确的数据库链接配置的environment：

```java
DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
TransactionFactory transactionFactory = new JdbcTransactionFactory();
Environment environment = new Environment("development", transactionFactory, dataSource);
```



代码样例（仅作为参考，请自行修改）：

```kotlin
fun getEnvironmentOfConfiguration() : Environment{
    val dataSource = PooledDataSource()
    dataSource.username = RssConfig.mysqlConfig.userName
    dataSource.password = RssConfig.mysqlConfig.password
    dataSource.url = RssConfig.mysqlConfig.url
    dataSource.driver = RssConfig.mysqlConfig.driver
    dataSource.poolMaximumActiveConnections = 100
    dataSource.poolMaximumIdleConnections = 8
    val transactionFactory: TransactionFactory = JdbcTransactionFactory()
    val environment = Environment("development", transactionFactory, dataSource)
    return environment
}//得到具有正确的数据库链接配置的environment实例
```



其次我们用输入流生成configuration实例，然后更改configuration实例的environment为自己手动生成的environment，最后我们将修改好的configuration交给SqlSessionFactoryBuilder().build方法

```kotlin
private var sqlSessionFactory : SqlSessionFactory // MybatisUtil的用于保存全局唯一sqlSessionFactory的变量
```

```kotlin
init {
    val thread = Thread.currentThread()
    val oc = thread.contextClassLoader
    try {
        thread.contextClassLoader = PluginMain::class.java.classLoader //此处切换类加载器是为了正确加载resource中的所有xml配置文件，包括Mapper.xml

        val resource = "mybatis-config.xml"
        val inputStream: InputStream = Resources.getResourceAsStream(resource)!! //TODO 此处强制转为非空，因为mybatis-config.xml文件是存在的，以及类加载器是正常的，我们确定可以读取到该配置文件
        val configuration = XMLConfigBuilder(inputStream).parse() //从inputStream流生成configuration实例
        configuration.environment = getEnvironmentOfConfiguration() //修改configuration的environment的值为自己需要的值
        sqlSessionFactory = SqlSessionFactoryBuilder().build(configuration)

    } finally {
        thread.contextClassLoader = oc
    }
}// MybatisUtil的构造函数的执行体，因为MybatisUtil是object，所以第一次调用MybatisUtil时会自动调用该段代码
```



当然，为了方便，我们可以封装一个切换线程类加载器的方法：

```kotlin
fun changeClassLoader(block : () -> Unit){
    val thread = Thread.currentThread()
    val oc = thread.contextClassLoader
    try {
        thread.contextClassLoader = PluginMain::class.java.classLoader

        block()

    } finally {
        thread.contextClassLoader = oc
    }
}
```



此时，我们得到了可供参考的MybatisUtil.kt的参考版本：

```kotlin
object MybatisUtil {

    private lateinit var sqlSessionFactory : SqlSessionFactory

    fun getEnvironmentOfConfiguration() : Environment{
        val dataSource = PooledDataSource()
        dataSource.username = RssConfig.mysqlConfig.userName
        dataSource.password = RssConfig.mysqlConfig.password
        dataSource.url = RssConfig.mysqlConfig.url
        dataSource.driver = RssConfig.mysqlConfig.driver
        dataSource.poolMaximumActiveConnections = 100
        dataSource.poolMaximumIdleConnections = 8
        val transactionFactory: TransactionFactory = JdbcTransactionFactory()
        val environment = Environment("development", transactionFactory, dataSource)
        return environment
    }//得到具有正确的数据库链接配置的environment实例

    init {
        ChangeClassLoaderUtil.changeClassLoader {
            logger.error("初始化")

            val resource = "mybatis-config.xml"
            val inputStream: InputStream = Resources.getResourceAsStream(resource)!!//TODO 此处强制转为非空，因为mybatis-config.xml文件是存在的，以及类加载器是正常的

            val configuration = XMLConfigBuilder(inputStream).parse()
            configuration.environment = getEnvironmentOfConfiguration()

            sqlSessionFactory = SqlSessionFactoryBuilder().build(configuration)
        }
    }

    fun getSqlSession():SqlSession{
        return sqlSessionFactory.openSession()
    }

}
```

mybatis-config.xml配置文件的参考版本：

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <mappers>
        <mapper resource="mapper/UserMapper.xml"/>
    </mappers>

</configuration>
```

>注：由于configuration的environment是被我们拦截了下来，使用的我们自己生成的environment，所以在此处配置configuration标签的子标签environment是无效的，必须在MybatisUtil.getEnvironmentOfConfiguration()方法中手动修改对应参数，或将对应参数设计默认值后"映射"到Config类中，让插件使用者可以自己修改对应的配置
