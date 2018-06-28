package com.example.hepan.huaweiui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
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
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GRAY);

        mPath = new Path();

        postDelayed(new Runnable() {
            @Override
            public void run() {
                startValueAnimation();
            }
        }, 300);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();

        int startHeight = 100;
        for (int i = 0; i < 3; i++) {
            mPercent = mPercent + 0.4f * i;
            mPercent = mPercent % 1;
            canvas.save();
            canvas.translate(mWidth * mPercent, 0);

            //画波浪线
            mPath.reset();
            mPath.moveTo(-mWidth, startHeight);
            mPath.quadTo(-mWidth * 0.75f, startHeight + 80, -mWidth * 0.5f, startHeight);
            mPath.quadTo(-mWidth * 0.25f, startHeight-80, 0, startHeight);
            mPath.quadTo(mWidth * 0.25f, startHeight + 80, mWidth * 0.5f, startHeight);
            mPath.quadTo(mWidth * 0.75f, startHeight-80, mWidth, startHeight);

            mPath.lineTo(mWidth, mHeight);
            mPath.lineTo(-mWidth, mHeight);
            mPath.close();

            Shader shader = new LinearGradient(0, 0, 0, mHeight,
                    Color.argb(255, 129, 186, 248),
                    Color.argb(255, 135, 222, 250), Shader.TileMode.CLAMP);
            mPaint.setShader(shader);
            if (i == 0) {
                mPaint.setAlpha(50);
            }
            if (i == 1) {
                mPaint.setAlpha(100);
            }
            if (i == 2) {
                mPaint.setAlpha(150);
            }
            canvas.drawPath(mPath, mPaint);
            canvas.restore();
        }

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
