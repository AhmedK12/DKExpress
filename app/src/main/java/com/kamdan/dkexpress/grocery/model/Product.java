package com.kamdan.dkexpress.grocery.model;

import java.io.Serializable;
import java.util.Map;

public class Product implements Serializable {
    private String id;
    private String brand_name;
    private String availability;
    private String image;
    private String category;
    private String size;
    private String popularity;
    private String title;
    private String subcategory;
    private String cake;

    public String getCake() {
        return cake;
    }

    public void setCake(String cake) {
        this.cake = cake;
    }

    public String getFreed_elivery_amount() {
        return freed_elivery_amount;
    }

    public void setFreed_elivery_amount(String freed_elivery_amount) {
        this.freed_elivery_amount = freed_elivery_amount;
    }

    private String freed_elivery_amount;

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    private String offer;

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNewprice() {
        return newprice;
    }

    public void setNewprice(String newprice) {
        this.newprice = newprice;
    }

    public String getMaxquantity() {
        return maxquantity;
    }

    public void setMaxquantity(String maxquantity) {
        this.maxquantity = maxquantity;
    }

    private String price;
    private String newprice;
    private String maxquantity;
    private Map<String, String> timestamp;

    public Map<String, String> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp1(Map<String, String> timestamp) {
        this.timestamp = timestamp;
    }
    public void setTimestamp(float timestamp) {
        this.timestamp = null;
    }
    public Product (){
        this.price = "0.0";
        this.availability = "notavailable";
        this.brand_name = "";
        this.category = "";
        this.id = "";
        this.maxquantity = "8";
        this.newprice = "";
        this.image = "";
        this.size = "";
        this.title = "";
        this.popularity = "0";
        this.subcategory = "";
        this.offer = "0";
        this.freed_elivery_amount = "1";
        this.cake = "1";
    }
}
