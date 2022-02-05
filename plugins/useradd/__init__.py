from nonebot import on_command
from nonebot.typing import T_State
from nonebot.adapters.cqhttp import Bot, MessageEvent
from ..function import *

dlreg = on_command("开始穿越")
@dlreg.handle()
async def handle_dlreg(bot: Bot, event: MessageEvent, state: T_State):
    plain_text = str(event.get_message()).strip()  #首次发送命令时跟随的参数
    user_qq = str(event.user_id)
    result = select_user(user_qq)
    if result == 1:
        await dlreg.send(message=('您已经穿越到斗罗大陆了，不可以再穿越了哦！'+'\n'+'<可用命令>'+'\n'+'武魂觉醒'))
        print(result)
    else:
        if len(plain_text)==0:
            await dlreg.send(message=("创建角色失败！注册格式：开始穿越 夜夜-男，注意名字不可超过6位！"))
        else:
            str1 = plain_text
            pos = str1.find('-')
            if pos > 0:
                username = str1[:pos]
                usersex = str1[pos+1]
                if usersex not in ["男","女"]:
                    await dlreg.send(message=("本游戏可不支持硅基生物的性别哦！请遵循本身意向性别按格式填写【男】或【女】！"))
                elif len(username)>6:
                    await dlreg.send(message=("您的名字过长，游戏名字不可超过6位！"))
                else:
                    useradd = user_add(user_qq,username,usersex)
                    if useradd == 0:
                        type = 'nowmap'
                        nowmap = get_userinfo(user_qq,type)
                        await dlreg.send(message=('To'+username+'〔未觉醒〕:\n 创建角色成功！\n您biu的一声从天上掉下['+nowmap+']\n欢迎来到斗罗大陆世界，接下来我们来下基础的操作吧！这个世界最基础的是武魂，有了武魂后你才能去做很多事情，让我们发送[武魂觉醒]来觉醒吧！\n<可用命令>\n武魂觉醒'))