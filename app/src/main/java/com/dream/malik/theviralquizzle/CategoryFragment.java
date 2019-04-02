package com.dream.malik.theviralquizzle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dream.malik.theviralquizzle.Common.Common;
import com.dream.malik.theviralquizzle.Interface.ItemClickListener;
import com.dream.malik.theviralquizzle.ViewHolder.CategoryViewHolder;
import com.dream.malik.theviralquizzle.model.Category;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

//A Fragment is a piece of an application's user interface or behavior that can be placed in an Activity .


public class CategoryFragment extends Fragment {
    private boolean isRunning;
    View myFragment;
    /*
     If your app needs to display a scrolling list of elements based on large data sets
 (or data that frequently changes), you should use RecyclerView
 The RecyclerView widget is a more advanced and flexible version of ListView.

     */
    RecyclerView listCategory;
    /*
    A LayoutManager is responsible for measuring and positioning item views within a RecyclerView
    as well as determining the policy for when to recycle item views that are no longer visible to the user.
    By changing the LayoutManager a RecyclerView can be used to implement a standard vertically scrolling list.
     */
    RecyclerView.LayoutManager layoutManager;
/*
This class is a generic way of backing an RecyclerView with a Firebase location.
 It handles all of the child events at the given Firebase location.
It marshals received data into the given class type
1s parameter:The Java class that maps to the type of objects stored in the Firebase location.
2nd parameter:  The ViewHolder class that contains the Views in the layout that is shown for each object.

 */
    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;
    DatabaseReference categories;
/*
Fragment.newInstance(args1,args2...) is used as static construction method. ... But in Fragment,
doing this can help us to save arguments, and we can get these arguments in onCreate() Method for when accidentally your app boom,
and Android help you to restore your fragment with Constructor without arguments.
 */
    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }
/*


onCreate():

The onCreate() method in a Fragment is called after the Activity's onAttachFragment() but before that Fragment's onCreateView().
In this method, you can assign variables, get Intent extras, and anything else that doesn't involve the View hierarchy
 (i.e. non-graphical initialisations). This is because this method can be called when the Activity's onCreate() is not finished,
 and so trying to access the View hierarchy here may result in a crash.
 */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        As all the category fetched from firebase JSON Array will be loaded when this method called
       *********--> Mechanism of loading category:  CategoryViewHolder xml contain [image view and textview]
       in CategoryViewHolder java code we have initialize imagev and txtv and set itemclickedlisner onclicked


        RecyclerView :it is a listview
        RecyclerView.LayoutManager:  it will give the item position
         FirebaseRecyclerAdapter<Category, CategoryViewHolder>: here
         Category class:it contain getters and setters to get name ,image and set name, image of the category
        CategoryViewHolder  :it contain the xml with written and initialize code for img(category img) and name(category name)


        [ Category] is the root child are [Name],[Image] which is fetched by this two parameters


         */
        categories = FirebaseDatabase.getInstance().getReference().child("Category");

    }

    @Nullable
    @Override
    /*
    onCreateView():

After the onCreate() is called (in the Fragment), the Fragment's onCreateView() is called.
 You can assign your View variables and do any graphical initialisations.
 You are expected to return a View from this method, and this is the main UI view,
  but if your Fragment does not use any layouts or graphics,
 you can return null (happens by default if you don't override).
     */
    /*
    Inflate gives you the object reference to that layout to call findViewById on.
    LayoutInflater is used to manipulate Android screen using predefined XML layouts.
     This class is used to instantiate layout XML file into its corresponding View objects. It is never used directly

     A ViewGroup is a special view that can contain other views (called children.)
     The view group is the base class for layouts and views containers
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(com.dream.malik.theviralquizzle.R.layout.fragment_category, container, false);
        listCategory = myFragment.findViewById(com.dream.malik.theviralquizzle.R.id.listCategory);
        listCategory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container != null ? container.getContext() : null);

/*
Avoid unnecessary layout passes by setting setHasFixedSize to true when you are adding or removing items
in the RecyclerView and that doesn't change it's height or the width.
 */
// we are setting position orientation of RecyclerView
        listCategory.setLayoutManager(layoutManager);
        loadCategories();

        return myFragment;//it is view obj
    }

    private void loadCategories() {


     adapter=new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(
             Category.class,//category get set img and name
             com.dream.malik.theviralquizzle.R.layout.category_layout,//CategoryViewHolder(xml) layout with init and back written code to work with Category.class
             CategoryViewHolder.class,//this is the back written code
             categories//Db ref obj where it is fetched and loaded in our following xml CategoryViewHolder.class and Category.class

     ) {
         @Override
         protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {
                 viewHolder.category_name.setText(model.getName());//Txtview of category_layout
                 Picasso.with(getActivity())
                         .load(model.getImage())
                         .into(viewHolder.category_image);//imgview of category_layout
                 viewHolder.setItemClickListener(new ItemClickListener() {
                     @Override
                     public void onClick(View view, int position, boolean isLongClick) {


                         //when clicked to category Sart.class Activity will be started
                         Intent startGame=new Intent(getActivity(),Start.class);
                         Common.categoryId=adapter.getRef(position).getKey();//it will get the position of category item
                         Common.categoryName=model.getName();//it will get the name of the category
                         startActivity(startGame);


                     }
                 });


             }
     };
        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);

    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }



}
