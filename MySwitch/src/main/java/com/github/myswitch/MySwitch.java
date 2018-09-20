package com.github.myswitch;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;

import static android.graphics.Path.Op.DIFFERENCE;

public class MySwitch extends View implements Checkable{
    private OnSwitchClickListener onSwitchClickListener;
    private OnSwitchChangeListener onSwitchChangeListener;

    public interface OnSwitchClickListener{
        boolean onSwitchClick(boolean isEnabled,View mySwitch);
    }
    public interface OnSwitchChangeListener{
        void onSwitchChange(boolean isChecked,View mySwitch);
    }

    public OnSwitchClickListener getOnSwitchClickListener() {
        return onSwitchClickListener;
    }
    public void setOnSwitchClickListener(OnSwitchClickListener onSwitchClickListener) {
        this.onSwitchClickListener = onSwitchClickListener;
    }

    public OnSwitchChangeListener getOnSwitchChangeListener() {
        return onSwitchChangeListener;
    }
    public void setOnSwitchChangeListener(OnSwitchChangeListener onSwitchChangeListener) {
        this.onSwitchChangeListener = onSwitchChangeListener;
    }

    /**********边框**********/
    private Paint borderPaint;
    private Path borderPath;
    private RectF borderRectF;
    //边框圆角
    private float borderRadius=-1;
    //边框宽度
    private int borderWidth=5;
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
    private float barRadius=-1;
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
    private int barBorderWidth=2;
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
    //画布绘制时view的颜色
    private int canvasColor;


    private float centerX;
    private float centerY;

    private boolean checked=true;


    //滑块阴影颜色宽度
    private int barShadowWidth=4;
    //滑块阴影颜色
    private int barShadowColor;
    //滑块滑块是否启用阴影
    private boolean useBarShadow=true;
    //是否启用动画
    private boolean useAnimation=true;
    //动画执行时间
    private int duration=230;
    //是否反向
    private boolean reverse=true;

    //是否可以点击
    private boolean enabled=true;


    private ArgbEvaluator argbEvaluator;


    private GestureDetector gestureDetector;
    private ValueAnimator valueAnimator;


    private boolean sizeChanged;

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
        argbEvaluator=new ArgbEvaluator();
        //边框
        checkBorderColor= Color.parseColor("#B014BD28");
        unCheckBorderColor= Color.parseColor("#FFD7DADD");


        //滑块
        checkBarColor= Color.WHITE;
        unCheckBarColor= Color.WHITE;

        //滑块边框
        barBorderColor= Color.parseColor("#eeeeee");
        barShadowColor=Color.parseColor("#eeeeee");
        //主体颜色
        checkColor= Color.parseColor("#B014BD28");
        unCheckColor=  Color.WHITE;

        /*滑块阴影*/
        barShadowColor=Color.parseColor("#BEC2C7");

        if(attrs!=null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MySwitch);
            /*--边框--*/
            borderRadius=typedArray.getDimension(R.styleable.MySwitch_borderRadius,borderRadius);
            borderWidth= (int) typedArray.getDimension(R.styleable.MySwitch_borderWidth,borderWidth);
            checkBorderColor=typedArray.getColor(R.styleable.MySwitch_checkBorderColor,checkBorderColor);
            unCheckBorderColor=typedArray.getColor(R.styleable.MySwitch_unCheckBorderColor,unCheckBorderColor);

            /*--滑块--*/
            barRadius=typedArray.getDimension(R.styleable.MySwitch_barRadius,barRadius);
            checkBarColor=typedArray.getColor(R.styleable.MySwitch_checkBarColor,checkBarColor);
            unCheckBarColor=typedArray.getColor(R.styleable.MySwitch_unCheckBarColor,unCheckBarColor);
            barShadowWidth= (int) typedArray.getDimension(R.styleable.MySwitch_barShadowWidth,barShadowWidth);
            barShadowColor=typedArray.getColor(R.styleable.MySwitch_barShadowColor,barShadowColor);
            useBarShadow=typedArray.getBoolean(R.styleable.MySwitch_useBarShadow,useBarShadow);

            /*--滑块边框--*/
            barBorderWidth= (int) typedArray.getDimension(R.styleable.MySwitch_barBorderWidth,barBorderWidth);
            barBorderColor=typedArray.getColor(R.styleable.MySwitch_barBorderColor,barBorderColor);

            /*--view--*/
            checkColor=typedArray.getColor(R.styleable.MySwitch_checkColor,checkColor);
            unCheckColor=typedArray.getColor(R.styleable.MySwitch_unCheckColor,unCheckColor);


            useAnimation=typedArray.getBoolean(R.styleable.MySwitch_useAnimation,useAnimation);
            duration=typedArray.getInt(R.styleable.MySwitch_duration,duration);
            reverse=typedArray.getBoolean(R.styleable.MySwitch_reverse,reverse);
            enabled=typedArray.getBoolean(R.styleable.MySwitch_enabled,enabled);
            checked=typedArray.getBoolean(R.styleable.MySwitch_checked,checked);

