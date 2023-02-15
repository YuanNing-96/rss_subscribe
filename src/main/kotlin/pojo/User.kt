package top.yuanning.rss_subscribe.pojo

import java.io.Serializable

class User : Serializable {
    var id:Int = 0
    var name:String = ""

    constructor()

    constructor(id: Int, name: String) {
        this.id = id
        this.name = name
    }

    override fun toString(): String {
        return "User(id=$id, name='$name')"
    }


}