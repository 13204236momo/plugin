package com.example.plugin;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

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

    }




}
