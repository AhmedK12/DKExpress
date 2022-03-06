package com.kamdan.dkexpress.grocery.adapter;

public class Product_lite {
    String id, offer,popularity,title;
    int index;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Product_lite(int index, String popularity, String id, String offer,String title){
        this.id = id;
        this.index = index;
        this.popularity = popularity;
        this.offer = offer;
        this.title = title;
    }
    public Product_lite(){
        id = "";
        offer = "";
        popularity = "";
        index = 0;
        title = "";
    }
}
