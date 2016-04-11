package com.estrelladelsur.estrelladelsur.adaptador;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.entidad.Usuario;

import java.util.ArrayList;

public class AdapterSpinnerUsuario extends ArrayAdapter<Usuario>
    {
        private Activity context;
        ArrayList<Usuario> usuarioArray = null;

        public AdapterSpinnerUsuario(Activity context, int resource, ArrayList<Usuario> usuarioArray)
        {
            super(context, resource, usuarioArray);
            this.context = context;
            this.usuarioArray = usuarioArray;
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
            Usuario usuario = usuarioArray.get(position);
            if(usuario != null)
            {
                TextView spinnerGeneral = (TextView) row.findViewById(R.id.descripcionTextGeneral);
                if(spinnerGeneral != null)
                	spinnerGeneral.setText(usuario.getUSUARIO());
            }
            return row;
        }
    }

