package xyz.wit543.wit.alarm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.model.Alarm;

/**
 * Created by WIT on 19-Apr-16.
 */
public class AlarmRecycleViewAdapter extends RecyclerView.Adapter<AlarmRecycleViewAdapter.AlarmViewHolder>{
    public static class AlarmViewHolder extends RecyclerView.ViewHolder{
        private TextView statusTextView;
        private ImageView statusImageView;
        public AlarmViewHolder(View itemView) {
            super(itemView);
            statusTextView =(TextView) itemView.findViewById(R.id.text_view_status);
            statusImageView = (ImageView) itemView.findViewById(R.id.icon_status);
        }
    }
    private List<Alarm> statuses;
    public AlarmRecycleViewAdapter(List<Alarm> statuses){
        this.statuses=statuses;
    }


    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_alarm_status,parent,false);
        return new AlarmViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        holder.statusImageView.setImageResource(R.drawable.ic_alarm_black_24dp);
        holder.statusTextView.setText(statuses.get(position).getHour()+":"+statuses.get(position).getMinute());
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }
}
