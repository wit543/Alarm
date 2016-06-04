package xyz.wit543.wit.alarm.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.adapter.AlarmRecycleViewAdapter;
import xyz.wit543.wit.alarm.adapter.SelectFriendRecycleViewAdapter;
import xyz.wit543.wit.alarm.model.Alarm;
import xyz.wit543.wit.alarm.model.Controler;
import xyz.wit543.wit.alarm.model.Storage;
import xyz.wit543.wit.alarm.model.User;
import xyz.wit543.wit.alarm.viewHolder.SelectFriendViewHolder;

public class SelectFriendActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Storage storage;
    private FirebaseRecyclerAdapter selectFriendRecycleViewAdapter;
    private List<User> friends = new ArrayList<>();
    private Controler controler;
    private DatabaseReference firebaseDatabase;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friend);
        init();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void init() {
        storage = Storage.getInstance();
        controler = Controler.getInstant();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        initRecycleView();
        reloadFriend();
    }

    private void initRecycleView() {
        recyclerView = (RecyclerView) findViewById(R.id.friends_recycle_view);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query postsQuery = getQuery(firebaseDatabase);

        selectFriendRecycleViewAdapter = new FirebaseRecyclerAdapter<User, SelectFriendViewHolder>(User.class,R.layout.cell_select_friend,SelectFriendViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(SelectFriendViewHolder viewHolder, User model, int position) {
                final DatabaseReference friendRef = getRef(position);
                final String postKey = friendRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createAlarm(v.getContext());
                    }
                });
            }
        };
        recyclerView.setAdapter(selectFriendRecycleViewAdapter);
    }
    private void createAlarm(final Context context){
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Log.v("test","in");
        TimePickerDialog tpd = new TimePickerDialog(context, R.style.red_dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Controler.getInstant().addAlarmToPending(hourOfDay,minute,context);
            }

        }, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), true);
        tpd.show();
    }
    public  Query getQuery(DatabaseReference databaseReference){
        return databaseReference.child("user-posts")
                .child(getUid());
    }
    private void reloadFriend() {
        friends.clear();
        Iterator<User> friendsIt = Storage.getInstance().getFriends();
        while (friendsIt.hasNext()) {
            friends.add(friendsIt.next());
        }
        selectFriendRecycleViewAdapter.notifyDataSetChanged();
        Log.v("test", selectFriendRecycleViewAdapter.getItemCount() + "");
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SelectFriend Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://xyz.wit543.wit.alarm.activity/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SelectFriend Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://xyz.wit543.wit.alarm.activity/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
