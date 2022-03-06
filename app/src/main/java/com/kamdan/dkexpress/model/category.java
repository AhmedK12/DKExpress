package com.kamdan.dkexpress.model;

public class category {
    private String  category;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String image;
    private String key;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public category(){
        this.category = "";
        this.image = "";
        this.key = "";
    }
}
