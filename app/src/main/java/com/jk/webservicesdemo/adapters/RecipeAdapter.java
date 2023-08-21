package com.jk.webservicesdemo.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jk.webservicesdemo.OnRecipeClickListener;
import com.jk.webservicesdemo.databinding.ItemRecipeBinding;
import com.jk.webservicesdemo.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final Context context;
    private final ArrayList<Recipe> recipeArrayList;
    ItemRecipeBinding binding;
    private final OnRecipeClickListener clickListener;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes, OnRecipeClickListener clickListener){
        this.recipeArrayList = recipes;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeViewHolder(ItemRecipeBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe currentRecipe = recipeArrayList.get(position);
        holder.bind(context, currentRecipe, clickListener);
    }

    @Override
    public int getItemCount() {
        Log.d("RecipeAdapter", "getItemCount: Number of recipes " +this.recipeArrayList.size() );
        return this.recipeArrayList.size();
    }


    public static class RecipeViewHolder extends RecyclerView.ViewHolder{
        ItemRecipeBinding itemBinding;

        public RecipeViewHolder(ItemRecipeBinding binding){
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        public void bind(Context context, Recipe currentRecipe, OnRecipeClickListener clickListener){
            itemBinding.tvRecipeName.setText(currentRecipe.getRecipeName());
            itemBinding.tvRegion.setText(currentRecipe.getRegionName());
            Picasso.get().load(currentRecipe.getImageThumb()).into(itemBinding.imgRecipe);

            itemBinding.imgRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onRecipeItemClicked(currentRecipe);
                }
            });
        }
    }
}
