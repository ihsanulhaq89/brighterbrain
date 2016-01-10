package com.ihsan.brighterbrain.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.ihsan.brighterbrain.R;
import com.ihsan.brighterbrain.adapters.ItemsAdapter;
import com.ihsan.brighterbrain.commons.TransitionManager;
import com.ihsan.brighterbrain.models.Item;
import com.orm.SugarRecord;

import java.util.List;

public class MainActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private RecyclerView itemsView;
    private ItemsAdapter adapter;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAdapter();
    }

    private void setAdapter() {
        List<Item> list = SugarRecord.listAll(Item.class);
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
}
