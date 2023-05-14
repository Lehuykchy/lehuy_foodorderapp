package com.example.foodorderapp.activity;




import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.customview.widget.Openable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.BottomNavigationViewKt;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.example.foodorderapp.R;
//import com.example.foodorderapp.adapter.ViewPagerAdapter;
import com.example.foodorderapp.fragment.FragmentCart;
import com.example.foodorderapp.fragment.FragmentFavorite;
import com.example.foodorderapp.fragment.FragmentHome;
import com.example.foodorderapp.fragment.FragmentNotification;
import com.example.foodorderapp.fragment.FragmentProfileEditname;
import com.example.foodorderapp.fragment.FragmentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final int MY_REQUEST_CODE = 10;
    private BottomNavigationView mNavigationView;
    private ActionBar toolbar;
    private NavController navController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
        toolbar = getSupportActionBar();
        mNavigationView = findViewById(R.id.navigation_butom);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragmentHome();


//        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//        navController = navHostFragment.getNavController();
//
//        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_butom);
//
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_cart, R.id.navigation_favorite
//                , R.id.navigation_notifications, R.id.navigation_profile)
//                .build();
//
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        return NavigationUI.navigateUp(navController, (Openable) null) || super.onSupportNavigateUp();
//    }

//    }
    private View.OnCreateContextMenuListener onCreateContextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    };
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            int id = item.getItemId();
            FragmentManager fragmentManage = getSupportFragmentManager();
            Fragment currentFragment = fragmentManage.findFragmentById(R.id.frame_container);
            if(id == R.id.navigation_home){
//                fragment = new FragmentHome();
//                loadFragment(fragment);
                NavHostFragment navHostFragment = NavHostFragment.create(R.navigation.navigation_home);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frame_container, navHostFragment);
                transaction.remove(currentFragment);
                transaction.commit();
                return true;
            }else if(id == R.id.navigation_cart){
                NavHostFragment navHostFragment = NavHostFragment.create(R.navigation.navigation_cart);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frame_container, navHostFragment);
                transaction.remove(currentFragment);
                transaction.commit();
//                fragment = new FragmentCart();
//                loadFragment(fragment);
                return true;
            }else if(id == R.id.navigation_favorite){
                NavHostFragment navHostFragment = NavHostFragment.create(R.navigation.navigation_favorite);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frame_container, navHostFragment);
                transaction.remove(currentFragment);
                transaction.commit();
//                fragment = new FragmentFavorite();
//                loadFragment(fragment);
                return true;
            }else if(id == R.id.navigation_notifications){
                fragment = new FragmentNotification();
                loadFragment(fragment);
                return true;
            }else if(id == R.id.navigation_profile){
//                fragment = new FragmentProfile();
//                loadFragment(fragment);
                NavHostFragment navHostFragment = NavHostFragment.create(R.navigation.navigation_profile);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frame_container, navHostFragment);
                transaction.remove(currentFragment);
                transaction.commit();
                return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void loadFragmentHome(){
        NavHostFragment navHostFragment = NavHostFragment.create(R.navigation.navigation_home);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, navHostFragment);
        transaction.commit();
    }



}
