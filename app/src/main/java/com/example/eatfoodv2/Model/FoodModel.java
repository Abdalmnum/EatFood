package com.example.eatfoodv2.Model;

import java.util.List;

public class FoodModel {
    private String name,image,id,description;
    private Long Price;
    private List<AddonModel> addon;
    private List<SizeModel> sizeModelList;

    public FoodModel() {
    }

    public FoodModel(String name, String image, String id, String description, Long price, List<AddonModel> addon, List<SizeModel> sizeModelList) {
        this.name = name;
        this.image = image;
        this.id = id;
        this.description = description;
        Price = price;
        this.addon = addon;
        this.sizeModelList = sizeModelList;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }

    public List<AddonModel> getAddon() {
        return addon;
    }

    public void setAddon(List<AddonModel> addon) {
        this.addon = addon;
    }

    public List<SizeModel> getSizeModelList() {
        return sizeModelList;
    }

    public void setSizeModelList(List<SizeModel> sizeModelList) {
        this.sizeModelList = sizeModelList;
    }
}
