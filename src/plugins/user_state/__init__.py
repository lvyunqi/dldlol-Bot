import json
import nonebot
from nonebot import on_command
from nonebot.adapters.onebot.v11 import Bot, GroupMessageEvent
from nonebot.adapters import Message
from nonebot.params import CommandArg
from models.function import get_message_at, is_number, is_user_awaken, event_plain_text, select_alluserqq
from models.command import get_command
from models.num_calc import u_exp, p_exp, get_ce
from models.view import user_info_view_myself, user_info_view_other
from models.status_info import user_exist, hunhuan_num, get_userinfo, get_lvname, get_upsp, get_spname

info = on_command("状态")

@info.handle()
async def handle_info(bot:  Bot, event: GroupMessageEvent, args: Message = CommandArg()):
    #driver = nonebot.get_driver()
    group = get_command('group_list')
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
            
            command_up_exp = get_command('exp_up')
            lv = user_data['userData']['等级']
            up_exp = u_exp(lv,command_up_exp)
            exp = user_data['userData']['经验']
            exp_p = p_exp(exp,up_exp)
            user_ce = get_ce(user_data)
            sp = user_data['userData']['精神力']
            up_sp = get_upsp(sp)
            sp_name = get_spname(sp)
            message_data = user_info_view_myself(user_data,hunhuan_name,up_exp,exp_p,user_ce,up_sp,sp_name)

            if isinstance_group in group:
                group_id = event.group_id
                await bot.send_group_msg(group_id=group_id,message=message_data)
            if isinstance_group == 'private':
                await info.send(message=message_data)
            elif isinstance_group not in group:
                pass
        
        if event_text != 0:
            """
            *当指令未带参数时执行
            """
            #判断指令附加的参数是否为数字
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
                        userInfo = get_userinfo(event_text,'state_info')
                        self_userInfo = json.loads(get_userinfo(user_qq,'state_info'))
                        #user_info = status_info(user_qq)
                        hhnum = hunhuan_num(event_text)
                        user_data = json.loads(userInfo)
                        user_data['hhnum'] = hhnum
                        hunhuan_name = get_lvname(hhnum)
                        #获取EXP增幅倍率
            
                        command_up_exp = get_command('exp_up')
                        lv = user_data['userData']['等级']
                        up_exp = u_exp(lv,command_up_exp)
                        exp = user_data['userData']['经验']
                        exp_p = p_exp(exp,up_exp)
                        user_ce = get_ce(user_data)
                        sp = user_data['userData']['精神力']
                        up_sp = get_upsp(sp)
                        sp_name = get_spname(sp)
                        message_data = user_info_view_other(self_userInfo,user_data,hunhuan_name,up_exp,exp_p,user_ce,up_sp,sp_name,event_text)

                        if isinstance_group in group:
                           group_id = event.group_id
                           await bot.send_group_msg(group_id=group_id,message=message_data)
                        if isinstance_group == 'private':
                           await info.send(message=message_data)
                        elif isinstance_group not in group:
                           pass
                        
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
                        userInfo = get_userinfo(qq,'state_info')
                        self_userInfo = json.loads(get_userinfo(user_qq,'state_info'))
                        #user_info = status_info(user_qq)
                        hhnum = hunhuan_num(qq)
                        user_data = json.loads(userInfo)
                        user_data['hhnum'] = hhnum
                        hunhuan_name = get_lvname(hhnum)
                        #获取EXP增幅倍率
            
                        command_up_exp = get_command('exp_up')
                        lv = user_data['userData']['等级']
                        up_exp = u_exp(lv,command_up_exp)
                        exp = user_data['userData']['经验']
                        exp_p = p_exp(exp,up_exp)
                        user_ce = get_ce(user_data)
                        sp = user_data['userData']['精神力']
                        up_sp = get_upsp(sp)
                        sp_name = get_spname(sp)
                        message_data = user_info_view_other(self_userInfo,user_data,hunhuan_name,up_exp,exp_p,user_ce,up_sp,sp_name,qq)
                        
                        if isinstance_group in group:
                           group_id = event.group_id
                           await bot.send_group_msg(group_id=group_id,message=message_data)
                        if isinstance_group == 'private':
                           await info.send(message=message_data)
                        elif isinstance_group not in group:
                           pass