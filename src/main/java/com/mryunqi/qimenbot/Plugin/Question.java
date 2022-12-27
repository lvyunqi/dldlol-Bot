package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.PublicAuth;
import com.mryunqi.qimenbot.Controller.User;
import com.mryunqi.qimenbot.Template.QuestionTemplate;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Question extends BotPlugin {
    private final JdbcTemplate jct;

    public Question(JdbcTemplate jct) {
        this.jct = jct;
    }

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String CmdMatcher = "^抢答(.*)$";
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        User user = new User(userId);
        Command command = new Command();
        String UserData = user.Get_UserData(jct);
        String Attribute = user.Get_UserNowAttribute(UserData);
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        if (msg.matches(CmdMatcher)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            if (!com.mryunqi.qimenbot.Controller.Question.Is_Question(jct)){
                String message = QuestionTemplate.UnQuestion(UserData, Attribute);
                String sendMsg = MsgUtils.builder()
                        .at(event.getUserId())
                        .text(message)
                        .build();
                bot.sendMsg(event, sendMsg, false);
                return MESSAGE_IGNORE;
            }
            JSONObject questionData = JSON.parseObject(com.mryunqi.qimenbot.Controller.Question.QuestionData(jct));
            String quest_id = questionData.getString("id");
            String quest_player = questionData.getString("player");
            String PlanText = msg.replaceAll("^抢答","").strip();
            String Answer = com.mryunqi.qimenbot.Controller.Question.Get_Answer(jct,quest_id);
            int question_player_num = Command.Get_CommandQuestionPlayer(jct);
            if (quest_player != null){
                List<String> PlayerList = Arrays.asList(quest_player.split(","));
                if (PlayerList.size() >= question_player_num){
                    String message = QuestionTemplate.OccupiedMaxQuestion(jct,PlayerList,Attribute,UserData);
                    String sendMsg = MsgUtils.builder()
                            .at(event.getUserId())
                            .text(message)
                            .build();
                    bot.sendMsg(event, sendMsg, false);
                    return MESSAGE_IGNORE;
                }
                if(Answer.contains(PlanText)){
                    String message = QuestionTemplate.SuccessAnswer(PlayerList,Attribute,UserData,Answer);
                    String sendMsg = MsgUtils.builder()
                            .at(event.getUserId())
                            .text(message)
                            .build();
                    bot.sendMsg(event, sendMsg, false);
                    PlayerList.add(userId);
                    return MESSAGE_IGNORE;
                }
            }

        }
        return MESSAGE_IGNORE;
    }
}
