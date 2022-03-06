package com.kamdan.dkexpress.products;

import android.app.Application;

import com.kamdan.dkexpress.grocery.adapter.Product_lite;
import com.kamdan.dkexpress.grocery.model.CartItem;
import com.kamdan.dkexpress.grocery.model.MyProfile;
import com.kamdan.dkexpress.grocery.model.Product;
import com.kamdan.dkexpress.model.Product_Price;
import com.kamdan.dkexpress.model.Products;
import com.kamdan.dkexpress.model.User;

import java.util.ArrayList;

public class Helper extends Application {


//    products
    public ArrayList<Product> Products = new ArrayList<>(0);
    public String Keywords;
    public String Available_Amount;
    public float FREE_DELIVERY_MINIMUM_AMOUNT_I = 249;
    public float FREE_DELIVERY_MINIMUM_AMOUNT_II = 549;
    public float DELIVERY_CHARGE_NEAR;
    public float DELIVERY_CHARGE_FAR = 39;
    public ArrayList<Product_Price> Prods;
    public ArrayList<CartItem> Items;
    public User user;
    public MyProfile myProfile;
    public Boolean Box;
    public boolean Retailer = false;
    public String Location;
    public Helper() {
        Prods = new ArrayList<Product_Price>();
        Items = new ArrayList<>(0);
        myProfile = new MyProfile();
        DELIVERY_CHARGE_NEAR = 19;
        Available_Amount = "";
    }
    public void clear(){
        Prods.clear();
        Items.clear();
    }
    public void delet(int postion){
        Prods.remove(postion);
    }
    public String Total_Price(){
        float price = 0;
        for (Product_Price p: Prods){
            price = price + Integer.parseInt(p.Quantity)*Float.parseFloat(p.Price_Per_Item.replace("₹",""));
        }
        return Float.toString(price);
    }
    public int sze(){
        return Prods.size();
    }

    public boolean isAdded(String Id){
        try {
            for (Product_Price p : Prods) {
                if (p.Product_Id.equals(Id))
                    return true;
            }
        }catch(Exception e){
            return false;
        }
        return false;
    }


    public String Totalprice(){
        float total_amount = 0;
        for(CartItem c: Items){
            float q,p;
            try {
                q = Float.parseFloat(c.getQuantity());
            }catch (Exception e){
                q=0;

            }
            try {
                p = Float.parseFloat(c.getPrice().replace("₹","").trim());
            }catch (Exception e){
                p=0;
            }
            total_amount = total_amount + p*q;
        }
        return Float.toString(total_amount);
    }

    public ArrayList<Integer> getOffer(int offer,String cat){
        ArrayList<Integer> data = new ArrayList<>(0);



            for (int i=0;i<Products.size();i++) {
                if (Products.get(i).getSubcategory().contains(cat) && Integer.parseInt(Products.get(i).getOffer())<=offer) {
                    data.add(i);
                }
            }


        return data;
}

    public ArrayList<Integer> getProduct(String value){
        ArrayList<Integer> arr = new ArrayList<>(0);
        for(int i=0;i<Products.size();i++){
            if(Products.get(i).getSubcategory().contains(value))
                arr.add(i);
        }
        return arr;
    }

    public ArrayList<Product_lite> getProducts(String category){
        ArrayList<Product_lite> arr = new ArrayList<>(0);
        for(int i=0;i<Products.size();i++){
            if(!Products.get(i).getCategory().equals("") &&Products.get(i).getCategory().toLowerCase().contains(category.toLowerCase())||category.toLowerCase().contains(Products.get(i).getCategory().toLowerCase())) {
                Product_lite p = new Product_lite(i, Products.get(i).getPopularity(), Products.get(i).getId(), Products.get(i).getOffer(),Products.get(i).getTitle().toLowerCase());
                arr.add(p);
            }
        }
        return arr;
    }

    public String get_image(String id){
        for(Product p : Products){
            if(p.getTitle().equals(id)) {
                return p.getImage();
            }
        }
        return "";
    }

}