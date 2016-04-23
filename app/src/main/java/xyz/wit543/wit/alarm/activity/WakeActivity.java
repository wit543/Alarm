package xyz.wit543.wit.alarm.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.model.Alarm;
import xyz.wit543.wit.alarm.model.Storage;

public class WakeActivity extends AppCompatActivity {
    private Storage storage = Storage.getInstance();
    private Button waked;
    private Button append;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Button b = (Button) findViewById(R.id.button_waked);
//        assert b != null;
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                WakeActivity.this.finish();
//            }
//        });

    }

    private void initComponent() {
        waked = (Button) findViewById(R.id.button_waked);
        append = (Button) findViewById(R.id.button_append_time);
        waked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        append.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendAlarm5min();
                finish();
            }
        });
    }
    private void appendAlarm5min(){

        Alarm alarm = new Alarm(System.currentTimeMillis()+5*60*1000);
        storage.addAlarm(alarm);
        Intent intent = new Intent(WakeActivity.this,WakeActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.putExtra("alarm",alarm.hashCode());

        PendingIntent pendingIntent = PendingIntent.getActivity(WakeActivity.this,((int)System.currentTimeMillis()),intent,0);
        storage.addPendingIntent(pendingIntent);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,alarm.getTime(),   pendingIntent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        int hashCode = getIntent().getIntExtra("alarm",0);
        storage.removeAlarm(hashCode);
        setContentView(R.layout.activity_wake);
        initComponent();
    }
}
