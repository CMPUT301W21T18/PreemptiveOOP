package com.example.preemptiveoop.uiwidget;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class MyDialog {
    public static void errorDialog(Context context, String title, String msg) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }
}