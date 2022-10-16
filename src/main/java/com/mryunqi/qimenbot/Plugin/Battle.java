package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.WholeMessageEvent;
import com.mryunqi.qimenbot.Controller.*;
import com.mryunqi.qimenbot.Template.BattleTemplate;
import com.mryunqi.qimenbot.dao.FightdataDao;
import com.mryunqi.qimenbot.dao.UserDao;
import com.mryunqi.qimenbot.service.FightdataService;
import com.mryunqi.qimenbot.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static com.mryunqi.qimenbot.Template.BattleTemplate.Escape_PVE;


@Component
public class Battle extends BotPlugin {
    private final FightdataService fightdataService;
    private final FightdataDao fightdataDao;
    private final UserService userService;

    private final JdbcTemplate jct;

    public Battle(FightdataService fightdataService, FightdataDao fightdataDao, UserService userService, JdbcTemplate jct){
        this.fightdataService = fightdataService;
        this.fightdataDao = fightdataDao;
        this.userService = userService;
        this.jct = jct;
    }

    public static String PVEAttackMain(JdbcTemplate jct, User user, Function func, Fight fight,String BatterId, String UserData){
        JSONObject map = JSONObject.parseObject(UserData);
        String DropMessage;
        String Attribute = user.Get_UserNowAttribute(UserData);
        PublicAuth publicAuth = new PublicAuth();
        // 判断玩家是否死亡
        if (user.Is_UserDead(jct)){
            return publicAuth.Get_UserHead(UserData,Attribute)+"您已经死亡！！\n<可用命令>\n复活";
        }
        String HunShouData = fight.Get_PVE_MonsterAttribute(jct, Integer.parseInt(BatterId));
        JSONObject HunShouDataJson = JSONObject.parseObject(HunShouData);
        GameHunshou hunshou = new GameHunshou();
        Skill skill = new Skill();
        String HunShouName = fight.Get_PVE_MonsterName(jct, Integer.parseInt(BatterId));
        int UserCrit = Integer.parseInt(map.getJSONObject("userData").getString("暴击率"));
        int UserCritDamage = Integer.parseInt(map.getJSONObject("userData").getString("暴击伤害"));
        int UserPower = Integer.parseInt(map.getJSONObject("userData").getString("力量"));
        int UserAtk = Integer.parseInt(map.getJSONObject("userData").getString("攻击"));
        int UserSp = Integer.parseInt(map.getJSONObject("userData").getString("精神力"));
        int UserLevel = Integer.parseInt(map.getJSONObject("userData").getString("等级"));
        int UserExp = Integer.parseInt(map.getJSONObject("userData").getString("经验"));
        int HunShouDef = Integer.parseInt(HunShouDataJson.getString("防御"));
        int HunShouHP = Integer.parseInt(HunShouDataJson.getString("生命"));
        int HunShouDodge = Integer.parseInt(HunShouDataJson.getString("闪避"));
        int HunShouSpeed = Integer.parseInt(HunShouDataJson.getString("速度"));
        int HunShouEXP = hunshou.Get_Hunshou_EXP(jct,HunShouName);
        int ExpUP = Integer.parseInt(Command.Get_CommandExpUp(jct));
        String NowTime = Function.Get_NowTime();
        String LastFightTime = fight.Get_LastFightTime(jct, Integer.parseInt(BatterId));
        String LastTime = Function.DateToStamp(LastFightTime);
        int TimeCha = Function.Get_Second_Diff(NowTime,LastTime);
        if (TimeCha <= 7){
            TimeCha = 2;
        }
        int BatterCount = TimeCha / 2;
        if (BatterCount > 12){
            BatterCount = 12;
        }
        Fight.Update_FightLastdate(jct, Integer.parseInt(BatterId));
        if(func.Get_RandomRun(UserCrit)){
            if (!func.Get_RandomRun(fight.Get_Dodge(HunShouDodge, HunShouSpeed))){
                return BattleTemplate.Get_User_PVE_HunShou_Dodge_Attack(UserData, Attribute,HunShouHP,BatterCount,
                        HunShouBatter(jct,user,func,hunshou,fight,skill,BatterId,UserData,HunShouData,BatterCount));
            }
            int Hurt = fight.Get_UserCritDamage(UserAtk,UserCritDamage,UserPower,UserSp,HunShouDef);
            int NewHunShouHP = HunShouHP - Hurt;
            if (NewHunShouHP < 0){
                NewHunShouHP = 0;
            }
            HunShouDataJson.put("生命",NewHunShouHP);
            Fight.Update_HunShouAttribute(jct, Integer.parseInt(BatterId), HunShouDataJson.toJSONString());
            String HunShouFightData = HunShouBatter(jct,user,func,hunshou,fight,skill,BatterId,UserData,HunShouDataJson.toJSONString(),BatterCount);
            if(NewHunShouHP == 0){
                int HunShouAge = hunshou.Get_Hunshou_Age(jct,HunShouName);
                int AgeRange = (int) (HunShouAge * 0.25);
                int AgeAroundMax = HunShouAge + AgeRange;
                int AgeAroundMin = HunShouAge - AgeRange;
                int NewHunShouAge = Function.Get_Random_Range(AgeAroundMin,AgeAroundMax);
                String HunShouAgeLevelName = GameHunshou.Get_HunShouAgeLevelName(NewHunShouAge);
                user.Set_UserTempHuan(jct,NewHunShouAge,HunShouName);
                String HunShouDropAge = HunShouName+"["+HunShouAgeLevelName+"]\n";
                int NextLevelEXE = Function.Get_NextLevelExp(UserLevel,ExpUP);
                int NewUserEXP = UserExp + HunShouEXP;
                user.Update_UserEXP(jct,HunShouEXP,NextLevelEXE);
                user.Delete_UserPVEId(jct);
                fight.PVE_EndFight(jct,Integer.parseInt(BatterId));
                String HunShouDrop = hunshou.Get_Hunshou_Drop_Item(jct,HunShouName);
                if (HunShouDrop != null){
                    DropMessage = BP.Get_HunShouDrop(jct,user,func,HunShouDrop);
                } else {
                    DropMessage = "无掉落\n";
                }
                if (NewUserEXP >= NextLevelEXE) {
                    // 升级
                    if (user.Is_UserNeedHp(jct,NextLevelEXE)){
                        // 需要魂环
                        return BattleTemplate.Get_User_PVE_HunShou_Crit_Die_NeedHuan(UserData,Attribute,TimeCha,Hurt,HunShouEXP,HunShouFightData,DropMessage,HunShouDropAge,HunShouAgeLevelName);
                    }
                    return BattleTemplate.Get_User_PVE_HunShou_Crit_Die_LevelUp(UserData,Attribute,TimeCha,Hurt,HunShouEXP,HunShouFightData,DropMessage,HunShouDropAge,HunShouAgeLevelName);
                }
                // 不升级
                return BattleTemplate.Get_User_PVE_HunShou_Crit_Die(UserData,Attribute,TimeCha,Hurt,HunShouEXP,HunShouFightData,DropMessage,HunShouDropAge,HunShouAgeLevelName);
            }
            return BattleTemplate.Get_User_PVE_HunShou_Crit_NotDie(UserData, Attribute,NewHunShouHP,BatterCount, Hurt,HunShouFightData);
        }
        if (func.Get_RandomRun(fight.Get_Dodge(HunShouDodge, HunShouSpeed))){
            return BattleTemplate.Get_User_PVE_HunShou_Dodge_Attack(UserData, Attribute,HunShouHP,BatterCount,
                    HunShouBatter(jct,user,func,hunshou,fight,skill,BatterId,UserData,HunShouData,BatterCount));
        }
        int Hurt = fight.Get_UserDamage(UserAtk,UserPower,UserSp,HunShouDef);
        int NewHunShouHP = HunShouHP - Hurt;
        if (NewHunShouHP < 0){
            NewHunShouHP = 0;
        }
        HunShouDataJson.put("生命",NewHunShouHP);
        Fight.Update_HunShouAttribute(jct, Integer.parseInt(BatterId), HunShouDataJson.toJSONString());
        String HunShouFightData = HunShouBatter(jct,user,func,hunshou,fight,skill,BatterId,UserData,HunShouDataJson.toJSONString(),BatterCount);
        if(NewHunShouHP == 0){
            int HunShouAge = hunshou.Get_Hunshou_Age(jct,HunShouName);
            int AgeRange = (int) (HunShouAge * 0.25);
            int AgeAroundMax = HunShouAge + AgeRange;
            int AgeAroundMin = HunShouAge - AgeRange;
            int NewHunShouAge = Function.Get_Random_Range(AgeAroundMin,AgeAroundMax);
            String HunShouAgeLevelName = GameHunshou.Get_HunShouAgeLevelName(NewHunShouAge);
            user.Set_UserTempHuan(jct,NewHunShouAge,HunShouName);
            String HunShouDropAge = HunShouName+"["+HunShouAgeLevelName+"]\n";
            int NextLevelEXE = Function.Get_NextLevelExp(UserLevel,ExpUP);
            int NewUserEXP = UserExp + HunShouEXP;
            user.Update_UserEXP(jct,HunShouEXP,NextLevelEXE);
            user.Delete_UserPVEId(jct);
            fight.PVE_EndFight(jct,Integer.parseInt(BatterId));
            String HunShouDrop = hunshou.Get_Hunshou_Drop_Item(jct,HunShouName);
            if (HunShouDrop != null){
                DropMessage = BP.Get_HunShouDrop(jct,user,func,HunShouDrop);
            } else {
                DropMessage = "无掉落\n";
            }
            if (NewUserEXP >= NextLevelEXE) {
                // 升级
                if (user.Is_UserNeedHp(jct,NextLevelEXE)){
                    // 需要魂环
                    return BattleTemplate.Get_User_PVE_HunShou_Die_NeedHuan(UserData,Attribute,TimeCha,Hurt,HunShouEXP,HunShouFightData,DropMessage,HunShouDropAge,HunShouAgeLevelName);
                }
                return BattleTemplate.Get_User_PVE_HunShou_Die_LevelUp(UserData,Attribute,TimeCha,Hurt,HunShouEXP,HunShouFightData,DropMessage,HunShouDropAge,HunShouAgeLevelName);
            }
            // 不升级
            return BattleTemplate.Get_User_PVE_HunShou_Die(UserData,Attribute,TimeCha,Hurt,HunShouEXP,HunShouFightData,DropMessage,HunShouDropAge,HunShouAgeLevelName);
        }
        return BattleTemplate.Get_User_PVE_HunShou_NotDie(UserData, Attribute,NewHunShouHP,BatterCount, Hurt,HunShouFightData);
    }

