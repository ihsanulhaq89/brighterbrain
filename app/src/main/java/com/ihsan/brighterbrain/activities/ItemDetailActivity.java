package com.ihsan.brighterbrain.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
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
 * Created by ihsan on 1/10/2016.
 */
public class ItemDetailActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private ImageView attachment;
    private FloatingActionButton removeAttachmentBtn;
    private EditText name;
    private EditText description;
    private EditText cost;
    private EditText id;
    private Button save;
    private Button cancel;
    private Item item;
    private Menu menu;
    private boolean isEditMode = false;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            isEditMode = savedInstanceState.getBoolean("editmode");
        }
        item = (Item) getIntent().getExtras().getSerializable("ITEM");
        if(!isEditMode){
            paint();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("editmode", isEditMode);
        super.onSaveInstanceState(savedInstanceState);
    }
    private void clearAttachment() {
        filePath = null;
        attachment.setVisibility(View.GONE);
        removeAttachmentBtn.setVisibility(View.GONE);
        setMenu();
    }
    private void paint() {
        name.setText(item.getName());
        description.setText(item.getDescription());
        cost.setText(item.getCost());
        id.setText(item.getId() + "");
        if(item.getImage() != null && !item.getImage().isEmpty()){
            MediaUtils.setImageFromFile(this, item.getImage(), attachment);
            attachment.setVisibility(View.VISIBLE);
        }else {
            clearAttachment();
        }
        removeAttachmentBtn.setVisibility(View.GONE);
        save.setEnabled(false);
        cost.setEnabled(false);
        name.setEnabled(false);
        description.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            if(isEditMode) {
                MediaUtils.createImageChooser(this);
            }
            else {
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
            if(attachment.getVisibility() != View.GONE){
                menu.getItem(1).setVisible(false);
            }else {
                menu.getItem(1).setVisible(true);
                menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_file_attachment));
            }
        }else {
            menu.getItem(1).setVisible(true);
            menu.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_action_content_create));
        }
    }

    @Override
    protected void initComponents() {
        attachment = (ImageView) findViewById(R.id.image);
        removeAttachmentBtn = (FloatingActionButton) findViewById(R.id.fab);
        name = (EditText) findViewById(R.id.et_name);
        description = (EditText) findViewById(R.id.et_description);
        cost = (EditText) findViewById(R.id.et_cost);
        save = (Button) findViewById(R.id.btn_save);
        id = (EditText) findViewById(R.id.et_item_id);
        cancel = (Button) findViewById(R.id.btn_cancel);
        id.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setListeners() {
        removeAttachmentBtn.setOnClickListener(this);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    protected int getXMLLayoutID() {
        return R.layout.activity_add_item;
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
                clearAttachment();
                break;
            case R.id.btn_save:
                saveItem();
                break;

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        String filePath = MediaUtils.getImagePathFromActivityResult(this, requestCode, resultCode, data);
        if(MediaUtils.setImageFromFile(this, filePath, attachment)){
            this.filePath = filePath;
            attachment.setVisibility(View.VISIBLE);
            removeAttachmentBtn.setVisibility(View.VISIBLE);
        }
    }

    private boolean validate(EditText et){
        if(et.getText().toString().isEmpty()) {
            et.setError(getString(R.string.compulsory));
            return true;
        }
        return false;
    }
    private void saveItem(){
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
            Toast.makeText(this, item.getName() + " updated!", Toast.LENGTH_SHORT);
            setReadMode();
        }
    }

    private void setEditMode(){
        isEditMode = true;
        cost.setEnabled(true);
        name.setEnabled(true);
        description.setEnabled(true);
        save.setEnabled(true);
        if(attachment.getVisibility() != View.GONE){
            removeAttachmentBtn.setVisibility(View.VISIBLE);
        }
        setMenu();
    }
    private void setReadMode(){
        isEditMode = false;
        paint();
        setMenu();
    }
}
