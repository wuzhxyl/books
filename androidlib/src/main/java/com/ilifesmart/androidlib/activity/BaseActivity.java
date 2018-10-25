package com.ilifesmart.androidlib.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.ilifesmart.androidlib.interfaces.IOnTouchEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {

    protected FragmentManager fm;
    private List<IOnTouchEvent> mTouchListeners = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        fm = getSupportFragmentManager();
    }

    @LayoutRes
    protected abstract int getLayoutResID();

    public void registerTouchListener(IOnTouchEvent listener) {
        if (listener == null) {
            return;
        }
        if (!mTouchListeners.contains(listener)) {
            mTouchListeners.add(listener);
        }
    }

    public void unregisterTouchListener(IOnTouchEvent listener) {
        if (listener == null) {
            return;
        }
        if (mTouchListeners.contains(listener)) {
            mTouchListeners.remove(listener);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (IOnTouchEvent listener : mTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static boolean isShouldHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];

            int bottom = top + v.getHeight();
            int right = left + v.getWidth();

            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
        }

        return false;
    }

}
