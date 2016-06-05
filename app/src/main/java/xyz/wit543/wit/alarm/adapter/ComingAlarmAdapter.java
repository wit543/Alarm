package xyz.wit543.wit.alarm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import xyz.wit543.wit.alarm.model.Status;

/**
 * Created by WIT on 05-Jun-16.
 */
public class ComingAlarmAdapter extends RecyclerView.Adapter {
    private List<Status> statuses;
    public ComingAlarmAdapter(List<Status> statuses){
        this.statuses=statuses;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CommingAlarmViewHolder extends RecyclerView.ViewHolder{

        public CommingAlarmViewHolder(View itemView) {
            super(itemView);
        }
    }
}
