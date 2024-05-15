package com.example.pojo;

import java.sql.Timestamp;

public class DatabaseLog
{
    private String username;
    private String Act;
    private String Table;
    private Timestamp Time;

    @Override
    public String toString() {
        return "DatabaseLog{" +
                "username='" + username + '\'' +
                ", Act='" + Act + '\'' +
                ", Table='" + Table + '\'' +
                ", Time=" + Time +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAct() {
        return Act;
    }

    public void setAct(String act) {
        Act = act;
    }

    public String getTable() {
        return Table;
    }

    public void setTable(String table) {
        Table = table;
    }

    public Timestamp getTime() {
        return Time;
    }

    public void setTime(Timestamp time) {
        Time = time;
    }
}
