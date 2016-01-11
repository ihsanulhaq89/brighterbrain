package com.ihsan.brighterbrain.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ihsan.brighterbrain.R;
import com.ihsan.brighterbrain.adapters.ItemsAdapter;
import com.ihsan.brighterbrain.commons.Constants;
import com.ihsan.brighterbrain.commons.TransitionManager;
import com.ihsan.brighterbrain.models.Item;
import com.orm.SugarRecord;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private RecyclerView itemsView;
    private ItemsAdapter adapter;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = this.getSharedPreferences(
                Constants.SHARED, Context.MODE_PRIVATE);
        boolean isFirst = prefs.getBoolean(Constants.IS_FIRST, true);
        if(isFirst) {
            prefs.edit().putBoolean(Constants.IS_FIRST, false);
            sync();
        }else {

            List<Item> list = SugarRecord.listAll(Item.class);
            setAdapter(list);
        }
    }

    private void setAdapter(List<Item> list) {
        adapter = new ItemsAdapter(this, list);
        itemsView.setAdapter(adapter);
        itemsView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    protected void initComponents() {
        itemsView = (RecyclerView) findViewById(R.id.recycler_view);
        addButton = (FloatingActionButton) findViewById(R.id.fab);
    }

    @Override
    protected void setListeners() {
        addButton.setOnClickListener(this);
    }

    @Override
    protected int getXMLLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        TransitionManager.slideUp(this, AddItemActivity.class);
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.txt_collection);
    }

    private void sync(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(Constants.ITEM_TABLE);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> markers, ParseException e) {
                if (e == null) {
                   parseData(markers);
                } 
            }
        });
    }

    private void parseData(List<ParseObject> markers) {
        List<Item> items = new ArrayList<>();
        for(ParseObject obj: markers){
            Item item = new Item();
            item.setImage((String) obj.get("image"));
            item.setDescription((String) obj.get("description"));
            item.setName((String) obj.get("name"));
            item.setCost((String) obj.get("cost"));
            item.setId(new Long((Integer) obj.get("id")));
            items.add(item);
        }
        SugarRecord.saveInTx(items);
        setAdapter(items);
    }
}
