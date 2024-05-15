package com.example.pojo;

public class GameProfile {
    private String gameName;
    private String type;
    private Integer amount;
    private Double sumPrice;

    @Override
    public String toString() {
        return "GameProfile{" +
                "gameName='" + gameName + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", sumPrice=" + sumPrice +
                '}';
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
