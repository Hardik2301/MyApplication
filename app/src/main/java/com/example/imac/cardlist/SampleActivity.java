package com.example.imac.cardlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.imac.cardlist.view.GroupButtonView;

/**
 * Created by imac on 3/10/17.
 */

public class SampleActivity extends AppCompatActivity {

    GroupButtonView mSwitchButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);

        mSwitchButton = (GroupButtonView) findViewById(R.id.switchButton);
        mSwitchButton.setOnChangeListener(new GroupButtonView.OnChangeListener() {

            @Override
            public void onChange(int position) {
                Toast.makeText(getApplicationContext(),position+" Clicked",Toast.LENGTH_LONG).show();
            }
        });

    }
}
