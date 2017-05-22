package com.example.imac.cardlist.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.imac.cardlist.R;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by imac on 5/16/17.
 */

public class CustomScrollView extends HorizontalScrollView {

    private LinearLayout lnv;
    public Context mContext;
    private SparseArray<CheckBox> mImageArrays;
    private SparseArray<StateListDrawable> mStateDrawables;
    private SparseArray<Integer> mSelectedImgs;
    private SparseIntArray mSparseIds;
    private int switchCount;
    private int marginToChild;
    private boolean mSingleSelection;
    private float cornerRadius,textsize;
    private CharSequence[] mTexts;
    private int checkedColor;
    private int unCheckedColor;
    private int strokeColor;
    private int strokeWidth;
    private ColorStateList textColor;
    private final int[] CHECKED_STATE = { android.R.attr.state_checked }, UNCHECKED_STATE = { -android.R.attr.state_checked };
    private OnCheckedListener mCheckedListener;

    public CustomScrollView(Context context) {
        super(context,null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        Log.e("CustomScrollView: ", "Called 2");

        mContext=context;
        lnv=new LinearLayout(context);
        lnv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        lnv.setOrientation(LinearLayout.HORIZONTAL);
        addView(lnv);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CustomScrollView);

        setTextColor(a.getColorStateList(R.styleable.CustomScrollView_android_textColor));
        setCornerRadius(a.getInt(R.styleable.CustomScrollView_cs_radious,20));
        setMarginToChild(a.getInt(R.styleable.CustomScrollView_cs_margintochild,5));
        setCheckedColor(a.getColor(R.styleable.CustomScrollView_cs_checkedcolor,getColor(R.color.colorAccent)));
        setUnCheckedColor(a.getColor(R.styleable.CustomScrollView_cs_uncheckedcolor,getColor(R.color.colorPrimary)));
        setStrokeColor(a.getColor(R.styleable.CustomScrollView_cs_strokecolor,Color.BLACK));
        setStrokeWidth(a.getInt(R.styleable.CustomScrollView_cs_strokewidth,0));
        setTextsize(a.getDimension(R.styleable.GroupCircleButton_android_textSize, 0));
        setTextArray(a.getTextArray(R.styleable.CustomScrollView_cs_textArray));
        setSingleSelection(a.getBoolean(R.styleable.CustomScrollView_cs_singleSelection,false));

        setHorizontalScrollBarEnabled(false);

        addChilds();
    }

