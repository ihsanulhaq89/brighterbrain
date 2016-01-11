package com.ihsan.brighterbrain.models;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.androidquery.AQuery;
import com.ihsan.brighterbrain.commons.Constants;
import com.ihsan.brighterbrain.commons.MediaUtils;
import com.orm.dsl.Table;
import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Created by ihsan on 1/9/2016.
 */

@Table
@ParseClassName(Constants.ITEM_TABLE)
public class Item implements Serializable{

    private Long id;
    private String name;
    private String description;
    private String image;
    private String location;
    private String cost;

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", location='" + location + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }

    public void saveToParse(){
        ParseObject object = new ParseObject(Constants.ITEM_TABLE);
        saveParseObject(object);

    }

    private void saveParseObject(ParseObject object) {
        object.put("id", id);
        object.put("cost", cost);
        object.put("description", description);
        object.put("name", name);
        if(image != null) {
            object.put("image", image);
        }
        try {
            object.save();
        } catch (ParseException e) {

        }
    }

    public void updateToParse() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Constants.ITEM_TABLE);
        query.whereEqualTo("id", id);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    saveParseObject(list.get(0));
                }
            }
        });
    }

    public void deleteItemFromParse() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Constants.ITEM_TABLE);
        query.whereEqualTo("id", id);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    try {
                        list.get(0).delete();
                    } catch (ParseException e1) {
                    }
                }
            }
        });
    }
}
