package com.e.vknew;

import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    Fragment f1 = new FeedFragment();
    Fragment f2 = new FavouritesFragment();
    List<Fragment> list = new ArrayList<>();
    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = (BottomNavigationView)  findViewById(R.id.nav_view);
//        navView.setOnNavigationItemSelectedListener(navListener);
        list.add(f1);
        list.add(f2);
        pager = findViewById(R.id.pager);
        pagerAdapter = new Pager(getSupportFragmentManager(), list);
        pager.setAdapter(pagerAdapter);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        pager.setCurrentItem(0,false);
                        break;
                    case R.id.navigation_favourites:
                        pager.setCurrentItem(1,false);
                        break;
                }

                return false;
            }
        });

    }

    public void myClick(FeedModel feedModel, int option){
        Fragment fragment= getSupportFragmentManager().findFragmentById(R.id.pager);
        if(option==1)
            ((FavouritesFragment)fragment).saveNews(feedModel);
        else
            ((FavouritesFragment)fragment).removeNews(feedModel);
    }


    public void removeItemLike(FeedModel news) {
        ((FeedFragment)f1).removeLike(news);
        ((FavouritesFragment)f2).removeLike(news);
    }

}
