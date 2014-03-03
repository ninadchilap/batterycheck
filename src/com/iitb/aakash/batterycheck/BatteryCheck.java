package com.iitb.aakash.batterycheck;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iitb.aakash.batterycheck.SimpleGestureFilter.SimpleGestureListener;

public class BatteryCheck extends Activity implements SimpleGestureListener {

	// static TextView batteryInfo;
	/*
	 * private ImageView imageBatteryState; private TextView batterypercent;
	 * private TextView charging;
	 */
	private SimpleGestureFilter detector;
	private TextView txtStatus, txtHealth, txtTemp, txtVolt, txtTech,
			txtPercentage, txt_info, txtTitle;
	// private SQLiteAdapter mySQLiteAdapter;
	// SimpleCursorAdapter cursorAdapter;
	// Cursor cursor;
	Calendar today;
	ListView listContent;
	static int level;
	ImageView imgBatteryState;
	TextView txt_logs, txt_graph;
	SQLiteAdapter dbAdapter;
	Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		dbAdapter = new SQLiteAdapter(this);
		dbAdapter.openToWrite();
		cursor = dbAdapter.queueAll();

		detector = new SimpleGestureFilter(this, this);
		/* Initialization of all the objects of widgets */

		// batteryInfo = ((TextView) findViewById(R.id.textViewBatteryInfo));

		/*
		 * old imageBatteryState = (ImageView)
		 * findViewById(R.id.imageViewBatteryState); batterypercent = (TextView)
		 * findViewById(R.id.batterypercent); charging = (TextView)
		 * findViewById(R.id.charging);
		 */

		txtStatus = (TextView) findViewById(R.id.txtStatus);
		txtHealth = (TextView) findViewById(R.id.txtHealth);
		txtTemp = (TextView) findViewById(R.id.txtTemperature);
		txtVolt = (TextView) findViewById(R.id.txtVoltage);
		txtTech = (TextView) findViewById(R.id.txtTechnology);
		txtPercentage = (TextView) findViewById(R.id.txtPercentage);
		txt_logs = (TextView) findViewById(R.id.txtLogs_inactive);
		txt_graph = (TextView) findViewById(R.id.txtGraph_inactive);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		txt_info = (TextView) findViewById(R.id.txtInfo_active);

		Typeface font = Typeface.createFromAsset(getAssets(), "JosefinSlab-Light.ttf");
		Typeface font_bold = Typeface.createFromAsset(getAssets(), "JosefinSlab-SemiBold.ttf");
		Typeface font_normal = Typeface.createFromAsset(getAssets(), "JosefinSlab-Regular.ttf");
		txtStatus.setTypeface(font);
		txtHealth.setTypeface(font);
		txtVolt.setTypeface(font);
		txtTemp.setTypeface(font);
		txtPercentage.setTypeface(font);
		txt_logs.setTypeface(font);
		txt_graph.setTypeface(font);
		txtTech.setTypeface(font);
		txtTitle.setTypeface(font_bold);
		txt_info.setTypeface(font_normal);
		
