package com.example.eatfoodv2.Callback;

import com.example.eatfoodv2.Model.BestDealModel;

import java.util.List;

public interface IBestDealListener {
    void onBestDealLoadSuccess(List<BestDealModel> bestDealModels);
    void onBestDealLoadFailed(String massege);
}