    public String PVESkillMain(JdbcTemplate jct, User user, Function func, Status status, Fight fight, String BatterId, int SillId){
        String UserData = user.Get_UserData(jct);
        JSONObject map = JSONObject.parseObject(UserData);
        String DropMessage;
        String StatusMessage;
        String Attribute = user.Get_UserNowAttribute(UserData);
        PublicAuth publicAuth = new PublicAuth();
        // 判断玩家是否死亡
        if (user.Is_UserDead(jct)){
            return publicAuth.Get_UserHead(UserData,Attribute)+"您已经死亡！！\n<可用命令>\n复活";
        }
        String HunShouData = fight.Get_PVE_MonsterAttribute(jct, Integer.parseInt(BatterId));
        JSONObject HunShouDataJson = JSONObject.parseObject(HunShouData);
        GameHunshou hunshou = new GameHunshou();
        Skill skill = new Skill();
        String HunShouName = fight.Get_PVE_MonsterName(jct, Integer.parseInt(BatterId));
        int UserCrit = Integer.parseInt(map.getJSONObject("userData").getString("暴击率"));
        int UserCritDamage = Integer.parseInt(map.getJSONObject("userData").getString("暴击伤害"));
        int UserPower = Integer.parseInt(map.getJSONObject("userData").getString("力量"));
        int UserAtk = Integer.parseInt(map.getJSONObject("userData").getString("攻击"));
        int UserSp = Integer.parseInt(map.getJSONObject("userData").getString("精神力"));
        int UserMP = Integer.parseInt(map.getJSONObject("userData").getString("当前魂力值"));
        int UserLevel = Integer.parseInt(map.getJSONObject("userData").getString("等级"));
        int UserExp = Integer.parseInt(map.getJSONObject("userData").getString("经验"));
        int HunShouDef = Integer.parseInt(HunShouDataJson.getString("防御"));
        int HunShouHP = Integer.parseInt(HunShouDataJson.getString("生命"));
        int HunShouDodge = Integer.parseInt(HunShouDataJson.getString("闪避"));
        int HunShouSpeed = Integer.parseInt(HunShouDataJson.getString("速度"));
        int HunShouEXP = hunshou.Get_Hunshou_EXP(jct,HunShouName);
        int ExpUP = Integer.parseInt(Command.Get_CommandExpUp(jct));
        String UserStatus = "";
        String HunShouStatus = "";
        String NowTime = Function.Get_NowTime();
        String LastFightTime = fight.Get_LastFightTime(jct, Integer.parseInt(BatterId));
        String LastTime = Function.DateToStamp(LastFightTime);
        int TimeCha = Function.Get_Second_Diff(NowTime,LastTime);
        if (TimeCha <= 7){
            TimeCha = 2;
        }
        int BatterCount = TimeCha / 2;
        if (BatterCount > 12){
            BatterCount = 12;
        }
        Fight.Update_FightLastdate(jct, Integer.parseInt(BatterId));
        String SkillName = user.Get_UserSkillName(jct, String.valueOf(SillId),Attribute);
        int SkillMP = Integer.parseInt(skill.Get_SkillCost(jct,SkillName));
        int SkillPower = Integer.parseInt(skill.Get_SkillPower(jct,SkillName));
        int SkillCD = Integer.parseInt(skill.Get_SkillCD(jct,SkillName));
        String SkillInfo = skill.Get_SkillInfo(jct,SkillName);
        if (UserMP < SkillMP){
            String HunShouFightData = HunShouBatter(jct,user,func,hunshou,fight,skill,BatterId,UserData,HunShouDataJson.toJSONString(),BatterCount);
            return BattleTemplate.Get_User_PVE_HunShou_Skill_No_MP(UserData,Attribute,HunShouHP,TimeCha,HunShouFightData);
        }
        if (fight.Get_UserSkill_CoolDown(jct, Integer.parseInt(BatterId),SkillName) < SkillCD){
            int CDCha = SkillCD - fight.Get_UserSkill_CoolDown(jct, Integer.parseInt(BatterId),SkillName);
            String HunShouFightData = HunShouBatter(jct,user,func,hunshou,fight,skill,BatterId,UserData,HunShouDataJson.toJSONString(),BatterCount);
            return BattleTemplate.Get_User_PVE_HunShou_Skill_Cooling(UserData,Attribute,HunShouHP,TimeCha,CDCha,HunShouFightData,SkillName);
        }
        fight.Set_UserSkill_CoolDown(fightdataService,fightdataDao,Integer.parseInt(BatterId),SkillName);
        user.Sub_UserMP(jct,SkillMP);
        if (!(skill.Get_SkillSelfStatus(jct,SkillName) == null)){
            /**
             * 这里要为UserStatus附加状态名称
             */
            UserStatus = skill.Get_SkillSelfStatus(jct,SkillName);
            user.Set_UserStatus(jct,status,skill.Get_SkillSelfStatus(jct,SkillName));
        }
        if (!(skill.Get_SkillStatus(jct,SkillName) == null)){
            /**
             * 这里要为HunShouStatus附加状态名称
             */
            HunShouStatus = skill.Get_SkillStatus(jct,SkillName);
            status.Set_StatusPVE(jct,HunShouStatus, Integer.parseInt(BatterId));
        }
        int SkillAround = skill.Get_SkillAround(jct,SkillName);
        if (SkillAround == 0){
            //敌全体
            int SkillHit = Integer.parseInt(skill.Get_SkillHit(jct,SkillName));
            if(func.Get_RandomRun(SkillHit)){
                String HunShouFightData = HunShouBatter(jct,user,func,hunshou,fight,skill,BatterId,UserData,HunShouDataJson.toJSONString(),BatterCount);
                return BattleTemplate.Get_User_PVE_HunShou_Skill_No_Attack(UserData,Attribute,HunShouHP,TimeCha,HunShouFightData,SkillName);
            }
            int UserDamage = fight.Get_UserDamage(UserAtk,UserPower,UserSp,HunShouDef);
            int Hurt = UserDamage * SkillPower / 100;
            int NewHunShouHP = HunShouHP - Hurt;
            if (NewHunShouHP <= 0){
                NewHunShouHP = 0;
            }
            HunShouDataJson.put("生命",NewHunShouHP);
            Fight.Update_HunShouAttribute(jct, Integer.parseInt(BatterId), HunShouDataJson.toJSONString());
            String HunShouFightData = HunShouBatter(jct,user,func,hunshou,fight,skill,BatterId,UserData,HunShouDataJson.toJSONString(),BatterCount);
            if (NewHunShouHP == 0){
                int HunShouAge = hunshou.Get_Hunshou_Age(jct,HunShouName);
                int AgeRange = (int) (HunShouAge * 0.25);
                int AgeAroundMax = HunShouAge + AgeRange;
                int AgeAroundMin = HunShouAge - AgeRange;
                int NewHunShouAge = Function.Get_Random_Range(AgeAroundMin,AgeAroundMax);
                String HunShouAgeLevelName = GameHunshou.Get_HunShouAgeLevelName(NewHunShouAge);
                user.Set_UserTempHuan(jct,NewHunShouAge,HunShouName);
                String HunShouDropAge = HunShouName+"["+HunShouAgeLevelName+"]\n";
                int NextLevelEXE = Function.Get_NextLevelExp(UserLevel,ExpUP);
                int NewUserEXP = UserExp + HunShouEXP;
                user.Update_UserEXP(jct,HunShouEXP,NextLevelEXE);
                user.Delete_UserPVEId(jct);
                fight.PVE_EndFight(jct,Integer.parseInt(BatterId));
                String HunShouDrop = hunshou.Get_Hunshou_Drop_Item(jct,HunShouName);
                if (HunShouDrop != null){
                    DropMessage = BP.Get_HunShouDrop(jct,user,func,HunShouDrop);
                } else {
                    DropMessage = "无掉落\n";
                }
                if (NewUserEXP >= NextLevelEXE) {
                    // 升级
                    if (user.Is_UserNeedHp(jct,NextLevelEXE)){
                        // 需要魂环
                        return BattleTemplate.Get_User_PVE_HunShou_Skill_Die_NeedHuan(UserData,Attribute,TimeCha,Hurt,HunShouName,SkillName,SkillInfo,HunShouStatus,UserStatus,HunShouFightData,HunShouDropAge,HunShouAgeLevelName,HunShouEXP,DropMessage);
                    }
                    return BattleTemplate.Get_User_PVE_HunShou_Skill_LvUP(UserData,Attribute,TimeCha,Hurt,HunShouName,SkillName,SkillInfo,HunShouStatus,UserStatus,HunShouFightData,HunShouDropAge,HunShouAgeLevelName,HunShouEXP,DropMessage);
                }
                // 不升级
                return BattleTemplate.Get_User_PVE_HunShou_Skill_Die_NoLvUp(UserData,Attribute,TimeCha,Hurt,HunShouName,SkillName,SkillInfo,HunShouStatus,UserStatus,HunShouFightData,HunShouDropAge,HunShouAgeLevelName,HunShouEXP,DropMessage);
            }
            return BattleTemplate.Get_User_PVE_HunShou_Skill_NoDie(UserData,Attribute,HunShouHP,TimeCha,Hurt,HunShouName,SkillName,SkillInfo,HunShouStatus,UserStatus,HunShouFightData);
        }
        return "系统错误！请及时联系管理员！";
    }

