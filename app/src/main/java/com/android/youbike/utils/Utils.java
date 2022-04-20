package com.android.youbike.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

public class Utils {
    public static void setNavigationBarStatusBarTranslucent(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(option);
        activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

}
