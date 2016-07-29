package net.tawazz.spendee;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.joanzapata.iconify.widget.IconTextView;

import net.tawazz.androidutil.Util;
import net.tawazz.spendee.AppData.AppData;
import net.tawazz.spendee.AppData.Tags;
import net.tawazz.spendee.adapters.AddAdapter;
import net.tawazz.spendee.fragments.AddFragment;
import net.tawazz.spendee.helpers.Sync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static net.tawazz.spendee.helpers.AppHelper.MONTHS;

public class AddActivity extends AppCompatActivity implements AddFragment.TagSelectedListener {

    @BindView(R.id.calendar_icon)
    IconTextView calendarIcon;
    @BindView(R.id.date_text)
    TextView dateText;
    @BindView(R.id.tags_text)
    TextView tagsText;
    private ArrayList<String> tabTitles;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private AppBarLayout appBar;
    private EditText amount, item;
    private TextView title;
    private int currentPosition;
    private AppData appData;
    private Calendar selectedDate;
    private ArrayList<Tags> selectedTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);

        appData = (AppData) getApplication();
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

        tabLayout.setBackgroundResource(R.color.redAccent);
        viewPager.setAdapter(new AddAdapter(fragmentManager));

        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        Util.setCurrencyEditText(amount);
        updateDate();
        AddFragment fragment = (AddFragment) ((AddAdapter) viewPager.getAdapter()).getItem(viewPager.getCurrentItem());
        fragment.setTagSelectedListener(AddActivity.this);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                AddFragment fragment = (AddFragment) ((AddAdapter) viewPager.getAdapter()).getItem(position);
                fragment.setTagSelectedListener(AddActivity.this);
                if (selectedTags != null) {
                    selectedTags.clear();
                    tagsText.setText("Select Tags Below");
                }
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

        currentPosition = position;
        updateDate();
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
        AddFragment fragment = (AddFragment) ((AddAdapter) viewPager.getAdapter()).getItem(viewPager.getCurrentItem());
        Calendar date = selectedDate;
        String itemName = item.getText().toString();
        String amntString = amount.getText().toString();
        amntString = amntString.substring(1); //remove $ sign
        amntString = amntString.replace(",", ""); // remove formated commas
        float amnt = Float.parseFloat(amntString);
        final ProgressDialog dialog = Util.loadingDialog(this, "Saving...");

        JSONObject data = new JSONObject();
        JSONArray tags = new JSONArray();
        JSONObject item = new JSONObject();
        try {
            item.put("name", itemName);
            item.put("cost", amnt);
            item.put("date", date.get(Calendar.YEAR) + "/" + (date.get(Calendar.MONTH) + 1) + "/" + date.get(Calendar.DAY_OF_MONTH));
            item.put("user_id", AppData.user.getUserId());
            data.put("item", item);

            if (selectedTags.size() > 0) {
                for (Tags tag : selectedTags) {
                    JSONObject tagData = new JSONObject();
                    tagData.put("tag_id", tag.getTagId());
                    tags.put(tagData);
                }
                data.put("tags", tags);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = (0 == viewPager.getCurrentItem()) ? Sync.ADD_EXP_URL : Sync.ADD_INC_URL;

        JsonObjectRequest request = new JsonObjectRequest(url, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                AddActivity.this.finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        });

        AppData.getWebRequestInstance().getRequestQueue().add(request);

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
            tabLayout.setBackgroundColor(getResources().getColor(R.color.greenAccent));
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.green));
            tabLayout.setTabTextColors(Color.parseColor("#333333"), getResources().getColor(R.color.green));
        }
    }

    private void updateDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        selectedDate = c;
        dateText.setText(MONTHS.get(month) + " " + day + ", " + year);
        int theme;

        if (currentPosition == AddFragment.addType.EXPENSE.ordinal()) {
            theme = R.style.DialogThemeRed;
        } else if (currentPosition == AddFragment.addType.INCOME.ordinal()) {
            theme = R.style.DialogThemeGreen;
        } else {
            theme = R.style.AppTheme;
        }

        final DatePickerDialog datePicker = new DatePickerDialog(this, theme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, monthOfYear);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dateText.setText(MONTHS.get(monthOfYear) + " " + dayOfMonth + ", " + year);
            }
        }, year, month, day);


        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });

    }

    @Override
    public void onItemSelected(ArrayList<Tags> tags) {
        if (tags.size() > 0) {
            tagsText.setText(tagListToString(tags));
        } else {
            tagsText.setText("Select Tags Below");
        }
        selectedTags = tags;
    }

    private String tagListToString(ArrayList<Tags> tags) {
        String tagsString = "";

        for (Tags tag : tags) {
            tagsString += tag.getTagName() + ", ";
        }

        tagsString = tagsString.trim();
        tagsString = tagsString.substring(0, tagsString.length() - 1);
        return tagsString;
    }
}
