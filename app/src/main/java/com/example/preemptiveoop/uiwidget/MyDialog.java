package com.example.preemptiveoop.uiwidget;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class MyDialog {
    public static AlertDialog messageDialog(Context context, String title, String msg) {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }
}