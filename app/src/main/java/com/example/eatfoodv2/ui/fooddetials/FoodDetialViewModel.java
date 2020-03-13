package com.example.eatfoodv2.ui.fooddetials;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eatfoodv2.Common.common;
import com.example.eatfoodv2.Model.CommentModel;
import com.example.eatfoodv2.Model.FoodModel;

public class FoodDetialViewModel extends ViewModel {

    private MutableLiveData<FoodModel> foodModelMutableLiveData;
    private MutableLiveData<CommentModel> commentModelMutableLiveData;


    public void setCommentModel(CommentModel commentModel) {

        if (commentModelMutableLiveData != null) {
            commentModelMutableLiveData.setValue(commentModel);

        }
    }

    public MutableLiveData<CommentModel> getCommentModelMutableLiveData() {
        return commentModelMutableLiveData;
    }

    public FoodDetialViewModel() {

        foodModelMutableLiveData = new MutableLiveData<>();
        commentModelMutableLiveData = new MutableLiveData<>();


    }


    public MutableLiveData<FoodModel> getFoodModelMutableLiveData() {
        if (foodModelMutableLiveData == null)

            foodModelMutableLiveData = new MutableLiveData<>();

        foodModelMutableLiveData.setValue(common.selectedFood);
        return foodModelMutableLiveData;
    }

    public void setFoodModel(FoodModel foodModel) {
        if (foodModelMutableLiveData != null)
            foodModelMutableLiveData.setValue(foodModel);
    }
}