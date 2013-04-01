package sms.sniff.sms;

public enum SmsType {

	MESSAGE_ALL    (0),
	MESSAGE_INBOX  (1),
	MESSAGE_SENT   (2),
	MESSAGE_DRAFT  (3),
	MESSAGE_OUTBOX (4),
	MESSAGE_FAILED (5),
	MESSAGE_QUEUED (6);
	
	private int value;

	private SmsType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
