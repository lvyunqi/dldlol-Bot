package com.mryunqi.qimenbot.cron;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mryunqi.qimenbot.Controller.Function;
import com.mryunqi.qimenbot.dao.FightdataDao;
import com.mryunqi.qimenbot.dao.UserDao;
import com.mryunqi.qimenbot.entity.Fightdata;
import com.mryunqi.qimenbot.entity.Status;
import com.mryunqi.qimenbot.entity.User;
import com.mryunqi.qimenbot.service.FightdataService;
import com.mryunqi.qimenbot.service.StatusService;
import com.mryunqi.qimenbot.service.UserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.mryunqi.qimenbot.Controller.Player.ALLPlayerQQ;

@Component  //用于对那些比较中立的类进行注释；
@EnableScheduling  //开启定时任务
@EnableAsync   //开启多线程
public class cron {
    private final FightdataService fightdataService;
    private final FightdataDao fightdataDao;
    private final StatusService statusService;
    private final UserDao userDao;
    private final UserService userService;

    public cron(FightdataService fightdataService, FightdataDao fightdataDao, StatusService statusService, UserDao userDao, UserService userService) {
        this.fightdataService = fightdataService;
        this.fightdataDao = fightdataDao;
        this.statusService = statusService;
        this.userDao = userDao;
        this.userService = userService;
    }

    @Async
    @Scheduled(fixedDelay = 1000)  //每隔1秒执行一次
    public void HunShouStatus(){
        List<Fightdata> selectFightData = selectFightData();
        if (selectFightData != null){
            for (Fightdata data : selectFightData) {
                String status = data.getStatus();
                JSONObject jsonData = JSONObject.parseObject(status);
                if (jsonData == null){
                    continue;
                }
                for(String key : jsonData.keySet()){
                    setHunShouStatus(data,key, jsonData.getString(key));
                }
            }
        }

    }

    @Async
    @Scheduled(fixedDelay = 10000)  //每隔10秒执行一次
    public void ReservePool(){
        List<Long> AllPlayerQQ = ALLPlayerQQ(userDao);
        for (Long QQ : AllPlayerQQ){
            User player = userService.getById(QQ);
            JSONObject jsonPlayerData = JSONObject.parseObject(player.getStateInfo());
            JSONObject jsonPlayer_UserData = jsonPlayerData.getJSONObject("userData");
            if (jsonPlayer_UserData.getLong("当前生命")>=jsonPlayer_UserData.getLong("生命")){
                continue;
            }
            if (jsonPlayer_UserData.getLong("生命储备")<= 0){
                continue;
            }
            long RecoveryMax;
            RecoveryMax = (long) (jsonPlayer_UserData.getLong("生命") * 0.2);
            if (jsonPlayer_UserData.getLong("生命储备")<RecoveryMax){
                RecoveryMax = jsonPlayer_UserData.getLong("生命储备");
            }
            if ((jsonPlayer_UserData.getLong("生命") - jsonPlayer_UserData.getLong("当前生命")) < RecoveryMax){
                RecoveryMax = RecoveryMax-(jsonPlayer_UserData.getLong("生命") - jsonPlayer_UserData.getLong("当前生命"));
                jsonPlayer_UserData.put("当前生命",jsonPlayer_UserData.getLong("生命"));
                jsonPlayer_UserData.put("生命储备",RecoveryMax);
                jsonPlayerData.put("userData",jsonPlayer_UserData);
                player.setStateInfo(jsonPlayerData.toJSONString());
                userDao.updateById(player);
                continue;
            }
            jsonPlayer_UserData.put("当前生命",jsonPlayer_UserData.getLong("当前生命")+RecoveryMax);
            jsonPlayer_UserData.put("生命储备",jsonPlayer_UserData.getLong("生命储备") - RecoveryMax);
            jsonPlayerData.put("userData",jsonPlayer_UserData);
            player.setStateInfo(jsonPlayerData.toJSONString());
            userDao.updateById(player);
        }
        for (Long QQ : AllPlayerQQ){
            User player = userService.getById(QQ);
            JSONObject jsonPlayerData = JSONObject.parseObject(player.getStateInfo());
            JSONObject jsonPlayer_UserData = jsonPlayerData.getJSONObject("userData");
            if (jsonPlayer_UserData.getLong("当前魂力值")>=jsonPlayer_UserData.getLong("魂力值")){
                continue;
            }
            if (jsonPlayer_UserData.getLong("魂力储备")<= 0){
                continue;
            }
            long RecoveryMax;
            RecoveryMax = (long) (jsonPlayer_UserData.getLong("魂力值") * 0.2);
            if (jsonPlayer_UserData.getLong("魂力储备")<RecoveryMax){
                RecoveryMax = jsonPlayer_UserData.getLong("魂力储备");
            }
            if ((jsonPlayer_UserData.getLong("魂力值") - jsonPlayer_UserData.getLong("当前魂力值")) < RecoveryMax){
                RecoveryMax = RecoveryMax-(jsonPlayer_UserData.getLong("魂力值") - jsonPlayer_UserData.getLong("当前魂力值"));
                jsonPlayer_UserData.put("当前魂力值",jsonPlayer_UserData.getLong("魂力值"));
                jsonPlayer_UserData.put("魂力储备",RecoveryMax);
                jsonPlayerData.put("userData",jsonPlayer_UserData);
                player.setStateInfo(jsonPlayerData.toJSONString());
                userDao.updateById(player);
                continue;
            }
            jsonPlayer_UserData.put("当前魂力值",jsonPlayer_UserData.getLong("当前魂力值")+RecoveryMax);
            jsonPlayer_UserData.put("魂力储备",jsonPlayer_UserData.getLong("魂力储备") - RecoveryMax);
            jsonPlayerData.put("userData",jsonPlayer_UserData);
            player.setStateInfo(jsonPlayerData.toJSONString());
            userDao.updateById(player);
        }
    }

