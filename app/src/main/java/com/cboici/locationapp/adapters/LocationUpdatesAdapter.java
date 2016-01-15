package com.cboici.locationapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cboici.locationapp.R;
import com.cboici.locationapp.data.LocationUpdate;

import java.util.ArrayList;

// Un adapter se foloseste pt a corela o colectie de date cu un element al interfetei.
// Se defineste un layout pentru un item al colectiei,iar acel element se foloseste pentru fiecare item din lista.
public class LocationUpdatesAdapter extends ArrayAdapter<LocationUpdate> {

    private ArrayList<LocationUpdate> storedLocations;
    private Context context;

    public LocationUpdatesAdapter(Context context, int resource) {

        super(context, resource);

        this.storedLocations = new ArrayList<>();
        this.context = context;
    }

    // Metoda-minune ce initializeaza layout-ul cu datele itemului curent din colectie.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View locationUpdateView = inflater.inflate(R.layout.location_item, parent, false);
        LocationUpdate locationRecord = storedLocations.get(position);

        TextView locationTimeContainer = (TextView) locationUpdateView.findViewById(R.id.location_time);
        TextView locationCoordinatesContainer = (TextView) locationUpdateView.findViewById(R.id.location_coords);

        locationTimeContainer.setText(locationRecord.time);
        locationCoordinatesContainer.setText(locationRecord.location);

        return locationUpdateView;
    }

    @Override
    public LocationUpdate getItem(int position) { return storedLocations.get(position); }

    @Override
    public int getCount() { return storedLocations.size(); }

    // Cand adaugam un nou update pentru a fi afisat, pe langa faptul de a-l stoca in colectia ce contine toate elemente,
    // trebuie sa cerem o actualizare a setului de date, pentru a afisa si elementeul nou adaugat.
    public void addItem(LocationUpdate update) {

        storedLocations.add(0, update);
        notifyDataSetChanged();
    }
}
