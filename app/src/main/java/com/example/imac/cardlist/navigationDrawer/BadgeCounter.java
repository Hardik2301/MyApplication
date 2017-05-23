package com.example.imac.cardlist.navigationDrawer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.imac.cardlist.R;

/**
 * Created by imac on 22/05/17.
 */
public class BadgeCounter {

    public static int mbudges;

    public enum BadgeColor {
        GREY(Color.parseColor("#9E9E9E")),
        BLUE_GREY(Color.parseColor("#607D8B")),
        RED(Color.parseColor("#f44336")),
        BLUE(Color.parseColor("#2196F3")),
        CYAN(Color.parseColor("#00BCD4")),
        TEAL(Color.parseColor("#009688")),
        GREEN(Color.parseColor("#4CAF50")),
        YELLOW(Color.parseColor("#FFEB3B")),
        ORANGE(Color.parseColor("#FF9800")),
        DEEP_ORANGE(Color.parseColor("#FF5722")),
        PURPLE(Color.parseColor("#9C27B0")),
        LIGHT_BLUE(Color.parseColor("#03A9F4")),
        LIGHT_GREEN(Color.parseColor("#8BC34A")),
        BLACK(Color.parseColor("#000000"));

        private int color;

        BadgeColor(int color) {
            this.color = color;
        }

        public int getColor() {
            return color;
        }
    }

    private static Drawable getDrawable(Activity activity, int icon) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return activity.getResources().getDrawable(icon, activity.getTheme());
        } else {
            return activity.getResources().getDrawable(icon);
        }
    }

    public static void update(final Activity activity, final MenuItem menu, int icon, BadgeColor color, int counter) {
        update(activity, menu, getDrawable(activity,icon), color, String.valueOf(counter));
    }

    public static void update(final MenuItem menu, Drawable icon, int counter) {
        if (counter == Integer.MIN_VALUE) {
            update(null, menu, icon, null, null);
        } else {
            update(null, menu, icon, null, String.valueOf(counter));
        }
    }

    public static void update(final MenuItem menu, Drawable icon, String counter) {
        if (counter == null) {
            update(null, menu, icon, null, null);
        } else {
            update(null, menu, icon, null, String.valueOf(counter));
        }
    }

    /**
     * update the given menu item with icon, badgeCount and style
     *
     * @param activity use to bind onOptionsItemSelected
     * @param menu    class menuItem
     * @param icon    icon action menu
     * @param color   background badge
     * @param counter counter
     */
    public static void update(final Activity activity, final MenuItem menu, Drawable icon, BadgeColor color, String counter) {
        if (menu == null) return;

        if(counter != null) {
            mbudges = Integer.parseInt(counter);
        }
        Log.e("Menu edges: ", mbudges+"");
        GradientDrawable mDrawable = new GradientDrawable();

        RelativeLayout mContainer;
        TextView mBadgeCount;
        ImageView mIconBadge;

        if (color == null) {
            mContainer = (RelativeLayout) menu.getActionView();
        } else {
            mContainer = (RelativeLayout) menu.setActionView(R.layout.badge_counter).getActionView();
        }

        mBadgeCount = (TextView) mContainer.findViewById(R.id.count_badge);
        mIconBadge = (ImageView) mContainer.findViewById(R.id.icon_badge);

        //Display icon in ImageView
        if (icon != null) {
            mIconBadge.setImageDrawable(icon);
        }

        if (color != null) {
            // Set Color
            mDrawable.setCornerRadius(5);
            mDrawable.setColor(color.getColor());
            mBadgeCount.setPadding(2, 0, 2, 1);
            mBadgeCount.setBackground(mDrawable);
        }

        //Bind onOptionsItemSelected to the activity
        if (activity != null) {
            mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onOptionsItemSelected(menu);
                }
            });
        }

        //Manage min value
        if (counter == null) {
            mBadgeCount.setVisibility(View.GONE);
        } else {
            mBadgeCount.setVisibility(View.VISIBLE);
            mBadgeCount.setText(String.valueOf(mbudges));
        }

        menu.setVisible(true);
    }

    public static void addBudge(Activity activity,MenuItem menu,BadgeColor color, int icon,int counter){
        update(activity, menu, getDrawable(activity,icon), color, counter+"");
    }

    public static void hide(MenuItem menu) {
        menu.setVisible(false);
    }
}
