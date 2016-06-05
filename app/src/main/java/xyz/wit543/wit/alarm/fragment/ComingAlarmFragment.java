package xyz.wit543.wit.alarm.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.adapter.AlarmAddedToFriendAdapter;
import xyz.wit543.wit.alarm.adapter.ComingAlarmAdapter;
import xyz.wit543.wit.alarm.model.Status;
import xyz.wit543.wit.alarm.model.Storage;


public class ComingAlarmFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private List<Status> statuses;
    private Storage storage;
    private RecyclerView recyclerView;
    private ComingAlarmAdapter comingAlarmAdapter;
    private View v;
    private LinearLayoutManager linearLayoutManager;

    public ComingAlarmFragment() {
        init();
    }

    private void init() {
        statuses = new ArrayList<>();
        storage = Storage.getInstance();
        comingAlarmAdapter = new ComingAlarmAdapter(statuses);

    }

    private void reloadStatuses() {
        statuses.clear();
        Iterator<Status> ite = storage.getStatus();
        while (ite.hasNext()) {
            statuses.add(ite.next());
        }
        comingAlarmAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) v.findViewById(R.id.recycle_view);
        linearLayoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(comingAlarmAdapter);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_coming_alarm, container, false);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        initRecyclerView();
        reloadStatuses();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
