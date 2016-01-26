package com.estrelladelsur.estrelladelsur.adaptador;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;


public class AdapterSpinnerCancha extends ArrayAdapter<Cancha>
    {
        private Activity context;
        ArrayList<Cancha> canchaArray = null;

        public AdapterSpinnerCancha(Activity context, int resource, ArrayList<Cancha> canchaArray)
        {
            super(context, resource, canchaArray);
            this.context = context;
            this.canchaArray = canchaArray;
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

            Cancha cancha = canchaArray.get(position);

            if(cancha != null)
            {   // Parse the data from each object and set it.

                TextView spinnerGeneral = (TextView) row.findViewById(R.id.descripcionTextGeneral);
                if(spinnerGeneral != null)
                	spinnerGeneral.setText(cancha.getNOMBRE());
            }
            return row;
        }
    }

