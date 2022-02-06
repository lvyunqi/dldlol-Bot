from nonebot import require
import pymysql
from .function import *
scheduler = require("nonebot_plugin_apscheduler").scheduler

def cron_hpadd():
    list = cron_hp()
    for i in list:
        hp = get_userinfo(i,'hp')
        nowhp = get_userinfo(i,'nowhp')
        newhp = int(nowhp)+2
        if newhp>hp:
            newhp = hp
        w_info(i,'nowhp',newhp)

scheduler.add_job(cron_hpadd, "interval", seconds=1, id="hpadd",max_instances=5)

def cron_conadd():
    list = cron_con()
    for i in list:
        con = get_userinfo(i,'con')
        newcon = int(con)+1
        if newcon>300:
            newcon = 300
        w_info(i,'con',newcon)

scheduler.add_job(cron_conadd, "interval", seconds=144, id="conadd",max_instances=5)

def cron_mpadd():
    list = cron_mp()
    for i in list:
        mp = get_userinfo(i,'mp')
        nowmp = get_userinfo(i,'nowmp')
        newmp = int(nowmp)+2
        if newmp>mp:
            newmp = mp
        w_info(i,'nowmp',newmp)

scheduler.add_job(cron_mpadd, "interval", seconds=1, id="mpadd",max_instances=5)

def cron_lvup():
    qqlist = all_qqlist()
    for i in qqlist:
        hhnum = get_hhnum(i)
        user_lv = get_userinfo(i,'lv')#获取玩家LV
        user_exp = get_userinfo(i,'exp')#获取玩家EXP
        a = lvup(i,user_lv,user_exp,hhnum)
        if a == 0:
            pass
        else:
            pass

scheduler.add_job(cron_lvup, "interval", seconds=1, id="lvup",max_instances=5)

def cron_hjbat():
    idlist = select_hjbatid()
    for i in idlist:
        hjname = get_hjbatinfo(i,'hjname')
        hjcd = get_hjinfo(hjname,'hjcd')
        nowtime = get_time()
        startdate = get_hjbatinfo(i,'startdate')
        cdcha = get_datecha(nowtime,startdate)
        if cdcha>=hjcd:
            del_hjbat(i)
        else:
            pass

scheduler.add_job(cron_hjbat, "interval", seconds=1, id="hjbat")

def cron_hp():
    allqq = get_alluserqq()
    list = []
    for qq in allqq:
        nqq = qq[0]
        bat = get_userinfo(nqq,'battle')
        if bat == 0:
            hp = get_userinfo(nqq,'hp')
            nowhp = get_userinfo(nqq,'nowhp')
            if nowhp < hp:
                list.append(nqq)
            else:
                pass
        else:
            pass
    return list

def cron_con():
    allqq = get_alluserqq()
    list = []
    for qq in allqq:
        nqq = qq[0]
        con = get_userinfo(nqq,'con')
        if con < 300:
            list.append(nqq)
        else:
            pass
    return list

def cron_mp():
    allqq = get_alluserqq()
    list = []
    for qq in allqq:
        nqq = qq[0]
        bat = get_userinfo(nqq,'battle')
        if bat == 0:
            mp = get_userinfo(nqq,'mp')
            nowmp = get_userinfo(nqq,'nowmp')
            if nowmp < mp:
                list.append(nqq)
            else:
                pass
        else:
            pass
    return list