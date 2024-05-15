package com.example.pojo;

public class UserProfile {
    private String username;
    private Integer amount;
    private Double sumPrice;
    private String mostLove;
    private String secondLove;
    public UserProfile() {
    }
    public UserProfile(String username, Integer amount, Double sumPrice, String mostLove, String secondLove) {
        this.username = username;
        this.amount = amount;
        this.sumPrice = sumPrice;
        this.mostLove = mostLove;
        this.secondLove = secondLove;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "username='" + username + '\'' +
                ", amount=" + amount +
                ", sumPrice=" + sumPrice +
                ", mostLove='" + mostLove + '\'' +
                ", secondLove='" + secondLove + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getMostLove() {
        return mostLove;
    }

    public void setMostLove(String mostLove) {
        this.mostLove = mostLove;
    }

    public String getSecondLove() {
        return secondLove;
    }

    public void setSecondLove(String secondLove) {
        this.secondLove = secondLove;
    }
}
