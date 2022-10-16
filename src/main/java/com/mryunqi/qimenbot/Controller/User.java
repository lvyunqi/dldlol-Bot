package com.mryunqi.qimenbot.Controller;

import org.springframework.jdbc.core.JdbcTemplate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * @author mryunqi
 * @since 2022-6-22
 * @version 1.0
 */

public class User {
    String QQ;

    public User(String QQ) {
        this.QQ = QQ;
    }

    /* 获取玩家数据 */
    public String Get_UserData(JdbcTemplate jct) {
        String sql = "SELECT state_info FROM user WHERE qq=" + User.this.QQ;
        return jct.queryForObject(sql, String.class);
    }

    /* 获取特定alias */
    public String Get_UserAlias(JdbcTemplate jct, String alias) {
        String sql = "SELECT alias FROM user WHERE qq=" + User.this.QQ;
        String Alias = jct.queryForObject(sql, String.class);
        if (Alias == null) return null;
        JSONObject obj = JSON.parseObject(Alias);
        if (obj.containsKey(alias)) return obj.getString(alias);
        else return null;
    }

    /* 获取alias */
    public String Get_UserAllAlias(JdbcTemplate jct){
        String sql = "SELECT alias FROM user WHERE qq=" + User.this.QQ;
        return jct.queryForObject(sql, String.class);
    }

    /* 修改用户alias */
    public void Set_UserAlias(JdbcTemplate jct, String alias){
        String sql = "UPDATE user SET alias =? WHERE qq =?";
        jct.update(sql, alias,User.this.QQ);
    }

    /* 判断是否存在此玩家 */
    public boolean Is_UserExist(JdbcTemplate jct) {
        String sql = "SELECT qq FROM user";
        List<String> list = jct.queryForList(sql, String.class);
        return !list.contains(String.valueOf(User.this.QQ));
    }

    /* 判断玩家是否觉醒武魂 */
    public boolean Is_UserAwake(JdbcTemplate jct) {
        String sql = "SELECT state_info FROM user WHERE qq=" + User.this.QQ;
        return jct.queryForObject(sql, String.class) == null;
    }

    /* 获取玩家等级称号*/
    public String Get_UserLevelName(int lv) {
        switch (lv) {
            case 0:
                return "魂士";
            case 1:
                return "一环魂师";
            case 2:
                return "二环大魂师";
            case 3:
                return "三环魂尊";
            case 4:
                return "三环魂宗";
            case 5:
                return "五环魂王";
            case 6:
                return "六环魂帝";
            case 7:
                return "七环魂圣";
            case 8:
                return "八环魂斗罗";
            case 9:
                return "九环封号斗罗";
            case 10:
                return "三级神祇";
            case 11:
                return "二级神祇";
            case 12:
                return "一级神祇";
            case 13:
                return "神界执法者";
            case 14:
                return "至高神";
            case 15:
                return "神王";
            default:
                return "？？？";
        }
    }

    /* 获取玩家下一阶精神力 */
    public int Get_UserSpirit(int SP) {
        if (SP <= 99) return 100;
        else if (SP <= 499) return 500;
        else if (SP <= 4999) return 5000;
        else if (SP <= 19999) return 20000;
        else if (SP <= 49999) return 50000;
        else return 9999999;
    }

    /* 获取玩家精神力等阶名称 */
    public String Get_UserSpiritName(int SP) {
        if (SP <= 99) return "灵元境";
        else if (SP <= 499) return "灵通境";
        else if (SP <= 4999) return "灵海境";
        else if (SP <= 19999) return "灵渊境";
        else if (SP <= 49999) return "灵域境";
        else return "神元境";
    }

    /* 获取玩家魂导器名称 */
    public String Get_UserWeaponName(JdbcTemplate jct, int WeaponType) {
        if (WeaponType == 0){
            String sql = "SELECT weapons_attack FROM user WHERE qq=" + User.this.QQ;
            String weapons_attack = jct.queryForObject(sql, String.class);
            if (weapons_attack == null){
                return "";
            }
            String sql2 = "SELECT name FROM user_weapons_data WHERE id=" + weapons_attack;
            return jct.queryForObject(sql2, String.class);
        }
        if (WeaponType == 1){
            String sql = "SELECT weapons_defence FROM user WHERE qq=" + User.this.QQ;
            String weapons_defense = jct.queryForObject(sql, String.class);
            if (weapons_defense == null){
                return "";
            }
            String sql2 = "SELECT name FROM user_weapons_data WHERE id=" + weapons_defense;
            return jct.queryForObject(sql2, String.class);
        }
        return "";
    }

    /* 初始化玩家账号 */
    public void Init_User(JdbcTemplate jct,String Name,String Sex,String DefaultMap) {
        String sql = "INSERT INTO user (qq,name,sex,nowmap) VALUES (?,?,?,?)";
        jct.update(sql,User.this.QQ,Name,Sex,DefaultMap);
    }

