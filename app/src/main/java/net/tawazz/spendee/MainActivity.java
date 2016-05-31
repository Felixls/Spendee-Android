package net.tawazz.spendee;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.tawazz.spendee.adapters.MyFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView dateTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        dateTitle.setText("May/2016");
        setSupportActionBar(toolbar);

        ActionBar appBar = getSupportActionBar();
        appBar.setDisplayShowTitleEnabled(false);
        fragmentManager = getSupportFragmentManager();

        assert viewPager != null;
        viewPager.setAdapter(new MyFragmentPagerAdapter(fragmentManager,
                MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

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

    private void addFragment(Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .add(R.id.viewPager, fragment, fragment.getClass().getName())
                .hide(fragment)
                .commit();

    }


}
