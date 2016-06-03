package net.tawazz.spendee;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import net.tawazz.androidutil.TazzyFragmentPagerAdapter;
import net.tawazz.androidutil.Util;
import net.tawazz.spendee.fragments.ExpFragment;
import net.tawazz.spendee.fragments.IncFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class AddActivity extends AppCompatActivity {

    private ArrayList<String> tabTitles;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragmentList;
    private Toolbar toolbar;
    private AppBarLayout appBar;
    private EditText amount, item;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Util.setWindowColor(this, R.color.red);

        init();
    }

    private void init() {
        amount = (EditText) findViewById(R.id.amount);
        item = (EditText) findViewById(R.id.item_name);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        title = (TextView) toolbar.findViewById(R.id.toolbar_secondary_title);
        appBar = (AppBarLayout) findViewById(R.id.app_bar);

        fragmentManager = getSupportFragmentManager();

        fragmentList = new ArrayList<>();
        fragmentList.add(new ExpFragment());
        fragmentList.add(new IncFragment());

        tabTitles = new ArrayList<>(
                Arrays.asList("Expense", "Income")
        );

        tabLayout.setBackgroundResource(R.color.redAccent);
        viewPager.setAdapter(new TazzyFragmentPagerAdapter(fragmentManager, tabTitles, fragmentList));

        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        Util.setCurrencyEditText(amount);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        updateViews(position);
                        break;
                    case 1:
                        updateViews(position);
                        break;
                    case 2:
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateViews(int position) {
        updateTabColors(position);
        if (position == 0) { //add expense
            title.setText("Add Expense");
            appBar.setBackgroundColor(getResources().getColor(R.color.red));
            Util.setWindowColor(this, R.color.red);

        } else if (position == 1) { // add income
            title.setText("Add Income");
            appBar.setBackgroundColor(getResources().getColor(R.color.green));
            Util.setWindowColor(this, R.color.green);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.submit:
                submit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void submit() {
        /* Todo
        *  add validation and submitting code here
        */
        Util.alert(this, "Submitting ...", item.getText().toString() + "\n" + amount.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void updateTabColors(int position) {

        if (position == 0) {
            tabLayout.setBackgroundColor(getResources().getColor(R.color.redAccent));
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
            tabLayout.setTabTextColors(Color.parseColor("#333333"), getResources().getColor(R.color.red));
        } else if (position == 1) {
            tabLayout.setBackgroundResource(R.color.greenAccent);
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.green));
            tabLayout.setTabTextColors(Color.parseColor("#333333"), getResources().getColor(R.color.green));
        }
    }
}