    /* 获取所有玩家的名字 */
    public String Get_UserAllName(JdbcTemplate jct){
        String sql = "SELECT name FROM user";
        List<Map<String,Object>> list = jct.queryForList(sql);
        return list.toString();
    }

    /* 获取玩家当前属性种类 */
    public String Get_UserNowAttribute(String UserData){
        JSONObject obj = JSON.parseObject(UserData);
        return obj.getJSONObject("userInfo").getString("当前属性");
    }

    /* 获取玩家武魂数量 */
    public int Get_UserAttributeNum(String UserData){
        JSONObject obj = JSON.parseObject(UserData);
        return Integer.parseInt(obj.getJSONObject("userInfo").getString("武魂数量"));
    }
    /* 修改玩家属性 */
    public void Set_UserData(JdbcTemplate jct,String UserData){
        String sql = "UPDATE user SET state_info=? WHERE qq =?";
        jct.update(sql,UserData,User.this.QQ);
    }

    /* 获取玩家名字 */
    public String Get_UserName(JdbcTemplate jct){
        String sql = "SELECT name FROM user WHERE qq="+User.this.QQ;
        return jct.queryForObject(sql,String.class);
    }

    /* 获取玩家性别 */
    public String Get_UserSex(JdbcTemplate jct){
        String sql = "SELECT sex FROM user WHERE qq="+User.this.QQ;
        return jct.queryForObject(sql,String.class);
    }
    /* 写玩家数据 */
    public void Set_UserData(JdbcTemplate jct,String Data,String DbType){
        String sql = "UPDATE user SET "+DbType+"=? WHERE qq =?";
        jct.update(sql,Data,User.this.QQ);
    }

    /* 获取玩家当前地图 */
    public String Get_UserNowMap(JdbcTemplate jct){
        String sql = "SELECT nowmap FROM user WHERE qq="+User.this.QQ;
        return jct.queryForObject(sql,String.class);
    }

    /* 判断玩家是否存在msg对应的alias */
    public boolean Is_UserHaveAlias(JdbcTemplate jct,String CmdMsg,String msg){
        String sql = String.format("SELECT alias FROM user WHERE qq=%s",User.this.QQ);
        if (Is_UserExist(jct)){
            return false;
        }
        String alias = jct.queryForObject(sql,String.class);
        if (alias == null){
            return false;
        }
        JSONObject obj = JSON.parseObject(alias);
        if (!obj.containsKey(CmdMsg)){
            return false;
        }
        return obj.getJSONObject(CmdMsg).containsKey(msg);
    }

    /* 获取玩家alias对应的指令 */
    public String Get_UserAliasCmd(JdbcTemplate jct,String msg){
        String sql = String.format("SELECT alias FROM user WHERE qq=%s",User.this.QQ);
        if (Is_UserExist(jct)){
            return "";
        }
        String alias = jct.queryForObject(sql,String.class);
        if (alias == null){
            return "";
        }
        JSONObject obj = JSON.parseObject(alias);
        // 遍历所有指令
        for (String key : obj.keySet()) {
            JSONObject obj2 = obj.getJSONObject(key);
            // 遍历所有alias
            for (String key2 : obj2.keySet()) {
                if (key2.equals(msg)){
                    return key;
                }
            }
        }
        return "";
    }

    /* 获取玩家物品名对应的物品类型 */
    public String Get_UserItemType(String ItemName,String BackPack){
        JSONObject obj = JSON.parseObject(BackPack);
        for (String key : obj.keySet()) {
            JSONObject obj2 = obj.getJSONObject(key);
            for (String key2 : obj2.keySet()) {
                if (key2.equals(ItemName)){
                    return key;
                }
            }
        }
        return "";
    }

    /* 修改玩家当前地图 */
    public void Set_UserNowMap(JdbcTemplate jct,String Map){
        String sql = "UPDATE user SET nowmap=? WHERE qq =?";
        jct.update(sql,Map,User.this.QQ);
    }

    /* 获取玩家背包数据 */
    public String Get_UserBagData(JdbcTemplate jct){
        String sql = "SELECT backpack FROM user WHERE qq="+User.this.QQ;
        return jct.queryForObject(sql,String.class);
    }

    /* 获取玩家钱包数据 */
    public String Get_UserWalletData(JdbcTemplate jct){
        String sql = "SELECT money FROM user WHERE qq="+User.this.QQ;
        return jct.queryForObject(sql,String.class);
    }

    /* 获取玩家剩余的货币 */
    public int Get_UserMoney(JdbcTemplate jct,String Currency){
        String MoneyData = Get_UserWalletData(jct);
        JSONObject obj = JSON.parseObject(MoneyData);
        return obj.getIntValue(Currency);
    }

