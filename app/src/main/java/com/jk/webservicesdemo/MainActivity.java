package com.jk.webservicesdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.jk.webservicesdemo.databinding.ActivityMainBinding;
import com.jk.webservicesdemo.models.CategoryContainer;
import com.jk.webservicesdemo.models.Recipe;
import com.jk.webservicesdemo.models.RecipeContainer;
import com.jk.webservicesdemo.network.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    ActivityMainBinding binding;
    private ArrayList<String> categoryList;
    private ArrayAdapter<String> adapter;
    private String TAG = this.getClass().getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.categoryList = new ArrayList<>();
        this.adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                this.categoryList);
        this.adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.binding.spnCategories.setAdapter(this.adapter);

        this.getCategoryList();
        this.binding.spnCategories.setOnItemSelectedListener(this);

        this.binding.btnGetRecipe.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view != null){
            switch (view.getId()){
                case R.id.btn_get_recipe:{
                    this.getRandomRecipe();
                    break;
                }
            }
        }
    }

    private void getCategoryList(){
        Call<CategoryContainer> categoryCall = RetrofitClient.getInstance().getApi().retrieveCategories();

        try{

            categoryCall.enqueue(new Callback<CategoryContainer>() {
                @Override
                public void onResponse(Call<CategoryContainer> call, Response<CategoryContainer> response) {

//                    if (response.isSuccessful()){
//
//                    }

                    if (response.code() == 200){
                        //extract the data
                        CategoryContainer mainResponse = response.body();
                        Log.d(TAG, "onResponse: Received Response : " + mainResponse.toString());
                        Log.d(TAG, "onResponse: Number of categories : " + mainResponse.getCategoryList().size());
                        Log.d(TAG, "onResponse: CategoryList : " + mainResponse.getCategoryList().toString());

                        if (mainResponse.getCategoryList().isEmpty()){
                            Log.e(TAG, "onResponse: No categories received");
                        }else{
                            categoryList.clear();

                            for (int i=0 ; i < mainResponse.getCategoryList().size(); i++){
                                Log.d(TAG, "onResponse: CategoryList objects " + mainResponse.getCategoryList().get(i).toString());
                                categoryList.add(mainResponse.getCategoryList().get(i).getCategoryName());
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }else{
                        Log.e(TAG, "onResponse: Unsuccessful response " + response.code() + response.errorBody() );
                    }

                    call.cancel();
                }

                @Override
                public void onFailure(Call<CategoryContainer> call, Throwable t) {
                    call.cancel();
                    Log.e(TAG, "onFailure: Failed to get the categoryList from API" + t.getLocalizedMessage() );
                }
            });


        }catch(Exception ex){
            Log.e(TAG, "getCategoryList: Cannot retrieve category list " + ex.getLocalizedMessage() );
        }
    }

    private void getRandomRecipe(){
        Call<RecipeContainer> recipeCall = RetrofitClient.getInstance().getApi().retrieveRandomRecipe();

        try{
            recipeCall.enqueue(new Callback<RecipeContainer>() {
                @Override
                public void onResponse(Call<RecipeContainer> call, Response<RecipeContainer> response) {
                    if (response.code() == 200){
                        if (response.body() != null) {
                            Log.d(TAG, "onResponse: " + response.body().getRandomRecipe());

                            Recipe currentRecipe = response.body().getRandomRecipe().get(0);
                            binding.tvRecipeName.setText(currentRecipe.getRecipeName());
                            binding.tvRegion.setText(currentRecipe.getRegionName());

                            //to retrieve image from URL
//                            Picasso.get().load(currentRecipe.getImageThumb()).into(binding.imgRecipe);

                            Glide.with(getApplicationContext()).load(currentRecipe.getImageThumb()).into(binding.imgRecipe);

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
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Log.d(TAG, "onItemClick: Selected meal category : " + this.categoryList.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}








