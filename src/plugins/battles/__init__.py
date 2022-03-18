import nonebot
from nonebot import on_command
from nonebot.adapters.onebot.v11 import Bot, GroupMessageEvent
from nonebot.adapters import Message
from nonebot.params import CommandArg
from ..__function import get_message_at, is_number, is_user_awaken, event_plain_text, select_alluserqq
from ..__command import get_command
from ..__num_calc import u_exp, p_exp, get_ce
from ..__view import user_info_view_myself, user_info_view_other
from ..__status_info import user_exist, status_info, hunhuan_num, get_userinfo, get_lvname, get_upsp, get_spname

battle = on_command('挑战魂兽')
@battle.handle()
async def handle_battle(bot: Bot, event: GroupMessageEvent, args: Message = CommandArg()):
    driver = nonebot.get_driver()
    group = driver.config.group_id
    isinstance_group = f'{event.group_id if isinstance(event, GroupMessageEvent) else "private"}'

    event_text = event_plain_text(event.json(),2) 

    #plain_text = args.extract_plain_text()  #发送命令时跟随的参数

    user_qq = str(event.user_id)  #获取玩家QQ号

    userexist = user_exist(event.user_id)
    if userexist != 0:
        if isinstance_group in group:
            await battle.send(message=str(userexist))
        if isinstance_group == 'private':
            await battle.send(message=str(userexist))
        elif isinstance_group not in group:
            pass
    if userexist == 0:
        if event_text == 0:
            if isinstance_group in group:
                await battle.send(message='请')
        if isinstance_group == 'private':
            await battle.send(message=str(userexist))
        elif isinstance_group not in group:
            pass

