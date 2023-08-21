package com.jk.webservicesdemo.network;

import com.jk.webservicesdemo.models.CategoryContainer;
import com.jk.webservicesdemo.models.RecipeContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    //base URL must end with /
    String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    //HTTP request - Get
    //https://www.themealdb.com/api/json/v1/1/categories.php
    @GET("./categories.php")
    Call<CategoryContainer> retrieveCategories();

//    https://www.themealdb.com/api/json/v1/1/random.php
    @GET("./random.php")
    Call<RecipeContainer> retrieveRandomRecipe();

//    www.themealdb.com/api/json/v1/1/search.php?f=a

    @GET("./search.php")
    Call<RecipeContainer> fetchRecipeByLetter(@Query("f") String searchLetter);

//    www.themealdb.com/api/json/v1/1/filter.php?a=Canadian
//    @GET("./filter.php")
//    Call<RecipeContainer> fetchRecipeByArea(@Query("a") String country);

//    @GET("./filter.php?a={area}")
//    Call<RecipeContainer> fetchRecipeByArea(@Path("area") String country);


    //Weather example
//    BASE_URL = "https://api.weatherapi.com/v1/";


//    https://api.weatherapi.com/v1/current.json?key=44270dfc0b6d4087b0033718211311&q=19.017615,72.856164
//    https://api.weatherapi.com/v1/current.json?key=44270dfc0b6d4087b0033718211311&q=Toronto

//    @GET("./current.json")
//    Call<Weather> fetchWeather(@Query("key") String apiKey, @Query("q") String location);
}
