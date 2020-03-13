package com.example.eatfoodv2.ui.menu;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eatfoodv2.Callback.ICatagoryCallbackListener;
import com.example.eatfoodv2.Common.common;
import com.example.eatfoodv2.Model.CatagoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuViewModel extends ViewModel implements ICatagoryCallbackListener {

    private MutableLiveData<List<CatagoryModel>> catagoryListMultible;

    private MutableLiveData<String> massageErorr = new MutableLiveData<>();

    private ICatagoryCallbackListener iCatagoryCallbackListener;


    public MenuViewModel() {
        iCatagoryCallbackListener = this;
    }

    public MutableLiveData<List<CatagoryModel>> getCatagoryListMultible() {

        if (catagoryListMultible == null) {
            catagoryListMultible = new MutableLiveData<>();
            massageErorr = new MutableLiveData<>();

            loadCatagories();
        }

        return catagoryListMultible;
    }

    private void loadCatagories() {

        List<CatagoryModel> tmpList = new ArrayList<>();
        DatabaseReference catagoryRef = FirebaseDatabase.getInstance().getReference(common.CATAGORY_KEY);
        catagoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot itemSnapShot : dataSnapshot.getChildren()) {
                    CatagoryModel catagoryModel = itemSnapShot.getValue(CatagoryModel.class);
                    catagoryModel.setMenu_id(itemSnapShot.getKey());
                    tmpList.add(catagoryModel);



                }

                iCatagoryCallbackListener.onCatagoryLoadSuccess(tmpList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iCatagoryCallbackListener.onCatagoryLoadFailed(databaseError.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMassageErorr() {
        return massageErorr;
    }

    @Override
    public void onCatagoryLoadSuccess(List<CatagoryModel> CatagoryModels)  {
        catagoryListMultible.setValue(CatagoryModels);
    }

    @Override
    public void onCatagoryLoadFailed(String massege) {
        massageErorr.setValue(massege);

    }
}
