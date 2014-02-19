package com.iitb.aakash.batterycheck;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.iitb.aakash.batterycheck.SimpleGestureFilter.SimpleGestureListener;

@SuppressLint("NewApi")
public class Logs extends Activity implements SimpleGestureListener {

	TextView txt_info, txt_graph;
	private SQLiteAdapter mySQLiteAdapter;
	SimpleCursorAdapter cursorAdapter;
	Cursor cursor,cursor1;
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
		cursor1 = mySQLiteAdapter.queueAll();
		cursor = mySQLiteAdapter.queueAll();
		listContent = (ListView) findViewById(R.id.listView);
		txt_graph = (TextView) findViewById(R.id.txtGraph_inactive);

		String[] from = new String[] {SQLiteAdapter.KEY_ID, SQLiteAdapter.TIME_IN,
				SQLiteAdapter.TIME_OUT, SQLiteAdapter.START_PER,
				SQLiteAdapter.END_PER,SQLiteAdapter.TIME_TAKEN,SQLiteAdapter.PERCENTAGE };

		int[] to = new int[] { R.id.SrNo, R.id.StartTime, R.id.EndTime, R.id.StartPer, R.id.EndPer,
				R.id.TimeDiff, R.id.PerCharged  };

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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.battery_check, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.export:

			backupDatabaseCSV("/mnt/sdcard/"); // call to export function

			return true;
		case R.id.about:
			// startActivity(new Intent(this, About.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

		// respond to menu item selection

	}

	// ------------- Export table data to CSV file--------------------//
	private void backupDatabaseCSV(String outFileName) {

		String csvValues = "";

		try {

			File outFile = new File(outFileName, "batterycheck.csv");
			FileWriter fileWriter = new FileWriter(outFile);
			BufferedWriter out = new BufferedWriter(fileWriter);

			if (cursor1 != null) {
				// out.write(csvHeader);
				while (cursor1.moveToNext()) {
					csvValues = cursor1.getInt(0) + ",";
					csvValues += cursor1.getString(1) + ",";
					csvValues += cursor1.getString(2) + ",";
					csvValues += cursor1.getString(3) + ",";
					csvValues += cursor1.getString(4) + ",";
					csvValues += cursor1.getString(5) + ",";
					csvValues += cursor1.getString(6) + ",";
					csvValues += cursor1.getString(7) + ",";
					csvValues += cursor1.getString(8);

					csvValues += "\n";

					out.write(csvValues);

				}
				cursor1.close();
			}
			out.close();
			Toast.makeText(getApplicationContext(),
					"Exported to /mnt/sdcard/ ", Toast.LENGTH_SHORT).show();

		} catch (IOException e) {

		}
		mySQLiteAdapter.close();

	}
	

}
