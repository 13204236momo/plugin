package com.example.plugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;

import com.example.stander.ActivityInterface;

import java.lang.reflect.Constructor;

/**
 * 代理/占位 插件里的Activity
 */
public class ProxyActivity extends Activity {


    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance(this).getClassLoader();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance(this).getResources();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //做真正的加载 插件里的Activity
        String className = getIntent().getStringExtra("className");
        try {
            Class<?> activity = getClassLoader().loadClass(className);
           // Object o = activity.newInstance();
            Constructor<?> constructor = activity.getConstructor(new Class[]{});
            Object ac = constructor.newInstance(new Object[]{});
            ActivityInterface activityInterface = (ActivityInterface) ac;

            //注入
            activityInterface.insertAppContext(this);
            //执行插件里的onCreate方法
            Bundle bundle = new Bundle();
            bundle.putString("appName","我是宿主传递过来的信息");
            activityInterface.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra("className");

        //要给OtherActivity 进栈
        Intent intent1 = new Intent(this,ProxyActivity.class);
        intent1.putExtra("className",className);
        super.startActivity(intent1);
    }

    @Override
    public ComponentName startService(Intent service) {
        String className = service.getStringExtra("className");
        Intent intent = new Intent(this,ProxyService.class);
        intent.putExtra("className",className);
        return super.startService(intent);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
       String pluginReceiverClassName = receiver.getClass().getName();
        //在宿主注册广播
        return super.registerReceiver(new ProxyReceiver(pluginReceiverClassName), filter);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        super.sendBroadcast(intent);  //真正的发送
    }
}
