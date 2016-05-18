package com.zzl.demo.statusbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void lightMode1(View view) {
        Helper.statusBarLightMode(this);
    }

    public void lightMode2(View view) {
//        Helper.statusBarLightMode(this, Helper.MIUI);
//        Helper.statusBarLightMode(this, Helper.FLYME);
        Helper.statusBarLightMode(this, Helper.ANDROID_M);
    }

    public void darkMode(View view) {
//        Helper.statusBarDarkMode(this, Helper.MIUI);
//        Helper.statusBarDarkMode(this, Helper.FLYME);
        Helper.statusBarDarkMode(this, Helper.ANDROID_M);
    }
}
