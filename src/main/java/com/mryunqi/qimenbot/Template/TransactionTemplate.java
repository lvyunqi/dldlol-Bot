package com.mryunqi.qimenbot.Template;

import com.mryunqi.qimenbot.Controller.PublicAuth;

public class TransactionTemplate {
    public String Get_TransactionMoneyTemplate(String UserData,String Attribute,int count,String Currency) {
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "成功发送"+count+"枚["+Currency+"]给对方！\n" +
                "<可用命令>\n" +
                "背包\n";
    }

    public String Get_TransactionItemTemplate(String UserData,String Attribute,int count,String ItemName){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "成功发送"+count+"个["+ItemName+"]给对方！\n" +
                "<可用命令>\n" +
                "背包\n";
    }
}
