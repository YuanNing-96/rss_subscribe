package top.yuanning.rss_subscribe

import kotlinx.coroutines.*
import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info
import net.mamoe.mirai.utils.warning
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import top.yuanning.rss_subscribe.configs.RssConfig
import top.yuanning.rss_subscribe.dao.UserMapper
import top.yuanning.rss_subscribe.datas.RssData
import top.yuanning.rss_subscribe.task.XmlCheckTask
import top.yuanning.rss_subscribe.util.Http
import top.yuanning.rss_subscribe.util.MybatisUtil
import java.io.InputStream
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine


object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "top.yuanning.rss_subscribe",
        name = "rss订阅更新插件",
        version = "0.2.1"
    ) {
        author("作者：源宁，联系方式：sxyn_749@163.com")
        info(
            """
            这是一个rss订阅更新插件，优先完成www.miobt.com的订阅更新功能
        """.trimIndent()
        )
        // author 和 info 可以删除.
    }
) {
//    lateinit var sqlSessionFactory: SqlSessionFactory

    var job : Job? = null


    override fun onEnable() {
        RssConfig.reload()
        RssData.reload()

        job = launch {
            XmlCheckTask( RssData.subscribes).main()
            while (this.isActive){
                delay(RssConfig.checkTime)//订阅间隔的周期
                XmlCheckTask( RssData.subscribes).main()
            }
        }

        logger.info {
            var sqlSession = MybatisUtil.getSqlSession()
            var mapper = sqlSession.getMapper(UserMapper::class.java)
            return@info mapper.getUserList().toString()
        }

    }

    override fun onDisable() {
        job?.cancel()
        super.onDisable()
    }


    /**
     * 注：切换线程的类加载器再获取sqlSession的行为必须发生在PluginMain里，才能正确加载UserMapper的配置文件与类
     *
     * 因此这段代码必须写在这里，在MybatisUtil的getSqlSession是调用了这里的getSqlSession，从而减少了MybatisUtil类的修改
     */
    fun getSqlSession():SqlSession{

        val sqlSession : SqlSession

        val thread = Thread.currentThread()
        val oc = thread.contextClassLoader
        try {
            thread.contextClassLoader = PluginMain::class.java.classLoader

            sqlSession = MybatisUtil.getSqlSessionFactory().openSession()

        } finally {
            thread.contextClassLoader = oc
        }

        return sqlSession

    }
}
