package com.example.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.stander.ReceiverInterface;


public class ProxyReceiver extends BroadcastReceiver {

    private String className;


    public ProxyReceiver(String className) {
        this.className = className;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Class<?> aClass = PluginManager.getInstance(context).getClassLoader().loadClass(className);
            ReceiverInterface receiverInterface = (ReceiverInterface) aClass.newInstance();
            //注入组建环境
            receiverInterface.onReceive(context,intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
