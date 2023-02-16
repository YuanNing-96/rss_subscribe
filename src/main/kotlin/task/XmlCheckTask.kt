package top.yuanning.rss_subscribe.task


import kotlinx.coroutines.delay
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.utils.info
import org.dom4j.Document
import org.dom4j.DocumentHelper;
import org.dom4j.Element
import top.yuanning.rss_subscribe.PluginMain.logger
import top.yuanning.rss_subscribe.configs.RssConfig
import top.yuanning.rss_subscribe.dao.UserMapper
import top.yuanning.rss_subscribe.pojo.Subscribe
import top.yuanning.rss_subscribe.pojo.Subscriber
import top.yuanning.rss_subscribe.rss.Channel
import top.yuanning.rss_subscribe.rss.Item
import top.yuanning.rss_subscribe.rss.ItemAttributes
import top.yuanning.rss_subscribe.rss.Rss
import top.yuanning.rss_subscribe.util.*
import java.util.*


class XmlCheckTask {
    var subscribes : MutableSet<Subscribe> = mutableSetOf()

    constructor(subscribes : MutableSet<Subscribe>){
        this.subscribes = subscribes
    }

    val testXml: String =
        "<?xml version=\"1.0\" encoding=\"utf-8\"?><rss version=\"2.0\"><channel><title><![CDATA[＊MioBT＊]]></title><link>http://www.miobt.com</link><description><![CDATA[＊MioBT＊]]></description><lastBuildDate>Thu, 16 Feb 2023 17:36:54 +0800</lastBuildDate><language>zh-cn</language><generator>MioBT RSS Generator</generator><copyright>MioBT</copyright><item><title><![CDATA[[SweetSub&amp;LoliHouse] 不当哥哥了！/ Oniichan ha Oshimai! - 06 [WebRip 1080p HEVC-10bit AAC][简繁日内封字幕]（检索用：别当欧尼酱了！）]]></title><link>http://www.miobt.com/show-2fd4a2dfc729657cff05160d1891ca77cd2341f8.html</link><description><![CDATA[<br /><p><strong><img src=\"https://p.sda1.dev/9/bfe103bd0e51cce16697e7e6ebf0537d/Onimai%20Poster%20v2.jpg\" style=\"width:800px;height:1131px\" alt=\"\" /><br /></strong></p><p><strong>字幕：SweetSub</strong><br /><strong><strong>脚本: sinsanction@LoliHouse</strong><br /><strong>压制: Jalapeño<strong>@LoliHouse</strong></strong></strong></p><p><strong><strong><strong><br /></strong></strong></strong></p><p><strong>本片与 SweetSub 合作，感谢字幕组的辛勤劳动。</strong></p><hr /><p></p><p>是采用 Abema 源的无修版。</p><p></p><hr /><p>欢迎大家关注 SweetSub 的 <a href=\"https://t.me/SweetSub\" rel=\"external nofollow\">telegram 频道</a> 。</p><p>SweetSub 新开设了 <a href=\"https://marshmallow-qa.com/sweetsub\" target=\"_blank\" rel=\"external nofollow\">提问箱</a>，大家对字幕组有什么好奇的，或是有报错，都可以来此提问。问题的回答会发布在 telegram 频道中。</p><p><a href=\"https://github.com/tastysugar/SweetSub\" rel=\"external nofollow\">点此下载字幕文件</a></p><hr /><p>SweetSub 的字幕在二次使用时默认遵从 <a href=\"https://creativecommons.org/licenses/by-nc-nd/4.0/\" rel=\"external nofollow\">知识共享 署名-非商业性使用-禁止演绎 4.0 许可协议</a> （Creative Common BY-NC-ND 4.0） ，在遵循规则的情况下可以在不需要与我联系的情况下自由转载、使用。<br />\n" +
            "但是，对于调整时间轴用于匹配自己的不同片源的小伙伴，可以例外在署名、非商业使用的情况下，调整时间轴，不需要与我联系，自由转载、使用。 <br />\n" +
            "如果对字幕做了除了调整时间轴以外的修改，请不要公开发布，留着自己看就好，谢谢。 <br />\n" +
            "详细说明请 <a href=\"https://github.com/tastysugar/SweetSub#%E8%BD%AC%E8%BD%BD%E5%8F%8A%E5%86%8D%E5%88%A9%E7%94%A8%E8%AF%B4%E6%98%8E\" rel=\"external nofollow\">点击这里查看</a></p><hr /><p><strong>为了顺利地观看我们的作品，推荐大家使用以下播放器：</strong></p><p>Windows：<a href=\"https://mpv.io/\" target=\"_blank\" rel=\"external nofollow\">mpv</a>（<a href=\"https://vcb-s.com/archives/7594\" target=\"_blank\" rel=\"external nofollow\">教程</a>）</p><p>macOS：<a href=\"https://iina.io/\" target=\"_blank\" rel=\"external nofollow\">IINA</a></p><p>iOS/Android：<a href=\"https://www.videolan.org/vlc/\" target=\"_blank\" rel=\"external nofollow\">VLC media player</a></p><hr /><p><a href=\"https://share.dmhy.org/topics/view/599634_LoliHouse_LoliHouse_5th_Anniversary_Announcement.html\" rel=\"external nofollow\">点击查看LoliHouse五周年纪念公告（附往年全部礼包）</a></p><hr /><p><strong>人人为我，我为人人，为了各位观众能快速下载，请使用uTorrent / qBittorrent 等正规 BT 软件下载，并保持开机上传，谢谢~</strong></p><p></p><p></p><br />]]></description><guid isPermaLink=\"true\">http://www.miobt.com/show-2fd4a2dfc729657cff05160d1891ca77cd2341f8.html</guid><author><![CDATA[LoliHouse]]></author><enclosure url=\"http://v2.uploadbt.com/?r=down&amp;hash=2fd4a2dfc729657cff05160d1891ca77cd2341f8\" type=\"application/x-bittorrent\" /><pubDate>Fri, 10 Feb 2023 02:54:24 +0800</pubDate><category domain=\"http://www.miobt.com/sort-1-1.html\"><![CDATA[动画]]></category></item><item><title><![CDATA[[SweetSub&amp;LoliHouse] 不当哥哥了！/ Oniichan ha Oshimai! - 05 [WebRip 1080p HEVC-10bit AAC][简繁日内封字幕]（检索用：别当欧尼酱了！）]]></title><link>http://www.miobt.com/show-d743f9d9c914b9d48c199e523f708fe97b851167.html</link><description><![CDATA[<br /><p><strong><img src=\"https://p.sda1.dev/9/bfe103bd0e51cce16697e7e6ebf0537d/Onimai%20Poster%20v2.jpg\" style=\"width:800px;height:1131px\" alt=\"\" /><br /></strong></p><p><strong>字幕：SweetSub</strong><br /><strong><strong>脚本: sinsanction@LoliHouse</strong><br /><strong>压制: Jalapeño<strong>@LoliHouse</strong></strong></strong></p><p><strong><strong><strong><br /></strong></strong></strong></p><p><strong>本片与 SweetSub 合作，感谢字幕组的辛勤劳动。</strong></p><hr /><p></p><p>是采用 Abema 源的无修版。</p><p></p><hr /><p>欢迎大家关注 SweetSub 的 <a href=\"https://t.me/SweetSub\" rel=\"external nofollow\">telegram 频道</a> 。</p><p>SweetSub 新开设了 <a href=\"https://marshmallow-qa.com/sweetsub\" target=\"_blank\" rel=\"external nofollow\">提问箱</a>，大家对字幕组有什么好奇的，或是有报错，都可以来此提问。问题的回答会发布在 telegram 频道中。</p><p><a href=\"https://github.com/tastysugar/SweetSub\" rel=\"external nofollow\">点此下载字幕文件</a></p><hr /><p>SweetSub 的字幕在二次使用时默认遵从 <a href=\"https://creativecommons.org/licenses/by-nc-nd/4.0/\" rel=\"external nofollow\">知识共享 署名-非商业性使用-禁止演绎 4.0 许可协议</a> （Creative Common BY-NC-ND 4.0） ，在遵循规则的情况下可以在不需要与我联系的情况下自由转载、使用。<br />\n" +
            "但是，对于调整时间轴用于匹配自己的不同片源的小伙伴，可以例外在署名、非商业使用的情况下，调整时间轴，不需要与我联系，自由转载、使用。 <br />\n" +
            "如果对字幕做了除了调整时间轴以外的修改，请不要公开发布，留着自己看就好，谢谢。 <br />\n" +
            "详细说明请 <a href=\"https://github.com/tastysugar/SweetSub#%E8%BD%AC%E8%BD%BD%E5%8F%8A%E5%86%8D%E5%88%A9%E7%94%A8%E8%AF%B4%E6%98%8E\" rel=\"external nofollow\">点击这里查看</a></p><hr /><p><strong>为了顺利地观看我们的作品，推荐大家使用以下播放器：</strong></p><p>Windows：<a href=\"https://mpv.io/\" target=\"_blank\" rel=\"external nofollow\">mpv</a>（<a href=\"https://vcb-s.com/archives/7594\" target=\"_blank\" rel=\"external nofollow\">教程</a>）</p><p>macOS：<a href=\"https://iina.io/\" target=\"_blank\" rel=\"external nofollow\">IINA</a></p><p>iOS/Android：<a href=\"https://www.videolan.org/vlc/\" target=\"_blank\" rel=\"external nofollow\">VLC media player</a></p><hr /><p><a href=\"https://share.dmhy.org/topics/view/599634_LoliHouse_LoliHouse_5th_Anniversary_Announcement.html\" rel=\"external nofollow\">点击查看LoliHouse五周年纪念公告（附往年全部礼包）</a></p><hr /><p><strong>人人为我，我为人人，为了各位观众能快速下载，请使用uTorrent / qBittorrent 等正规 BT 软件下载，并保持开机上传，谢谢~</strong></p><p></p><p></p><br />]]></description><guid isPermaLink=\"true\">http://www.miobt.com/show-d743f9d9c914b9d48c199e523f708fe97b851167.html</guid><author><![CDATA[LoliHouse]]></author><enclosure url=\"http://v2.uploadbt.com/?r=down&amp;hash=d743f9d9c914b9d48c199e523f708fe97b851167\" type=\"application/x-bittorrent\" /><pubDate>Fri, 03 Feb 2023 03:04:45 +0800</pubDate><category domain=\"http://www.miobt.com/sort-1-1.html\"><![CDATA[动画]]></category></item><item><title><![CDATA[[SweetSub&amp;LoliHouse] 不当哥哥了！/ Oniichan ha Oshimai! - 04v2 [WebRip 1080p HEVC-10bit AAC][简繁日内封字幕]（检索用：别当欧尼酱了！）]]></title><link>http://www.miobt.com/show-90123bd5ccd11f8b8123eb5c48a551d75839c377.html</link><description><![CDATA[<br /><p><strong><img src=\"https://p.sda1.dev/9/bfe103bd0e51cce16697e7e6ebf0537d/Onimai%20Poster%20v2.jpg\" style=\"width:800px;height:1131px\" alt=\"\" /><br /></strong></p><p><strong>字幕：SweetSub</strong><br /><strong><strong>脚本: sinsanction@LoliHouse</strong><br /><strong>压制: Jalapeño<strong>@LoliHouse</strong></strong></strong></p><p><strong><strong><strong><br /></strong></strong></strong></p><p><strong>本片与 SweetSub 合作，感谢字幕组的辛勤劳动。</strong></p><hr /><p></p><p>是采用 Abema 源的无修版。</p><p></p><hr /><p>欢迎大家关注 SweetSub 的 <a href=\"https://t.me/SweetSub\" rel=\"external nofollow\">telegram 频道</a> 。</p><p>SweetSub 新开设了 <a href=\"https://marshmallow-qa.com/sweetsub\" target=\"_blank\" rel=\"external nofollow\">提问箱</a>，大家对字幕组有什么好奇的，或是有报错，都可以来此提问。问题的回答会发布在 telegram 频道中。</p><p><a href=\"https://github.com/tastysugar/SweetSub\" rel=\"external nofollow\">点此下载字幕文件</a></p><hr /><p>SweetSub 的字幕在二次使用时默认遵从 <a href=\"https://creativecommons.org/licenses/by-nc-nd/4.0/\" rel=\"external nofollow\">知识共享 署名-非商业性使用-禁止演绎 4.0 许可协议</a> （Creative Common BY-NC-ND 4.0） ，在遵循规则的情况下可以在不需要与我联系的情况下自由转载、使用。<br />\n" +
            "但是，对于调整时间轴用于匹配自己的不同片源的小伙伴，可以例外在署名、非商业使用的情况下，调整时间轴，不需要与我联系，自由转载、使用。 <br />\n" +
            "如果对字幕做了除了调整时间轴以外的修改，请不要公开发布，留着自己看就好，谢谢。 <br />\n" +
            "详细说明请 <a href=\"https://github.com/tastysugar/SweetSub#%E8%BD%AC%E8%BD%BD%E5%8F%8A%E5%86%8D%E5%88%A9%E7%94%A8%E8%AF%B4%E6%98%8E\" rel=\"external nofollow\">点击这里查看</a></p><hr /><p><strong>为了顺利地观看我们的作品，推荐大家使用以下播放器：</strong></p><p>Windows：<a href=\"https://mpv.io/\" target=\"_blank\" rel=\"external nofollow\">mpv</a>（<a href=\"https://vcb-s.com/archives/7594\" target=\"_blank\" rel=\"external nofollow\">教程</a>）</p><p>macOS：<a href=\"https://iina.io/\" target=\"_blank\" rel=\"external nofollow\">IINA</a></p><p>iOS/Android：<a href=\"https://www.videolan.org/vlc/\" target=\"_blank\" rel=\"external nofollow\">VLC media player</a></p><hr /><p><a href=\"https://share.dmhy.org/topics/view/599634_LoliHouse_LoliHouse_5th_Anniversary_Announcement.html\" rel=\"external nofollow\">点击查看LoliHouse五周年纪念公告（附往年全部礼包）</a></p><hr /><p><strong>人人为我，我为人人，为了各位观众能快速下载，请使用uTorrent / qBittorrent 等正规 BT 软件下载，并保持开机上传，谢谢~</strong></p><p></p><p></p><br />]]></description><guid isPermaLink=\"true\">http://www.miobt.com/show-90123bd5ccd11f8b8123eb5c48a551d75839c377.html</guid><author><![CDATA[LoliHouse]]></author><enclosure url=\"http://v2.uploadbt.com/?r=down&amp;hash=90123bd5ccd11f8b8123eb5c48a551d75839c377\" type=\"application/x-bittorrent\" /><pubDate>Fri, 27 Jan 2023 03:29:53 +0800</pubDate><category domain=\"http://www.miobt.com/sort-1-1.html\"><![CDATA[动画]]></category></item><item><title><![CDATA[[SweetSub&amp;LoliHouse] 不当哥哥了！/ Oniichan ha Oshimai! - 04 [WebRip 1080p HEVC-10bit AAC][简繁日内封字幕]（检索用：别当欧尼酱了！）]]></title><link>http://www.miobt.com/show-5fa921f7553de6ee123501cc95afdbaf0f6cb996.html</link><description><![CDATA[<br /><p><strong><img src=\"https://p.sda1.dev/9/bfe103bd0e51cce16697e7e6ebf0537d/Onimai%20Poster%20v2.jpg\" style=\"width:800px;height:1131px\" alt=\"\" /><br /></strong></p><p><strong>字幕：SweetSub</strong><br /><strong><strong>脚本: sinsanction@LoliHouse</strong><br /><strong>压制: Jalapeño<strong>@LoliHouse</strong></strong></strong></p><p><strong><strong><strong><br /></strong></strong></strong></p><p><strong>本片与 SweetSub 合作，感谢字幕组的辛勤劳动。</strong></p><hr /><p></p><p>是采用 Abema 源的无修版。</p><p></p><hr /><p>欢迎大家关注 SweetSub 的 <a href=\"https://t.me/SweetSub\" rel=\"external nofollow\">telegram 频道</a> 。</p><p>SweetSub 新开设了 <a href=\"https://marshmallow-qa.com/sweetsub\" target=\"_blank\" rel=\"external nofollow\">提问箱</a>，大家对字幕组有什么好奇的，或是有报错，都可以来此提问。问题的回答会发布在 telegram 频道中。</p><p><a href=\"https://github.com/tastysugar/SweetSub\" rel=\"external nofollow\">点此下载字幕文件</a></p><hr /><p>SweetSub 的字幕在二次使用时默认遵从 <a href=\"https://creativecommons.org/licenses/by-nc-nd/4.0/\" rel=\"external nofollow\">知识共享 署名-非商业性使用-禁止演绎 4.0 许可协议</a> （Creative Common BY-NC-ND 4.0） ，在遵循规则的情况下可以在不需要与我联系的情况下自由转载、使用。<br />\n" +
            "但是，对于调整时间轴用于匹配自己的不同片源的小伙伴，可以例外在署名、非商业使用的情况下，调整时间轴，不需要与我联系，自由转载、使用。 <br />\n" +
            "如果对字幕做了除了调整时间轴以外的修改，请不要公开发布，留着自己看就好，谢谢。 <br />\n" +
            "详细说明请 <a href=\"https://github.com/tastysugar/SweetSub#%E8%BD%AC%E8%BD%BD%E5%8F%8A%E5%86%8D%E5%88%A9%E7%94%A8%E8%AF%B4%E6%98%8E\" rel=\"external nofollow\">点击这里查看</a></p><hr /><p><strong>为了顺利地观看我们的作品，推荐大家使用以下播放器：</strong></p><p>Windows：<a href=\"https://mpv.io/\" target=\"_blank\" rel=\"external nofollow\">mpv</a>（<a href=\"https://vcb-s.com/archives/7594\" target=\"_blank\" rel=\"external nofollow\">教程</a>）</p><p>macOS：<a href=\"https://iina.io/\" target=\"_blank\" rel=\"external nofollow\">IINA</a></p><p>iOS/Android：<a href=\"https://www.videolan.org/vlc/\" target=\"_blank\" rel=\"external nofollow\">VLC media player</a></p><hr /><p><a href=\"https://share.dmhy.org/topics/view/599634_LoliHouse_LoliHouse_5th_Anniversary_Announcement.html\" rel=\"external nofollow\">点击查看LoliHouse五周年纪念公告（附往年全部礼包）</a></p><hr /><p><strong>人人为我，我为人人，为了各位观众能快速下载，请使用uTorrent / qBittorrent 等正规 BT 软件下载，并保持开机上传，谢谢~</strong></p><p></p><p></p><br />]]></description><guid isPermaLink=\"true\">http://www.miobt.com/show-5fa921f7553de6ee123501cc95afdbaf0f6cb996.html</guid><author><![CDATA[LoliHouse]]></author><enclosure url=\"http://v2.uploadbt.com/?r=down&amp;hash=5fa921f7553de6ee123501cc95afdbaf0f6cb996\" type=\"application/x-bittorrent\" /><pubDate>Fri, 27 Jan 2023 02:12:34 +0800</pubDate><category domain=\"http://www.miobt.com/sort-1-1.html\"><![CDATA[动画]]></category></item><item><title><![CDATA[[SweetSub&amp;LoliHouse] 不当哥哥了！/ Oniichan ha Oshimai! - 03 [WebRip 1080p HEVC-10bit AAC][简繁日内封字幕]（检索用：别当欧尼酱了！）]]></title><link>http://www.miobt.com/show-2b0431c3bc573ce26d0332cc1fbd6e5ec9cc8af8.html</link><description><![CDATA[<br /><p><strong><img src=\"https://p.sda1.dev/9/bfe103bd0e51cce16697e7e6ebf0537d/Onimai%20Poster%20v2.jpg\" style=\"width:800px;height:1131px\" alt=\"\" /><br /></strong></p><p><strong>字幕：SweetSub</strong><br /><strong><strong>脚本: sinsanction@LoliHouse</strong><br /><strong>压制: Jalapeño<strong>@LoliHouse</strong></strong></strong></p><p><strong><strong><strong><br /></strong></strong></strong></p><p><strong>本片与 SweetSub 合作，感谢字幕组的辛勤劳动。</strong></p><hr /><p></p><p>是采用 Abema 源的无修版。</p><p></p><hr /><p>欢迎大家关注 SweetSub 的 <a href=\"https://t.me/SweetSub\" rel=\"external nofollow\">telegram 频道</a> 。</p><p>SweetSub 新开设了 <a href=\"https://marshmallow-qa.com/sweetsub\" target=\"_blank\" rel=\"external nofollow\">提问箱</a>，大家对字幕组有什么好奇的，或是有报错，都可以来此提问。问题的回答会发布在 telegram 频道中。</p><p><a href=\"https://github.com/tastysugar/SweetSub\" rel=\"external nofollow\">点此下载字幕文件</a></p><hr /><p>SweetSub 的字幕在二次使用时默认遵从 <a href=\"https://creativecommons.org/licenses/by-nc-nd/4.0/\" rel=\"external nofollow\">知识共享 署名-非商业性使用-禁止演绎 4.0 许可协议</a> （Creative Common BY-NC-ND 4.0） ，在遵循规则的情况下可以在不需要与我联系的情况下自由转载、使用。<br />\n" +
            "但是，对于调整时间轴用于匹配自己的不同片源的小伙伴，可以例外在署名、非商业使用的情况下，调整时间轴，不需要与我联系，自由转载、使用。 <br />\n" +
            "如果对字幕做了除了调整时间轴以外的修改，请不要公开发布，留着自己看就好，谢谢。 <br />\n" +
            "详细说明请 <a href=\"https://github.com/tastysugar/SweetSub#%E8%BD%AC%E8%BD%BD%E5%8F%8A%E5%86%8D%E5%88%A9%E7%94%A8%E8%AF%B4%E6%98%8E\" rel=\"external nofollow\">点击这里查看</a></p><hr /><p><strong>为了顺利地观看我们的作品，推荐大家使用以下播放器：</strong></p><p>Windows：<a href=\"https://mpv.io/\" target=\"_blank\" rel=\"external nofollow\">mpv</a>（<a href=\"https://vcb-s.com/archives/7594\" target=\"_blank\" rel=\"external nofollow\">教程</a>）</p><p>macOS：<a href=\"https://iina.io/\" target=\"_blank\" rel=\"external nofollow\">IINA</a></p><p>iOS/Android：<a href=\"https://www.videolan.org/vlc/\" target=\"_blank\" rel=\"external nofollow\">VLC media player</a></p><hr /><p><a href=\"https://share.dmhy.org/topics/view/599634_LoliHouse_LoliHouse_5th_Anniversary_Announcement.html\" rel=\"external nofollow\">点击查看LoliHouse五周年纪念公告（附往年全部礼包）</a></p><hr /><p><strong>人人为我，我为人人，为了各位观众能快速下载，请使用uTorrent / qBittorrent 等正规 BT 软件下载，并保持开机上传，谢谢~</strong></p><p></p><p></p><br />]]></description><guid isPermaLink=\"true\">http://www.miobt.com/show-2b0431c3bc573ce26d0332cc1fbd6e5ec9cc8af8.html</guid><author><![CDATA[LoliHouse]]></author><enclosure url=\"http://v2.uploadbt.com/?r=down&amp;hash=2b0431c3bc573ce26d0332cc1fbd6e5ec9cc8af8\" type=\"application/x-bittorrent\" /><pubDate>Fri, 20 Jan 2023 05:08:27 +0800</pubDate><category domain=\"http://www.miobt.com/sort-1-1.html\"><![CDATA[动画]]></category></item><item><title><![CDATA[[SweetSub&amp;LoliHouse] 不当哥哥了！/ Oniichan ha Oshimai! - 02 [WebRip 1080p HEVC-10bit AAC][简繁日内封字幕]（检索用：别当欧尼酱了！）]]></title><link>http://www.miobt.com/show-47abbd856ca33e70b920ff69aa288caa71a92ff1.html</link><description><![CDATA[<br /><p><strong><img src=\"https://p.sda1.dev/9/31f4ef13f8bad75e80ac8e6730a3a2cf/Onimai%20Poster.jpg\" style=\"width:800px;height:1131px\" /><br /></strong></p><p><strong>字幕：SweetSub</strong><br /><strong><strong>脚本: sinsanction@LoliHouse</strong><br /><strong>压制: Jalapeño<strong>@LoliHouse</strong></strong></strong></p><p><strong><strong><strong><br /></strong></strong></strong></p><p><strong>本片与 SweetSub 合作，感谢字幕组的辛勤劳动。</strong></p><hr /><p><br /></p><p>是采用 Abema 源的无修版。<br /></p><p><br /></p><hr /><p>欢迎大家关注 SweetSub 的 <a href=\"https://t.me/SweetSub\" rel=\"external nofollow\">telegram 频道</a> 。</p><p>SweetSub 新开设了 <a href=\"https://marshmallow-qa.com/sweetsub\" target=\"_blank\" rel=\"external nofollow\">提问箱</a>，大家对字幕组有什么好奇的，或是有报错，都可以来此提问。问题的回答会发布在 telegram 频道中。</p><p><a href=\"https://github.com/tastysugar/SweetSub\" rel=\"external nofollow\">点此下载字幕文件</a></p><hr /><p>SweetSub 的字幕在二次使用时默认遵从 <a href=\"https://creativecommons.org/licenses/by-nc-nd/4.0/\" rel=\"external nofollow\">知识共享 署名-非商业性使用-禁止演绎 4.0 许可协议</a> （Creative Common BY-NC-ND 4.0） ，在遵循规则的情况下可以在不需要与我联系的情况下自由转载、使用。<br />但是，对于调整时间轴用于匹配自己的不同片源的小伙伴，可以例外在署名、非商业使用的情况下，调整时间轴，不需要与我联系，自由转载、使用。 <br />如果对字幕做了除了调整时间轴以外的修改，请不要公开发布，留着自己看就好，谢谢。 <br />详细说明请 <a href=\"https://github.com/tastysugar/SweetSub#%E8%BD%AC%E8%BD%BD%E5%8F%8A%E5%86%8D%E5%88%A9%E7%94%A8%E8%AF%B4%E6%98%8E\" rel=\"external nofollow\">点击这里查看</a></p><hr /><p><strong>为了顺利地观看我们的作品，推荐大家使用以下播放器：</strong></p><p>Windows：<a href=\"https://mpv.io/\" target=\"_blank\" rel=\"external nofollow\">mpv</a>（<a href=\"https://vcb-s.com/archives/7594\" target=\"_blank\" rel=\"external nofollow\">教程</a>）</p><p>macOS：<a href=\"https://iina.io/\" target=\"_blank\" rel=\"external nofollow\">IINA</a></p><p>iOS/Android：<a href=\"https://www.videolan.org/vlc/\" target=\"_blank\" rel=\"external nofollow\">VLC media player</a></p><hr /><p><a href=\"https://share.dmhy.org/topics/view/599634_LoliHouse_LoliHouse_5th_Anniversary_Announcement.html\" rel=\"external nofollow\">点击查看LoliHouse五周年纪念公告（附往年全部礼包）</a></p><hr /><p><strong>人人为我，我为人人，为了各位观众能快速下载，请使用uTorrent / qBittorrent 等正规 BT 软件下载，并保持开机上传，谢谢~</strong></p><br /><p><br /></p><br />]]></description><guid isPermaLink=\"true\">http://www.miobt.com/show-47abbd856ca33e70b920ff69aa288caa71a92ff1.html</guid><author><![CDATA[LoliHouse]]></author><enclosure url=\"http://v2.uploadbt.com/?r=down&amp;hash=47abbd856ca33e70b920ff69aa288caa71a92ff1\" type=\"application/x-bittorrent\" /><pubDate>Fri, 13 Jan 2023 19:42:49 +0800</pubDate><category domain=\"http://www.miobt.com/sort-1-1.html\"><![CDATA[动画]]></category></item><item><title><![CDATA[[SweetSub&amp;LoliHouse] 不当哥哥了！/ Oniichan ha Oshimai! - 01 [WebRip 1080p HEVC-10bit AAC][简繁日内封字幕]（检索用：别当欧尼酱了！）]]></title><link>http://www.miobt.com/show-f45bcc3e77f3bbe0e6cdf97a89c27a7f8ad92514.html</link><description><![CDATA[<br /><p><strong><img src=\"https://p.sda1.dev/9/31f4ef13f8bad75e80ac8e6730a3a2cf/Onimai%20Poster.jpg\" style=\"width:800px;height:1131px\" /><br /></strong></p><p><strong>字幕：SweetSub</strong><br /><strong><strong>脚本: sinsanction@LoliHouse</strong><br /><strong>压制: Jalapeño<strong>@LoliHouse</strong></strong></strong></p><p><strong><strong><strong><br /></strong></strong></strong></p><p><strong>本片与 SweetSub 合作，感谢字幕组的辛勤劳动。</strong></p><hr /><p><br /></p><p>是采用 Abema 源的无修版。<br /></p><p><br /></p><hr /><p>欢迎大家关注 SweetSub 的 <a href=\"https://t.me/SweetSub\" rel=\"external nofollow\">telegram 频道</a> 。</p><p>SweetSub 新开设了 <a href=\"https://marshmallow-qa.com/sweetsub\" target=\"_blank\" rel=\"external nofollow\">提问箱</a>，大家对字幕组有什么好奇的，或是有报错，都可以来此提问。问题的回答会发布在 telegram 频道中。</p><p><a href=\"https://github.com/tastysugar/SweetSub\" rel=\"external nofollow\">点此下载字幕文件</a></p><hr /><p>SweetSub 的字幕在二次使用时默认遵从 <a href=\"https://creativecommons.org/licenses/by-nc-nd/4.0/\" rel=\"external nofollow\">知识共享 署名-非商业性使用-禁止演绎 4.0 许可协议</a> （Creative Common BY-NC-ND 4.0） ，在遵循规则的情况下可以在不需要与我联系的情况下自由转载、使用。<br />但是，对于调整时间轴用于匹配自己的不同片源的小伙伴，可以例外在署名、非商业使用的情况下，调整时间轴，不需要与我联系，自由转载、使用。 <br />如果对字幕做了除了调整时间轴以外的修改，请不要公开发布，留着自己看就好，谢谢。 <br />详细说明请 <a href=\"https://github.com/tastysugar/SweetSub#%E8%BD%AC%E8%BD%BD%E5%8F%8A%E5%86%8D%E5%88%A9%E7%94%A8%E8%AF%B4%E6%98%8E\" rel=\"external nofollow\">点击这里查看</a></p><hr /><p><strong>为了顺利地观看我们的作品，推荐大家使用以下播放器：</strong></p><p>Windows：<a href=\"https://mpv.io/\" target=\"_blank\" rel=\"external nofollow\">mpv</a>（<a href=\"https://vcb-s.com/archives/7594\" target=\"_blank\" rel=\"external nofollow\">教程</a>）</p><p>macOS：<a href=\"https://iina.io/\" target=\"_blank\" rel=\"external nofollow\">IINA</a></p><p>iOS/Android：<a href=\"https://www.videolan.org/vlc/\" target=\"_blank\" rel=\"external nofollow\">VLC media player</a></p><hr /><p><a href=\"https://share.dmhy.org/topics/view/599634_LoliHouse_LoliHouse_5th_Anniversary_Announcement.html\" rel=\"external nofollow\">点击查看LoliHouse五周年纪念公告（附往年全部礼包）</a></p><hr /><p><strong>人人为我，我为人人，为了各位观众能快速下载，请使用uTorrent / qBittorrent 等正规 BT 软件下载，并保持开机上传，谢谢~</strong></p><br /><p><br /></p><br />]]></description><guid isPermaLink=\"true\">http://www.miobt.com/show-f45bcc3e77f3bbe0e6cdf97a89c27a7f8ad92514.html</guid><author><![CDATA[LoliHouse]]></author><enclosure url=\"http://v2.uploadbt.com/?r=down&amp;hash=f45bcc3e77f3bbe0e6cdf97a89c27a7f8ad92514\" type=\"application/x-bittorrent\" /><pubDate>Sat, 07 Jan 2023 14:13:28 +0800</pubDate><category domain=\"http://www.miobt.com/sort-1-1.html\"><![CDATA[动画]]></category></item></channel></rss>\n"

