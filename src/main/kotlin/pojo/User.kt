package top.yuanning.rss_subscribe.pojo

import java.io.Serializable

/**
 * 此接口类作为样例，如果以后需要用到mybatis，就照着这个格式写接口
 */
class User : Serializable {
    var id:Int = 0
    var name:String = ""

    constructor()

    constructor(id: Int, name: String) {
        this.id = id
        this.name = name
    }

}