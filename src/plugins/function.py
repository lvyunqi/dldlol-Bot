import pymysql
import random
import re
import time
from datetime import datetime, date

dbinfo = {
    'host':'localhost',
    'user':'dldlol_bot',
    'passwd':'dldlol',
    'db':'dldlol_bot',
}

def get_time():
    date = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
    return date

def get_timecha(nowtime,lasttime):
    lasttime = datetime.strptime(str(lasttime), "%Y-%m-%d %H:%M:%S")
    nowtime = datetime.strptime(str(nowtime), "%Y-%m-%d %H:%M:%S")
    seconds = (nowtime-lasttime).seconds
    if seconds > 20:
        s1 = 20
        return s1
    else:
        return seconds

def get_datecha(nowtime,lasttime):
    lasttime = datetime.strptime(str(lasttime), "%Y-%m-%d %H:%M:%S")
    nowtime = datetime.strptime(str(nowtime), "%Y-%m-%d %H:%M:%S")
    seconds = (nowtime-lasttime).seconds
    return seconds

def ran_num():
    random_num = ''.join([str(random.randint(0, 999)) for _ in range(2)])
    return random_num

def j_con(con,num):
    newcon = int(con)-num
    return newcon

def add_con(con,num):
    newcon = int(con)+num
    return newcon

def u_exp(lv,up):
    exp = int((lv*1500)/8*up)
    return str(exp)

def p_exp(exp,up_exp):
    if exp == 0:
        z = '0.00'
        return z
    else:
        p = float(int(exp)/int(up_exp)*100)
        new_p = round(p,2)
        return new_p

def add_pow(lv,power):
    a1 = int(1.41*int(lv)+int(int(power)*1.02))
    a2 = int(1.11*int(lv))
    pow = int((random.randint(a1,a2+a1)+random.randint(a1,a2+a1))/2)
    return pow

def add_pr(lv,pr):
    a1 = int(1.21*int(lv)+int(int(pr)*1.01))
    a2 = int(1.11*int(lv))
    newpr = int((random.randint(a1,a2+a1)+random.randint(a1,a2+a1))/2)
    return newpr

def add_mp(lv,mp):
    a1 = int(1.41*int(lv)+int(int(mp)*1.01))
    a2 = int(1.11*int(lv))
    newmp = int((random.randint(a1,a2+a1)+random.randint(a1,a2+a1))/2)
    return newmp

def add_hp(lv,hp):
    a1 = int(1.41*int(lv)+int(int(hp)*1.01))
    a2 = int(1.11*int(lv))
    newhp = int((random.randint(a1,a2+a1)+random.randint(a1,a2+a1))/2)
    return newhp

def add_de(lv,de):
    a1 = int(1.31*int(lv)+int(int(de)*1.02))
    a2 = int(1.11*int(lv))
    newde = int((random.randint(a1,a2+a1)+random.randint(a1,a2+a1))/2)
    return newde

def r_wh():
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT name FROM wuhun")
    fetchall = cursor.fetchall()
    user_wh = random.choice(fetchall)[0]
    cursor.close()
    conn.close()
    return user_wh

def get_whdata(user_wh,type):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT "+type+" FROM wuhun WHERE name='"+user_wh+"'")
    p = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return p

def r_lv():
    lv=random.randint(1,10)
    return lv
    
def w_wh(user_qq,power,dodge,ct,ctp,cdadd,pr,def1,mp,nowmp,speed,sp,des,exp,type1,user_wh,lv,hp,con,nowhp):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("UPDATE user SET wuhun = '"+str(user_wh)+"',category = '"+str(type1)+"',lv = '"+str(lv)+"',pow = '"+str(power)+"',dodge = '"+str(dodge)+"',ct = '"+str(ct)+"',ctp = '"+str(ctp)+"',cdadd = '"+str(cdadd)+"',pr = '"+str(pr)+"',de = '"+str(def1)+"',mp = '"+str(mp)+"',nowmp = '"+str(nowmp)+"',speed = '"+str(speed)+"',sp = '"+str(sp)+"',des = '"+str(des)+"',exp = '"+str(exp)+"',con = '"+str(con)+"',hp = '"+str(hp)+"',nowhp = '"+str(nowhp)+"' WHERE qq = '"+str(user_qq)+"'")
    cursor.execute("insert into hunhuan (qq,hhnum) values("+str(user_qq)+",0)")
    conn.commit()
    cursor.close()
    conn.close()
    return 0

def user_add(user_qq,username,usersex):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("insert into user (qq,name,sex,nowmap) values("+str(user_qq)+",'"+username+"','"+usersex+"','新手村')")
    conn.commit()
    cursor.close()
    conn.close()
    return 0

