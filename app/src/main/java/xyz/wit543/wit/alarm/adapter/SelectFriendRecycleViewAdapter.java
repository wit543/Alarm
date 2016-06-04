package xyz.wit543.wit.alarm.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.activity.SelectFriendActivity;
import xyz.wit543.wit.alarm.model.Controler;
import xyz.wit543.wit.alarm.model.User;

/**
 * Created by WIT on 09-May-16.
 */
public class SelectFriendRecycleViewAdapter extends RecyclerView.Adapter <SelectFriendRecycleViewAdapter.SelectFriendViewHolder>{
    public static class SelectFriendViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        public SelectFriendViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createAlarm(v.getContext());
                }
            });
            name =(TextView) itemView.findViewById(R.id.name);
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
    }

    private List<User> friends;
    private Context context;
    public SelectFriendRecycleViewAdapter(Context context,List<User> friends) {
        this.friends = friends;
        this.context = context;
    }

    @Override
    public SelectFriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_select_friend,parent,false);
        Log.v("test","hello");
        return new SelectFriendViewHolder(v);

    }

    @Override
    public void onBindViewHolder(SelectFriendViewHolder holder, int position) {
        Log.v("test", friends.get(position).getUserName()+" "+position);
        holder.name.setText(friends.get(position).getUserName());
//        Picasso.with(context).load(Uri.parse("wit543.xyz")).into();
    }
    @Override
    public int getItemCount() {
        return friends.size();
    }

}
