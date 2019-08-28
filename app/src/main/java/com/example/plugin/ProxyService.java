package com.example.plugin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.stander.ServiceInterface;

public class ProxyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String className = intent.getStringExtra("classname");
        try {
            Class<?> aClass = PluginManager.getInstance(this).getClassLoader().loadClass(className);
            ServiceInterface serviceInterface = (ServiceInterface) aClass.newInstance();
            //注入组建环境
            serviceInterface.insertAppContext(this);
            serviceInterface.onStartCommand(intent, flags, startId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
