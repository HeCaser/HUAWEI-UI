package com.example.hepan.huaweiui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class ProgressCircle extends View {

    Paint mPaint;
    float mWidth;
    float mHeight;
    // 作图的最小范围
    float minSize;
    int mProgress = 10;
    float mDotPosition = 0;

    public ProgressCircle(Context context) {
        this(context, null);
    }

    public ProgressCircle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        minSize = Math.min(mWidth, mHeight) * 0.45f;
        // 移动画布,方便计算
        canvas.translate(mWidth / 2, mHeight / 2);
        drawProgress(canvas);
        drawCircle(canvas);
    }


    private void drawProgress(Canvas canvas) {
        canvas.save();
        for (int i = 1; i < 101; i++) {
            if (mProgress >= i) {
                mPaint.setColor(Color.RED);
            } else {
                mPaint.setColor(Color.GRAY);
            }
            // 刻度线的长度设置为 minSize 的 10%
            canvas.drawLine(0, -minSize, 0, -(minSize * 0.9f), mPaint);
            // 画布旋转
            canvas.rotate(3.6f, 0, 0);
        }
        canvas.restore();
    }

    private void drawCircle(Canvas canvas) {
        mPaint.setColor(Color.RED);
        canvas.save();
        canvas.rotate(mDotPosition * 3.6f, 0, 0);
        canvas.drawCircle(0, -(minSize * 0.85f), minSize * 0.03f, mPaint);
        canvas.restore();
    }

    // 开始小球动画
    public void startCircleRun() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
        animator.setDuration(1500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                mDotPosition = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    public void setmProgress(int mProgress) {
        if (mProgress >= 0 && mProgress <= 100) {
            this.mProgress = mProgress;
            invalidate();
        }
    }
}
