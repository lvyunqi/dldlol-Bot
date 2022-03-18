import random
import pymysql
import json
import nonebot

#**************调取数据库连接信息模块**************#
driver = nonebot.get_driver()
MYSQL_HOST = driver.config.mysql_host
MYSQL_PORT = driver.config.mysql_port
MYSQL_USER = driver.config.mysql_user
MYSQL_PASSWORD = driver.config.mysql_password
MYSQL_DB = driver.config.mysql_db

dbinfo = {'host':MYSQL_HOST,'port':MYSQL_PORT,'user':MYSQL_USER,'passwd':MYSQL_PASSWORD,'db':MYSQL_DB}

#*************************************************#

def add_user(user_qq,username,usersex,start_map):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("insert into user (qq,name,sex,nowmap) values("+str(user_qq)+",'"+username+"','"+usersex+"','"+start_map+"')")
    conn.commit()
    cursor.close()
    conn.close()

def get_whdata(user_wh,type):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT "+type+" FROM wuhun WHERE name='"+user_wh+"'")
    p = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return p

def user_awaken(user_qq,user_name,user_sex,user_wuhun,lv,user_con,up_exp):
    type_list = ['pow','dodge','ct','ctp','cdadd','pr','de','mp','speed','sp','des','category']
    hp = random.randint(250,480)
    power = get_whdata(user_wuhun,type_list[0])+random.randint(1,5)*lv
    dodge = get_whdata(user_wuhun,type_list[1])
    ct = get_whdata(user_wuhun,type_list[2])
    ctp = get_whdata(user_wuhun,type_list[3])
    cdadd = get_whdata(user_wuhun,type_list[4])
    pr = get_whdata(user_wuhun,type_list[5])+random.randint(1,5)*lv
    de = get_whdata(user_wuhun,type_list[6])+random.randint(1,10)
    mp = get_whdata(user_wuhun,type_list[7])+random.randint(10,30)
    speed = get_whdata(user_wuhun,type_list[8])
    sp = get_whdata(user_wuhun,type_list[9])+random.randint(30,60)
    des = get_whdata(user_wuhun,type_list[10])
    type = get_whdata(user_wuhun,type_list[11])
    userData = {'userInfo':{'name':user_name,'sex':user_sex},'userData':{'武魂':user_wuhun,'武魂类型':type,'等级':lv,'力量':power,'闪避':dodge,'暴击率':ct,'暴击伤害':ctp,'技能加速':cdadd,'攻击':pr,'防御':de,'魂力值':mp,'当前魂力值':mp,'速度':speed,'精神力':sp,'武魂描述':des,'经验':up_exp,'体力':user_con,'血量':hp,'当前血量':hp}}
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("UPDATE user SET state_info = '"+json.dumps(userData,ensure_ascii=False)+"'")
    conn.commit()
    cursor.close()
    conn.close()