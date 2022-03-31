import json
from models.status_info import get_userinfo,get_material_lv,get_weapons_lv
from models.command import get_command
def bp_one(qq):
    currency = get_command('currency')
    user_money_dict = get_userinfo(qq,'money')
    user_money_dict = json.loads(user_money_dict)
    json_bp_data = get_userinfo(qq,'backpack')
    bp_Data = json.loads(json_bp_data)
    list = ['consumables','weapons','material','battle_armor','mecha']
    bp = []
    mon = []
    for i in list:
        if i == 'consumables':
            consumables_dict = bp_Data['consumables']
            for key,value in consumables_dict.items():
                data = '·[消耗]'+key+'x'+value
                bp.append(data)
        if i == 'material':
            material_dict = bp_Data['material']
            for key,value in material_dict.items():
                coordinates = value.find('|')
                lv = value[:coordinates]
                lv_name = get_material_lv(lv)
                num = value[coordinates+1:]
                data = '·[材料]'+lv_name+'·'+key+'x'+num
                bp.append(data)
    for key,value in user_money_dict.items():
        if key in currency:
            data = '['+key+']:'+value
            mon.append(data)
    return bp,mon

def bp_weapons(qq):
    currency = get_command('currency')
    user_money_dict = get_userinfo(qq,'money')
    user_money_dict = json.loads(user_money_dict)
    json_bp_data = get_userinfo(qq,'backpack')
    bp_Data = json.loads(json_bp_data)
    list = ['consumables','weapons','material','battle_armor','mecha']
    bp = []
    mon = []
    for i in list:
        if i == 'weapons':
            weapons_dict = bp_Data['weapons']
            for key,value in weapons_dict.items():
                coordinates = value.find('|')
                lv = value[:coordinates]
                #lv_name = get_weapons_lv(lv)
                num = value[coordinates+1:]
                if int(lv) == 0:
                    data = '·[攻击魂导器]'+key+'x'+num
                    bp.append(data)
                if int(lv) == 1:
                    data = '·[防御魂导器]'+key+'x'+num
                    bp.append(data)
    for key,value in user_money_dict.items():
        if key in currency:
            data = '['+key+']:'+value
            mon.append(data)
    return bp,mon

def bp_battle_armor(qq):
    currency = get_command('currency')
    user_money_dict = get_userinfo(qq,'money')
    user_money_dict = json.loads(user_money_dict)
    json_bp_data = get_userinfo(qq,'backpack')
    bp_Data = json.loads(json_bp_data)
    list = ['consumables','weapons','material','battle_armor','mecha']
    bp = []
    mon = []
    for i in list:
        if i == 'battle_armor':
            battle_armor_dict = bp_Data['battle_armor']
            for key,value in battle_armor_dict.items():
                coordinates = value.find('|')
                lv = value[:coordinates]
                lv_name = get_material_lv(lv)
                num = value[coordinates+1:]
                data = '·[斗铠]'+lv_name+'·'+key+'x'+num
                bp.append(data)
    for key,value in user_money_dict.items():
        if key in currency:
            data = '['+key+']:'+value
            mon.append(data)
    return bp,mon

def bp_mecha(qq):
    currency = get_command('currency')
    user_money_dict = get_userinfo(qq,'money')
    user_money_dict = json.loads(user_money_dict)
    json_bp_data = get_userinfo(qq,'backpack')
    bp_Data = json.loads(json_bp_data)
    list = ['consumables','weapons','material','battle_armor','mecha']
    bp = []
    mon = []
    for i in list:
        if i == 'mecha':
            mecha_dict = bp_Data['mecha']
            for key,value in mecha_dict.items():
                coordinates = value.find('|')
                lv = value[:coordinates]
                lv_name = get_material_lv(lv)
                num = value[coordinates+1:]
                data = '·[机甲]'+lv_name+'·'+key+'x'+num
                bp.append(data)
    for key,value in user_money_dict.items():
        if key in currency:
            data = '['+key+']:'+value
            mon.append(data)
    return bp,mon