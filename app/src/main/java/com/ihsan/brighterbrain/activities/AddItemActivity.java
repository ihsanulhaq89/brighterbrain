package com.ihsan.brighterbrain.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
 * Created by ihsan on 1/9/2016.
 */
public class AddItemActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ImageView attachment;
    private FloatingActionButton removeAttachmentBtn;
    private EditText name;
    private EditText description;
    private EditText cost;
    private Button save;
    private Button cancel;
    private String filePath;

    @Override
    protected void initComponents() {
        attachment = (ImageView) findViewById(R.id.image);
        removeAttachmentBtn = (FloatingActionButton) findViewById(R.id.fab);
        name = (EditText) findViewById(R.id.et_name);
        description = (EditText) findViewById(R.id.et_description);
        cost = (EditText) findViewById(R.id.et_cost);
        save = (Button) findViewById(R.id.btn_save);
        cancel = (Button) findViewById(R.id.btn_cancel);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            MediaUtils.createImageChooser(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            Item item = new Item();
            item.setCost(cost.getText().toString());
            item.setName(name.getText().toString());
            item.setDescription(description.getText().toString());
            item.setDescription(description.getText().toString());
            item.setImage(filePath);
            SugarRecord.save(item);
            Toast.makeText(this, item.getName() + " added!", Toast.LENGTH_SHORT);
            this.finish();
        }
    }

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
                clearAttachment();
                break;
        }

    }

    private void clearAttachment() {
        attachment.setVisibility(View.GONE);
        removeAttachmentBtn.setVisibility(View.GONE);
    }
}
