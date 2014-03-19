package com.digione.zgb2b.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class MyGallery extends Gallery{

	private int verticalMinDistance = 20;  
	private int minVelocity         = 0;  
	public MyGallery(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public MyGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public MyGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		if(isScrollingLeft(e1,e2) && Math.abs(velocityX) > minVelocity ){
			super.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
		}else if(!isScrollingLeft(e1,e2) && Math.abs(velocityX) > minVelocity){
			super.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
		}
		return false;
	}
	
	private boolean isScrollingLeft(MotionEvent e1,MotionEvent e2){
		return (e2.getX()-e1.getX())>verticalMinDistance;
	}
	

	
}
