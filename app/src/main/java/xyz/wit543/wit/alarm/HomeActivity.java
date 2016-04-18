package xyz.wit543.wit.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.MainThread;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class HomeActivity extends AppCompatActivity {
    private TimePicker alarmTimePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {

        Button button = (Button) findViewById(R.id.add_alarm);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(HomeActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        changeTime(hourOfDay,minute);
                    }
                }, 24, 60, true);
                tpd.show();

            }
        });
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.alarm_recycler_view);

    }
    private void changeTime(int hour,int minute){
        TextView tx =(TextView) findViewById(R.id.textView);
        assert tx !=null;
        tx.setText("hour: "+hour+" minute: "+minute);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(HomeActivity.this,WakeActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        PendingIntent pendingIntent = PendingIntent.getActivity(HomeActivity.this,0,intent,0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Log.v("time","current: "+calendar.getTime());
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.SECOND,0);
        Log.v("time","current: "+System.currentTimeMillis());
        Log.v("time","set: "+calendar.getTimeInMillis());
        Log.v("time","set: "+calendar.getTime());
        Log.v("time","uptime: "+SystemClock.uptimeMillis());
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),   pendingIntent);
    }
    private class Reciver extends WakefulBroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
    private class AlarmReciver extends WakefulBroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            TextView tx =(TextView) findViewById(R.id.textView);
            tx.setText("ALARM!!!!!!!!!!!!!!!!!!!!!!!!!");
            Log.v("test","Awake!!!");
        }
    }
}
