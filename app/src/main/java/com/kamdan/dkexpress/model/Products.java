package com.kamdan.dkexpress.model;

import android.content.Context;
import android.database.Cursor;


import com.kamdan.dkexpress.products.Product;

import java.io.Serializable;

public class Products implements Serializable {
    private   String id;

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    private String benefit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
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

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String title;
    private String price;
    private String newprice;

    public String getNewprice() {
        return newprice;
    }

    public void setNewprice(String newprice) {
        this.newprice = newprice;
    }

    private String brand_id;
    private String availability;
    private String image;
    private String scheme;
    private String offer;
    private String category;
    private String description;
    private String size;
    private String old_price_box;
    private String old_price_layer;
    private String layer;
    private String popularity;

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOld_price_box() {
        return old_price_box;
    }

    public void setOld_price_box(String old_price_box) {
        this.old_price_box = old_price_box;
    }

    public String getOld_price_layer() {
        return old_price_layer;
    }

    public void setOld_price_layer(String old_price_layer) {
        this.old_price_layer = old_price_layer;
    }

    public String getNew_price_box() {
        return new_price_box;
    }

    public void setNew_price_box(String new_price_box) {
        this.new_price_box = new_price_box;
    }

    public String getNew_price_layer() {
        return new_price_layer;
    }

    public void setNew_price_layer(String new_price_layer) {
        this.new_price_layer = new_price_layer;
    }

    private String new_price_box;
    private String new_price_layer;
    private String description_box;
    private String description_layer;

    public String getDescription_box() {
        return description_box;
    }

    public void setDescription_box(String description_box) {
        this.description_box = description_box;
    }

    public String getDescription_layer() {
        return description_layer;
    }

    public void setDescription_layer(String description_layer) {
        this.description_layer = description_layer;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public Products() {
        this.id="";
        this.title="Product";
        this.price="210";
        this.brand_id="Glucorine";
        this.availability="Available";
        this.image = "";
        this.scheme = "0";
        this.offer = "0%";
        this.category = "Coldrinks";
        this.description = "kl";
        this.size="";
        this.old_price_box="0.00";
        this.old_price_layer=null;
        this.new_price_box="null";
        this.new_price_layer="null";
        this.description_box = "null";
        this.description_layer = "null";
        this.layer = " Layer";
        this.popularity = "0";
    }


}
