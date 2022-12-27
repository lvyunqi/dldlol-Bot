package com.mryunqi.qimenbot.Template;

import com.mryunqi.qimenbot.Controller.PublicAuth;

import static com.mryunqi.qimenbot.Util.FileUtils.is_file;

/**
 * @author mryunqi
 * @date 2022/12/17
 */
public class NpcTpl {
    public static String npcMain(String userData, String attribute, String npcName, String talk){
        PublicAuth publicAuth = new PublicAuth();
        String rootPath = System.getProperty("user.dir");
        return publicAuth.Get_UserHead(userData,attribute)+
                (!is_file("/斗罗大陆图片/NPC/".concat(npcName.concat(".jpg"))) ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/NPC/" + npcName + ".jpg,cache=0]\n") +
                "[NPC："+npcName+"]：\n"+
                talk+"\n"+
                "<可用命令>\n"+
                "任务\n"+
                "商店";
    }
}
