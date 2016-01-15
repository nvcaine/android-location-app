package com.cboici.locationapp.helpers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodingHelper {

    public static String geocodeAddress(double lat, double lon, Context context) throws IOException {
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(lat, lon, 1);

        if (addresses.size() > 0 && addresses.get(0).getLocality() != null)
            return addresses.get(0).getLocality();

        return "[Unidentified location]";
    }
}
