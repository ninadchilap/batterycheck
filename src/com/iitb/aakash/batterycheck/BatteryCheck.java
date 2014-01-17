package com.iitb.aakash.batterycheck;

import java.util.Calendar;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class BatteryCheck extends Activity {

	
	static TextView batteryInfo;
	private ImageView imageBatteryState;
	private TextView batterypercent;
	private TextView charging;
	private SQLiteAdapter mySQLiteAdapter;
	SimpleCursorAdapter cursorAdapter;
	Cursor cursor;
	Calendar today;
	ListView listContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		batteryInfo = ((TextView) findViewById(R.id.textViewBatteryInfo));
		imageBatteryState = (ImageView) findViewById(R.id.imageViewBatteryState);
		batterypercent = (TextView) findViewById(R.id.batterypercent);
		charging = (TextView) findViewById(R.id.charging);
		listContent = (ListView) findViewById(R.id.listView);

		today = Calendar.getInstance();
		batteryInfo
				.setText("Time is" + today.get(Calendar.HOUR) + ":"
						+ today.get(Calendar.MINUTE) + ":"
						+ today.get(Calendar.SECOND));

		mySQLiteAdapter = new SQLiteAdapter(this);
		mySQLiteAdapter.openToWrite();
		System.out.println("before insert");

		// updateList();
		System.out.println("after insert");
		cursor = mySQLiteAdapter.queueAll();
		String[] from = new String[] { SQLiteAdapter.TIME_IN,
				SQLiteAdapter.TIME_OUT, SQLiteAdapter.START_PER,
				SQLiteAdapter.END_PER, SQLiteAdapter.START_DATE,
				SQLiteAdapter.END_DATE };
		int[] to = new int[] { R.id.txt1, R.id.txt2, R.id.txt3, R.id.txt4,
				R.id.txt5, R.id.txt6 };
		cursorAdapter = new SimpleCursorAdapter(this, R.layout.list, cursor,
				from, to);
		listContent.setAdapter(cursorAdapter);

		getBatteryPercentage();

	}

	// method for getting the battery details

	private void getBatteryPercentage() {
		BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				context.unregisterReceiver(this);
				
				int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,
						0);
				/*int currentLevel = intent.getIntExtra(
						BatteryManager.EXTRA_LEVEL, -1);
				
				int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
				int level = -1;
				if (currentLevel >= 0 && scale > 0) {
					level = (currentLevel * 100) / scale;
				}*/
				int level1 = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

				batterypercent.setText("Battery Level Remaining: " + level1
						+ "%");

				if (plugged == 1 || plugged == 2) {
					charging.setText("Charging: YES");

				} else if (plugged == 0) {
					charging.setText("Charging: NO");

				}

			}
		};
		IntentFilter batteryLevelFilter = new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(batteryLevelReceiver, batteryLevelFilter);
	}

	// ------------------------------------------//

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.battery_check, menu);
		return true;
	}

}
