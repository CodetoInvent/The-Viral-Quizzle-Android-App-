package com.dream.malik.theviralquizzle.model;

/**
 * Created by malik on 2/17/2018.
 */

public class Category {//getters and setters for catname and catimg
    private  String Name;
    private  String Image;

    public Category() {

    }

    public Category(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