    public static String EscapePVE(JdbcTemplate jct, User user, Fight fight, String BatterId){
        String UserData = user.Get_UserData(jct);
        JSONObject map = JSONObject.parseObject(UserData);
        String Attribute = user.Get_UserNowAttribute(UserData);
        PublicAuth publicAuth = new PublicAuth();
        // 判断玩家是否死亡
        if (user.Is_UserDead(jct)){
            return publicAuth.Get_UserHead(UserData,Attribute)+"您已经死亡！！\n<可用命令>\n复活";
        }
        int UserLevel = Integer.parseInt(map.getJSONObject("userData").getString("等级"));
        Fight.Update_FightLastdate(jct, Integer.parseInt(BatterId));
        int Coin = Function.Get_Revive_Cost(UserLevel);
        String Currency = Command.Get_CommandReviveCurrency(jct);
        user.Delete_UserPVEId(jct);
        fight.PVE_EndFight(jct,Integer.parseInt(BatterId)); // 结束战斗
        return Escape_PVE(UserData,Attribute,Coin*2,Currency);
    }

    public static String HunShouBatter(JdbcTemplate jct,User user, Function func, GameHunshou hunshou,Fight fight,Skill skill ,String BatterId, String UserData,String HunShouData,int BatterCount){
        JSONObject map = JSONObject.parseObject(UserData);
        JSONObject HunShouDataJson = JSONObject.parseObject(HunShouData);
        String HunShouName = fight.Get_PVE_MonsterName(jct, Integer.parseInt(BatterId));
        int HunShouCrit = Integer.parseInt(HunShouDataJson.getString("暴击率"));
        int HunShouCritDamage = Integer.parseInt(HunShouDataJson.getString("暴伤"));
        int HunShouPower = Integer.parseInt(HunShouDataJson.getString("力量"));
        int HunShouMP = Integer.parseInt(HunShouDataJson.getString("魂力"));
        int UserDef = Integer.parseInt(map.getJSONObject("userData").getString("防御"));
        int UserDodge = Integer.parseInt(map.getJSONObject("userData").getString("闪避"));
        int UserSpeed = Integer.parseInt(map.getJSONObject("userData").getString("速度"));
        StringBuilder FightData = new StringBuilder();
        int i;
        for (i=0; i<BatterCount; i++){
            if (user.Is_UserDead(jct)){
                break;
            }
            if (!user.Is_UserBattle(jct)){
                break;
            }
            if (func.Get_RandomRun(30)){
                FightData.append("★[").append(HunShouName).append("]正在蓄力，暂时还没有出手！！\n");
                continue;
            }
            if (func.Get_RandomRun(50)){
                // 魂兽普通攻击
                FightData.append(HunShouAttack(jct,user,func,fight,BatterId,HunShouCrit,HunShouCritDamage,HunShouPower,UserDef,UserDodge,UserSpeed,HunShouName));
                continue;
            }
            // 魂兽释放技能
            String HunShouSkill = hunshou.Get_Hunshou_Random_Skill(jct,HunShouName);
            int SkillCD = Integer.parseInt(skill.Get_SkillCD(jct,HunShouSkill));
            if (!hunshou.Is_Hunshou_Has_Skill(jct,HunShouName) | fight.Is_HunShouSkill_CoolDown(jct, Integer.parseInt(BatterId),HunShouSkill,SkillCD)){
                FightData.append(HunShouAttack(jct,user,func,fight,BatterId,HunShouCrit,HunShouCritDamage,HunShouPower,UserDef,UserDodge,UserSpeed,HunShouName));
                continue;
            }
            fight.Update_HunShouSkill_CoolDown(jct, Integer.parseInt(BatterId),HunShouSkill,Function.Get_NowTime());
            int SkillHit = Integer.parseInt(skill.Get_SkillHit(jct,HunShouSkill));
            if (func.Get_RandomRun(SkillHit)){
                // 无附加状态
                if(!skill.Is_SkillStatus(jct,HunShouSkill)){
                    FightData.append(HunShouSkillNoState(jct,user,fight,skill,BatterId,HunShouData,HunShouName,HunShouSkill,HunShouMP,HunShouPower,UserDef));
                    continue;
                }
            }
            // 未命中
            int SkillMP = Integer.parseInt(skill.Get_SkillCost(jct,HunShouSkill));
            int NewHunShouMP = HunShouMP - SkillMP;
            if (NewHunShouMP < 0){
                NewHunShouMP = 0;
            }
            HunShouDataJson.put("魂力",NewHunShouMP);
            Fight.Update_HunShouAttribute(jct, Integer.parseInt(BatterId),HunShouDataJson.toJSONString());
            FightData.append("★[").append(HunShouName).append("]对").append(map.getJSONObject("userInfo").getString("name"))
                    .append("发动了[").append(HunShouSkill).append("]，却没有命中！\n");
        }
        return FightData.toString();
    }

