#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import nonebot
from nonebot.adapters.cqhttp import Bot as CQHTTPBot


nonebot.init()
nonebot.init(apscheduler_autostart=True)

nonebot.init(apscheduler_config={
    "apscheduler.timezone": "Asia/Shanghai"
})

driver = nonebot.get_driver()
driver.register_adapter("cqhttp", CQHTTPBot)
# 加载插件目录，该目录下为各插件，以下划线开头的插件将不会被加载
nonebot.load_plugins("src/plugins")
nonebot.load_builtin_plugins()



if __name__ == "__main__":
    nonebot.run()
