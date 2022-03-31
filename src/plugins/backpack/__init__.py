import json
from nonebot import on_command
from nonebot.adapters.onebot.v11 import Bot, GroupMessageEvent
from nonebot.adapters import Message
from nonebot.params import CommandArg
from models.function import get_event_args, is_number, is_user_awaken, event_plain_text, select_alluserqq
from models.command import get_command
from models.num_calc import u_exp, p_exp, get_ce
from models.view import bp_view,bp_num_view
from models.status_info import user_exist, hunhuan_num, get_userinfo, get_lvname, get_upsp, get_spname
from src.plugins.backpack.bp_one import bp_one,bp_weapons

backpack = on_command("背包")
@backpack.handle()
async def handle_backpack(bot:  Bot, event: GroupMessageEvent, args: Message = CommandArg()):
    #driver = nonebot.get_driver()
    group = get_command('group_list')
    isinstance_group = f'{event.group_id if isinstance(event, GroupMessageEvent) else "private"}'

    event_text = event_plain_text(event.json(),2)
    plain_text = get_event_args(event_text,2)
    is_num = is_number(plain_text)

    #plain_text = args.extract_plain_text()  #发送命令时跟随的参数

    user_qq = str(event.user_id)  #获取玩家QQ号

    userexist = user_exist(event.user_id)
    if userexist != 0:
        if isinstance_group in group:
            await backpack.send(message=str(userexist))
        if isinstance_group == 'private':
            await backpack.send(message=str(userexist))
        elif isinstance_group not in group:
            pass
    if userexist == 0:
        userInfo = get_userinfo(user_qq,'state_info')
        user_data = json.loads(userInfo)
        bp,mon = bp_one(user_qq)
        #当指令未带任何参数时
        if event_text == 0:
            message_data = bp_view(bp,mon,user_data)
            if isinstance_group in group:
                await backpack.send(message=message_data)
            if isinstance_group == 'private':
                await backpack.send(message=message_data)
            elif isinstance_group not in group:
                pass
        elif is_num == 1:
            #为1是数字
            message_data = bp_num_view(bp,mon,plain_text,user_data)
            if isinstance_group in group:
                await backpack.send(message=message_data)
            if isinstance_group == 'private':
                await backpack.send(message=message_data)
            elif isinstance_group not in group:
                pass
        else:
            pass

weapons_bp = on_command("魂导器背包")
@weapons_bp.handle()
async def handle_weapons_bp(bot:  Bot, event: GroupMessageEvent, args: Message = CommandArg()):
    #driver = nonebot.get_driver()
    group = get_command('group_list')
    isinstance_group = f'{event.group_id if isinstance(event, GroupMessageEvent) else "private"}'

    event_text = event_plain_text(event.json(),5)
    plain_text = get_event_args(event_text,5)
    is_num = is_number(plain_text)

    #plain_text = args.extract_plain_text()  #发送命令时跟随的参数

    user_qq = str(event.user_id)  #获取玩家QQ号

    userexist = user_exist(event.user_id)
    if userexist != 0:
        if isinstance_group in group:
            await weapons_bp.send(message=str(userexist))
        if isinstance_group == 'private':
            await weapons_bp.send(message=str(userexist))
        elif isinstance_group not in group:
            pass
    if userexist == 0:
        userInfo = get_userinfo(user_qq,'state_info')
        user_data = json.loads(userInfo)
        bp,mon = bp_weapons(user_qq)
        #当指令未带任何参数时
        if event_text == 0:
            message_data = bp_view(bp,mon,user_data)
            if isinstance_group in group:
                await weapons_bp.send(message=message_data)
            if isinstance_group == 'private':
                await weapons_bp.send(message=message_data)
            elif isinstance_group not in group:
                pass
        elif is_num == 1:
            #为1是数字
            message_data = bp_num_view(bp,mon,plain_text,user_data)
            if isinstance_group in group:
                await weapons_bp.send(message=message_data)
            if isinstance_group == 'private':
                await weapons_bp.send(message=message_data)
            elif isinstance_group not in group:
                pass
        else:
            pass