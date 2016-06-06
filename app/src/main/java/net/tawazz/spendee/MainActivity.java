package net.tawazz.spendee;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import net.tawazz.androidutil.TazzyFragmentPagerAdapter;
import net.tawazz.androidutil.Util;
import net.tawazz.spendee.AppData.ExpData;
import net.tawazz.spendee.AppData.ExpItem;
import net.tawazz.spendee.AppData.IncData;
import net.tawazz.spendee.AppData.Items;
import net.tawazz.spendee.fragments.DashBoardFragment;
import net.tawazz.spendee.fragments.ExpFragment;
import net.tawazz.spendee.fragments.IncFragment;
import net.tawazz.spendee.fragments.ViewsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> tabTitles;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView dateTitle, expAmount, incAmount, balAmount;
    private FloatingActionButton addButton;
    private LinearLayout appBar;
    private ArrayList<Integer> dates;
    private IconTextView nextDate, prevDate;
    private int currentPosition;
    private ViewPager.OnPageChangeListener navigation = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            currentPosition = position;
            updateViews(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

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

    private void init() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        addButton = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dateTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        nextDate = (IconTextView) toolbar.findViewById(R.id.nextDate);
        prevDate = (IconTextView) toolbar.findViewById(R.id.prevDate);
        expAmount = (TextView) findViewById(R.id.expense_total_amount);
        incAmount = (TextView) findViewById(R.id.income_total_amount);
        balAmount = (TextView) findViewById(R.id.balance_amount);
        appBar = (LinearLayout) findViewById(R.id.info_bar);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        setSupportActionBar(toolbar);
        ActionBar appBar = getSupportActionBar();
        appBar.setDisplayShowTitleEnabled(false);

        fragmentManager = getSupportFragmentManager();
        fragmentList = new ArrayList<>();

        fragmentList.add(new ExpFragment());
        fragmentList.add(new IncFragment());
        fragmentList.add(new DashBoardFragment());

        currentPosition = 0;
        dateTitle.setText(generateDate(null, null, null));

        expAmount.setText(dashAmount(1000));
        incAmount.setText(dashAmount(800));
        balAmount.setText(dashAmount(200));

        tabTitles = new ArrayList<>(
                Arrays.asList("Expenses", "Incomes", "Dashboard")
        );

        viewPager.setAdapter(new TazzyFragmentPagerAdapter(fragmentManager, tabTitles, fragmentList));

        // Give the TabLayout the ViewPager

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundResource(R.color.redAccent);

        viewPager.addOnPageChangeListener(navigation);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        nextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTitle.setText(generateDate(dates.get(0), dates.get(1) + 1, null));
            }
        });

        prevDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTitle.setText(generateDate(dates.get(0), dates.get(1) - 1, null));
            }
        });

    }

    private String generateDate(Integer year, Integer month, Integer day) {

        Calendar cal = Calendar.getInstance();
        ArrayList<String> months = new ArrayList<>(Arrays.asList(
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        ));

        if (year != null && month != null && day != null) {

            if (month == 13) {
                month = 1;
                year += 1;
            }
            if (month == 0) {
                month = 12;
                year -= 1;
            }
            cal.set(year, month - 1, day);

        } else if (year != null && month != null) {
            if (month == 13) {
                month = 1;
                year += 1;
            }
            if (month == 0) {
                month = 12;
                year -= 1;
            }
            cal.set(year, month - 1, 1);
        } else {
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) + 1;
            day = cal.get(Calendar.DATE);

        }
        if (dates == null) {
            dates = new ArrayList<>();
            dates.add(year);
            dates.add(month);
            dates.add(day);
            setFragmentListeners(0);
        } else {
            dates.set(0, year);
            dates.set(1, month);
            dates.set(2, day);
        }

        return months.get(cal.get(Calendar.MONTH)) + "/" + cal.get(Calendar.YEAR);
    }

    private void updateViews(int position) {
        // TODO update fragment views when fragment created
        updateColors(position);
        switch (position) {
            case 0:
                ExpFragment fragment = (ExpFragment) fragmentList.get(position);
                ArrayList<Items> items = new ArrayList<>();
                ArrayList<String> tags = new ArrayList<>(Arrays.asList("food", "drink"));
                items.add(new ExpItem("food", 12, tags, new Date()));
                items.add(new ExpItem("mexican", 8, tags, new Date()));
                items.add(new ExpItem("asian", (float) 8.95, tags, new Date()));

                ArrayList<ExpData> data = new ArrayList<>();
                data.add(new ExpData(new Date(), items));

                fragment.setExpenses(data);
                break;
            case 1:
                IncFragment inFragment = (IncFragment) fragmentList.get(position);
                items = new ArrayList<>();
                tags = new ArrayList<>(Arrays.asList("food", "drink"));
                items.add(new ExpItem("mexican", 8, tags, new Date()));
                items.add(new ExpItem("asian", (float) 8.95, tags, new Date()));

                ArrayList<IncData> incData = new ArrayList<>();
                incData.add(new IncData(new Date(), items));

                inFragment.setIncomes(incData);
                break;
            case 2:
                break;
        }
    }

    public String dashAmount(double amount) {
        return String.format(this.getText(R.string.dash_amnt).toString(), amount);
    }

    public void setFragmentListeners(final int pos) {
        ViewsFragment.onCreateViewListener fragmentCreatedListener = new ViewsFragment.onCreateViewListener() {
            @Override
            public void onFragmentCreateView() {
                if (currentPosition == 0) {
                    updateViews(pos);
                }

            }
        };

        ((ViewsFragment) fragmentList.get(pos)).setOnCreateViewListener(fragmentCreatedListener);
    }

    private void updateColors(int position) {

        if (position == 0) {
            tabLayout.setBackgroundColor(getResources().getColor(R.color.redAccent));
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
            tabLayout.setTabTextColors(Color.parseColor("#333333"), getResources().getColor(R.color.red));
            appBar.setBackgroundColor(getResources().getColor(R.color.red));
            toolbar.setBackgroundColor(getResources().getColor(R.color.red));
            Util.setWindowColor(this, R.color.red);
        } else if (position == 1) {
            tabLayout.setBackgroundResource(R.color.greenAccent);
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.green));
            tabLayout.setTabTextColors(Color.parseColor("#333333"), getResources().getColor(R.color.green));
            appBar.setBackgroundColor(getResources().getColor(R.color.green));
            toolbar.setBackgroundColor(getResources().getColor(R.color.green));
            Util.setWindowColor(this, R.color.green);
        } else if (position == 2) {
            tabLayout.setBackgroundColor(getResources().getColor(R.color.balanceAccent));
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.balance));
            tabLayout.setTabTextColors(Color.parseColor("#333333"), getResources().getColor(R.color.white));
            appBar.setBackgroundColor(getResources().getColor(R.color.balance));
            toolbar.setBackgroundColor(getResources().getColor(R.color.balance));
            Util.setWindowColor(this, R.color.balance);
        }
    }
}
