package com.example.hepan.huaweiui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;


public class WaveView extends View {

    Paint mPaint;
    Path mPath;
    float mWidth;
    float mHeight;
    float mPercent = 0;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);

        mPath = new Path();

        postDelayed(new Runnable() {
            @Override
            public void run() {
                startValueAnimation();
            }
        }, 1000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();

        canvas.translate(mWidth * mPercent, 0);

        // 在画布的 x=0 处画一个点,方便理解原理
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0,60,10,mPaint);

        //画波浪线
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath.reset();
        mPath.moveTo(-mWidth,60);
        mPath.quadTo(-mWidth*0.75f,120,-mWidth*0.5f,60);
        mPath.quadTo(-mWidth*0.25f,0,0,60);
        mPath.quadTo(mWidth*0.25f,120,mWidth*0.5f,60);
        mPath.quadTo(mWidth*0.75f,0,mWidth,60);

        canvas.drawPath(mPath, mPaint);
    }

    private void startValueAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(2000);
        // 设置插值器,默认是加速减速插值器,会导致顿挫感
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }
}
