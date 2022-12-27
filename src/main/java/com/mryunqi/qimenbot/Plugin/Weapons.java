package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mikuac.shiro.annotation.Shiro;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.Function;
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
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.Objects;

import static com.mryunqi.qimenbot.Controller.Function.Get_Random_Number;
import static com.mryunqi.qimenbot.Template.WeaponsTpl.*;

@Shiro
@Component
public class Weapons extends BotPlugin {
    private String userId;
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
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String CmdMatcher = "^解封(.*)$";
        String CmdWeaponsList = "^魂导器列表(.*)$";
        String CmdWeaponsEq = "^装备魂导器(.*)$";
        String CmdCloseWeapons = "^卸下魂导器(.*)$";
        String CmdSelectWeapons = "^查看魂导器(.*)$";
        userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        User user = new User(userId);
        Command command = new Command();
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
            if (ObjectUtil.isNull(BPW)){
                bot.sendMsg(event, "当前世界不存在该魂导器，再检查一下要解封的魂导器吧！", false);
                return MESSAGE_IGNORE;
            }
            com.mryunqi.qimenbot.entity.User player = userService.getById(userId);
            JSONObject PlayerWeapons = JSONObject.parseObject(player.getBackpack()).getJSONObject("weapons");
            if (!PlayerWeapons.containsKey(PlanText)){
                bot.sendMsg(event, "你的背包中不存在该魂导器，再检查一下要解封的魂导器吧！", false);
                return MESSAGE_IGNORE;
            }
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            // 背包中减少该物品
            user.Reduce_UserItemNum(jct,PlanText,"weapons",1);
            // 获取魂导器属性
            QueryWrapper<com.mryunqi.qimenbot.entity.Weapons> weaponsQueryWrapper = new QueryWrapper<>();
            weaponsQueryWrapper.eq("name",PlanText);
            com.mryunqi.qimenbot.entity.Weapons weapons = weaponsService.getOne(weaponsQueryWrapper);
            String WeaponsData = weapons.getDataInfo();
            int WeaponsId = Integer.parseInt(Get_Random_Number());
            UserWeaponsData userWeaponsData = new UserWeaponsData();
            userWeaponsData.setId(WeaponsId);
            userWeaponsData.setName(PlanText);
            userWeaponsData.setQq(Integer.valueOf(userId));
            userWeaponsData.setDataInfo(WeaponsData);
            userWeaponsDataDao.insert(userWeaponsData);
            String message = Unblock_Success(UserData,Attribute,WeaponsId,PlanText);
            bot.sendMsg(event, message, false);
            return MESSAGE_IGNORE;
        }
        if (msg.matches(CmdWeaponsList) | AliasCmd.matches(CmdWeaponsList)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            if (AliasCmd.matches(CmdWeaponsList)){
                msg = AliasCmd;
            }
            String PlanText = msg.replaceAll("^魂导器列表","");
            if (PlanText.equals("")){
                PlanText = "1";
            }
            Function func = new Function();
            if (!func.Is_Number(PlanText)){
                bot.sendMsg(event, "请正确输入页数！", false);
                return MESSAGE_IGNORE;
            }
            Page<UserWeaponsData> DataList = SelectWeaponsListPage(Integer.parseInt(PlanText),8);
            StringBuilder data = new StringBuilder();
            for (UserWeaponsData userWeaponsData: DataList.getRecords()){
                int WeaponsId = userWeaponsData.getId();
                String WeaponsName = userWeaponsData.getName();
                com.mryunqi.qimenbot.Controller.Weapons w = new com.mryunqi.qimenbot.Controller.Weapons();
                String WeaponsType = w.Get_WeaponsType(jct,WeaponsName).equals("0") ? "┃〔攻击〕" : "┃〔防御〕";
                data.append(WeaponsType);
                data.append("[").append(WeaponsId).append("]").append(WeaponsName).append("\n");
            }
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            long PageTotal = DataList.getTotal();
            Long PageCurrent = DataList.getCurrent();
            Long AllPage = PageTotal / 8;
            if (PageTotal%8 != 0){
                AllPage++;
            }
            String message = Weapons_Unblock_List(UserData,Attribute,PageCurrent,AllPage,data);
            bot.sendMsg(event, message, false);
            return MESSAGE_IGNORE;
        }
        if (msg.matches(CmdWeaponsEq) | AliasCmd.matches(CmdWeaponsEq)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            if (AliasCmd.matches(CmdWeaponsEq)){
                msg = AliasCmd;
            }
            String PlanText = msg.replaceAll("^装备魂导器","");
            if (PlanText.equals("")){
                bot.sendMsg(event, "请正确选择要装备的魂导器！装备魂导器[ID]", false);
                return MESSAGE_IGNORE;
            }
            Function func = new Function();
            if (!func.Is_Number(PlanText)){
                bot.sendMsg(event, "请正确输入魂导器ID！", false);
                return MESSAGE_IGNORE;
            }
            QueryWrapper<UserWeaponsData> userWeaponsDataQueryWrapper = new QueryWrapper<>();
            userWeaponsDataQueryWrapper.eq("id",PlanText);
            UserWeaponsData userWeapons = userWeaponsDataService.getById(PlanText);
            if (ObjectUtil.isNull(userWeapons)){
                bot.sendMsg(event, "您没有解封的该魂导器，再检查一下要装备的魂导器ID吧！", false);
                return MESSAGE_IGNORE;
            }
            String WeaponAttack = user.Get_UserWeaponName(jct,0);
            String WeaponDefense = user.Get_UserWeaponName(jct,1);
            String userWeaponsData = userWeapons.getDataInfo();
            String weaponsName = userWeapons.getName();
            QueryWrapper<com.mryunqi.qimenbot.entity.Weapons> weaponsQueryWrapper = new QueryWrapper<>();
            weaponsQueryWrapper.eq("name",weaponsName);
            com.mryunqi.qimenbot.entity.Weapons weapons = weaponsService.getOne(weaponsQueryWrapper);
            int weaponsType = weapons.getType();
            UpdateWrapper<com.mryunqi.qimenbot.entity.User> playerUpdate = new UpdateWrapper<>();
            if (weaponsType == 0){
                if (!StringUtils.isBlank(WeaponAttack)){
                    bot.sendMsg(event, "您已经装备的有攻击魂导器了，请卸下后再装备！", false);
                    return MESSAGE_IGNORE;
                }
                playerUpdate.eq("qq",userId).set("weapons_attack",PlanText);
            }
            if (weaponsType == 1){
                if (!StringUtils.isBlank(WeaponDefense)){
                    bot.sendMsg(event, "您已经装备的有防御魂导器了，请卸下后再装备！", false);
                    return MESSAGE_IGNORE;
                }
                playerUpdate.eq("qq",userId).set("weapons_defence",PlanText);
            }
            userDao.update(null,playerUpdate);
            JSONObject jsonUserWeaponsData = JSONObject.parseObject(userWeaponsData);
            JSONObject jsonWeaponsData = jsonUserWeaponsData.getJSONObject("dataInfo");
            long Pow = jsonWeaponsData.getLong("力量");
            long Atk = jsonWeaponsData.getLong("攻击");
            long HP = jsonWeaponsData.getLong("血量");
            long Speed = jsonWeaponsData.getLong("速度");
            long Dodge = jsonWeaponsData.getLong("闪避");
            long Def = jsonWeaponsData.getLong("防御");
            int Ct = jsonWeaponsData.getIntValue("暴击率");
            long Ctp = jsonWeaponsData.getLong("暴击伤害");
            long SP = jsonWeaponsData.getLong("魂力值");
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            JSONObject jsonPlayer = JSONObject.parseObject(UserData);
            JSONObject jsonPlayerData = jsonPlayer.getJSONObject("userData");
            jsonPlayerData.put("力量",jsonPlayerData.getLong("力量") + Pow);
            jsonPlayerData.put("攻击",jsonPlayerData.getLong("攻击") + Atk);
            jsonPlayerData.put("生命",jsonPlayerData.getLong("生命") + HP);
            jsonPlayerData.put("速度",jsonPlayerData.getLong("速度") + Speed);
            jsonPlayerData.put("闪避",jsonPlayerData.getLong("闪避") + Dodge);
            jsonPlayerData.put("防御",jsonPlayerData.getLong("防御") + Def);
            jsonPlayerData.put("暴击率",jsonPlayerData.getLong("暴击率") + Ct);
            jsonPlayerData.put("暴击伤害",jsonPlayerData.getLong("暴击伤害") + Ctp);
            jsonPlayerData.put("魂力值",jsonPlayerData.getLong("魂力值") + SP);
            jsonPlayer.put("userData",jsonPlayerData);
            UpdateWrapper<com.mryunqi.qimenbot.entity.User> playerUpdateWrapper = new UpdateWrapper<>();
            playerUpdateWrapper.eq("qq",userId).set("state_info",jsonPlayer.toJSONString());
            userDao.update(null,playerUpdateWrapper);
            StringBuilder data = new StringBuilder();
            data.append("┃·[力量]:").append((Pow >= 0) ? "+":"-").append(Pow).append("\n");
            data.append("┃·[攻击]:").append((Atk >= 0) ? "+":"-").append(Atk).append("\n");
            data.append("┃·[生命]:").append((HP >= 0) ? "+":"-").append(HP).append("\n");
            data.append("┃·[速度]:").append((Speed >= 0) ? "+":"-").append(Speed).append("\n");
            data.append("┃·[闪避]:").append((Dodge >= 0) ? "+":"-").append(Dodge).append("\n");
            data.append("┃·[防御]:").append((Def >= 0) ? "+":"-").append(Def).append("\n");
            data.append("┃·[魂力值]:").append((SP >= 0) ? "+":"-").append(SP).append("\n");
            data.append("┃·[暴击率]:").append((Ct >= 0) ? "+":"-").append(Ct).append("\n");
            data.append("┃·[暴击伤害]:").append((Ctp >= 0) ? "+":"-").append(Ctp).append("\n");
            String message = Weapons_EQ(UserData,Attribute,weaponsName,data);
            bot.sendMsg(event,message,false);
            return MESSAGE_IGNORE;
        }
        if (msg.matches(CmdCloseWeapons) | AliasCmd.matches(CmdCloseWeapons)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            if (AliasCmd.matches(CmdCloseWeapons)){
                msg = AliasCmd;
            }
            String PlanText = msg.replaceAll("^卸下魂导器","");
            if (PlanText.equals("")){
                bot.sendMsg(event, "请正确选择要卸下的魂导器！卸下魂导器[0/1]", false);
                return MESSAGE_IGNORE;
            }
            Function func = new Function();
            if (!func.Is_Number(PlanText)){
                bot.sendMsg(event, "请正确输入魂导器类型！卸下魂导器[0/1]", false);
                return MESSAGE_IGNORE;
            }
            UserWeaponsData userWeaponsData = new UserWeaponsData();
            QueryWrapper<com.mryunqi.qimenbot.entity.User> playerWrapper = new QueryWrapper<>();
            playerWrapper.eq("qq",userId);
            com.mryunqi.qimenbot.entity.User playerData = userService.getOne(playerWrapper);
            String WeaponAttack = String.valueOf(playerData.getWeaponsAttack());
            UpdateWrapper<com.mryunqi.qimenbot.entity.User>player = new UpdateWrapper<>();
            player.eq("qq",userId);
            if (PlanText.equals("0")){
                if (StringUtils.isBlank(WeaponAttack)){
                    bot.sendMsg(event, "您还未装备任何攻击类型魂导器！", false);
                    return MESSAGE_IGNORE;
                }
                userWeaponsData = userWeaponsDataService.getById(WeaponAttack);
                player.set("weapons_attack",null);
            }
            String WeaponDefense = String.valueOf(playerData.getWeaponsDefence());
            if (PlanText.equals("1")){
                if (StringUtils.isBlank(WeaponDefense)){
                    bot.sendMsg(event, "您还未装备任何防御类型魂导器！", false);
                    return MESSAGE_IGNORE;
                }
                userWeaponsData = userWeaponsDataService.getById(WeaponDefense);
                player.set("weapons_defence",null);
            }
            String userData = playerData.getStateInfo();
            String Attribute = user.Get_UserNowAttribute(userData);
            JSONObject jsonUserData = JSONObject.parseObject(userData);
            JSONObject jsonPlayerData = jsonUserData.getJSONObject("userData");
            String weaponsData = userWeaponsData.getDataInfo();
            String weaponsName = userWeaponsData.getName();
            JSONObject jsonWeaponsData = JSONObject.parseObject(weaponsData);
            JSONObject jsonWeaponsDataInfo = jsonWeaponsData.getJSONObject("dataInfo");
            jsonPlayerData.put("力量",jsonPlayerData.getLong("力量") - jsonWeaponsDataInfo.getLong("力量"));
            jsonPlayerData.put("攻击",jsonPlayerData.getLong("攻击") - jsonWeaponsDataInfo.getLong("攻击"));
            jsonPlayerData.put("生命",jsonPlayerData.getLong("生命") - jsonWeaponsDataInfo.getLong("血量"));
            jsonPlayerData.put("防御",jsonPlayerData.getLong("防御") - jsonWeaponsDataInfo.getLong("防御"));
            jsonPlayerData.put("速度",jsonPlayerData.getLong("速度") - jsonWeaponsDataInfo.getLong("速度"));
            jsonPlayerData.put("闪避",jsonPlayerData.getLong("闪避") - jsonWeaponsDataInfo.getLong("闪避"));
            jsonPlayerData.put("暴击率",jsonPlayerData.getLong("暴击率") - jsonWeaponsDataInfo.getLong("暴击率"));
            jsonPlayerData.put("暴击伤害",jsonPlayerData.getLong("暴击伤害") - jsonWeaponsDataInfo.getLong("暴击伤害"));
            jsonPlayerData.put("魂力值",jsonPlayerData.getLong("魂力值") - jsonWeaponsDataInfo.getLong("魂力值"));
            jsonUserData.put("userData",jsonPlayerData);
            player.set("state_info",jsonUserData.toJSONString());
            userDao.update(null,player);
            StringBuilder data = new StringBuilder();
            data.append("┃·[力量]:").append((jsonWeaponsDataInfo.getLong("力量") >= 0) ? "-":"+").append(jsonWeaponsDataInfo.getLong("力量")).append("\n");
            data.append("┃·[攻击]:").append((jsonWeaponsDataInfo.getLong("攻击") >= 0) ? "-":"+").append(jsonWeaponsDataInfo.getLong("攻击")).append("\n");
            data.append("┃·[生命]:").append((jsonWeaponsDataInfo.getLong("血量") >= 0) ? "-":"+").append(jsonWeaponsDataInfo.getLong("血量")).append("\n");
            data.append("┃·[速度]:").append((jsonWeaponsDataInfo.getLong("速度") >= 0) ? "-":"+").append(jsonWeaponsDataInfo.getLong("速度")).append("\n");
            data.append("┃·[闪避]:").append((jsonWeaponsDataInfo.getLong("闪避") >= 0) ? "-":"+").append(jsonWeaponsDataInfo.getLong("闪避")).append("\n");
            data.append("┃·[防御]:").append((jsonWeaponsDataInfo.getLong("防御") >= 0) ? "-":"+").append(jsonWeaponsDataInfo.getLong("防御")).append("\n");
            data.append("┃·[魂力值]:").append((jsonWeaponsDataInfo.getLong("魂力值") >= 0) ? "-":"+").append(jsonWeaponsDataInfo.getLong("魂力值")).append("\n");
            data.append("┃·[暴击率]:").append((jsonWeaponsDataInfo.getLong("暴击率") >= 0) ? "-":"+").append(jsonWeaponsDataInfo.getLong("暴击率")).append("\n");
            data.append("┃·[暴击伤害]:").append((jsonWeaponsDataInfo.getLong("暴击伤害") >= 0) ? "-":"+").append(jsonWeaponsDataInfo.getLong("暴击伤害")).append("\n");
            String message = CloseWeapons(userData,Attribute,weaponsName,data);
            bot.sendMsg(event,message,false);
            return MESSAGE_IGNORE;
        }
        if (msg.matches(CmdSelectWeapons) | AliasCmd.matches(CmdSelectWeapons)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            if (AliasCmd.matches(CmdSelectWeapons)){
                msg = AliasCmd;
            }
            String PlanText = msg.replaceAll("^查看魂导器","");
            if (PlanText.equals("")){
                bot.sendMsg(event, "查询指令错误！查看魂导器[ID/魂导器名]", false);
                return MESSAGE_IGNORE;
            }
            UserWeaponsData userWeaponsData = new UserWeaponsData();
            com.mryunqi.qimenbot.entity.Weapons weapons = new com.mryunqi.qimenbot.entity.Weapons();
            StringBuilder data = new StringBuilder();
            Function func = new Function();
            if (func.Is_Number(PlanText)){
                userWeaponsData = userWeaponsDataService.getById(PlanText);
                if (ObjectUtil.isNull(userWeaponsData)){
                    bot.sendMsg(event, "您没有解封的该魂导器，再检查一下要查询的魂导器ID吧！", false);
                    return MESSAGE_IGNORE;
                }
                String weaponsName = userWeaponsData.getName();
                String weaponsData = userWeaponsData.getDataInfo();
                JSONObject jsonWeaponsData = JSONObject.parseObject(weaponsData);
                JSONObject jsonWeaponsDataInfo = jsonWeaponsData.getJSONObject("dataInfo");
                JSONObject jsonWeaponsDataStatusInfo = jsonWeaponsData.getJSONObject("stateSkill");
                weapons = weaponsService.getById(weaponsName);
                Object(weapons, data, jsonWeaponsDataInfo, jsonWeaponsDataStatusInfo);
            }else {
                weapons = weaponsService.getById(PlanText);
                if (ObjectUtil.isNull(weapons)){
                    bot.sendMsg(event, "当前世界不存在该魂导器，再检查一下要查询的魂导器名吧！", false);
                    return MESSAGE_IGNORE;
                }
                String weaponsData = weapons.getDataInfo();
                JSONObject jsonWeaponsData = JSONObject.parseObject(weaponsData);
                JSONObject jsonWeaponsDataInfo = jsonWeaponsData.getJSONObject("dataInfo");
                JSONObject jsonWeaponsDataStatusInfo = jsonWeaponsData.getJSONObject("stateSkill");
                Object(weapons, data, jsonWeaponsDataInfo, jsonWeaponsDataStatusInfo);
            }
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            String message = SelectWeapons(UserData,Attribute, weapons.getName(), data);
            bot.sendMsg(event,message,false);
        }
        return MESSAGE_IGNORE;
    }

    private void Object(com.mryunqi.qimenbot.entity.Weapons weapons, StringBuilder data, JSONObject jsonWeaponsDataInfo, JSONObject jsonWeaponsDataStatusInfo) {
        String weaponsInfo = weapons.getInfo();
        int weaponsType = weapons.getType();
        data.append((weaponsType == 0) ? "类型：[攻击魂导器]\n":"类型：[防御魂导器]\n");
        data.append("说明：\n")
                .append(weaponsInfo).append("\n")
                .append("属性增幅：\n")
                //.append((jsonWeaponsDataInfo.getLong("力量") == 0) ? "·力量：":"") 等于0则不显示
                .append("·力量：").append((jsonWeaponsDataInfo.getLong("力量") >= 0) ? "+":"-").append(jsonWeaponsDataInfo.getLong("力量")).append("\n")
                .append("·攻击：").append((jsonWeaponsDataInfo.getLong("力量") >= 0) ? "+":"-").append(jsonWeaponsDataInfo.getLong("攻击")).append("\n")
                .append("·防御：").append((jsonWeaponsDataInfo.getLong("力量") >= 0) ? "+":"-").append(jsonWeaponsDataInfo.getLong("防御")).append("\n")
                .append("·速度：").append((jsonWeaponsDataInfo.getLong("力量") >= 0) ? "+":"-").append(jsonWeaponsDataInfo.getLong("速度")).append("\n")
                .append("·闪避：").append((jsonWeaponsDataInfo.getLong("力量") >= 0) ? "+":"-").append(jsonWeaponsDataInfo.getLong("闪避")).append("\n")
                .append("·魂力值：").append((jsonWeaponsDataInfo.getLong("力量") >= 0) ? "+":"-").append(jsonWeaponsDataInfo.getLong("魂力值")).append("\n")
                .append("·暴击率：").append((jsonWeaponsDataInfo.getLong("力量") >= 0) ? "+":"-").append(jsonWeaponsDataInfo.getLong("暴击率")).append("\n")
                .append("·暴击伤害：").append((jsonWeaponsDataInfo.getLong("力量") >= 0) ? "+":"-").append(jsonWeaponsDataInfo.getLong("暴击伤害")).append("\n");
        if (jsonWeaponsDataStatusInfo.size() != 0){
            data.append("附加状态：\n");
            for (String key : jsonWeaponsDataStatusInfo.keySet()){
                if ("自身附加".equals(key)){
                    String StatusSelf = jsonWeaponsDataStatusInfo.getString(key);
                    data.append((!Objects.equals(StatusSelf, "")) ? "〔自身附加〕\n"+StatusSelf.substring(0,StatusSelf.indexOf("|"))+"["+StatusSelf.substring(StatusSelf.indexOf("|")+1)+"%]\n":"");
                }
                if ("附加状态".equals(key)){
                    String StatusSelf = jsonWeaponsDataStatusInfo.getString(key);
                    data.append((!Objects.equals(StatusSelf, "")) ? "〔敌方附加〕\n"+StatusSelf.substring(0,StatusSelf.indexOf("|"))+"["+StatusSelf.substring(StatusSelf.indexOf("|")+1)+"%]\n":"");
                }
            }
        }
    }

    public Page<UserWeaponsData> SelectWeaponsListPage(int num, int PageSize){
        QueryWrapper<UserWeaponsData> userWeaponsDataQueryWrapper = new QueryWrapper<>();
        userWeaponsDataQueryWrapper.eq("qq",Integer.valueOf(userId));
        Page<UserWeaponsData> page = new Page<>(num,PageSize);
        return userWeaponsDataDao.selectPage(page,userWeaponsDataQueryWrapper);
    }
}