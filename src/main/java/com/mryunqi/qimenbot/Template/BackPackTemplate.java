package com.mryunqi.qimenbot.Template;

import com.mryunqi.qimenbot.Controller.PublicAuth;

import java.util.List;

public class BackPackTemplate {
    public String Get_backpackTemplate(List<String> BackPack,String MoneyData,String PlanText,String UserData,String Attribute) {
        int AllPage = BackPack.size()/8;
        // 如果有余数，则页数+1
        if (BackPack.size()%8 != 0) {
            AllPage++;
        }
        if(Integer.parseInt(PlanText) > AllPage) {
            PlanText = String.valueOf(AllPage);
        }

        int LeftIndex;
        int RightIndex;

        LeftIndex = 8*(Integer.parseInt(PlanText)-1);
        RightIndex = LeftIndex+8;

        if (Integer.parseInt(PlanText) == AllPage) {
            RightIndex = BackPack.size();
        }
        // 如果BackPack.size()不足8个，则LeftIndex只有BackPack.size()个
        if (BackPack.size()<8){
            LeftIndex = 0;
            RightIndex = BackPack.size();
        }
        // 将BackPack分成PlanText页的BackPackList
        List<String> BackPackList = BackPack.subList(LeftIndex,RightIndex);
        StringBuilder data = new StringBuilder();
        for(String str : BackPackList){
            data.append(str).append("\n");
        }
        data.append("---第1页/共").append(AllPage).append("页---\n");
        data.append(MoneyData);
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                data +
                "<可用命令>\n" +
                "查看物品\n" +
                "查看魂导器 魂导器名称\n" +
                "转账 币种 对方QQ-数量\n" +
                "发送物品 对方QQ-物品名称-数量\n" +
                "出售 物品名称-数量\n" +
                "背包 页数\n";
    }
}
