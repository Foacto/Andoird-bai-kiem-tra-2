package com.example.les6.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.les6.fragments.FragmentFood;
import com.example.les6.fragments.FragmentMovie;
import com.example.les6.fragments.FragmentTravel;

public class AdapterViewPager extends FragmentStatePagerAdapter {
    private int pageNumber;
    public AdapterViewPager(@NonNull FragmentManager fm, int behavior) {
        super(fm);
        this.pageNumber = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentFood food = new FragmentFood();
                return  food;
            case 1:
                FragmentMovie movie = new FragmentMovie();
                return  movie;
            case 2:
                FragmentTravel travel = new FragmentTravel();
                return  travel;
        }
        return null;
    }

    @Override
    public int getCount() {
        return pageNumber;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return  "Food";
            case 1: return  "Movie";
            case 2: return  "Travel";
        }
        return null;
    }
}
