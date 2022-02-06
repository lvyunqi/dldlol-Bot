# 斗罗大陆OL-Bot

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
####Linux(建议使用Ubuntu20系列)
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
## 简单配置

```
1.在.env.dev文件中
  SUPERUSERS = [""]   # 填写你的QQ，这里是超级管理员账号，多个用英文逗号分开
2.在src/plugins/function.py文件中
  * 修改第7行到第12行dbinfo数据库连接信息
3.在src/plugins/battle.py文件中
  * 修改数据库连接信息
```

