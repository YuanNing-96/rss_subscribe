package top.yuanning.rss_subscribe.dao

import top.yuanning.rss_subscribe.pojo.User

interface UserMapper {

    fun getUserList():List<User>
}