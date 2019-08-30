package com.example.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

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
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "p.apk");
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

    /**
     * 反射系统源，来解析apk文件里的所有信息
     * 并注册静态广播
     * @return
     */
    public void parserApkAction() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "p.apk");
        if (!file.exists()) {
            Log.d(TAG, "插件包不存在");
            return;
        }

        try {
            // public Package parsePackage(File packageFile, int flags, boolean useCaches)
            // 执行此方法  得到package即AndroidManifest的所有内容
            Class<?> mPackageParserClass = Class.forName("android.content.pm.PackageParser");
            Object mPackageParser  = mPackageParserClass.newInstance();
            Method parsePackageMethod = mPackageParserClass.getMethod("parsePackage", File.class, int.class);
            Object mPackage = parsePackageMethod.invoke(mPackageParser, file, PackageManager.GET_ACTIVITIES);
            Field receiversField = mPackage.getClass().getField("receivers");
            //receivers 本质就是ArrayList
            Object receivers = receiversField.get(mPackage);
            ArrayList arrayList = (ArrayList) receivers;

            Class<?> mComponentClass = Class.forName("android.content.pm.PackageParser$Component");
            Field intentsField = mComponentClass.getField("intents");
            //此activity是PackageParser的一个内部类，对应AndroidManifest中的组件标签（activity，receiver，service等）
            for (Object mActivity : arrayList) {
             //获取<intent-filter>
                ArrayList<IntentFilter> intentFilterArrayList = (ArrayList) intentsField.get(mActivity);

                // 我们还有一个任务，就是要拿到 android:name=".StaticReceiver"
                // activityInfo.name; == android:name=".StaticReceiver"
                // 分析源码 如何 拿到 ActivityInfo

                Class mPackageUserState = Class.forName("android.content.pm.PackageUserState");

                Class mUserHandle = Class.forName("android.os.UserHandle");
                int userId = (int) mUserHandle.getMethod("getCallingUserId").invoke(null);

                /**
                 * 执行此方法，就能拿到 ActivityInfo
                 * public static final ActivityInfo generateActivityInfo(Activity a, int flags,
                 *             PackageUserState state, int userId)
                 */
                Method  generateActivityInfoMethod = mPackageParserClass.getDeclaredMethod("generateActivityInfo", mActivity.getClass()
                        , int.class, mPackageUserState, int.class);
                generateActivityInfoMethod.setAccessible(true);
                // 执行此方法，拿到ActivityInfo
                ActivityInfo mActivityInfo = (ActivityInfo) generateActivityInfoMethod.invoke(null,mActivity, 0, mPackageUserState.newInstance(), userId);
                String receiverClassName = mActivityInfo.name;

                Class mStaticReceiverClass = getClassLoader().loadClass(receiverClassName);

                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) mStaticReceiverClass.newInstance();
                for (IntentFilter intentFilter : intentFilterArrayList) {
                    //去拿到<receiver android:name=".StaticReceiver">
                    context.registerReceiver(broadcastReceiver,intentFilter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClassLoader getClassLoader() {
        return dexClassLoader;
    }

    public Resources getResources() {
        return resources;
    }
}
