package com.example.plugin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //加载插件，并启动插件中Activity
    public void loadPlugin(View view) {
        //1.加载插件
        int ret = PluginManager.getInstance(this).loadPlugin();
        //2.跳转Activity
        if (ret>0){
            Intent intent = new Intent(this,ProxyActivity.class);
            startActivity(intent);
        }
    }
}
