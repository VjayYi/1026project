package com.butuh.uang.bu.tuhu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.butuh.uang.bu.tuhu.R;

import androidx.annotation.NonNull;

public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.LoadingDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
    }

}