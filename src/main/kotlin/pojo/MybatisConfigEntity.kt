package top.yuanning.rss_subscribe.pojo

import kotlinx.serialization.Serializable

@Serializable
class MybatisConfigEntity {
    var driver : String = "com.mysql.cj.jdbc.Driver"
    var url : String = "jdbc:mysql://localhost:3306/temp"
    var userName : String = "root"
    var password : String = "root"

    constructor()

    constructor(driver: String, url: String, userName: String, password: String) {
        this.driver = driver
        this.url = url
        this.userName = userName
        this.password = password
    }


}