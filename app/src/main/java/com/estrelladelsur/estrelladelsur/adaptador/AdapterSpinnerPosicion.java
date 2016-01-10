package com.estrelladelsur.estrelladelsur.adaptador;

import java.util.ArrayList;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.abstracta.Posicion;


public class AdapterSpinnerPosicion extends ArrayAdapter<Posicion>
    {
        private Activity context;
        ArrayList<Posicion> puestoArray = null;

        public AdapterSpinnerPosicion(Activity context, int resource, ArrayList<Posicion> puestoArray)
        {
            super(context, resource, puestoArray);
            this.context = context;
            this.puestoArray = puestoArray;
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
              //  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.spinner_item_general, parent, false);
            }

            Posicion puesto = puestoArray.get(position);

            if(puesto != null)
            {
                TextView spinnerGeneral = (TextView) row.findViewById(R.id.descripcionTextGeneral);

                if(spinnerGeneral != null)
                	spinnerGeneral.setText(puesto.getDESCRIPCION());

            }

            return row;
        }
    }