    public static String HunShouSkillNoState(JdbcTemplate jct,User user,Fight fight,Skill skill ,String BatterId,String HunShouData,String HunShouName,String HunShouSkill,int HunShouMP,int HunShouPower,int UserDef){
        int SkillMP = Integer.parseInt(skill.Get_SkillCost(jct,HunShouSkill));
        int NewUserHP;
        String UserData = user.Get_UserData(jct);
        JSONObject map = JSONObject.parseObject(UserData);
        int UserHP = Integer.parseInt(map.getJSONObject("userData").getString("当前生命"));
        if (HunShouMP < SkillMP){
            int Hurt = fight.Get_HunShouAttack(HunShouPower,UserDef);
            NewUserHP = UserHP - Hurt;
            if (NewUserHP < 0){
                NewUserHP = 0;
            }
            // 更新玩家血量
            map.getJSONObject("userData").put("当前生命",NewUserHP);
            user.Set_UserData(jct,map.toJSONString(),"state_info");
            if (NewUserHP == 0){
                int Level = Integer.parseInt(map.getJSONObject("userData").getString("等级"));
                int Coin = Function.Get_Revive_Cost(Level);
                String Currency = Command.Get_CommandReviveCurrency(jct);
                user.User_Revive(jct); // 玩家复活
                user.Delete_UserPVEId(jct);
                fight.PVE_EndFight(jct,Integer.parseInt(BatterId)); // 结束战斗
                return "★[".concat(HunShouName).concat("]{魂力不足}对").concat(map.getJSONObject("userInfo").getString("name"))
                        .concat("造成").concat(String.valueOf(Hurt)).concat("点伤害，")
                        .concat(map.getJSONObject("userInfo").getString("name")).concat("已阵亡！\n")
                        .concat("┄━═【战斗损失】═━┄\n")
                        .concat("[").concat(Currency).concat("]: -").concat(String.valueOf(Coin));
            }
            return "★[".concat(HunShouName).concat("]{魂力不足}对").concat(map.getJSONObject("userInfo").getString("name"))
                    .concat("进行猛烈攻击，造成").concat(String.valueOf(Hurt)).concat("点伤害，\n玩家生命：")
                    .concat(String.valueOf(NewUserHP)).concat("\n");
        }
        HunShouMP = HunShouMP - SkillMP;
        JSONObject HunShouDataJson = JSONObject.parseObject(HunShouData);
        HunShouDataJson.put("魂力",HunShouMP);
        Fight.Update_HunShouAttribute(jct, Integer.parseInt(BatterId),HunShouDataJson.toJSONString());
        int SkillPower = Integer.parseInt(skill.Get_SkillPower(jct,HunShouSkill));
        int Hurt = (int) (fight.Get_HunShouAttack(HunShouPower,UserDef)*SkillPower*0.01);
        NewUserHP = UserHP - Hurt;
        if (NewUserHP < 0){
            NewUserHP = 0;
        }
        // 更新玩家血量
        map.getJSONObject("userData").put("当前生命",NewUserHP);
        user.Set_UserData(jct,map.toJSONString(),"state_info");
        if (NewUserHP == 0){
            int Level = Integer.parseInt(map.getJSONObject("userData").getString("等级"));
            int Coin = Function.Get_Revive_Cost(Level);
            String Currency = Command.Get_CommandReviveCurrency(jct);
            user.User_Revive(jct); // 玩家复活
            user.Delete_UserPVEId(jct);
            fight.PVE_EndFight(jct,Integer.parseInt(BatterId)); // 结束战斗
            return "★[".concat(HunShouName).concat("]对").concat(map.getJSONObject("userInfo").getString("name"))
                    .concat("发动了[").concat(HunShouSkill)
                    .concat("]造成").concat(String.valueOf(Hurt)).concat("点伤害，")
                    .concat(map.getJSONObject("userInfo").getString("name")).concat("已阵亡！\n")
                    .concat("┄━═【战斗损失】═━┄\n")
                    .concat("[").concat(Currency).concat("]: -").concat(String.valueOf(Coin));
        }
        return "★[".concat(HunShouName).concat("]对").concat(map.getJSONObject("userInfo").getString("name"))
                .concat("发动了[").concat(HunShouSkill)
                .concat("]造成").concat(String.valueOf(Hurt)).concat("点伤害，\n玩家生命：")
                .concat(String.valueOf(NewUserHP)).concat("\n");
    }

