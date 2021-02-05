package com.example.androidtaskeslammohamed.utilits.helperFunction;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.androidtaskeslammohamed.R;
import com.example.androidtaskeslammohamed.utilits.constants.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public final class HelperFunctions {

    //region checkGPS
    public static void initAlertDialogForGPS(Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity)
                .setTitle(activity.getResources().getString(R.string.permission))
                .setMessage(activity.getResources().getString(R.string.gps_required))
                .setPositiveButton(activity.getResources().getString(R.string.yes),
                        ((dialogInterface, i) -> {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            activity.startActivityForResult(intent, Constants.GPS_REQUEST_CODE);
                        }));
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public static boolean checkGPSEnabled(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    //endregion


    //region check permission gps


    public static boolean checkLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }


    public static void askLocationPermission(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSION_REQUEST_CODE);
        }
    }


    //endregion


    //region check servcies
    public static boolean isServicesOk(Activity context) {

        GoogleApiAvailability googleApi = GoogleApiAvailability.getInstance();

        int result = googleApi.isGooglePlayServicesAvailable(context);

        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApi.isUserResolvableError(result)) {
            Dialog dialog = googleApi.getErrorDialog(context, result, Constants.PLAY_SERVICES_ERROR_CODE, task ->
                    Toast.makeText(context, "Dialog is cancelled by User", Toast.LENGTH_SHORT).show());
            dialog.show();
        } else {
            Toast.makeText(context, "Play services are required by this application", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    //endregion
}
