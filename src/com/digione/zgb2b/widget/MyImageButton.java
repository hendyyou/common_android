package com.digione.zgb2b.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class MyImageButton extends ImageButton {

	private Paint mPaint;
	private int textSize=10;
	private int r=10;//圆的半径
	private int num;
	private boolean flag=false;
	public MyImageButton(Context context) {
		super(context);
		init();
	}
	public MyImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
		mPaint=new Paint();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if(flag){
			mPaint=new Paint();
			mPaint.setAntiAlias(true);
			mPaint.setColor(Color.RED);
			canvas.drawCircle(getWidth()-r-1, r+1, r, mPaint);
			mPaint.setColor(Color.WHITE);
			mPaint.setStrokeWidth(3);
			mPaint.setStyle(Paint.Style.STROKE); // 设置为空心的
			mPaint.setAntiAlias(true); // 设置去锯齿
			canvas.drawCircle(getWidth()-r-1, r+1, r+1, mPaint);
			mPaint.setColor(Color.WHITE);
			mPaint.setTypeface(Typeface.SERIF);
			float[] widths=new float[String.valueOf(num).length()];
			mPaint.getTextWidths(String.valueOf(num), widths);
			float length=0;
			float height=mPaint.getTextSize()/2;
			for(float width : widths){
				length=width+length;
			}
			length = length / 2;
	        canvas.drawText(String.valueOf(num), getWidth() - r - length, r + height, mPaint);
		}
		super.onDraw(canvas);
	}
	
	public void setShopCart(int quanity){
		num=quanity;
		if(num>0){
			flag=true;
		}else{
			flag=false;
		}
		invalidate();
	}

}
