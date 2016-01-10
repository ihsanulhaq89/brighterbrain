package com.ihsan.brighterbrain.models;


import com.orm.dsl.Table;

import java.io.Serializable;

/**
 * Created by ihsan on 1/9/2016.
 */

@Table
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
}
