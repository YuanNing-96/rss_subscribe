# rss-subscribe

[Mirai Console](https://github.com/mamoe/mirai-console) 插件, 使用 Kotlin + Gradle.

订阅rss更新的项目√

# 项目使用方式：

该插件第一次运行结束后，会生成config/top.yuanning.rss_subscribe/RssConfig.yml和data/top.yuanning.rss_subscribe/RssData.yml两个文件，

RssConfig样例如下：

```
# 订阅内容有更新时发送的消息的模板,
# ${title}替换item的title
# ${link}替换item的link
# ${description}替换item的description
# ${pubDate}替换item的pubDate
# ${category}替换item的category
subscribeInfoSendMessage: "订阅内容更新了\n标题:${title}\n链接:${link}"
# 发送消息的周期，即发送消息后，经过该时间段后才会继续执行订阅任务，单位：毫秒
sendMessageTime: 5000
# 检测的周期，检测结束后，经过该时间段才会开始下一轮检测，单位：毫秒
checkTime: 60000
# 最多通知更新的数量，最大值为20,建议不要设置的过大，防止封号，最小值为1
maxNotifyNum: 1
```

RssData样例如下：

```
subscribes: 
  - name: Lolihouse组的《别当欧尼酱》
    targetUrl: 'https://www.miobt.com/rss-%E5%88%AB%E5%BD%93%E6%AC%A7%E5%B0%BC%E9%85%B1+LoliHouse.xml'
    subscribers: 
      - type: friend
        id: 123456
        notifideDate: '2023年02月10日-02时54分24秒'
      - type: group
        id: 123456
        notifideDate: '2023年02月10日-02时54分24秒'
    initialized: false
    minNotifideDate: '2023年02月10日-02时54分24秒'
```


如果想要添加订阅，则需要修改RssData，

name是自定义订阅的名称（目前没用上）

targetUrl是订阅rss的地址，要求是访问该地址是返回且只返回rss订阅信息（xml格式）

subscribers是订阅者集合，其中type只有两种值：friend或group，id则是对应的qq号或群号，

其他配置项为插件自用，不建议修改，用于判断是否需要通知



# 注意事项：

在第一次加载插件生成config以及data文件后请退mcl，修改config与data为想要的且正确的内容后重新启动mcl，因为RssData中的样例配置是无法通知到的，如果保留的话会导致每次项目检查更新时都会尝试通知该样例配置的subscriber

# 项目情况概述：

目前的通知策略：如果该subscriber通知不到（有可能是因为没有bot能联系上该好友/群聊）则会跳过该subscriber，等到下一次检查更新时再尝试通知
因此请确保所有的联系人/群都在bot的好友列表/群列表中

> 以后考虑优化该项目（的shit代码），不过作者滚去学kotlin协程了，学完归来后会考虑优化
> 
> 如果发现bug作者看到了的话会进行修复
> 
> 虽说功能测试正常，但是用起来不方便，目前只能通过修改data文件的方式修改订阅。以后可能会做通过控制台和通过qq聊天的方式，根据权限修改订阅