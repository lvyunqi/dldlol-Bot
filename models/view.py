from pathlib import Path
#利用多个GPU加速
import os
os.environ['CUDA_VISIBLE_DEVICES'] = '2,1,0'
#from configs.path_config import IMAGE_PATH
def user_info_view_myself(status_data,hunhuan_name,up_exp,p_exp,user_ce,upsp,sp_name):
    n1 = 'To'+status_data['userInfo']['name']+'〔'+status_data['userData']['武魂']+'〕:\n'
    n2 = '【'+status_data['userData']['武魂类型']+'】\n'
    n3 = '[攻击魂导器：敬请期待]\n'
    n4 = '[防御魂导器：敬请期待]\n'
    n5 = '·职业：敬请期待\n'
    n6 = '·斗铠：敬请期待\n'
    n7 = '·机甲：敬请期待\n'
    n8 = '·战舰：敬请期待\n'
    n9 = '·等级：'+str(status_data['userData']['等级'])+'['+hunhuan_name+']\n'
    n10 = '·经验：'+str(status_data['userData']['经验'])+'/'+str(up_exp)+'['+str(p_exp)+'%]\n'
    n11 = '·战力：'+str(user_ce)+'\n'
    n12 = '·精神力：'+str(status_data['userData']['精神力'])+'/'+upsp+'['+sp_name+']\n'
    n13 = '·生命：'+str(status_data['userData']['当前血量'])+'/'+str(status_data['userData']['血量'])+'\n'
    n14 = '·魂力：'+str(status_data['userData']['当前魂力值'])+'/'+str(status_data['userData']['魂力值'])+'\n'
    n15 = '·攻击：'+str(status_data['userData']['攻击'])+'\n'
    n16 = '·力量：'+str(status_data['userData']['力量'])+'\n'
    n17 = '·防御：'+str(status_data['userData']['防御'])+'\n'
    n18 = '.暴击：'+str(status_data['userData']['暴击率'])+'%\n'
    n19 = '.爆伤：'+str(status_data['userData']['暴击伤害'])+'\n'
    n20 = '·速度：'+str(status_data['userData']['速度'])+'\n'
    n21 = '·闪避：'+str(status_data['userData']['闪避'])+'%\n'
    n22 = '·体力值：'+str(status_data['userData']['体力'])+'\n'
    n23 = '<可用命令>\n'
    n24 = '背包\n'
    all_view = n1+n2+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24
    return all_view

def user_info_view_other(user_status_data,status_data,hunhuan_name,up_exp,p_exp,user_ce,upsp,sp_name,qq):
    n1 = 'To'+user_status_data['userInfo']['name']+'〔'+user_status_data['userData']['武魂']+'〕:\n'
    n2 = '以下是'+status_data['userInfo']['name']+'['+str(qq)+']的信息\n【'+status_data['userData']['武魂']+'-'+status_data['userData']['武魂类型']+'】\n'
    n3 = '[攻击魂导器：敬请期待]\n'
    n4 = '[防御魂导器：敬请期待]\n'
    n5 = '·职业：敬请期待\n'
    n6 = '·斗铠：敬请期待\n'
    n7 = '·机甲：敬请期待\n'
    n8 = '·战舰：敬请期待\n'
    n9 = '·等级：'+str(status_data['userData']['等级'])+'['+hunhuan_name+']\n'
    n10 = '·经验：'+str(status_data['userData']['经验'])+'/'+str(up_exp)+'['+str(p_exp)+'%]\n'
    n11 = '·战力：'+str(user_ce)+'\n'
    n12 = '·精神力：'+str(status_data['userData']['精神力'])+'/'+upsp+'['+sp_name+']\n'
    n13 = '·生命：'+str(status_data['userData']['当前血量'])+'/'+str(status_data['userData']['血量'])+'\n'
    n14 = '·魂力：'+str(status_data['userData']['当前魂力值'])+'/'+str(status_data['userData']['魂力值'])+'\n'
    n15 = '·攻击：'+str(status_data['userData']['攻击'])+'\n'
    n16 = '·力量：'+str(status_data['userData']['力量'])+'\n'
    n17 = '·防御：'+str(status_data['userData']['防御'])+'\n'
    n18 = '.暴击：'+str(status_data['userData']['暴击率'])+'%\n'
    n19 = '.爆伤：'+str(status_data['userData']['暴击伤害'])+'\n'
    n20 = '·速度：'+str(status_data['userData']['速度'])+'\n'
    n21 = '·闪避：'+str(status_data['userData']['闪避'])+'%\n'
    n22 = '·体力值：'+str(status_data['userData']['体力'])+'\n'
    n23 = '<可用命令>\n'
    n24 = '背包\n'
    all_view = n1+n2+n9+n10+n11+n12+n13+n14+n15+n16+n17+n18+n19+n20+n21+n22+n23+n24
    return all_view

def map_view(username,wuhun,nowmap,map_list,hunshou_list,tp):
    IMAGE_PATH = Path()
    e = ''.join('%s|' %id for id in hunshou_list)
    n1 = 'To'+username+'〔'+wuhun+'〕:\n'
    n2 = '【'+nowmap+'】\n'
    n3 =f'[CQ:image,file={IMAGE_PATH}/圣魂村.jpg]\n'
    #n3 =f'[CQ:image,file=http://114.132.234.220/pic/圣魂村.jpg]\n'
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
    s1 = ''.join(map_list)
    n11 = "<可用命令>"+'\n'
    n12 = '进入 场景名称\n'
    n13 = '对话 NPC名称\n'
    n14 = '查看传送 地图名称\n'
    n15 = '传送 地图名称\n'
    n16 = '向 上/下/左/右\n'
    all_map = n1+n2+n4+n5+n6+n7+n8+n9+n10+s1+n11+n12+n13+n14+n15+n16
    return all_map

def bp_view(bp,mon,userInfo):
    bp_len = len(bp)
    all_page = bp_len/8
    e = ''.join('%s\n' %id for id in bp[0:7])
    moe = ''.join('%s\n' %id for id in mon)
    n1 = 'To'+userInfo['userInfo']['name']+'〔'+userInfo['userData']['武魂']+'〕：\n'
    n2 = e
    n3 = '---第1页/共'+str(int(all_page)+1)+'页\n'
    n4 = moe
    n5 = '<可用命令>\n'
    n6 = '查看物品\n'
    n7 = '查看魂导器 魂导器名称\n'
    n8 = '转账 对方QQ-数量\n'
    n9 = '发送物品 对方QQ-物品名称-数量\n'
    n10 = '出售 物品名称-数量\n'
    n11 = '背包 页数'
    all = n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11
    return all

def bp_num_view(bp,mon,page,userInfo):
    bp_len = len(bp)
    all_page = bp_len/8
    l = 8*(int(page)-1)
    e = ''.join('%s\n' %id for id in bp[l:l+7])
    moe = ''.join('%s\n' %id for id in mon)
    n1 = 'To'+userInfo['userInfo']['name']+'〔'+userInfo['userData']['武魂']+'〕：\n'
    n2 = e
    n3 = '---第'+str(page)+'页/共'+str(int(all_page)+1)+'页\n'
    n4 = moe
    n5 = '<可用命令>\n'
    n6 = '查看物品\n'
    n7 = '查看魂导器 魂导器名称\n'
    n8 = '转账 对方QQ-数量\n'
    n9 = '发送物品 对方QQ-物品名称-数量\n'
    n10 = '出售 物品名称-数量\n'
    n11 = '背包 页数'
    all = n1+n2+n3+n4+n5+n6+n7+n8+n9+n10+n11
    return all