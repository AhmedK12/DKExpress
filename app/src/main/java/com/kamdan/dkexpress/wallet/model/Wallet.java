package com.kamdan.dkexpress.wallet.model;

import java.util.ArrayList;

public class Wallet {
    private String wallet_id;
    private String user_id;
    private String balance;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Transection> getTransections() {
        return transections;
    }

    public void setTransections(ArrayList<Transection> transections) {
        this.transections = transections;
    }

    public ArrayList<Creditscards> getCreditscards() {
        return creditscards;
    }

    public void setCreditscards(ArrayList<Creditscards> creditscards) {
        this.creditscards = creditscards;
    }

    private String image;
    private ArrayList<Transection> transections;
    private ArrayList<Creditscards> creditscards;

    public Wallet(String wallet_id,String user_id, String balance, String image){
        this.image = image;
        this.balance = balance;
        this.user_id = user_id;
        this.wallet_id = wallet_id;
        this.transections = new ArrayList<>(0);
        this.creditscards = new ArrayList<>(0);
    }


    public Wallet(){
        this.balance="";
        this.wallet_id="";
        this.user_id ="";
        this.image = "";
    }


    public String getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(String wallet_id) {
        this.wallet_id = wallet_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
