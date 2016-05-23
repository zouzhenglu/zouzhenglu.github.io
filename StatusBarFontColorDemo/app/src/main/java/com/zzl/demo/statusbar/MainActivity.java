package com.zzl.demo.statusbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.zzl.demo.statusbar.statusbar.StatusBarFontHelper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void lightMode1(View view) {
        StatusBarFontHelper.statusBarLightMode(this);
    }

    public void lightMode2(View view) {
//        StatusBarFontHelper.statusBarLightMode(this, StatusBarFontHelper.MIUI);
//        StatusBarFontHelper.statusBarLightMode(this, StatusBarFontHelper.FLYME);
        StatusBarFontHelper.statusBarLightMode(this, StatusBarFontHelper.ANDROID_M);
    }

    public void darkMode(View view) {
//        StatusBarFontHelper.statusBarDarkMode(this, StatusBarFontHelper.MIUI);
//        StatusBarFontHelper.statusBarDarkMode(this, StatusBarFontHelper.FLYME);
        StatusBarFontHelper.statusBarDarkMode(this, StatusBarFontHelper.ANDROID_M);
    }
}