    public static String HunShouAttack(JdbcTemplate jct,User user,Function func,Fight fight,String BatterId,int HunShouCrit, int HunShouCritDamage, int HunShouPower, int UserDef, int UserDodge, int UserSpeed, String HunShouName){
        String UserData = user.Get_UserData(jct);
        JSONObject map = JSONObject.parseObject(UserData);
        int UserHP = Integer.parseInt(map.getJSONObject("userData").getString("当前生命"));
        int NewUserHP;
        if (func.Get_RandomRun(HunShouCrit)){
            // 暴击
            if (func.Get_RandomRun(fight.Get_Dodge(UserDodge, UserSpeed))){
                return "★[".concat(HunShouName).concat("]对").concat(map.getJSONObject("userInfo").getString("name"))
                        .concat("进行猛烈攻击，却被对方巧妙的避开了！\n");
            }
            int Hurt = fight.Get_HunShouCritDamage(HunShouCritDamage,HunShouPower,UserDef);
            NewUserHP = UserHP - Hurt;
            if (NewUserHP < 0){
                NewUserHP = 0;
            }
            // 更新玩家血量
            map.getJSONObject("userData").put("当前生命",NewUserHP);
            user.Set_UserData(jct,map.toJSONString(),"state_info");
            if (NewUserHP == 0){
                int Level = Integer.parseInt(map.getJSONObject("userData").getString("等级"));
                int Coin = Function.Get_Revive_Cost(Level);
                String Currency = Command.Get_CommandReviveCurrency(jct);
                user.User_Revive(jct); // 玩家复活
                user.Delete_UserPVEId(jct);
                fight.PVE_EndFight(jct,Integer.parseInt(BatterId)); // 结束战斗
                return "★[".concat(HunShouName).concat("]对").concat(map.getJSONObject("userInfo").getString("name"))
                        .concat("造成").concat(String.valueOf(Hurt)).concat("点[暴击]伤害，")
                        .concat(map.getJSONObject("userInfo").getString("name")).concat("已阵亡！\n")
                        .concat("┄━═【战斗损失】═━┄\n")
                        .concat("[").concat(Currency).concat("]: -").concat(String.valueOf(Coin));
            }
            return "★[".concat(HunShouName).concat("]对").concat(map.getJSONObject("userInfo").getString("name"))
                    .concat("进行猛烈攻击，造成").concat(String.valueOf(Hurt)).concat("点[暴击]伤害，\n玩家生命：")
                    .concat(String.valueOf(NewUserHP)).concat("\n");
        }
        //不暴击
        if (func.Get_RandomRun(fight.Get_Dodge(UserDodge, UserSpeed))){
            return "★[".concat(HunShouName).concat("]对").concat(map.getJSONObject("userInfo").getString("name"))
                    .concat("进行猛烈攻击，却被对方巧妙的避开了！\n");
        }
        int Hurt = fight.Get_HunShouAttack(HunShouPower,UserDef);
        NewUserHP = UserHP - Hurt;
        if (NewUserHP < 0){
            NewUserHP = 0;
        }
        // 更新玩家血量
        map.getJSONObject("userData").put("当前生命",NewUserHP);
        user.Set_UserData(jct,map.toJSONString(),"state_info");
        if (NewUserHP == 0){
            int Level = Integer.parseInt(map.getJSONObject("userData").getString("等级"));
            int Coin = Function.Get_Revive_Cost(Level);
            String Currency = Command.Get_CommandReviveCurrency(jct);
            user.User_Revive(jct); // 玩家复活
            user.Delete_UserPVEId(jct);
            fight.PVE_EndFight(jct,Integer.parseInt(BatterId)); // 结束战斗
            return "★[".concat(HunShouName).concat("]对").concat(map.getJSONObject("userInfo").getString("name"))
                    .concat("造成").concat(String.valueOf(Hurt)).concat("点伤害，")
                    .concat(map.getJSONObject("userInfo").getString("name")).concat("已阵亡！\n")
                    .concat("┄━═【战斗损失】═━┄\n")
                    .concat("[").concat(Currency).concat("]: -").concat(String.valueOf(Coin));
        }
        return "★[".concat(HunShouName).concat("]对").concat(map.getJSONObject("userInfo").getString("name"))
                .concat("进行猛烈攻击，造成").concat(String.valueOf(Hurt)).concat("点伤害，\n玩家生命：")
                .concat(String.valueOf(NewUserHP)).concat("\n");
    }

