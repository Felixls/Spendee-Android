package net.tawazz.spendee;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.tawazz.androidutil.TazzyFragmentPagerAdapter;
import net.tawazz.spendee.fragments.DashBoardFragment;
import net.tawazz.spendee.fragments.ExpFragment;
import net.tawazz.spendee.fragments.IncFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> tabTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView dateTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        dateTitle.setText("June/2016");
        setSupportActionBar(toolbar);

        ActionBar appBar = getSupportActionBar();
        appBar.setDisplayShowTitleEnabled(false);
        fragmentManager = getSupportFragmentManager();

        fragmentList = new ArrayList<>();

        fragmentList.add(new ExpFragment());
        fragmentList.add(new IncFragment());
        fragmentList.add(new DashBoardFragment());

        tabTitles = new ArrayList<>(
                Arrays.asList("Expenses","Incomes","Dashboard")
        );
        assert viewPager != null;
        viewPager.setAdapter(new TazzyFragmentPagerAdapter(fragmentManager,tabTitles, fragmentList));

        // Give the TabLayout the ViewPager
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundResource(R.color.redAccent);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        ExpFragment expFragment = (ExpFragment) fragmentList.get(position);
                        expFragment.setText("Scrolled");
                        tabLayout.setBackgroundResource(R.color.redAccent);
                        break;
                    case 1:
                        tabLayout.setBackgroundResource(R.color.greenAccent);
                        break;
                    case 2:
                        tabLayout.setBackgroundResource(R.color.colorAccent);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.fab);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



}
