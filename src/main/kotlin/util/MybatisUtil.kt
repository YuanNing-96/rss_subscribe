package top.yuanning.rss_subscribe.util

import org.apache.ibatis.builder.xml.XMLConfigBuilder
import org.apache.ibatis.datasource.pooled.PooledDataSource
import org.apache.ibatis.io.Resources
import org.apache.ibatis.mapping.Environment
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.apache.ibatis.transaction.TransactionFactory
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory
import top.yuanning.rss_subscribe.configs.RssConfig
import java.io.InputStream


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