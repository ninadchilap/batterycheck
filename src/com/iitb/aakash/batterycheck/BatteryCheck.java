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

	// static TextView batteryInfo;
	/*
	 * private ImageView imageBatteryState; private TextView batterypercent;
	 * private TextView charging;
	 */

	private TextView txtStatus, txtHealth, txtTemp, txtVolt, txtTech,txtPercentage;
	private SQLiteAdapter mySQLiteAdapter;
	SimpleCursorAdapter cursorAdapter;
	Cursor cursor;
	Calendar today;
	ListView listContent;
	static int level;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);

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


		listContent = (ListView) findViewById(R.id.listView);

		today = Calendar.getInstance();
		/*
		 * batteryInfo .setText("Time is" + today.get(Calendar.HOUR) + ":" +
		 * today.get(Calendar.MINUTE) + ":" + today.get(Calendar.SECOND));
		 */

		/* initialization of SQLite adapter */
		mySQLiteAdapter = new SQLiteAdapter(this);
		mySQLiteAdapter.openToWrite();

		// updateList();

		cursor = mySQLiteAdapter.queueAll();
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

	public void updateList() {
		cursor.requery();
	}

	BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {

			int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
			int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
			int icon_small = intent.getIntExtra(
					BatteryManager.EXTRA_ICON_SMALL, 0);

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

			/*
			 * batterypercent.setText(level + "%");
			 */
			txtHealth.setText("Health: " + health);
			txtTemp.setText("Temperature: " + temperature);
			txtVolt.setText("Voltage: " + voltage + "mAh");
			txtTech.setText("Technology: " + technology);
			txtPercentage.setText(level+ "%");
			
			if (plugged == 1 || plugged == 2) {
				txtStatus.setText("Status: Charging");

				updateList();
				// updateList();

			} else if (plugged == 0) {
				txtStatus.setText("Status: Not Charging");
				updateList();

			}

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.battery_check, menu);
		return true;
	}

}
