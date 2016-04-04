package com.estrelladelsur.estrelladelsur.adaptador;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;

import java.util.ArrayList;

public class AdapterSpinnerDivisionEntrenamiento extends ArrayAdapter<Entrenamiento>
    {
        private Activity context;
        ArrayList<Entrenamiento> divisionArray = null;

        public AdapterSpinnerDivisionEntrenamiento(Activity context, int resource, ArrayList<Entrenamiento> divisionArray)
        {
            super(context, resource, divisionArray);
            this.context = context;
            this.divisionArray = divisionArray;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) 
        {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
            return super.getView(position, convertView, parent);   
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent)
        {   // This view starts when we click the spinner.
            View row = convertView;
            if(row == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                row = inflater.inflate(R.layout.spinner_item_general, parent, false);
            }

            Entrenamiento division = divisionArray.get(position);

            if(division != null)
            {   // Parse the data from each object and set it.
                TextView spinnerGeneral = (TextView) row.findViewById(R.id.descripcionTextGeneral);
               if(spinnerGeneral != null)
                	spinnerGeneral.setText(division.getDESCRIPCION());
            }
            return row;
        }
    }

