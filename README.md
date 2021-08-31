# wakatime-sync

## 数据库准备
建好MySQL数据库后后，按序号顺序依次执行`sql`目录下的sql脚本

## 参数说明：

|参数 | 含义 | 默认值|
| --- | --- | --- |
| SERVER_PORT | 服务端口号 | 3040 |
| WAKATIME_APP_KEY | wakatime app key | <nil> |
| WAKATIME_PROXY_URL | wakatime接口访问代理地址,例如：socks5://127.0.0.1:1080 请根据实际情况配置 | false,表示不开启代理 |
| WAKATIME_FTQQ_KEY | Server酱 SCKEY,见http://sc.ftqq.com，为空表示不发生此类消息通知 | <nil> |
| WAKATIME_DINGDING_KEY | 钉钉机器人token，为空表示不发生此类消息通知 | <nil> |
| START_DAY | 起始记录时间 | 2016-01-01 |
| MYSQL_URL | MySQL连接地址 | jdbc:mysql://127.0.0.1:3306/wakatime?characterEncoding=utf8&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=PRC |
| MYSQL_USERNAME | MySQL用户名 | wakatime |
| MYSQL_PASSWORD | MySQL密码 | 123456 |
| JVM_OPTS | JVM运行参数，以容器方式启动时有用 | -Xmx256m -Xms64m -Xss256k |

## quick start

### 本地调试
1. 根据说明，完善`src/main/resources/application-config.properties`中的参数
2. 运行`WakatimeSyncApplication`启动类；

### 构建Docker镜像
```shell
mvn -DskipTests clean package
docker build -t wf2311/wakatime-sync:1.0 .
```

## 使用容器运行
### docker方式

```shell
docker run -d -t  -p 3040:3040 -v ~/logs/wakatime-sync/:/application/logs \
-e JAVA_OPTS='-Xmx256m -Xms64m -Xss256k' \
-e SERVER_PORT='3040' \
-e WAKATIME_APP_KEY='<WAKATIME_APP_KEY>' \
-e WAKATIME_PROXY_URL='false' \
-e WAKATIME_FTQQ_KEY='<WAKATIME_FTQQ_KEY>' \
-e WAKATIME_DINGDING_KEY='<WAKATIME_DINGDING_KEY>' \
-e START_DAY='2020-01-01' \
-e MYSQL_URL='jdbc:mysql://<ip:port>/wakatime?characterEncoding=utf8&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=PRC' \
-e MYSQL_USERNAME='wakatime' \
-e MYSQL_PASSWORD='123456' \
--name wakatime-sync wf2311/wakatime-sync:1.0
```

### docker-compose
docker-compose.yml文件
```yaml
version: "3"
services:
  wakatime-sync:
    image: wf2311/wakatime-sync:1.0
    container_name: wakatime-sync
    environment:
      - JAVA_OPTS=-Xmx256m -Xms64m -Xss256k
      - SERVER_PORT=3040
      - WAKATIME_APP_KEY=<WAKATIME_APP_KEY>
      - WAKATIME_PROXY_URL=false
      - WAKATIME_FTQQ_KEY=<WAKATIME_FTQQ_KEY>
      - WAKATIME_DINGDING_KEY=<WAKATIME_DINGDING_KEY>
      - START_DAY=2016-02-01
      - MYSQL_URL=jdbc:mysql://<ip:port>/wakatime?characterEncoding=utf8&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=PRC
      - MYSQL_USERNAME=wakatime
      - MYSQL_PASSWORD=123456
    volumes:
      - ~/Share/logs/wakatime-sync/:/application/logs
    ports:
      - "3040:3040"
```

