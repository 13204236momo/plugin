package com.example.plugin_package;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PluginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(activity, savedInstanceState.getString("appName"), Toast.LENGTH_LONG).show();

        findViewBtId(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, OtherActivity.class));
            }
        });

        findViewBtId(R.id.btn_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, TestService.class);
                startService(intent);
            }
        });


        findViewBtId(R.id.btn_receiver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.example.plugin_package.ACTION");
                registerReceiver(new MyReceiver(), intentFilter);
            }
        });

        findViewBtId(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("com.example.plugin_package.ACTION");
                sendBroadcast(intent);
            }
        });
    }


}
