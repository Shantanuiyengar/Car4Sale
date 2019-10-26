package com.shantanu.car4sale;

import android.graphics.Bitmap;

public class Car {
    int id;
    String name,price,model;
    Bitmap image;

    Car(int id, String name, String model, String price, Bitmap image){
        this.id = id;
        this.name = name;
        this.model = model;
        this.price = price;
        this.image = image;
    }
    Car(){

    }
}
