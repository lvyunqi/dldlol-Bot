import demjson
import pymysql
dbinfo = {
    'host':'114.132.234.220',
    'user':'dldlol_bot',
    'passwd':'dldlol',
    'db':'dldlol_bot',
}
data = {'狂化':'2021-5-6','禁锢':'2022-7-8'}
json = demjson.encode(data)

def user_data(user_qq,typ,data):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("UPDATE userdata SET "+typ+"='"+data+"' WHERE qq = '"+str(user_qq)+"'")
    conn.commit()
    cursor.close()
    conn.close()

def select_userdata(user_qq,typ):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT "+typ+" FROM userdata WHERE qq='"+str(user_qq)+"'")
    fetchall = cursor.fetchall()[0][0]
    conn.commit()
    cursor.close()
    conn.close()
    return fetchall
p = select_userdata(434658198,'status')
text = demjson.decode(json)


#user_data(434658198,'status',json)
print(text)
