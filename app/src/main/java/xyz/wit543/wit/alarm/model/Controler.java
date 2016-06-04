package xyz.wit543.wit.alarm.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import xyz.wit543.wit.alarm.activity.WakeActivity;

/**
 * Created by WIT on 03-Jun-16.
 */
public class Controler {
    private static Controler controler;
    private Storage storage;
    private List<PendingIntent> pendingIntents;

    private Controler(){
        storage=Storage.getInstance();
        pendingIntents = new ArrayList<>();
    }
    public static Controler getInstant(){
        if(controler==null){
            controler = new Controler();
        }
        return controler;
    }
    public void addAlarmToPending(int hour, int minute, Context context){
        Alarm alarm = new Alarm(hour,minute);
        storage.addAlarm(alarm);
        Intent intent = new Intent(context,WakeActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.putExtra("alarm",alarm.hashCode());

        PendingIntent pendingIntent = PendingIntent.getActivity(context,((int)System.currentTimeMillis()),intent,0);

        pendingIntents.add(pendingIntent);
        storage.addPendingIntent(pendingIntent);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,alarm.getTime(),   pendingIntent);
    }
}
