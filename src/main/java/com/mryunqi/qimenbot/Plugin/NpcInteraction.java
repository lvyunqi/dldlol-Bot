package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.GameNPC;
import com.mryunqi.qimenbot.Controller.PublicAuth;
import com.mryunqi.qimenbot.Controller.User;
import com.mryunqi.qimenbot.Util.ObjectUtil;
import com.mryunqi.qimenbot.dao.UserDao;
import com.mryunqi.qimenbot.entity.Npc;
import com.mryunqi.qimenbot.service.NpcService;
import com.mryunqi.qimenbot.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mryunqi.qimenbot.Controller.Function.Get_Random_Range;
import static com.mryunqi.qimenbot.Controller.TaskAct.playerTaskNpc;
import static com.mryunqi.qimenbot.Template.NpcTpl.npcMain;

/**
 * @author mryunqi
 * @date 2022/12/17
 */
@Component
public class NpcInteraction extends BotPlugin {
    private final JdbcTemplate jct;
    private final NpcService npcService;
    private final UserService userService;
    private final UserDao userDao;

    private String userId;

    public NpcInteraction(JdbcTemplate jct, NpcService npcService, UserService userService, UserDao userDao) {
        this.jct = jct;
        this.npcService = npcService;
        this.userService = userService;
        this.userDao = userDao;
    }

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String isShop;
        String isTask;
        String msg = event.getMessage();
        String cmdMatcher = "^对话(.*)$";
        userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        User user = new User(userId);
        Command command = new Command();
        String authGroupList = command.Get_CommandAuthGroupList(jct);
        String aliasCmd = user.Get_UserAliasCmd(jct, msg);
        if (msg.matches(cmdMatcher) | aliasCmd.matches(cmdMatcher)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, authGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            if (aliasCmd.matches(cmdMatcher)){
                msg = aliasCmd;
            }
            String planText = msg.replaceAll("^对话","");
            if ("".equals(planText)){
                bot.sendMsg(event, "不能与空气对话耶，格式：对话[NPC]。", false);
                return MESSAGE_IGNORE;
            }
            GameNPC gameNPC = new GameNPC();
            String userNowPosition = user.Get_UserNowMap(jct);
            String npcString = gameNPC.get_NPC_Name_Template(jct, userNowPosition);

            Npc npc = npcService.getById(planText);
            if (ObjectUtil.isNull(npc)){
                bot.sendMsg(event, "当前世界不存在这号人。", false);
                return MESSAGE_IGNORE;
            }
            if (!npcString.contains(planText)){
                bot.sendMsg(event, "当前地图不存在这号人！", false);
                return MESSAGE_IGNORE;
            }
            String userData = user.Get_UserData(jct);
            String attribute = user.Get_UserNowAttribute(userData);
            String message = npcMain(userData,attribute,planText,dailyTalk(npc));
            bot.sendMsg(event,message,false);
            com.mryunqi.qimenbot.entity.User player = userService.getById(event.getUserId());
            player.setNpc(planText);
            userDao.updateById(player);
            playerTaskNpc(userDao,player,planText);
        }
        return MESSAGE_IGNORE;
    }

    private static String dailyTalk(Npc npc){
        String dailyTalk = npc.getDailyTalk();
        if (dailyTalk == null){
            return "此人很是高冷，一句话都没说！";
        }
        List<String> dailyTalkList = Stream.of(dailyTalk.replaceAll("\\[|\\]", "").split(","))
                .map(s -> s.replaceAll("^\"|\"$", ""))
                .collect(Collectors.toList());
        if (dailyTalkList.size() == 0){
            return "此人很是高冷，一句话都没说！";
        }
        long key = Get_Random_Range(0,dailyTalkList.size());
        return dailyTalkList.get((int) key-1);
    }
}
