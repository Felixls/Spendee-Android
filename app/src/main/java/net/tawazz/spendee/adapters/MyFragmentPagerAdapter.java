package net.tawazz.spendee.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import net.tawazz.spendee.fragments.DashBoardFragment;
import net.tawazz.spendee.fragments.ExpFragment;
import net.tawazz.spendee.fragments.IncFragment;

import java.util.ArrayList;

public class MyFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"Expenses", "Incomes", "Dashboard"};
    private Context context;
    private ArrayList<Fragment> fragmentList;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragmentList = new ArrayList<>();

        fragmentList.add(new ExpFragment());
        fragmentList.add(new IncFragment());
        fragmentList.add(new DashBoardFragment());

        this.context = context;
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
        return tabTitles[position];
    }

}