package com.example.eatfoodv2.Callback;

import com.example.eatfoodv2.Model.PopularCatagoryModel;

import java.util.List;

public interface IPopularCallbackListener {

    void onPopularLoadSuccess(List<PopularCatagoryModel> popularCatagoryModels);
    void onPopularLoadFailed(String massege);
}
