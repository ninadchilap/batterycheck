package com.iitb.aakash.batterycheck;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
	private SQLiteAdapter mySQLiteAdapter;
	SimpleCursorAdapter cursorAdapter;
	Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		detector = new SimpleGestureFilter(this, this);
		txt_info = (TextView) findViewById(R.id.txtInfo_inactive);
		txt_logs = (TextView) findViewById(R.id.txtLogs_inactive);
		txt_graph = (TextView) findViewById(R.id.txtGraph_active);
		txtTitle = (TextView) findViewById(R.id.txtTitle);

		mySQLiteAdapter = new SQLiteAdapter(this);
		mySQLiteAdapter.openToRead();
		cursor = mySQLiteAdapter.queueAll();

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

		// Getting graph data from database
		int count = cursor.getCount();
		GraphViewData[] start_per = new GraphViewData[count];
		GraphViewData[] end_per = new GraphViewData[count];

		GraphViewData[] High = new GraphViewData[count];
		GraphViewData[] Low = new GraphViewData[count];

		cursor.moveToFirst();

		if (count == 0) {
			High = new GraphViewData[1];
			Low = new GraphViewData[1];

			High[0] = new GraphViewData(0, 100);
			Low[0] = new GraphViewData(0, 0);
		}

		for (int i = 0; i < count; i++) {
			start_per[i] = new GraphViewData(i, Integer.parseInt(cursor
					.getString(3)));

			if (cursor.getString(4).equalsIgnoreCase("--"))
				end_per[i] = new GraphViewData(i, Integer.parseInt(cursor
						.getString(3)));
			else
				end_per[i] = new GraphViewData(i, Integer.parseInt(cursor
						.getString(4)));

			High[i] = new GraphViewData(i, 100);
			Low[i] = new GraphViewData(i, 0);

			cursor.moveToNext();
		}

		GraphViewSeries start_percent_series = new GraphViewSeries("Start %",
				new GraphViewSeriesStyle(Color.RED, 0), start_per);

		GraphView graphView = new LineGraphView(this // context
				, ""// heading
		);
		graphView.addSeries(start_percent_series); // data

		GraphViewSeries end_percent_series = new GraphViewSeries("End %",
				new GraphViewSeriesStyle(Color.GREEN, 0), end_per);
		graphView.addSeries(end_percent_series); // data

		GraphViewSeries high_series = new GraphViewSeries(null,
				new GraphViewSeriesStyle(Color.TRANSPARENT, 0), High);
		graphView.addSeries(high_series); // data

		GraphViewSeries low_series = new GraphViewSeries(null,
				new GraphViewSeriesStyle(Color.TRANSPARENT, 0), Low);
		graphView.addSeries(low_series); // data

		graphView.setViewPort(0, 10);
		// optional - legend
		graphView.setShowLegend(false);
		graphView.setScrollable(true);
		// optional - activate scaling / zooming
		graphView.setScalable(false);
		graphView.getGraphViewStyle().setLegendBorder(20);
		graphView.getGraphViewStyle().setLegendSpacing(30);
		graphView.getGraphViewStyle().setLegendWidth(200);

		((LineGraphView) graphView).setDrawDataPoints(true);
		((LineGraphView) graphView).setDataPointsRadius(8f);

		graphView.getGraphViewStyle().setNumHorizontalLabels(5);
		graphView.getGraphViewStyle().setNumVerticalLabels(4);

		graphView.getGraphViewStyle().setGridColor(Color.GRAY);
		graphView.getGraphViewStyle().setTextSize(15f);

		graphView.getGraphViewStyle().setNumHorizontalLabels(11);
		graphView.getGraphViewStyle().setNumVerticalLabels(11);

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