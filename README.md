# tulingchat
wechat robot , automatic response,tuling

个人微信公众号， 微信机器人 微信可搜索  tulingchat

技术栈:

SpringBoot+ Mybatis + Mysql + MongoDB + Redis + Jsoup

阿里云服务器配置nginx完成请求转发

#####2017-10 更新

整合Mybatis+Mongodb+Redis,定期存储存储access_token，加入图灵接口，实现消息回复功能
选取Schedule 和 quartz做任务调度

#####2007-11-10 更新

完成邮件，知乎日报功能等，公众号输入： 邮件 收信人 内容
'新闻'或者'知乎日报',即可获取图文消息

#####2017-11-15 更新

实现回复音乐消息功能，
页面输入:音乐 歌手名 ,即可随机返回这位歌手的一首热门歌曲消息.
预先使用Redis存储 歌手名称:歌手链接,(不到3w多条数据)。
调用时，直接使用Jsoup爬取网易云音乐页面，拼装格式后回复音乐消息。
这里还会涉及到上传缩略图的临时素材（看了后面就知道了没必要每次都上传了）。


问题:1.音乐消息不显示缩略图
     2.音乐消息不能直接点击播放
     3.不能按照歌曲名称搜索


前两个问题暂时无法解决，需要使用微信SDK
第三个问题解决要事先爬取网易云音乐的歌曲，因为网易云的反爬虫机制，需要配置代理。
暂定springboot+webmagic实现,后续会考虑加入RabbitMQ
