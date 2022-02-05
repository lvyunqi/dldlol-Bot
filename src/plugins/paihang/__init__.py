from nonebot import on_command
from nonebot.typing import T_State
from nonebot.adapters.cqhttp import Bot, MessageEvent
from ..function import *

zl = on_command('战力排行榜')
@zl.handle()
async def zhanli_handle(bot: Bot, event: MessageEvent, state: T_State):
    plain_text = str(event.get_message()).strip()  #首次发送命令时跟随的参数
    user_qq = str(event.user_id)
    username = get_username(user_qq)
    result = select_user(user_qq)
    result1 = select_wuhun(user_qq)
    if result == 0:
        await zl.send(message=('您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越'))
    elif result1 == 1:
        await zl.send(message=('您还没有觉醒武魂！请先觉醒武魂\n<可用命令>\n武魂觉醒'))
    else:
        str1 = plain_text
        if len(str1) > 0:
            allqq = get_alluserqq()
            qqlist = []
            zhanlilist = []
            zlqqlist = []
            for qq in allqq:
                nqq = qq[0]
                qqlist.append(nqq)
            for i in qqlist:
                pr = get_userinfo(i,'pr')#攻击
                de = get_userinfo(i,'de')#防御
                sp = get_userinfo(i,'sp')#精神力
                speed = get_userinfo(i,'speed')#速度
                hp = get_userinfo(i,'hp')#生命
                zhanli = get_ce(pr,hp,de,speed,sp)
                zhanlilist.append(zhanli)
            zldict = dict(zip(qqlist,zhanlilist))
            num = 0
            for k in sorted(zldict,key=zldict.__getitem__,reverse=True):
                num = num+1
                username = get_username(k)
                zhnum = turn(int(num))
                a = '第'+zhnum+'名：['+username+']['+str(k)+']'+str(zldict[k])+'战力'
                zlqqlist.append(a)
            allpage1 = len(zlqqlist)/5
            allpage = int(allpage1)+1
            n = int(str1)
            l = (n-1)*5+2
            r = 5*n+1
            e = ''.join('%s\n' %id for id in zlqqlist[l:r])
            n1 = '以下是[战力排行榜]：\n'
            n2 = e
            n3 = '·第'+str(n)+'页/共'+str(allpage)+'页'
            all1 = n1+n2+n3
            await zl.send(message=all1)
        else:
            allqq = get_alluserqq()
            qqlist = []
            zhanlilist = []
            zlqqlist = []
            for qq in allqq:
                nqq = qq[0]
                qqlist.append(nqq)
            for i in qqlist:
                pr = get_userinfo(i,'pr')#攻击
                de = get_userinfo(i,'de')#防御
                sp = get_userinfo(i,'sp')#精神力
                speed = get_userinfo(i,'speed')#速度
                hp = get_userinfo(i,'hp')#生命
                zhanli = get_ce(pr,hp,de,speed,sp)
                zhanlilist.append(zhanli)
            zldict = dict(zip(qqlist,zhanlilist))
            num = 0
            for k in sorted(zldict,key=zldict.__getitem__,reverse=True):
                num = num+1
                username = get_username(k)
                zhnum = turn(int(num))
                a = '第'+zhnum+'名：['+username+']['+str(k)+']'+str(zldict[k])+'战力'
                zlqqlist.append(a)
            allpage1 = len(zlqqlist)/5
            allpage = int(allpage1)+1
            e = ''.join('%s\n' %id for id in zlqqlist[0:6])
            n1 = '以下是[战力排行榜]：\n'
            n2 = e
            n3 = '·第1页/共'+str(allpage)+'页'
            all2 = n1+n2+n3
            await zl.send(message=all2)

lv = on_command('等级排行榜')
@lv.handle()
async def lv_handle(bot: Bot, event: MessageEvent, state: T_State):
    plain_text = str(event.get_message()).strip()  #首次发送命令时跟随的参数
    user_qq = str(event.user_id)
    result = select_user(user_qq)
    result1 = select_wuhun(user_qq)
    if result == 0:
        await zl.send(message=('您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越'))
    elif result1 == 1:
        await zl.send(message=('您还没有觉醒武魂！请先觉醒武魂\n<可用命令>\n武魂觉醒'))
    else:
        str1 = plain_text
        if len(str1)>0:
            allqq = get_alluserqq()
            qqlist=[]
            lvlist=[]
            qqlvlist=[]
            for qq in allqq:
                nqq = qq[0]
                qqlist.append(nqq)
            for item in qqlist:
                userlv = get_userinfo(item,'lv')
                lvlist.append(userlv)
            lvdict = dict(zip(qqlist,lvlist))
            num = 0
            for k in sorted(lvdict,key=lvdict.__getitem__,reverse=True):
                num = num+1
                username = get_username(k)
                zhnum = turn(int(num))
                a = '第'+zhnum+'名：['+username+']['+str(k)+']'+str(lvdict[k])+'级'
                qqlvlist.append(a)
            allpage1 = len(qqlvlist)/5
            allpage = int(allpage1)+1
            n = int(str1)
            l = (n-1)*5+2
            r = 5*n+1
            e = ''.join('%s\n' %id for id in qqlvlist[l:r])
            n1 = '以下是[等级排行榜]：\n'
            n2 = e
            n3 = '·第'+str(n)+'页/共'+str(allpage)+'页'
            all2 = n1+n2+n3
            await lv.send(message=all2)
        else:
            allqq = get_alluserqq()
            qqlist=[]
            lvlist=[]
            qqlvlist=[]
            for qq in allqq:
                nqq = qq[0]
                qqlist.append(nqq)
            for item in qqlist:
                userlv = get_userinfo(item,'lv')
                lvlist.append(userlv)
            lvdict = dict(zip(qqlist,lvlist))
            num = 0
            for k in sorted(lvdict,key=lvdict.__getitem__,reverse=True):
                num = num+1
                username = get_username(k)
                zhnum = turn(int(num))
                a = '第'+zhnum+'名：['+username+']['+str(k)+']'+str(lvdict[k])+'级'
                qqlvlist.append(a)
            allpage1 = len(qqlvlist)/5
            allpage = int(allpage1)+1
            e = ''.join('%s\n' %id for id in qqlvlist[0:6])
            n1 = '以下是[等级排行榜]：\n'
            n2 = e
            n3 = '·第1页/共'+str(allpage)+'页'
            all2 = n1+n2+n3
            await lv.send(message=all2)