    /* 获取玩家某物品数量 */
    public int Get_UserItemNum(JdbcTemplate jct,String ItemName,String ItemType){
        String BackPackData = Get_UserBagData(jct);
        JSONObject obj = JSON.parseObject(BackPackData);
        JSONObject ItemList = obj.getJSONObject(ItemType);
        String InitItem = ItemList.getString(ItemName);
        if (ItemType.equals("consumables")) {
            return Integer.parseInt(InitItem);
        }
        if (ItemType.equals("material")) {
            return Integer.parseInt(InitItem);
        }
        if (ItemType.equals("weapons")) {
            int index = InitItem.indexOf("|");
            return Integer.parseInt(InitItem.substring(index+1));
        }
        return 0;
    }

    /* 设置玩家钱包数据 */
    public void Set_UserWalletData(JdbcTemplate jct,int Count,String Currency){
        String MoneyData = Get_UserWalletData(jct);
        JSONObject obj = JSON.parseObject(MoneyData);
        obj.put(Currency,Count);
        String sql = "UPDATE user SET money=? WHERE qq =?";
        jct.update(sql,obj.toJSONString(),User.this.QQ);
    }

    /* 增加货币数据 */
    public void Add_UserMoney(JdbcTemplate jct,int Count,String Currency){
        int Money = Get_UserMoney(jct,Currency);
        Money += Count;
        Set_UserWalletData(jct,Money,Currency);
    }

    /**
     * 减少货币数据
     * @param jct 数据库操作类
     * @param Count 要减少的数量
     * @param Currency 货币类型
     */
    public void Sub_UserMoney(JdbcTemplate jct,int Count,String Currency){
        int Money = Get_UserMoney(jct,Currency);
        Money -= Count;
        Set_UserWalletData(jct,Money,Currency);
    }

    /* 减少玩家背包某物品数量 */
    public void Reduce_UserItemNum(JdbcTemplate jct,String ItemName,String ItemType,int Count){
        String BackPackData = Get_UserBagData(jct);
        JSONObject obj = JSON.parseObject(BackPackData);
        JSONObject ItemList = obj.getJSONObject(ItemType);
        String InitItem = ItemList.getString(ItemName);
        if (ItemType.equals("consumables")) {
            int num = Integer.parseInt(InitItem)-Count;
            if (num == 0) {
                ItemList.remove(ItemName);
            } else {
                ItemList.put(ItemName,num);
            }
        }
        if (ItemType.equals("material")) {
            int num = Integer.parseInt(InitItem)-Count;
            if (num == 0) {
                ItemList.remove(ItemName);
            } else {
                ItemList.put(ItemName,num);
            }
        }
        if (ItemType.equals("weapons")) {
            int index = InitItem.indexOf("|");
            int num = Integer.parseInt(InitItem.substring(index+1))-Count;
            if (num == 0) {
                ItemList.remove(ItemName);
            } else {
                ItemList.put(ItemName,InitItem.substring(0,index)+"|"+num);
            }
        }
        obj.put(ItemType,ItemList);
        String sql = "UPDATE user SET backpack=? WHERE qq =?";
        jct.update(sql,obj.toJSONString(),User.this.QQ);
    }

    /* 增加玩家背包某物品数量 */
    public void Add_UserItemNum(JdbcTemplate jct,String ItemName,String ItemType,int Count){
        String BackPackData = Get_UserBagData(jct);
        JSONObject obj = JSON.parseObject(BackPackData);
        JSONObject ItemList = obj.getJSONObject(ItemType);
        // 如果该物品不存在，则新建一个物品
        if (ItemList.getString(ItemName) == null){
            if (ItemType.equals("consumables")) {
                ItemList.put(ItemName,Count);
            }
            if (ItemType.equals("material")) {
                ItemList.put(ItemName,Count);
            }
            if (ItemType.equals("weapons")) {
                Weapons w = new Weapons();
                String WeaponsType = w.Get_WeaponsType(jct,ItemName);
                ItemList.put(ItemName,WeaponsType+"|"+Count);
            }
        } else {
            // 如果该物品存在，则增加数量
            if (ItemType.equals("consumables")) {
                int num = Integer.parseInt(ItemList.getString(ItemName))+Count;
                ItemList.put(ItemName,num);
            }
            if (ItemType.equals("material")) {
                int num = Integer.parseInt(ItemList.getString(ItemName))+Count;
                ItemList.put(ItemName,num);
            }
            if (ItemType.equals("weapons")) {
                int index = ItemList.getString(ItemName).indexOf("|");
                int num = Integer.parseInt(ItemList.getString(ItemName).substring(index+1))+Count;
                ItemList.put(ItemName,ItemList.getString(ItemName).substring(0,index)+"|"+num);
            }
        }
        obj.put(ItemType,ItemList);
        String sql = "UPDATE user SET backpack=? WHERE qq =?";
        jct.update(sql,obj.toJSONString(),User.this.QQ);
    }

    /* 判断玩家是否被禁止战斗 */
    public boolean Is_UserForbidden(JdbcTemplate jct){
        String sql = "SELECT nofight FROM user WHERE qq="+User.this.QQ;
        return jct.queryForObject(sql, String.class) != null;
    }

