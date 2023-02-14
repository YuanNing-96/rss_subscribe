# rss-subscribe

[Mirai Console](https://github.com/mamoe/mirai-console) 插件, 使用 Kotlin + Gradle.

订阅rss更新的项目X

订阅www.miobt.com的番剧更新的项目√



# 注意:

该插件不适用于查询结果超过20条的情况，因为本插件目前的判断方式为通过获取到的订阅结果的数量判断的，而miobt的rss订阅只会返回前20条结果，所以如果查询结果超过20条本插件失效，以后会考虑更改判断策略

> 计划的判断策略，根据每条内容的发布时间进行判断，即rss -> channel -> item -> pubDate
>
> 以后会考虑最多只会返回最新的5条订阅内容，或支持最多返回订阅条数的自定义，否则bot无法登录mirai的概率有点高（作者的测试bot已经无法登录了）



# 项目使用方式：

该插件第一次运行结束后，会生成config/top.yuanning.rss_subscribe/RssConfig.yml和data/top.yuanning.rss_subscribe/RssData.yml两个文件，

RssConfig样例如下：

```
# 订阅内容有更新时发送的消息的模板
subscribeInfoSendMessage: 订阅内容更新了
# 发送消息的周期，即发送消息后，经过该时间段后才会继续执行订阅任务，单位：毫秒
sendMessageTime: 5000
# 检测的周期，检测结束后，经过该时间段才会开始下一轮检测，单位：毫秒
checkTime: 60000
```

RssData样例如下：

```
subscribes: 
  - name: Lolihouse组的《别当欧尼酱》
    targetUrl: 'https://www.miobt.com/rss-%E5%88%AB%E5%BD%93%E6%AC%A7%E5%B0%BC%E9%85%B1+LoliHouse.xml'
    subscribers: 
      - type: friend
        id: 12346
        notifiedNumber: 0
      - type: group
        id: 12346
        notifiedNumber: 0
    init: false
    elementNumber: 0
```

其中RssConfig的subscribeInfoSendMessage是没有用的，目前使用的固定格式，样例如下

```
订阅内容更新了
更新内容：[SweetSub&amp;LoliHouse] 不当哥哥了！/ Oniichan ha Oshimai! - 06 [WebRip 1080p HEVC-10bit AAC][简繁日内封字幕]（检索用：别当欧尼酱了！）
链接地址：http://www.miobt.com/show-2fd4a2dfc729657cff05160d1891ca77cd2341f8.html
```

如果想要添加订阅，则需要修改RssData，

name是自定义订阅的名称（目前没用上）

targetUrl是订阅地址，可以去www.miobt.com自己生成

subscribers是订阅者集合，其中type只有两种值：friend或group，id则是对应的qq号或群号，

至于notifiedNumber，init，elementNumber则是程序自己使用的用于判断是否需要通知以及通知哪些内容用的



# 注意事项：

在第一次加载插件生成config以及data文件后请推出mcl，修改config与data为想要的且正确的内容后重新启动mcl，RssData中的subscribers如果配置错误会增加一定程度的性能开销

该插件建议用于追新番，或者数据量较少的关键词或关键词的组合，因为没有对数据量很多的情况进行测试



# 项目情况概述：

> 虽然说是订阅rss更新的项目，但该项目的主要目的为订阅www.miobt.com的rss更新，所以优先完成订阅www.miobt.com的番剧的更新功能。针对miobt的功能开发结束后，作者可能就滚去继续学kotlin协程了。

目前的通知策略：检测到更新之后，对于每一个联系好友/群，只通知一次，如果因为bot没有该联系好友/群或其他原因没有通知到的话，不另外通知
因此请确保所有的联系人/群都在bot的好友列表/群列表中

> 暂时不对同时登录多个bot机器人的情况进行适配，该适配优先级小于适配非miobt的其他网站的优先级
> 
> 事实上对于向某一个联系人/群发送消息的功能做了多bot的适配（虽然目前阶段只有这一个功能需要适配多bot就是了）

由于如果存在无法被通知到的订阅者时，每次检查订阅链接时，本插件都会尝试通知该订阅者。所以如果有配置错误或订阅者永远无法被通知到时，建议删除该订阅者

> 针对miobt的开发，在小数据量，只运行比较短的时间的前提下，功能测试正常
> 
> 代码执行中出现的异常，目前一律以logger.error打印在控制台，并跳过当前有问题的代码片段
> 
> 接下来考虑优化该项目（的shit代码）

虽说功能测试正常，但是用起来不方便，目前只能通过修改data文件的方式修改订阅。以后可能会做通过控制台和通过qq聊天的方式，根据权限修改订阅