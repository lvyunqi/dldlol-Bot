from nonebot import on_command
from nonebot.typing import T_State
from nonebot.adapters.cqhttp import Bot, MessageEvent
from ..function import *
from ..battle import *

battlet = on_command('挑战魂兽')
@battlet.handle()
async def battle_handle(bot: Bot, event: MessageEvent, state: T_State):
    plain_text = str(event.get_message()).strip()  #首次发送命令时跟随的参数
    user_qq = str(event.user_id)
    username = get_username(user_qq)
    wh = 'wuhun'
    wuhun = get_userinfo(user_qq,wh)#武魂
    nowmap = get_nowmap(user_qq)
    result = select_user(user_qq)
    result1 = select_wuhun(user_qq)
    if result == 0:
        await battlet.send(message=('您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越'))
    elif result1 == 1:
        await battlet.send(message=('您还没有觉醒武魂！请先觉醒武魂\n<可用命令>\n武魂觉醒'))
    else:
        bat = get_battles(user_qq)
        if bat == 1:
            await battlet.send(message=('To'+username+'〔'+wuhun+'〕:\n您当前还在战斗中，请先结束战斗！\n<可用命令>\n逃跑'))
        else:
            str1 = plain_text
            hunshou = get_hslist()
            if str1 not in str(hunshou):
                await battlet.send(message=('To'+username+'〔'+wuhun+'〕:\n这只魂兽貌似不在野外哦！\n<可用命令>\n位置'))
            else:
                hscmmap = get_hscmmap(str1)
                if nowmap != str(hscmmap):
                    await battlet.send(message=('To'+username+'〔'+wuhun+'〕:\n这只魂兽好像不在这个地图哦，听说是出没在['+hscmmap+']，前去查看吧！\n<可用命令>\n位置'))
                else:
                    typ = 'con'
                    con = get_userinfo(user_qq,typ)
                    if con == 0:
                        await battlet.send(message=('To'+username+'〔'+wuhun+'〕:\n您的体力不足，无法挑战!\n<可用命令>\n位置'))
                    else:
                        ty = 'nowhp'
                        hp = get_userinfo(user_qq,ty)
                        if hp<=0:
                            await battlet.send(message=('To'+username+'〔'+wuhun+'〕:\n您已经死亡！\n<可用命令>\n复活'))
                        else:
                            typ0 = 'hp'
                            typ1 = 'batid'
                            newcon = j_con(con,1)
                            w_con(user_qq,newcon)
                            w_bat(user_qq,1)
                            ran_num1 = ran_num()
                            sp2 = 'name2'
                            sp3 = 'name3'
                            sp4 = 'name4'
                            sp5 = 'name5'
                            sp6 = 'name6'
                            sp7 = 'name7'
                            starttime = get_time()
                            hs_hp = get_hsinfo(str1,typ0)
                            hs_mp = get_hsinfo(str1,'mp')
                            w_info(user_qq,typ1,ran_num1)
                            w_batdata(ran_num1,str1,hs_hp,hs_mp,username,sp2,sp3,sp4,sp5,sp6,sp7,starttime,starttime,starttime,starttime)
                            rel = start_bat(username,wuhun,str1)
                            await battlet.send(message=rel)

