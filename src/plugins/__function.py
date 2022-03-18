import random
from typing import List, Union, Optional, Type, Any
from nonebot.adapters.onebot.v11 import Bot, Message
from nonebot.matcher import matchers, Matcher
import json
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

def is_number(s):
    """
    说明：
        检测 s 是否为数字
    参数：
        param s: 文本
    """
    try:
        float(s)
        return 1
    except ValueError:
        pass
 
    try:
        import unicodedata
        unicodedata.numeric(s)
        return 1
    except (TypeError, ValueError):
        pass
 
    return 0

def get_message_at(data):
    '''
    说明：
        获取at对象的QQ号
    参数：
        param data: json -->event.json()
    返回：
        return qq
    '''
    data = json.loads(data)
    re = data.get("raw_message")
    atqq = re[1:]
    a = atqq.find('=')
    qq = atqq[a+1:-1]
    return qq

def select_user(user_qq):
    '''
    说明：
        获取at对象的QQ号
    参数：
        param data: json -->event.json()
    返回:
        return 1 = 存在 | 0=不存在
    '''
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT qq FROM user")
    fetchall = cursor.fetchall()
    cursor.close()
    conn.close()
    if user_qq in str(fetchall):
        return 1
    else:
        return 0

def is_user_awaken(user_qq):
    '''
    说明：
        判断玩家是否觉醒武魂
    参数：
        param user_qq: any
    返回:
        return 1 = 没觉醒 | 0=觉醒
    '''
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT wuhun FROM user WHERE qq='"+str(user_qq)+"'")
    fetchall = cursor.fetchall()
    cursor.close()
    conn.close()
    if fetchall == None:
        return 1
    else:
        return 0

def event_plain_text(data,len_num):
    '''
    说明：
        判断消息参数是否附带跟随内容
    参数：
        param data: json | len_num: 当前调用指令长度（如：状态 --> 长度2 , len_num = 2）
    返回:
        return 0 = 无附带内容 | re = event原内容(带指令)
    '''
    data = json.loads(data)
    re = data.get("raw_message").strip()
    if len(re) == len_num:
        return 0
    if len(re) > len_num:
        return re

def select_alluserqq():
    '''
    说明：
        获取所有玩家QQ号
    返回:
        return list
    '''
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT qq FROM user")
    fetchall = cursor.fetchall()
    cursor.close()
    conn.close()
    list = []
    for qq in fetchall:
        nqq = qq[0]
        list.append(nqq)
    return list

def get_event_args(data,len_num):
    '''
    说明：
        获取当前命令后面跟随的数据
    参数：
        param data: str | len_num: 当前调用指令长度（如：状态 --> 长度2 , len_num = 2）
    返回:
        return result = 当前命令后面跟随的数据
    '''
    result = data[len_num:]
    return result

def random_wh():
    '''
    说明：
        随机获取一个武魂
    返回:
        return user_wh : str
    '''
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT name FROM wuhun")
    fetchall = cursor.fetchall()
    user_wh = random.choice(fetchall)[0]
    cursor.close()
    conn.close()
    return user_wh

def get_nowmap(user_qq):
    '''
    说明：
        获取玩家当前地图
    返回:
        return fetchall : str
    '''
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT nowmap FROM user WHERE qq='"+str(user_qq)+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def get_map(nowmap,maptype):
    '''
    说明：
        获取地图周围连接地图
    返回:
        return fetchall : str
    '''
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT "+maptype+" FROM map WHERE map='"+nowmap+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def get_mapwz(nowmap):
    maptype = ['t','d','l','r']
    chiness_map = {'t':'上','d':'下','l':'左','r':'右'}
    map_list = []
    map_dict = {}
    for i in maptype:
        map = get_map(nowmap,i)
        if map != None:
            map_dict[i] = map
    for key in map_dict:
        map_list.append(chiness_map[key]+':'+map_dict[key]+'\n')
    return map_list

def get_hscmlist(nowmap):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT hsname FROM hunshou WHERE map='"+nowmap+"'")
    fetchall = cursor.fetchall()
    cursor.close()
    conn.close()
    return fetchall