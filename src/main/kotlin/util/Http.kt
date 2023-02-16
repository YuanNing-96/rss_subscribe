package top.yuanning.rss_subscribe.util

import okhttp3.*
import okio.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object Http {

    /**
     * 从url中，以字符串的方式下载url，返回String
     */
    suspend fun getXmlStringFromUrl(url:String) : String = suspendCoroutine {
        val request = Request.Builder().url(url).build()
        OkHttpClient().newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                it.resumeWith(Result.failure(Exception("网络异常，数据获取失败")))
            }

            override fun onResponse(call: Call, response: Response) {
                it.resume(response.body.string())
            }
        })
    }

}