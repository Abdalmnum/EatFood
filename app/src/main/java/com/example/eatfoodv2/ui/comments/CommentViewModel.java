package com.example.eatfoodv2.ui.comments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eatfoodv2.Model.CommentModel;
import com.example.eatfoodv2.Model.FoodModel;

import java.util.List;

public class CommentViewModel extends ViewModel {
private MutableLiveData<List<CommentModel>> mutableLiveDataCommentList;

    public CommentViewModel() {

        mutableLiveDataCommentList = new MutableLiveData<>();

    }

    public MutableLiveData<List<CommentModel>> getMutableLiveDataCommentList() {
        return mutableLiveDataCommentList;
    }

   public void setCommentList(List<CommentModel> commentList)
   {
       mutableLiveDataCommentList.setValue(commentList);
   }
}

