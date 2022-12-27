package com.mryunqi.qimenbot.Template;

import com.mryunqi.qimenbot.Controller.PublicAuth;
import com.mryunqi.qimenbot.Controller.WordNoticeC;
import com.mryunqi.qimenbot.dao.WordnoticeDao;
import com.mryunqi.qimenbot.service.WordnoticeService;

/**
 * @author mryunqi
 * @date 2022/12/26
 */
public class TaskTpl {
    public static String getTaskTemplate(WordnoticeDao wordnoticeDao, WordnoticeService wordnoticeService, StringBuilder data){
        WordNoticeC wordNoticeC = new WordNoticeC(wordnoticeDao,wordnoticeService);
        return "[NPC：任务]\n---任务列表---\n"+
                data +
                "<可用命令>\n" +
                "查看物品 商品名\n" +
                "购买 商品名"+
                wordNoticeC.getOneWordNotice();
    }
    public static String getTaskNoTask(WordnoticeDao wordnoticeDao, WordnoticeService wordnoticeService,String userData, String attribute){
        PublicAuth publicAuth = new PublicAuth();
        WordNoticeC wordNoticeC = new WordNoticeC(wordnoticeDao,wordnoticeService);
        return publicAuth.Get_UserHead(userData,attribute)+
                "这个任务貌似没有人发布过哦！\n"+
                "<可用命令>\n" +
                "查看任务 任务名"+
                wordNoticeC.getOneWordNotice();
    }
    public static String getTaskConnected(WordnoticeDao wordnoticeDao, WordnoticeService wordnoticeService,String userData, String attribute){
        PublicAuth publicAuth = new PublicAuth();
        WordNoticeC wordNoticeC = new WordNoticeC(wordnoticeDao,wordnoticeService);
        return publicAuth.Get_UserHead(userData,attribute)+
                "该任务已被你接取过或已完成！\n"+
                "<可用命令>\n" +
                "查看任务 任务名"+
                wordNoticeC.getOneWordNotice();
    }
    public static String getTaskUnable(WordnoticeDao wordnoticeDao, WordnoticeService wordnoticeService,String userData
            , String attribute,StringBuilder limitData){
        PublicAuth publicAuth = new PublicAuth();
        WordNoticeC wordNoticeC = new WordNoticeC(wordnoticeDao,wordnoticeService);
        return publicAuth.Get_UserHead(userData,attribute)+
                "【承接失败】\n"+
                "承接条件：\n"+
                limitData +
                "<可用命令>\n" +
                "查看任务 任务名"+
                wordNoticeC.getOneWordNotice();
    }
    public static String getTaskAccept(WordnoticeDao wordnoticeDao, WordnoticeService wordnoticeService,String userData
            , String attribute,String taskName,String acceptDetail,String taskReward){
        PublicAuth publicAuth = new PublicAuth();
        WordNoticeC wordNoticeC = new WordNoticeC(wordnoticeDao,wordnoticeService);
        return publicAuth.Get_UserHead(userData,attribute)+
                "【承接成功】\n"+
                "你一语把["+taskName+"]接了下来!\n"+
                "任务内容：\n"+acceptDetail+"\n"+
                "完成奖励：\n"+
                taskReward+
                "<可用命令>\n" +
                "我的任务\n"+
                "查看任务 "+taskName+""+
                wordNoticeC.getOneWordNotice();
    }

    public static String getSelfTask(WordnoticeDao wordnoticeDao, WordnoticeService wordnoticeService,String userData
            , String attribute,StringBuilder data){
        PublicAuth publicAuth = new PublicAuth();
        WordNoticeC wordNoticeC = new WordNoticeC(wordnoticeDao,wordnoticeService);
        return publicAuth.Get_UserHead(userData,attribute)+
                "----我的任务----\n"+
                data+
                "<可用命令>\n" +
                "查看任务 任务名"+
                wordNoticeC.getOneWordNotice();
    }
}
