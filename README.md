# MySwitchView


![github](https://github.com/zhongruiAndroid/MySwitchView/blob/master/app/src/main/res/drawable/switch.gif "github")  
  
  
| 属性               | 类型      | 说明                                                     |
|--------------------|-----------|----------------------------------------------------------|
|        **边框**        |           |                                                          |
| borderRadius       | dimension | 边框圆角,默认值:view高度/2                               |
| borderWidth        | dimension | 边框宽度,默认值:5px                                      |
| checkBorderColor   | color     | true:边框颜色,默认值:#B014BD28                           |
| unCheckBorderColor | color     | false:边框颜色,默认值:#FFD7DADD                          |
|        **滑块**        |           |                                                          |
| barRadius          | dimension | 滑块圆角,默认值:(viwe高度-borderWidth*2)/2               |
| checkBarColor      | color     | true:滑块颜色,默认值:white                               |
| unCheckBarColor    | color     | false:滑块颜色,默认值:white                              |
| barShadowWidth     | dimension | 滑块阴影宽度,默认值:4px                                  |
| barShadowColor     | color     | 滑块阴影颜色,默认值:#eeeeee                              |
| useBarShadow       | boolean   | 是否设置阴影,默认值:true                                 |
|      **滑块边框**      |           |                                                          |
| barBorderWidth     | dimension | 滑块边框宽度,默认值:2px                                  |
| barBorderColor     | color     | 滑块边框颜色,默认值:#eeeeee                              |
|        **其他**        |           |                                                          |
| checkColor         | color     | true:view主体颜色,默认值:#B014BD28                       |
| unCheckColor       | color     | false:view主体颜色,默认值:white                          |
| useAnimation       | boolean   | 是否设置动画,默认值:true                                 |
| duration           | integer   | 动画执行时间,默认值:230毫秒                              |
| reverse            | boolean   | View是否反向,true:右开左关,false:左开右关,默认值:false   |
| enabled            | boolean   | 是否启用点击和滑动,默认值:true(为false时覆盖canMove属性) |
| canMove            | boolean   | 是否可以滑动滑块,默认值:true                             |
| checked            | boolean   | 默认值:true                                              |  


```xml
<com.github.MySwitch
	 android:layout_width="75dp"
	 android:layout_height="40dp"
	 app:borderRadius="20dp"
	 app:borderWidth="5px"
	 app:checkBorderColor="#B014BD28"
	 app:unCheckBorderColor="#FFD7DADD"
	 app:barRadius="20dp"
	 app:checkBarColor="@android:color/white"
	 app:unCheckBarColor="@android:color/white"
	 app:barShadowWidth="4px"
	 app:barShadowColor="#eeeeee"
	 app:useBarShadow="true"
	 app:barBorderWidth="2px"
	 app:barBorderColor="#eeeeee"
	 app:checkColor="#B014BD28"
	 app:unCheckColor="@android:color/white"
	 app:useAnimation="true"
	 app:duration="230"
	 app:reverse="false"
	 app:enabled="true"
	 app:canMove="true"
	 app:checked="true"
	 />
```
##### 代码设置属性
```java
MySwitch ms=findViewById(R.id.ms);

ms.setBorderRadius();
ms.setBorderWidth();
ms.setCheckBorderColor();
ms.setUnCheckBorderColor();
ms.setBarRadius();
ms.setCheckBarColor();
ms.setUnCheckBarColor();
ms.setBarShadowWidth();
ms.setBarShadowColor();
ms.setUseBarShadow();
ms.setBarBorderWidth();
ms.setBarBorderColor();
ms.setCheckColor();
ms.setUnCheckColor();
ms.setUseAnimation();
ms.setDuration();
ms.setReverse();
ms.setEnabled();
ms.setCanMove();
ms.setChecked();
```

#### 状态改变事件和点击事件
```java
/*状态改变事件*/
ms.setOnSwitchChangeListener(new MySwitch.OnSwitchChangeListener() {
   @Override
    public void onSwitchChange(boolean isChecked, MySwitch mySwitch) {
        
    }
});

/*点击事件*/
ms.setOnSwitchClickListener(new MySwitch.OnSwitchClickListener() {
  @Override
  public boolean onSwitchClick(boolean isEnabled,boolean beforeClickChecked) {
       //isEnabled:           view是否启用
       //beforeClickChecked:  view点击之前的状态
       //return true          拦截点击,不改变view状态
       //return false         不拦截点击,改变view状态
       
       //适用场景：            点击view--->网络请求--->根据网络返回结果改变viwe状态--->设置返回值(true或false)
       return false;
   }
});
```
