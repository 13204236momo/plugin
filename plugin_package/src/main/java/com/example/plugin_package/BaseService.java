package com.example.plugin_package;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.stander.ServiceInterface;

public class BaseService extends Service implements ServiceInterface {
    public Service service;
    @Override
    public void insertAppContext(Service service) {
        this.service = service;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 0;
    }

    @Override
    public void onDestroy() {

    }
}
