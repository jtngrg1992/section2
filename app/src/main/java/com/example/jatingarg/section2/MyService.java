package com.example.jatingarg.section2;

import android.app.Service;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.sql.Date;
import java.sql.Time;
import java.util.Locale;
import java.util.Random;

public class MyService extends Service {

    MyBinder mBinder = new MyBinder();
    Random mGenerator = new Random();
    private static final String TAG = "MyService";

    public MyService() {
        Log.d(TAG, "MyService: constructor called");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: called");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: called");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind: called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: called");
        return mBinder;
    }


    public int getRandomNumber(){
        return mGenerator.nextInt(100);
    }



    public class MyBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }
}