    /* 判断玩家是否存在PVE战斗 */
    public boolean Is_UserPVE(JdbcTemplate jct){
        String sql = "SELECT batid FROM user WHERE qq="+User.this.QQ;
        String batid = jct.queryForObject(sql, String.class);
        if (batid == null) {
            return false;
        }
        JSONObject obj = JSON.parseObject(batid);
        if (obj.getString("pve") != null) {
            return true;
        }
        return false;
    }

    /* 判断玩家是PVE战斗还是PVP战斗还是副本战斗 */
    public String Is_UserPVEorPVPorCopy(JdbcTemplate jct){
        String sql = "SELECT batid FROM user WHERE qq="+User.this.QQ;
        String batid = jct.queryForObject(sql, String.class);
        if (batid == null) {
            return "";
        }
        JSONObject obj = JSON.parseObject(batid);
        if (obj.getString("pve") != null) {
            return "pve";
        }
        if (obj.getString("pvp") != null) {
            return "pvp";
        }
        if (obj.getString("copy") != null) {
            return "copy";
        }
        return "";
    }

    /* 判断玩家是否存在战斗 */
    public boolean Is_UserBattle(JdbcTemplate jct){
        String sql = "SELECT batid FROM user WHERE qq="+User.this.QQ;
        String batid = jct.queryForObject(sql, String.class);
        if (batid == null) {
            return false;
        }
        JSONObject obj = JSON.parseObject(batid);
        // 如果存在pve战斗，则返回true
        if (obj.getString("pve") != null) {
            return true;
        }
        // 如果存在pvp战斗，则返回true
        if (obj.getString("pvp") != null) {
            return true;
        }
        // 如果存在副本战斗，则返回true
        if (obj.getString("copy") != null) {
            return true;
        }
        return false;
    }

    /* 获取玩家PVE战斗ID */
    public String Get_UserPVEId(JdbcTemplate jct){
        String sql = "SELECT batid FROM user WHERE qq="+User.this.QQ;
        String batid = jct.queryForObject(sql, String.class);
        if (batid == null) {
            return null;
        }
        // 如果存在pve战斗，则返回pve战斗ID
        JSONObject obj = JSON.parseObject(batid);
        return obj.getString("pve");
    }

    /* 获取玩家战斗数据 */
    public String Get_UserBattleData(JdbcTemplate jct){
        String sql = "SELECT batid FROM user WHERE qq="+User.this.QQ;
        return jct.queryForObject(sql, String.class);
    }

    /* 获取玩家当前生命 */
    public int Get_UserNowHP(JdbcTemplate jct){
        String UserData = Get_UserData(jct);
        JSONObject map = JSON.parseObject(UserData);
        return Integer.parseInt(map.getJSONObject("userData").getString("当前生命"));
    }

    /* 获取玩家生命总上限 */
    public int Get_UserMaxHP(JdbcTemplate jct){
        String UserData = Get_UserData(jct);
        JSONObject map = JSON.parseObject(UserData);
        return Integer.parseInt(map.getJSONObject("userData").getString("生命"));
    }

    /* 增加玩家当前生命 */
    public void Add_UserNowHP(JdbcTemplate jct,int HP){
        String UserData = Get_UserData(jct);
        JSONObject map = JSON.parseObject(UserData);
        int nowHP = Integer.parseInt(map.getJSONObject("userData").getString("当前生命"));
        int maxHP = Integer.parseInt(map.getJSONObject("userData").getString("生命"));
        map.getJSONObject("userData").put("当前生命",nowHP+HP);
        if (nowHP+HP > maxHP) {
            map.getJSONObject("userData").put("当前生命",maxHP);
        }
        String sql = "UPDATE user SET state_info=? WHERE qq =?";
        jct.update(sql,map.toJSONString(),User.this.QQ);
    }

    /* 减少玩家当前生命 */
    public void Sub_UserNowHP(JdbcTemplate jct,int HP){
        String UserData = Get_UserData(jct);
        JSONObject map = JSON.parseObject(UserData);
        int nowHP = Integer.parseInt(map.getJSONObject("userData").getString("当前生命"));
        map.getJSONObject("userData").put("当前生命",nowHP-HP);
        if (nowHP-HP < 0) {
            map.getJSONObject("userData").put("当前生命",0);
        }
        String sql = "UPDATE user SET state_info=? WHERE qq =?";
        jct.update(sql,map.toJSONString(),User.this.QQ);
    }

    /* 判断玩家是否死亡 */
    public boolean Is_UserDead(JdbcTemplate jct){
        String UserData = Get_UserData(jct);
        JSONObject map = JSON.parseObject(UserData);
        return Integer.parseInt(map.getJSONObject("userData").getString("当前生命")) <= 0;
    }

