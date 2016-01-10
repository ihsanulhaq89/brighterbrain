package com.ihsan.brighterbrain.commons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.BitmapAjaxCallback;

import java.io.File;
import java.io.IOException;

/**
 * Created by ihsan on 1/10/2016.
 */
public class MediaUtils {

    public static String getImagePathFromActivityResult(Activity context, int requestCode, int resultCode, Intent data) {
        String selectedImagePath = null;
        if (resultCode == context.RESULT_OK) {
            if (requestCode == Constants.MEDIA_REQUEST_CODE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPathStringFromUri(context, selectedImageUri);
            }
        }
        return selectedImagePath;
    }

    public static String getPathStringFromUri(Activity context, Uri contentURI) {
        try {
            String path = null;
            Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) {
                path =  contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
            return path;
        } catch (Exception e){

            return null;
        }
    }


    public static void createImageChooser(Activity context){
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT,null);
        galleryIntent.setType("image/*");
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);


        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent);
        chooser.putExtra(Intent.EXTRA_TITLE, "title");

        Intent[] intentArray =  {cameraIntent};
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        context.startActivityForResult(chooser, Constants.MEDIA_REQUEST_CODE);
    }
    public static boolean setImageFromFile(Context context, String imageFilePath, ImageView imageView) {
        if(imageFilePath == null){
            return false;
        }
        File imgFile = new File(imageFilePath);
        if(imgFile.exists()){
            AQuery aq = new AQuery(context);
            //load image from file, down sample to target width of 300 pixels
            BitmapAjaxCallback cb = new BitmapAjaxCallback();

            //target width is necessary, the rotation will recreate the bitmap which can cause OOM issue if image is too large
            cb.url(imgFile.getAbsolutePath()).targetWidth(300).rotate(true);
            aq.id(imageView).image(cb);
            return true;
        }
        return false;
    }
}
