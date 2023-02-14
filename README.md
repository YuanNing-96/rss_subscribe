# rss-subscribe

[Mirai Console](https://github.com/mamoe/mirai-console) 插件, 使用 Kotlin + Gradle.

订阅rss更新的项目X

订阅www.miobt.com的番剧更新的项目√

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