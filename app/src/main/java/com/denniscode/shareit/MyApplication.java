package com.denniscode.shareit;

import android.app.Application;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Crashlytics collection
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
    }
}

