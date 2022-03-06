package com.kamdan.dkexpress.grocery.model;


import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category(){

        this.category = "DKExpress";
        this.products = new ArrayList<>(0);
        this.id="0";
    }
    public Category(Product products, String id){
        this.category = products.getCategory();
        this.products = new ArrayList<>(0);
        this.products.add(products);
        this.id = id;
    }

    private String category;
    private ArrayList<Product> products;
    private String id;

    public void add_product(Product product){
        this.products.add(product);
    }

}