    /* 设置战斗数据 */
    public void Set_UserBatData(JdbcTemplate jct,String BatId,String BatType){
        String BatData = Get_UserBattleData(jct);
        if (BatData == null) {
            JSONObject obj = new JSONObject();
            obj.put(BatType,BatId);
            String sql = "UPDATE user SET batid=? WHERE qq =?";
            jct.update(sql,obj.toJSONString(),User.this.QQ);
        } else {
            JSONObject obj = JSON.parseObject(BatData);
            obj.put(BatType,BatId);
            String sql = "UPDATE user SET batid=? WHERE qq =?";
            jct.update(sql,obj.toJSONString(),User.this.QQ);
        }
    }

    /* 减少玩家体力 */
    public void Reduce_UserCon(JdbcTemplate jct,int con){
        String UserData = Get_UserData(jct);
        JSONObject map = JSON.parseObject(UserData);
        int nowCon = Integer.parseInt(map.getJSONObject("userData").getString("体力"));
        nowCon -= con;
        if (nowCon < 0) {
            nowCon = 0;
        }
        map.getJSONObject("userData").put("体力",nowCon);
        String sql = "UPDATE user SET state_info=? WHERE qq =?";
        jct.update(sql,map.toJSONString(),User.this.QQ);
    }

    /* 判断玩家当前是否需要魂环 */
    public boolean Is_UserNeedHp(JdbcTemplate jct,int NextEXP){
        String UserData = Get_UserData(jct);
        JSONObject map = JSON.parseObject(UserData);
        int Level = Integer.parseInt(map.getJSONObject("userData").getString("等级"));
        int UserEXP = Integer.parseInt(map.getJSONObject("userData").getString("经验"));
        // 判断Level是否是10的倍数
        if (Level % 10 == 0) {
            // 判断玩家经验是否达到升级所需经验
            return UserEXP >= NextEXP;
        }
        return false;
    }

    /* 更新经验 */
    public void Update_UserEXP(JdbcTemplate jct,int AddEXP,int NextEXP){
        String UserData = Get_UserData(jct);
        JSONObject map = JSON.parseObject(UserData);
        int UserEXP = Integer.parseInt(map.getJSONObject("userData").getString("经验"));
        int Level = Integer.parseInt(map.getJSONObject("userData").getString("等级"));
        int UserPower = Integer.parseInt(map.getJSONObject("userData").getString("力量"));
        int UserAtk = Integer.parseInt(map.getJSONObject("userData").getString("攻击"));
        int UserMP = Integer.parseInt(map.getJSONObject("userData").getString("魂力值"));
        int UserHP = Integer.parseInt(map.getJSONObject("userData").getString("生命"));
        int UserDef = Integer.parseInt(map.getJSONObject("userData").getString("防御"));
        int NewEXP = UserEXP + AddEXP;
        if (Level % 10 == 0){
            map.getJSONObject("userData").put("经验",NewEXP);
            String sql = "UPDATE user SET state_info=? WHERE qq =?";
            jct.update(sql,map.toJSONString(),User.this.QQ);
            return;
        }
        if (NewEXP >= NextEXP) {
            map.getJSONObject("userData").put("经验",NewEXP - NextEXP);
            map.getJSONObject("userData").put("等级",Level + 1);
            map.getJSONObject("userData").put("攻击",Get_UserAttackUp(Level,UserAtk));
            map.getJSONObject("userData").put("力量",Get_UserPowerUp(Level,UserPower));
            map.getJSONObject("userData").put("魂力值",Get_UserMPUp(Level,UserMP));
            map.getJSONObject("userData").put("生命",Get_UserHPUp(Level,UserHP));
            map.getJSONObject("userData").put("防御",Get_UserDefenseUp(Level,UserDef));
            map.getJSONObject("userData").put("当前生命",Get_UserHPUp(Level,UserHP));
            map.getJSONObject("userData").put("当前魂力值",Get_UserMPUp(Level,UserMP));
        } else {
            map.getJSONObject("userData").put("经验",NewEXP);
        }
        String sql = "UPDATE user SET state_info=? WHERE qq =?";
        jct.update(sql,map.toJSONString(),User.this.QQ);
    }

    /* 攻击升级增幅计算 */
    public int Get_UserAttackUp(int lv,int pr){
        int a = (int) (1.21*lv + 1.01*pr);
        int b = (int) (1.11*lv);
        return ((Function.Get_Random_Range(a,a+b))+(Function.Get_Random_Range(a,a+b)))/2;
    }
    /* 防御升级增幅计算 */
    public int Get_UserDefenseUp(int lv,int def){
        int a = (int) (1.31*lv + 1.02*def);
        int b = (int) (1.11*lv);
        return ((Function.Get_Random_Range(a,a+b))+(Function.Get_Random_Range(a,a+b)))/2;
    }
    /* 生命升级增幅计算 */
    public int Get_UserHPUp(int lv,int hp){
        int a = (int) (1.41*lv + 1.01*hp);
        int b = (int) (1.11*lv);
        return ((Function.Get_Random_Range(a,a+b))+(Function.Get_Random_Range(a,a+b)))/2;
    }
    /* 力量升级增幅计算 */
    public int Get_UserPowerUp(int lv,int pow){
        int a = (int) (1.41*lv + 1.02*pow);
        int b = (int) (1.11*lv);
        return ((Function.Get_Random_Range(a,a+b))+(Function.Get_Random_Range(a,a+b)))/2;
    }
    /* MP升级增幅计算 */
    public int Get_UserMPUp(int lv,int mp){
        int a = (int) (1.41*lv + 1.01*mp);
        int b = (int) (1.11*lv);
        return ((Function.Get_Random_Range(a,a+b))+(Function.Get_Random_Range(a,a+b)))/2;
    }

