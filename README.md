# tulingchat
wechat robot , automatic response,tuling

开发的个人微信公众号， 测试号  微信可搜索  tulingchat

现阶段 已加入图灵机器人接口，实现消息自动回复功能，简易的微信机器人功能。

技术栈:

SpringBoot+ Mybatis + Mysql + MongoDB + Redis 

阿里云服务器配置nginx完成请求转发

选取Quartz做任务调度

后续 ： 
Mysql存储用户信息，mongo存储聊天记录，Redis(或者采用本地缓存)存储access_token
快递查询，音乐推送，豆瓣电影影评，留言等，前提个人公众号需要扩展成企业号
