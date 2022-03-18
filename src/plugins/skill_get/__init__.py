import random
import nonebot
from nonebot import on_command
from nonebot.adapters.onebot.v11 import Bot, GroupMessageEvent
from nonebot.adapters import Message
from nonebot.params import CommandArg
from ..__function import random_wh
from ..__command import get_command
from ..__status_info import get_whdata, get_userinfo, select_user
from ..__add_data import user_awaken

skill_get = on_command("武魂觉醒")

@skill_get.handle()

async def skill_get_handle(bot: Bot, event: GroupMessageEvent, args: Message = CommandArg()):
    driver = nonebot.get_driver()
    group = driver.config.group_id
    isinstance_group = f'{event.group_id if isinstance(event, GroupMessageEvent) else "private"}'


    user_qq = event.user_id  #获取玩家QQ号

    is_user = select_user(user_qq)
    is_wuhun = get_userinfo(user_qq,'state_info')
    if is_user == 0:
        if isinstance_group in group:
            await skill_get.send(message='您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越')
        if isinstance_group == 'private':
            await skill_get.send(message='您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越')
        elif isinstance_group not in group:
            pass
    elif is_wuhun != None:
        if isinstance_group in group:
            await skill_get.send(message='您已经觉醒武魂，请不要重复觉醒！\n<可用命令>\n状态')
        if isinstance_group == 'private':
            await skill_get.send(message='您已经觉醒武魂，请不要重复觉醒！\n<可用命令>\n状态')
        elif isinstance_group not in group:
            pass
    else:
        user_name = get_userinfo(user_qq,'name')
        user_sex = get_userinfo(user_qq,'sex')
        user_wuhun = random_wh()
        user_lv = random.randint(1,10)
        user_con = get_command('con')
        up_exp= get_command('exp_up')
        user_awaken(user_qq,user_name,user_sex,user_wuhun,user_lv,user_con,up_exp)
        if isinstance_group in group:
            await skill_get.send(('To'+user_name+'〔'+user_wuhun+'〕:\n恭喜您觉醒成功，您的武魂信息如下:\n·武魂名称：'+user_wuhun+'\n·武魂系列：'+get_whdata(user_wuhun,'category')+'\n·武魂描述：'+get_whdata(user_wuhun,'des')+'\n·先天魂力：'+str(user_lv)+'\n<可用命令>\n状态\n<新手教程>\n位置'))
        if isinstance_group == 'private':
            await skill_get.send(('To'+user_name+'〔'+user_wuhun+'〕:\n恭喜您觉醒成功，您的武魂信息如下:\n·武魂名称：'+user_wuhun+'\n·武魂系列：'+get_whdata(user_wuhun,'category')+'\n·武魂描述：'+get_whdata(user_wuhun,'des')+'\n·先天魂力：'+str(user_lv)+'\n<可用命令>\n状态\n<新手教程>\n位置'))
        elif isinstance_group not in group:
            pass