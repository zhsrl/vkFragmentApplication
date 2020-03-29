package com.e.vknew;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class NoSwipablePager extends ViewPager {
    public NoSwipablePager(@NonNull Context context) {
        super(context);
    }

    public NoSwipablePager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // stop swipe
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // stop switching pages
        return false;
    }


}
