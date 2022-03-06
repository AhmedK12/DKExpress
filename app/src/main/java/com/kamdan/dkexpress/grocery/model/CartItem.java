package com.kamdan.dkexpress.grocery.model;

import java.sql.Date;

public class CartItem {
    private String productname;
    private String productid;
    private String price;
    private String size;

    public int getDeliver_charge() {
        return deliver_charge;
    }

    public void setDeliver_charge(int deliver_charge) {
        this.deliver_charge = deliver_charge;
    }

    private int deliver_charge;
    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    private String popularity;

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    private String subtotal;
    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return (Date) date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private String image;
    private String quantity;
    private java.util.Date date;
    public CartItem(){
        this.image = "";
        this.price = "";
        this.productid = "";
        this.productname = "";
        this.quantity = "";
        this.size = "";
        this.date = null;
        popularity = "1";
        deliver_charge = 249;

    }

    public CartItem(String productname, String productid, String price, String size, String image, String quantity,String subtotal,String popularity,int delivery_charge) {
        this.productname = productname;
        this.productid = productid;
        this.price = price;
        this.size = size;
        this.image = image;
        this.quantity = quantity;
        this.date = new java.util.Date();
        this.subtotal = subtotal;
        this.popularity = popularity;
        this.deliver_charge = delivery_charge;
    }
}
