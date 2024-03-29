package com.shantanu.car4sale;

import android.app.Application;
import android.content.Context;

public class ContextResolverApp extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        ContextResolverApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ContextResolverApp.context;
    }

}
