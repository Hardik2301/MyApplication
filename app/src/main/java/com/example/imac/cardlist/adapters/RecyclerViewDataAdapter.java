package com.example.imac.cardlist.adapters;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imac.cardlist.R;
import com.example.imac.cardlist.models.SectionDataModel;

import java.util.ArrayList;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {

    private ArrayList<SectionDataModel> dataList;
    private Context mContext;

    public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final ItemRowHolder itemRowHolder, int i) {

        final String sectionName = dataList.get(i).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

        itemRowHolder.itemTitle.setText(sectionName);

        final SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems);

        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
        itemRowHolder.recycler_view_list.setLayoutManager(linearLayoutManager);
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
        itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);

        itemRowHolder.btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rvpos=linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if(rvpos < itemListDataAdapter.getItemCount()){
                    //linearLayoutManager.scrollToPosition(rvpos+1);
                    itemRowHolder.recycler_view_list.smoothScrollToPosition(rvpos+1);
                }else{
                    Toast.makeText(mContext,"No item Available",Toast.LENGTH_LONG).show();
                }
            }
        });

        itemRowHolder.btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rvpos=linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                if(rvpos >= 1){
                    //linearLayoutManager.scrollToPosition(rvpos-1);
                    itemRowHolder.recycler_view_list.smoothScrollToPosition(rvpos-1);
                }else{
                    Toast.makeText(mContext,"No item Available",Toast.LENGTH_LONG).show();
                }
            }
        });

        itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "click event on more, " + sectionName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;
        protected RecyclerView recycler_view_list;
        protected Button btnMore;
        protected FloatingActionButton btn_next,btn_prev;

        public ItemRowHolder(View view) {
            super(view);

            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            this.btnMore = (Button) view.findViewById(R.id.btnMore);
            this.btn_next=(FloatingActionButton)view.findViewById(R.id.fab_listitem_next);
            this.btn_prev=(FloatingActionButton)view.findViewById(R.id.fab_listitem_prev);
        }
    }

}