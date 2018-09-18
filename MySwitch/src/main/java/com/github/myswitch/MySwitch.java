package com.github.myswitch;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Checkable;

public class MySwitch extends View implements Checkable{
    public interface OnSwitchClickListener{
        boolean onSwitchClick();
    }
    private OnSwitchClickListener onSwitchClickListener;
    public OnSwitchClickListener getOnSwitchClickListener() {
        return onSwitchClickListener;
    }
    public void setOnSwitchClickListener(OnSwitchClickListener onSwitchClickListener) {
        this.onSwitchClickListener = onSwitchClickListener;
    }

    /**********边框**********/
    private Paint borderPaint;
    private Path borderPath;
    private RectF borderRectF;
    //边框圆角
    private int borderRadius;
    //边框宽度
    private int borderWidth=20;
    //选中状态view边框颜色
    private int checkBorderColor;
    //不选中状态view边框颜色
    private int unCheckBorderColor;

    //画布绘制时的颜色
    private int canvasBorderColor;


    /**********滑块**********/
    private Paint barPaint;
    private Path barPath;
    private RectF barRectF;
    //滑块圆角
    private int barRadius;
    //不选中状态滑块颜色
    private int unCheckBarColor;
    //选中状态滑块颜色
    private int checkBarColor;
    //画布绘制时的颜色
    private int canvasBarColor;


    /**********滑块边框**********/
    private Paint barBorderPaint;
    private Path barBorderPath;
    //滑块边框滑块边框宽度
    private int barBorderWidth;
    //选中状态滑块边框颜色
    private int barBorderColor;


    /**********主体背景**********/
    private Paint checkColorPaint;
    private Path checkColorPath;
    private RectF checkColorRectF;
    //不选中状态view颜色
    private int unCheckColor;
    //选中状态view颜色
    private int checkColor;
    //画布绘制时的颜色
    private int canvasColor;


    private float centerX;
    private float centerY;

    private boolean checked=true;


    //滑块阴影颜色宽度
    private int barShadowWidth;
    //滑块阴影颜色
    private int barShadowColor;
    //滑块滑块是否启用阴影
    private int useBarShadow;
    //是否启用动画
    private boolean useAnimation=true;
    //动画执行时间
    private int duration=300;
    //是否反向
    private int reverse;



    private GestureDetector gestureDetector;
    private ValueAnimator valueAnimator;

    public MySwitch(Context context) {
        super(context);
        initAttr(null);
    }

    public MySwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
    }

    public MySwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        //边框
        checkBorderColor= Color.parseColor("#B014BD28");
        unCheckBorderColor= Color.parseColor("#FF4081");


        //滑块
        checkBarColor= Color.WHITE;

        //主体颜色
        checkColor= Color.parseColor("#B014BD28");
        unCheckColor= Color.WHITE;

        if(attrs==null){
            return;
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initGestureDetector();
        centerX=w/2;
        centerY=h/2;

        if(w<h){
            borderRadius=w/2;
            barRadius=(w-borderWidth)/2;
        }else{
            borderRadius=h/2;
            barRadius=(h-borderWidth)/2;
        }



        //边框
        borderPath=new Path();

        borderPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(checkBorderColor);

        //滑块
        barPath=new Path();

        barPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        barPaint.setStyle(Paint.Style.FILL);
        barPaint.setColor(checkBarColor);


        //主体
        checkColorPath=new Path();

        checkColorPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        checkColorPaint.setStyle(Paint.Style.FILL);
        checkColorPaint.setColor(checkColor);

        //滑块边框
        barBorderPath=new Path();

        barBorderPaint=new Paint(Paint.ANTI_ALIAS_FLAG);



        initPath();
    }

    private void initGestureDetector() {
        gestureDetector=new GestureDetector(getContext(),new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if(onSwitchClickListener!=null&&onSwitchClickListener.onSwitchClick()==true){

                }else{
                    if(isEnabled()){
                        toggle();
                    }
                }
                return true;
            }
        });
    }

    private void initPath(){
        if(!borderPath.isEmpty()){
            borderPath.reset();
        }
        borderPath.addRoundRect(new RectF(0,0,getWidth(),getHeight()),borderRadius,borderRadius, Path.Direction.CW);

        if(!checkColorPath.isEmpty()){
            checkColorPath.reset();
        }
        checkColorPath.addRoundRect(new RectF(borderWidth,borderWidth,getWidth()-borderWidth,getHeight()-borderWidth),barRadius,barRadius, Path.Direction.CW);



        if(!barPath.isEmpty()){
            barPath.reset();
        }
        if(isChecked()){
            //处于右边状态(true)的滑块位置
            float barLeft=getWidth()-getHeight()+borderWidth;
            float barTop=borderWidth;
            float barRight=getWidth()-borderWidth;
            float barBottom=getHeight()-borderWidth;

            barPath.addRoundRect(new RectF(barLeft,barTop,barRight,barBottom),barRadius,barRadius, Path.Direction.CW);
        }else{
            float barLeft=borderWidth;
            float barTop=borderWidth;
            float barRight=getHeight()-borderWidth;
            float barBottom=getHeight()-borderWidth;

            barPath.addRoundRect(new RectF(barLeft,barTop,barRight,barBottom),barRadius,barRadius, Path.Direction.CW);
        }




    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("==","===onDraw");
        //设置选中状态颜色
        if(isChecked()){
        }else{

        }

        //border
        canvas.drawPath(borderPath,borderPaint);

        //view主体背景
        //如果选中状态，主体颜色和边框颜色一样，则不绘制主体颜色，防止颜色重叠加深
        if(checkColor!=checkBorderColor){
            canvas.drawPath(checkColorPath,checkColorPaint);
        }
        //滑块
        canvas.drawPath(barPath,barPaint);

    }

    @Override
    public void setChecked(boolean checked) {
        if(checked!=isChecked()){
            toggle();
        }
    }
    @Override
    public boolean isChecked() {
        return checked;
    }
    @Override
    public void toggle() {
        toggle(this.useAnimation);
    }

    public void toggle(boolean useAnimation) {
        checked=!checked;

        if(useAnimation){
            //更新动画
            valueAnimator=ValueAnimator.ofFloat(0,1);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    invalidate();
                }
            });
            valueAnimator.setDuration(duration);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.start();

        }else{
            if(checked){
                canvasBorderColor=checkBorderColor;
            }else{
                canvasBorderColor=unCheckBorderColor;
            }
            invalidate();
        }
    }

}
