package com.example.pojo;

import java.sql.Time;
import java.sql.Timestamp;

public class LoginLog {
    private String username;
    private String loginIp;
    private Timestamp loginTime;
    private Timestamp disLoginTime;
    private Integer type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getDisLoginTime() {
        return disLoginTime;
    }

    public void setDisLoginTime(Timestamp disLoginTime) {
        this.disLoginTime = disLoginTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LoginLog{" +
                "username='" + username + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", loginTime=" + loginTime +
                ", disLoginTime=" + disLoginTime +
                ", type=" + type +
                '}';
    }
}
