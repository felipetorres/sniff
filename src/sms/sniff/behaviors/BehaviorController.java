package sms.sniff.behaviors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class BehaviorController extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		
		if(bundle != null) {
			
			try {
				Object[] pdus = (Object[]) bundle.get("pdus");
				msgs = new SmsMessage[pdus.length];
				for (int i=0; i < pdus.length; i++) {
					
					msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
					
					new BehaviorProcessor(this, context).process(msgs[i]);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
