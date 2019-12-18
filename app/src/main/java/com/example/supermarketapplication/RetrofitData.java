package com.example.supermarketapplication;

import com.google.gson.annotations.SerializedName;

public class RetrofitData {

    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;
    @SerializedName("imageurl")
    private String imageurl;
    @SerializedName("height")
    private int height;
    @SerializedName("width")
    private int width;
    @SerializedName("price")
    private double price;
    @SerializedName("rating")
    private int rating;
    @SerializedName("description")
    private String description;


    @SerializedName("titlear")
    private String titlear;
    @SerializedName("typear")
    private String typear;
    @SerializedName("descriptionar")
    private String descriptionar;




    public RetrofitData(String title, String type, String imageurl, int height,
                        int width, double price, int rating , String description, String titlear , String typear , String descriptionar) {
        this.title = title;
        this.type = type;
        this.imageurl = imageurl;
        this.height = height;
        this.width = width;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.titlear = titlear;
        this.descriptionar = descriptionar;
        this.typear = typear;


    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitlear() {
        return titlear;
    }

    public void setTitlear(String titlear) {
        this.titlear = titlear;
    }

    public String getTypear() {
        return typear;
    }

    public void setTypear(String typear) {
        this.typear = typear;
    }

    public String getDescriptionar() {
        return descriptionar;
    }

    public void setDescriptionar(String descriptionar) {
        this.descriptionar = descriptionar;
    }
}
