package com.jk.webservicesdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jk.webservicesdemo.adapters.RecipeAdapter;
import com.jk.webservicesdemo.databinding.ActivityHomeBinding;
import com.jk.webservicesdemo.models.Recipe;
import com.jk.webservicesdemo.models.RecipeContainer;
import com.jk.webservicesdemo.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements OnRecipeClickListener{
    private final String TAG = this.getClass().getCanonicalName();
    private ActivityHomeBinding binding;
    private ArrayList<Recipe> recipeArrayList;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.recipeArrayList = new ArrayList<>();
        this.recipeAdapter = new RecipeAdapter(this, this.recipeArrayList, this::onRecipeItemClicked);

        this.binding.rvRecipeList.setLayoutManager(new LinearLayoutManager(this));
        this.binding.rvRecipeList.addItemDecoration(new DividerItemDecoration(this.getApplicationContext(), DividerItemDecoration.VERTICAL));
        this.binding.rvRecipeList.setAdapter(this.recipeAdapter);

        this.fetchRecipeByLetter("s");
    }

    private void fetchRecipeByLetter(String searchLetter){
//        www.themealdb.com/api/json/v1/1/search.php?f=a

        Call<RecipeContainer> recipeCall = RetrofitClient.getInstance().getApi().fetchRecipeByLetter(searchLetter);

        try{
            recipeCall.enqueue(new Callback<RecipeContainer>() {
                @Override
                public void onResponse(Call<RecipeContainer> call, Response<RecipeContainer> response) {
                    if (response.code() == 200){
                        if (response.body() != null) {
                            Log.d(TAG, "onResponse: " + response.body().getRandomRecipe());

                            ArrayList<Recipe> recipes = response.body().getRandomRecipe();
                            Log.d(TAG, "onResponse: Number of recipes recieved : " + recipes.size());

                            recipeArrayList.clear();
                            recipeArrayList.addAll(recipes);
                            //notify the adapter of data change
                            recipeAdapter.notifyDataSetChanged();
                        }else{
                            Log.e(TAG, "onResponse: Null response received");
                        }
                    }else{
                        Log.e(TAG, "onResponse: Unsuccessful response received " + response.code() );
                    }

                    call.cancel();
                }

                @Override
                public void onFailure(Call<RecipeContainer> call, Throwable t) {
                    call.cancel();
                    Log.e(TAG, "onFailure: Failed to get random recipe " + t.getLocalizedMessage() );
                }
            });
        }catch (Exception ex){
            Log.e(TAG, "getRandomRecipe: Cannot retrieve random recipe " + ex.getLocalizedMessage() );
        }
    }

    @Override
    public void onRecipeItemClicked(Recipe recipe) {
        Log.d(TAG, "onRecipeItemClicked: User selected " + recipe.toString());
        Toast.makeText(this, "Recipe : " + recipe.getRecipeName(), Toast.LENGTH_LONG).show();
    }
}