package com.mryunqi.qimenbot.Template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mryunqi.qimenbot.Controller.PublicAuth;
import com.mryunqi.qimenbot.Controller.User;
import com.mryunqi.qimenbot.Util.NumberUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class QuestionTemplate {
    public static String UnQuestion(String UserData,String Attribute){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute)+
                "当前不存在答题,等下一次问答时间再抢答吧！";
    }

    public static String OccupiedMaxQuestion(JdbcTemplate jct, List<String> PlayerList, String Attribute,String UserData){
        PublicAuth publicAuth = new PublicAuth();
        StringBuilder data = new StringBuilder();
        int i = 0;
        for (String str: PlayerList){
            i++;
            String PlayerData = new User(str).Get_UserData(jct);
            JSONObject PlayerMap = JSON.parseObject(PlayerData);
            String PlayerLv = PlayerMap.getJSONObject("userData").getString("等级");
            String PlayerName = PlayerMap.getJSONObject("userInfo").getString("name");
            data.append("第").append(NumberUtil.int2chineseNum(i)).append("名:").append("[").append(PlayerLv)
                    .append("级]").append(PlayerName).append("(").append(str).append(")\n");
        }
        return publicAuth.Get_UserHead(UserData,Attribute)+
                "抢答失败，等下一次问答时间再抢答吧！\n"+
                data;
    }

    public static String SuccessAnswer(List<String> PlayerList,String Attribute,String UserData,String Answer){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute)+
                "恭喜第"+NumberUtil.int2chineseNum(PlayerList.size()+1)+"名抢答者抢答成功，正确答案：【"+Answer+"】";
    }
}
