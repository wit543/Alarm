package xyz.wit543.wit.alarm.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import xyz.wit543.wit.alarm.R;

/**
 * Created by WIT on 23-Apr-16.
 */
public class AddFriendDialog extends android.support.v4.app.DialogFragment {
    public static AddFriendDialog newInstance() {

        Bundle args = new Bundle();

        AddFriendDialog fragment = new AddFriendDialog();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.add_friend_dialog,null)).
                setPositiveButton("Add friend", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
