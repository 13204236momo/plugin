package com.example.stander;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public interface ServiceInterface {

    /**
     * 把宿主的环境给插件
     * @param appService
     */
    void insertAppContext(Service appService);

    IBinder onBind(Intent intent);

    void onCreate();

     int onStartCommand(Intent intent, int flags, int startId);

     void onDestroy();
}
