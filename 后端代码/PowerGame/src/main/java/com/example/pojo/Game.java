package com.example.pojo;

import java.sql.Blob;

public class Game {
    private String gameName;
    private String gameDetail;
    private Double price;
    private Integer stock;
    private String type;
    private String ownerName;
    private Blob img;
    private Integer isOnSale;

    public Game() {
    }

    public Game(String gameName, String gameDetail, Double price, Integer stock, String type, String ownerName, Blob img, Integer isOnSale) {
        this.gameName = gameName;
        this.gameDetail = gameDetail;
        this.price = price;
        this.stock = stock;
        this.type = type;
        this.ownerName = ownerName;
        this.img = img;
        this.isOnSale = isOnSale;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameName='" + gameName + '\'' +
                ", gameDetail='" + gameDetail + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", type='" + type + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", img=" + img +
                ", isOnSale=" + isOnSale +
                '}';
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameDetail() {
        return gameDetail;
    }

    public void setGameDetail(String gameDetail) {
        this.gameDetail = gameDetail;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Blob getImg() {
        return img;
    }

    public void setImg(Blob img) {
        this.img = img;
    }

    public Integer getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Integer isOnSale) {
        this.isOnSale = isOnSale;
    }
}
