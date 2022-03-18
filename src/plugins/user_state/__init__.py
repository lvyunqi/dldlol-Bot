import json
import nonebot
from nonebot import on_command
from nonebot.adapters.onebot.v11 import Bot, GroupMessageEvent
from nonebot.adapters import Message
from nonebot.params import CommandArg
from ..__function import get_message_at, is_number, is_user_awaken, event_plain_text, select_alluserqq
from ..__command import get_command
from ..__num_calc import u_exp, p_exp, get_ce
from ..__view import user_info_view_myself, user_info_view_other
from ..__status_info import user_exist, status_info, hunhuan_num, get_userinfo, get_lvname, get_upsp, get_spname

info = on_command("状态")

@info.handle()
async def handle_info(bot: Bot, event: GroupMessageEvent, args: Message = CommandArg()):
    driver = nonebot.get_driver()
    group = driver.config.group_id
    isinstance_group = f'{event.group_id if isinstance(event, GroupMessageEvent) else "private"}'

    event_text = event_plain_text(event.json(),2) 

    #plain_text = args.extract_plain_text()  #发送命令时跟随的参数

    is_num = is_number(event_text)
    user_qq = str(event.user_id)  #获取玩家QQ号

    userexist = user_exist(event.user_id)
    if userexist != 0:
        if isinstance_group in group:
            await info.send(message=str(userexist))
        if isinstance_group == 'private':
            await info.send(message=str(userexist))
        elif isinstance_group not in group:
            pass
    
    if userexist == 0:

        if event_text == 0:
            userInfo = get_userinfo(user_qq,'state_info')
            #user_info = status_info(user_qq)
            hhnum = hunhuan_num(user_qq)
            user_data = json.loads(userInfo)
            user_data['hhnum'] = hhnum
            hunhuan_name = get_lvname(hhnum)
            #获取EXP增幅倍率
            '''
            command_up_exp = get_command('exp_up')
            lv = userInfo['userData']['等级']
            up_exp = u_exp(lv,command_up_exp)
            exp = userInfo['userData']['经验']
            exp_p = p_exp(exp,up_exp)
            user_ce = get_ce(userInfo)
            sp = userInfo['userData']['精神力']
            up_sp = get_upsp(sp)
            sp_name = get_spname(sp)
            message_data = user_info_view_myself(userInfo,hunhuan_name,up_exp,exp_p,user_ce,up_sp,sp_name)'''

            if isinstance_group in group:
                group_id = event.group_id
                await bot.send_group_msg(group_id=group_id,message=str(user_data))
            if isinstance_group == 'private':
                await info.send(message=user_data)
            elif isinstance_group not in group:
                pass
        
        if event_text != 0:
            is_num = is_number(event_text)
            if is_num == 1:
                is_game_user_qq = select_alluserqq()
                if event_text not in is_game_user_qq:
                    await info.send(message='对方还没有穿越到斗罗大陆！')
                else:
                    is_game_user_awaken = is_user_awaken(event_text)
                    if is_game_user_awaken == 1:
                        await info.send(message='对方还没有觉醒武魂！')
                    if is_game_user_awaken == 0:
                        user_info = status_info(event_text)
                        hhnum = hunhuan_num(event_text)
                        user_info['hhnum'] = hhnum
                        hunhuan_name = get_lvname(hhnum)
                        #获取EXP增幅倍率
                        command_up_exp = get_command('exp_up')
                        lv = get_userinfo(event_text,'lv')
                        up_exp = u_exp(lv,command_up_exp)
                        exp = get_userinfo(event_text,'exp')
                        exp_p = p_exp(exp,up_exp)
                        user_ce = get_ce(user_info)
                        sp = get_userinfo(event_text,'sp')
                        up_sp = get_upsp(sp)
                        sp_name = get_spname(sp)
                        user_name = get_userinfo(user_qq,'name')
                        user_wuhun = get_userinfo(user_qq,'wuhun')
                        message_data = user_info_view_other(event_text,user_name,user_wuhun,user_info,hunhuan_name,up_exp,exp_p,user_ce,up_sp,sp_name)
            if is_num == 0:
                qq = get_message_at(event.json())
                is_game_user_qq = select_alluserqq()
                if qq not in is_game_user_qq:
                    await info.send(message='对方还没有穿越到斗罗大陆！')
                else:
                    is_game_user_awaken = is_user_awaken(qq)
                    if is_game_user_awaken == 1:
                        await info.send(message='对方还没有觉醒武魂！')
                    if is_game_user_awaken == 0:
                        user_info = status_info(qq)
                        hhnum = hunhuan_num(qq)
                        user_info['hhnum'] = hhnum
                        hunhuan_name = get_lvname(hhnum)
                        #获取EXP增幅倍率
                        command_up_exp = get_command('exp_up')
                        lv = get_userinfo(qq,'lv')
                        up_exp = u_exp(lv,command_up_exp)
                        exp = get_userinfo(qq,'exp')
                        exp_p = p_exp(exp,up_exp)
                        user_ce = get_ce(user_info)
                        sp = get_userinfo(qq,'sp')
                        up_sp = get_upsp(sp)
                        sp_name = get_spname(sp)
                        user_name = get_userinfo(user_qq,'name')
                        user_wuhun = get_userinfo(user_qq,'wuhun')
                        message_data = user_info_view_other(qq,user_name,user_wuhun,user_info,hunhuan_name,up_exp,exp_p,user_ce,up_sp,sp_name)