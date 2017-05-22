package com.example.imac.cardlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.imac.cardlist.adapters.RecyclerViewDataAdapter;
import com.example.imac.cardlist.models.SectionDataModel;
import com.example.imac.cardlist.models.SingleItemModel;
import com.example.imac.cardlist.view.GroupButtonView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private GroupButtonView mSwitchButton;

    ArrayList<SectionDataModel> allSampleData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        allSampleData = new ArrayList<SectionDataModel>();

        if (toolbar != null) {
            toolbar.setTitle("Sample App");
            setSupportActionBar(toolbar);
        }

        createDummyData();
        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, allSampleData);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter);

        mSwitchButton = (GroupButtonView) findViewById(R.id.switchButton);
        mSwitchButton.setOnChangeListener(new GroupButtonView.OnChangeListener() {
            @Override
            public void onChange(int position) {
                Toast.makeText(getApplicationContext(),position+" Clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createDummyData() {
        for (int i = 1; i <= 5; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle("Section " + i);

            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "URL " + j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }
}
