package com.example.plugin_package;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import com.example.stander.ActivityInterface;

public class BaseActivity extends Activity implements ActivityInterface {
    public Activity activity;

    @Override
    public void insertAppContext(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @SuppressLint("MissingSuperCall")

    @Override
    public void onStart() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {

    }

    @SuppressLint("MissingSuperCall")

    @Override
    public void onDestroy() {

    }
}
