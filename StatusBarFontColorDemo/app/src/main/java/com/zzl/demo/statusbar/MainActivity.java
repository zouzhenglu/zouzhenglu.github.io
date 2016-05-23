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
        StatusBarFontHelper.setStatusBarMode(this, true);
    }

    public void lightMode2(View view) {
        StatusBarFontHelper.setLightMode(this, StatusBarFontHelper.MIUI);
        //        StatusBarFontHelper.setStatusBarMode(this, StatusBarFontHelper.FLYME);
        //        StatusBarFontHelper.setStatusBarMode(this, StatusBarFontHelper.ANDROID_M);
    }

    public void darkMode(View view) {
        StatusBarFontHelper.setDarkMode(this, StatusBarFontHelper.MIUI);
        //        StatusBarFontHelper.setDarkMode(this, StatusBarFontHelper.FLYME);
        //        StatusBarFontHelper.setDarkMode(this, StatusBarFontHelper.ANDROID_M);
    }
}
