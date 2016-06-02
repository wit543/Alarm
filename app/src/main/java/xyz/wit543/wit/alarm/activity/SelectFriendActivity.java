package xyz.wit543.wit.alarm.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.adapter.SelectFriendRecycleViewAdapter;
import xyz.wit543.wit.alarm.model.Storage;
import xyz.wit543.wit.alarm.model.User;

public class SelectFriendActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SelectFriendRecycleViewAdapter selectFriendRecycleViewAdapter;
    List<User> friends = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friend);
        init();
    }

    private void init() {
        initRecycleView();
        reloadFriend();
    }

    private void initRecycleView() {
        recyclerView = (RecyclerView) findViewById(R.id.friend_list);
        selectFriendRecycleViewAdapter = new SelectFriendRecycleViewAdapter(friends);
        recyclerView.setAdapter(selectFriendRecycleViewAdapter);
    }
    private void reloadFriend(){
        friends.clear();
        Iterator<User> friendsIt = Storage.getInstance().getFriends();
        while (friendsIt.hasNext()){
            friends.add(friendsIt.next());
        }
        selectFriendRecycleViewAdapter.notifyDataSetChanged();
        Log.v("test",selectFriendRecycleViewAdapter.getItemCount()+"");
    }

}
