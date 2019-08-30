package com.example.plugin_package;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.stander.ReceiverInterface;

public class MyReceiver extends BroadcastReceiver implements ReceiverInterface {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"我是插件里的广播接受者",Toast.LENGTH_LONG).show();
    }
}
