package com.kamdan.dkexpress.model;

import java.util.ArrayList;

public class Category {
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
    }

    public Category(){
        this.category = "DKExpress";
        this.products = new ArrayList<>(0);
    }
    public Category(Products products){
        this.category = products.getCategory();
        this.products = new ArrayList<>(0);
        this.products.add(products);
    }

    private String category;
    private ArrayList<Products> products;

    public void add_product(Products product){
        this.products.add(product);
    }
}
