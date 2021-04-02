package com.example.preemptiveoop.user.model;

import android.content.Context;
import android.provider.Settings;

public class DeviceId {
    public static String getDeviceId(Context applicationContext) {
        return Settings.Secure.getString(applicationContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
