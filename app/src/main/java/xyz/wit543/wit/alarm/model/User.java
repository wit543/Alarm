package xyz.wit543.wit.alarm.model;

import android.graphics.Bitmap;

/**
 * Created by WIT on 09-May-16.
 */
public class User {
    private String userName;
    private int imageId;
    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public int getImageId() {
        return imageId;
    }
}
