package sms.sniff.call;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class IncomingOutgoingCall extends BroadcastReceiver{
	
	PhoneListener phoneListener = new PhoneListener();

	@Override
	public void onReceive(Context context, Intent intent) {
		TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		manager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
		phoneListener.setIntent(intent);
	}
}
