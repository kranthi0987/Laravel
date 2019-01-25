package com.sanjay.laravel.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class utility {

    public static ProgressDialog showProgressDialog(Context context, String message) {
        ProgressDialog m_Dialog = new ProgressDialog(context);
        m_Dialog.setMessage(message);
        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        return m_Dialog;
    }

}
