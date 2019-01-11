# wakatime-sync

## wakatime
### wakatime 简介 
[wakatime](https://wakatime.com/) 是一款可以记录你的编码时间的工具，目前支持绝大部分主流的 IDE 以及 Chrome 浏览器。
### 使用步骤
1. [注册](https://wakatime.com/signup) wakatime 账号；
2. 在[官网](https://wakatime.com/plugins)找到对应的 IDE 插件，按照步骤安装 wakatime 插件(下图中灰色表示目前官方还不支持):
![所支持的IDE](https://file.wf2311.com/images/20190111125101.png)
3. 在[个人设置](https://wakatime.com/settings/account)页面复制 Secret API Key ，填入对应的 wakatime 插件中；
![复制 Secret API Key](https://file.wf2311.com/images/20190111124627.png)
4. 过一段时间后，你就可以在 wakatime 网站上看到你的编码情况，如下图所示： 
![](https://file.wf2311.com/images/20190111125255.png)

## wakatime-sync(本项目)
### 项目简介
  wakatime 提供了丰富多样的图表可以多维度地查看自己的编码时间情况。不过作为免费用户，最多只能查看自己最近14天的数据；如果要查看全部的数据，需要 $9/月的订阅费用。
  
  还好 wakatime 提供了 [API](https://wakatime.com/developers) 接口，通过接口可以获取到编码时间统计情况的原始数据(作为免费用户还是有只能查看最近14天数据的限制)。
  
  本项目通过 wakatime 提供的 API 接口，可以把自己的 wakatime 数据保存在自己的数据库库，然后利用图表插件展示出来，目前已完成三种类型的图表展示：
- 每日项目持续时间图：
  ![每日项目持续时间图](https://file.wf2311.com/images/20190111132136.png)
- 最近两周项目活动情况：
  ![最近两周项目活动情况](https://file.wf2311.com/images/20190111132651.png)
- 最近一年每日编码耗时情况：
  ![最近一年每日编码耗时情况](https://file.wf2311.com/images/20190111132759.png)
  
  不过因为本人能力有限，目前页面图表排版很差,还需要请他人帮忙优化。

### 所用技术
- 后端： JDK1.8、SpringBoot、Jodd-HTTP、Thymeleaf
- 数据库： MongoDB
- 前端：Moment.js、ElementUI、AntV-G2 、Echarts 

### 使用方法
替换或设置好`src/main/resources/application.yml`配置文件中的`wakatime.app.key`和`mongodb.wakatime-sync.uri`，采用maven打包的方式安装即可，支持Docker方式安装。

示例地址：[https://wakatime.wf2311.com](https://wakatime.wf2311.com)

> tips: 如果你一直在使用 wakatime ，如果想使用本项目同步你所有的历史数据，可以在官网上试用付费版的方式获得1个月(还是半个月？)的付费版功能，然后通过本项目来同步所有的历史数据：`POST /api/v1/sync`。
### TODO
- [ ] 页面样式优化；
- [ ] 数据同步成功消息通知；
- [ ] 查询接口缓存；
- [ ] 支持更多其他维度的数据展示；
- [ ] 可以对项目名称设置别名展示；