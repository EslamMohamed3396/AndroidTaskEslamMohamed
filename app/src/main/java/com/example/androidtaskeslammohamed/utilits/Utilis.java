package com.example.androidtaskeslammohamed.utilits;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.androidtaskeslammohamed.utilits.application.App;

public class Utilis {
    private static final String TAG = "UtilisUtilis";
    //instance from shared pref


    //checking network
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void toastMessage(String message) {
        Toast.makeText(App.getAppContext(), message, Toast.LENGTH_LONG).show();
    }

}
