package com.iitb.aakash.batterycheck;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.iitb.aakash.batterycheck.SimpleGestureFilter.SimpleGestureListener;

@SuppressLint("NewApi")
public class Logs extends Activity implements SimpleGestureListener {

	TextView txt_info, txt_graph, txtTitle, txt_logs;
	private SQLiteAdapter mySQLiteAdapter;
	SimpleCursorAdapter cursorAdapter;
	Cursor cursor;
	TableRow logtable;
	ListView listContent;
	private SimpleGestureFilter detector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logs);
		detector = new SimpleGestureFilter(this, this);
		txt_info = (TextView) findViewById(R.id.txtInfo_inactive);
		mySQLiteAdapter = new SQLiteAdapter(this);
		mySQLiteAdapter.openToWrite();
		cursor = mySQLiteAdapter.queueAll();
		listContent = (ListView) findViewById(R.id.listView);
		txt_graph = (TextView) findViewById(R.id.txtGraph_inactive);
		txt_logs = (TextView) findViewById(R.id.txtLogs_active);
		txtTitle = (TextView) findViewById(R.id.txtTitle);

		Typeface font = Typeface.createFromAsset(getAssets(), "JosefinSlab-Light.ttf");
		Typeface font_bold = Typeface.createFromAsset(getAssets(), "JosefinSlab-SemiBold.ttf");
		Typeface font_normal = Typeface.createFromAsset(getAssets(), "JosefinSlab-Regular.ttf");
		txt_info.setTypeface(font);
		txt_graph.setTypeface(font);
		txt_logs.setTypeface(font_normal);
		txtTitle.setTypeface(font_bold);
		
		
		String[] from = new String[] {SQLiteAdapter.TIME_IN,
				SQLiteAdapter.TIME_OUT, SQLiteAdapter.START_PER,
				SQLiteAdapter.END_PER,SQLiteAdapter.TIME_TAKEN,SQLiteAdapter.PERCENTAGE };

		int[] to = new int[] { R.id.txt1, R.id.txt2, R.id.txt3, R.id.txt4,
				R.id.txt5, R.id.txt6 };

		cursorAdapter = new SimpleCursorAdapter(this, R.layout.list, cursor,
				from, to);
		listContent.setAdapter(cursorAdapter);

		this.registerReceiver(this.batteryLevelReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));

		txt_info.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setAlpha((float) 0.2);
				Intent infoactivity = new Intent(Logs.this, BatteryCheck.class);
				startActivity(infoactivity);
				overridePendingTransition(R.anim.anim_left_to_right,
						R.anim.anim_right_to_left);
				finish();
			}
		});

		txt_graph.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setAlpha((float) 0.2);
				Intent infoactivity = new Intent(Logs.this, Graph.class);
				startActivity(infoactivity);
				overridePendingTransition(R.anim.anim_left_to_right1,
						R.anim.anim_right_to_left1);
				finish();
			}
		});

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		this.unregisterReceiver(this.batteryLevelReceiver);
		finish();
		super.onStop();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		// this.unregisterReceiver(this.batteryLevelReceiver);
		finish();
		super.onPause();
	}

	public void updateList() {
		cursor.requery();
	}

	BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {

			updateList();

		}
	};

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
			Intent infoactivity = new Intent(Logs.this, BatteryCheck.class);
			startActivity(infoactivity);
			overridePendingTransition(R.anim.anim_left_to_right,
					R.anim.anim_right_to_left);
			finish();
			break;
		case SimpleGestureFilter.SWIPE_LEFT:
			System.out.println("LEFT");
			Intent graphactivity = new Intent(Logs.this, Graph.class);
			startActivity(graphactivity);
			overridePendingTransition(R.anim.anim_left_to_right1,
					R.anim.anim_right_to_left1);
			finish();
			break;

		}
	}

	@Override
	public void onDoubleTap() {
		// Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
	}

}
