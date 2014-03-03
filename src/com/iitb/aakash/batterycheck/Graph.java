package com.iitb.aakash.batterycheck;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iitb.aakash.batterycheck.SimpleGestureFilter.SimpleGestureListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class Graph extends Activity implements SimpleGestureListener {

	TextView txt_info, txt_logs, txt_graph, txtTitle;
	private SimpleGestureFilter detector;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph); 
		detector = new SimpleGestureFilter(this, this);
		txt_info = (TextView) findViewById(R.id.txtInfo_inactive);
		txt_logs = (TextView) findViewById(R.id.txtLogs_inactive);
		txt_graph = (TextView) findViewById(R.id.txtGraph_active);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		
		Typeface font = Typeface.createFromAsset(getAssets(), "JosefinSlab-Light.ttf");
		Typeface font_bold = Typeface.createFromAsset(getAssets(), "JosefinSlab-SemiBold.ttf");
		Typeface font_normal = Typeface.createFromAsset(getAssets(), "JosefinSlab-Regular.ttf");
		txt_info.setTypeface(font);
		txt_graph.setTypeface(font_normal);
		txt_logs.setTypeface(font);
		txtTitle.setTypeface(font_bold);
		
		txt_info.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setAlpha((float) 0.2);
				Intent infoactivity = new Intent(Graph.this, BatteryCheck.class);
				startActivity(infoactivity);
				overridePendingTransition(R.anim.anim_left_to_right,
						R.anim.anim_right_to_left);
				finish();
			}
		});

		txt_logs.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setAlpha((float) 0.2);
				Intent infoactivity = new Intent(Graph.this, Logs.class);
				startActivity(infoactivity);
				overridePendingTransition(R.anim.anim_left_to_right,
						R.anim.anim_right_to_left);
				finish();
			}
		});

		GraphViewSeries exampleSeries = new GraphViewSeries(
				new GraphViewData[] { new GraphViewData(1, 2.0d),
						new GraphViewData(2, 1.5d), new GraphViewData(3, 2.5d),
						new GraphViewData(4, 1.0d) });

		GraphView graphView = new LineGraphView(this // context
				, "" // heading
		);
		graphView.addSeries(exampleSeries); // data
		graphView.setViewPort(1, 5);
		graphView.setScrollable(false);
		// optional - activate scaling / zooming
		graphView.setScalable(false);

		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		layout.addView(graphView);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {
		// Call onTouchEvent of SimpleGestureFilter class
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	@Override
	public void onSwipe(int direction) {
		
		switch (direction) {

		case SimpleGestureFilter.SWIPE_RIGHT:
			System.out.println("RIGHT");
			Intent logsactivity = new Intent(Graph.this, Logs.class);
			startActivity(logsactivity);
			overridePendingTransition(R.anim.anim_left_to_right,
					R.anim.anim_right_to_left);
			finish();
			break;
		case SimpleGestureFilter.SWIPE_LEFT:
			System.out.println("LEFT");
			
			break;
		
		}
	}

	@Override
	public void onDoubleTap() {
		//Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
	}
}