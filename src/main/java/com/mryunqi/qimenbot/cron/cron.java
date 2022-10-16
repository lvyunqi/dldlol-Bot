package com.mryunqi.qimenbot.cron;

import com.alibaba.fastjson2.JSONObject;
import com.mryunqi.qimenbot.Controller.Function;
import com.mryunqi.qimenbot.dao.FightdataDao;
import com.mryunqi.qimenbot.entity.Fightdata;
import com.mryunqi.qimenbot.entity.Status;
import com.mryunqi.qimenbot.service.FightdataService;
import com.mryunqi.qimenbot.service.StatusService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component  //用于对那些比较中立的类进行注释；
@EnableScheduling  //开启定时任务
@EnableAsync   //开启多线程
public class cron {
    private final FightdataService fightdataService;
    private final FightdataDao fightdataDao;
    private final StatusService statusService;

    public cron(FightdataService fightdataService, FightdataDao fightdataDao, StatusService statusService) {
        this.fightdataService = fightdataService;
        this.fightdataDao = fightdataDao;
        this.statusService = statusService;
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