    private void addChilds() {

        for (int i = 0; i < switchCount; i++) {
            if (mImageArrays == null)
                mImageArrays = new SparseArray<CheckBox>();

            CheckBox mImgButton = mImageArrays.get(i, createImageView());
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(getCalWidth(),getCalWidth());
            params.setMargins(marginToChild,0,0,0);
            mImgButton.setLayoutParams(params);
            mImgButton.setText(mTexts[i]);
            mImgButton.setButtonDrawable(new ColorDrawable());
            mImgButton.setClickable(true);
            if (Build.VERSION.SDK_INT >= 16) {
                mImgButton.setBackground(getStateDrawable(i));
            } else {
                mImgButton.setBackgroundDrawable(getStateDrawable(i));
            }

            if (mImgButton.getId() < 0) {
                int id = getViewId();
                if (mSparseIds == null)
                    mSparseIds = new SparseIntArray();
                mSparseIds.put(i, id);
                mImgButton.setId(id);
            } else {
                lnv.removeView(mImgButton);
            }
            mImgButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(mSingleSelection){
                        mSelectedImgs = null;
                    }
                    toggleSelection(buttonView,isChecked);
                }
            });
            lnv.addView(mImgButton, i);
            mImageArrays.put(i, mImgButton);
        }
    }

    private void toggleSelection(CompoundButton buttonView, boolean isChecked) {
         if(mSelectedImgs == null)
             mSelectedImgs=new SparseArray<Integer>();

        CheckBox mImg = (CheckBox) buttonView;
        int checkedId=mImg.getId();
        if(!isChecked)
        {
            mImg.setSelected(false);
            mSelectedImgs.remove(checkedId);
        }
        else
        {
            if(mSingleSelection){
                for(int i=0;i<mImageArrays.size();i++){
                    CheckBox cb=mImageArrays.valueAt(i);
                    if(!mImg.equals(cb))
                        cb.setChecked(false);
                }
                mSelectedImgs.put(checkedId, mImageArrays.indexOfValue(mImg));
            }else {
                mSelectedImgs.put(checkedId, mImageArrays.indexOfValue(mImg));
                mImg.setSelected(true);
            }
        }
        Log.e("toggleSelection: ", mSelectedImgs.toString());
        if (mCheckedListener != null && mSelectedImgs.size() > 0)
            mCheckedListener.onChecked(mImageArrays.indexOfValue(mImg),mSelectedImgs);
    }

    @Override
    protected float getLeftFadingEdgeStrength() {
        return 0.0f;
    }

    @Override
    protected float getRightFadingEdgeStrength() {
        return 0.0f;
    }

    public void SetonCheckedlistener(OnCheckedListener mCheckedListener)
    {
        this.mCheckedListener=mCheckedListener;
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        /*if (listeners != null) {
            for (OnPageChangeListener list: listeners) {
                list.onPageChange(this);
            }
        }*/
    }

    private CheckBox createImageView() {
        CheckBox mIButton = new CheckBox(getContext());
        mIButton.setGravity(Gravity.CENTER);
        mIButton.setEllipsize(TextUtils.TruncateAt.END);
        mIButton.setTextColor(textColor);
        if(textsize > 0) {
            mIButton.setTextSize(textsize);
        }
        return mIButton;
    }

    private Drawable getStateDrawable(int i) {
        if (mStateDrawables == null)
            mStateDrawables = new SparseArray<StateListDrawable>();
        StateListDrawable mStateListDrawable = mStateDrawables.size() >= i + 1 && (i != switchCount - 1 || i == switchCount - 1) ? null : mStateDrawables.get(i);
        if (mStateListDrawable == null) {
            float leftRadius = i == 0 ? cornerRadius : 0;
            float rightRadius = i == 0 ? 0 : i == switchCount - 1 ? cornerRadius : 0;
            float[] cRadius = { leftRadius, leftRadius, rightRadius, rightRadius, rightRadius, rightRadius, leftRadius, leftRadius };
            mStateListDrawable = new StateListDrawable();
            GradientDrawable cornerDrawable = new GradientDrawable();
            cornerDrawable.setColor(checkedColor);
            cornerDrawable.setShape(GradientDrawable.OVAL);
            mStateListDrawable.addState(CHECKED_STATE, cornerDrawable);
            cornerDrawable = new GradientDrawable();
            cornerDrawable.setColor(unCheckedColor);
            cornerDrawable.setStroke(strokeWidth, strokeColor);
            cornerDrawable.setShape(GradientDrawable.OVAL);
            mStateListDrawable.addState(UNCHECKED_STATE, cornerDrawable);
            mStateDrawables.put(i, mStateListDrawable);
        }
        return mStateListDrawable;
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public int getViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range
            // under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF)
                newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public int getColor(int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(mContext, id);
        } else {
            return mContext.getResources().getColor(id);
        }
    }

    private int getCalWidth(){
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int tmp=width/6;
        tmp = tmp - marginToChild;
        return tmp;
    }

    public interface OnCheckedListener {
        public void onChecked(int current, SparseArray<Integer> selected);
    }

    public void setTextArray(CharSequence[] mTexts) {
        this.mTexts=mTexts;
        switchCount = mTexts.length;
    }

    public void setMarginToChild(int marginToChild) {
        this.marginToChild = marginToChild;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
    }

    public void setUnCheckedColor(int unCheckedColor) {
        this.unCheckedColor = unCheckedColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setTextColor(ColorStateList textColor) {
        this.textColor = textColor;
    }

    public void setTextsize(float textsize) {
        this.textsize = textsize;
    }

    public void setSingleSelection(boolean mSingleSelection) {
        this.mSingleSelection = mSingleSelection;
    }
}
