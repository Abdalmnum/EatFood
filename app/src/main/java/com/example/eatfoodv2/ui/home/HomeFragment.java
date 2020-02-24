package com.example.eatfoodv2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.asksira.loopingviewpager.LoopingViewPager;
import com.example.eatfoodv2.Adapter.MyBestDealAdapter;
import com.example.eatfoodv2.Adapter.MyPopularCatagoriesAdapter;
import com.example.eatfoodv2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    Unbinder unbinder;

    @BindView(R.id.recycler_popular)
    RecyclerView recycler_popular;

    @BindView(R.id.viewpager)
    LoopingViewPager viewpager;

    @BindView(R.id.popular_catagories_txt)
    TextView popular_catagories_txt;

    @BindView(R.id.best_deals_txt)
    TextView best_deal_txt;
    LayoutAnimationController layoutAnimationController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.home_fragment, container, false);

        unbinder = ButterKnife.bind(this,root);

        init();

        homeViewModel.getPopularList().observe((LifecycleOwner) getContext(), popularCatagoryModels -> {
            MyPopularCatagoriesAdapter adapter = new MyPopularCatagoriesAdapter(getContext(),popularCatagoryModels);
            recycler_popular.setAdapter(adapter);
            recycler_popular.setLayoutAnimation(layoutAnimationController);
        });

        homeViewModel.getBestDealList().observe((LifecycleOwner) getContext(), bestDealModels -> {
            MyBestDealAdapter adapter = new MyBestDealAdapter(getContext(),bestDealModels,true);
            viewpager.setAdapter(adapter);
        });

        return root;
    }

    private void init() {
        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(),R.anim.layout_item_from_left);
        recycler_popular.setHasFixedSize(true);
        recycler_popular.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Home");

        popular_catagories_txt.setText("Popular Catagories");
        popular_catagories_txt.setVisibility(View.VISIBLE);

        best_deal_txt.setText("Best Deal ");
        best_deal_txt.setVisibility(View.VISIBLE);
    }



    public void onResume() {
        viewpager.resumeAutoScroll();

        super.onResume();

    }

    @Override
    public void onPause() {
        viewpager.pauseAutoScroll();

        super.onPause();
    }
}


