# tulingchat

wechat robot , automatic response,tuling

个人微信公众号， 微信机器人 微信可搜索  tulingchat

## 技术栈

SpringBoot + Mybatis + Mysql + MongoDB + Redis + Jsoup

阿里云服务器配置nginx完成请求转发

## 2017-10 更新

1. 整合Mybatis+Mongodb+Redis,定期存储存储access_token，加入图灵接口，实现消息回复功能
2. 选取Schedule 和 quartz做任务调度

## 2017-11-10 更新

1. 完成邮件，知乎日报功能等
2. 公众号输入：'新闻'或者'知乎日报',即可获取图文消息

## 2017-11-15 更新

### 功能
1. 实现回复音乐消息功能
页面输入:音乐 歌手名 ,即可随机返回这位歌手的一首热门歌曲消息.
### 实现逻辑
1. 预先使用Redis存储 歌手名称:歌手链接,(不到3w多条数据)。
2. 调用时，直接使用Jsoup爬取网易云音乐页面，拼装格式后回复音乐消息。
3. 处理上传缩略图的临时素材

### 问题
1. 音乐消息不显示缩略图
2. 音乐消息不能直接点击播放
3. 不能按照歌曲名称搜索

前两个问题暂时无法解决，需要使用微信SDK
第三个问题解决要事先爬取网易云音乐的歌曲，因为网易云的反爬虫机制，需要配置代理。
暂定springboot+webmagic实现,后续会考虑加入MQ

## 2017-11-29 更新

[spider4j](https://github.com/CharleyXu/spider4j)

springboot + webmagic + mybatis 实现 ，未配置代理，爬取了4个多小时的数据，没有被封IP

1.成功抓取网易云音乐 歌手信息 6w+ 和 音乐信息 80w+,
  存入mysql，可以按照歌曲名称或者歌手名称、歌手别名等进行查询
    
2.成功获取评论数据，用户歌单，歌单详情，已实现查询接口,查询成功后存入mongodb

## 2017-12-07 更新

补充图例 

![Image text](https://raw.githubusercontent.com/CharleyXu/tulingchat/master/src/main/resources/static/legend.png)

## 2017-12-27 更新

使用actuator组件和Metric库做项目监控 

## 后续

项目使用SDK开发工具包 https://github.com/Wechat-Group/weixin-java-tools

借鉴这个微信点餐系统接触下微信支付项目 [Wechat_Sell](https://github.com/ldlood/SpringBoot_Wechat_Sell)
