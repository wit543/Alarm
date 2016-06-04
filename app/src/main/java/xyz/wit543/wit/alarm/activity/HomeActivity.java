package xyz.wit543.wit.alarm.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.adapter.ViewPagerAdapter;
import xyz.wit543.wit.alarm.fragment.AlarmAddedToFriendFragment;
import xyz.wit543.wit.alarm.fragment.StatusFragment;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tableLayout;
    private ViewPager viewPager;

    private StatusFragment statusFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        initTab();
    }

    private void initTab(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tableLayout = (TabLayout) findViewById(R.id.tabs);
        tableLayout.setupWithViewPager(viewPager);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    public void setupViewPager(ViewPager upViewPager) {
        statusFragment =new StatusFragment();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(statusFragment,"Status");
        viewPagerAdapter.addFragment(new StatusFragment(),"pending");
        viewPagerAdapter.addFragment(new AlarmAddedToFriendFragment(),"friend alarm");
        viewPager.setAdapter(viewPagerAdapter);
    }
}