def select_wuhun(user_qq):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT wuhun FROM user WHERE qq='"+str(user_qq)+"'")
    fetchall = cursor.fetchall()
    cursor.close()
    conn.close()
    none = 'None'
    if none in str(fetchall):
        return 1
    else:
        return 0

def select_user(user_qq):
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

    '''file = open('/home/dldlol/src/config/user.txt','a+')
    with file as f:
        f.seek(0)
        lines = str(f.readlines())
        file.close()
        if user_qq in lines:
            return 1
        else:
            return 0'''

def get_username(user_qq):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT name FROM user WHERE qq='"+str(user_qq)+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def get_userinfo(user_qq,type):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT "+type+" FROM user WHERE qq='"+str(user_qq)+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def get_ce(pr,hp,de,speed,sp):
    ce = int(0.12*hp+0.75*pr+11+de+0.2*speed+0.1*sp)
    return ce
    
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

def get_hhnum(user_qq):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT hhnum FROM hunhuan WHERE qq='"+str(user_qq)+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall
    
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
    

def get_infoedqq(plain_text):
    str1 = plain_text.find('=')
    if str1 > 0:
        at_qq = plain_text[str1+1:-1]
        return at_qq
    else:
        try:
            float(plain_text)
            return plain_text
        except ValueError:
            pass
        return 0

def get_nowmap(user_qq):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT nowmap FROM user WHERE qq='"+str(user_qq)+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def get_map(nowmap,maptype):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT "+maptype+" FROM map WHERE map='"+nowmap+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def get_hscmlist(nowmap):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT hsname FROM hunshou WHERE map='"+nowmap+"'")
    fetchall = cursor.fetchall()
    cursor.close()
    conn.close()
    return fetchall

def get_mapwz(user_qq):
    maptype = ['t','d','l','r','tp','wuhun']
    nowmap = get_nowmap(user_qq)
    t = get_map(nowmap,maptype[0])
    d = get_map(nowmap,maptype[1])
    l = get_map(nowmap,maptype[2])
    r = get_map(nowmap,maptype[3])
    tp = get_map(nowmap,maptype[4])
    wuhun = get_userinfo(user_qq,maptype[5])#武魂
    username = get_username(user_qq)
    hunshou = get_hscmlist(nowmap)
    e = ''.join('%s|' %id for id in hunshou)
    n1 = 'To'+username+'〔'+wuhun+'〕:\n'
    n2 = '【'+nowmap+'】\n'
    n3 ='[CQ:image,file=http://114.132.234.220/pic/'+nowmap+'.jpg]\n'
    n4 = 'NPC：敬请期待\n'
    n5 = '魂兽：'+e.rstrip('|')+'\n'
    n6 = '\n'
    if tp == 0:
        n7 = ''
    else:
        n7 = '|>>>传送装置<<<|\n\n' 
    n8 = '特殊场景：敬请期待\n'
    n9 = '\n'
    n10 = '[地图方向]\n'
    if t == '无':
        m1 = ''
    if d == '无':
        m2 = ''
    if l == '无':
        m3 = ''
    if r == '无':
        m4 = ' \n'
    if t != '无':
        m1 = "上："+t+'\n'
    if d != '无':
        m2 = "下："+d+'\n'
    if l != '无':
        m3 = "左："+l+'\n'
    if r != '无':
        m4 = "右："+r+'\n'
    s1 = m1.lstrip()+m2.lstrip()+m3.lstrip()+m4.lstrip()
    n11 = '<可用命令>\n'
    n12 = '进入 场景名称\n'
    n13 = '对话 NPC名称\n'
    n14 = '查看传送 地图名称\n'
    n15 = '传送 地图名称\n'
    n16 = '向 上/下/左/右\n'
    all_map = n1+n2+n4+n5+n6+n7+n8+n9+n10+s1+n11+n12+n13+n14+n15+n16
    return all_map

def w_map(user_qq,newmap):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("UPDATE user SET nowmap='"+newmap+"' WHERE qq='"+str(user_qq)+"'")
    conn.commit()
    cursor.close()
    conn.close()
    return 0

def get_maplist():
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT map FROM map")
    fetchall = cursor.fetchall()
    cursor.close()
    conn.close()
    return fetchall

def get_battles(user_qq):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT battle FROM user WHERE qq='"+str(user_qq)+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def get_hscmmap(hsname):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT map FROM hunshou WHERE hsname='"+hsname+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def get_hslist():
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT hsname FROM hunshou")
    fetchall = cursor.fetchall()
    cursor.close()
    conn.close()
    return fetchall

