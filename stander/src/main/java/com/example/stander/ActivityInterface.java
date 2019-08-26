package com.example.stander;

import android.app.Activity;
import android.os.Bundle;

public interface ActivityInterface {

    /**
     * 把宿主的环境给插件
     * @param activity
     */
    void insertAppContext(Activity activity);

    void onCreate(Bundle savedInstanceState);

    void onStart();


    void onResume();


    void onDestroy();
}
