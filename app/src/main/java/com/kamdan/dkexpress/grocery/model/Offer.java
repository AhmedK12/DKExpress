package com.kamdan.dkexpress.grocery.model;

public class Offer {
    private String image;
    private String category;
    private String offer;

    public String getCategory() {
        return category;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    private String active;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public Offer(){
        this.image="";
        this.category = "NOT AVAILABLE";
        this.active = "false";
        this.offer = "60";

    }
}
