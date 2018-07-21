package com.github.myswitch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

public class MySwitch extends View implements Checkable{
    public MySwitch(Context context) {
        super(context);
    }

    public MySwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MySwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {

    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void toggle() {

    }
}
