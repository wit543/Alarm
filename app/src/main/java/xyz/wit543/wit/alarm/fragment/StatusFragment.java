package xyz.wit543.wit.alarm.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.activity.LoginActivity;
import xyz.wit543.wit.alarm.activity.SelectFriendActivity;
import xyz.wit543.wit.alarm.activity.WakeActivity;
import xyz.wit543.wit.alarm.adapter.AlarmRecycleViewAdapter;
import xyz.wit543.wit.alarm.adapter.SelectFriendRecycleViewAdapter;
import xyz.wit543.wit.alarm.dialog.AddFriendDialog;
import xyz.wit543.wit.alarm.model.Alarm;
import xyz.wit543.wit.alarm.model.Storage;
import xyz.wit543.wit.alarm.model.User;


public class StatusFragment extends Fragment {
    private List<Alarm> alarms;
    private RecyclerView.Adapter recycleViewAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addAlarmButton;
    private FloatingActionButton deleteAlarmButton;
    private FloatingActionButton addFriendButton;
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
        initAddFriendButton();
        initRecyclerView();

    }

    @Override
    public void onStart() {
        super.onStart();
        loadAlarms();
        loadPendingIntents();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAlarms();
        loadPendingIntents();
    }
    private void initAddFriendButton(){
        addFriendButton = (FloatingActionButton) v.findViewById(R.id.friend_add);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AddFriendDialog addFriendDialog = AddFriendDialog.newInstance();
//                addFriendDialog.show(getActivity().getSupportFragmentManager(),"hellp");

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView =(RecyclerView) v.findViewById(R.id.alarm_recycler_view);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recycleViewAdapter = new AlarmRecycleViewAdapter(alarms);
        recyclerView.setAdapter(recycleViewAdapter);
    }
    private void initAddAlarmButton(){
        addAlarmButton = (FloatingActionButton) v.findViewById(R.id.alarm_add);
        assert addAlarmButton!=null;
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                createAlarm();
                LayoutInflater lif = LayoutInflater.from(getContext());
                PopupWindow window = new PopupWindow(lif.inflate(R.layout.fragment_select_friend,null),100,100,true);
                window.showAsDropDown(StatusFragment.this.v);
                Intent intent = new Intent(getActivity(), SelectFriendActivity.class);
                startActivity(intent);
            }
        });
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
        alarms.clear();
        storage.removeAllAlarm();
        storage.removeAllPendingIntent();
        recycleViewAdapter.notifyDataSetChanged();
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

}
