package com.example.imac.cardlist.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

import com.example.imac.cardlist.R;

/**
 * Created by imac on 3/10/17.
 */

public class CyclicLayout extends FrameLayout {

    private int height = 0;
    private int width = 0;
    private Path clipPath;

    public final static int CROP_INSIDE = 0;
    public final static int CROP_OUTSIDE = 1;

    public final static int POSITION_BOTTOM = 0;
    public final static int POSITION_TOP = 1;
    public final static int POSITION_LEFT = 2;
    public final static int POSITION_RIGHT = 3;

    private boolean cropInside = true;
    private float arcHeight;
    private float elevation;

    private int position;

    public CyclicLayout(Context context) {
        super(context);
        init(context, null);
    }

    public CyclicLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.CyclicLayout, 0, 0);
        arcHeight = styledAttributes.getDimension(R.styleable.CyclicLayout_arc_height, dpToPx(context, 10));

        final int cropDirection = styledAttributes.getInt(R.styleable.CyclicLayout_arc_cropDirection, CROP_INSIDE);
        cropInside = (cropDirection == CROP_INSIDE);

        position = styledAttributes.getInt(R.styleable.CyclicLayout_arc_position, POSITION_BOTTOM);

        styledAttributes.recycle();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    private Path createClipPath() {
        final Path path = new Path();

        switch (position){
            case POSITION_BOTTOM:{
                if (cropInside) {
                    path.moveTo(0, 0);
                    path.lineTo(0, height);
                    path.quadTo(width / 2, height - 2 * arcHeight, width, height);
                    path.lineTo(width, 0);
                    path.close();
                } else {
                    path.moveTo(0, 0);
                    path.lineTo(0, height - arcHeight);
                    path.quadTo(width / 2, height + arcHeight, width, height - arcHeight);
                    path.lineTo(width, 0);
                    path.close();
                }
                break;
            }
            case POSITION_TOP:
                if (cropInside) {
                    path.moveTo(0, height);
                    path.lineTo(0, 0);
                    path.quadTo(width / 2, 2 * arcHeight, width, 0);
                    path.lineTo(width, height);
                    path.close();
                } else {
                    path.moveTo(0, arcHeight);
                    path.quadTo(width / 2, -arcHeight, width, arcHeight);
                    path.lineTo(width, height);
                    path.lineTo(0, height);
                    path.close();
                }
                break;
        }

        return path;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            calculateLayout();
        }
    }

    private void calculateLayout() {
        height = getMeasuredHeight();
        width = getMeasuredWidth();
        if (width > 0 && height > 0) {

            clipPath = createClipPath();
            ViewCompat.setElevation(this, elevation);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !cropInside) {
                ViewCompat.setElevation(this, elevation);
                setOutlineProvider(new ViewOutlineProvider() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setConvexPath(clipPath);
                    }
                });
            }
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();

        canvas.clipPath(clipPath);
        super.dispatchDraw(canvas);

        canvas.restore();
    }

    private static float dpToPx(Context context, int dp) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public float getElevation() {
        return elevation;
    }

    public void setElevation(float elevation) {
        this.elevation = elevation;
    }

    public boolean isCropInside() {
        return cropInside;
    }

    public float getArcHeight() {
        return arcHeight;
    }

    public int getPosition() {
        return position;
    }
}
