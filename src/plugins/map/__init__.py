import json
from nonebot import on_command
from nonebot.adapters.onebot.v11 import Bot, GroupMessageEvent
from nonebot.adapters import Message
from nonebot.params import CommandArg
from models.function import get_mapwz, get_hscmlist, get_nowmap, get_map
from models.view import map_view
from models.command import get_command
from models.status_info import user_exist, get_userinfo

map = on_command("位置")

@map.handle()
async def map_handle(bot: Bot, event: GroupMessageEvent, args: Message = CommandArg()):
    #driver = nonebot.get_driver()
    group = get_command('group_list')
    isinstance_group = f'{event.group_id if isinstance(event, GroupMessageEvent) else "private"}'

    user_qq = str(event.user_id)  #获取玩家QQ号

    userexist = user_exist(event.user_id)
    if userexist != 0:
        if isinstance_group in group:
            await map.send(message=str(userexist))
        if isinstance_group == 'private':
            await map.send(message=str(userexist))
        elif isinstance_group not in group:
            pass
    
    if userexist == 0:
        userInfo = get_userinfo(user_qq,'state_info')
        user_data = json.loads(userInfo)
        user_name = user_data['userInfo']['name']
        wuhun = user_data['userData']['武魂']#武魂
        nowmap = get_nowmap(user_qq)
        map_list = get_mapwz(nowmap)
        hunshou = get_hscmlist(nowmap)
        is_tp = get_map(nowmap,'tp')
        message_data = map_view(user_name,wuhun,nowmap,map_list,hunshou,is_tp)
        if isinstance_group in group:
            await bot.send_group_msg(group_id = event.group_id,message=message_data)
        if isinstance_group == 'private':
            await map.send(message=message_data)
        elif isinstance_group not in group:
            pass
        
        