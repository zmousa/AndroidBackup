package com.zmousa.androidbackup.application;

import android.app.Application;
import android.content.Context;

public class BackupApplication extends Application{
    public static BackupApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized BackupApplication getInstance() {
        return instance;
    }

    public static Context getContext(){
        return BackupApplication.getInstance().getApplicationContext();
    }

}
