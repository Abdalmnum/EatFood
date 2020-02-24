package com.example.eatfoodv2.Model;

import java.util.List;

public class CatagoryModel {
    private String menu_id,name,image;
    private List<FoodModel> foods;

    public CatagoryModel() {
    }

    public CatagoryModel(String menu_id, String name, String image, List<FoodModel> foods) {
        this.menu_id = menu_id;
        this.name = name;
        this.image = image;
        this.foods = foods;
    }

    public List<FoodModel> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodModel> foods) {
        this.foods = foods;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
