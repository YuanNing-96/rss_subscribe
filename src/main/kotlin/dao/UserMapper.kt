package top.yuanning.rss_subscribe.dao

import top.yuanning.rss_subscribe.pojo.User

/**
 * 此接口类作为样例，如果以后需要用到mybatis，就照着这个格式写接口
 */
interface UserMapper {

    fun getUserList():List<User>
}