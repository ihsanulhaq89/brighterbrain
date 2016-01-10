package com.ihsan.brighterbrain.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ihsan.brighterbrain.R;
import com.ihsan.brighterbrain.commons.MediaUtils;
import com.ihsan.brighterbrain.models.Item;
import com.orm.SugarRecord;

/**
 * Created by ihsan on 1/9/2016.
 */
public class AddItemActivity extends BaseItemActivity  {

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                this.finish();
                break;
            case R.id.btn_save:
                saveItem();
                break;
            case R.id.fab:
                if(filePath == null){
                    MediaUtils.createImageChooser(this);
                }else {
                    clearAttachment();
                }
                break;
        }
    }

    @Override
    protected void setFab() {
        if (filePath == null) {
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_file_attachment));
        } else {
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_content_clear));
        }
    }

    @Override
    protected boolean saveItem(){
        item = new Item();
        boolean result = super.saveItem();
        if(result){
            this.finish();
        }
        return result;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.txt_new_item);
    }
}
