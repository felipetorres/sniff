package sms.sniff;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.PhoneLookup;

public class Utils {

	public String getContactByPhoneNumber(ContentResolver contentResolver, String phoneNumber) {
		Uri personUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
		Cursor cursor = contentResolver.query(personUri, new String[] { PhoneLookup.DISPLAY_NAME }, null, null, null);
		if (cursor.moveToFirst()) {
			int index = cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME);
			String name = cursor.getString(index);
			cursor.close();
			return name;
		}
		return null;
	}
	
	public String format(String date) {
		long l = Long.parseLong(date);
		   SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm a");        
		   Date resultdate = new Date(l);
		   String strDate = sdf.format(resultdate);
		return strDate;
	}
}
