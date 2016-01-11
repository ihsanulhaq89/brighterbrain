package com.ihsan.brighterbrain;

import com.ihsan.brighterbrain.models.Item;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by ihsan on 1/11/2016.
 */
public class BrighterBrainApplication extends com.orm.SugarApp{
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this);
    }
}
