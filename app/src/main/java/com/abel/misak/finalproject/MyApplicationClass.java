package com.abel.misak.finalproject;

import android.app.Application;
import android.content.res.Configuration;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplicationClass extends Application {

    private FirebaseDatabase mFirebaseDatabase;

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(getApplicationContext());

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mFirebaseDatabase.setPersistenceEnabled(true);
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
