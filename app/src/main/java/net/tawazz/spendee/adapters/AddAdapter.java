package net.tawazz.spendee.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.tawazz.spendee.fragments.AddFragment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by tawanda on 10/06/2016.
 */
public class AddAdapter extends FragmentPagerAdapter {

    private ArrayList<String> titles;
    private ArrayList<AddFragment> fragments;

    public AddAdapter(FragmentManager fm) {
        super(fm);
        titles = new ArrayList<>(Arrays.asList("Expense","Income"));
        fragments = new ArrayList<>();
        fragments.add(AddFragment.getInstance(0));
        fragments.add(AddFragment.getInstance(1));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
