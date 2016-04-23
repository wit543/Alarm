package xyz.wit543.wit.alarm.model;

import android.os.SystemClock;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by WIT on 19-Apr-16.
 */
public class Alarm{
    private Calendar date;
    public Alarm(int hour, int minute){
        date = Calendar.getInstance();
        date.setTimeInMillis(System.currentTimeMillis());
        if(date.get(Calendar.HOUR_OF_DAY)>hour)
            date.set(Calendar.HOUR_OF_DAY, date.get(Calendar.HOUR_OF_DAY) + 24);
        date.set(Calendar.HOUR_OF_DAY,hour);
        date.set(Calendar.MINUTE,minute);
        date.set(Calendar.SECOND,0);
    }
    public Alarm(long currentTimeMil){
        date = Calendar.getInstance();
        date.setTimeInMillis(currentTimeMil);
    }
    public int getMinute(){
        return date.get(Calendar.MINUTE);
    }
    public int getHour(){
        return date.get(Calendar.HOUR_OF_DAY);
    }
    public long getTime(){
        return date.getTimeInMillis();
    }

}
