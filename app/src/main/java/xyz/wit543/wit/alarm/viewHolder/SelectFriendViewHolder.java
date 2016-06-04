package xyz.wit543.wit.alarm.viewHolder;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.model.User;

/**
 * Created by WIT on 04-Jun-16.
 */
public class SelectFriendViewHolder extends RecyclerView.ViewHolder {
    public TextView username;
    public ImageView userImage;
    public SelectFriendViewHolder(View itemView) {
        super(itemView);
        username = (TextView) itemView.findViewById(R.id.name);
        userImage = (ImageView) itemView.findViewById(R.id.friend_image);
    }
    public void bindToFriend(User user, View.OnClickListener onClickListener){
        username.setText(user.getUserName());
        userImage.setImageResource(user.getImageId());
    }
}
