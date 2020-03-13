package com.example.eatfoodv2.ui.foodlist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eatfoodv2.Common.common;
import com.example.eatfoodv2.Model.FoodModel;

import java.util.List;

public class FoodListViewModel extends ViewModel {

    private MutableLiveData<List<FoodModel>> mutableLiveData;

    public FoodListViewModel() {


    }

    public MutableLiveData<List<FoodModel>> getMutableLiveData() {

        if(mutableLiveData  == null)

            mutableLiveData = new MutableLiveData<>();
            mutableLiveData.setValue(common.catagorySelected.getFoods());



        return mutableLiveData;
    }
}