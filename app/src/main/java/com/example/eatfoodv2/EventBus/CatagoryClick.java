package com.example.eatfoodv2.EventBus;

import com.example.eatfoodv2.Model.CatagoryModel;

public class CatagoryClick {

    private boolean success;
    private CatagoryModel catagoryModel;

    public CatagoryClick(boolean success, CatagoryModel catagoryModel) {
        this.success = success;
        this.catagoryModel = catagoryModel;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public CatagoryModel getCatagoryModel() {
        return catagoryModel;
    }

    public void setCatagoryModel(CatagoryModel catagoryModel) {
        this.catagoryModel = catagoryModel;
    }
}
