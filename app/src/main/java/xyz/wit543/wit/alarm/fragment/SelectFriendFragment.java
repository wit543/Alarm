package xyz.wit543.wit.alarm.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import xyz.wit543.wit.alarm.R;
import xyz.wit543.wit.alarm.adapter.SelectFriendRecycleViewAdapter;
import xyz.wit543.wit.alarm.model.Storage;
import xyz.wit543.wit.alarm.model.User;


public class SelectFriendFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private List<User> friends;
    private RecyclerView friendRecyclerView;
    private SelectFriendRecycleViewAdapter selectFriendRecycleViewAdapter;
    public SelectFriendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectFriendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectFriendFragment newInstance(String param1, String param2) {
        SelectFriendFragment fragment = new SelectFriendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_friend, container, false);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        friends = new ArrayList<User>();
        loadFriend();
        friendRecyclerView = (RecyclerView) getActivity().findViewById(R.id.friend_recycle_view);
//        selectFriendRecycleViewAdapter = new SelectFriendRecycleViewAdapter(friends);
    }

    private void loadFriend(){
        friends.clear();
        Iterator<User> ite = Storage.getInstance().getFriends();
        while (ite.hasNext()){
            friends.add(ite.next());
        }
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