    @Override
    public int onWholeMessage(@NotNull Bot bot, @NotNull WholeMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        Command command = new Command();
        User user = new User(userId);
        Function func = new Function();
        Fight fight = new Fight();
        Status status = new Status();
        PublicAuth publicAuth = new PublicAuth();
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        String CmdAttackMatcher = "^攻击$";
        String CmdAttackMsg = "攻击";
        String CmdEscapeMatcher = "^逃跑$";
        String CmdEscapeMsg = "逃跑";
        String CmdSkillAttackMatcher = "^魂技(.*)$";
        String AliasCmd = user.Get_UserAliasCmd(jct,msg);
        if (msg.matches(CmdEscapeMatcher) | user.Is_UserHaveAlias(jct,CmdEscapeMsg,msg)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            if (!user.Is_UserBattle(jct)){
                bot.sendMsg(event, publicAuth.Get_UserHead(UserData,Attribute)+"您当前不在战斗中！\n<可用命令>\n挑战 魂兽名", false);
                return MESSAGE_IGNORE;
            }
            String BattleType = user.Is_UserPVEorPVPorCopy(jct);
            if (BattleType.equals("pve")){
                String BattleId = user.Get_UserPVEId(jct);
                String message = EscapePVE(jct,user,fight,BattleId);
                bot.sendMsg(event, message, false);
                return MESSAGE_IGNORE;
            }
            return MESSAGE_IGNORE;
        }

        if (msg.matches(CmdAttackMatcher) | user.Is_UserHaveAlias(jct,CmdAttackMsg,msg)){

            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            if (!user.Is_UserBattle(jct)){
                bot.sendMsg(event, publicAuth.Get_UserHead(UserData,Attribute)+"您当前不在战斗中，请先发起挑战！\n<可用命令>\n挑战 魂兽名", false);
                return MESSAGE_IGNORE;
            }
            String BattleType = user.Is_UserPVEorPVPorCopy(jct);

            if (BattleType.equals("pve")){
                String BattleId = user.Get_UserPVEId(jct);
                String message = PVEAttackMain(jct,user,func,fight,BattleId,UserData);
                bot.sendMsg(event, message, false);
                return MESSAGE_IGNORE;
            }
            return MESSAGE_IGNORE;
        }
        if (AliasCmd.matches(CmdSkillAttackMatcher)){
            msg = AliasCmd;
        }
        if (msg.matches(CmdSkillAttackMatcher) | AliasCmd.matches(CmdSkillAttackMatcher)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            if (!user.Is_UserBattle(jct)){
                bot.sendMsg(event, publicAuth.Get_UserHead(UserData,Attribute)+"您当前不在战斗中，请先发起挑战！\n<可用命令>\n挑战 魂兽名", false);
                return MESSAGE_IGNORE;
            }
            String BattleType = user.Is_UserPVEorPVPorCopy(jct);
            // 获取魂技后面的数字
            String SkillId = msg.replaceAll("^魂技","");
            // 判断是否为数字
            if (!SkillId.matches("\\d+")){
                bot.sendMsg(event, publicAuth.Get_UserHead(UserData,Attribute)+"魂技编号错误！\n<可用命令>\n魂技1~20", false);
                return MESSAGE_IGNORE;
            }
            QueryWrapper<com.mryunqi.qimenbot.entity.User> wrapper = new QueryWrapper<>();
            wrapper.eq("qq",userId);
            com.mryunqi.qimenbot.entity.User player = userService.getOne(wrapper);
            String PlayerSkill = player.getSkill();
            JSONObject jsonSkill = JSONObject.parseObject(PlayerSkill);
            if (jsonSkill.getString(Attribute)== null){
                bot.sendMsg(event, publicAuth.Get_UserHead(UserData,Attribute)+"当前使用的武魂魂技位还未附加任何魂技！\n<可用命令>\n切换武魂", false);
                return MESSAGE_IGNORE;
            } else if (jsonSkill.getString(Attribute).equals("{}")) {
                bot.sendMsg(event, publicAuth.Get_UserHead(UserData,Attribute)+"当前使用的武魂魂技位还未附加任何魂技！\n<可用命令>\n切换武魂", false);
                return MESSAGE_IGNORE;
            } else if (jsonSkill.getString(Attribute).equals("")) {
                bot.sendMsg(event, publicAuth.Get_UserHead(UserData,Attribute)+"当前使用的武魂魂技位还未附加任何魂技！\n<可用命令>\n切换武魂", false);
                return MESSAGE_IGNORE;
            }
            if (!jsonSkill.getString(Attribute).contains(SkillId)){
                bot.sendMsg(event, publicAuth.Get_UserHead(UserData,Attribute)+"当前魂技位还未附加魂技！\n<可用命令>\n魂技1~20", false);
                return MESSAGE_IGNORE;
            }
            if (BattleType.equals("pve")){
                String BattleId = user.Get_UserPVEId(jct);
                String message = PVESkillMain(jct,user,func,status,fight,BattleId, Integer.parseInt(SkillId));
                bot.sendMsg(event, message, false);
                return MESSAGE_IGNORE;
            }
        }
        return MESSAGE_IGNORE;
    }
}