hit = on_command('攻击')
@hit.handle()
async def hit_handle(bot: Bot, event: MessageEvent, state: T_State):
    user_qq = str(event.user_id)
    username = get_username(user_qq)
    wh = 'wuhun'
    wuhun = get_userinfo(user_qq,wh)#武魂
    result = select_user(user_qq)
    result1 = select_wuhun(user_qq)
    if result == 0:
        await hit.send(message=('您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越'))
    elif result1 == 1:
        await hit.send(message=('您还没有觉醒武魂！请先觉醒武魂\n<可用命令>\n武魂觉醒'))
    else:
        bat = get_battles(user_qq)
        if bat == 0:
            await hit.send(message=('To'+username+'〔'+wuhun+'〕:\n您当前不在战斗中，请先发起挑战！\n<可用命令>\n挑战魂兽 魂兽名'))
        else:
            typ3 = 'batid'
            typ4 = 'hsname'
            typ5 = 'nowhp'
            typ6 = 'bubat'
            bat_id = get_userinfo(user_qq,typ3)#获取玩家战斗id
            hsname = get_batinfo(bat_id,typ4)#获取战斗魂兽名
            user_hp = get_userinfo(user_qq,typ5)#获取当前玩家HP
            user_bubat = get_userinfo(user_qq,typ6)
            if user_hp<=0:
                await hit.send(message=('To'+username+'〔'+wuhun+'〕:\n您已经死亡！\n<可用命令>\n复活'))
            elif user_bubat == 1:
                typ2 = 'pr'
                typ3 = 'sp'
                typ4 = 'pow'
                typ5 = 'de'
                typ6 = 'lasttime'
                typ7 = 'hshp'
                typ8 = 'battle'
                typ9 = 'ct'
                typ10 = 'ctp'
                typ11 = 'info'
                typ12 = 'fjzt'
                typ13 = 'exp'
                typ14 = 'ztstartdete'
                user_exp = get_userinfo(user_qq,typ13)#获取玩家EXP
                hs_exp = get_hsinfo(hsname,typ13)#获取魂兽EXP
                hs_den = get_hsinfo(hsname,typ5)#获取魂兽防御
                hs_pow = get_hsinfo(hsname,typ4)#获取魂兽力量
                userde = get_userinfo(user_qq,typ5)#获取玩家防御
                hs_hp = get_batinfo(bat_id,typ7)#获取战斗中魂兽当前HP
                hs_ct = get_hsinfo(hsname,typ9)#获取魂兽暴击率
                hs_ctp = get_hsinfo(hsname,typ10)#获取魂兽暴击伤害
                user_zt = get_userinfo(user_qq,typ12)#获取玩家附加状态
                zt_info = get_zt(user_zt,typ11)#获取附加状态的info
                lastdate = get_batinfo(bat_id,typ6)#获取上一次攻击的时间
                lastztdate = get_userinfo(user_qq,typ14)#获取附加状态的时间
                nowdate = get_time()#获取当前时间
                timecha = get_timecha(nowdate,lastdate)#计算时间差（秒）
                zttimecha= get_datecha(nowdate,lastztdate)
                nowdate1 = get_time()
                user_dodge = 1
                w_batinfo(bat_id,typ6,nowdate1)
                e = bat_hspg(timecha,bat_id,hsname,hs_hp,hs_ct,hs_pow,hs_ctp,username,user_qq,user_hp,user_dodge,userde)
                n1 = 'To'+username+'〔'+wuhun+'〕:\n'
                n2 = username+"当前被附加状态["+user_zt+"]{"+zt_info+"}"+'，无法进行攻击！\n'
                n3= '距离状态解除还有：'+str(zttimecha)+'秒\n'
                n5 = '【战斗状况】\n'
                n6 = '在之前的'+str(timecha)+'秒中\n'
                n7 = e.rstrip('\n')
                all = n1+n2+n3+n5+n6+n7
                await hit.send(message=all)
            else:#先判断是否可攻击状态
                typ2 = 'pr'
                typ3 = 'sp'
                typ4 = 'pow'
                typ5 = 'de'
                typ6 = 'lasttime'
                typ7 = 'hshp'
                typ8 = 'battle'
                typ9 = 'ct'
                typ10 = 'ctp'
                typ11 = 'dodge'
                typ12 = 'speed'
                typ13 = 'exp'
                typ14 = 'lv'
                user_lv = get_userinfo(user_qq,typ14)#获取玩家LV
                user_exp = get_userinfo(user_qq,typ13)#获取玩家EXP
                hs_exp = get_hsinfo(hsname,typ13)#获取魂兽EXP
                user_pr = get_userinfo(user_qq,typ2)#获取玩家攻击
                user_sp = get_userinfo(user_qq,typ3)#获取玩家精神力
                user_pow = get_userinfo(user_qq,typ4)#获取玩家力量
                hs_den = get_hsinfo(hsname,typ5)#获取魂兽防御
                hs_pow = get_hsinfo(hsname,typ4)#获取魂兽力量
                user_de = get_userinfo(user_qq,typ5)#获取玩家防御
                hs_hp = get_batinfo(bat_id,typ7)#获取战斗中魂兽当前HP
                hs_ct = get_hsinfo(hsname,typ9)#获取魂兽暴击率
                hs_ctp = get_hsinfo(hsname,typ10)#获取魂兽暴击伤害
                user_ct = get_userinfo(user_qq,typ9)#获取玩家暴击率
                user_ctp = get_userinfo(user_qq,typ10)#获取玩家暴击伤害
                user_dodge = get_userinfo(user_qq,typ11)#获取玩家闪避
                user_speed = get_userinfo(user_qq,typ12)#获取玩家速度
                lastdate = get_batinfo(bat_id,typ6)#获取上一次攻击的时间
                nowdate = get_time()#获取当前时间
                timecha = get_timecha(nowdate,lastdate)#计算时间差（秒）
                userdodge = int(user_dodge+user_speed*0.8)
                userct = random_run(user_ct)
                if userct == 0:
                    user_atk = get_userhitpt(user_pow,user_pr,user_sp,hs_den)#计算玩家普通攻击无暴击伤害
                else:
                    user_atk = get_userhitct(user_pow,user_pr,user_sp,user_ctp,hs_den)#计算玩家普通攻击暴击伤害
                new_hshp = hs_hp-user_atk#伤害计算后魂兽HP
                if new_hshp <= 0:
                    new_exp = int(user_exp)+int(hs_exp)
                    hhnum = get_hhnum(user_qq)
                    a = lvup(user_qq,user_lv,new_exp,hhnum)
                    del_batdata(bat_id)#删除战斗数据
                    w_info(user_qq,typ8,0)#修改玩家战斗状态
                    if a[0] == 1:
                        a1 = 'To'+username+'〔'+wuhun+'〕:\n'
                        a2 = username+'拿起[拳头]攻击向对方，造成'+str(user_atk)+'点伤害\n'
                        a3 = '对方生命:'+str(new_hshp)+'\n\n'
                        a4 = '对方已倒下，魂环徐徐升起：敬请期待\n'
                        a5 = '['+username+']获得经验'+hs_exp+'[100%][需要魂环]\n'
                        a6 = '<可用命令>\n'
                        a7 = '魂环信息'
                        alla = a1+a2+a3+a4+a5+a6+a7
                        await hit.send(message=alla)
                    elif a[0] == 0:
                        a1 = 'To'+username+'〔'+wuhun+'〕:\n'
                        a2 = username+'拿起[拳头]攻击向对方，造成'+str(user_atk)+'点伤害\n'
                        a3 = '对方生命:'+str(new_hshp)+'\n\n'
                        a4 = '对方已倒下，魂环徐徐升起：敬请期待\n'
                        a5 = '['+username+']获得经验'+hs_exp+'[100%]\n'
                        a6 = '<可用命令>\n'
                        a7 = '魂环信息'
                        alla = a1+a2+a3+a4+a5+a6+a7
                        await hit.send(message=alla)
                    else:
                        a1 = 'To'+username+'〔'+wuhun+'〕:\n'
                        a2 = username+'拿起[拳头]攻击向对方，造成'+str(user_atk)+'点伤害\n'
                        a3 = '对方生命:'+str(new_hshp)+'\n\n'
                        a4 = '对方已倒下，魂环徐徐升起：敬请期待\n'
                        a5 = '['+username+']获得经验'+hs_exp+'[100%][Lv up!]\n'
                        a6 = '<可用命令>\n'
                        a7 = '魂环信息'
                        alla = a1+a2+a3+a4+a5+a6+a7
                        await hit.send(message=alla)
                else:
                    nowdate1 = get_time()
                    w_batinfo(bat_id,typ7,new_hshp)
                    w_batinfo(bat_id,typ6,nowdate1)
                    if userct == 0:
                        e = bat_hspg(timecha,bat_id,hsname,new_hshp,hs_ct,hs_pow,hs_ctp,username,user_qq,user_hp,userdodge,user_de)
                        n1 = 'To'+username+'〔'+wuhun+'〕:\n'
                        n2 = username+'拿起[拳头]攻击向对方，造成'+str(user_atk)+'点伤害\n'
                        n3 = '我方生命剩余'+str(user_hp)+'\n'
                        n4 = '对方生命剩余'+str(new_hshp)+'\n\n'
                        n5 = '【战斗状况】\n'
                        n6 = '在之前的'+str(timecha)+'秒中\n'
                        n7 = e.rstrip('\n')
                        all = n1+n2+n3+n4+n5+n6+n7
                        await hit.send(message=all)
                    else:
                        e = bat_hspg(timecha,bat_id,hsname,new_hshp,hs_ct,hs_pow,hs_ctp,username,user_qq,user_hp,userdodge,user_de)
                        n1 = 'To'+username+'〔'+wuhun+'〕:\n'
                        n2 = username+'拿起[拳头]攻击向对方，造成'+str(user_atk)+'点[暴击]伤害\n'
                        n3 = '我方生命剩余'+str(user_hp)+'\n'
                        n4 = '对方生命剩余'+str(new_hshp)+'\n\n'
                        n5 = '【战斗状况】\n'
                        n6 = '在之前的'+str(timecha)+'秒中\n'
                        n7 = e.rstrip('\n')
                        all = n1+n2+n3+n4+n5+n6+n7
                        await hit.send(message=all)
                        


