package com.kamdan.dkexpress.model;
import java.util.List;
import java.util.Map;


public class Order  {
    private String id;
    private String userid;
    private String dateorder;
    private String datarrival;
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

    public String getUserid() {
        return userid;
    }



    public void setUserid(String userid) {
        this.userid = userid;
    }

    private String price;
    private String names;
    private String quantity;
    private String status;
    private String pstatus;
    private String pdue;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public Order(){
        this.id = "";
        this.datarrival = "";
        this.dateorder = "";
        this.price = "";
        this.names = "";
        this.quantity = "";
        this.status = "";
        this.pstatus = "";
        this.pdue = "";
        this.image = "";
        this.address = "";
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateorder() {
        return dateorder;
    }

    public void setDateorder(String dateorder) {
        this.dateorder = dateorder;
    }

    public String getDatarrival() {
        return datarrival;
    }

    public void setDatarrival(String datarrival) {
        this.datarrival = datarrival;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String ids) {
        this.names = ids;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPstatus() {
        return pstatus;
    }

    public void setPstatus(String pstatus) {
        this.pstatus = pstatus;
    }

    public String getPdue() {
        return pdue;
    }

    public void setPdue(String pdue) {
        this.pdue = pdue;
    }

    public void copy(Order a, Order b){
        a.names = b.names;
        a.address = b.address;
        a.datarrival = b.datarrival;
        a.dateorder = b.dateorder;
        a.id = b.id;
        a.pdue = b.pdue;
        a.price = b.price;
        a.pstatus = b.pstatus;
        a.quantity = b.quantity;
        a.timestamp = b.timestamp;
        a.image = b.image;
    }


}
