package com.example.chronus.TimeLine;

import java.util.Date;

/**
 * 存储数据bena类
 * Created by wen on 2019/8/14.
 */

public class TimeData {

    private Date postDate;
    private String title;
    private String desc;

    public TimeData(){

    }

    public TimeData(Date postDate, String title, String desc) {
        this.postDate = postDate;
        this.title = title;
        this.desc = desc;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
