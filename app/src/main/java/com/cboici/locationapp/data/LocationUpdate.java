package com.cboici.locationapp.data;

import java.util.Calendar;

// Obiectul ce contine datele unui update.
// In acest caz, o locatie si ora la care a fost adaugata in lista.
public class LocationUpdate {

    public String location;
    public String time;

    public LocationUpdate(double lat, double lon, String locality) {

        this.location = getFormattedCoordinates(lat, lon, locality);
        this.time = getFormattedTime(Calendar.getInstance());
    }

    private String getFormattedCoordinates(double lat, double lon, String locality) {

        return locality + ": " + String.valueOf(lat) + ", " + String.valueOf(lon);
    }

    // Ora curenta, in format HH:MM:SS
    private String getFormattedTime(Calendar calendarInstance) {

        return calendarInstance.get(Calendar.HOUR_OF_DAY) + ":" +
            getValueWithLeadingZero(calendarInstance.get(Calendar.MINUTE)) + ":" +
            getValueWithLeadingZero(calendarInstance.get(Calendar.SECOND));
    }

    // Daca o valoare 'x' este mai mica de 10, se transforma in '0x'
    private String getValueWithLeadingZero(int value) {
        return (value < 10) ? ("0" + String.valueOf(value)) : String.valueOf(value);
    }
}
