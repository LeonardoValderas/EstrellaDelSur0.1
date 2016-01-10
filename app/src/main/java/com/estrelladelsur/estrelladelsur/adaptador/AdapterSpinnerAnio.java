package com.estrelladelsur.estrelladelsur.adaptador;

import java.util.ArrayList;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.abstracta.Anio;


public class AdapterSpinnerAnio extends ArrayAdapter<Anio>
    {
        private Activity context;
        ArrayList<Anio> anioArray = null;

        public AdapterSpinnerAnio(Activity context, int resource, ArrayList<Anio> anioArray)
        {
            super(context, resource, anioArray);
            this.context = context;
            this.anioArray = anioArray;
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

            Anio anio = anioArray.get(position);

            if(anio != null)
            {   // Parse the data from each object and set it.

                TextView spinnerGeneral = (TextView) row.findViewById(R.id.descripcionTextGeneral);
                if(spinnerGeneral != null)
                	spinnerGeneral.setText(anio.getANIO());
            }
            return row;
        }
    }

