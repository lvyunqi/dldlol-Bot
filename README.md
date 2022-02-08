# 斗罗大陆OL-Bot

[![star](https://gitee.com/lvyunqi/dldlol-Bot/badge/star.svg?theme=dark)](https://gitee.com/lvyunqi/dldlol-Bot/stargazers)
****
此项目基于 Nonebot2 和 go-cqhttp 开发，以 MySQL 作为数据库的QQ群娱乐机器人
#### 介绍
基于Python/NoneBot/go-cqhttp/CQHTTP的大型RPG群文字游戏

## 关于
免费开源，使用GPL2.0协议

目前此项目还在开发中

如果希望有个地方讨论，或者有问题或建议，可以发送issues或加入[ <strong>[斗罗大陆·S2公测一群](https://jq.qq.com/?_wv=1027&k=t82s58Pl)</strong>]

## 声明

此项目仅用于学习交流，请勿用于非法用途

 **未经允许，请遵守GPL2.0协议，禁止私自商业化！** 

## 简单部署

 **安装前请先配置好Python3（作者使用的Python3.8.10）** 

#### Linux(建议使用Ubuntu20系列)
```
# 配置gocq
在 https://github.com/Mrs4s/go-cqhttp 下载Releases最新版本，运行后选择反向代理，
  后将gocq的配置文件config.yml中的universal改为universal: ws://127.0.0.1:8080/cqhttp/ws
# 获取代码
git clone https://gitee.com/lvyunqi/dldlol-Bot.git
# 进入目录
cd dldlol-Bot
# 安装依赖
pip install -r requirements.txt
# 进行基础配置
####请查看 配置 部分####
# 开始运行
python bot.py
```

#### Windows
```
# 配置gocq
在 https://github.com/Mrs4s/go-cqhttp 下载Releases最新版本，运行后选择反向代理，
  后将gocq的配置文件config.yml中的universal改为universal: ws://127.0.0.1:8080/cqhttp/ws
# 获取代码
https://gitee.com/lvyunqi/dldlol-Bot/repository/archive/master.zip
# 解压进入目录
# 安装依赖
依赖库在requirements.txt中，请根据实际情况安装
# 进行基础配置
####请查看 配置 部分####
# 开始运行
python bot.py
```

## 简单配置

```
1.在.env.dev文件中
  SUPERUSERS = [""]   # 填写你的QQ，这里是超级管理员账号，多个用英文逗号分开
2.在src/plugins/function.py文件中
  * 修改第7行到第12行dbinfo数据库连接信息
3.在src/plugins/battle.py文件中
  * 修改数据库连接信息
```

## 更新

### 2022/2/7

* 新增玩家/魂兽释放魂技CD冷却
* 调整玩家前10级等级升级各项属性只增幅1点
* 调整魂兽最多攻击次数为10次


## 感谢
[botuniverse / onebot](https://github.com/botuniverse/onebot) ：超棒的机器人协议  
[Mrs4s / go-cqhttp](https://github.com/Mrs4s/go-cqhttp) ：cqhttp的golang实现，轻量、原生跨平台.  
[nonebot / nonebot2](https://github.com/nonebot/nonebot2) ：跨平台Python异步机器人框架  