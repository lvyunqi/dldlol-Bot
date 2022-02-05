from nonebot import on_command
from nonebot.typing import T_State
from nonebot.adapters.cqhttp import Bot, MessageEvent
from ..function import *

info = on_command("状态")
@info.handle()
async def handle_info(bot: Bot, event: MessageEvent, state: T_State):
    plain_text = str(event.get_message()).strip()  #首次发送命令时跟随的参数
    user_qq = str(event.user_id)
    result = select_user(user_qq)
    result1 = select_wuhun(user_qq)
    r = get_infoedqq(plain_text)
    if len(plain_text)==0:
        if result==0:
            await info.send(message=('您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越'))
        elif result1 == 1:
            await info.send(message=('您还没有觉醒武魂！请先觉醒武魂\n<可用命令>\n武魂觉醒'))
        else:
            type = ['pow','dodge','ct','ctp','cdadd','pr','de','mp','speed','sp','des','category','exp','con','lv','wuhun','hp','nowhp','nowmp']
            power = get_userinfo(user_qq,type[0])#力量
            dodge = get_userinfo(user_qq,type[1])#闪避
            ct = get_userinfo(user_qq,type[2])#暴击率
            ctp = get_userinfo(user_qq,type[3])#暴击伤害
            pr = get_userinfo(user_qq,type[5])#攻击
            def1 = get_userinfo(user_qq,type[6])#防御
            mp = get_userinfo(user_qq,type[7])#魂力值
            speed = get_userinfo(user_qq,type[8])#速度
            sp = get_userinfo(user_qq,type[9])#精神力
            type1 = get_userinfo(user_qq,type[11])#武魂类型
            exp = get_userinfo(user_qq,type[12])#当前经验
            con = get_userinfo(user_qq,type[13])#体力值
            lv = get_userinfo(user_qq,type[14])#等级
            wuhun = get_userinfo(user_qq,type[15])#武魂
            hp = get_userinfo(user_qq,type[16])#生命
            nowhp = get_userinfo(user_qq,type[17])
            nowmp = get_userinfo(user_qq,type[18])
            username = get_username(user_qq)
            up = 1
            hhnum = get_hhnum(user_qq)
            lvname = get_lvname(hhnum)
            sp_name = get_spname(sp)
            up_exp = u_exp(lv,up)
            n_exp = p_exp(exp,up_exp)
            upsp = get_upsp(sp)
            ce = get_ce(pr,hp,def1,speed,sp)
            n1 = 'To'+username+'〔'+wuhun+'〕:\n'
            n2 = '【'+type1+'】\n'
            n3 = '[攻击魂导器：敬请期待]\n'
            n4 = '[防御魂导器：敬请期待]\n'
            n5 = '·职业：敬请期待\n'
            n6 = '·斗铠：敬请期待\n'
            n7 = '·机甲：敬请期待\n'
            n8 = '·战舰：敬请期待\n'
            n9 = '·等级：'+str(lv)+'['+lvname+']\n'
            n10 = '·经验：'+str(exp)+'/'+str(up_exp)+'['+str(n_exp)+'%]\n'
            n11 = '·战力：'+str(ce)+'\n'
            n12 = '·精神力：'+str(sp)+'/'+upsp+'['+sp_name+']\n'
            n13 = '·生命：'+str(nowhp)+'/'+str(hp)+'\n'
            n14 = '·魂力：'+str(nowmp)+'/'+str(mp)+'\n'
            n15 = '·攻击：'+str(pr)+'\n'
            n16 = '·力量：'+str(power)+'\n'
            n17 = '·防御：'+str(def1)+'\n'
            n18 = '.暴击：'+str(ct)+'%\n'
            n19 = '.爆伤：'+str(ctp)+'\n'
            n20 = '·速度：'+str(speed)+'\n'
            n21 = '·闪避：'+str(dodge)+'%\n'
            n22 = '·体力值：'+str(con)+'\n'
            n23 = '<可用命令>\n'
            n24 = '背包\n'
            await info.send(message=(n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24))

    '''if r == 0:
        await info.send(message=('请按格式操作！\n<可用命令>\n状态@对方\n状态 对方QQ'))'''
    if r != 0:
        result2 =select_user(r)
        result3 =select_wuhun(r)
        if result2==0:
            await info.send(message=('对方还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越'))
        elif result3 == 1:
            await info.send(message=('对方还没有觉醒武魂！请先觉醒武魂\n<可用命令>\n武魂觉醒'))
        else:
            type = ['pow','dodge','ct','ctp','cdadd','pr','de','mp','speed','sp','des','category','exp','con','lv','wuhun','hp','nowhp','nowmp']
            power = get_userinfo(r,type[0])#力量
            dodge = get_userinfo(r,type[1])#闪避
            ct = get_userinfo(r,type[2])#暴击率
            ctp = get_userinfo(r,type[3])#暴击伤害
            pr = get_userinfo(r,type[5])#攻击
            def1 = get_userinfo(r,type[6])#防御
            mp = get_userinfo(r,type[7])#魂力值
            speed = get_userinfo(r,type[8])#速度
            sp = get_userinfo(r,type[9])#精神力
            type1 = get_userinfo(r,type[11])#武魂类型
            exp = get_userinfo(r,type[12])#当前经验
            con = get_userinfo(r,type[13])#体力值
            lv = get_userinfo(r,type[14])#等级
            wuhun = get_userinfo(r,type[15])#武魂
            my_wuhun = get_userinfo(user_qq,type[15])#自己的武魂
            hp = get_userinfo(r,type[16])#生命
            nowhp = get_userinfo(r,type[17])
            nowmp = get_userinfo(r,type[18])
            username = get_username(user_qq)
            atusername = get_username(r)
            up = 1
            hhnum = get_hhnum(r)
            lvname = get_lvname(hhnum)
            sp_name = get_spname(sp)
            up_exp = u_exp(lv,up)
            n_exp = p_exp(exp,up_exp)
            upsp = get_upsp(sp)
            ce = get_ce(pr,hp,def1,speed,sp)
            await info.send(message=('To'+username+'〔'+my_wuhun+'〕:\n以下是 '+atusername+'['+str(r)+']的信息\n【'+wuhun+'-'+type1+'】\n[攻击魂导器：敬请期待]\n[防御魂导器:敬请期待]\n·职业：敬请期待\n·斗铠：敬请期待\n·机甲：敬请期待\n·战舰：敬请期待\n·等级：'+str(lv)+'['+lvname+']\n·经验：'+str(exp)+'/'+str(up_exp)+'['+str(n_exp)+'%]\n·战力：'+str(ce)+'\n·精神力：'+str(sp)+'/'+upsp+'['+sp_name+']\n·生命：'+str(nowhp)+'/'+str(hp)+'\n·魂力：'+str(nowmp)+'/'+str(mp)+'\n·攻击：'+str(pr)+'\n·力量：'+str(power)+'\n·防御：'+str(def1)+'\n.暴击：'+str(ct)+'%\n.爆伤：'+str(ctp)+'\n·速度：'+str(speed)+'\n·闪避：'+str(dodge)+'%\n·体力值：'+str(con)+'\n<可用命令>\n背包\n'))
