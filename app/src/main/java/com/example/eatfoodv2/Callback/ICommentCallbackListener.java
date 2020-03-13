package com.example.eatfoodv2.Callback;

import com.example.eatfoodv2.Model.CommentModel;

import java.util.List;

public interface ICommentCallbackListener {

    void onCommentLoadSuccess(List<CommentModel> commentModels);
    void onCommentLoadFailed(String message);

}