    /* 附加魂环升级 */
    public void Update_UserLevelAddHuan(JdbcTemplate jct){
        String UserData = Get_UserData(jct);
        JSONObject map = JSON.parseObject(UserData);
        int Level = Integer.parseInt(map.getJSONObject("userData").getString("等级"));
        int UserEXP = Integer.parseInt(map.getJSONObject("userData").getString("经验"));
        int UserPower = Integer.parseInt(map.getJSONObject("userData").getString("力量"));
        int UserAtk = Integer.parseInt(map.getJSONObject("userData").getString("攻击"));
        int UserMP = Integer.parseInt(map.getJSONObject("userData").getString("魂力值"));
        int UserHP = Integer.parseInt(map.getJSONObject("userData").getString("生命"));
        int UserDef = Integer.parseInt(map.getJSONObject("userData").getString("防御"));
        int NextEXP = Function.Get_NextLevelExp(Level, Integer.parseInt(Command.Get_CommandExpUp(jct)));
        UserPower = (int) (Get_UserPowerUp(Level,UserPower) * 1.5);
        UserAtk = (int) (Get_UserAttackUp(Level,UserAtk) * 1.5);
        UserMP = (int) (Get_UserMPUp(Level,UserMP) * 1.5);
        UserHP = (int) (Get_UserHPUp(Level,UserHP) * 1.5);
        UserDef = (int) (Get_UserDefenseUp(Level,UserDef) * 1.5);
        while (UserEXP >= NextEXP) {
            Level++;
            UserEXP -= NextEXP;
            NextEXP = Function.Get_NextLevelExp(Level, Integer.parseInt(Command.Get_CommandExpUp(jct)));
            UserPower = Get_UserPowerUp(Level,UserPower);
            UserAtk = Get_UserAttackUp(Level,UserAtk);
            UserMP = Get_UserMPUp(Level,UserMP);
            UserHP = Get_UserHPUp(Level,UserHP);
            UserDef = Get_UserDefenseUp(Level,UserDef);
        }
        map.getJSONObject("userData").put("等级",Level);
        map.getJSONObject("userData").put("经验",UserEXP);
        map.getJSONObject("userData").put("力量",UserPower);
        map.getJSONObject("userData").put("攻击",UserAtk);
        map.getJSONObject("userData").put("魂力值",UserMP);
        map.getJSONObject("userData").put("生命",UserHP);
        map.getJSONObject("userData").put("防御",UserDef);
        map.getJSONObject("userData").put("当前生命",UserHP);
        map.getJSONObject("userData").put("当前魂力值",UserMP);
        String sql = "UPDATE user SET state_info=? WHERE qq =?";
        jct.update(sql,map.toJSONString(),User.this.QQ);

    }

    /* 附加魂环不升级 */
    public void Update_UserLevel_NO_AddHuan(JdbcTemplate jct){
        String UserData = Get_UserData(jct);
        JSONObject map = JSON.parseObject(UserData);
        int Level = Integer.parseInt(map.getJSONObject("userData").getString("等级"));
        int UserEXP = Integer.parseInt(map.getJSONObject("userData").getString("经验"));
        int UserPower = Integer.parseInt(map.getJSONObject("userData").getString("力量"));
        int UserAtk = Integer.parseInt(map.getJSONObject("userData").getString("攻击"));
        int UserMP = Integer.parseInt(map.getJSONObject("userData").getString("魂力值"));
        int UserHP = Integer.parseInt(map.getJSONObject("userData").getString("生命"));
        int UserDef = Integer.parseInt(map.getJSONObject("userData").getString("防御"));
        UserPower = (int) (Get_UserPowerUp(Level,UserPower) * 1.5);
        UserAtk = (int) (Get_UserAttackUp(Level,UserAtk) * 1.5);
        UserMP = (int) (Get_UserMPUp(Level,UserMP) * 1.5);
        UserHP = (int) (Get_UserHPUp(Level,UserHP) * 1.5);
        UserDef = (int) (Get_UserDefenseUp(Level,UserDef) * 1.5);
        map.getJSONObject("userData").put("经验",UserEXP);
        map.getJSONObject("userData").put("力量",UserPower);
        map.getJSONObject("userData").put("攻击",UserAtk);
        map.getJSONObject("userData").put("魂力值",UserMP);
        map.getJSONObject("userData").put("生命",UserHP);
        map.getJSONObject("userData").put("防御",UserDef);
        map.getJSONObject("userData").put("当前生命",UserHP);
        map.getJSONObject("userData").put("当前魂力值",UserMP);
        String sql = "UPDATE user SET state_info=? WHERE qq =?";
        jct.update(sql,map.toJSONString(),User.this.QQ);
    }
    /* 删除玩家PVE战斗ID */
    public void Delete_UserPVEId(JdbcTemplate jct){
        String BatData = Get_UserBattleData(jct);
        if (BatData == null) {
            return;
        }
        JSONObject obj = JSON.parseObject(BatData);
        obj.remove("pve");
        if (obj.isEmpty()) {
            String sql = "UPDATE user SET batid=null WHERE qq =?";
            jct.update(sql,User.this.QQ);
        } else {
            String sql = "UPDATE user SET state_info=? WHERE qq =?";
            jct.update(sql,obj.toJSONString(),User.this.QQ);
        }
    }

