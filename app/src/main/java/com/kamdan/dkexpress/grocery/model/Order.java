package com.kamdan.dkexpress.grocery.model;

import java.util.ArrayList;
import java.util.Map;

public class Order {
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    private String type;
    private String date;
    private String id;
    private String products;
    private String delivery_date;

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String total_amount;

    public String getWallet_use() {
        return wallet_use;
    }

    public void setWallet_use(String wallet_use) {
        this.wallet_use = wallet_use;
    }

    private String wallet_use;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public String getQuantities() {
        return quantities;
    }

    public void setQuantities(String quantities) {
        this.quantities = quantities;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    private String status;
    private String ids;
    private String names;
    private String sizes;
    private String quantities;
    private String prices;

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    private String payment_method;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private String due;
    private String slot;
    private Map<String, String> timestamp;
    private String user;
    public Map<String, String> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp1(Map<String, String> timestamp) {
        this.timestamp = timestamp;
    }
    public void setTimestamp(float timestamp) {
        this.timestamp = null;
    }

    public Order(){
        ids = "";
        names = "";
        quantities = "";
        prices = "";
        sizes = "";
        this.id="";
        this.due="";
        this.products = "";
        this.slot = "";
        this.status = "";
        this.total_amount="";
        this.user="";
        this.payment_method = "COD";
        this.date = "12-10-199";
        this.wallet_use = "0";
        this.type = "normal";
    }
}
