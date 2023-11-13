package com.example.a1517;

public class ProductTree {
    int resId;
    int price;

    Boolean isPurchased=false;

    public ProductTree(){}
    public ProductTree(int resId, int price){
        this.resId=resId;
        this.price=price;
    }

    public int getPrice(){
        return price;
    }
    public void setPrice(int price){
        this.price=price;
    }
    public int getResId(){
        return resId;
    }
    public void setResId(int resId){
        this.resId=resId;
    }
    public void setIsPurchased(Boolean value){isPurchased=value;}
    public Boolean getIsPurchased(){return isPurchased;}

}