    suspend fun main(){

//        logger.info {
//            var sqlSession = MybatisUtil.getSqlSession()
//            var mapper = sqlSession.getMapper(UserMapper::class.java)
//            return@info mapper.getUserList().toString()
//        } TODO 测试mybatis用的测试代码

        subscribes.forEach {
            subscribe ->

            val url = subscribe.targetUrl
            val xml: String

            try {
                xml = Http.getXmlStringFromUrl(url)
            }catch (e : Exception){
                logger.error(Exception("网络不畅或url格式错误，订阅${subscribe.name}的链接${url}，获取xml信息失败，跳过当前订阅目标").toString())
                return@forEach
            }

//            xml = testXml // TODO 测试用

            val document : Document = DocumentHelper.parseText(xml)
            val root : Element = document.rootElement
            val channel = Rss(root).channel
            val items = channel.items
            val latestItem = items[0]

            if(!subscribe.initialized){//如果该订阅没有被初始化，则初始化该订阅，初始化过程为将所有记录的已通知个数更改为当前url已有个数
                subscribe.initialize(latestItem.pubDate)

//                subscribe.initialize(latestItem.pubDate.let {
//                        date ->
//
//                    if (date == null){
//                        logger.error(Exception("目前版本要订阅更新的话，订阅的item必须包含pubDate，否则无法检测到是否需要通知更新。后续版本可能会修改该逻辑，但当前版本必须要item节点含有${ItemAttributes.PUBDATE.value}属性").toString())
//                        return@forEach
//                    }else{
//                        return@let date
//                    }
//                }) // TODO 不要删，等版本升级，pubDate可以为null时，使用这段初始化代码替换上面的初始化代码

                return@forEach
            }

            if(RssUpdateCheck.checkSubscribeUpdate(latestItem,subscribe)){

                subscribe.subscribers.forEach {
                    subscriber: Subscriber ->

                    if (RssUpdateCheck.checkSubscriberUpdate(latestItem,subscriber)){

                        val contact : Contact

                        try {
                            contact = ContactUtil.getContactOrThrow(subscriber.type,subscriber.id)
                        }catch (e:Exception){ //如果没有能联系上该subscriber的bot，则跳过当前的subscriber
                            return@forEach
                        }//TODO 测试时注释掉contact，因为不一定有bot

                        var startIndex = if (items.size-RssConfig.maxNotifyNum>=0) items.size-RssConfig.maxNotifyNum else 0
                        items.asReversed().subList(startIndex ,items.size).forEach { //此处进行翻转，从旧到新开始推送，因为items是从新到旧排列的，items[0]是最新的
                            item: Item ->

                            if(RssUpdateCheck.checkSubscriberUpdate(item, subscriber)){//由旧到新，对于每一个item，判断是否需要通知
                                ContactUtil.sendMessageOfItemToContact(item,contact)

//                                ContactUtil.logMessageWithItemSubscriber(item,subscriber)//TODO 测试用
                                subscriber.notifideDate = item.pubDate
                                delay(RssConfig.sendMessageTime)
                            }
                        }
                    }


                }
                var date = latestItem.pubDate
                subscribe.subscribers.forEach {
                    subscriber: Subscriber ->

                    if(date.compareTo(subscriber.notifideDate)>0){
                        date = subscriber.notifideDate
                    }
                }
                subscribe.minNotifideDate = date
            }
        }
    }

}
