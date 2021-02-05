package com.example.androidtaskeslammohamed.utilits.application;

import android.app.Application;
import android.content.Context;

import com.example.androidtaskeslammohamed.firebase.FirebaseManager;

public class App extends Application {
    private static App mApp;

    private static FirebaseManager firebaseManager;

    public static FirebaseManager getFirebaseUtilits() {
        return firebaseManager;
    }

    public static synchronized App getInstance() {
        return mApp;
    }

    public static Context getAppContext() {
        return mApp.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initFirebase();
    }

    private void initFirebase() {
        firebaseManager = new FirebaseManager();
    }


}
