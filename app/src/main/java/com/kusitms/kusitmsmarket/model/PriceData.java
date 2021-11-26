package com.kusitms.kusitmsmarket.model;

public class PriceData {

    private int img;
    private String category;
    private String unit;
    private String price;

    public PriceData() {
    }

    public PriceData(int img, String category, String unit, String price) {
        this.img = img;
        this.category = category;
        this.unit = unit;
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
