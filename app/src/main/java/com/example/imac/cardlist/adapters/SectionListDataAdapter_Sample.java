package com.example.imac.cardlist.adapters;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imac.cardlist.R;
import com.example.imac.cardlist.models.SingleItemModel;
import com.example.imac.cardlist.util.Screensize;

import java.util.ArrayList;

public class SectionListDataAdapter_Sample extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private ArrayList<SingleItemModel> itemsList;
    private Context mContext;

    public SectionListDataAdapter_Sample(Context context, ArrayList<SingleItemModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, viewGroup, false);
            return new SingleItemRowHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_progress_item, viewGroup, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {

        if(holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(Screensize.getCardItemWidth(mContext), Screensize.getCardItemHeight());
            loadingViewHolder.itemView.setLayoutParams(params);
            loadingViewHolder.progressBar.setIndeterminate(true);
        }else if(holder instanceof SingleItemRowHolder) {
            SingleItemRowHolder Itemholder=(SingleItemRowHolder)holder;
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(Screensize.getCardItemWidth(mContext), Screensize.getCardItemHeight());
            Itemholder.itemView.setLayoutParams(params);
            SingleItemModel singleItem = itemsList.get(i);

            Itemholder.tvTitle.setText(singleItem.getName());
        }
       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemViewType(int position) {
        return itemsList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;
        protected CardView itemView;
        protected ImageView itemImage;


        public SingleItemRowHolder(View view) {
            super(view);

            this.itemView=(CardView)view.findViewById(R.id.list_single_card_item);
            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        protected CardView itemView;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            this.itemView=(CardView)itemView.findViewById(R.id.list_single_progress_item);
        }
    }
}