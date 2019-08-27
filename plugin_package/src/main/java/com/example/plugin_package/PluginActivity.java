package com.example.plugin_package;

import android.os.Bundle;
import android.widget.Toast;

public class PluginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this,savedInstanceState.getString("appName"),Toast.LENGTH_LONG).show();
    }
}
