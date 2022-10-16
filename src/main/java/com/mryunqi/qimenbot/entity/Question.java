package com.mryunqi.qimenbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Question)表实体类
 *
 * @author mryunqi
 * @since 2022-10-09 09:46:55
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "question")
public class Question extends Model<Question> {
    
    private Integer id;
    
    private String question;
    
    private String answer;


/*    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }*/

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
    }

