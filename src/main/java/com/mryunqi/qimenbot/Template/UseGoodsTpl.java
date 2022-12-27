package com.mryunqi.qimenbot.Template;

import com.mryunqi.qimenbot.Controller.PublicAuth;

public class UseGoodsTpl {
    public static String useGoodsSuccess(String UserData,String Attribute,String GoodsName,StringBuilder data){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "您成功使用了["+GoodsName+"]\n" +
                data;
    }
}
