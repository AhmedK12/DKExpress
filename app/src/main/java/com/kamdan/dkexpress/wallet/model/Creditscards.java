package com.kamdan.dkexpress.wallet.model;

public class Creditscards {
   private String card_no, card_type,card_holder_name,exp_date,mfd_date, date_of_birth;

   public Creditscards(){
       card_no = "";
       card_holder_name = "";
       card_type = "";
       exp_date = "";
       mfd_date = "";
       date_of_birth = "";
   }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getCard_type() {
        return card_type;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCard_holder_name() {
        return card_holder_name;
    }

    public void setCard_holder_name(String card_holder_name) {
        this.card_holder_name = card_holder_name;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getMfd_date() {
        return mfd_date;
    }

    public void setMfd_date(String mfd_date) {
        this.mfd_date = mfd_date;
    }
}
