# tulingchat
wechat robot , automatic response,tuling

开发的个人微信公众号， 测试号  微信可搜索  tulingchat

现阶段 已加入图灵机器人接口，实现消息自动回复功能，简易的微信机器人功能。

技术栈:

SpringBoot+ Mybatis + Mysql + MongoDB + Redis + Jsoup

阿里云服务器配置nginx完成请求转发

选取Schedule 和 quartz做任务调度

Jsoup爬取网易云音乐页面


 
后续 ： 
Mysql存储用户信息，mongo存储聊天记录，Redis(或者采用本地缓存)存储access_token
音乐，知乎日报，留言，邮件，天气 快递查询功能等

 2007-11-9 更新
现在已完成 Redis(或者采用本地缓存)存储access_token
知乎日报,邮件功能等
