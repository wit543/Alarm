package xyz.wit543.wit.alarm.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.firebase.ui.auth.util.FirebaseAuthWrapperFactory;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.Servive.DownloadService;
import xyz.wit543.wit.alarm.adapter.AlarmRecycleViewAdapter;
import xyz.wit543.wit.alarm.adapter.SelectFriendRecycleViewAdapter;
import xyz.wit543.wit.alarm.model.Alarm;
import xyz.wit543.wit.alarm.model.Controler;
import xyz.wit543.wit.alarm.model.Storage;
import xyz.wit543.wit.alarm.model.User;
import xyz.wit543.wit.alarm.viewHolder.SelectFriendViewHolder;

public class SelectFriendActivity extends AppCompatActivity {

    private static final String KEY_FILE_URI = "key_file_uri";
    private static final String KEY_DOWNLOAD_URL = "key_download_url";
    private RecyclerView recyclerView;
    private Storage storage;
    private FirebaseRecyclerAdapter selectFriendRecycleViewAdapter;
    private List<User> friends = new ArrayList<>();
    private Controler controler;
    private DatabaseReference firebaseDatabase;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private Uri mDownloadUrl = null;
    private Uri mFileUri = null;
    private BroadcastReceiver downloadReceiver;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friend);
        init(savedInstanceState);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void init(Bundle savedInstanceState) {
        storage = Storage.getInstance();
        controler = Controler.getInstant();
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();


        // Restore instance state
        if (savedInstanceState != null) {
            mFileUri = savedInstanceState.getParcelable(KEY_FILE_URI);
            mDownloadUrl = savedInstanceState.getParcelable(KEY_DOWNLOAD_URL);
        }

        downloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                if (DownloadService.ACTION_COMPLETED.equals(intent.getAction())) {
                    String path = intent.getStringExtra(DownloadService.EXTRA_DOWNLOAD_PATH);
                    long numBytes = intent.getLongExtra(DownloadService.EXTRA_BYTES_DOWNLOADED, 0);

                    // Alert success
//                    showMessageDialog("Success", String.format(Locale.getDefault(),
//                            "%d bytes downloaded from %s", numBytes, path));
                }

                if (DownloadService.ACTION_ERROR.equals(intent.getAction())) {
                    String path = intent.getStringExtra(DownloadService.EXTRA_DOWNLOAD_PATH);

                    // Alert failure
//                    showMessageDialog("Error", String.format(Locale.getDefault(),
//                            "Failed to download from %s", path));
                }
            }
        };
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
                viewHolder.bindToFriend(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createAlarm(view.getContext());
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
                });
            }
        };
        recyclerView.setAdapter(new SelectFriendRecycleViewAdapter(this,friends));
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
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public String getUid() {
        if(FirebaseAuth.getInstance().getCurrentUser()==null)
            return "demo";
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
