/**
 *  Plugin diagnostic
 *
 *  Copyright (c) 2012 AVANTIC ESTUDIO DE INGENIEROS
 *  
**/

package net.avantic.diagnosticPlugin;

import org.apache.cordova.api.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;


public class Diagnostic extends org.apache.cordova.api.CordovaPlugin {

	private static final String LOG_TAG = "Diagnostic";

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext ctx) throws JSONException {
		Log.d(LOG_TAG, "Executing Diagnostic Plugin");
		if ("isLocationEnabled".equals(action))
			ctx.success(isLocationEnabled() ? 1 : 0);
		else if ("switchToLocationSettings".equals(action))
			switchToLocationSettings();
		else if ("isGpsEnabled".equals(action))
			ctx.success(isGpsEnabled() ? 1 : 0);
		else if ("isWirelessNetworkLocationEnabled".equals(action))
			ctx.success(isWirelessNetworkLocationEnabled() ? 1 : 0);
		else if ("isWifiEnabled".equals(action))
			ctx.success(isWifiEnabled() ? 1 : 0);
		else if ("switchToWifiSettings".equals(action))
			switchToWifiSettings();
		else if ("isBluetoothEnabled".equals(action))
			ctx.success(isBluetoothEnabled() ? 1 : 0);
		else if ("switchToBluetoothSettings".equals(action))
			switchToBluetoothSettings();
		else {
			Log.d(LOG_TAG, "Invalid action " + action);
			return false;
		}
		return true;
	}

	
	/**
	 * Check device settings for location.
	 * 
	 * @returns {boolean} The status of location services in device settings.
	 */
	public boolean isLocationEnabled() {
		boolean result = (isGpsEnabled() || isWirelessNetworkLocationEnabled());
		Log.d(LOG_TAG, "Location enabled: " + result);
		return result;
	}
	
	/**
	 * Requests that the user enable the location in device settings.
	 */
	public void switchToLocationSettings() {
		Log.d(LOG_TAG, "Switch to Location Settings");
		Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		cordova.getActivity().startActivity(settingsIntent);
	}
	
	/**
	 * Check device settings for GPS.
	 * 
	 * @returns {boolean} The status of GPS in device settings.
	 */
	public boolean isGpsEnabled() {
		boolean result = isLocationProviderEnabled(LocationManager.GPS_PROVIDER);
		Log.d(LOG_TAG, "GPS enabled: " + result);
		return result;
	}

	/**
	 * Check device settings for wireless network location (Wi-Fi and/or mobile
	 * networks).
	 * 
	 * @returns {boolean} The status of wireless network location in device
	 *          settings.
	 */
	public boolean isWirelessNetworkLocationEnabled() {
		boolean result = isLocationProviderEnabled(LocationManager.NETWORK_PROVIDER);
		Log.d(LOG_TAG, "Wireless Network Location enabled: " + result);
		return result;
	}

	private boolean isLocationProviderEnabled(String provider) {
		LocationManager locationManager = (LocationManager) cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(provider);
	}

	/**
	 * Check device settings for Wi-Fi.
	 * 
	 * @returns {boolean} The status of Wi-Fi in device settings.
	 */
	public boolean isWifiEnabled() {
		WifiManager wifiManager = (WifiManager) cordova.getActivity().getSystemService(Context.WIFI_SERVICE);
		boolean result = wifiManager.isWifiEnabled();
		Log.d(LOG_TAG, "Wifi enabled: " + result);
		return result;
	}
	
	/**
	 * Requests that the user enable the Wi-Fi in device settings.
	 */
	public void switchToWifiSettings() {
		Log.d(LOG_TAG, "Switch to Wifi Settings");
		Intent settingsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
		cordova.getActivity().startActivity(settingsIntent);
	}
	
	/**
	 * Check device settings for Bluetooth.
	 * 
	 * @returns {boolean} The status of Bluetooth in device settings.
	 */
	public boolean isBluetoothEnabled() {
		Looper.prepare();
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		boolean result = bluetoothAdapter.isEnabled();
		Log.d(LOG_TAG, "Bluetooth enabled: " + result);
		return result;
	}
	
	/**
	 * Requests that the user enable the Bluetooth in device settings.
	 */
	public void switchToBluetoothSettings() {
		Log.d(LOG_TAG, "Switch to Bluetooth Settings");
		Intent settingsIntent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
		cordova.getActivity().startActivity(settingsIntent);
	}
}