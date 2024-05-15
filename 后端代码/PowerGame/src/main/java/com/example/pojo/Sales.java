package com.example.pojo;

public class Sales {
    private String gameName;
    private Integer amount;
    private Double sumPrice;
    private String type;

    public Sales(String gameName, Integer amount, Double sumPrice, String type) {
        this.gameName = gameName;
        this.amount = amount;
        this.sumPrice = sumPrice;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Sales{" +
                "gameName='" + gameName + '\'' +
                ", amount=" + amount +
                ", sumPrice=" + sumPrice +
                ", type='" + type + '\'' +
                '}';
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
