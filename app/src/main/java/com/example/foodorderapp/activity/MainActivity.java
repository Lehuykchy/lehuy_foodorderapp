package com.example.foodorderapp.activity;




import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.foodorderapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation_buttom);

        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHost.getNavController();

        NavigationUI.setupWithNavController(bottomNavigationView, navController);



//        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        navController.setGraph(R.navigation.navigation);
//        bottomNavigationView = findViewById(R.id.navigation_butom);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
//        navController.navigate(R.id.fragmentHome);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                if(id == R.id.navigation_home){
//                    navController.navigate(R.id.fragmentHome);
//                    return true;
//                } else if(id == R.id.navigation_cart){
//                    navController.navigate(R.id.fragmentCart);
//                    return true;
//                } else if(id == R.id.navigation_favorite){
//                    navController.navigate(R.id.fragmentFavorite);
//                    return true;
//                } else if(id == R.id.navigation_notifications){
//                    navController.navigate(R.id.fragmentNotification);
//                    return true;
//                } else if(id == R.id.navigation_profile){
//                    navController.navigate(R.id.fragmentProfile);
//                    return true;
//                }
//                return false;
//            }
     //   });
    }


}