    @Async
    @Scheduled(cron = "0 0 4 * * ? ")  //每天早上4点整
    public void sginInClean(){
        List<Long> allPlayerQQ = ALLPlayerQQ(userDao);
        for (Long QQ : allPlayerQQ){
            User player = userService.getById(QQ);
            String sginInDay = player.getSginInDay();
            JSONObject jsonSginInDay = JSONObject.parseObject(sginInDay);
            int isNowDay = jsonSginInDay.getIntValue("isNowDay");
            if (isNowDay == 0){
                continue;
            }
            jsonSginInDay.put("isNowDay",0);
            player.setSginInDay(jsonSginInDay.toJSONString());
            userDao.updateById(player);
        }
    }
    /*@Async
    @Scheduled(fixedDelay = 2000) //每隔2秒执行一次
    public void second(){
        System.out.println("第二个定时任务开始："+ LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
    }*/

    public List<Fightdata> selectFightData(){
        List<Fightdata> list = fightdataService.list();
        if (list.isEmpty()){
            return null;
        }else {
            return list;
        }
    }

    public Status selectStatus(String status){
        return statusService.getById(status);
    }

    @Async
    public void setHunShouStatus(Fightdata data,String name,String time){
        Status statusData = selectStatus(name);
        int CD = statusData.getCd();
        String NowTime = Function.Get_NowTime();
        int CDCha = Function.Get_Second_Diff(NowTime, time);
        if (CDCha > CD){
            int pow = statusData.getPow();
            int ct = statusData.getCt();
            int ctp = statusData.getCtp();
            int speed = statusData.getSpeed();
            int de = statusData.getDe();
            String hsdata = data.getData();
            JSONObject jsonData = JSONObject.parseObject(hsdata);
            int Pow = jsonData.getIntValue("力量");
            int Ct = jsonData.getIntValue("暴击率");
            int CtP = jsonData.getIntValue("爆伤");
            int Speed = jsonData.getIntValue("速度");
            int De = jsonData.getIntValue("防御");
            jsonData.put("力量",(int)(Pow/(pow*0.01)));
            jsonData.put("暴击率",Ct-ct);
            jsonData.put("爆伤",CtP-ctp);
            jsonData.put("速度",(int)(Speed/(speed*0.01)));
            jsonData.put("防御",(int)(De/(de*0.01)));
            data.setData(jsonData.toJSONString());
            fightdataDao.updateById(data);
        }
    }


}
