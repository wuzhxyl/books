package com.ilifesmart.androidlib.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.EditText;

import com.ilifesmart.androidlib.R;
import com.ilifesmart.androidlib.utils.DisplayUtils;

public class DrawableLeftEditText extends AppCompatEditText {

    private Drawable drawableLeft;
    private int scaleWidth;
    private int scaleHeight;

    public DrawableLeftEditText(Context context) {
        this(context, null);
    }

    public DrawableLeftEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableLeftEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        Context context = getContext();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawableLeftEditText);

        drawableLeft = a.getDrawable(R.styleable.DrawableLeftEditText_leftDrawable);
        scaleWidth = a.getDimensionPixelOffset(R.styleable.DrawableLeftEditText_drawableWidth, DisplayUtils.dip2px(context, 30));
        scaleHeight = a.getDimensionPixelOffset(R.styleable.DrawableLeftEditText_drawableHeight, DisplayUtils.dip2px(context, 30));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (drawableLeft != null) {
            drawableLeft.setBounds(10, 0, scaleWidth, scaleHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setCompoundDrawables(drawableLeft, null, null, null);
        setCompoundDrawablePadding(20);
    }

    /**
     * 设置左侧图片并重绘
     * @param drawableLeft
     */
    public void setDrawableLeft(Drawable drawableLeft) {
        this.drawableLeft = drawableLeft;
        invalidate();
    }

    /**
     * 设置左侧图片并重绘
     * @param drawableLeftRes
     */
    public void setDrawableLeft(int drawableLeftRes) {
        this.drawableLeft = getContext().getResources().getDrawable(drawableLeftRes);
        invalidate();
    }
}
