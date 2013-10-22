package com.beatrich.acgwallpaper.ui;

import android.content.Context;
import android.os.Build;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public abstract class VersionedScaleGestureDetector 
{
    OnGestureListener mListener;
    
    public abstract boolean onTouchEvent(MotionEvent ev);
    public interface OnGestureListener 
    {
        public boolean onScaleBegin( float dx, float dy );
        public boolean onScale(float scaleFactor, float span );
        public void onScaleEnd();
    }
    
    public static VersionedScaleGestureDetector newInstance(
    		Context context,
            OnGestureListener listener) 
    {
    	final int sdkVersion = Integer.parseInt( Build.VERSION.SDK );
    	
    	VersionedScaleGestureDetector detector = null;
        if (sdkVersion < Build.VERSION_CODES.FROYO) 
        {
            detector = new EclairDetector(context);
        } 
        else 
        {
            detector = new FroyoDetector(context);
        } 
        
        detector.mListener = listener;   
        return detector;
    }

    
    static final float SCALE_MIN_SPAN = 1f;
}


class FroyoDetector extends VersionedScaleGestureDetector
{
    public FroyoDetector(Context context) 
    {
        mDetector = new ScaleGestureDetector(
        		context,
        		new ScaleGestureDetector.SimpleOnScaleGestureListener() 
            {
                @Override 
                public boolean onScale(ScaleGestureDetector detector) 
                {
                	return mListener.onScale(detector.getScaleFactor(), detector.getCurrentSpan() );
                }
                
                @Override 
                public boolean onScaleBegin (ScaleGestureDetector detector)
                {
                	return mListener.onScaleBegin(detector.getFocusX(), detector.getFocusY());
                }
                
                @Override 
                public void onScaleEnd (ScaleGestureDetector detector)
                {
                	mListener.onScaleEnd();
                }
            }
        );
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) 
    {
    	int act = ev.getAction() &MotionEvent.ACTION_MASK;
    	if( act == MotionEvent.ACTION_UP || act == MotionEvent.ACTION_POINTER_UP )
    	{
    		mListener.onScaleEnd();
    	}
    	return mDetector.onTouchEvent(ev);
    }
    
    private ScaleGestureDetector mDetector;
}



class EclairDetector extends VersionedScaleGestureDetector
{
	public EclairDetector(Context context)
	{
		
	}
	
    @Override
    public boolean onTouchEvent(MotionEvent event) 
    {
    	boolean bHandled = false;
        // Handle touch events here...
        switch (event.getAction() & MotionEvent.ACTION_MASK) 
        {
           case MotionEvent.ACTION_DOWN:
              mode = DRAG;
              bHandled = true;
              break;
              
           case MotionEvent.ACTION_UP:
           case MotionEvent.ACTION_POINTER_UP:
              if( mode == ZOOM )
              {
            	  mListener.onScaleEnd();
            	  bHandled = true;
              }
              
              mode = NONE;
              break;
              
           case MotionEvent.ACTION_POINTER_DOWN:
              oldDist = spacing(event);
              if( oldDist > SCALE_MIN_SPAN )
              {
	              mode = ZOOM;
	              float dx = ( event.getX(0) + event.getX(1) ) / 2;
	          	  float dy = ( event.getY(0) + event.getY(1) ) / 2;
	              mListener.onScaleBegin( dx, dy );
	              bHandled = true;
              }
              break;
              
           case MotionEvent.ACTION_MOVE:
              if (mode == ZOOM) 
              {
                 float newDist = spacing(event);
                 if( newDist > SCALE_MIN_SPAN ) 
                 {
                     float scaleFactor = newDist / oldDist;
                     oldDist = newDist;
                     mListener.onScale( scaleFactor, newDist );
                     bHandled = true;
                 }
              }
              break;
        }

        return bHandled; 
    }
    
    private float spacing(MotionEvent event) 
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;
    
    float oldDist = 1.0f;
} 