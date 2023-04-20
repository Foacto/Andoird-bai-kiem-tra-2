package com.example.les6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;

import com.example.les6.adapter.AdapterViewPager;
import com.example.les6.model.HorizontalFlipTransformation;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tab;
    ViewPager vPager;
    AdapterViewPager adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init
        tab = findViewById(R.id.tabLayout);
        vPager = findViewById(R.id.vPager);
        adapter = new AdapterViewPager(getSupportFragmentManager(), 3);
        vPager.setAdapter(adapter);
        vPager.setPageTransformer(false, new HorizontalFlipTransformation());
        tab.setupWithViewPager(vPager);

        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab t) {
                switch (t.getPosition()) {
                    case 0:
                        tab.setTabTextColors(Color.BLACK, getResources().getColor(R.color.colorPink));

                        break;
                    case 1:
                        tab.setTabTextColors(Color.BLACK, getResources().getColor(R.color.colorGreen));

                        break;
                    case 2:
                        tab.setTabTextColors(Color.BLACK, getResources().getColor(R.color.colorBlue));

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}