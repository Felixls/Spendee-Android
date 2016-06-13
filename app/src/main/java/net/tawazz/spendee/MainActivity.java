package net.tawazz.spendee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.joanzapata.iconify.widget.IconTextView;

import net.tawazz.androidutil.PersistentLoginManager;
import net.tawazz.androidutil.TazzyFragmentPagerAdapter;
import net.tawazz.androidutil.Util;
import net.tawazz.spendee.AppData.AppData;
import net.tawazz.spendee.AppData.ExpData;
import net.tawazz.spendee.AppData.ExpItem;
import net.tawazz.spendee.AppData.IncData;
import net.tawazz.spendee.AppData.IncItem;
import net.tawazz.spendee.AppData.Items;
import net.tawazz.spendee.fragments.DashBoardFragment;
import net.tawazz.spendee.fragments.ExpFragment;
import net.tawazz.spendee.fragments.IncFragment;
import net.tawazz.spendee.fragments.ViewsFragment;
import net.tawazz.spendee.helpers.Sync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

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
    private AppData appData;
    private ArrayList<ExpData> expData = new ArrayList<>();
    private ArrayList<IncData> incData = new ArrayList<>();
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
        appData = (AppData) getApplication();

        init();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout :
                appData.user = null;
                PersistentLoginManager manager = new PersistentLoginManager(this);
                manager.ClearPersitentData();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        expAmount.setText(dashAmount(0));
        incAmount.setText(dashAmount(0));
        balAmount.setText(dashAmount(0));
        currentPosition = 0;

        fragmentManager = getSupportFragmentManager();
        fragmentList = new ArrayList<>();

        fragmentList.add(new ExpFragment());
        fragmentList.add(new IncFragment());
        fragmentList.add(new DashBoardFragment());


        dateTitle.setText(generateDate(null, null, null));

        tabTitles = new ArrayList<>(
                Arrays.asList("Expenses", "Incomes", "Dashboard")
        );

        viewPager.setAdapter(new TazzyFragmentPagerAdapter(fragmentManager, tabTitles, fragmentList));

        // Give the TabLayout the ViewPager

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundResource(R.color.redAccent);

        viewPager.addOnPageChangeListener(navigation);
        viewPager.setOffscreenPageLimit(2);

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
                loadData();
            }
        });

        prevDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTitle.setText(generateDate(dates.get(0), dates.get(1) - 1, null));
                loadData();
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
        } else if (year != null ) {
            cal.set(year,0, 1);
            return ""+cal.get(Calendar.YEAR);
        }else {
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
                ExpFragment expFragment = (ExpFragment) fragmentList.get(position);
                expFragment.setExpenses(expData);
                break;
            case 1:
                IncFragment incFragment = (IncFragment) fragmentList.get(position);
                incFragment.setIncomes(incData);
                incFragment.setOnCreateViewListener(new ViewsFragment.onCreateViewListener() {
                    @Override
                    public void onFragmentCreateView() {

                    }

                    @Override
                    public void onRefresh() {
                        loadData();
                    }
                });
                break;
            case 2:
                generateDate(dates.get(0),null,null);
                final DashBoardFragment dashBoardFragment = (DashBoardFragment) fragmentList.get(position);
                dashBoardFragment.setOnCreateViewListener(new ViewsFragment.onCreateViewListener() {
                    @Override
                    public void onFragmentCreateView() {

                    }

                    @Override
                    public void onRefresh() {
                        dashBoardFragment.refresh();
                    }
                });
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

            @Override
            public void onRefresh() {
                loadData();
            }
        };

        ExpFragment fragment = ((ExpFragment) fragmentList.get(pos));
        fragment.setOnCreateViewListener(fragmentCreatedListener);
    }

    private void updateColors(final int position) {

        Runnable updateUi = new Runnable() {
            @Override
            public void run() {
                if (position == 0) {
                    tabLayout.setBackgroundColor(getResources().getColor(R.color.redAccent));
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
                    tabLayout.setTabTextColors(Color.parseColor("#333333"), getResources().getColor(R.color.red));
                    appBar.setBackgroundColor(getResources().getColor(R.color.red));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.red));
                    Util.setWindowColor(MainActivity.this, R.color.red);
                } else if (position == 1) {
                    tabLayout.setBackgroundColor(getResources().getColor(R.color.greenAccent));
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.green));
                    tabLayout.setTabTextColors(Color.parseColor("#333333"), getResources().getColor(R.color.green));
                    appBar.setBackgroundColor(getResources().getColor(R.color.green));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.green));
                    Util.setWindowColor(MainActivity.this, R.color.green);
                } else if (position == 2) {
                    tabLayout.setBackgroundColor(getResources().getColor(R.color.balanceAccent));
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.balance));
                    tabLayout.setTabTextColors(Color.parseColor("#333333"), getResources().getColor(R.color.white));
                    appBar.setBackgroundColor(getResources().getColor(R.color.balance));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.balance));
                    Util.setWindowColor(MainActivity.this, R.color.balance);
                }
            }
        };

        Handler handler = new Handler();
        handler.post(updateUi);
    }

    public void loadData() {

        String url;

        if (dates != null) {
            url = Sync.DATA_URL + AppData.user.getUserId() + "/" + dates.get(0) + "/" + dates.get(1);

        } else {
            url = Sync.DATA_URL + AppData.user.getUserId();
        }

        JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    expAmount.setText(dashAmount(response.getDouble("exp_total")));
                    incAmount.setText(dashAmount(response.getDouble("inc_total")));
                    balAmount.setText(dashAmount(response.getDouble("balance")));

                    expData.clear();
                    incData.clear();
                    JSONObject expenses = response.getJSONObject("exp_data");

                    Iterator<String> expIter = expenses.keys();

                    if (expenses.length() > 0) {
                        while (expIter.hasNext()) {
                            String key = expIter.next();
                            try {

                                JSONArray expOnDate = expenses.getJSONArray(key);
                                ArrayList<Items> items = new ArrayList<>();
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-d");
                                Date parsedDate = formatter.parse(key);
                                for (int i = 0; i < expOnDate.length(); i++) {
                                    JSONObject expItem = (JSONObject) expOnDate.get(i);
                                    items.add(new ExpItem(expItem.getString("name"), (float) expItem.getDouble("cost"), null, parsedDate));
                                }
                                expData.add(new ExpData(parsedDate, items));


                            } catch (JSONException e) {
                                // Something went wrong!
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    JSONObject incomes = response.getJSONObject("inc_data");
                    Iterator<String> incIter = incomes.keys();
                    if (incomes.length() > 0) {
                        while (incIter.hasNext()) {
                            String key = incIter.next();
                            try {

                                JSONArray incOnDate = incomes.getJSONArray(key);
                                ArrayList<Items> items = new ArrayList<>();
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-d");
                                Date parsedDate = formatter.parse(key);
                                for (int i = 0; i < incOnDate.length(); i++) {
                                    JSONObject incItem = (JSONObject) incOnDate.get(i);
                                    items.add(new IncItem(incItem.getString("name"), (float) incItem.getDouble("cost"), null, parsedDate));
                                }
                                incData.add(new IncData(parsedDate, items));


                            } catch (JSONException e) {
                                // Something went wrong!
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    updateViews(currentPosition);

                } catch (JSONException e) {
                    updateViews(currentPosition);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updateViews(currentPosition);
            }
        });

        AppData.getWebRequestInstance().getRequestQueue().add(request);

    }



}
