package com.example.pojo;

import java.sql.Timestamp;

public class Order {
    private String username;
    private String gameName;
    private Integer amount;
    private Double unitPrice;
    private Double sumPrice;
    private Timestamp orderTime;
    private String ownerName;

    @Override
    public String toString() {
        return "Order{" +
                "username='" + username + '\'' +
                ", gameName='" + gameName + '\'' +
                ", amount=" + amount +
                ", unitPrice=" + unitPrice +
                ", sumPrice=" + sumPrice +
                ", orderTime=" + orderTime +
                ", ownerName='" + ownerName + '\'' +
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
