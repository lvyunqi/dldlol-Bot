package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.Function;
import com.mryunqi.qimenbot.Controller.PublicAuth;
import com.mryunqi.qimenbot.Controller.User;
import com.mryunqi.qimenbot.dao.WordnoticeDao;
import com.mryunqi.qimenbot.entity.Item;
import com.mryunqi.qimenbot.entity.Npc;
import com.mryunqi.qimenbot.service.NpcService;
import com.mryunqi.qimenbot.service.UserService;
import com.mryunqi.qimenbot.service.WordnoticeService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.mryunqi.qimenbot.Template.NpcShopTpl.npcShopMain;

/**
 * @author mryunqi
 * @date 2022/12/19
 */
@Component
@RequiredArgsConstructor
public class NpcShop extends BotPlugin {
    private final JdbcTemplate jct;
    private final UserService userService;
    private final NpcService npcService;
    private final WordnoticeDao wordnoticeDao;
    private final WordnoticeService wordnoticeService;

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        int pageSize = 8;
        int pageNumber;
        String msg = event.getMessage();
        String cmdMatcher = "^商店(.*)$";
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        User user = new User(userId);
        Command command = new Command();
        Function func = new Function();
        String authGroupList = command.Get_CommandAuthGroupList(jct);
        String aliasCmd = user.Get_UserAliasCmd(jct, msg);
        if (msg.matches(cmdMatcher) | aliasCmd.matches(cmdMatcher)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, authGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            if (aliasCmd.matches(cmdMatcher)){
                msg = aliasCmd;
            }
            String planText = msg.replaceAll("^商店","");
            if("".equals(planText)){
                pageNumber = 1;
                com.mryunqi.qimenbot.entity.User player = userService.getById(event.getUserId());
                String npcName = player.getNpc();
                if (npcName == null){
                    bot.sendMsg(event,"你还未跟任何NPC对话，请先与NPC对话！",false);
                    return MESSAGE_IGNORE;
                }
                Npc npc = npcService.getById(npcName);
                String npcShop = npc.getShop();
                if(npcShop == null){
                    bot.sendMsg(event,npcName+"并不打算与你交易，再换一个人交易吧！",false);
                    return MESSAGE_IGNORE;
                }
                JSONObject jsonNpcShop = JSONObject.parseObject(npcShop);
                JSONObject data = npcShopData(jsonNpcShop,pageNumber,pageSize);
                String message = npcShopMain(wordnoticeDao,wordnoticeService,data.getString("data"),pageNumber,data.getIntValue("totalPages"));
                bot.sendMsg(event,message,false);
                return MESSAGE_IGNORE;
            }
            if (func.Is_Number(planText)){
                pageNumber = Integer.parseInt(planText);
                com.mryunqi.qimenbot.entity.User player = userService.getById(event.getUserId());
                String npcName = player.getNpc();
                if (npcName == null){
                    bot.sendMsg(event,"你还未跟任何NPC对话，请先与NPC对话！",false);
                    return MESSAGE_IGNORE;
                }
                Npc npc = npcService.getById(npcName);
                String npcShop = npc.getShop();
                if(npcShop == null){
                    bot.sendMsg(event,npcName+"并不打算与你交易，再换一个人交易吧！",false);
                    return MESSAGE_IGNORE;
                }
                JSONObject jsonNpcShop = JSONObject.parseObject(npcShop);
                JSONObject data = npcShopData(jsonNpcShop,pageNumber,pageSize);
                String message = npcShopMain(wordnoticeDao,wordnoticeService,data.getString("data"),pageNumber,data.getIntValue("totalPages"));
                bot.sendMsg(event,message,false);
                return MESSAGE_IGNORE;
            }
        }
        return MESSAGE_IGNORE;
    }

    private static String goodsCategory(String category){
        if ("material".equals(category)){
            return "材料";
        }
        return "消耗";
    }

    private static JSONObject npcShopData(JSONObject jsonNpcShop, int pageNumber, int pageSize){
        StringBuilder data = new StringBuilder();
        // 解析 JSON 数据
        List<Item> items = new ArrayList<>();
        jsonNpcShop.keySet().forEach(category -> {
            JSONObject categoryData = jsonNpcShop.getJSONObject(category);
            categoryData.keySet().forEach(itemName -> {
                String[] itemInfo = categoryData.getString(itemName).split("\\|");
                String currency = itemInfo[0];
                long price = Long.parseLong(itemInfo[1]);
                Item item = new Item();
                item.setCategory(goodsCategory(category));
                item.setItemName(itemName);
                item.setCurrency(currency);
                item.setPrice(price);
                items.add(item);
            });
        });
        // 获取总记录数
        int total = items.size();
        int pageEnd = pageNumber * pageSize;
        if (total<pageEnd){
            pageEnd = total;
        }
        if (total<pageSize){
            pageSize=total;
        }
        // 计算总页数
        int totalPages = (int) Math.ceil((double) total / pageSize);
        List<Item> page = items.subList((pageNumber - 1) * pageSize, pageEnd);
        for (Item item : page){
            data.append("[").append(item.getItemName()).append("]").append("--").append(item.getPrice())
                    .append(item.getCurrency()).append("\n");
        }
        JSONObject result = new JSONObject();
        result.put("data",data.toString());
        result.put("totalPages",totalPages);
        return result;
    }
}
