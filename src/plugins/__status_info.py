import pymysql
import nonebot
import json

#**************调取数据库连接信息模块**************#
driver = nonebot.get_driver()
MYSQL_HOST = driver.config.mysql_host
MYSQL_PORT = driver.config.mysql_port
MYSQL_USER = driver.config.mysql_user
MYSQL_PASSWORD = driver.config.mysql_password
MYSQL_DB = driver.config.mysql_db

dbinfo = {'host':MYSQL_HOST,'port':MYSQL_PORT,'user':MYSQL_USER,'passwd':MYSQL_PASSWORD,'db':MYSQL_DB}

#*************************************************#

def get_userinfo(user_qq,type):
    '''获取user表单单个数据|*user_qq = QQ号|*type = 表单名'''
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT "+type+" FROM user WHERE qq='"+str(user_qq)+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def status_info(user_qq):
    '''获取玩家属性'''
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT lv,pow,dodge,ct,ctp,pr,de,mp,nowmp,hp,nowhp,speed,sp,exp,con,wuhun,category,name FROM user WHERE qq='"+str(user_qq)+"'")
    fetchall = cursor.fetchall()[0]
    cursor.close()
    conn.close()
    data = {'lv':fetchall[0],'pow':fetchall[1],'dodge':fetchall[2],'ct':fetchall[3],'ctp':fetchall[4],'pr':fetchall[5],'de':fetchall[6],'mp':fetchall[7],'nowmp':fetchall[8],'hp':fetchall[9],'nowhp':fetchall[10],'speed':fetchall[11],'sp':fetchall[12],'exp':fetchall[13],'con':fetchall[14],'wuhun':fetchall[15],'category':fetchall[16],'name':fetchall[17]}
    return data

def select_user(user_qq):
    '''查找玩家是否存在|*1 = 在|*0 = 不在'''
    list = []
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT qq FROM user")
    fetchall = cursor.fetchall()
    cursor.close()
    conn.close()
    for qq in fetchall:
        nqq = qq[0]
        list.append(nqq)
    if user_qq in list:
        return 1
    else:
        return 0

def user_exist(user_qq):
    '''判断玩家是否注册和是否武魂觉醒|*0 = 正常玩家'''
    a = select_user(user_qq)
    if a == 0:
        Message = '您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越'
        return Message
    elif a == 1:
        b = get_userinfo(user_qq,'state_info')
        if b == None:
            Message = '您还没有觉醒武魂！请先觉醒武魂\n<可用命令>\n武魂觉醒'
            return Message
        else:
            return 0

def hunhuan_num(user_qq):
    '''获取魂环数量'''
    hunhuan_data = get_userinfo(user_qq,'skill')
    if hunhuan_data == None:
        return 0
    else:
        hhnum = len(hunhuan_data)
        return hhnum

def get_lvname(hhnum):
    switcher = {
        0: "魂士",
        1: "一环魂师",
        2: "两环大魂师",
        3: "三环魂尊",
        4: "四环魂总",
        5: "五环魂王",
        6: "六环魂帝",
        7: "七环魂圣",
        8: "八环魂斗罗",
        9: "九环封号斗罗",
        10: "十环准神",
    }
     
    return switcher.get(hhnum, 'None')

def get_upsp(sp):
    if 0<=sp<=99:
        n1 = '100'
        return n1
    elif 100<=sp<=499:
        n1 = '500'
        return n1
    elif 500<=sp<=4999:
        n1 = '5000'
        return n1
    elif 5000<=sp<=19999:
        n1 = '20000'
        return n1
    elif 20000<=sp<=49999:
        n1 = '50000'
        return n1
    else:
        n1 = '∞'
        return n1

def get_spname(sp):
    if 0<=sp<=99:
        n1 = '灵元境'
        return n1
    elif 100<=sp<=499:
        n1 = '灵通境'
        return n1
    elif 500<=sp<=4999:
        n1 = '灵海境'
        return n1
    elif 5000<=sp<=19999:
        n1 = '灵渊境'
        return n1
    elif 20000<=sp<=49999:
        n1 = '灵域境'
        return n1
    else:
        n1 = '神元境'
        return n1

def is_user(user_qq):
    '''
    说明：
        判断玩家是否存在
    参数：
        param user_qq
    返回:
        return result = 0 (不存在)|*1 (存在)
    '''
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT qq FROM user")
    fetchall = cursor.fetchall()
    cursor.close()
    conn.close()
    if user_qq in fetchall:
        return 1
    else:
        return 0

def get_whdata(user_wh,type):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT "+type+" FROM wuhun WHERE name='"+user_wh+"'")
    p = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return p