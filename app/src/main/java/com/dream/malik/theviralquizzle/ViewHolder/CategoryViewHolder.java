package com.dream.malik.theviralquizzle.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.malik.theviralquizzle.Interface.ItemClickListener;
import com.dream.malik.theviralquizzle.R;

/**
 * Created by malik on 2/17/2018.
 */
/*
ViewHolder design pattern is used to speed up rendering of your ListView - actually to make it work smoothly,
 findViewById is quite expensive (it does DOM( Document Object  model) parsing) when used each time a list item is rendered, it must traverse your layout hierarchy .
Since lists can redraw its items quite frequently during scrolling such overhead might be substantial

 If your app needs to display a scrolling list of elements based on large data sets
 (or data that frequently changes), you should use RecyclerView
 The RecyclerView widget is a more advanced and flexible version of ListView.
 */


public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
   public TextView category_name;
   public ImageView category_image;
   private ItemClickListener itemClickListener;//we have initialize interface


    public CategoryViewHolder(View itemView) {
        super(itemView);
        //in this constructor  we hav initialize category category name and category image
        category_name=itemView.findViewById(R.id.category_name);
        category_image=itemView.findViewById(R.id.category_image);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {//interface class with initialize
        //we will use this method in CategoryFragment Class when ever the category is clicked   this.itemClickListener = itemClickListener is invoked

        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        //Returns the Adapter position of the item represented by this ViewHolder.
        /*
Android's Adapter is described in the API documentation, as “a bridge between an AdapterView and the underlying data for that view”
 An AdapterView is a group of widgets (ie. view) components in Android that include the ListView, Spinner, and GridView.
 */
        itemClickListener.onClick(v,getAdapterPosition(),false);
        //it is a interface folder interface class which contain onclick abstract method

    }
}

