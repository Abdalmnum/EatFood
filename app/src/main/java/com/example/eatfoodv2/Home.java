package com.example.eatfoodv2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.eatfoodv2.EventBus.CatagoryClick;
import com.example.eatfoodv2.EventBus.FoodItemClick;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    private DrawerLayout drawer;
    private  NavigationView navigationView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
         drawer = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_menu,R.id.nav_food_detail,
                R.id.nav_tools, R.id.nav_share, R.id.nav_food_list)
                .setDrawerLayout(drawer)
                .build();
         navController = Navigation.findNavController(this, R.id.content_frame);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.content_frame);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
      menuItem.setChecked(true);
      drawer.closeDrawers();

      switch(menuItem.getItemId())
      {
          case R.id.nav_home:
              navController.navigate(R.id.nav_home);
              break;
          case R.id.nav_menu:
              navController.navigate(R.id.nav_menu);
              break;


      }

        return true;
    }


    //EventBus
    @Override
    protected void onStart() {

        super.onStart();

        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);


    }


    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onCatagorySelected(CatagoryClick event)
    {

        if(event.isSuccess())
        {
           // Toast.makeText(this, "click to"+event.getCatagoryModel().getName(), Toast.LENGTH_SHORT).show();

            navController.navigate(R.id.nav_food_list);
        }
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onFoodSelected(FoodItemClick event)
    {

        if(event.isSuccess())
        {
            // Toast.makeText(this, "click to"+event.getCatagoryModel().getName(), Toast.LENGTH_SHORT).show();

            navController.navigate(R.id.nav_food_detail);
        }
    }

}
