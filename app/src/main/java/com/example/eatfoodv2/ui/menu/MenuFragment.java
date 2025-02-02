package com.example.eatfoodv2.ui.menu;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatfoodv2.Adapter.MyCatagoriesAdapter;
import com.example.eatfoodv2.Common.SpaceItemDecoration;
import com.example.eatfoodv2.Common.common;
import com.example.eatfoodv2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class MenuFragment extends Fragment {

    private MenuViewModel menuViewModel;
    Unbinder unbinder;

    @BindView(R.id.recycler_menu)
    RecyclerView recycler_menu;

    AlertDialog dialog;
    LayoutAnimationController layoutAnimationController;
    MyCatagoriesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu, container, false);


        unbinder = ButterKnife.bind(this, root);

        initView();

        menuViewModel.getMassageErorr().observe(getViewLifecycleOwner(), s -> {

            Toast.makeText(getContext(), "" + s, Toast.LENGTH_SHORT).show();
            dialog.dismiss();

        });
        menuViewModel.getCatagoryListMultible().observe(getViewLifecycleOwner(), catagoryModels -> {
            dialog.dismiss();
            adapter = new MyCatagoriesAdapter(getContext(), catagoryModels);
            recycler_menu.setAdapter(adapter);
            recycler_menu.setLayoutAnimation(layoutAnimationController);
        });


        return root;
    }

    private void initView() {
        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();

        dialog.show();

        layoutAnimationController = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_item_from_left);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if (adapter != null) {
                    switch (adapter.getItemViewType(position)) {
                        case common.DEFAULT_COULMN_COUNT:
                            return 1;
                        case common.FULL_WIDTH_COULMN:
                            return 2;
                        default:
                            return -1;
                    }
                }
                return -1;
            }
        });
        recycler_menu.setLayoutManager(layoutManager);
        recycler_menu.addItemDecoration(new SpaceItemDecoration(0));

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 2");
    }
}
