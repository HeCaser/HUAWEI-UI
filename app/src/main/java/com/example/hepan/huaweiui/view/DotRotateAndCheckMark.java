package com.example.hepan.huaweiui.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
    // 第一个圆旋转的角度
    float angle;
    // 第一个圆最大半径
    float maxRadius;
    float[] radio = new float[5];
    ObjectAnimator circleAnimator;

    // 画对号相关参数
    boolean isDrawMark = false;
    Path path;
    int mMarkAlpha;

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
        }, 1000);
        radio[0] = 1f;
        radio[1] = 1.2f;
        radio[2] = 1.5f;
        radio[3] = 1.9f;
        radio[4] = 2.5f;
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        minSize = Math.min(mWidth, mHeight) * 0.25f;
        maxRadius = minSize * 0.06f;
        canvas.translate(mWidth / 2, mHeight / 2);
        if (isDrawMark) {
            drawMark(canvas);
        } else {
            drawDots(canvas);
        }
    }

    private void drawMark(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        path.moveTo(-minSize * 0.5f, -minSize * 0.15f);
        path.lineTo(-minSize * 0.05f, minSize * 0.3f);
        path.lineTo(minSize * 0.6f, -minSize * 0.4f);
        mPaint.setAlpha(mMarkAlpha);
        canvas.drawPath(path, mPaint);
    }

    private void drawDots(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 5; i++) {
            canvas.save();
            canvas.rotate((float) (Math.pow(angle / 360, radio[i]) * 360), 0, 0);
            canvas.drawCircle(0, -minSize, getRadius(angle, i), mPaint);
            canvas.restore();
        }
        //
        postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        }, 20);

    }

    // 获取小球半径
    private float getRadius(float angele, int pos) {
        float radius;
        if (angele <= 180) {
            radius = (1 - (angele / 360)) * maxRadius;
        } else {
            radius = (angele / 360) * maxRadius;
        }
        return radius / radio[pos];
    }

    // 开始小球动画
    private void startCircleRun() {
        // 控制 angle 参数变化的属性动画
        if (circleAnimator == null) {
            circleAnimator = ObjectAnimator.ofFloat(this, "angle", 360);
            circleAnimator.setDuration(1500);
            circleAnimator.setRepeatCount(ValueAnimator.INFINITE);
            circleAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            circleAnimator.start();// 动画开始后,setAngele()会被反射调用从而设置 angele 值
        } else {
            circleAnimator.cancel();
            circleAnimator.start();
        }

    }

    // 开始对勾
    public void drawCheckMark() {
        if (circleAnimator != null) circleAnimator.cancel();
        isDrawMark = true;
        ValueAnimator animator = ValueAnimator.ofInt(0, 255);
        animator.setDuration(2500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMarkAlpha = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

}