def bat_hspg(timecha,bat_id,hsname,hs_hp,hs_ct,hs_pow,hs_ctp,username,user_qq,user_hp,user_dodge,user_de):
    list = []
    list2 = []
    x= int(timecha/2)
    typ = 'nowhp'
    typ1 = 'battle'
    for i in range(x):
        list.append(random_run(30))

    for item in list:
        if hs_hp <= 0:
            pass
        elif item == 0:
            user_hp = get_userinfo(user_qq,typ)
            n1 = random_run(hs_ct)
            hs_hshjsf = get_hsinfo(hsname,'hjsf')#获取魂兽是否有魂技
            if int(hs_hshjsf) == 1:
                f1 = random_run(85)
                if f1 == 0:
                    if n1 == 0:
                        s1 = random_run(user_dodge)
                        if s1 == 1:
                            n = '★['+hsname+']对 '+username+' 进行猛烈攻击，却被对方巧妙的避开了！'
                            list2.append(n)
                        else:
                            atk = get_hshitpt(hs_pow,user_de)
                            newuser_hp = user_hp-atk
                            if newuser_hp <= 0:
                                del_batdata(bat_id)#删除战斗数据
                                w_info(user_qq,typ1,0)#修改玩家战斗状态
                                w_info(user_qq,typ,0)
                                n = '★['+hsname+']对 '+username+' 造成'+str(atk)+'点伤害，'+username+' 已阵亡！'
                            else:
                                w_info(user_qq,typ,newuser_hp)
                                n = '★['+hsname+']对 '+username+'进行猛烈攻击，造成'+str(atk)+'点伤害！\n玩家生命：'+str(newuser_hp)+''
                            list2.append(n)
                    else:
                        s1 = random_run(user_dodge)
                        if s1 == 1:
                            n = '★['+hsname+']对 '+username+' 进行猛烈攻击，却被对方巧妙的避开了！'
                            list2.append(n)
                        else:
                            atk = get_hshitct(hs_pow,user_de,hs_ctp)
                            newuser_hp = user_hp-atk
                            if newuser_hp <= 0:
                                del_batdata(bat_id)#删除战斗数据
                                w_info(user_qq,typ1,0)#修改玩家战斗状态
                                w_info(user_qq,typ,0)
                                n = '★['+hsname+']对 '+username+' 造成'+str(atk)+'点[暴击]伤害，'+username+' 已阵亡！'
                            else:
                                w_info(user_qq,typ,newuser_hp)
                                n = '★['+hsname+']对 '+username+'进行猛烈攻击，造成'+str(atk)+'点[暴击]伤害！\n玩家生命：'+str(newuser_hp)+''
                            list2.append(n)
                else:#释放魂技
                    hjbatlist = select_hjbat(bat_id,'1')
                    hshjlist = get_hshjlist(hsname)
                    hshj2 = get_hshj(hshjlist)#获得字典
                    hshj = hshj2
                    for i in hjbatlist:
                        del hshj[i]
                    if not bool(hshj):
                        n1 = random_run(hs_ct)
                        if n1 == 0:
                            s1 = random_run(user_dodge)
                            if s1 == 1:
                                n = '★['+hsname+']对 '+username+' 进行猛烈攻击，却被对方巧妙的避开了！'
                                list2.append(n)
                            else:
                                atk = get_hshitpt(hs_pow,user_de)
                                newuser_hp = user_hp-atk
                                if newuser_hp <= 0:
                                    del_batdata(bat_id)#删除战斗数据
                                    w_info(user_qq,typ1,0)#修改玩家战斗状态
                                    w_info(user_qq,typ,0)
                                    n = '★['+hsname+']对 '+username+' 造成'+str(atk)+'点伤害，'+username+' 已阵亡！'
                                else:
                                    w_info(user_qq,typ,newuser_hp)
                                    n = '★['+hsname+']对 '+username+'进行猛烈攻击，造成'+str(atk)+'点伤害！\n玩家生命：'+str(newuser_hp)+''
                                list2.append(n)
                        else:
                            s1 = random_run(user_dodge)
                            if s1 == 1:
                                n = '★['+hsname+']对 '+username+' 进行猛烈攻击，却被对方巧妙的避开了！'
                                list2.append(n)
                            else:
                                atk = get_hshitct(hs_pow,user_de,hs_ctp)
                                newuser_hp = user_hp-atk
                                if newuser_hp <= 0:
                                    del_batdata(bat_id)#删除战斗数据
                                    w_info(user_qq,typ1,0)#修改玩家战斗状态
                                    w_info(user_qq,typ,0)
                                    n = '★['+hsname+']对 '+username+' 造成'+str(atk)+'点[暴击]伤害，'+username+' 已阵亡！'
                                else:
                                    w_info(user_qq,typ,newuser_hp)
                                    n = '★['+hsname+']对 '+username+'进行猛烈攻击，造成'+str(atk)+'点[暴击]伤害！\n玩家生命：'+str(newuser_hp)+''
                                list2.append(n)
                    else:
                        allhjgl2 = []
                        allhjname2 = []
                        a =len(hshj)
                        for v in hshj:
                            allhjgl2.append(hshj[v])
                        for ke in hshj.keys():
                            allhjname2.append(ke)
                        p = random_run2(allhjgl2)
                        hjname = allhjname2[p]#即将释放的魂技名
                        hjmp = get_hjinfo(hjname,'hjmp')
                        hsmp = get_batinfo(bat_id,'hsmp')
                        if int(hsmp)>=int(hjmp):
                            d = get_hjinfo(hjname,'around')
                            if int(d) == 0:#判断释放范围
                                u = get_hjinfo(hjname,'hjsffujia')
                                if int(u) == 0:#判断是否有附加状态   
                                    hjhr =get_hjinfo(hjname,'hjhr')
                                    p1 = random_run(hjhr)
                                    newhsmp = int(hsmp)-int(hjmp)
                                    if p1 == 1:
                                        hjpr = get_hjinfo(hjname,'hjpr')
                                        atk = get_hshitpt(hs_pow,user_de)
                                        hjatk = int(int(atk)*int(hjpr)*0.01)
                                        newuser_hp = user_hp-hjatk
                                        if newuser_hp <= 0:
                                            del_batdata(bat_id)#删除战斗数据
                                            w_info(user_qq,typ1,0)#修改玩家战斗状态
                                            w_info(user_qq,typ,0)
                                            n = '★['+hsname+']对 '+username+' 发动了['+hjname+"]造成"+str(hjatk)+'点伤害，'+username+' 已阵亡！'
                                        else:
                                            w_info(user_qq,typ,newuser_hp)
                                            w_batinfo(bat_id,'hsmp',newhsmp)
                                            nowdate = get_time()
                                            add_hjbat(bat_id,0,1,hjname,nowdate)
                                            n = '★['+hsname+']对 '+username+'发动了['+hjname+"]造成"+str(hjatk)+'点伤害！\n玩家生命：'+str(newuser_hp)+''
                                        list2.append(n)
                                    else:
                                        w_batinfo(bat_id,'hsmp',newhsmp)
                                        nowdate = get_time()
                                        add_hjbat(bat_id,0,1,hjname,nowdate)
                                        n = '★['+hsname+']对 '+username+'发动了['+hjname+']，却没有命中！'
                                        list2.append(n)
                                else:
                                    d1 = 1#暂未开放
                            else:
                                d2 = 2
                        else:
                            atk = get_hshitpt(hs_pow,user_de)
                            newuser_hp = user_hp-atk
                            if newuser_hp <= 0:
                                del_batdata(bat_id)#删除战斗数据
                                w_info(user_qq,typ1,0)#修改玩家战斗状态
                                w_info(user_qq,typ,0)
                                n = '★['+hsname+"]{魂力不足}对 "+username+' 造成'+str(atk)+'点伤害，'+username+' 已阵亡！'
                            else:
                                w_info(user_qq,typ,newuser_hp)
                                n = '★['+hsname+"]{魂力不足}对 "+username+'进行猛烈攻击，造成'+str(atk)+'点伤害！\n玩家生命：'+str(newuser_hp)+''
                            list2.append(n)
            else:
                if n1 == 0:
                    s1 = random_run(user_dodge)
                    if s1 == 1:
                        n = '★['+hsname+']对 '+username+' 进行猛烈攻击，却被对方巧妙的避开了！'
                        list2.append(n)
                    else:
                        atk = get_hshitpt(hs_pow,user_de)
                        newuser_hp = user_hp-atk
                        if newuser_hp <= 0:
                            del_batdata(bat_id)#删除战斗数据
                            w_info(user_qq,typ1,0)#修改玩家战斗状态
                            w_info(user_qq,typ,0)
                            n = '★['+hsname+']对 '+username+' 造成'+str(atk)+'点伤害，'+username+' 已阵亡！'
                        else:
                            w_info(user_qq,typ,newuser_hp)
                            n = '★['+hsname+']对 '+username+'进行猛烈攻击，造成'+str(atk)+'点伤害！\n玩家生命：'+str(newuser_hp)+''
                        list2.append(n)
                else:
                    s1 = random_run(user_dodge)
                    if s1 == 1:
                        n = '★['+hsname+']对 '+username+' 进行猛烈攻击，却被对方巧妙的避开了！'
                        list2.append(n)
                    else:
                        atk = get_hshitct(hs_pow,user_de,hs_ctp)
                        newuser_hp = user_hp-atk
                        if newuser_hp <= 0:
                            del_batdata(bat_id)#删除战斗数据
                            w_info(user_qq,typ1,0)#修改玩家战斗状态
                            w_info(user_qq,typ,0)
                            n = '★['+hsname+']对 '+username+' 造成'+str(atk)+'点[暴击]伤害，'+username+' 已阵亡！'
                        else:
                            w_info(user_qq,typ,newuser_hp)
                            n = '★['+hsname+']对 '+username+'进行猛烈攻击，造成'+str(atk)+'点[暴击]伤害！\n玩家生命：'+str(newuser_hp)+''
                        list2.append(n)  
        else:
            n = '★['+hsname+']虎视眈眈的看着你，并没有攻击你！'
            list2.append(n)
    e = ''.join('%s\n' %id for id in list2)
    return e