    /* 设置玩家临时魂环 */
    public void Set_UserTempHuan(JdbcTemplate jct,int Age,String HunShouName){
        JSONObject map = new JSONObject();
        map.put("HunShouName",HunShouName);
        map.put("Age",Age);
        String sql = "UPDATE user SET temporary_skill=? WHERE qq =?";
        jct.update(sql,map.toJSONString(),User.this.QQ);
    }

    /* 获取玩家临时魂环 */
    public String Get_UserTempHuan(JdbcTemplate jct){
        String sql = "SELECT temporary_skill FROM user WHERE qq =?";
        String result = jct.queryForObject(sql,String.class,User.this.QQ);
        if (result == null) {
            return null;
        }
        return result;
    }

    /* 获取玩家原始魂环 */
    public String Get_UserSkillData(JdbcTemplate jct){
        String sql = "SELECT skill_data FROM user WHERE qq =?";
        try {
            return jct.queryForObject(sql,String.class,User.this.QQ);
        } catch (Exception e) {
            return null;
        }
    }

    /* 增加玩家原始魂环 */
    public void Add_UserSkillData(JdbcTemplate jct,String SkillData,String Attribute){
        String Skill_data = Get_UserSkillData(jct);
        JSONObject SkillDataObj = JSON.parseObject(SkillData);
        if (Skill_data == null) {
            JSONObject map = new JSONObject();
            JSONObject obj = new JSONObject();
            obj.put("1",SkillDataObj);
            map.put(Attribute,obj);
            String sql = "UPDATE user SET skill_data=? WHERE qq =?";
            jct.update(sql,map.toJSONString(),User.this.QQ);
            return;
        }
        JSONObject map = JSON.parseObject(Skill_data);
        if (map.getJSONObject(Attribute) == null){
            JSONObject obj = new JSONObject();
            obj.put("1",SkillDataObj);
            map.put(Attribute,obj);
            return;
        }
        int len = map.getJSONObject(Attribute).size();
        map.getJSONObject(Attribute).put(String.valueOf(len+1),SkillDataObj);
        String sql = "UPDATE user SET skill_data=? WHERE qq =?";
        jct.update(sql,map.toJSONString(),User.this.QQ);
    }

    /* 清空玩家临时魂环 */
    public void Clear_UserTempHuan(JdbcTemplate jct){
        String sql = "UPDATE user SET temporary_skill=null WHERE qq =?";
        jct.update(sql,User.this.QQ);
    }

    /* 判断玩家是否需要附加魂环 */
    public boolean Is_UserNeedHuan(JdbcTemplate jct,String Attribute){
        String UserData = Get_UserData(jct);
        JSONObject obj = JSON.parseObject(UserData);
        String Skill_data = Get_UserSkillData(jct);
        JSONObject map = JSON.parseObject(Skill_data);
        int len;
        if (map.getJSONObject(Attribute) == null) {
            len = 0;
        } else {
            len = map.getJSONObject(Attribute).size();
        }
        int Level = obj.getJSONObject("userData").getIntValue("等级");
        return Level / 10 > len;
    }

    /* 玩家复活 */
    public void User_Revive(JdbcTemplate jct){
        String UserData = Get_UserData(jct);
        JSONObject obj = JSON.parseObject(UserData);
        obj.getJSONObject("userData").put("当前生命",1);
        User_Revive_Cost(jct);
        String sql = "UPDATE user SET state_info=? WHERE qq =?";
        jct.update(sql,obj.toJSONString(),User.this.QQ);
    }

    /* 玩家复活扣除货币 */
    public void User_Revive_Cost(JdbcTemplate jct){
        String Currency = Command.Get_CommandReviveCurrency(jct);
        String UserData = Get_UserData(jct);
        JSONObject obj = JSON.parseObject(UserData);
        int Level = obj.getJSONObject("userData").getIntValue("等级");
        int Cost = Function.Get_Revive_Cost(Level);
        Sub_UserMoney(jct,Cost,Currency);
    }

