package com.jk.webservicesdemo.models;

import com.google.gson.annotations.SerializedName;

public class Category {
    private @SerializedName("strCategory") String categoryName;
    private @SerializedName("idCategory") String id;
//    private String strCategory;


    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                '}';
    }
}
