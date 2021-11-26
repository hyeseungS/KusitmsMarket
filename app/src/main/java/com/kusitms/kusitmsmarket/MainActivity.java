package com.kusitms.kusitmsmarket;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.kusitms.kusitmsmarket.databinding.ActivityMainBinding;
import com.kusitms.kusitmsmarket.ui.market.ReviewActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    public static Context mContext;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        token = intent.getStringExtra("userToken");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Status Bar 높이만큼 Padding 부여
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_market, R.id.navigation_price, R.id.navigation_mypage)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        mContext = this;

    }

    public String getUserToken() {
        return token;
    }

    //status bar의 높이 계산
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }

    // MarketFragment(내시장)에서 MarketDetailFragment로 이동
    public void moveToDetail(){
        navController.navigate(R.id.action_navigation_market_to_market_detail);
    }

    // home에서 마커 클릭하면 MarketDetailFragment로 이동
    public void moveToMarker(){
        navController.navigate(R.id.action_navigation_home_to_market_detail);
    }


}