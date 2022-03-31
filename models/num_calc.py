def u_exp(lv,up):
    '''计算升级下一等级所需经验'''
    exp = int((lv*1500)/8*up)
    return str(exp)

def p_exp(exp,up_exp):
    '''距离下一等级经验百分比'''
    if exp == 0:
        z = '0.00'
        return z
    else:
        p = float(int(exp)/int(up_exp)*100)
        new_p = round(p,2)
        return new_p

def get_ce(status_data):
    hp = status_data['userData']['血量']
    pr = status_data['userData']['攻击']
    de = status_data['userData']['防御']
    speed = status_data['userData']['速度']
    sp = status_data['userData']['精神力']
    ce = int(0.12*hp+0.75*pr+11+de+0.2*speed+0.1*sp)
    return ce