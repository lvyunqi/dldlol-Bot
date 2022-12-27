package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.annotation.Shiro;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.PublicAuth;
import com.mryunqi.qimenbot.Controller.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static com.mryunqi.qimenbot.Template.SelectReservePoolTpl.SuccessSelectReservePool;

@Shiro
@Component
public class SelectReservePool extends BotPlugin {
    private final JdbcTemplate jct;

    public SelectReservePool(JdbcTemplate jct) {
        this.jct = jct;
    }

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String CmdMatcher = "^查看储备池$";
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        User user = new User(userId);
        Command command = new Command();
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        String AliasCmd = user.Get_UserAliasCmd(jct, msg);
        if (msg.matches(CmdMatcher) | AliasCmd.matches(CmdMatcher)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            JSONObject jsonUserData = JSONObject.parseObject(UserData);
            JSONObject userData = jsonUserData.getJSONObject("userData");
            long HP_Pool = userData.getLong("生命储备");
            long HP_Max_Pool = userData.getLong("生命储备上限");
            long MP_Pool = userData.getLong("魂力储备");
            long MP_Max_Pool = userData.getLong("魂力储备上限");
            String message = SuccessSelectReservePool(UserData,Attribute,HP_Pool,HP_Max_Pool,MP_Pool,MP_Max_Pool);
            bot.sendMsg(event,message,false);
        }
        return MESSAGE_IGNORE;
    }
}
