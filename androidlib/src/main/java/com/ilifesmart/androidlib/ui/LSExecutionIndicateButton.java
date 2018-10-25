package com.ilifesmart.androidlib.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.ilifesmart.androidlib.R;
import com.ilifesmart.androidlib.utils.DisplayUtils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * /**
 * {@link LSExecutionIndicateButton} can be used to display a simple rotating {@link Drawable} to give the user
 * the effect of a executing button. The {@link Drawable} will be displayed once the user clicks the button and will have to be
 * manually dismissed using the {@link #stopExecutingAnimation()} method.
 * <p>
 * Created by fanxq on 2017/6/16.
 */

@SuppressLint("AppCompatCustomView")
public class LSExecutionIndicateButton extends ImageButton {
    private static String TAG = LSExecutionIndicateButton.class.getSimpleName();
    private String mText;
    private TextPaint mTextPaint;
    private Rect mTextBounds;

    protected int mTextSize;
    protected int mTextColor;

    protected int mProgressIndicatorBackground;
    protected int mProgressIndicatorForeground;
    protected boolean mProgressIndicatorShowArrow = false;
    protected AtomicBoolean mExecutingAnimation = new AtomicBoolean(false);

    protected MaterialProgressDrawable mProgressDrawable;

    public LSExecutionIndicateButton(Context context) {
        this(context, null);
    }

    public LSExecutionIndicateButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LSExecutionIndicateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = null;
            try {
                a = context.obtainStyledAttributes(attrs, R.styleable.ExecutingIndicateButton);
                mText = a.getString(R.styleable.ExecutingIndicateButton_text);
                mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                mTextPaint.density = getResources().getDisplayMetrics().density;

                mTextColor = a.getColor(R.styleable.ExecutingIndicateButton_textColor, Color.BLACK);
                mTextPaint.setColor(mTextColor);
                mTextPaint.setTextAlign(Paint.Align.CENTER);
                mTextSize = (int) a.getDimension(R.styleable.ExecutingIndicateButton_textSize, DisplayUtils.sp2px(context, 16));
                mTextPaint.setTextSize(mTextSize);
                // 在xml文件中使用android:textStyle="bold" 可以将英文设置成粗体， 但是不能将中文设置成粗体，
                // 将中文设置成粗体的方法是：setFakeBoldText(true)
                // mTextPaint.setFakeBoldText(true);
                mTextPaint.setAntiAlias(true);
                mTextBounds = new Rect();

                mProgressIndicatorBackground = a.getColor(R.styleable.ExecutingIndicateButton_progressIndicatorBackground, Color.DKGRAY);
                mProgressIndicatorForeground = a.getColor(R.styleable.ExecutingIndicateButton_progressIndicatorForeground, Color.WHITE);
                mProgressIndicatorShowArrow = a.getBoolean(R.styleable.ExecutingIndicateButton_progressIndicatorShowArrow, false);
            } finally {
                if (a != null)
                    a.recycle();
            }
        }

        mProgressDrawable = new MaterialProgressDrawable(context, this);
        this.setImageDrawable(mProgressDrawable);
        shouldShowAnimation(false);
    }


    public void startExecutingAnimation() {
        if (mExecutingAnimation.compareAndSet(false ,true)) {
            shouldShowAnimation(true);
            this.invalidate();
        }
    }

    public void stopExecutingAnimation() {
        mExecutingAnimation.set(false);
        shouldShowAnimation(false);
        this.invalidate();
    }

    public boolean isExecutingAnimation() {
        return mExecutingAnimation.get();
    }

    public void setTextWithProps(String text, int textSize, int textColor) {
        this.mText = text;
        this.mTextPaint.setTextSize(textSize);
        this.mTextPaint.setColor(textColor);
        this.invalidate();
    }

    public void setText(String text) {
        mText = text;
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        invalidate();
    }

    /**
     * Display a executing animation if the user has clicked the button or hide it if {@link #stopExecutingAnimation()}
     * has been called.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (isExecutingAnimation() == false && !TextUtils.isEmpty(mText)) {
            mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);
            canvas.drawText(mText, getWidth() / 2, (getHeight() / 2) + ((mTextBounds.bottom - mTextBounds.top) / 2), mTextPaint);
        }
        super.onDraw(canvas);
    }

    protected void shouldShowAnimation(boolean shouldShow) {
        mProgressDrawable.setBackgroundColor(mProgressIndicatorBackground);
        mProgressDrawable.setColorSchemeColors(new int[]{mProgressIndicatorForeground});
        mProgressDrawable.showArrow(mProgressIndicatorShowArrow);
        mProgressDrawable.setArrowScale(1f); //0~1之间
        mProgressDrawable.setStartEndTrim(0f, 0.8f);
        mProgressDrawable.setProgressRotation(1f);
        mProgressDrawable.updateSizes(MaterialProgressDrawable.DEFAULT);

        if (shouldShow) {
            mProgressDrawable.setVisible(true, false);
            mProgressDrawable.setAlpha(255);
            mProgressDrawable.start();
        } else {
            mProgressDrawable.setVisible(false, false);
            mProgressDrawable.setAlpha(255);
            mProgressDrawable.stop();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mProgressDrawable != null) {
            stopExecutingAnimation();
            requestLayout();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mProgressDrawable != null) {
            stopExecutingAnimation();
        }
    }

}
