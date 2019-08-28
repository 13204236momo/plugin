package com.example.plugin_package;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    public void setContentView(int resId) {
        activity.setContentView(resId);
    }

    public View findViewBtId(int layoutId){
        return activity.findViewById(layoutId);
    }

    @Override
    public void startActivity(Intent intent) {
        Intent intentNew = new Intent();
        intentNew.putExtra("className",intent.getComponent().getClassName());
        activity.startActivity(intentNew);
    }

    @Override
    public ComponentName startService(Intent service) {
        Intent intentNew = new Intent();
        intentNew.putExtra("className",service.getComponent().getClassName());
        return activity.startService(intentNew);
    }
}
