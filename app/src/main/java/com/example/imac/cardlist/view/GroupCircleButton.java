package com.example.imac.cardlist.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.imac.cardlist.R;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by imac on 3/10/17.
 */

public class GroupCircleButton extends RadioGroup implements OnCheckedChangeListener {

    private final int[] CHECKED_STATE = { android.R.attr.state_checked }, UNCHECKED_STATE = { -android.R.attr.state_checked };

    private final static int DEFAULT_SWITCH_COUNT = 2;

    private final static int EX = 5;// margins

    private ColorStateList mTextColor;

    private int mParentWidth, mParentHeight;

    private int mRadioStyle;

    private float cornerRadius, textSize;

    private int checkedColor, unCheckedColor, strokeColor, strokeWidth;

    private CharSequence[] mTexts;

    private int switchCount;

    private final int default_margins = 5;

    private int marginToChild;

    // is measurement completed
    private boolean isMeasure;

    private SparseArray<RadioButton> mRadioArrays;
    private SparseArray<Drawable> mButtonDrawables;
    private SparseArray<StateListDrawable> mStateDrawables;
    private SparseIntArray mSparseIds;

    private int mCurrentPosition;

    private OnChangeListener changeListener;

    /**
     * @param context
     */
    public GroupCircleButton(Context context) {
        super(context, null);
    }

    public GroupCircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, new int[] { android.R.attr.orientation, android.R.attr.layout_height });
        setOrientation(LinearLayout.HORIZONTAL);
        //mParentHeight = a.getDimensionPixelSize(1, 0);
        a.recycle();
        a = context.obtainStyledAttributes(attrs, R.styleable.GroupCircleButton);
        setTextColor(a.getColorStateList(R.styleable.GroupCircleButton_android_textColor));
        setTextArray(a.getTextArray(R.styleable.GroupCircleButton_gb_textArray));
        setMarginToChild(a.getInteger(R.styleable.GroupCircleButton_gb_childMargin,default_margins));
        setCheckedColor(a.getColor(R.styleable.GroupCircleButton_gb_checkedColor, Color.GREEN));
        setUnCheckedColor(a.getColor(R.styleable.GroupCircleButton_gb_unCheckedColor, Color.WHITE));
        setStrokeColor(a.getColor(R.styleable.GroupCircleButton_gb_strokeColor, Color.GREEN));
        setStrokeWidth((int) a.getDimension(R.styleable.GroupCircleButton_gb_strokeWidth, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, getResources().getDisplayMetrics())));
        setTextSize(a.getDimension(R.styleable.GroupCircleButton_android_textSize, 0f));
        a.recycle();
        setOnCheckedChangeListener(this);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void initUI(Context context) {

        if (mTexts != null && mTexts.length != switchCount) {
            throw new IllegalArgumentException("The textArray's length must equal to the switchCount");
        }
        if (mParentWidth == 0)
            return;
        ColorDrawable colorDrawable = new ColorDrawable();
        LayoutParams mParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getCalWidth(), 1);
        Log.e("initUI: ", getCalWidth()+"");
        mParams.setMargins(marginToChild,0,0,0);
        for (int i = 0; i < switchCount; i++) {
            if (mRadioArrays == null)
                mRadioArrays = new SparseArray<RadioButton>();
            RadioButton mRadioButton = mRadioArrays.get(i, createRadioView());
            mRadioButton.setLayoutParams(mParams);
            mRadioButton.setButtonDrawable(mButtonDrawables != null ? mButtonDrawables.get(i, colorDrawable) : colorDrawable);
            if (Build.VERSION.SDK_INT >= 16) {
                mRadioButton.setBackground(getStateDrawable(i));
            } else {
                mRadioButton.setBackgroundDrawable(getStateDrawable(i));
            }
            mRadioButton.setText(mTexts[i]);
            if (mRadioButton.getId() < 0) {
                int id = getViewId();
                if (mSparseIds == null)
                    mSparseIds = new SparseIntArray();
                mSparseIds.put(i, id);
                mRadioButton.setId(id);
            } else {
                removeView(mRadioButton);
            }
            mRadioButton.setChecked(mCurrentPosition == i);
            addView(mRadioButton, i);
            mRadioArrays.put(i, mRadioButton);
            setHorizontalScrollBarEnabled(true);
        }
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

    private RadioButton createRadioView() {
        RadioButton mRadioButton = new RadioButton(getContext(), null, mRadioStyle > 0 ? mRadioStyle : android.R.attr.radioButtonStyle);
        if (mRadioStyle == 0) {
            mRadioButton.setGravity(Gravity.CENTER);
            mRadioButton.setEllipsize(TruncateAt.END);
        }
        if (mTextColor != null)
            mRadioButton.setTextColor(mTextColor);
        if (textSize > 0)
            mRadioButton.setTextSize(textSize);
        return mRadioButton;
    }

    @Deprecated
    public void initialize() {
        notifyDataSetChange();
    }

    public void notifyDataSetChange() {
        removeAllViews();
        switchCount = mTexts != null ? mTexts.length : switchCount;
        initUI(getContext());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isMeasure) {
            initUI(getContext());
            isMeasure = !isMeasure;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mParentWidth = widthMeasureSpec - EX;
        mParentHeight = mParentHeight == 0 ? heightMeasureSpec : mParentHeight;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (changeListener != null)
            changeListener.onChange(mSparseIds.indexOfValue(checkedId));
    }

    public void setCurrentPosition(int selectedPosition) {
        if (selectedPosition >= 0 && selectedPosition <= switchCount) {
            mCurrentPosition = selectedPosition;
        }
    }

    public void setCheckedPosition(int selectedPosition) {
        if (selectedPosition >= 0 && selectedPosition <= switchCount) {
            mCurrentPosition = selectedPosition;
            if (mSparseIds != null)
                check(mSparseIds.get(mSparseIds.keyAt(selectedPosition)));
        }
    }

    public void setTextColor(ColorStateList mTextColor) {
        this.mTextColor = mTextColor;
    }

    public void setTextArray(CharSequence[] mTexts) {
        this.mTexts = mTexts;
        setSwitchCount(mTexts.length);
    }

    public int getCount() {
        return switchCount;
    }

    public void setParentWidth(int mParentWidth) {
        this.mParentWidth = mParentWidth;
    }

    public void setParentHeight(int mParentHeight) {
        this.mParentHeight = mParentHeight;
    }

    public void setSwitchCount(int switchCount) {
        this.switchCount = switchCount < 2 ? DEFAULT_SWITCH_COUNT : switchCount;
        if (mButtonDrawables == null)
            mButtonDrawables = new SparseArray<Drawable>();
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

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setOnChangeListener(OnChangeListener eventListener) {
        this.changeListener = eventListener;
    }

    public void setMarginToChild(int marginToChild) {
        this.marginToChild = marginToChild;
    }

    public interface OnChangeListener {
        public void onChange(int position);
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

    private int getCalWidth(){
        int tmp=getWidth()/switchCount;
        tmp = tmp - marginToChild;
        return tmp;
    }
}