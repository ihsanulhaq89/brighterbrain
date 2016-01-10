package com.ihsan.brighterbrain.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ihsan.brighterbrain.R;
import com.ihsan.brighterbrain.commons.Constants;
import com.ihsan.brighterbrain.commons.MediaUtils;
import com.ihsan.brighterbrain.models.Item;
import com.orm.SugarRecord;

/**
 * Created by ihsan on 1/11/2016.
 */
public abstract class BaseItemActivity extends BaseAppCompatActivity implements View.OnClickListener{

    protected ImageView attachment;
    protected FloatingActionButton fab;
    protected EditText name;
    protected EditText description;
    protected EditText cost;
    protected EditText id;
    protected Button save;
    protected Button cancel;
    protected String filePath;
    protected Item item;

    @Override
    protected void initComponents() {
        id = (EditText) findViewById(R.id.et_item_id);
        attachment = (ImageView) findViewById(R.id.image);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        name = (EditText) findViewById(R.id.et_name);
        description = (EditText) findViewById(R.id.et_description);
        cost = (EditText) findViewById(R.id.et_cost);
        save = (Button) findViewById(R.id.btn_save);
        cancel = (Button) findViewById(R.id.btn_cancel);
        setFab();
    }

    @Override
    protected void setListeners() {
        fab.setOnClickListener(this);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    protected int getXMLLayoutID() {
        return R.layout.activity_add_item;
    }

    protected boolean validate(EditText et){
        if(et.getText().toString().isEmpty()) {
            et.setError(getString(R.string.compulsory));
            return true;
        }
        return false;
    }

    protected boolean saveItem(){
        boolean error = validate(cost);
        error = error | validate(name);
        error = error | validate(description);
        if(!error) {
            item.setCost(cost.getText().toString());
            item.setName(name.getText().toString());
            item.setDescription(description.getText().toString());
            item.setDescription(description.getText().toString());
            item.setImage(filePath);
            SugarRecord.save(item);
            Toast.makeText(this, item.getName() + Constants.DONE, Toast.LENGTH_SHORT);
        }
        return !error;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        String filePath = MediaUtils.getImagePathFromActivityResult(this, requestCode, resultCode, data);
        if(MediaUtils.setImageFromFile(this, filePath, attachment)){
            this.filePath = filePath;
            setFab();
        }
    }

    protected void clearAttachment() {
        filePath = null;
        attachment.setImageDrawable(null);
        setFab();
    }

    protected abstract void setFab();

}
