package xyz.wit543.wit.alarm.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.model.User;

/**
 * Created by WIT on 09-May-16.
 */
public class SelectFriendRecycleViewAdapter extends RecyclerView.Adapter<SelectFriendRecycleViewAdapter.SelectFriendViewHolder>{
    public static class SelectFriendViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        public SelectFriendViewHolder(View itemView) {
            super(itemView);
            name =(TextView) itemView.findViewById(R.id.name);
        }
    }

    private List<User> fruends;

    public SelectFriendRecycleViewAdapter(List<User> friends) {
        this.fruends = friends;
    }

    @Override
    public SelectFriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_select_friend,parent,false);
        Log.v("test","hello");
        return new SelectFriendViewHolder(v);

    }

    @Override
    public void onBindViewHolder(SelectFriendViewHolder holder, int position) {
        Log.v("test",fruends.get(position).getUserName()+" "+position);
        holder.name.setText(fruends.get(position).getUserName());
    }

    @Override
    public int getItemCount() {
        return fruends.size();
    }
}
