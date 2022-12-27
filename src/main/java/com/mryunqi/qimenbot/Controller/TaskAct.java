package com.mryunqi.qimenbot.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.mryunqi.qimenbot.dao.UserDao;
import com.mryunqi.qimenbot.entity.User;

/**
 * @author mryunqi
 * @date 2022/12/26
 */
public class TaskAct {
    public static void playerTaskNpc(UserDao userDao, User player, String npcName){
        String playerTaskData = player.getTaskData();
        if (playerTaskData != null){
            JSONObject jsonPlayerTaskData = JSONObject.parseObject(playerTaskData);
            if (jsonPlayerTaskData.containsKey("已接取")){
                JSONObject acceptData = jsonPlayerTaskData.getJSONObject("已接取");
                if (acceptData.size() != 0){
                    for(String id : acceptData.keySet()){
                        JSONObject npc = acceptData.getJSONObject(id);
                        JSONObject npcD = npc.getJSONObject("对话");
                        if (npcD.size() != 0){
                            if (npcD.containsKey(npcName)) {
                                npcD.put(npcName, 1);
                                npc.put("对话",npcD);
                                acceptData.put(id,npc);
                                jsonPlayerTaskData.put("已接取",acceptData);
                                player.setTaskData(jsonPlayerTaskData.toJSONString());
                                userDao.updateById(player);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void playerTaskHunshouBeat(UserDao userDao, User player, String hunshouName){
        String playerTaskData = player.getTaskData();
        if (playerTaskData != null){
            JSONObject jsonPlayerTaskData = JSONObject.parseObject(playerTaskData);
            if (jsonPlayerTaskData.containsKey("已接取")){
                JSONObject acceptData = jsonPlayerTaskData.getJSONObject("已接取");
                if (acceptData.size() != 0){
                    for(String id : acceptData.keySet()){
                        JSONObject hunshouBeat = acceptData.getJSONObject(id);
                        JSONObject hunshou = hunshouBeat.getJSONObject("击杀魂兽");
                        if (hunshou.containsKey(hunshouName)){
                            hunshou.put(hunshouName,hunshou.getLong(hunshouName)+1);
                            hunshouBeat.put("击杀魂兽",hunshou);
                            acceptData.put(id,hunshouBeat);
                            jsonPlayerTaskData.put("已接取",acceptData);
                            player.setTaskData(jsonPlayerTaskData.toJSONString());
                            userDao.updateById(player);
                        }
                    }
                }
            }
        }
    }
}
