package com.ilifesmart.androidlib.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ilifesmart.androidlib.activity.BaseActivity;
import com.ilifesmart.androidlib.interfaces.IOnTouchEvent;

public class BaseFragment extends Fragment implements IOnTouchEvent {

    @Override
    public void onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getActivity().getCurrentFocus();
            if (isShouldHideInput(v, event)) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }

                v.clearFocus();
            }
        }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((BaseActivity)getActivity()).registerTouchListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((BaseActivity)getActivity()).unregisterTouchListener(this);
    }

}