sfhj = on_command('魂技')
@sfhj.handle()
async def sfhj_handle(bot: Bot, event: MessageEvent, state: T_State):
    plain_text = str(event.get_message()).strip()  #首次发送命令时跟随的参数
    user_qq = str(event.user_id)
    username = get_username(user_qq)
    wh = 'wuhun'
    wuhun = get_userinfo(user_qq,wh)#武魂
    result = select_user(user_qq)
    result1 = select_wuhun(user_qq)
    if result == 0:
        await sfhj.send(message=('您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越'))
    elif result1 == 1:
        await sfhj.send(message=('您还没有觉醒武魂！请先觉醒武魂\n<可用命令>\n武魂觉醒'))
    else:
        str1 = int(plain_text)
        around = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18]
        if str1 not in around:
            await sfhj.send(message=('您是不是做梦梦到的魂技！请发送正确指令\n<可用命令>\n魂技 1~18'))
        else:   
            bat = get_battles(user_qq)
            if bat == 0:
                await sfhj.send(message=('To'+username+'〔'+wuhun+'〕:\n您当前不在战斗中，请先发起挑战！\n<可用命令>\n挑战魂兽 魂兽名'))
            else:
                hhid = 'hh'+str(str1)
                hh = get_hunhuan(user_qq,hhid)
                resu = get_hhyear(hh,0)
                hhnum = get_hhnum(user_qq)
                if hh != 'None':
                    typ3 = 'batid'
                    typ4 = 'hsname'
                    typ5 = 'nowhp'
                    typ6 = 'bubat'
                    bat_id = get_userinfo(user_qq,typ3)#获取玩家战斗id
                    hsname = get_batinfo(bat_id,typ4)#获取战斗魂兽名
                    user_hp = get_userinfo(user_qq,typ5)#获取当前玩家HP
                    user_bubat = get_userinfo(user_qq,typ6)
                    if user_hp<=0:
                        await sfhj.send(message=('To'+username+'〔'+wuhun+'〕:\n您已经死亡！\n<可用命令>\n复活'))
                    elif user_bubat == 1:
                        typ2 = 'pr'
                        typ3 = 'sp'
                        typ4 = 'pow'
                        typ5 = 'de'
                        typ6 = 'lasttime'
                        typ7 = 'hshp'
                        typ8 = 'battle'
                        typ9 = 'ct'
                        typ10 = 'ctp'
                        typ11 = 'info'
                        typ12 = 'fjzt'
                        typ13 = 'exp'
                        typ14 = 'ztstartdete'
                        user_exp = get_userinfo(user_qq,typ13)#获取玩家EXP
                        hs_exp = get_hsinfo(hsname,typ13)#获取魂兽EXP
                        hs_den = get_hsinfo(hsname,typ5)#获取魂兽防御
                        hs_pow = get_hsinfo(hsname,typ4)#获取魂兽力量
                        userde = get_userinfo(user_qq,typ5)#获取玩家防御
                        hs_hp = get_batinfo(bat_id,typ7)#获取战斗中魂兽当前HP
                        hs_ct = get_hsinfo(hsname,typ9)#获取魂兽暴击率
                        hs_ctp = get_hsinfo(hsname,typ10)#获取魂兽暴击伤害
                        user_zt = get_userinfo(user_qq,typ12)#获取玩家附加状态
                        zt_info = get_zt(user_zt,typ11)#获取附加状态的info
                        lastdate = get_batinfo(bat_id,typ6)#获取上一次攻击的时间
                        lastztdate = get_userinfo(user_qq,typ14)#获取附加状态的时间
                        nowdate = get_time()#获取当前时间
                        timecha = get_timecha(nowdate,lastdate)#计算时间差（秒）
                        zttimecha= get_timecha(nowdate,lastztdate)
                        nowdate1 = get_time()
                        w_batinfo(bat_id,typ6,nowdate1)
                        user_dodge = 1
                        e = bat_hspg(timecha,bat_id,hsname,hs_hp,hs_ct,hs_pow,hs_ctp,username,user_qq,user_hp,user_dodge,userde)
                        n1 = 'To'+username+'〔'+wuhun+'〕:\n'
                        n2 = username+"当前被附加状态["+user_zt+"]{"+zt_info+"}"+'，无法释放魂技！\n'
                        n3= '距离状态解除还有：'+str(zttimecha)+'秒\n'
                        n5 = '【战斗状况】\n'
                        n6 = '在之前的'+str(timecha)+'秒中\n'
                        n7 = e.rstrip('\n')
                        all = n1+n2+n3+n5+n6+n7
                        await hit.send(message=all)
                    else:
                        typ2 = 'pr'
                        typ3 = 'sp'
                        typ4 = 'pow'
                        typ5 = 'de'
                        typ6 = 'lasttime'
                        typ7 = 'hshp'
                        typ8 = 'battle'
                        typ9 = 'ct'
                        typ10 = 'ctp'
                        typ11 = 'dodge'
                        typ12 = 'speed'
                        typ13 = 'exp'
                        typ14 = 'lv'
                        user_lv = get_userinfo(user_qq,typ14)#获取玩家LV
                        user_exp = get_userinfo(user_qq,typ13)#获取玩家EXP
                        hs_exp = get_hsinfo(hsname,typ13)#获取魂兽EXP
                        user_pr = get_userinfo(user_qq,typ2)#获取玩家攻击
                        user_sp = get_userinfo(user_qq,typ3)#获取玩家精神力
                        user_pow = get_userinfo(user_qq,typ4)#获取玩家力量
                        hs_den = get_hsinfo(hsname,typ5)#获取魂兽防御
                        hs_pow = get_hsinfo(hsname,typ4)#获取魂兽力量
                        user_de = get_userinfo(user_qq,typ5)#获取玩家防御
                        hs_hp = get_batinfo(bat_id,typ7)#获取战斗中魂兽当前HP
                        hs_ct = get_hsinfo(hsname,typ9)#获取魂兽暴击率
                        hs_ctp = get_hsinfo(hsname,typ10)#获取魂兽暴击伤害
                        user_mp = get_userinfo(user_qq,'nowmp')#获取玩家MP
                        user_ctp = get_userinfo(user_qq,typ10)#获取玩家暴击伤害
                        user_dodge = get_userinfo(user_qq,typ11)#获取玩家闪避
                        user_speed = get_userinfo(user_qq,typ12)#获取玩家速度
                        lastdate = get_batinfo(bat_id,typ6)#获取上一次攻击的时间
                        nowdate = get_time()#获取当前时间
                        timecha = get_timecha(nowdate,lastdate)#计算时间差（秒）
                        userdodge = int(user_dodge+user_speed*0.8)
                        hjname = resu
                        hjmp = get_hjinfo(hjname,'hjmp')
                        if int(user_mp) < int(hjmp):
                            await sfhj.send(message=('To'+username+'〔'+wuhun+'〕:\n您的魂力不足，无法释放['+hjname+']！\n<可用命令>\n攻击'))
                        else:
                            q2 = get_hjinfo(hjname,'around')
                            if q2 == 0:
                                u = get_hjinfo(hjname,'hjsffujia')
                                if u == 0:
                                    hjhr =get_hjinfo(hjname,'hjhr')
                                    p1 = random_run(hjhr)
                                    hjinfo = get_hjinfo(hjname,'info')
                                    hjpr = get_hjinfo(hjname,'hjpr')
                                    atk = get_userhitpt(user_pow,user_pr,user_sp,hs_den)
                                    hjatk = int(int(atk)*int(hjpr)*0.01)
                                    newusermp = int(user_mp)-int(hjmp)
                                    if p1 == 1:
                                        newhshp = hs_hp-hjatk
                                        if newhshp <= 0:
                                            new_exp = int(user_exp)+int(hs_exp)
                                            hhnum = get_hhnum(user_qq)
                                            a = lvup(user_qq,user_lv,new_exp,hhnum)
                                            del_batdata(bat_id)#删除战斗数据
                                            w_info(user_qq,typ8,0)#修改玩家战斗状态
                                            w_info(user_qq,'nowmp',newusermp)
                                            if a[0] == 1:
                                                a1 = 'To'+username+'〔'+wuhun+'〕:\n'
                                                a2 = username+'发动了['+hjname+"]{"+hjinfo+"}"+':\n'+hsname+'，受到['+str(hjatk)+']点伤害\n'
                                                a3 = '对方生命:'+str(newhshp)+'\n\n'
                                                a4 = '对方已倒下，魂环徐徐升起：敬请期待\n'
                                                a5 = '['+username+']获得经验'+hs_exp+'[100%][需要魂环]\n'
                                                a6 = '<可用命令>\n'
                                                a7 = '魂环信息'
                                                alla = a1+a2+a3+a4+a5+a6+a7
                                                await sfhj.send(message=alla)
                                            elif a[0] == 0:
                                                a1 = 'To'+username+'〔'+wuhun+'〕:\n'
                                                a2 = username+'发动了['+hjname+"]{"+hjinfo+"}"+':\n'+hsname+'，受到['+str(hjatk)+']点伤害\n'
                                                a3 = '对方生命:'+str(newhshp)+'\n\n'
                                                a4 = '对方已倒下，魂环徐徐升起：敬请期待\n'
                                                a5 = '['+username+']获得经验'+hs_exp+'[100%]\n'
                                                a6 = '<可用命令>\n'
                                                a7 = '魂环信息'
                                                alla = a1+a2+a3+a4+a5+a6+a7
                                                await sfhj.send(message=alla)
                                            else:
                                                a1 = 'To'+username+'〔'+wuhun+'〕:\n'
                                                a2 = username+'发动了['+hjname+"]{"+hjinfo+"}"+':\n'+hsname+'，受到['+str(hjatk)+']点伤害\n'
                                                a3 = '对方生命:'+str(newhshp)+'\n\n'
                                                a4 = '对方已倒下，魂环徐徐升起：敬请期待\n'
                                                a5 = '['+username+']获得经验'+hs_exp+'[100%][Lv up!]\n'
                                                a6 = '<可用命令>\n'
                                                a7 = '魂环信息'
                                                alla = a1+a2+a3+a4+a5+a6+a7
                                                await sfhj.send(message=alla)
                                        else:
                                            nowdate1 = get_time()
                                            w_batinfo(bat_id,typ7,newhshp)
                                            w_batinfo(bat_id,typ6,nowdate1)
                                            w_info(user_qq,'nowmp',newusermp)
                                            e = bat_hspg(timecha,bat_id,hsname,newhshp,hs_ct,hs_pow,hs_ctp,username,user_qq,user_hp,userdodge,user_de)
                                            n1 = 'To'+username+'〔'+wuhun+'〕:\n'
                                            n2 = username+'发动了['+hjname+"]{"+hjinfo+"}"+':\n'+hsname+'，受到['+str(hjatk)+']点伤害\n'
                                            n3 = '我方生命剩余'+str(user_hp)+'\n'
                                            n4 = '对方生命剩余'+str(newhshp)+'\n\n'
                                            n5 = '【战斗状况】\n'
                                            n6 = '在之前的'+str(timecha)+'秒中\n'
                                            n7 = e.rstrip('\n')
                                            all = n1+n2+n3+n4+n5+n6+n7
                                            await sfhj.send(message=all)
                                    else:
                                        e = bat_hspg(timecha,bat_id,hsname,hs_hp,hs_ct,hs_pow,hs_ctp,username,user_qq,user_hp,userdodge,user_de)
                                        nowdate1 = get_time()
                                        w_batinfo(bat_id,typ6,nowdate1)
                                        w_info(user_qq,'nowmp',newusermp)
                                        a1 = 'To'+username+'〔'+wuhun+'〕:\n'
                                        a2 = username+'发动了['+hjname+']可惜没有命中！\n'
                                        a3 = '我方生命剩余'+str(user_hp)+'\n'
                                        a4 = '对方生命剩余'+str(hs_hp)+'\n\n'
                                        a5 = '【战斗状况】\n'
                                        a6 = '在之前的'+str(timecha)+'秒中\n'
                                        a7 = e.rstrip('\n')
                                        alla = a1+a2+a3+a4+a5+a6+a7
                                        await sfhj.send(message=alla)
                                else:
                                    x = 4
                            else:
                                x = 2
                else:
                    await sfhj.send(message=('To'+username+'〔'+wuhun+'〕:\n您的魂环数量不足！\n<可用命令>\n攻击'))