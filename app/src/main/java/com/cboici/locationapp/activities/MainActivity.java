package com.cboici.locationapp.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.cboici.locationapp.R;
import com.cboici.locationapp.adapters.LocationUpdatesAdapter;
import com.cboici.locationapp.data.LocationUpdate;
import com.cboici.locationapp.helpers.DialogHelper;
import com.cboici.locationapp.helpers.GeocodingHelper;
import com.cboici.locationapp.interfaces.IDialogCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;

public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private LocationUpdatesAdapter locationUpdatesAdapter;

    @Override
    public void onConnected(Bundle connectionInfo) {

        getAndDisplayLocation();

        Button refreshButton = (Button) findViewById(R.id.request_location_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndDisplayLocation();
            }
        });
    }

    private void getAndDisplayLocation() {

        Location lastLocation = getInitialLocation();

        if (lastLocation != null) {
            try {
                showLocationAlert(lastLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            DialogHelper.showAlert(getResources().getString(R.string.dialog_receive_location_error), getResources().getString(R.string.dialog_receive_location_error_title), this, null);
        }
    }

    @Override
    public void onConnectionSuspended(int var1) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult var1) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this).setMessage("The app could not connect to Google API.").setTitle("Connection error");
        dialog.show();
    }

    // Dupa ce activitatea a fost creata, vom initializa un client pt Google Play API
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleApiClient = getGoogleApiClientInstance();
        ListView locationUpdatesList = (ListView) findViewById(R.id.location_updates_list);
        locationUpdatesAdapter = new LocationUpdatesAdapter(this, R.id.location_updates_list);
        locationUpdatesList.setAdapter(locationUpdatesAdapter);
    }

    // Dupa terminarea initializarii activitatii, clientul se va conecta la Google API
    @Override
    protected void onStart() {

        googleApiClient.connect();
        super.onStart();
    }

    // Dupa ascunderea activitatii, clientul se va deconecta de la API
    @Override
    protected void onStop() {

        googleApiClient.disconnect();
        super.onStop();
    }

    // Creaza o instanta de client pt Google API
    private GoogleApiClient getGoogleApiClientInstance() {

        GoogleApiClient result = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();

        return result;
    }

    private void showLocationAlert(final Location location) throws IOException {

        String message = getResources().getString(R.string.dialog_current_location_coordinates);
        String title = getResources().getString(R.string.dialog_receive_location_title);
        String localAddress = GeocodingHelper.geocodeAddress(location.getLatitude(), location.getLongitude(), MainActivity.this);
        final LocationUpdate update = new LocationUpdate(location.getLatitude(), location.getLatitude(), localAddress);

        message = String.format(message, location.getLatitude(), location.getLongitude(), localAddress);

        DialogHelper.showAlert(message, title, this, new IDialogCallback() {
            @Override
            public void executeCallback() {
                locationUpdatesAdapter.addItem(update);
            }
        });
    }

    private Location getInitialLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            DialogHelper.showAlert(getResources().getString(R.string.dialog_permissions_error), getResources().getString(R.string.dialog_permissions_error_title), this, null);
            return null;
        }

        return LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }
}
