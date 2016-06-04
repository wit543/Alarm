package xyz.wit543.wit.alarm.adapter;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.model.Alarm;
import xyz.wit543.wit.alarm.model.Status;
import xyz.wit543.wit.alarm.model.Storage;

/**
 * Created by WIT on 05-Jun-16.
 */
public class AlarmAddedToFriendAdapter extends RecyclerView.Adapter<AlarmAddedToFriendAdapter.AlarmAddedToFriendViewHolder>{
    private List<Status> statuses;
    public AlarmAddedToFriendAdapter(List<Status> statuses){
        this.statuses = statuses;
    }
    @Override
    public AlarmAddedToFriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_alarm_added_to_friend,parent,false);
        return new AlarmAddedToFriendViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlarmAddedToFriendViewHolder holder, final int position) {
        holder.name.setText(statuses.get(position).getSender().getUserName());
        holder.time.setText(statuses.get(position).getAlarm().getHour()+":"+statuses.get(position).getAlarm().getMinute());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Storage.getInstance().removeStatus(statuses.get(position));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    public static class AlarmAddedToFriendViewHolder extends RecyclerView.ViewHolder{
        public ImageView friendImage;
        public TextView name;
        public TextView time;
        public AlarmAddedToFriendViewHolder(View itemView) {
            super(itemView);
            friendImage = (ImageView)itemView.findViewById(R.id.friend_image);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);

        }
    }
}
