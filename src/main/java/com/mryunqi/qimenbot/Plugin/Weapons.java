package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mikuac.shiro.annotation.Shiro;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.WholeMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.PublicAuth;
import com.mryunqi.qimenbot.Controller.User;
import com.mryunqi.qimenbot.Util.ObjectUtil;
import com.mryunqi.qimenbot.dao.UserDao;
import com.mryunqi.qimenbot.dao.UserWeaponsDataDao;
import com.mryunqi.qimenbot.dao.WeaponsDao;
import com.mryunqi.qimenbot.entity.UserWeaponsData;
import com.mryunqi.qimenbot.service.UserService;
import com.mryunqi.qimenbot.service.UserWeaponsDataService;
import com.mryunqi.qimenbot.service.WeaponsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Shiro
@Component
public class Weapons extends BotPlugin {
    private final JdbcTemplate jct;
    private final WeaponsService weaponsService;
    private final WeaponsDao weaponsDao;
    private final UserService userService;
    private final UserDao userDao;
    private final UserWeaponsDataService userWeaponsDataService;
    private final UserWeaponsDataDao userWeaponsDataDao;

    public Weapons(JdbcTemplate jct, WeaponsService weaponsService, WeaponsDao weaponsDao, UserService userService, UserDao userDao, UserWeaponsDataService userWeaponsDataService, UserWeaponsDataDao userWeaponsDataDao) {
        this.jct = jct;
        this.weaponsService = weaponsService;
        this.weaponsDao = weaponsDao;
        this.userService = userService;
        this.userDao = userDao;
        this.userWeaponsDataService = userWeaponsDataService;
        this.userWeaponsDataDao = userWeaponsDataDao;
    }

    @Override
    public int onWholeMessage(@NotNull Bot bot, @NotNull WholeMessageEvent event){
        String msg = event.getMessage();
        String CmdMatcher = "^解封(.*)$";
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        User user = new User(userId);
        Command command = new Command();
        String UserData = user.Get_UserData(jct);
        String Attribute = user.Get_UserNowAttribute(UserData);
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        String AliasCmd = user.Get_UserAliasCmd(jct,msg);
        if (msg.matches(CmdMatcher) | AliasCmd.matches(CmdMatcher)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            if (AliasCmd.matches(CmdMatcher)){
                msg = AliasCmd;
            }
            String PlanText = msg.replaceAll("^解封","");
            if (PlanText.equals("")){
                bot.sendMsg(event, "不能解封空气耶，看下你的指令对不对。", false);
                return MESSAGE_IGNORE;
            }
            QueryWrapper<com.mryunqi.qimenbot.entity.Weapons> wrapper = new QueryWrapper<>();
            wrapper.eq("name",PlanText);
            com.mryunqi.qimenbot.entity.Weapons BPW = weaponsService.getOne(wrapper);
            if (!ObjectUtil.isNull(BPW)){
                bot.sendMsg(event, "当前世界不存在该魂导器，再检查一下要解封的魂导器吧！", false);
                return MESSAGE_IGNORE;
            }
            com.mryunqi.qimenbot.entity.User player = userService.getById(userId);
            JSONObject PlayerWeapons = JSONObject.parseObject(player.getBackpack()).getJSONObject("weapons");
            if (!PlayerWeapons.containsKey(PlanText)){
                bot.sendMsg(event, "你的背包中不存在该魂导器，再检查一下要解封的魂导器吧！", false);
                return MESSAGE_IGNORE;
            }
            // 背包中减少该物品
            user.Reduce_UserItemNum(jct,PlanText,"weapons",1);
            QueryWrapper<UserWeaponsData> usersData = new QueryWrapper<>();
        }
        return MESSAGE_IGNORE;
    }
}
