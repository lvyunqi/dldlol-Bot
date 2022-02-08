from nonebot import on_command
from nonebot.typing import T_State
from nonebot.adapters import Bot, Event
from numpy import power
from ..function import *


whjx = on_command("武魂觉醒")
@whjx.handle()
async def handle_whjx(bot: Bot, event: Event, state: T_State):
    user_qq = str(event.user_id)
    username = get_username(user_qq)
    user_wh = r_wh()
    lv = r_lv()
    await w_whjx(user_qq,username,user_wh,lv)
    

async def w_whjx(user_qq,username,user_wh,lv):
    result = select_user(user_qq)
    result1 = select_wuhun(user_qq)
    if result==0:
        await whjx.send(message=('您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越'))
    elif result1 == 0:
        await whjx.send(message=('您已经觉醒过武魂了！\n<可用命令>\n状态'))
    else:
        type = ['pow','dodge','ct','ctp','cdadd','pr','de','mp','speed','sp','des','category']
        hp = random.randint(250,480)
        power = get_whdata(user_wh,type[0])+random.randint(1,5)*lv
        dodge = get_whdata(user_wh,type[1])
        ct = get_whdata(user_wh,type[2])
        ctp = get_whdata(user_wh,type[3])
        cdadd = get_whdata(user_wh,type[4])
        pr = get_whdata(user_wh,type[5])+random.randint(1,5)*lv
        def1 = get_whdata(user_wh,type[6])+random.randint(1,10)
        mp = get_whdata(user_wh,type[7])+random.randint(10,30)
        speed = get_whdata(user_wh,type[8])
        sp = get_whdata(user_wh,type[9])+random.randint(30,60)
        des = get_whdata(user_wh,type[10])
        type1 = get_whdata(user_wh,type[11])
        con = 300
        exp = 0
        result1 = w_wh(user_qq,power,dodge,ct,ctp,cdadd,pr,def1,mp,mp,speed,sp,des,exp,type1,user_wh,lv,hp,con,hp)
        if result1 == 0:
            await whjx.send(message=('To'+username+'〔'+user_wh+'〕:\n恭喜您觉醒成功，您的武魂信息如下:\n·武魂名称：'+user_wh+'\n·武魂系列：'+type1+'\n·武魂描述：'+des+'\n·先天魂力：'+str(lv)+'\n<可用命令>\n状态\n<新手教程>\n位置'))
