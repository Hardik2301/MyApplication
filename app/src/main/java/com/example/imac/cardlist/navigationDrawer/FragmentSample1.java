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

import com.example.imac.cardlist.R;

public class FragmentSample1 extends Fragment {

    private String mParam1;
    private FloatingActionButton mPlusOneButton;
    private TextView tvTitle;

    MenuItem mBudgeMenu;
    NavigationDrawerSample mActivity;

    public FragmentSample1() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentSample1 newInstance(String param1, String param2) {
        FragmentSample1 fragment = new FragmentSample1();
        Bundle args = new Bundle();
        args.putString(FragmentSample.ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(FragmentSample.ARG_PARAM1);
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
                mActivity.mBudgecounter++;
                BadgeCounter.addBudge(getActivity(),mBudgeMenu, BadgeCounter.BadgeColor.DEEP_ORANGE,R.drawable.ic_delete_black_24dp,mActivity.mBudgecounter);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the state of the +1 button each time the activity receives focus.
        Log.e("Fragment 1 onResume: ", "Focused");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        mBudgeMenu=menu.findItem(R.id.action_settings);
    }
}
