import pymysql

dbinfo = {
    'host':'114.132.234.220',
    'user':'dldlol_bot',
    'passwd':'dldlol',
    'db':'dldlol_bot',
}

def w_con(user_qq,newcon):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("UPDATE user SET con="+str(newcon)+" WHERE qq='"+str(user_qq)+"'")
    conn.commit()
    cursor.close()
    conn.close()

def w_bat(user_qq,num):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("UPDATE user SET battle="+str(num)+" WHERE qq='"+str(user_qq)+"'")
    conn.commit()
    cursor.close()
    conn.close()

def w_batdata(num,hsname,hshp,hsmp,pow,ct,ctp,speed,de,p1,starttime,lasttime,endtime):
    conn = pymysql.connect(**dbinfo)
    cursor=conn.cursor()
    cursor.execute("insert into batdata (id,hsname,hshp,hsmp,pow,ct,ctp,speed,de,p1,starttime,lasttime,endtime) values('"+str(num)+"','"+str(hsname)+"','"+str(hshp)+"','"+str(hsmp)+"','"+str(pow)+"','"+str(ct)+"','"+str(ctp)+"','"+str(speed)+"','"+str(de)+"','"+str(p1)+"','"+str(starttime)+"','"+str(lasttime)+"','"+str(endtime)+"')")
    conn.commit()
    cursor.close()
    conn.close()

def start_bat(username,wuhun,hsname):
    n1 = 'To'+username+'〔'+wuhun+'〕:\n'
    n2 = '战斗开始啦，请小心。[体力-1]\n'
    n3 = '敌方魂兽：'+hsname+'\n'
    n4 = 'pic'
    n5 = '1P：'+username+'\n\n'
    n6 = 'Tip：P 为己方目标\n'
    n7 = '<可用命令>\n'
    n8 = '攻击\n'
    n9 = '魂技1~50-1/2/3/4 P\n'
    n10 = '使物 1~20\n'
    all = n1+n2+n3+n5+n6+n7+n8+n9+n10
    return all

def get_userhitpt(user_pow,user_pr,user_sp,den):
    atk = int(int(user_pr)*(1+(int(user_pow)+int(user_sp))*0.004)-int(den)*1.5)
    if atk < 0:
        at = 1
        return at
    else:
        return atk

def get_hshitpt(hs_pow,user_de):
    atk = int(int(hs_pow)*2.5-int(user_de)*1.5)
    if atk < 0:
        at = 1
        return at
    else:
        return atk

def get_hshitct(hs_pow,user_de,hs_ctp):
    atk = int(int(hs_pow)*2.5*(int(hs_ctp)*0.01)-int(user_de)*1.5)
    if atk < 0:
        at = 1
        return at
    else:
        return atk

def get_userhitct(user_pow,user_pr,user_sp,user_ctp,den):
    atk = int(int(user_pr)*(1+(int(user_pow)+int(user_sp))*0.004)*int(user_ctp)*0.01-int(den)*1.5)
    if atk < 0:
        at = 1
        return at
    else:
        return atk

'''def get_jshs(username,wuhun,ct,user_atk,user_hp):
    n1 = 'To'+username+'〔'+wuhun+'〕:\n'
    if ct == 0:
        n2 = username+'拿起[拳头]攻击向对方,造成'+str(user_atk)+'点伤害\n'
    else:
        n2 = username+'拿起[拳头]攻击向对方,造成'+int(user_atk)+'点[暴击]伤害\n'
    n3 = '我方生命剩余'+user_hp+'\n'
    n4 = '对方生命剩余0\n\n'
    n5 = '对方已倒下，魂环徐徐升起：敬请期待[][]\n\n'
    n6 = '['+username+']获得经验[敬请期待][100%]\n'
    n7 = '<可用命令>'
    n8 = '魂环信息'
    all = n1+n2+n3+n4+n5+n6+n7+n8
    return all'''
