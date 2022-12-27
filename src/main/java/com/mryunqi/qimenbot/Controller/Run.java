package com.mryunqi.qimenbot.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mryunqi.qimenbot.cron.cron;
import com.mryunqi.qimenbot.dao.CommandDao;
import com.mryunqi.qimenbot.dao.UserDao;
import com.mryunqi.qimenbot.entity.Command;
import com.mryunqi.qimenbot.entity.User;
import com.mryunqi.qimenbot.service.CommandService;
import com.mryunqi.qimenbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.mryunqi.qimenbot.Controller.Player.ALLPlayerQQ;
import static com.mryunqi.qimenbot.Model.Login.Get_CommandAdmin;
import static com.mryunqi.qimenbot.Util.FileUtils.is_Directory;

/**
 * @author mryunqi
 */
@Configuration
@Component
@RequiredArgsConstructor
public class Run {
    private final Environment environment;
    private final JdbcTemplate jct;
    private final CommandDao commandDao;
    private final CommandService commandService;
    private final UserDao userDao;
    private final UserService userService;

    private String LocalAdministrator(){
        return environment.getProperty("bot.administrator");
    }
    private String LocalAdministratorPassword(){
        return environment.getProperty("bot.password");
    }
    private static final Logger log = LoggerFactory.getLogger(Run.class);


    @PostConstruct
    public void run() {
        List<String> Directory = new ArrayList<>();
        Directory.add("/斗罗大陆图片/地图");
        Directory.add("/斗罗大陆图片/魂兽");
        Directory.add("/斗罗大陆图片/魂环");
        Directory.add("/斗罗大陆图片/武魂");
        Directory.add("/斗罗大陆图片/NPC");
        Directory.add("/斗罗大陆图片/魂导器");
        Directory.add("/斗罗大陆图片/其他");
        log.info("开始初始化!");
        for (String data : Directory){
            log.info(is_Directory(data));
        }
        Command command = commandService.getById(1);
        String strLastRunningTime = command.getLastRunningTime();
        long lastRunningTime;
        if (strLastRunningTime == null){
            lastRunningTime = 0;
        }else {
            lastRunningTime = Long.parseLong(command.getLastRunningTime());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date time = calendar.getTime();
        long timestamp = time.getTime();
        if (timestamp>=lastRunningTime){
            clearSginIn();
        }
        log.info("开始检查超级管理员权限账号");
        String AdminInit = Get_CommandAdmin(jct);
        if (!AdminInit.contains(LocalAdministrator())){
            log.warn("数据库中不存在超级管理员账号["+LocalAdministrator()+"],即将开始创建");
            JSONObject jsonAdmin = JSONObject.parse(AdminInit);
            jsonAdmin.put(LocalAdministrator(),LocalAdministratorPassword());
            UpdateWrapper<Command> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",1).set("admin",jsonAdmin.toJSONString());
            commandDao.update(null,updateWrapper);
            log.info("超级管理员账户创建成功");
        }
        log.info("超级管理员账号["+LocalAdministrator()+"]已存在");


    }

    @PreDestroy
    public void stop(){
        UpdateWrapper<Command> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",1).set("last_running_time",System.currentTimeMillis());
        commandDao.update(null,updateWrapper);
    }

    private void clearSginIn(){
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
}
