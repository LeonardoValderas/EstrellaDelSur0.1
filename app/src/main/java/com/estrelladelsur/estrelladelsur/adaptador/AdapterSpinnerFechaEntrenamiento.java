package com.estrelladelsur.estrelladelsur.adaptador;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.EntrenamientoRecycler;

import java.util.ArrayList;

public class AdapterSpinnerFechaEntrenamiento extends ArrayAdapter<EntrenamientoRecycler>
    {
        private Activity context;
        ArrayList<EntrenamientoRecycler> arrayEntrenamiento = null;

        public AdapterSpinnerFechaEntrenamiento(Activity context, int resource, ArrayList<EntrenamientoRecycler> arrayEntrenamiento)
        {
            super(context, resource, arrayEntrenamiento);
            this.context = context;
            this.arrayEntrenamiento = arrayEntrenamiento;
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

            EntrenamientoRecycler entrenamientoRecycler = arrayEntrenamiento.get(position);

            if(entrenamientoRecycler != null)
            {   // Parse the data from each object and set it.
                TextView spinnerGeneral = (TextView) row.findViewById(R.id.descripcionTextGeneral);
                if(spinnerGeneral != null)
                	spinnerGeneral.setText(entrenamientoRecycler.getDIAHORA().toString());
            }
            return row;
        }
    }

