package xyz.wit543.wit.alarm.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.adapter.AlarmRecycleVew;
import xyz.wit543.wit.alarm.adapter.ViewPagerAdapter;
import xyz.wit543.wit.alarm.fragment.StatusFragment;
import xyz.wit543.wit.alarm.model.Alarm;
import xyz.wit543.wit.alarm.model.Storage;

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
        viewPagerAdapter.addFragment(new StatusFragment(),"incoming");
        viewPager.setAdapter(viewPagerAdapter);
    }
}
