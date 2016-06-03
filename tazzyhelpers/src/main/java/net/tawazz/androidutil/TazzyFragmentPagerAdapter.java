package net.tawazz.androidutil;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

public class TazzyFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private ArrayList<String> tabTitles;
    private ArrayList<Fragment> fragmentList;

    public TazzyFragmentPagerAdapter(FragmentManager fm, ArrayList<String> titles, ArrayList<Fragment> fragments) {
        super(fm);
        fragmentList = fragments;
        tabTitles = titles;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles.get(position);
    }

}