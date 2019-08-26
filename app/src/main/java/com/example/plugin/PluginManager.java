package com.example.plugin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManager {
    private static final String TAG = PluginManager.class.getSimpleName();
    private static PluginManager instance;
    private Context context;
    private DexClassLoader dexClassLoader;
    private Resources resources;

    public static PluginManager getInstance(Context context) {
        if (instance == null) {
            synchronized (PluginManager.class) {
                if (instance == null) {
                    instance = new PluginManager(context);
                }
            }
        }
        return instance;
    }

    public PluginManager(Context context) {
        this.context = context;
    }

    /**
     * 加载插件（class   layout）
     */
    public int loadPlugin() {
        File file = new File(Environment.getExternalStorageState() + File.separator + "p.apk");
        if (!file.exists()) {
            Log.d(TAG, "插件包不存在");
            return -1;
        }

        /**
         * 下面是加载插件里的class
         */
        //插件包路径
        String pluginPath = file.getAbsolutePath();
        //dexClassLoader需要一个缓存路径
        String fileDir = context.getDir("p_dir", Context.MODE_PRIVATE).getAbsolutePath();
        dexClassLoader = new DexClassLoader(pluginPath, fileDir, null, context.getClassLoader());

        /**
         * 下面是加载插件里的layout
         */
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(assetManager, pluginPath);

            //宿主的资源配置信息
            Resources r = context.getResources();
            resources = new Resources(assetManager, r.getDisplayMetrics(), r.getConfiguration());

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }


        return 1;
    }

    public ClassLoader getClassLoader() {
        return dexClassLoader;
    }

    public Resources getResources(){
        return resources;
    }
}
