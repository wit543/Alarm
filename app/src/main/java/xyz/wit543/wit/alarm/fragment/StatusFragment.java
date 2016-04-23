package xyz.wit543.wit.alarm.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.activity.WakeActivity;
import xyz.wit543.wit.alarm.adapter.AlarmRecycleVew;
import xyz.wit543.wit.alarm.model.Alarm;
import xyz.wit543.wit.alarm.model.Storage;


public class StatusFragment extends Fragment {
    private List<Alarm> alarms;
    private RecyclerView.Adapter recycleViewAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addAlarmButton;
    private FloatingActionButton deleteAlarmButton;
    private List<PendingIntent> pendingIntents;
    private Storage storage = Storage.getInstance();
    private View v;
    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_status, container, false);
        init();
        return v;
    }

    private void init() {
        alarms = new ArrayList<Alarm>();
        pendingIntents = new ArrayList<>();
        initAddAlarmButton();
        initDeleteAlarmButton();
        initRecyclerView();

    }

    @Override
    public void onStart() {
        super.onStart();
        loadAlarms();
        loadPendingIntents();
    }

    private void initRecyclerView() {
        recyclerView =(RecyclerView) v.findViewById(R.id.alarm_recycler_view);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recycleViewAdapter = new AlarmRecycleVew(alarms);
        recyclerView.setAdapter(recycleViewAdapter);
    }
    private void initAddAlarmButton(){
        addAlarmButton = (FloatingActionButton) v.findViewById(R.id.alarm_add);
        assert addAlarmButton!=null;
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlarm();
            }
        });
    }
    private void createAlarm(){
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Log.v("test","in");
        TimePickerDialog tpd = new TimePickerDialog(getActivity(), R.style.red_dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                addAlarmToPending(hourOfDay,minute);
            }

        }, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), true);
        tpd.show();
    }
    private void initDeleteAlarmButton(){
        deleteAlarmButton = (FloatingActionButton) v.findViewById(R.id.alarm_delete);
        deleteAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlarm();
            }
        });
    }
    private void deleteAlarm(){
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        for(PendingIntent p:pendingIntents){
            alarmManager.cancel(p);
        }
    }
    private void addAlarmToPending(int hour, int minute){
        Alarm alarm = new Alarm(hour,minute);
        alarms.add(alarm);
        storage.addAlarm(alarm);
        recycleViewAdapter.notifyDataSetChanged();
        Intent intent = new Intent(getActivity(),WakeActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.putExtra("alarm",alarm.hashCode());

        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),((int)System.currentTimeMillis()),intent,0);

        pendingIntents.add(pendingIntent);
        storage.addPendingIntent(pendingIntent);

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,alarm.getTime(),   pendingIntent);
    }
    private void loadAlarms(){
        alarms.clear();
        Iterator<Alarm> alarmIterator = storage.getAlarms();
        while (alarmIterator.hasNext())
            alarms.add(alarmIterator.next());
        recycleViewAdapter.notifyDataSetChanged();
    }
    private void loadPendingIntents(){
        pendingIntents.clear();
        Iterator<PendingIntent> pendingIntentIterator =storage.getPendingIntents();
        while (pendingIntentIterator.hasNext())
            pendingIntents.add(pendingIntentIterator.next());
    }
    public void update(){
        loadAlarms();
        loadPendingIntents();
        Log.v("test","in");
    }
}
