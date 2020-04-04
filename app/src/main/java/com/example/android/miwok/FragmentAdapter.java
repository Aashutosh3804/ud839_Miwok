package com.example.android.miwok;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }
    private Context context;
    private String tabTitles[] = new String[] { "Numbers", "Family", "Colors" };

    @Override
    public Fragment getItem(int i) {
        if(i==0){
            return new NumbersFragment();
        }
        else if(i==1){
            return new FamilyFragment();

        }
        else{
            return new ColorsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
