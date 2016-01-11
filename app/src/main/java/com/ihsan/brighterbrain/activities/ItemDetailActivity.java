package com.ihsan.brighterbrain.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ihsan.brighterbrain.R;
import com.ihsan.brighterbrain.commons.Constants;
import com.ihsan.brighterbrain.commons.MediaUtils;
import com.ihsan.brighterbrain.models.Item;
import com.orm.SugarRecord;

/**
 * Created by ihsan on 1/10/2016.
 */
public class ItemDetailActivity extends BaseItemActivity {
    private Menu menu;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            isEditMode = savedInstanceState.getBoolean(Constants.EDIT_MODE);
        }
        item = (Item) getIntent().getExtras().getSerializable(Constants.ITEM_SERIALIZABLE);
        filePath = item.getImage();
        if(!isEditMode){
            paint();
            setFab();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(Constants.EDIT_MODE, isEditMode);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void clearAttachment() {
        super.clearAttachment();
        setMenu();
    }
    private void paint() {
        name.setText(item.getName());
        description.setText(item.getDescription());
        cost.setText(item.getCost());
        id.setText(item.getId() + "");
        if(item.getImage() != null && !item.getImage().isEmpty()){
            MediaUtils.setImageFromFile(this, item.getImage(), attachment);
        }else {
            clearAttachment();
        }
        fab.setVisibility(View.GONE);
        save.setEnabled(false);
        cost.setEnabled(false);
        name.setEnabled(false);
        description.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            if(!isEditMode) {
                setEditMode();
            }
            return true;
        } else if(id == R.id.action_delete){
            deleteItem();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteItem() {
        SugarRecord.delete(item);
        item.deleteItemFromParse();
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        this.menu = menu;
        setMenu();
        return true;
    }

    private void setMenu() {
        if(menu == null){
            return;
        }
        if(isEditMode){
            if(filePath != null){
                menu.getItem(1).setVisible(false);
            }else {
                menu.getItem(1).setVisible(true);
            }
        }else {
            menu.getItem(1).setVisible(true);
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_content_create));
        }
    }

    @Override
    protected void initComponents() {
        super.initComponents();
        id.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                if(isEditMode)
                {
                    setReadMode();
                }else {
                    this.finish();
                }
                break;
            case R.id.fab:
                if(filePath != null) {
                    clearAttachment();
                }else {
                    MediaUtils.createImageChooser(this);
                }
                break;
            case R.id.btn_save:
                saveItem();
                break;

        }
    }

    @Override
    protected boolean saveItem(){
        boolean result = super.saveItem();
        if(result){
            item.updateToParse();
            Toast.makeText(this, item.getName() + Constants.DONE, Toast.LENGTH_SHORT).show();
            setReadMode();
        }
        return result;
    }

    private void setEditMode(){
        isEditMode = true;
        cost.setEnabled(true);
        name.setEnabled(true);
        description.setEnabled(true);
        save.setEnabled(true);
        setFab();
        setMenu();
        cancel.setText(getString(R.string.txt_cancel));
    }

    protected void setFab() {
        if(isEditMode) {
            if (filePath == null) {
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_file_attachment));
            } else {
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_content_clear));
            }
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }
    }

    private void setReadMode(){
        isEditMode = false;
        paint();
        setMenu();
        setFab();
        cancel.setText(getString(R.string.txt_close));
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.txt_item_detail);
    }
}
