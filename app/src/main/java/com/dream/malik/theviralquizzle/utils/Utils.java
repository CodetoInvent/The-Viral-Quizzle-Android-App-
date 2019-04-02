package com.dream.malik.theviralquizzle.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by malik on 2/2/2018.
 */

public class Utils {
    /*
    It's a class whose implementation is provided by the Android system.
    Context allows access to application-specific resources and classes,
     as well as calls for application-level operations such as launching activities, broadcasting and receiving intents, etc
     */
    public Context mContext;

    /**
     * Public constructor that takes mContext for later use
     */
    public Utils(Context con) {
        mContext = con;
    }


    //This is a method to Check if the device internet connection is currently on
    public  boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager

                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }
}
