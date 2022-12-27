package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mikuac.shiro.annotation.Shiro;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.*;
import com.mryunqi.qimenbot.Template.GameMapTemplate;
import com.mryunqi.qimenbot.Util.ObjectUtil;
import com.mryunqi.qimenbot.dao.UserDao;
import com.mryunqi.qimenbot.entity.Goods;
import com.mryunqi.qimenbot.entity.Map;
import com.mryunqi.qimenbot.service.GoodsService;
import com.mryunqi.qimenbot.service.MapService;
import com.mryunqi.qimenbot.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.mryunqi.qimenbot.Controller.Function.Get_RandomRun;
import static com.mryunqi.qimenbot.Controller.Player.Add_GoodsTimesPerDay;
import static com.mryunqi.qimenbot.Template.UseGoodsTpl.useGoodsSuccess;

@Shiro
@Component
public class UseGoods extends BotPlugin {
    private String userId;
    private Long rise;
    private Long hp_recovery;
    private Long hp_storage;
    private Long mp_recovery;
    private Long mp_storage;
    private final JdbcTemplate jct;
    private final GoodsService goodsService;
    private final UserService userService;
    private final UserDao userDao;
    private final MapService mapService;

    public UseGoods(JdbcTemplate jct, GoodsService goodsService, UserService userService, UserDao userDao, MapService mapService) {
        this.jct = jct;
        this.goodsService = goodsService;
        this.userService = userService;
        this.userDao = userDao;
        this.mapService = mapService;
    }

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String CmdMatcher = "^使用(.*)$";
        userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        User user = new User(userId);
        Command command = new Command();
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        String AliasCmd = user.Get_UserAliasCmd(jct, msg);
        if (msg.matches(CmdMatcher) | AliasCmd.matches(CmdMatcher)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            if (AliasCmd.matches(CmdMatcher)){
                msg = AliasCmd;
            }
            String PlanText = msg.replaceAll("^使用","");
            if (PlanText.equals("")){
                bot.sendMsg(event, "不能使用空气耶，格式：使用[物品]。", false);
                return MESSAGE_IGNORE;
            }
            QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
            goodsQueryWrapper.eq("name",PlanText);
            Goods GS = goodsService.getOne(goodsQueryWrapper);
            if (ObjectUtil.isNull(GS)){
                bot.sendMsg(event, "当前世界不存在该物品，再检查一下要使用的物品吧！", false);
                return MESSAGE_IGNORE;
            }
            com.mryunqi.qimenbot.entity.User player = userService.getById(userId);
            JSONObject PlayerMaterial = JSONObject.parseObject(player.getBackpack()).getJSONObject("material");
            JSONObject PlayerConsumables = JSONObject.parseObject(player.getBackpack()).getJSONObject("consumables");
            if (!PlayerMaterial.containsKey(PlanText)){
                if (!PlayerConsumables.containsKey(PlanText)){
                    bot.sendMsg(event, "你的背包中不存在该物品，再检查一下要使用的物品吧！", false);
                    return MESSAGE_IGNORE;
                }
            }

            // 删号
            int DeleteAccount = GS.getDeleteAccount();
            if (DeleteAccount == 0){
                // 删号
                return MESSAGE_IGNORE;
            }
            // 次数使用限制
            int times_per_day = GS.getTimesPerDay();
            JSONObject PlayerGoodsData = JSONObject.parseObject(player.getGoodsData());
            if (!Times_Per_Day(PlayerGoodsData,PlanText,times_per_day)){
                bot.sendMsg(event, "该物品的今天使用次数已经上限！", false);
                return MESSAGE_IGNORE;
            }
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            int Use_scenarios = GS.getUseScenarios(); // 使用场景
            if (Use_scenarios == 1){ // 非战斗中使用
                if (user.Is_UserBattle(jct)){
                    bot.sendMsg(event, "该物品只允许在非战斗中使用！", false);
                    return MESSAGE_IGNORE;
                }
            }
            if (Use_scenarios == 2){ // 战斗中使用
                if (!user.Is_UserBattle(jct)){
                    bot.sendMsg(event, "该物品只允许在战斗中使用！", false);
                    return MESSAGE_IGNORE;
                }
            }
            if (Use_scenarios == 0){ // 所有场景
                if (useGoodsMain(bot, event, user, PlanText, GS, UserData, Attribute)) {
                    return MESSAGE_IGNORE;
                }
            }
            // 战斗中
            if (Use_scenarios == 1){
                int Effect_range = GS.getEffectRange(); // 效果范围
                if (Effect_range == 0){ // 己方单体
                    int item_type = GS.getItemType(); // 是否可消耗
                    if (item_type == 0){
                        user.Reduce_UserItemNum(jct,PlanText,"consumables",1);
                    }
                    int hit_rate = GS.getHitRate();
                    StringBuilder data = new StringBuilder();
                    JSONObject jsonUserData = JSONObject.parseObject(UserData);
                    JSONObject userData = jsonUserData.getJSONObject("userData");
                    // 概率
                    if (Get_RandomRun(hit_rate)){
                        String ability = GS.getAbility();

                        if (GS.getRise() == null){
                            rise = 0L;
                        }else {
                            rise = Long.valueOf(GS.getRise());
                        }

                        if (Objects.equals(ability, "体力")){
                            userData.put("体力",userData.getLong("体力")+rise);
                            if (rise != 0){
                                data.append("┃·[体力]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                            }
                        }
                        if (Objects.equals(ability, "力量")){
                            userData.put("力量",userData.getLong("力量")+rise);
                            if (rise != 0){
                                data.append("┃·[力量]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                            }
                        }
                        if (Objects.equals(ability, "攻击")){
                            userData.put("攻击",userData.getLong("攻击")+rise);
                            if (rise != 0){
                                data.append("┃·[攻击]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                            }
                        }
                        if (Objects.equals(ability, "生命")){
                            userData.put("生命",userData.getLong("生命")+rise);
                            if (rise != 0){
                                data.append("┃·[生命]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                            }
                        }
                        if (Objects.equals(ability, "经验")){
                            userData.put("经验",userData.getLong("经验")+rise);
                            if (rise != 0){
                                data.append("┃·[经验]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                            }
                        }
                        if (Objects.equals(ability, "速度")){
                            userData.put("速度",userData.getLong("速度")+rise);
                            if (rise != 0){
                                data.append("┃·[速度]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                            }
                        }
                        if (Objects.equals(ability, "闪避")){
                            userData.put("闪避",userData.getLong("闪避")+rise);
                            if (rise != 0){
                                data.append("┃·[闪避]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                            }
                        }
                        if (Objects.equals(ability, "防御")){
                            userData.put("防御",userData.getLong("防御")+rise);
                            if (rise != 0){
                                data.append("┃·[防御]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                            }
                        }
                        if (Objects.equals(ability, "暴击率")){
                            userData.put("暴击率",userData.getLong("暴击率")+rise);
                            if (rise != 0){
                                data.append("┃·[暴击率]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                            }
                        }
                        if (Objects.equals(ability, "精神力")){
                            userData.put("精神力",userData.getLong("精神力")+rise);
                            if (rise != 0){
                                data.append("┃·[精神力]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                            }
                        }
                        if (Objects.equals(ability, "魂力值")){
                            userData.put("魂力值",userData.getLong("魂力值")+rise);
                            if (rise != 0){
                                data.append("┃·[魂力值]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                            }
                        }
                        if (Objects.equals(ability, "暴击伤害")){
                            userData.put("暴击伤害",userData.getLong("暴击伤害")+rise);
                            if (rise != 0){
                                data.append("┃·[暴击伤害]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                            }
                        }

                        // 生命恢复

                        if (GS.getHpRecovery() == null){
                            hp_recovery = 0L;
                        }else {
                            hp_recovery = Long.valueOf(GS.getHpRecovery());
                        }
                        userData.put("当前生命",userData.getLong("当前生命")+hp_recovery);
                        if (hp_recovery != 0){
                            data.append("┃恢复").append((hp_recovery > 0) ? "+":"-").append(hp_recovery).append("点当前生命\n");
                        }

                        // 增加生命储备
                        if (GS.getHpStorage() == null){
                            hp_storage = 0L;
                        }else {
                            hp_storage = Long.valueOf(GS.getHpStorage());
                        }
                        if (userData.getLong("生命储备上限") >= userData.getLong("生命储备")){
                            userData.put("生命储备",userData.getLong("生命储备")+hp_storage);
                            if (userData.getLong("生命储备") > userData.getLong("生命储备上限")){
                                userData.put("生命储备",userData.getLong("生命储备上限"));
                                hp_storage = hp_storage - ((userData.getLong("生命储备")+hp_storage)-userData.getLong("生命储备上限"));
                            }
                            if (hp_recovery != 0){
                                data.append("┃☆增加").append((hp_storage > 0) ? "+":"-").append(hp_storage).append("点生命储备\n");
                            }
                        }

                        // 魂力恢复
                        if (GS.getHpRecovery() == null){
                            mp_recovery = 0L;
                        }else {
                            mp_recovery = Long.valueOf(GS.getHpRecovery());
                        }
                        userData.put("当前魂力值",userData.getLong("当前魂力值")+mp_recovery);
                        if (mp_recovery != 0){
                            data.append("┃恢复").append((mp_recovery > 0) ? "+":"-").append(mp_recovery).append("点当前魂力\n");
                        }

                        // 增加魂力储备
                        if (GS.getHpStorage() == null){
                            mp_storage = 0L;
                        }else {
                            mp_storage = Long.valueOf(GS.getHpStorage());
                        }
                        if (userData.getLong("魂力储备上限") >= userData.getLong("魂力储备")){
                            userData.put("魂力储备",userData.getLong("魂力储备")+mp_storage);
                            if (userData.getLong("魂力储备") > userData.getLong("魂力储备上限")){
                                userData.put("魂力储备",userData.getLong("魂力储备上限"));
                                mp_storage = mp_storage - ((userData.getLong("魂力储备")+mp_storage)-userData.getLong("魂力储备上限"));
                            }
                            if (mp_storage != 0){
                                data.append("┃★增加").append((mp_storage > 0) ? "+":"-").append(mp_storage).append("点魂力储备\n");
                            }
                        }

                        // 内涵物品
                        if(GS.getGoodsList() != null){
                            String GoodsList = GS.getGoodsList();
                            Function func = new Function();
                            String GoodsListMessage = BP.Get_HunShouDrop(jct,user,func,GoodsList);
                            data.append("[获得物品]\n").append(GoodsListMessage);
                        }
                    }else {
                        // 未命中
                        data.append("万中无一，该物品使用失败！");
                    }

                    // 物品次数+1
                    Add_GoodsTimesPerDay(userService,userDao,PlanText, Integer.parseInt(userId));

                    // 更新玩家数据
                    jsonUserData.put("userData",userData);
                    UpdateWrapper<com.mryunqi.qimenbot.entity.User> playerUpdateWrapper = new UpdateWrapper<>();
                    playerUpdateWrapper.eq("qq",userId).set("state_info",jsonUserData.toJSONString());
                    userDao.update(null,playerUpdateWrapper);

                    String message = useGoodsSuccess(UserData,Attribute,PlanText,data);
                    bot.sendMsg(event,message,false);
                    return MESSAGE_IGNORE;
                }
                if (Effect_range == 1) { // 己方全体

                }
                if (Effect_range == 2){ // 敌方

                }
            }

            // 非战斗
            if (Use_scenarios == 2){
                if (useGoodsMain(bot, event, user, PlanText, GS, UserData, Attribute)) return MESSAGE_IGNORE;
            }

        }
        return MESSAGE_IGNORE;
    }

    private boolean useGoodsMain(@NotNull Bot bot, @NotNull AnyMessageEvent event, User user, String planText, Goods GS, String UserData, String attribute) {
        int Effect_range = GS.getEffectRange(); // 效果范围
        if (Effect_range == 0){ // 己方单体
            int item_type = GS.getItemType(); // 是否可消耗
            if (item_type == 0){
                user.Reduce_UserItemNum(jct, planText,"consumables",1);
            }
            int hit_rate = GS.getHitRate();
            StringBuilder data = new StringBuilder();
            JSONObject jsonUserData = JSONObject.parseObject(UserData);
            JSONObject userData = jsonUserData.getJSONObject("userData");
            // 概率
            if (Get_RandomRun(hit_rate)){
                String ability = GS.getAbility();

                if (GS.getRise() == null){
                    rise = 0L;
                }else {
                    rise = Long.valueOf(GS.getRise());
                }

                if (Objects.equals(ability, "体力")){
                    userData.put("体力",userData.getLong("体力")+rise);
                    if (rise != 0){
                        data.append("┃·[体力]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                    }
                }
                if (Objects.equals(ability, "力量")){
                    userData.put("力量",userData.getLong("力量")+rise);
                    if (rise != 0){
                        data.append("┃·[力量]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                    }
                }
                if (Objects.equals(ability, "攻击")){
                    userData.put("攻击",userData.getLong("攻击")+rise);
                    if (rise != 0){
                        data.append("┃·[攻击]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                    }
                }
                if (Objects.equals(ability, "生命")){
                    userData.put("生命",userData.getLong("生命")+rise);
                    if (rise != 0){
                        data.append("┃·[生命]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                    }
                }
                if (Objects.equals(ability, "经验")){
                    userData.put("经验",userData.getLong("经验")+rise);
                    if (rise != 0){
                        data.append("┃·[经验]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                    }
                }
                if (Objects.equals(ability, "速度")){
                    userData.put("速度",userData.getLong("速度")+rise);
                    if (rise != 0){
                        data.append("┃·[速度]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                    }
                }
                if (Objects.equals(ability, "闪避")){
                    userData.put("闪避",userData.getLong("闪避")+rise);
                    if (rise != 0){
                        data.append("┃·[闪避]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                    }
                }
                if (Objects.equals(ability, "防御")){
                    userData.put("防御",userData.getLong("防御")+rise);
                    if (rise != 0){
                        data.append("┃·[防御]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                    }
                }
                if (Objects.equals(ability, "暴击率")){
                    userData.put("暴击率",userData.getLong("暴击率")+rise);
                    if (rise != 0){
                        data.append("┃·[暴击率]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                    }
                }
                if (Objects.equals(ability, "精神力")){
                    userData.put("精神力",userData.getLong("精神力")+rise);
                    if (rise != 0){
                        data.append("┃·[精神力]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                    }
                }
                if (Objects.equals(ability, "魂力值")){
                    userData.put("魂力值",userData.getLong("魂力值")+rise);
                    if (rise != 0){
                        data.append("┃·[魂力值]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                    }
                }
                if (Objects.equals(ability, "暴击伤害")){
                    userData.put("暴击伤害",userData.getLong("暴击伤害")+rise);
                    if (rise != 0){
                        data.append("┃·[暴击伤害]: 永久").append((rise > 0) ? "+":"-").append(rise).append("\n");
                    }
                }

                // 生命恢复

                if (GS.getHpRecovery() == null){
                    hp_recovery = 0L;
                }else {
                    hp_recovery = Long.valueOf(GS.getHpRecovery());
                }
                userData.put("当前生命",userData.getLong("当前生命")+hp_recovery);
                if (hp_recovery != 0){
                    data.append("┃恢复").append((hp_recovery > 0) ? "+":"-").append(hp_recovery).append("点当前生命\n");
                }

                // 增加生命储备
                if (GS.getHpStorage() == null){
                    hp_storage = 0L;
                }else {
                    hp_storage = Long.valueOf(GS.getHpStorage());
                }
                if (userData.getLong("生命储备上限") >= userData.getLong("生命储备")){
                    userData.put("生命储备",userData.getLong("生命储备")+hp_storage);
                    if (userData.getLong("生命储备") > userData.getLong("生命储备上限")){
                        userData.put("生命储备",userData.getLong("生命储备上限"));
                        hp_storage = hp_storage - ((userData.getLong("生命储备")+hp_storage)-userData.getLong("生命储备上限"));
                    }
                    if (hp_recovery != 0){
                        data.append("┃☆增加").append((hp_storage > 0) ? "+":"-").append(hp_storage).append("点生命储备\n");
                    }
                }

                // 魂力恢复
                if (GS.getHpRecovery() == null){
                    mp_recovery = 0L;
                }else {
                    mp_recovery = Long.valueOf(GS.getHpRecovery());
                }
                userData.put("当前魂力值",userData.getLong("当前魂力值")+mp_recovery);
                if (mp_recovery != 0){
                    data.append("┃恢复").append((mp_recovery > 0) ? "+":"-").append(mp_recovery).append("点当前魂力\n");
                }

                // 增加魂力储备
                if (GS.getHpStorage() == null){
                    mp_storage = 0L;
                }else {
                    mp_storage = Long.valueOf(GS.getHpStorage());
                }
                if (userData.getLong("魂力储备上限") >= userData.getLong("魂力储备")){
                    userData.put("魂力储备",userData.getLong("魂力储备")+mp_storage);
                    if (userData.getLong("魂力储备") > userData.getLong("魂力储备上限")){
                        userData.put("魂力储备",userData.getLong("魂力储备上限"));
                        mp_storage = mp_storage - ((userData.getLong("魂力储备")+mp_storage)-userData.getLong("魂力储备上限"));
                    }
                    if (mp_storage != 0){
                        data.append("┃★增加").append((mp_storage > 0) ? "+":"-").append(mp_storage).append("点魂力储备\n");
                    }
                }

                // 普通传送
                String tp_map = GS.getTp();
                if (tp_map != null){
                    if (user.Is_UserPVE(jct)){
                        bot.sendMsg(event,"您当前正在战斗中，请先结束战斗后再尝试传送！",false);
                    }else {
                        QueryWrapper<Map> mapQueryWrapper = new QueryWrapper<>();
                        mapQueryWrapper.eq("map",tp_map);
                        Map map = mapService.getOne(mapQueryWrapper);
                        if (ObjectUtil.isNull(map)){
                            bot.sendMsg(event, "该物品要传送的地图不存在！", false);
                        }
                        bot.sendMsg(event, "您使用了【"+ planText +"】，即将传送至["+tp_map+"]...", false);
                        GameNPC npc = new GameNPC();
                        GameMap gamemap = new GameMap();
                        GameHunshou hunshou = new GameHunshou();
                        String NPC = npc.get_NPC_Name_Template(jct, tp_map);
                        String Hunshou = hunshou.Get_Hunshou_Name_Template(jct, tp_map);
                        String Gamemap = gamemap.Get_MapAroundName(jct, tp_map);
                        GameMapTemplate gamemaptemplate = new GameMapTemplate();
                        String MapTransport = gamemap.Get_MapTransport(jct, tp_map);
                        String tp_message = gamemaptemplate.GetGameMapTemplate(UserData,tp_map,NPC,Hunshou,Gamemap, attribute,MapTransport);
                        bot.sendMsg(event, tp_message, false);
                        user.Set_UserNowMap(jct, tp_map);
                    }
                }
                // 内涵物品
                if(GS.getGoodsList() != null){
                    String GoodsList = GS.getGoodsList();
                    Function func = new Function();
                    String GoodsListMessage = BP.Get_HunShouDrop(jct,user,func,GoodsList);
                    data.append("[获得物品]\n").append(GoodsListMessage);
                }

            }else {
                // 未命中
                data.append("万中无一，该物品使用失败！");
            }

            // 物品次数+1
            Add_GoodsTimesPerDay(userService,userDao, planText, Integer.parseInt(userId));

            // 更新玩家数据
            jsonUserData.put("userData",userData);
            UpdateWrapper<com.mryunqi.qimenbot.entity.User> playerUpdateWrapper = new UpdateWrapper<>();
            playerUpdateWrapper.eq("qq",userId).set("state_info",jsonUserData.toJSONString());
            userDao.update(null,playerUpdateWrapper);

            String message = useGoodsSuccess(UserData, attribute, planText,data);
            bot.sendMsg(event,message,false);
            return true;
        }
        if (Effect_range == 1) { // 己方全体

        }
        if (Effect_range == 2){ // 敌方

        }
        return false;
    }

    public Boolean Times_Per_Day(JSONObject PlayerGoodsData,String GoodsName,int times_per_day){
        if (times_per_day == 0){
            return true;
        }
        if (PlayerGoodsData == null){
            return true;
        }
        if (!PlayerGoodsData.containsKey(GoodsName)){
            return true;
        }
        int goodsDay = PlayerGoodsData.getIntValue(GoodsName);
        return goodsDay < times_per_day;
    }
}
