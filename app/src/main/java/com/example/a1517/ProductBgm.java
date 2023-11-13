package com.example.a1517;

public class ProductBgm {

    String title;
    int price;
    Boolean isPurchased=false; // 보유 중인지 아닌지

    public ProductBgm(){}
    public ProductBgm(String title, int price){
        this.title=title;
        this.price=price;
        isPurchased=false;
    }

    public int getPrice(){
        return price;
    }
    public void setPrice(int price){
        this.price=price;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public Boolean getIsPurchased(){return isPurchased;}
    public void setIsPurchased(Boolean value){this.isPurchased=value;}
}