### 注意事项
```
jdbc:mysql://<mysql-ip>:<mysql-port>/<mysql-db>?characterEncoding=utf8&...
```
1. 数据库使用 `jdbc` 进行通信。在上述的 `jdbc-url` 中，如果 `<mysql-ip>` 填写 `localhost` 或者 `127.0.0.1` 是无法指向宿主机的，建议直接使用公网 IP。如果你没有公网 IP，网上也有相关的解决方案。当然，如果你使用 `docker-compose`，`<mysql-ip>` 填写 `mysql` 服务的名称即可。

2. 使用 `jre` 或者 `jdk` 的镜像，`oracle` 可能会要求使用者进行一些验证，详见：
> https://blog.csdn.net/wengyupeng/article/details/87897866

3. 此项目每天都会自动爬取前一天的记录。如果需要导入历史数据，你可以手动调用项目的接口：
```shell
curl -X POST http://<your-domain>:<your-port>/api/v1/sync \
-d day=<sync-days-num> \
-d apiKey=<your-wakatime-api-key>
```
---


### WakaTime简介 
[WakaTime](https://wakatime.com/) 是一款可以记录你的编码时间的工具，目前支持绝大部分主流的 IDE 以及 Chrome 浏览器。

### 使用步骤
1. [注册](https://wakatime.com/signup) WakaTime 账号；
2. 在[官网](https://wakatime.com/plugins)找到对应的 IDE 插件，按照步骤安装 WakaTime 插件(下图中灰色表示目前官方还不支持):
![所支持的IDE](https://file.wf2311.com/images/20190111125101.png)
3. 在[个人设置](https://wakatime.com/settings/account)页面复制 Secret API Key ，填入对应的 WakaTime 插件中；
![复制 Secret API Key](https://file.wf2311.com/images/20190111124627.png)
4. 过一段时间后，你就可以在 WakaTime 网站上看到你的编码情况，如下图所示： 
![](https://file.wf2311.com/images/20190111125255.png)

## wakatime-sync项目
### 项目简介
  WakaTime 提供了丰富多样的图表可以多维度地查看自己的编码时间情况。不过作为免费用户，最多只能查看自己最近14天的数据；如果要查看全部的数据，需要 $9/月的订阅费用。

  还好 WakaTime 提供了 [API](https://wakatime.com/developers) 接口，通过接口可以获取到编码时间统计情况的原始数据(作为免费用户还是有只能查看最近14天数据的限制)。

  本项目通过 WakaTime 提供的 API 接口，可以把自己的 WakaTime 数据保存在的数据库中，然后利用图表插件展示出来，目前已完成三种类型的图表展示：
- 每日项目持续时间图：
  ![每日项目持续时间图](https://file.wf2311.com/images/20190115180738.png)
- 时间范围内活动情况：
  ![时间范围内活动情况](https://file.wf2311.com/images/20190115180838.png)
- 每日编码耗时日历图：
  ![每日编码耗时日历图](https://file.wf2311.com/images/20190115180946.png)

### 项目地址
- GITHUB: [https://github.com/wf2311/wakatime-sync](https://github.com/wf2311/wakatime-sync)
- 码云: [https://gitee.com/wf2311/wakatime-sync](https://gitee.com/wf2311/wakatime-sync)

### 所用技术
后端：JDK1.8、SpringBoot、Jodd-HTTP、Thymeleaf;

数据库：MySQL 5.7;

前端：Moment.js、ElementUI、AntV-G2 、Echarts;

通知服务：[Server酱](http://sc.ftqq.com/3.version)、[钉钉机器人](https://open-doc.dingtalk.com/docs/doc.htm?spm=a219a.7629140.0.0.61074a9747Wldt&treeId=257&articleId=105735&docType=1)

项目逻辑比较简单，就是每天会定时通过 WakaTime 的 API 抓取并保存前一天的数据，再通过图标插件展示出来。之后还会完善接口缓存、同步通知等功能；

同时使用了 AntV-G2 和 Echarts 的原因是因为持续时间图可以用 AntV-G2 实现，但日历图用 AntV-G2 实现过于复杂，就采用了用 Echarts 实现日历图；

另外本项目最初是的数据库是 MongoDB ，但是考虑到通用性和易用性后来又换成了 MySQL。MongoDB 版本的代码也实现了相关的同步展示逻辑，代码在 [mongodb](https://github.com/wf2311/wakatime-sync/tree/mongdb) 分支中。

### 使用方法
替换或设置好`src/main/resources/application.yml`配置文件中的`wakatime.app.key`和`spring.datasource.*` 相关数据库配置，采用 maven 打包的方式安装即可，支持 Docker 方式安装。

数据库建库脚本位于`sql/wakatime_sync.sql`中。

数据库使用 MongoDB 的版本位于分支 [mongdb](https://github.com/wf2311/wakatime-sync/tree/mongdb) 中。

示例地址：[https://wakatime.wangfeng.pro/](https://wakatime.wangfeng.pro/)。

### 消息通知
系统中有一个定时任务，会在每天早上09:00会根据配置信息想钉钉或微信发送上一天的编码时间信息；
需要在`application.yml`配置对应的参数:
- Server酱微信通知：按照[Server酱网站说明](http://sc.ftqq.com/3.version)获得一个**SCKEY**，设置成`wakatime.ftqq-key`的值；
- 钉钉机器人通知：在要获得提醒的钉钉群里面生成一个**自定义机器人**，将机器人的 Hook 地址中的 access_token 的值设置成`wakatime.dingding-key`的值；

**如果不想使用对应的消息通知，请将`application.yml`中对应的参数注释掉或将值置为空**


### 可能会遇到的问题

1. 由于本项目采用的是SpringBoot 2，对应的 `mysql-connector-java`  驱动使用的是MySQL服务端的时区，如果你使用的MySQL的时区和你程序中的时区以及你在 [WakaTime 个人设置](https://wakatime.com/settings/preferences)中的时区不一致，就会导致保存的相关数据中时间不准，解决办法就是首先调整好 [WakaTime 个人设置](https://wakatime.com/settings/preferences)里的时区，再调整 MySQL 数据库的时区，或者是使用 `5.X`版本的`mysql-connector-java`驱动。
2. 如果你一直在使用 WakaTime ，如果想使用本项目同步你所有的历史数据，可以在官网上试用团队版的方式获得1个月(还是半个月？)的付费版功能或者是订阅一个月的付费版，然后通过本项目来同步所有的历史数据：`POST /api/v1/sync` 或参见项目中的测试方法。使用测试方法进行时不能同时使用太多的线程去同时调用 API 接口，会被限流。

## TODO
- [X] 昨日编码消息消息通知；
- [ ] 查询接口缓存；
- [ ] 可以对项目名称设置别名展示；

## 更新记录
### 2021-08-31
- Server酱更新至新版推送方式，参见[Server酱](https://sct.ftqq.com/sendkey)，需要修改新的SendKey
### 2021-05-09
- 使用SpringBoot分层jar包进行构建Docker镜像
- 优化使用Docker运行时的参数传递
- 添加DockerCompose运行说明
### 2019-02-08
- 添加获取 wakatime api 数据接口的代理配置，解决 Wakatime 网站被墙导致无法获取接口数据的问题；
### 2019-02-02
- 新增图表交互：点击Activity或Calendar图指定日期的数据时，Duration图会显示对应日期的数据；
### 2019-01-23
- 页面优化：修改时间区间选择器在移动端的位置；
### 2019-01-20
- 活动图中如果某天没数据，可以选择是否要用空数据填充；
- 接口参数校验；
- bugfix: 某天无数据发送消息时的空指针错误
### 2019-01-17
- 修改图表中坐标轴的文字颜色为与背景颜色区分较大的白色；
- 持续时间图当天没数据时不显示图表结构；
- 新增了定时任务：每天早上09:00会根据配置信息想钉钉或微信发送上一天的编码时间信息；