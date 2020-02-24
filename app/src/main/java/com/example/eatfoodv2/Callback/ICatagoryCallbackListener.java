package com.example.eatfoodv2.Callback;

import com.example.eatfoodv2.Model.CatagoryModel;

import java.util.List;

public interface ICatagoryCallbackListener {

    void onCatagoryLoadSuccess(List<CatagoryModel> CatagoryModels);
    void onCatagoryLoadFailed(String massege);
}