            typedArray.recycle();
        }


        if(checked){
            canvasBorderColor=checkBorderColor;
            canvasColor=checkColor;
            canvasBarColor=checkBarColor;
        }else{
            canvasBorderColor=unCheckBorderColor;
            canvasColor=unCheckColor;
            canvasBarColor=unCheckBarColor;
        }

        setLayerType(LAYER_TYPE_SOFTWARE,null);
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

        /*边框*/
        borderPath=new Path();

        borderPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.FILL);


        /*滑块*/
        barPath=new Path();

        barPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        barPaint.setStyle(Paint.Style.FILL);


        /*滑块边框*/
        barBorderPath=new Path();

        barBorderPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        barBorderPaint.setStyle(Paint.Style.STROKE);

        /*View*/
        checkColorPath=new Path();

        checkColorPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        checkColorPaint.setStyle(Paint.Style.FILL);

        initRectF();
        initPath();
        sizeChanged=true;
    }

    private void initGestureDetector() {
        gestureDetector=new GestureDetector(getContext(),new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if(onSwitchClickListener!=null&&onSwitchClickListener.onSwitchClick(isEnabled(),MySwitch.this)==true){

                }else{
                    if(isEnabled()){
                        toggle();
                    }
                }
                return true;
            }
        });
    }

    /***
     * set方法需要initRect，但是执行动画的时候只需要initPath，不需要initRect
     */
    private void initRectF(){
        /*滑块*/
        float barLeft;
        float barTop;
        float barRight;
        float barBottom;
        if(isChecked()){
            //处于右边状态(true)的滑块位置
            barLeft=getWidth()-getHeight()+borderWidth;
            barTop=borderWidth;
            barRight=getWidth()-borderWidth;
            barBottom=getHeight()-borderWidth;
        }else{
            barLeft=borderWidth;
            barTop=borderWidth;
            barRight=getHeight()-borderWidth;
            barBottom=getHeight()-borderWidth;
        }
        barRectF=new RectF(barLeft,barTop,barRight,barBottom);


        /*边框*/

        borderRectF=new RectF(0,0,getWidth(),getHeight());

    }
    private void initPath(){

        if(borderRadius==-1&&barRadius==-1){
            borderRadius=getHeight()*1f/2f;
            barRadius=borderRadius*(getHeight()-borderWidth*2f)/(getHeight());
        }else if(borderRadius==-1&&barRadius>=0){
            borderRadius=barRadius*(getHeight())/(getHeight()-borderWidth*2f);
        }else if(borderRadius>=0&&barRadius==-1){
            barRadius=borderRadius*(getHeight()-borderWidth*2f)/(getHeight());
        }

        /*滑块边框*/
        barBorderPaint.setStrokeWidth(barBorderWidth);
        barBorderPaint.setColor(barBorderColor);

        /*主体*/
        checkColorPaint.setColor(checkColor);


        /*滑块*/
        barPaint.setColor(checkBarColor);

        if(useBarShadow){
            barPaint.setShadowLayer(barShadowWidth,0,0,barShadowColor);
        }else{
            barPaint.setShadowLayer(0,0,0,Color.TRANSPARENT);
        }


        /*边框*/
        borderPaint.setColor(checkBorderColor);

        if(!borderPath.isEmpty()){
            borderPath.reset();
        }
        borderPath.addRoundRect(borderRectF,borderRadius,borderRadius, Path.Direction.CW);

        /*view主体*/
        if(!checkColorPath.isEmpty()){
            checkColorPath.reset();
        }
        checkColorPath.addRoundRect(new RectF(borderWidth,borderWidth,getWidth()-borderWidth,getHeight()-borderWidth),barRadius,barRadius, Path.Direction.CW);

        /*滑块*/
        if(!barPath.isEmpty()){
            barPath.reset();
        }
        barPath.addRoundRect(barRectF,barRadius,barRadius, Path.Direction.CW);

        /*滑块边框*/
        if(!barBorderPath.isEmpty()){
            barBorderPath.reset();
        }
        barBorderPath.addRoundRect(barRectF,barRadius,barRadius, Path.Direction.CW);

        borderPath.op(checkColorPath,DIFFERENCE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(reverse){
            canvas.rotate(180,getWidth()/2,getHeight()/2);
        }

        /*border*/
        //设置选中状态颜色
        borderPaint.setColor(canvasBorderColor);
        canvas.drawPath(borderPath,borderPaint);


        /*view主体背景*/
        //如果选中状态，主体颜色和边框颜色一样，则不绘制主体颜色，防止颜色重叠加深
        checkColorPaint.setColor(canvasColor);
        canvas.drawPath(checkColorPath,checkColorPaint);

        /*滑块*/
        barPaint.setColor(canvasBarColor);
        canvas.drawPath(barPath,barPaint);

        /*滑块边框*/
        canvas.drawPath(barBorderPath,barBorderPaint);

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

    public void toggle(final boolean useAnimation) {
        checked=!checked;
        if(onSwitchChangeListener!=null){
            onSwitchChangeListener.onSwitchChange(checked,MySwitch.this);
        }
        post(new Runnable() {
            @Override
            public void run() {
                if(useAnimation){
                    //更新动画
                    valueAnimator=ValueAnimator.ofFloat(0,1);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            if(checked){
                                canvasColor= (int) argbEvaluator.evaluate(value,unCheckColor,checkColor);
                                canvasBarColor= (int) argbEvaluator.evaluate(value,unCheckBarColor,checkBarColor);
                                canvasBorderColor= (int) argbEvaluator.evaluate(value,unCheckBorderColor,checkBorderColor);


                                float barHeight=getHeight()-borderWidth * 2f;
                                float moveLength = getWidth() -barHeight-borderWidth*2f;
                                float left=borderWidth+moveLength*value;
                                float right=left+barHeight;
                                float top=borderWidth;
                                float bottom=getHeight()-borderWidth;
                                barRectF=new RectF(left,top,right,bottom);

                            }else{
                                canvasColor= (int) argbEvaluator.evaluate(value,checkColor,unCheckColor);
                                canvasBarColor= (int) argbEvaluator.evaluate(value,checkBarColor,unCheckBarColor);
                                canvasBorderColor= (int) argbEvaluator.evaluate(value,checkBorderColor,unCheckBorderColor);

                                float barHeight=getHeight()-borderWidth * 2f;
                                float moveLength = getWidth() -barHeight-borderWidth*2f;
                                float right=getWidth()-borderWidth-moveLength*value;
                                float left=right-barHeight;
                                float top=borderWidth;
                                float bottom=getHeight()-borderWidth;
                                barRectF=new RectF(left,top,right,bottom);
                            }
                            initPath();
                            invalidate();
                        }
                    });
                    valueAnimator.setDuration(duration);
                    valueAnimator.start();

                }else{
                    if(checked){
                        canvasColor= checkColor;
                        canvasBarColor=checkBarColor;
                        canvasBorderColor= checkBorderColor;
                    }else{
                        canvasColor= unCheckColor;
                        canvasBarColor= unCheckBarColor;
                        canvasBorderColor= unCheckBorderColor;
                    }
                    initRectF();
                    initPath();
                    invalidate();
                }
            }
        });
    }


    public float getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
        postRefresh();
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        postRefresh();
    }

    public int getCheckBorderColor() {
        return checkBorderColor;
    }

    public void setCheckBorderColor(int checkBorderColor) {
        this.checkBorderColor = checkBorderColor;
        postRefresh();
    }

    public int getUnCheckBorderColor() {
        return unCheckBorderColor;
    }

    public void setUnCheckBorderColor(int unCheckBorderColor) {
        this.unCheckBorderColor = unCheckBorderColor;
        postRefresh();
    }

    public float getBarRadius() {
        return barRadius;
    }

    public void setBarRadius(float barRadius) {
        this.barRadius = barRadius;
        postRefresh();
    }

    public int getUnCheckBarColor() {
        return unCheckBarColor;
    }

    public void setUnCheckBarColor(int unCheckBarColor) {
        this.unCheckBarColor = unCheckBarColor;
        postRefresh();
    }

    public int getCheckBarColor() {
        return checkBarColor;
    }

    public void setCheckBarColor(int checkBarColor) {
        this.checkBarColor = checkBarColor;
        postRefresh();
    }

    public int getBarBorderWidth() {
        return barBorderWidth;
    }

    public void setBarBorderWidth(int barBorderWidth) {
        this.barBorderWidth = barBorderWidth;
        postRefresh();
    }

    public int getBarBorderColor() {
        return barBorderColor;
    }

    public void setBarBorderColor(int barBorderColor) {
        this.barBorderColor = barBorderColor;
        postRefresh();
    }

    public int getUnCheckColor() {
        return unCheckColor;
    }

    public void setUnCheckColor(int unCheckColor) {
        this.unCheckColor = unCheckColor;
        postRefresh();
    }

    public int getCheckColor() {
        return checkColor;
    }

    public void setCheckColor(int checkColor) {
        this.checkColor = checkColor;
        postRefresh();
    }

    public int getBarShadowWidth() {
        return barShadowWidth;
    }

    public void setBarShadowWidth(int barShadowWidth) {
        this.barShadowWidth = barShadowWidth;
        postRefresh();
    }

    public int getBarShadowColor() {
        return barShadowColor;
    }

    public void setBarShadowColor(int barShadowColor) {
        this.barShadowColor = barShadowColor;
        postRefresh();
    }

    public boolean isUseBarShadow() {
        return useBarShadow;
    }

    public void setUseBarShadow(boolean useBarShadow) {
        this.useBarShadow = useBarShadow;
        postRefresh();
    }

    public boolean isUseAnimation() {
        return useAnimation;
    }

    public void setUseAnimation(boolean useAnimation) {
        this.useAnimation = useAnimation;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
        invalidate();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private void postRefresh(){
        post(new Runnable() {
            @Override
            public void run() {
                initRectF();
                initPath();
                invalidate();
            }
        });
    }
}
