package com.mryunqi.qimenbot.Template;

import com.mryunqi.qimenbot.Controller.PublicAuth;

public class SelectReservePoolTpl {
    public static String SuccessSelectReservePool(String UserData,String Attribute,long HP_Pool,long HP_Max_Pool
            ,long MP_Pool,long MP_Max_Pool){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "┏·┄━════━┄\n"+
                "☆生命储备:"+HP_Pool+"/"+HP_Max_Pool+"\n"+
                "★魂力储备:"+MP_Pool+"/"+MP_Max_Pool+"\n"+
                "┗·┄━════━┄\n"+
                "<可用命令>\n" +
                "状态\n";
    }
}
