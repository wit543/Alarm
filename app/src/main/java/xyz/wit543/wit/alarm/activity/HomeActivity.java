package xyz.wit543.wit.alarm.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.adapter.ViewPagerAdapter;
import xyz.wit543.wit.alarm.fragment.AlarmAddedToFriendFragment;
import xyz.wit543.wit.alarm.fragment.ComingAlarmFragment;
import xyz.wit543.wit.alarm.fragment.StatusFragment;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tableLayout;
    private ViewPager viewPager;
    private Button loginButton;
    private StatusFragment statusFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        initTab();
        initBar();
    }
    private void initBar(){
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.action_bar_home);
        actionBar.setDisplayShowTitleEnabled(false);
        loginButton = (Button) actionBar.getCustomView().findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
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
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new StatusFragment(),"Status");
        viewPagerAdapter.addFragment(new ComingAlarmFragment(),"pending");
        viewPagerAdapter.addFragment(new AlarmAddedToFriendFragment(),"friend alarm");
        viewPager.setAdapter(viewPagerAdapter);
    }
}
