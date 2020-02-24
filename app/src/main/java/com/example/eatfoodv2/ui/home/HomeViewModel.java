package com.example.eatfoodv2.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.eatfoodv2.Callback.IBestDealListener;
import com.example.eatfoodv2.Callback.IPopularCallbackListener;
import com.example.eatfoodv2.Common.common;
import com.example.eatfoodv2.Model.BestDealModel;
import com.example.eatfoodv2.Model.PopularCatagoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel implements IPopularCallbackListener, IBestDealListener {

    private MutableLiveData<List<PopularCatagoryModel>> popularList;
    private MutableLiveData<List<BestDealModel>> bestDealList;

    private MutableLiveData<String> massageErorr;

    private IPopularCallbackListener iPopularCallbackListener;
    private IBestDealListener iBestDealListener;

    public HomeViewModel() {

        iPopularCallbackListener = this;
        iBestDealListener = this;

    }


    public MutableLiveData<List<BestDealModel>> getBestDealList() {
        if (bestDealList == null) {
            bestDealList = new MutableLiveData<>();
            massageErorr = new MutableLiveData<>();

            loadBestDealList();
        }
        return bestDealList;
    }

    private void loadBestDealList() {

        List<BestDealModel> tempList = new ArrayList<>();

        DatabaseReference bestRef = FirebaseDatabase.getInstance().getReference(common.BEST_DEALS_KEY);
        bestRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    BestDealModel bestDealModel = itemSnapshot.getValue(BestDealModel.class);
                    tempList.add(bestDealModel);
                }

                iBestDealListener.onBestDealLoadSuccess(tempList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iBestDealListener.onBestDealLoadFailed(databaseError.getMessage());
            }
        });

    }


    public MutableLiveData<List<PopularCatagoryModel>> getPopularList() {
        if (popularList == null) {
            popularList = new MutableLiveData<>();
            massageErorr = new MutableLiveData<>();

            loadPopularList();
        }
        return popularList;
    }

    private void loadPopularList() {

        List<PopularCatagoryModel> tempList = new ArrayList<>();

        DatabaseReference popularRef = FirebaseDatabase.getInstance().getReference(common.POPULAR_CATAGORY_KEY);

        popularRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    PopularCatagoryModel catagoryModel = itemSnapshot.getValue(PopularCatagoryModel.class);
                    tempList.add(catagoryModel);
                }

                iPopularCallbackListener.onPopularLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iPopularCallbackListener.onPopularLoadFailed(databaseError.getMessage());
            }
        });
    }

    public MutableLiveData<String> getMassageErorr() {
        return massageErorr;
    }

    @Override
    public void onPopularLoadSuccess(List<PopularCatagoryModel> popularCatagoryModels) {

        popularList.setValue(popularCatagoryModels);

    }


    @Override
    public void onPopularLoadFailed(String massege) {
        massageErorr.setValue(massege);

    }

    @Override
    public void onBestDealLoadSuccess(List<BestDealModel> bestDealModels) {
        bestDealList.setValue(bestDealModels);
    }

    @Override
    public void onBestDealLoadFailed(String massege) {
        massageErorr.setValue(massege);
    }
}
