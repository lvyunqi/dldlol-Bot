import pymysql
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

def get_command(type):
    '''获取全局配置单个数据|*type = 表单名'''
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT "+type+" FROM command WHERE id=1")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall