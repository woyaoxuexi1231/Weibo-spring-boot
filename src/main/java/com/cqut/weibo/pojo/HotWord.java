package com.cqut.weibo.pojo;

/**
 * @Description:热搜词汇
 * @Author:HL
 * @date:2021.04.26.16.55.36
 */
public class HotWord {

    String value;

    int score;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public HotWord(String value, int score) {
        this.value = value;
        this.score = score;
    }
}
