package com.mryunqi.qimenbot.Controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BP {
    // 获取武器品阶名称
    public String Get_WeaponLvName(JdbcTemplate jct,String WeaponName){
        String sql = String.format("SELECT grade FROM weapons WHERE name='%s'",WeaponName);
        return jct.queryForObject(sql,String.class);
    }

    public List<String> Get_BackPackList(JdbcTemplate jct, String backpack) {
        JSONObject backpackJson = JSONObject.parseObject(backpack);
        List<String> list = new ArrayList<>();
        // 遍历backpackJson
        for (String key : backpackJson.keySet()) {
            if (key.equals("consumables")){
                JSONObject consumables = backpackJson.getJSONObject(key);
                for (String key2 : consumables.keySet()) {
                    String data = "·[消耗]"+key2+"x"+consumables.getString(key2);
                    list.add(data);
                }
            }
            if (key.equals("material")){
                JSONObject equipments = backpackJson.getJSONObject(key);
                for (String key2 : equipments.keySet()) {
                    String InitData = equipments.getString(key2);
                    int index = InitData.indexOf("|");
                    String count = InitData.substring(index+1);
                    String data = "·[材料]"+key2+"x"+count;
                    list.add(data);
                }
            }
            if (key.equals("weapons")){
                JSONObject equipments = backpackJson.getJSONObject(key);
                for (String key2 : equipments.keySet()) {
                    String InitData = equipments.getString(key2);
                    int index = InitData.indexOf("|");
                    String Lv = InitData.substring(0,index);
                    String count = InitData.substring(index+1);
                    String WeaponsName = Get_WeaponLvName(jct,key2);
                    String data = (Lv.equals("0") ? "·[攻击]" : "·[防御]")+"〔"+WeaponsName+"〕"+key2+"x"+count;
                    list.add(data);
                }
            }
        }
        return list;
    }

    /* 魂兽掉落物获取增加 */
    public static String Get_HunShouDrop(JdbcTemplate jct,User user,Function func,String Drop){
        JSONObject DropJson = JSONObject.parseObject(Drop);
        StringBuilder message = new StringBuilder();
        for (String key : DropJson.keySet()){
            if (Objects.equals(key, "necessary")){
                for(String key2 : DropJson.getJSONObject(key).keySet()){
                    if (key2.equals("currency")){
                        for (String key3 : DropJson.getJSONObject(key).getJSONObject(key2).keySet()){
                            user.Add_UserMoney(jct,Integer.parseInt(DropJson.getJSONObject(key).getJSONObject(key2).getString(key3)),key3);
                            message.append("·[").append(key3).append("]x").append(DropJson.getJSONObject(key).getJSONObject(key2).getString(key3)).append("\n");
                        }
                    }
                    if (key2.equals("material")){
                        for (String key3 : DropJson.getJSONObject(key).getJSONObject(key2).keySet()){
                            user.Add_UserItemNum(jct,key3,"material",Integer.parseInt(DropJson.getJSONObject(key).getJSONObject(key2).getString(key3)));
                            message.append("·[材料]").append(key3).append("x").append(DropJson.getJSONObject(key).getJSONObject(key2).getString(key3)).append("\n");
                        }
                    }
                    if (key2.equals("consumables")){
                        for (String key3 : DropJson.getJSONObject(key).getJSONObject(key2).keySet()){
                            user.Add_UserItemNum(jct,key3,"consumables",Integer.parseInt(DropJson.getJSONObject(key).getJSONObject(key2).getString(key3)));
                            message.append("·[消耗]").append(key3).append("x").append(DropJson.getJSONObject(key).getJSONObject(key2).getString(key3)).append("\n");
                        }
                    }
                }
            }
            if (Objects.equals(key, "probability")){
                if (func.Get_RandomRun(30)){
                    // 随机选择一个类型
                    String[] type = {"material","consumables","currency"};
                    int index = Function.Get_Random_Range(0,type.length-1);
                    String type_ = type[index];
                    // 判断类型是否存在
                    if (DropJson.getJSONObject(key).containsKey(type_)){
                        // 随机选择一个物品
                        String[] item = DropJson.getJSONObject(key).getJSONObject(type_).keySet().toArray(new String[0]);
                        String item_ = item[Function.Get_Random_Range(0,item.length-1)];
                        if (type_.equals("currency")){
                            user.Add_UserMoney(jct,Integer.parseInt(DropJson.getJSONObject(key).getJSONObject(type_).getString(item_)),item_);
                            message.append("·[概率-").append(item_).append("]x").append(DropJson.getJSONObject(key).getJSONObject(type_).getString(item_)).append("\n");
                        }
                        if (type_.equals("material")){
                            user.Add_UserItemNum(jct,item_,"material",Integer.parseInt(DropJson.getJSONObject(key).getJSONObject(type_).getString(item_)));
                            message.append("·[概率-材料]").append(item_).append("x").append(DropJson.getJSONObject(key).getJSONObject(type_).getString(item_)).append("\n");
                        }
                        if (type_.equals("consumables")){
                            user.Add_UserItemNum(jct,item_,"consumables",Integer.parseInt(DropJson.getJSONObject(key).getJSONObject(type_).getString(item_)));
                            message.append("·[概率-消耗]").append(item_).append("x").append(DropJson.getJSONObject(key).getJSONObject(type_).getString(item_)).append("\n");
                        }
                    }
                }
            }
        }
        return message.toString();
    }
}
