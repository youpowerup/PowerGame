package com.example.pojo;

public class ShoppingCar {
    private String username;
    private String gameName;
    private Double unitPrice;
    private Integer amount;
    private Double sumPrice;

    @Override
    public String toString() {
        return "ShoppingCarLog{" +
                "username='" + username + '\'' +
                ", gameName='" + gameName + '\'' +
                ", unitPrice=" + unitPrice +
                ", amount=" + amount +
                ", sumPrice=" + sumPrice +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
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
