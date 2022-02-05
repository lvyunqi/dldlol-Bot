from this import d
from nonebot import on_command
from nonebot.typing import T_State
from nonebot.adapters.cqhttp import Bot, MessageEvent
from ..function import *

map = on_command('位置')
@map.handle()
async def map_handle(bot: Bot, event: MessageEvent, state: T_State):
    user_qq = str(event.user_id)
    result = select_user(user_qq)
    result1 = select_wuhun(user_qq)
    if result == 0:
        await map.send(message=('您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越'))
    elif result1 == 1:
        await map.send(message=('您还没有觉醒武魂！请先觉醒武魂\n<可用命令>\n武魂觉醒'))
    else:
        nowmap = get_mapwz(user_qq)
        await map.send(message=nowmap,auto_escape=False)

map2 = on_command('向')
@map2.handle()
async def map2_handle(bot: Bot, event: MessageEvent, state: T_State):
    plain_text = str(event.get_message()).strip()  #首次发送命令时跟随的参数
    user_qq = str(event.user_id)
    username = get_username(user_qq)
    wh = 'wuhun'
    wuhun = get_userinfo(user_qq,wh)#武魂
    result = select_user(user_qq)
    result1 = select_wuhun(user_qq)
    nowmap = get_nowmap(user_qq)
    if result == 0:
        await map2.send(message=('您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越'))
    elif result1 == 1:
        await map2.send(message=('您还没有觉醒武魂！请先觉醒武魂\n<可用命令>\n武魂觉醒'))
    r = ['上','下','左','右']
    str1 = plain_text
    if str1 not in r:
        await map2.send(message=('To'+username+'〔'+wuhun+'〕:\n格式不对哟！\n<可用命令>\n向 上/下/左/右'))
    else:
        if str1 == '上':
            typ = 't'
            t = get_map(nowmap,typ)
            if t == '无':
                await map2.send(message=('To'+username+'〔'+wuhun+'〕:\n没有这条路哦！带上眼睛看清地图再走吧！\n<可用命令>\n向 上/下/左/右'))
            else:
                rel = w_map(user_qq,t)
                if rel == 0:
                    nowmap = get_mapwz(user_qq)
                    await map2.send(message=nowmap)
        elif str1 == '下':
            typ = 'd'
            d = get_map(nowmap,typ)
            if d == '无':
                await map2.send(message=('To'+username+'〔'+wuhun+'〕:\n没有这条路哦！带上眼睛看清地图再走吧！\n<可用命令>\n向 上/下/左/右'))
            else:
                rel = w_map(user_qq,d)
                if rel == 0:
                    nowmap = get_mapwz(user_qq)
                    await map2.send(message=nowmap)
        elif str1 == '左':
            typ = 'l'
            l = get_map(nowmap,typ)
            if l == '无':
                await map2.send(message=('To'+username+'〔'+wuhun+'〕:\n没有这条路哦！带上眼睛看清地图再走吧！\n<可用命令>\n向 上/下/左/右'))
            else:
                rel = w_map(user_qq,l)
                if rel == 0:
                    nowmap = get_mapwz(user_qq)
                    await map2.send(message=nowmap)
        else:
            typ = 'r'
            r = get_map(nowmap,typ)
            if r == '无':
                await map2.send(message=('To'+username+'〔'+wuhun+'〕:\n没有这条路哦！带上眼睛看清地图再走吧！\n<可用命令>\n向 上/下/左/右'))
            else:
                rel = w_map(user_qq,r)
                if rel == 0:
                    nowmap = get_mapwz(user_qq)
                    await map2.send(message=nowmap)
            
tp = on_command('传送')
@tp.handle()
async def tp_handle(bot: Bot, event: MessageEvent, state: T_State):
    plain_text = str(event.get_message()).strip()  #首次发送命令时跟随的参数
    user_qq = str(event.user_id)
    username = get_username(user_qq)
    wh = 'wuhun'
    wuhun = get_userinfo(user_qq,wh)#武魂
    result = select_user(user_qq)
    result1 = select_wuhun(user_qq)
    nowmap = get_nowmap(user_qq)
    if result == 0:
        await tp.send(message=('您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越'))
    elif result1 == 1:
        await tp.send(message=('您还没有觉醒武魂！请先觉醒武魂\n<可用命令>\n武魂觉醒'))
    else:
        maplist = get_maplist()
        str2 = plain_text
        if str2 not in str(maplist):
            await tp.send(message=('To'+username+'〔'+wuhun+'〕:\n【'+str2+'】不存在，您是不是做梦梦到的？\n<可用命令>\n传送 地图名'))
        else:
            tp2 = 'tp'
            tp1 = get_map(nowmap,tp2)
            if tp1 == 0:
                await tp.send(message=('To'+username+'〔'+wuhun+'〕:\n您当前的地图没有传送装置，不能传送！\n<可用命令>\n传送 地图名'))
            else:
                tp3 = 'tp'
                tp4 = get_map(str2,tp3)
                if tp4 == 0:
                    await tp.send(message=('To'+username+'〔'+wuhun+'〕:\n您要传送的地图没有传送装置，不能传送！\n<可用命令>\n传送 地图名'))
                else:
                    rel2 = w_map(user_qq,str2)
                    if rel2 == 0:
                        mapwz = get_mapwz(user_qq)
                        await tp.send(message=mapwz)
                    else:
                        await tp.send(message=('位置信息写入数据库错误！请及时联系管理员处理！'))
                        
        