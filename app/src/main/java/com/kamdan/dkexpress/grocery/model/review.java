package com.kamdan.dkexpress.grocery.model;

public class review {
    String image;
    String name;
    String text;

    public String getNo_star() {
        return no_star;
    }

    public void setNo_star(String no_star) {
        this.no_star = no_star;
    }

    String no_star;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public review(){
        image = "";
        name = "";
        text = "";
        no_star = "0";
    }
}
