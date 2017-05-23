package com.example.imac.cardlist.navigationDrawer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imac.cardlist.R;

public class FragmentSample2 extends Fragment {

    private String mParam1;
    private FloatingActionButton mPlusOneButton;
    private TextView tvTitle;

    public FragmentSample2() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentSample2 newInstance(String param1, String param2) {
        FragmentSample2 fragment = new FragmentSample2();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plus_one, container, false);

        mPlusOneButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        tvTitle=(TextView)view.findViewById(R.id.textView2);
        tvTitle.setText(mParam1+" fragment 3");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the state of the +1 button each time the activity receives focus.
        Log.e("Fragment 1 onResume: ", "Focused");
    }


}
