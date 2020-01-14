package com.example.nepal_app;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.nepal_app.UI.Fragments.Activities.ActivitiesFragment;
import com.example.nepal_app.UI.Fragments.Navigation.HomeFragment;
import com.example.nepal_app.UI.Fragments.Profile.ProfileFragment;
import com.example.nepal_app.UI.Fragments.Progress.ProgressFragment;
import com.example.nepal_app.UI.Fragments.Recipes.RecipeHome;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(new HomeFragment());
    }
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            openFragment(new HomeFragment());
                            return true;
                        case R.id.navigation_activities:
                            openFragment(new ActivitiesFragment());
                            return true;
                        case R.id.navigation_profile:
                            openFragment(new ProfileFragment());
                            return true;
                        case R.id.navigation_progress:
                            openFragment(new ProgressFragment());
                            return true;
                        case R.id.navigation_recipe:
                            openFragment(new RecipeHome());
                            return true;
                    }
                    return false;
                }
            };

} //ellers lav aktiviterer mellem de andres? ved godt vi ikke har aftalt hvordan vi gør det
