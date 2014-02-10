package com.iitb.aakash.batterycheck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DetectPowerConnectedStateRecevier extends BroadcastReceiver {
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		String action = intent.getAction();
       
		
		
		// Test for power connected
		if(action.equals(Intent.ACTION_POWER_CONNECTED)) {
		
			Toast.makeText(context, "connected: "+action.equals(Intent.ACTION_POWER_CONNECTED), Toast.LENGTH_SHORT).show();	
			
			
	    	Intent i = new Intent(context, DetectPowerConnectedService.class);
			context.startService(i);
			
			
		//	context.stopService(i);
			
		}
		//if(action.equals(Intent.ACTION_POWER_CONNECTED)) 
	    
	    // Test for power disconnected
		
		
		
		if(action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
			
			 Toast.makeText(context, "Disconnected: "+action.equals(Intent.ACTION_POWER_DISCONNECTED), Toast.LENGTH_SHORT).show();
	    	Intent i = new Intent(context, DetectPowerDisconnectedService.class);
			context.startService(i);
			//context.stopService(i);
			
		}
	}

}
