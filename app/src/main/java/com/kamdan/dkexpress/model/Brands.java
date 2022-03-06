package com.kamdan.dkexpress.model;


public class Brands {
    private String Name;
    private String Id;
    private String Image;

    public Brands() {
        Name = "";
        Id = "";
        this.Image = "";
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }
}
