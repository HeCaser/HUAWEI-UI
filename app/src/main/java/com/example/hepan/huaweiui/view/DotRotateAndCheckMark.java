package com.example.hepan.huaweiui.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * 圆点旋转和对勾
 */
public class DotRotateAndCheckMark extends View {

    Paint mPaint;
    float mWidth;
    float mHeight;
    // 作图的最小范围
    float minSize;
    float angle;

    float[] radio = new float[5];

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public DotRotateAndCheckMark(Context context) {
        this(context, null);
    }

    public DotRotateAndCheckMark(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotRotateAndCheckMark(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startCircleRun();
            }
        }, 2000);
        radio[0] = 1f;
        radio[1] = 1.2f;
        radio[2] = 1.5f;
        radio[3] = 1.9f;
        radio[4] = 2.5f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        minSize = Math.min(mWidth, mHeight) * 0.35f;
        drawDots(canvas);
    }

    private void drawDots(Canvas canvas) {
        canvas.translate(mWidth / 2, mHeight / 2);
        for (int i = 0; i < 5; i++) {
            canvas.save();
            canvas.rotate((float) (Math.pow(angle / 360, radio[i]) * 360), 0, 0);
            canvas.drawCircle(0, -minSize, 14, mPaint);
            canvas.restore();
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        }, 20);

    }

    // 开始小球动画
    public void startCircleRun() {
        // 控制 angle 参数变化的属性动画
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(this, "angle", 360);
        animator1.setDuration(1500);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.setInterpolator(new AccelerateDecelerateInterpolator());
        animator1.start();
    }

}
