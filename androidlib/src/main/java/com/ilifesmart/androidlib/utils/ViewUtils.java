package com.ilifesmart.androidlib.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ilifesmart.androidlib.activity.BaseActivity;

public class ViewUtils {

    public static void setEditorEnable(BaseActivity context, int enable, View container) {
        final boolean isenable = (enable == 1);
        View v = context.getCurrentFocus();
        if (v instanceof EditText) {
            if (isenable) {
                container.setFocusableInTouchMode(false);
                v.requestFocus();
            } else {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                }

                container.setFocusableInTouchMode(true);
                container.requestFocus();
            }
        }
    }

    public static void setEditorEnable(BaseActivity context, boolean enable, EditText v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (enable) {
            v.setFocusableInTouchMode(true);
            v.requestFocus();
            if (imm != null) {
                imm.showSoftInput(v, 0);
            }
        } else {
            v.clearFocus();
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
            }

        }
    }


}
