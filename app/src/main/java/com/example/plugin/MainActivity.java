package com.example.plugin;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.example.plugin.utils.PermissionUtility;
import java.io.File;

import io.reactivex.functions.Consumer;


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

        PermissionUtility.getRxPermission(MainActivity.this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) //申请所需权限
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            //1.加载插件
                            int ret = PluginManager.getInstance(MainActivity.this).loadPlugin();
                            //2.跳转Activity
                            if (ret > 0) {
                                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "p.apk");
                                String path = file.getAbsolutePath();

                                PackageManager packageManager = getPackageManager();
                                PackageInfo packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
                                ActivityInfo activityInfo = packageInfo.activities[0];
                                Intent intent = new Intent(MainActivity.this, ProxyActivity.class);
                                intent.putExtra("className", activityInfo.name);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "请开启读写权限", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
