package com.example.imac.cardlist.navigationDrawer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imac.cardlist.R;

public class FragmentSample extends Fragment {

    public static final String ARG_PARAM1 = "param1";
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private FloatingActionButton mPlusOneButton;
    private TextView tvTitle;

    private OnFragmentInteractionListener mListener;
    private NavigationDrawerSample mActivity;

    public FragmentSample() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSample.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSample newInstance(String param1, String param2) {
        FragmentSample fragment = new FragmentSample();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        mActivity=(NavigationDrawerSample)getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plus_one, container, false);

        mPlusOneButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        tvTitle=(TextView)view.findViewById(R.id.textView2);
        tvTitle.setText(mParam1);

        mPlusOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onFragmentInteraction(mParam1);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the state of the +1 button each time the activity receives focus.
        Log.e("Fragment onResume: ", "Focused");
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //getActivity().getMenuInflater().inflate(R.menu.navigation_drawer_sample,menu);
        mActivity.mBudgecounter++;
        BadgeCounter.addBudge(mActivity,menu.findItem(R.id.action_settings), BadgeCounter.BadgeColor.DEEP_ORANGE,R.drawable.ic_delete_black_24dp, mActivity.mBudgecounter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Log.e("onOptionsItemSelected: ", "called");
        }
        return super.onOptionsItemSelected(item);
    }
}
