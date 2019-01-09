/*
 * Copyright (C) 2009 University of Washington
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package np.com.naxa.iset.gps;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Window;
import android.widget.Toast;

import java.text.DecimalFormat;

import static np.com.naxa.iset.activity.OpenSpaceActivity.LOCATION_RESULT;


public class GeoPointActivity extends Activity implements LocationListener {

    private static final String LOCATION_COUNT = "locationCount";
    public static final double DEFAULT_LOCATION_ACCURACY = 15.0;

    private ProgressDialog progessDialogLocationRequest;
    private LocationManager locationManager;
    private Location location;
    private boolean isGPSOn = false;
    private boolean isNetworkOn = false;
    private double locationAccuracy;
    private int locationCount = 0;

    private String TAG = "GeoPointActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        if (savedInstanceState != null) {
            locationCount = savedInstanceState.getInt(LOCATION_COUNT);
        }

        //if you want to send accuracy manually send them here.
        locationAccuracy = DEFAULT_LOCATION_ACCURACY;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isGPSOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkOn = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSOn && !isNetworkOn) {
            Toast.makeText(this, "Sorry,location providers are turned off.", Toast.LENGTH_SHORT).show();
            finish();
        }

        setupLocationDialog();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(LOCATION_COUNT, locationCount);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stops the GPS. Note that this will turn off the GPS if the screen goes to sleep.
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }

        if (progessDialogLocationRequest != null && progessDialogLocationRequest.isShowing()) {
            progessDialogLocationRequest.dismiss();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (locationManager != null) {
            if (isGPSOn) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }
            if (isNetworkOn) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
        }
        if (progessDialogLocationRequest != null) {
            progessDialogLocationRequest.show();
        }
    }


    /**
     * Sets up the look and actions for the progress dialog while the GPS is searching.
     */
    private void setupLocationDialog() {
        // dialog displayed while fetching gps location
        progessDialogLocationRequest = new ProgressDialog(this);
        DialogInterface.OnClickListener geopointButtonListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                returnLocation();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                location = null;
                                finish();
                                break;
                        }
                    }
                };

        // back button doesn't cancel
        progessDialogLocationRequest.setCancelable(false);
        progessDialogLocationRequest.setIndeterminate(true);
        progessDialogLocationRequest.setIcon(android.R.drawable.ic_dialog_info);
        progessDialogLocationRequest.setTitle("Getting location");
        progessDialogLocationRequest.setMessage("Please wait. This could take a while");
        progessDialogLocationRequest.setButton(DialogInterface.BUTTON_POSITIVE, "Accept Location",
                geopointButtonListener);
        progessDialogLocationRequest.setButton(DialogInterface.BUTTON_NEGATIVE,
                "Dismiss",
                geopointButtonListener);
    }


    private void returnLocation() {
        if (location != null) {
            Intent i = new Intent();
            i.putExtra(
                    LOCATION_RESULT,
                    location.getLatitude() + " " + location.getLongitude() + " "
                            + location.getAltitude() + " " + location.getAccuracy());
            setResult(RESULT_OK, i);
        }
        finish();
    }


    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        if (this.location != null) {
            // Bug report: cached GeoPoint is being returned as the first value.
            // Wait for the 2nd value to be returned, which is hopefully not cached?
            ++locationCount;

            if (locationCount > 1) {
                progessDialogLocationRequest.setMessage("Location Accuracy: " + truncateDouble(this.location.getAccuracy()) + " meters");
                if (this.location.getAccuracy() <= locationAccuracy) {
                    returnLocation();
                }
            }
        }
    }



    private String truncateDouble(float number) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(number);
    }


    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onProviderEnabled(String provider) {

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                if (location != null) {
                    progessDialogLocationRequest.setMessage("Location Accuracy: " + truncateDouble(location.getAccuracy()) + " meters");
                }
                break;
            case LocationProvider.OUT_OF_SERVICE:
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                break;
        }
    }

}