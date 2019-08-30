package com.example.stander;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public interface ReceiverInterface {

    void onReceive(Context context, Intent intent);

}
