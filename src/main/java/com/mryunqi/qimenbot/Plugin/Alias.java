package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.WholeMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.Function;
import com.mryunqi.qimenbot.Controller.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Alias extends BotPlugin {
    private final JdbcTemplate jct;

    public Alias(JdbcTemplate jct) {
        this.jct = jct;
    }

    @Override
    public int onWholeMessage(@NotNull Bot bot, @NotNull WholeMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        String cmd = "设置快捷键";
        User user = new User(userId);
        Command command = new Command();
        Function func = new Function();
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        String Matcher = "^设置快捷键(.*)$";
        String AliasListMatcher = "^快捷键列表$";
        String SelectAlias = "^查看快捷键(.*)$";
        String CmdDeleteAlias = "^删除快捷键(.*)$";
        if (msg.matches(Matcher)){
            if (!AuthGroupList.contains(groupId)){
                bot.sendMsg(event, "该群没有授权斗罗大陆RPG！", false);
                return MESSAGE_IGNORE;
            }
            if (user.Is_UserExist(jct)){
                bot.sendMsg(event, "您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越", false);
                return MESSAGE_IGNORE;
            }
            String PlanText = func.Extract_plain_text(msg,cmd);
            if (PlanText.length() == 0 | !PlanText.contains("-")) {
                bot.sendMsg(event, "快捷键设置失败！设置格式：设置快捷键 [原指令]-[新指令]\n例：设置快捷键 状态-测试", false);
                return MESSAGE_IGNORE;
            }
            //判断-是否在最后一位
            if (PlanText.charAt(PlanText.length() - 1) == '-') {
                bot.sendMsg(event, "快捷键设置失败！设置格式：设置快捷键 [原指令]-[新指令]，你没有填写新指令！", false);
                return MESSAGE_IGNORE;
            }
            int index = PlanText.indexOf("-");
            if (index == 0) {
                bot.sendMsg(event, "快捷键设置失败！设置格式：设置快捷键 [原指令]-[新指令]，你没有填写原指令！", false);
                return MESSAGE_IGNORE;
            }
            String DefaultCmd = PlanText.substring(0, index).trim();
            String NewCmd =   PlanText.substring(index + 1).trim();
            String Alias = user.Get_UserAlias(jct,DefaultCmd);
            String AllAlias = user.Get_UserAllAlias(jct);
            if (AllAlias == null){
                JSONObject NewAlias = new JSONObject();
                JSONObject NewCmdAlias = new JSONObject();
                NewCmdAlias.put(NewCmd,"null");
                NewAlias.put(DefaultCmd,NewCmdAlias);
                user.Set_UserAlias(jct,JSON.toJSONString(NewAlias));
                bot.sendMsg(event, "你添加了新的快捷键["+NewCmd+"]到["+DefaultCmd+"]中！\n<可用命令>\n快捷键列表", false);
                return MESSAGE_IGNORE;
            }
            if (AllAlias.contains(NewCmd)){
                bot.sendMsg(event, "快捷键设置失败！要设置的新指令["+NewCmd+"]已存在！\n<可用命令>\n快捷键列表", false);
                return MESSAGE_IGNORE;
            }
            if (Alias == null){
                JSONObject map = JSONObject.parseObject(AllAlias);
                JSONObject NewCmdAlias = new JSONObject();
                NewCmdAlias.put(NewCmd,"null");
                map.put(DefaultCmd,NewCmdAlias);
                user.Set_UserAlias(jct,JSON.toJSONString(map));
                bot.sendMsg(event, "你添加了新的快捷键["+NewCmd+"]到["+DefaultCmd+"]中！\n<可用命令>\n快捷键列表", false);
                return MESSAGE_IGNORE;
            } else {
                JSONObject obj = JSON.parseObject(AllAlias);
                JSONObject DefaultCmdAlias = obj.getJSONObject(DefaultCmd);
                DefaultCmdAlias.put(NewCmd,"null");
                obj.put(DefaultCmd,DefaultCmdAlias);
                user.Set_UserAlias(jct,JSON.toJSONString(obj));
                bot.sendMsg(event, "你添加了新的快捷键["+NewCmd+"]到["+DefaultCmd+"]中！\n<可用命令>\n快捷键列表", false);
                return MESSAGE_IGNORE;
            }
        }

        if (msg.matches(AliasListMatcher)){
            if (!AuthGroupList.contains(groupId)){
                bot.sendMsg(event, "该群没有授权斗罗大陆RPG！", false);
                return MESSAGE_IGNORE;
            }
            if (user.Is_UserExist(jct)){
                bot.sendMsg(event, "您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越", false);
                return MESSAGE_IGNORE;
            }
            String AllAlias = user.Get_UserAllAlias(jct);
            StringBuilder message = new StringBuilder();
            Map<String, Object> map = JSONObject.parseObject(AllAlias);
            if (map == null){
                bot.sendMsg(event, "您还没有设置任何快捷键\n<可用命令>\n设置快捷键", false);
                return MESSAGE_IGNORE;
            }
            for (Map.Entry<String, Object> entry : map.entrySet()){
                message.append("·[").append(entry.getKey()).append("]\n");
            }
            bot.sendMsg(event, "已设置快捷键指令列表：\n"+message+"\n<可用命令>\n查看快捷键", false);
            return MESSAGE_IGNORE;
        }

        if (msg.matches(SelectAlias)){
            if (!AuthGroupList.contains(groupId)){
                bot.sendMsg(event, "该群没有授权斗罗大陆RPG！", false);
                return MESSAGE_IGNORE;
            }
            if (user.Is_UserExist(jct)){
                bot.sendMsg(event, "您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越", false);
                return MESSAGE_IGNORE;
            }
            String PlanText = msg.replaceAll("^查看快捷键","");
            String AllAlias = user.Get_UserAllAlias(jct);
            JSONObject map = JSONObject.parseObject(AllAlias);
            if (PlanText.equals("")){
                bot.sendMsg(event, "指令格式错误！查询格式：查看快捷键 [原指令]\n<可用命令>\n快捷键列表", false);
                return MESSAGE_IGNORE;
            }
            if (!map.containsKey(PlanText)){
                bot.sendMsg(event, "["+PlanText+"]不在您设置的快捷键列表中，请重新查看快捷键列表！\n<可用命令>\n快捷键列表", false);
                return MESSAGE_IGNORE;
            }
            StringBuilder message = new StringBuilder();
            Map<String, Object> obj = map.getJSONObject(PlanText);
            for (Map.Entry<String, Object> entry : obj.entrySet()){
                message.append("·[").append(entry.getKey()).append("]\n");
            }
            bot.sendMsg(event, "【"+PlanText+"】快捷键指令列表：\n"+message+"\n<可用命令>\n删除快捷键", false);
            return MESSAGE_IGNORE;
        }

        if (msg.matches(CmdDeleteAlias)){
            JSONObject AuthGroup = JSONObject.parseObject(AuthGroupList);
            if (!AuthGroup.containsKey(groupId)){
                bot.sendMsg(event, "该群没有授权斗罗大陆！", false);
                return MESSAGE_IGNORE;
            }
            if (user.Is_UserExist(jct)){
                bot.sendMsg(event, "您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越", false);
                return MESSAGE_IGNORE;
            }
            String PlanText = msg.replaceAll("^删除快捷键","");
            // 判断PlanText是否为空
            if (PlanText.equals("")){
                bot.sendMsg(event, "快捷键删除失败！删除格式：删除快捷键 [原指令]-[快捷键]\n<可用命令>\n快捷键列表", false);
                return MESSAGE_IGNORE;
            }
            if (PlanText.length() == 0 | !PlanText.contains("-")) {
                bot.sendMsg(event, "快捷键删除失败！删除格式：删除快捷键 [原指令]-[快捷键]\n例：删除快捷键 状态-测试", false);
                return MESSAGE_IGNORE;
            }
            //判断-是否在最后一位
            if (PlanText.charAt(PlanText.length() - 1) == '-') {
                bot.sendMsg(event, "快捷键删除失败！删除格式：删除快捷键 [原指令]-[快捷键]，你没有填写要删除的快捷键！", false);
                return MESSAGE_IGNORE;
            }
            int index = PlanText.indexOf("-");
            if (index == 0) {
                bot.sendMsg(event, "快捷键删除失败！删除格式：删除快捷键 [原指令]-[快捷键]，你没有填写原指令！", false);
                return MESSAGE_IGNORE;
            }
            String DefaultCmd = PlanText.substring(0, index).trim();
            String DelCmd =   PlanText.substring(index + 1).trim();
            String Alias = user.Get_UserAlias(jct,DefaultCmd);
            String AliasList = user.Get_UserAllAlias(jct);
            if (AliasList == null){
                bot.sendMsg(event, "你没有设置过快捷键，删除失败！\n<可用命令>\n快捷键列表", false);
                return MESSAGE_IGNORE;
            }

            if (Alias == null){
                bot.sendMsg(event, "你没有设置过该指令的快捷键，删除失败！\n<可用命令>\n快捷键列表", false);
                return MESSAGE_IGNORE;
            }

            JSONObject AliasMap = JSONObject.parseObject(Alias);
            // 判断DelCmd是否在AliasMap中
            if (!AliasMap.containsKey(DelCmd)){
                bot.sendMsg(event, "你没有设置过该快捷键，删除失败！\n<可用命令>\n快捷键列表", false);
                return MESSAGE_IGNORE;
            }
            AliasMap.remove(DelCmd);
            JSONObject AliasAllMap = JSONObject.parseObject(AliasList);
            if (AliasMap.size() == 0){
                AliasAllMap.remove(DefaultCmd);
            }else{
                AliasAllMap.put(DefaultCmd, AliasMap);
            }
            user.Set_UserAlias(jct, AliasAllMap.toJSONString());
            bot.sendMsg(event, "快捷键删除成功！\n<可用命令>\n快捷键列表", false);
            return MESSAGE_IGNORE;
        }
        return MESSAGE_IGNORE;
    }
}
