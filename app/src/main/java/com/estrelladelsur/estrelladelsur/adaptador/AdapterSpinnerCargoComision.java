package com.estrelladelsur.estrelladelsur.adaptador;

import java.util.ArrayList;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.abstracta.Cargo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class AdapterSpinnerCargoComision extends ArrayAdapter<Cargo> {
    private Activity context;
    ArrayList<Cargo> cargoArray = null;

    public AdapterSpinnerCargoComision(Activity context, int resource, ArrayList<Cargo> cargoArray) {
        super(context, resource, cargoArray);
        this.context = context;
        this.cargoArray = cargoArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {   // This view starts when we click the spinner.
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_item_general, parent, false);
        }

        Cargo cargo = cargoArray.get(position);

        if (cargo != null) {   // Parse the data from each object and set it.
            // ImageView myFlag = (ImageView) row.findViewById(R.id.imageIcon);
            TextView descripcionTextGeneral = (TextView) row.findViewById(R.id.descripcionTextGeneral);

            if (descripcionTextGeneral != null)
                descripcionTextGeneral.setText(cargo.getCARGO());
        }

        return row;
    }
}
