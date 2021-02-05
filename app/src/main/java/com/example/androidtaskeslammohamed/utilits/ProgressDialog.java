package com.example.androidtaskeslammohamed.utilits;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.example.androidtaskeslammohamed.R;


public class ProgressDialog {
    private static ProgressDialog mInstance;
    private Dialog dialog;

    public static synchronized ProgressDialog getInstance() {
        if (mInstance == null) {
            mInstance = new ProgressDialog();
        }
        return mInstance;
    }

    public void show(Context context) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.loading);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}