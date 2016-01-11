package com.ihsan.brighterbrain.adapters;

/**
 * Created by ihsan on 1/9/2016.
 */


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihsan.brighterbrain.R;
import com.ihsan.brighterbrain.viewholders.ItemsViewHolder;
import com.ihsan.brighterbrain.models.Item;
import java.util.List;

public class ItemsAdapter  extends RecyclerView.Adapter<ItemsViewHolder>{
        private List<Item> itemList;
        private Context mContext;

        public ItemsAdapter(Context context, List<Item> itemList) {
            this.itemList = itemList;
            this.mContext = context;
        }

        @Override
        public ItemsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, null);
            ItemsViewHolder viewHolder = new ItemsViewHolder((Activity)mContext, view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ItemsViewHolder customViewHolder, int i) {
            Item item = itemList.get(i);
            customViewHolder.paint(item);
        }

        @Override
        public int getItemCount() {
            return (null != itemList ? itemList.size() : 0);
        }

}
