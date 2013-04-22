package sms.sniff.behaviors;

import android.telephony.SmsMessage;

public interface Behavior {
	
	void process(SmsMessage sms);

	boolean canProcess(SmsMessage sms);

	
}