def w_info(user_qq,typ,data):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("UPDATE user SET "+typ+"='"+str(data)+"' WHERE qq='"+str(user_qq)+"'")
    conn.commit()
    cursor.close()
    conn.close()

def get_hsinfo(hsname,typ):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT "+typ+" FROM hunshou WHERE hsname='"+hsname+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def get_batinfo(batid,typ):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT "+typ+" FROM batdata WHERE id='"+str(batid)+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def del_batdata(batid):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("DELETE FROM batdata WHERE id = '"+str(batid)+"'")
    conn.commit()
    cursor.close()
    conn.close()

def w_batinfo(batid,typ,data):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("UPDATE batdata SET "+typ+"='"+str(data)+"' WHERE id='"+str(batid)+"'")
    conn.commit()
    cursor.close()
    conn.close()

def random_run(probability):
    """probability%的概率"""
    list = []
    for i in range(int(probability)):
        list.append(1)#list中放入probability个1
    for x in range(100 - int(probability)):
        list.append(0)#剩下的位置放入0
    random.shuffle(list)#随机打乱
    a = random.choice(list)#随机抽取一个
    if a == 0:
        return a
    if a == 1:
        return a
#多概率抽取函数
def random_run2(data):
    num = len(data)
    list = []
    t = 0
    for i in range(int(num)):
        for x in range(int(data[i])):
            list.append(t)
        t = t+1
    random.shuffle(list)
    a = random.choice(list)#随机抽取一个
    return a

def get_alluserqq():
    '''获取觉醒后的所有QQ'''
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT qq FROM hunhuan")
    fetchall = cursor.fetchall()
    cursor.close()
    conn.close()
    return fetchall

def get_lvup(lv):
    if lv==10:
        n1 = 1
        return n1
    elif lv==20:
        n1 = 2
        return n1
    elif lv==30:
        n1 = 3
        return n1
    elif lv==40:
        n1 = 4
        return n1
    elif lv==50:
        n1 = 5
        return n1
    elif lv==60:
        n1 = 6
        return n1
    elif lv==70:
        n1 = 7
        return n1
    elif lv==80:
        n1 = 8
        return n1
    elif lv==90:
        n1 = 9
        return n1
    elif lv==100:
        n1 = 10
        return n1
    elif lv==110:
        n1 = 11
        return n1
    elif lv==120:
        n1 = 12
        return n1
    else:
        n1 = 13
        return n1

def lvup(user_qq,lv,exp,hhnum):
    #0=经验不足|1=需要魂环|2=升级
    up = 1
    a = []
    user_mp = get_userinfo(user_qq,'mp')#获取玩家MP
    user_pr = get_userinfo(user_qq,'pr')#获取玩家PR
    user_pow = get_userinfo(user_qq,'pow')#获取玩家POW
    user_hp = get_userinfo(user_qq,'hP')#获取玩家HP
    user_de = get_userinfo(user_qq,'de')#获取玩家DE
    upexp = u_exp(lv,up)
    if int(exp)>=int(upexp):
        if int(lv)%10==0:
            n1 = get_lvup(lv)
            if hhnum >= n1:
                newexp = int(exp)-int(upexp)
                newlv = int(lv)+1
                newpr = add_pr(lv,user_pr)
                newmp = add_mp(lv,user_mp)
                newhp = add_hp(lv,user_hp)
                newpow = add_pow(lv,user_pow)
                newde = add_de(lv,user_de)
                w_info(user_qq,'de',newde)
                w_info(user_qq,'hp',newhp)
                w_info(user_qq,'pow',newpow)
                w_info(user_qq,'mp',newmp)
                w_info(user_qq,'pr',newpr)
                w_info(user_qq,'exp',newexp)
                w_info(user_qq,'lv',newlv)
                l = 2
                a.append(l)
            else:
                w_info(user_qq,'exp',exp)
                l = 1
                a.append(l)
        else:
            newexp = int(exp)-int(upexp)
            newlv = int(lv)+1
            newpr = add_pr(lv,user_pr)
            newmp = add_mp(lv,user_mp)
            newhp = add_hp(lv,user_hp)
            newpow = add_pow(lv,user_pow)
            newde = add_de(lv,user_de)
            w_info(user_qq,'de',newde)
            w_info(user_qq,'hp',newhp)
            w_info(user_qq,'pow',newpow)
            w_info(user_qq,'mp',newmp)
            w_info(user_qq,'pr',newpr)
            w_info(user_qq,'exp',newexp)
            w_info(user_qq,'lv',newlv)
            l = 2
            a.append(l)
    else:
        w_info(user_qq,'exp',exp)
        l = 0
        a.append(l)
    return a

