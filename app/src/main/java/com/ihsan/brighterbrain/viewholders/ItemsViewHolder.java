package com.ihsan.brighterbrain.viewholders;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihsan.brighterbrain.R;
import com.ihsan.brighterbrain.activities.ItemDetailActivity;
import com.ihsan.brighterbrain.commons.MediaUtils;
import com.ihsan.brighterbrain.commons.TransitionManager;
import com.ihsan.brighterbrain.models.Item;


/**
 * Created by ihsan on 1/9/2016.
 */
public class ItemsViewHolder extends ViewHolder implements View.OnClickListener{

    private final Activity context;
    TextView name;
    ImageView image;
    private Item item;

    public ItemsViewHolder(Activity context, View view) {
        super(view);
        this.context = context;
        name = (TextView) view.findViewById(R.id.name);
        image = (ImageView) view.findViewById(R.id.image);
        view.setOnClickListener(this);
    }
    public void paint(Item item){
        this.item = item;
        name.setText(item.getName());
        MediaUtils.setImageFromFile(context, item.getImage(), image);

    }
    @Override
    public void onClick(View v) {
        Bundle b = new Bundle();
        b.putSerializable("ITEM", item);
        TransitionManager.transitFrom(context, ItemDetailActivity.class, b);
    }
}
