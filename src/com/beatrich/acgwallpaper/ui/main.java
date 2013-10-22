package com.beatrich.acgwallpaper.ui;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class main extends Activity 
{
	private boolean m_bScaling = false;
	private VersionedScaleGestureDetector m_ScaleDetector = null;
	
	private TextView m_text = null;
	private View m_view = null;
	
//    @Override
//    public void onCreate(Bundle savedInstanceState) 
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        
//        m_ScaleDetector = VersionedScaleGestureDetector.newInstance(this, new SivScaleGestureListener() );
//        
//        m_text = (TextView)findViewById(R.id.text);
//        m_view = (View)findViewById(R.id.view);
//        m_view.setOnTouchListener(new OnTouchListener()
//        {
//			@Override
//			public boolean onTouch(View v, MotionEvent event)
//			{
//				m_ScaleDetector.onTouchEvent(event);
//				return true;
//			}
//		});
//    }
//    
    private class SivScaleGestureListener implements VersionedScaleGestureDetector.OnGestureListener
	{
    	private int m_xCenter = 0;
    	private int m_yCenter = 0;
		
		@Override
		public boolean onScale(float scaleFactor, float span)
		{
			if( span > 100 && m_bScaling )
			{
				m_text.setText(String.format("cX:%d, cY:%d, scale:%f", m_xCenter, m_yCenter, scaleFactor));
				m_text.setTextSize(scaleFactor*10);
			}
			return true;
		}

		@Override
		public boolean onScaleBegin(float dx, float dy)
		{
			if (!m_bScaling)
			{
				m_bScaling = true;
				m_xCenter = (int)dx;
				m_yCenter = (int)dy;
			}
			return true;
		}

		@Override
		public void onScaleEnd()
		{
			if (m_bScaling)
				m_bScaling = false;
		}
	}
	
}