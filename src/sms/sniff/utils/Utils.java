package sms.sniff.utils;

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
	
	public String format(String date, String pattern) {
		long l = Long.parseLong(date);
		return format(l, pattern);
	}
	
	public String format(long date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);        
		Date resultdate = new Date(date);
	    String strDate = sdf.format(resultdate);
		return strDate;
	}
}
