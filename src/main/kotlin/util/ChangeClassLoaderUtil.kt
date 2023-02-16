package top.yuanning.rss_subscribe.util

import top.yuanning.rss_subscribe.PluginMain

object ChangeClassLoaderUtil {

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

}