		txt_graph.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setAlpha((float) 0.2);
				Intent infoactivity = new Intent(BatteryCheck.this, Graph.class);
				startActivity(infoactivity);
				overridePendingTransition(R.anim.anim_left_to_right1,
						R.anim.anim_right_to_left1);
				finish();
			}
		});

		txt_logs.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setAlpha((float) 0.2);
				Intent infoactivity = new Intent(BatteryCheck.this, Logs.class);
				startActivity(infoactivity);
				overridePendingTransition(R.anim.anim_left_to_right1,
						R.anim.anim_right_to_left1);
				finish();
			}
		});

		imgBatteryState = (ImageView) findViewById(R.id.imgBattery);
		// listContent = (ListView) findViewById(R.id.listView);

		today = Calendar.getInstance();
		/*
		 * batteryInfo .setText("Time is" + today.get(Calendar.HOUR) + ":" +
		 * today.get(Calendar.MINUTE) + ":" + today.get(Calendar.SECOND));
		 */

		/* initialization of SQLite adapter */
		/*
		 * mySQLiteAdapter = new SQLiteAdapter(this);
		 * mySQLiteAdapter.openToWrite();
		 * 
		 * // updateList();
		 * 
		 * cursor = mySQLiteAdapter.queueAll();
		 */
		/*
		 * String[] from = new String[] { SQLiteAdapter.TIME_IN,
		 * SQLiteAdapter.TIME_OUT, SQLiteAdapter.START_PER,
		 * SQLiteAdapter.END_PER, SQLiteAdapter.START_DATE,
		 * SQLiteAdapter.END_DATE }; int[] to = new int[] { R.id.txt1,
		 * R.id.txt2, R.id.txt3, R.id.txt4, R.id.txt5, R.id.txt6 };
		 * cursorAdapter = new SimpleCursorAdapter(this, R.layout.list, cursor,
		 * from, to); listContent.setAdapter(cursorAdapter);
		 */

		/*
		 * IntentFilter batteryLevelFilter = new IntentFilter(
		 * Intent.ACTION_BATTERY_CHANGED);
		 * registerReceiver(batteryLevelReceiver, batteryLevelFilter);
		 */

		this.registerReceiver(this.batteryLevelReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));

	}

	/*
	 * public void updateList() { cursor.requery(); }
	 */
	BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {

			int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
			int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
			String strHealth = null;
			// Displaying the health constants
			switch (health) {
			case 7:
				strHealth = "Battery Cold";
				break;

			case 4:
				strHealth = "Battery Dead";
				break;

			case 2:
				strHealth = "Battery Good";
				break;

			case 3:
				strHealth = "Battery Overheat";
				break;

			case 5:
				strHealth = "Battery Over Voltage";
				break;

			case 1:
				strHealth = "Unknown";
				break;

			case 6:
				strHealth = "Battery Failure";
				break;
			}

			// boolean present=
			// intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
			int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
			int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
			String technology = intent.getExtras().getString(
					BatteryManager.EXTRA_TECHNOLOGY);

			int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
			int temperature = intent.getIntExtra(
					BatteryManager.EXTRA_TEMPERATURE, 0);
			/*
			 * int currentLevel = intent.getIntExtra(
			 * BatteryManager.EXTRA_LEVEL, -1);
			 * 
			 * int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
			 * int level = -1; if (currentLevel >= 0 && scale > 0) { level =
			 * (currentLevel * 100) / scale; }
			 */
			level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

			txtHealth.setText("Health: " + strHealth);
			txtTemp.setText("Temperature: " + temperature / 10 + "C");
			txtVolt.setText("Voltage: " + voltage + "mAh");
			txtTech.setText("Technology: " + technology);
			txtPercentage.setText(level + "%");

			if (plugged == 1 || plugged == 2) {

				if (level <= 10) {
					imgBatteryState
							.setImageResource(R.drawable.battery_red_charging);
				} else if (level > 10 && level <= 20) {
					imgBatteryState
							.setImageResource(R.drawable.battery_red_charging);
				} else if (level > 20 && level <= 60) {
					imgBatteryState
							.setImageResource(R.drawable.battery_orange_charging);
				} else if (level > 60 && level <= 90) {
					imgBatteryState
							.setImageResource(R.drawable.battery_yellow_charging);
				} else if (level > 90) {
					imgBatteryState
							.setImageResource(R.drawable.battery_green_charging);
				}

				txtStatus.setText("Status: Charging");

				// updateList();

			} else if (plugged == 0) {

				if (level <= 10) {
					imgBatteryState.setImageResource(R.drawable.battery_empty);
				} else if (level > 10 && level <= 20) {
					imgBatteryState.setImageResource(R.drawable.battery_red);
				} else if (level > 20 && level <= 60) {
					imgBatteryState.setImageResource(R.drawable.battery_orange);
				} else if (level > 60 && level <= 90) {
					imgBatteryState.setImageResource(R.drawable.battery_yellow);
				} else if (level > 90) {
					imgBatteryState.setImageResource(R.drawable.battery_green);
				}

				txtStatus.setText("Status: Not Charging");
				// updateList();

			}

		}
	};

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

			if (cursor != null) {
				// out.write(csvHeader);
				while (cursor.moveToNext()) {
					csvValues = cursor.getInt(0) + ",";
					csvValues += cursor.getString(1) + ",";
					csvValues += cursor.getString(2) + ",";
					csvValues += cursor.getString(3) + ",";
					csvValues += cursor.getString(4) + ",";
					csvValues += cursor.getString(5) + ",";
					csvValues += cursor.getString(6) + ",";
					csvValues += cursor.getString(7) + ",";
					csvValues += cursor.getString(8);

					csvValues += "\n";

					out.write(csvValues);

				}
				cursor.close();
			}
			out.close();
			Toast.makeText(getApplicationContext(),
					"Exported to /mnt/sdcard/ ", Toast.LENGTH_SHORT).show();

		} catch (IOException e) {

		}
		dbAdapter.close();

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
			
			break;
		case SimpleGestureFilter.SWIPE_LEFT:
			System.out.println("LEFT");
			Intent logsactivity = new Intent(BatteryCheck.this, Logs.class);
			startActivity(logsactivity);
			overridePendingTransition(R.anim.anim_left_to_right1,
					R.anim.anim_right_to_left1);
			finish();
			break;
		
		}
	}

	@Override
	public void onDoubleTap() {
		//Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
	}
}
