package com.jnu.myapplication.data;

import java.io.Serializable;

public class Tasks implements Serializable , Comparable {

    public String getTitle() {
        return title;
    }

    public int getScore() {
        return score;
    }

    private String title;
    private int score;

    public Tasks(String title_, int score_) {
        this.title = title_;
        this.score = score_;
    }
    // 自定义比较器，根据 taskNumber 进行比较

    public int compareTo(Object o) {
        Tasks stu = (Tasks) o;
        return this.score - stu.getScore();//按身高排序
        //this-参数：升序；参数-this：降序
    }
}

