from nonebot import on_command
from nonebot.adapters.onebot.v11 import Bot, GroupMessageEvent
from nonebot.adapters import Message
from nonebot.params import CommandArg
from models.function import event_plain_text, get_event_args
from models.command import get_command
from models.status_info import is_user
from models.add_data import add_user

dlreg = on_command("开始穿越")

@dlreg.handle()
async def handle_info(bot: Bot, event: GroupMessageEvent, args: Message = CommandArg()):
    event_text = event_plain_text(event.json(),4)
    user_qq = str(event.user_id)  #获取玩家QQ号

    isuser = is_user(user_qq)
    if isuser == 1:
        await dlreg.send(message=('您已经穿越到斗罗大陆了，不可以再穿越了哦！'+'\n'+'<可用命令>'+'\n'+'武魂觉醒'))
    if isuser == 0:
        print('第一位')

        if event_text == 0:
            await dlreg.send(message=("创建角色失败！注册格式：开始穿越 夜夜-男，注意名字不可超过6位！"))
        else:
            event_data = get_event_args(event_text,4)
            pos = str(event_data).find('-')
            if pos > 0:
                username = str(event_data)[:pos].strip()
                usersex = str(event_data)[pos+1]
                if usersex not in ["男","女"]:
                    await dlreg.send(message=("本游戏可不支持硅基生物的性别哦！请遵循本身意向性别按格式填写【男】或【女】！"))
                elif len(username)>6:
                    await dlreg.send(message=("您的名字过长，游戏名字不可超过6位！"))
                else:
                    start_map = get_command('start_map')
                    await dlreg.send(message=('To'+username+'〔未觉醒〕:\n 创建角色成功！\n您biu的一声从天上掉下['+start_map+']\n欢迎来到斗罗大陆世界，接下来我们来下基础的操作吧！这个世界最基础的是武魂，有了武魂后你才能去做很多事情，让我们发送[武魂觉醒]来觉醒吧！\n<可用命令>\n武魂觉醒'))
                    add_user(user_qq,username,usersex,start_map)

            
