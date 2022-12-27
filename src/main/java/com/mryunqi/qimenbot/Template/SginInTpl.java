package com.mryunqi.qimenbot.Template;

import com.mryunqi.qimenbot.Controller.PublicAuth;

import static com.mryunqi.qimenbot.Util.FileUtils.is_file;

/**
 * @author mryunqi
 * @date 2022/12/18
 */
public class SginInTpl {
    public static String sginInSuccess(String userData, String attribute, String data, int allDay,int nowDay){
        PublicAuth publicAuth = new PublicAuth();
        String rootPath = System.getProperty("user.dir");
        return publicAuth.Get_UserHead(userData,attribute)+
                (!is_file("/斗罗大陆图片/其他/".concat("签到成功.jpg")) ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/其他/" + "签到成功.jpg,cache=0]\n") +
                "【签到成功】\n"+
                "总签到："+allDay+"天"+"\n"+
                "累积签到："+nowDay+"天"+"\n"+
                "·┄本次签到获得┄·\n"+
                data+
                "连续签到越多，奖励越多哦！";
    }

    public static String sginInFail(String userData, String attribute){
        PublicAuth publicAuth = new PublicAuth();
        String rootPath = System.getProperty("user.dir");
        return publicAuth.Get_UserHead(userData,attribute)+
                (!is_file("/斗罗大陆图片/其他/".concat("签到失败.jpg")) ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/其他/" + "签到失败.jpg,cache=0]\n") +
                "【签到失败】\n"+
                "今日已签到，明日再来吧！";
    }
}
