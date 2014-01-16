package com.iitb.aakash.batterycheck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DetectPowerConnectedStateRecevier extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		String action = intent.getAction();

		// Test for power connected
		if(action.equals(Intent.ACTION_POWER_CONNECTED)) {
			
	    	Intent i = new Intent(context, DetectPowerConnectedService.class);
			context.startService(i);
			
	    } // if(action.equals(Intent.ACTION_POWER_CONNECTED)) 
	    
	    // Test for power disconnected
		if(action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
			
	    	Intent i = new Intent(context, DetectPowerDisconnectedService.class);
			context.startService(i);
			
	    } // if(action.equals(Intent.ACTION_POWER_DISCONNECTED)) 

	}

}
