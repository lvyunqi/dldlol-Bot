package com.mryunqi.qimenbot.Template;

import com.mryunqi.qimenbot.Controller.PublicAuth;

public class WeaponsTpl {
    public static String Unblock_Success(String UserData,String Attribute,int id,String weapons_name){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "【解封成功】\n" +
                "┏·┄━════━┄\n"+
                "┃·ID："+id+"\n"+
                "┃·魂导器："+weapons_name+"\n"+
                "┗·┄━════━┄\n"+
                "<可用命令>\n" +
                "魂导器列表\n" +
                "装备魂导器 ID";
    }
    public static String Weapons_Unblock_List(String UserData,String Attribute,Long PageCurrent,Long AllPage,StringBuilder data){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "┏·┄━════━┄\n"+
                data +
                "┗·┄━════━┄\n"+
                "---第" + PageCurrent + "页/共" + AllPage + "页\n" +
                "<可用命令>\n" +
                "装备魂导器 ID\n" +
                "查看魂导器 ID/魂导器名\n" +
                "魂导器列表 页数";
    }

    public static String Weapons_EQ(String UserData,String Attribute,String name,StringBuilder data){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "【装备成功】\n"+
                "┏·┄━════━┄\n"+
                "┃魂导器："+name +"\n"+
                data +
                "┗·┄━════━┄\n"+
                "<可用命令>\n" +
                "装备魂导器 ID\n" +
                "卸下魂导器 [0/1]\n"+
                "查看魂导器 ID/魂导器名\n";
    }
    public static String CloseWeapons(String UserData,String Attribute,String name,StringBuilder data){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "【卸下成功】\n"+
                "┏·┄━════━┄\n"+
                "┃魂导器："+name +"\n"+
                data +
                "┗·┄━════━┄\n"+
                "<可用命令>\n" +
                "装备魂导器 ID\n" +
                "卸下魂导器 [0/1]\n"+
                "查看魂导器 ID/魂导器名\n";
    }
    public static String SelectWeapons(String UserData,String Attribute,String name,StringBuilder data){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "【"+name+"】\n"+
                data +
                "<可用命令>\n" +
                "装备魂导器 ID\n" +
                "卸下魂导器 [0/1]\n";
    }
}
