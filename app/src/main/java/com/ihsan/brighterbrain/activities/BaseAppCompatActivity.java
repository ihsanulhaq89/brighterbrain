package com.ihsan.brighterbrain.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by ihsan on 1/9/2016.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getXMLLayoutID());
        initComponents();
        setListeners();
    }
    protected abstract void initComponents();
    protected abstract void setListeners();
    protected abstract int getXMLLayoutID();


}
