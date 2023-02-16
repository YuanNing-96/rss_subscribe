package top.yuanning.rss_subscribe.util

import org.junit.Assert
import org.junit.Test
import top.yuanning.rss_subscribe.PluginMain
import top.yuanning.rss_subscribe.util.ChangeClassLoaderUtil

class ChangeClassLoaderUtilTest {

    /**
     * 暂时无法确定这个单测通过就代表函数正常，但是程序里多处运行了这里的代码都没有出问题，就默认是没有问题的了
     */
    @Test
    fun changeClassLoaderTest(){
        ChangeClassLoaderUtil.changeClassLoader {
            Assert.assertEquals(PluginMain::class.java.classLoader,Thread.currentThread().contextClassLoader)
        }
    }
}