def get_hhnum(user_qq):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT hhnum FROM hunhuan WHERE qq='"+str(user_qq)+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def all_qqlist():
    allqq = get_alluserqq()
    list = []
    for qq in allqq:
        nqq = qq[0]
        list.append(nqq)
    return list

#...
num=['零','一','二','三','四','五','六','七','八','九']
kin=['十','百','千','万','零']
#如果超过万，则分为两部分以节约代码和运行速度
def turn(x):
    i=str(x)
    if len(i) >4:
        i=tran(i[0:-4])+'万'+tran(i[-4:])
    else:
        i=tran(i[-4:])
    return i
#转换数字并插入对应单位，单位为‘零’则再插入一个‘零’以方便正则表达式替换
def tran(x):
    num=['零','一','二','三','四','五','六','七','八','九']
    kin=['零','十','百','千']
    x=list(reversed(x))
    for i in x:
        x[(x.index(i))]=num[int(i)]
    if len(x) >= 2:
        if x[1]==num[0]:
            x.insert(1,kin[0])
        else:
            x.insert(1,kin[1])
        if len(x) >= 4:
            if x[3]==num[0]:
                x.insert(3,kin[0])
            else:
                x.insert(3,kin[2])
            if len(x) >= 6:
                if x[5]==num[0]:
                    x.insert(5,kin[0])
                else:
                    x.insert(5,kin[3])
    x=delz(x)
    return x
#进行多余‘零’的删除
#reversed()函数真是可以用在列表和字符串。
#加上 if 语句 防止对不必要的数据进行正则表达式检测
def delz(x):
    x=''.join(x)
    if '零零'in x:
        x=re.sub('零+','零',x)
    if x.startswith('零'):
        x=list(x)
        x.remove('零')
    x=reversed(x)
    x=''.join(x)
    return x
#...

def get_hshjlist(hsname):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT hj FROM hunshou WHERE hsname='"+hsname+"'")
    fetchall = cursor.fetchall()
    cursor.close()
    conn.close()
    return fetchall

def get_hjinfo(hjname,typ):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT "+typ+" FROM hunji WHERE hjname='"+hjname+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

#获取魂兽魂技以及释放概率（不含魂环掉落概率）
def get_hshj(data):
    data = str(data[0][0])
    a = data.split('|')
    hjname = []#魂技名字
    hjgl = []#魂兽释放魂技概率
    for i in a:
        c = i.find('@')
        hjnam = i[0:c]
        hjname.append(hjnam)
    for b in a:
        end = '$'
        start = b.find('@')
        if start >= 0:
            start += len('@')
            end = b.find(end,start)
            if end >= 0:
                rel = b[start:end].strip()
                hjgl.append(rel)
    hjdict = dict(zip(hjname,hjgl))
    return hjdict

def get_hunhuan(user_qq,typ):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT "+typ+" FROM hunhuan WHERE qq='"+str(user_qq)+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def get_hhyear(data,typ):
    str1 = str(data)
    a = str1.find('@')
    if typ == 0:
        b = str1[0:a]
        return b
    if typ == 1:
        b = str1[a+1:]
        return b

def get_zt(ztname,typ):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT "+typ+" FROM zhuangtai WHERE ztname='"+ztname+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def select_hjbatdate(batid,qq,ap,hjname):
    if ap==1:
        qq=0
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT startdate FROM hjbatdata WHERE batid='"+str(batid)+"' and qq='"+str(qq)+"' and ap='"+str(ap)+"' and hjname='"+hjname+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def select_hjbat(batid,ap):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT hjname FROM hjbatdata WHERE batid='"+str(batid)+"' and ap='"+str(ap)+"'")
    fetchall = cursor.fetchall()
    cursor.close()
    conn.close()
    list = []
    for i in fetchall:
        list.append(i[0])
    return list

def add_hjbat(batid,qq,ap,hjname,startdate):
    id = ran_num()
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("insert into hjbatdata values('"+str(id)+"','"+str(batid)+"','"+str(qq)+"','"+str(ap)+"','"+hjname+"','"+startdate+"')")
    conn.commit()
    cursor.close()
    conn.close()

def select_hjbatid():
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT id FROM hjbatdata")
    fetchall = cursor.fetchall()
    cursor.close()
    conn.close()
    list = []
    for i in fetchall:
        list.append(i[0])
    return list

def get_hjbatinfo(id,typ):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("SELECT "+typ+" FROM hjbatdata WHERE id='"+str(id)+"'")
    fetchall = cursor.fetchall()[0][0]
    cursor.close()
    conn.close()
    return fetchall

def del_hjbat(id):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("DELETE FROM hjbatdata WHERE id = '"+str(id)+"'")
    conn.commit()
    cursor.close()
    conn.close()