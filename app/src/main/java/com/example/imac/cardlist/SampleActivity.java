package com.example.imac.cardlist;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.example.imac.cardlist.adapters.SectionListDataAdapter_Sample;
import com.example.imac.cardlist.models.SingleItemModel;
import com.example.imac.cardlist.view.CustomScrollView;
import com.example.imac.cardlist.view.GroupCircleButton;

import java.util.ArrayList;

/**
 * Created by imac on 3/10/17.
 */

public class SampleActivity extends AppCompatActivity {

    private GroupCircleButton mGroupbtns;

    private RecyclerView rv;
    private SectionListDataAdapter_Sample itemListDataAdapter;
    private ArrayList<SingleItemModel> mItemList;
    private LinearLayoutManager linearLayoutManager;

    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private int page=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);

        rv = (RecyclerView) findViewById(R.id.recyclerview);
        mItemList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            mItemList.add(new SingleItemModel("Item " + i, "URL " + i));
        }
        itemListDataAdapter = new SectionListDataAdapter_Sample(getApplicationContext(), mItemList);

        rv.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(itemListDataAdapter);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold) && page < 4) {
                    page++;
                    createDummyData(page);
                    Log.e("onScrolled: ", "Page - "+page);
                    loading = true;
                }

            }
        });

        CustomScrollView mScrview=(CustomScrollView)findViewById(R.id.CustomScrollView);
        mScrview.SetonCheckedlistener(new CustomScrollView.OnCheckedListener() {
            @Override
            public void onChecked(int current, SparseArray<Integer> selected) {
                Toast.makeText(getApplicationContext(), "Current : "+current+"\n Total : "+selected.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createDummyData(final int start) {

        mItemList.add(null);
        itemListDataAdapter.notifyItemInserted(mItemList.size());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mItemList.remove(mItemList.size() - 1);
                itemListDataAdapter.notifyItemInserted(mItemList.size());
                for (int i = start; i <= start + 5; i++) {
                    mItemList.add(new SingleItemModel("Item " + start + "" + i, "URL " + i));
                }
                itemListDataAdapter.notifyDataSetChanged();
                loading=false;
            }
        }, 5000);


    }
}
