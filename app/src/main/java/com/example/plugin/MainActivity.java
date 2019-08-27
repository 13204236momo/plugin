package com.example.plugin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.util.List;

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
        if (ret > 0) {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "p.apk");
            String path = file.getAbsolutePath();

            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
            ActivityInfo activityInfo = packageInfo.activities[0];
            Intent intent = new Intent(this, ProxyActivity.class);
            intent.putExtra("className", activityInfo.name);
            startActivity(intent);

        }
    }
}
