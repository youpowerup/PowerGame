package com.example.pojo;

import java.sql.Timestamp;

public class ViewLog {
    private String username;
    private String type;
    private Timestamp beginViewTime;
    private Timestamp endViewTime;
    private Integer viewTime;

    @Override
    public String toString() {
        return "ViewLog{" +
                "username='" + username + '\'' +
                ", type='" + type + '\'' +
                ", beginViewTime=" + beginViewTime +
                ", endViewTime=" + endViewTime +
                ", viewTime=" + viewTime +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getBeginViewTime() {
        return beginViewTime;
    }

    public void setBeginViewTime(Timestamp beginViewTime) {
        this.beginViewTime = beginViewTime;
    }

    public Timestamp getEndViewTime() {
        return endViewTime;
    }

    public void setEndViewTime(Timestamp endViewTime) {
        this.endViewTime = endViewTime;
    }

    public Integer getViewTime() {
        return viewTime;
    }

    public void setViewTime(Integer viewTime) {
        this.viewTime = viewTime;
    }
}
