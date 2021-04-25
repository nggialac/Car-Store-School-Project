package com.example.de_tai_di_dong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("Id")
    @Expose
    private int Id = 0;

    @SerializedName("Description")
    @Expose
    private String Description = "";
    @SerializedName("Image1")
    @Expose
    private String Image1 = "";
    @SerializedName("Name")
    @Expose
    private String Name = "";
    @SerializedName("Price")
    @Expose
    private int Price = 0;
    @SerializedName("ProGroupId")
    @Expose
    private int ProGroupId = 0;
    @SerializedName("Stock")
    @Expose
    private int Stock = 0;



    public Product() {
    }

    public Product(int id, String image1, String name, int price, int proGroupId, int stock) {
        Id = id;

        Image1 = image1;
        Name = name;
        Price = price;
        ProGroupId = proGroupId;
        Stock = stock;

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }



    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image1) {
        Image1 = image1;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getProGroupId() {
        return ProGroupId;
    }

    public void setProGroupId(int proGroupId) {
        ProGroupId = proGroupId;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

}