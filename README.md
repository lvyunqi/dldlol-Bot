# 斗罗大陆OL-Bot

[![star](https://gitee.com/lvyunqi/dldlol-Bot/badge/star.svg?theme=dark)](https://gitee.com/lvyunqi/dldlol-Bot/stargazers)
****
此项目基于 Springboot开发，以 MySQL 作为数据库的QQ群娱乐机器人
#### 介绍
基于Springboot的大型RPG群文字游戏

## 关于
免费开源，使用GPL2.0协议

目前此项目还在开发中

如果希望有个地方讨论，或者有问题或建议，可以发送issues或加入[ <strong>[斗罗大陆·S2公测一群](https://jq.qq.com/?_wv=1027&k=t82s58Pl)</strong>]

## 声明

此项目仅用于学习交流，请勿用于非法用途

 **未经允许，请遵守GPL2.0协议，禁止私自商业化！** 

### 感谢以下赞助人员的支持
* 洛神赋1257267102 赞助100
* 陈啟铵113951929 赞助11.78
* [傲绝1875390189](http://ascv.cn) 赞助5

## 当前功能

* 战斗
* 属性查看
* 魂环附加
* 魂技
* 地图等

## 环境需求

* [Java SE - 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)或者更高
 **注意:** 如果想仅**运行服务端**, 只下载 **jre** 即可
* [MySql5.7](https://downloads.mysql.com/archives/community/)或更高

### 运行服务端
1. 下载已发布的版本（`QimenBot.jar`）或自行构建
2. 下载[数据包](https://gitee.com/lvyunqi/dldlol-Bot/releases)（`Data.zip`）
3. 解压数据包并将jar包放入解压目录下
4. MySql创建数据库，并导入SQL目录下数据库文件
5. 修改`/config/application.yml`文件中数据库配置信息
6. **确认 MySql 服务运行正常后**，使用命令行 `java -jar QimenBot.jar` 运行程序

### Bot客户端（go-cqhttp）
1. 在 https://github.com/Mrs4s/go-cqhttp 下载Releases最新版本，运行后选择反向代理，
  后将gocq的配置文件config.yml中的universal改为universal: ws://127.0.0.1:8088/cqhttp/ws
2. 一切正常直接运行即可

## 感谢
[MisakaTAT / Shiro](https://github.com/MisakaTAT/Shiro) ：跨平台Java异步机器人框架

[Mrs4s / go-cqhttp](https://github.com/Mrs4s/go-cqhttp) ：cqhttp的golang实现，轻量、原生跨平台.  