    /* 判断玩家原始魂环中是否存在某魂技 */
    public boolean Is_UserSkillExist(JdbcTemplate jct,String SkillName,String Attribute){
        String Skill_data = Get_UserSkillData(jct);
        if (Skill_data == null) {
            return false;
        }
        JSONObject obj = JSON.parseObject(Skill_data);
        JSONObject map = obj.getJSONObject(Attribute);
        if (map == null) {
            return false;
        }
        for (String key : map.keySet()) {
            JSONObject data = map.getJSONObject(key);
            if (data.getString("SkillName").equals(SkillName)) {
                return true;
            }
        }
        return false;
    }

    /* 获取玩家设置的技能 */
    public String Get_UserSkill(JdbcTemplate jct){
        String sql = "SELECT skill FROM user WHERE qq =?";
        try {
            return jct.queryForObject(sql,String.class, this.QQ);
        } catch (Exception e) {
            return null;
        }
    }

    /* 添加魂环 */
    public void Add_UserSkill(JdbcTemplate jct,String SkillName,String id,String Attribute){
        String SkillData = Get_UserSkill(jct);
        if (SkillData == null) {
            JSONObject map = new JSONObject();
            JSONObject obj = new JSONObject();
            obj.put(id,SkillName);
            map.put(Attribute,obj);
            String sql = "UPDATE user SET skill=? WHERE qq =?";
            jct.update(sql,map.toJSONString(),User.this.QQ);
            return;
        }
        JSONObject map = JSON.parseObject(SkillData);
        if (map.getJSONObject(Attribute) == null){
            JSONObject obj = new JSONObject();
            obj.put(id,SkillName);
            map.put(Attribute,obj);
            String sql = "UPDATE user SET skill=? WHERE qq =?";
            jct.update(sql,map.toJSONString(),User.this.QQ);
            return;
        }
        map.getJSONObject(Attribute).put(id,SkillName);
        String sql = "UPDATE user SET skill=? WHERE qq =?";
        jct.update(sql,map.toJSONString(),User.this.QQ);
    }

    /* 获取玩家设置的魂技名字 */
    public String Get_UserSkillName(JdbcTemplate jct,String id,String Attribute){
        String SkillData = Get_UserSkill(jct);
        if (SkillData == null) {
            return null;
        }
        JSONObject obj = JSON.parseObject(SkillData);
        return obj.getJSONObject(Attribute).getString(id);
    }

    /* 获取玩家附加状态 */
    public String Get_UserStatus(JdbcTemplate jct){
        String sql = "SELECT status FROM user WHERE qq =?";
        try {
            return jct.queryForObject(sql,String.class, this.QQ);
        } catch (Exception e){
            return null;
        }
    }

    /* 玩家附加状态 */
    public void Set_UserStatus(JdbcTemplate jct,Status status,String StatusName){
        String UserData = Get_UserData(jct);
        JSONObject obj = JSON.parseObject(UserData);
        int UpPr = status.Get_StatusAttack(jct, StatusName);
        int UpPow = status.Get_StatusPower(jct, StatusName);
        int UpCt = status.Get_StatusCrit(jct, StatusName);
        int UpCtP = status.Get_StatusCritDamage(jct, StatusName);
        int UpSpeed = status.Get_StatusSpeed(jct, StatusName);
        int UpDe = status.Get_StatusDefence(jct, StatusName);
        obj.getJSONObject("userData").put("攻击",UpPr * obj.getJSONObject("userData").getInteger("攻击"));
        obj.getJSONObject("userData").put("力量",UpPow * obj.getJSONObject("userData").getInteger("力量"));
        obj.getJSONObject("userData").put("暴击率",UpCt + obj.getJSONObject("userData").getInteger("暴击率"));
        obj.getJSONObject("userData").put("暴击伤害",UpCtP + obj.getJSONObject("userData").getInteger("暴击伤害"));
        obj.getJSONObject("userData").put("速度",UpSpeed * obj.getJSONObject("userData").getInteger("速度"));
        obj.getJSONObject("userData").put("防御",UpDe * obj.getJSONObject("userData").getInteger("防御"));
        Set_UserData(jct, obj.toString());
        JSONObject json;
        if (Get_UserStatus(jct) == null){
            json = new JSONObject();
        } else {
            json = JSONObject.parseObject(Get_UserStatus(jct));
        }
        json.put(StatusName,Function.Get_NowTime());
        String sql = "UPDATE user SET status=? WHERE qq=?";
        jct.update(sql,json.toJSONString(),User.this.QQ);
    }

    /* 减少玩家MP */
    public void Sub_UserMP(JdbcTemplate jct,int MP){
        String UserData = Get_UserData(jct);
        JSONObject obj = JSON.parseObject(UserData);
        obj.getJSONObject("userData").put("当前魂力值",obj.getJSONObject("userData").getInteger("当前魂力值") - MP);
        Set_UserData(jct, obj.toString());
    }
}
