package top.yuanning.rss_subscribe.util

import net.mamoe.mirai.utils.error
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import top.yuanning.rss_subscribe.PluginMain
import top.yuanning.rss_subscribe.PluginMain.logger
import java.io.InputStream

object MybatisUtil {

    private var sqlSessionFactory : SqlSessionFactory

    init {
        val thread = Thread.currentThread()
        val oc = thread.contextClassLoader
        try {
            thread.contextClassLoader = PluginMain::class.java.classLoader

            val resource = "mybatis-config.xml"
            val inputStream: InputStream = PluginMain::class.java.classLoader
                .getResourceAsStream(resource)!!//TODO 此处强制转为非空，因为mybatis-config.xml文件是存在的，以及类加载器是正常的
            sqlSessionFactory = SqlSessionFactoryBuilder().build(inputStream)

        } finally {
            thread.contextClassLoader = oc
        }
    }

    fun getSqlSession():SqlSession{
        return PluginMain.getSqlSession()
    }

    fun getSqlSessionFactory():SqlSessionFactory{
        return sqlSessionFactory
    }


}