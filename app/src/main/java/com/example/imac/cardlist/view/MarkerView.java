package com.example.imac.cardlist.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.imac.cardlist.R;
import com.example.imac.cardlist.util.Screensize;

/**
 * Created by imac on 4/17/17.
 */

public class MarkerView extends View {

    private Paint mBubblePaint;
    private Path mBubblePath;
    private RectF mBubbleRectF;
    private Rect mRect;
    private String mProgressText = "";
    private int mBubbleRadius;
    private int mBubbleColor;// color of bubble
    private int mBubbleTextSize; // text size of bubble-progress
    private int mBubbleTextColor; // text color of bubble-progress

    public MarkerView(Context context) {
        this(context, null);
    }

    MarkerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    MarkerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mBubbleRadius= Screensize.dp2px(14);
        mBubbleColor = ContextCompat.getColor(context, R.color.colorAccent);
        mBubbleTextSize = Screensize.sp2px(14);
        mBubbleTextColor = ContextCompat.getColor(context, R.color.white);

        mBubblePaint = new Paint();
        mBubblePaint.setAntiAlias(true);
        mBubblePaint.setTextAlign(Paint.Align.CENTER);

        mBubblePath = new Path();
        mBubbleRectF = new RectF();
        mRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(3 * mBubbleRadius, 3 * mBubbleRadius);

        mBubbleRectF.set(getMeasuredWidth() / 2f - mBubbleRadius, 0,
                getMeasuredWidth() / 2f + mBubbleRadius, 2 * mBubbleRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mBubblePath.reset();
        float x0 = getMeasuredWidth() / 2f;
        float y0 = getMeasuredHeight() - mBubbleRadius / 3f;
        mBubblePath.moveTo(x0, y0);
        float x1 = (float) (getMeasuredWidth() / 2f - Math.sqrt(3) / 2f * mBubbleRadius);
        float y1 = 3 / 2f * mBubbleRadius;
        mBubblePath.quadTo(
                x1 - Screensize.dp2px(2), y1 - Screensize.dp2px(2),
                x1, y1
        );
        mBubblePath.arcTo(mBubbleRectF, 150, 240);

        float x2 = (float) (getMeasuredWidth() / 2f + Math.sqrt(3) / 2f * mBubbleRadius);
        mBubblePath.quadTo(
                x2 + Screensize.dp2px(2), y1 - Screensize.dp2px(2),
                x0, y0
        );
        mBubblePath.close();

        mBubblePaint.setColor(mBubbleColor);
        canvas.drawPath(mBubblePath, mBubblePaint);

        mBubblePaint.setTextSize(mBubbleTextSize);
        mBubblePaint.setColor(mBubbleTextColor);
        mBubblePaint.getTextBounds(mProgressText, 0, mProgressText.length(), mRect);
        Paint.FontMetrics fm = mBubblePaint.getFontMetrics();
        float baseline = mBubbleRadius + (fm.descent - fm.ascent) / 2f - fm.descent;
        canvas.drawText(mProgressText, getMeasuredWidth() / 2f, baseline, mBubblePaint);
        Log.e("Bubble radious",mBubbleRadius+" ");
        canvas.drawCircle(mBubbleTextSize+14, mBubbleTextSize, mBubbleRadius-3, mBubblePaint);

    }

    public void setText(String progressText) {
        if (progressText != null && !mProgressText.equals(progressText)) {
            mProgressText = progressText;
            invalidate();
        }
    }
}
