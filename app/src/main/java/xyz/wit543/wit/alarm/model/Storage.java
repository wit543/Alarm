package xyz.wit543.wit.alarm.model;

import android.app.PendingIntent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by WIT on 20-Apr-16.
 */
public class Storage {
    private List<Alarm> alarms;
    private List<PendingIntent> pendingIntents;
    private List<User> friends;
    private static Storage storage;
    private Storage(){
        alarms = new ArrayList<>();
        pendingIntents = new ArrayList<>();
        friends = new ArrayList<>();
        initFriends();
    }
    public void  initFriends(){
        friends.add(new User("1"));
        friends.add(new User("2"));
        friends.add(new User("3"));
        friends.add(new User("4"));
        friends.add(new User("5"));
    }
    public static Storage getInstance(){
        if(storage==null)
            storage = new Storage();
        return storage;
    }
    public void addAlarm(Alarm alarm){
        alarms.add(alarm);
    }
    public void removeAlarm(Alarm alarm){
        alarms.remove(alarm);
    }
    public void removeAlarm(int hashCode){
        for(Alarm a:alarms)
            if(a.hashCode()==hashCode) {
                alarms.remove(a);
                Log.v("test","remove"+a);
            }
        for(Alarm a:alarms){
            Log.v("test","++ "+a.hashCode());
        }
        Log.v("test","hash: "+hashCode);
    }
    public Iterator<Alarm> getAlarms(){
        return alarms.iterator();
    }
    public void addPendingIntent(PendingIntent pendingIntent){
        pendingIntents.add(pendingIntent);
    }
    public Iterator<PendingIntent> getPendingIntents(){
        return pendingIntents.iterator();
    }
    public void removePendingIntent(PendingIntent pendingIntent){
        pendingIntents.remove(pendingIntent);
    }
    public void removePendingIntent(int hashCode){
        for(PendingIntent p:pendingIntents)
            if(p.hashCode()==hashCode)
                pendingIntents.remove(p);
    }
    public void removeAllAlarm(){
        alarms.clear();
    }
    public void removeAllPendingIntent(){
        pendingIntents.clear();
    }
    public Iterator<User> getFriends(){return friends.iterator();}
    public void addFriend(User friend){
        friends.add(friend);
    }
    public User getFriend(String userName){
        for(User u:friends)
            if(u.getUserName().equals(userName))
                return u;
        return null;
    }
}
