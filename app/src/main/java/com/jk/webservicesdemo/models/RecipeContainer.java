package com.jk.webservicesdemo.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecipeContainer {
    private @SerializedName("meals") ArrayList<Recipe> randomRecipe;

    public ArrayList<Recipe> getRandomRecipe() {
        return randomRecipe;
    }

    @Override
    public String toString() {
        return "RecipeContainer{" +
                "randomRecipe=" + randomRecipe +
                '}';
    }
}

