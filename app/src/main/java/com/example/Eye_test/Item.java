package com.example.Eye_test;

public class Item {

    String imgPath;

    public Item(){

    }

    public Item(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgPath(){
        return imgPath;
    }

    public void setImgPath(String imgPath){
        this.imgPath = imgPath;
    }
}
