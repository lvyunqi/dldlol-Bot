package com.mryunqi.qimenbot.Template;

public class RegisterTemplate {
    public String Register(String Name,String DefaultMap){
        String t1 = "To"+Name+"〔未觉醒〕:\n";
        String t2 = "创建角色成功！\n";
        String t3 = "";
        String t4 = "您biu的一声从天上掉下["+DefaultMap+"]\n";
        String t5 = "欢迎来到斗罗大陆世界，接下来我们来下基础的操作吧！这个世界最基础的是武魂，有了武魂后你才能去做很多事情，让我们发送[武魂觉醒]来觉醒吧！\n";
        String t6 = "<可用命令>\n";
        String t7 = "武魂觉醒";
        return t1+t2+t3+t4+t5+t6+t7;
    }
}
