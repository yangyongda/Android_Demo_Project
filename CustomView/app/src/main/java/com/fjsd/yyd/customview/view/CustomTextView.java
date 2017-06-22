package com.fjsd.yyd.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.fjsd.yyd.customview.R;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class CustomTextView extends View{

    /**
     * 文本
     */
    private String mTitleText;
    /**
     * 文本的颜色
     */
    private int mTitleTextColor;
    /**
     * 文本的大小
     */
    private int mTitleTextSize;

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    private Paint mPaint;

    /*
    * 获取自定义属性
    * */
    public CustomTextView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTextView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.CustomTextView_titleText:
                    mTitleText = a.getString(attr);
                    break;
                case R.styleable.CustomTextView_titleTextColor:
                    // 默认颜色设置为黑色
                    mTitleTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomTextView_titleTextSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;

            }

        }
        a.recycle();

        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        // mPaint.setColor(mTitleTextColor);
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
    }

    public CustomTextView(Context context, AttributeSet attrs){
        this(context,attrs, 0);
    }
    public CustomTextView(Context context){
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height ;
        //宽度
        if (widthMode == MeasureSpec.EXACTLY) //一般是设置了明确的值或者是MATCH_PARENT
        {
            width = widthSize;
        } else {  //AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textWidth = mBound.width(); //文本的宽度
            //文本的宽度+左右的padding = 控件宽度
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }
        //高度
        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //设置画笔颜色
        mPaint.setColor(Color.YELLOW);
        //在画布上画出正方形区域
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleTextColor);
        //在画布上画文字
        canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }
}
