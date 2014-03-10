package com.iitb.aakash.batterycheck;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iitb.aakash.batterycheck.SimpleGestureFilter.SimpleGestureListener;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

public class Graph extends Activity implements SimpleGestureListener {

	TextView txt_info, txt_logs, txt_graph, txtTitle;
	private SimpleGestureFilter detector;
	LinearLayout layout_logs, layout_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		detector = new SimpleGestureFilter(this, this);
		txt_info = (TextView) findViewById(R.id.txtInfo_inactive);
		txt_logs = (TextView) findViewById(R.id.txtLogs_inactive);
		txt_graph = (TextView) findViewById(R.id.txtGraph_active);
		txtTitle = (TextView) findViewById(R.id.txtTitle);

		layout_info = (LinearLayout) findViewById(R.id.layoutInfo_inactive);
		layout_logs = (LinearLayout) findViewById(R.id.layoutLogs_inactive);

		Typeface font = Typeface.createFromAsset(getAssets(),
				"JosefinSlab-Light.ttf");
		Typeface font_bold = Typeface.createFromAsset(getAssets(),
				"JosefinSlab-SemiBold.ttf");
		Typeface font_normal = Typeface.createFromAsset(getAssets(),
				"JosefinSlab-Regular.ttf");
		txt_info.setTypeface(font);
		txt_graph.setTypeface(font_normal);
		txt_logs.setTypeface(font);
		txtTitle.setTypeface(font_bold);

		layout_info.setOnClickListener(new OnClickListener() {

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

		layout_logs.setOnClickListener(new OnClickListener() {

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

		/*
		 * // GRAPH DATA int num = 150; GraphViewData[] data = new
		 * GraphViewData[num]; double v = 0; for (int i = 0; i < num; i++) { v
		 * += 0.2; data[i] = new GraphViewData(i, Math.sin(v)); }
		 * GraphViewSeries seriesSin = new GraphViewSeries("Sine", new
		 * GraphViewSeriesStyle(Color.BLUE, 3), data);
		 * 
		 * // cos curve data = new GraphViewData[num]; v = 0; for (int i = 0; i
		 * < num; i++) { v += 0.2; data[i] = new GraphViewData(i, Math.cos(v));
		 * } GraphViewSeries seriesCos = new GraphViewSeries("Cosine", new
		 * GraphViewSeriesStyle(Color.RED, 3), data);
		 * 
		 * GraphView graphView = new LineGraphView(this // context , "" //
		 * heading ); // add data graphView.addSeries(seriesCos);
		 * graphView.addSeries(seriesSin);
		 */

		GraphViewSeries start_percent_series = new GraphViewSeries("Start %",
				new GraphViewSeriesStyle(Color.RED, 0), new GraphViewData[] {
						new GraphViewData(1, 10), new GraphViewData(2, 20),
						new GraphViewData(3, 60), new GraphViewData(4, 30) });

		GraphView graphView = new LineGraphView(this // context
				, "GraphViewDemo" // heading
		);
		graphView.addSeries(start_percent_series); // data
		
		GraphViewSeries end_percent_series = new GraphViewSeries("End %",
				new GraphViewSeriesStyle(Color.GREEN, 0), new GraphViewData[] {
						new GraphViewData(1, 40), new GraphViewData(2, 70),
						new GraphViewData(3, 100), new GraphViewData(4, 60) });

	
		graphView.addSeries(end_percent_series); // data

		// optional - set view port, start=2, size=10
		graphView.setViewPort(1, 5);
		graphView.setScalable(false);
		// optional - legend
		graphView.setShowLegend(true);
		graphView.setScrollable(true);
		// optional - activate scaling / zooming
		graphView.setScalable(false);
		graphView.getGraphViewStyle().setLegendBorder(20);
		graphView.getGraphViewStyle().setLegendSpacing(30);
		graphView.getGraphViewStyle().setLegendWidth(200);
		
		((LineGraphView) graphView).setDrawDataPoints(true);
		((LineGraphView) graphView).setDataPointsRadius(15f);
		
		
		graphView.getGraphViewStyle().setGridColor(Color.GRAY);

		graphView.getGraphViewStyle().setNumHorizontalLabels(6);
		graphView.getGraphViewStyle().setNumVerticalLabels(5);

		graphView.getGraphViewStyle().setVerticalLabelsAlign(Align.CENTER);

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

			break;
		case SimpleGestureFilter.SWIPE_LEFT:

			break;

		}
	}

	@Override
	public void onDoubleTap() {
		// Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
	}
}