package com.example.imac.cardlist.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by imac on 3/9/17.
 */

public class Screensize {

    private static int width;

    public static int getCardItemWidth(Context mContext){
        DisplayMetrics metrics=mContext.getResources().getDisplayMetrics();
        int tmpwidth=metrics.widthPixels;
        width=(tmpwidth*48)/100;
        Log.e("getCardItemWidth: ", "Total width- "+metrics.widthPixels+",card width- "+width);
        return width;
    }

    public static int getCardItemHeight(){
        int height=(width*80)/100;
        return height;
    }
}
