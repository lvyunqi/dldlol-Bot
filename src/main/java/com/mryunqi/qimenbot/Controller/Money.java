package com.mryunqi.qimenbot.Controller;

import com.alibaba.fastjson2.JSONObject;


public class Money {
    /* 将钱包json数据转换为字符串 */
    public String Get_MoneyString(String Money,String Currency) {
        JSONObject obj = JSONObject.parseObject(Money);
        JSONObject obj2 = JSONObject.parseObject(Currency);
        StringBuilder data = new StringBuilder();
        for (String key : obj2.keySet()) {
            for (String key2 : obj.keySet()) {
                if (key.equals(key2)) {
                    data.append("[").append(key2).append("]").append(obj.getString(key2)).append("\n");
                }
            }
        }
        return data.toString();
    }
}
