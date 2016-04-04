package com.estrelladelsur.estrelladelsur.adaptador;

import java.util.ArrayList;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;

public class AdapterSpinnerEquipo extends ArrayAdapter<Equipo>
    {
        private Activity context;
        ArrayList<Equipo> equipoArray = null;

        public AdapterSpinnerEquipo(Activity context, int resource, ArrayList<Equipo> equipoArray)
        {
            super(context, resource, equipoArray);
            this.context = context;
            this.equipoArray = equipoArray;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) 
        {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
            return super.getView(position, convertView, parent);   
        }

        
        public View getDropDownView(int position, View convertView, ViewGroup parent)
        {   // This view starts when we click the spinner.
            View row = convertView;
            if(row == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
              //  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.spinner_item_general, parent, false);
            }

            Equipo equipo = equipoArray.get(position);

            if(equipo != null)
            {   // Parse the data from each object and set it.
                 ImageView imageViewEscudo = (ImageView) row.findViewById(R.id.imageViewEscudo);
                 imageViewEscudo.setVisibility(View.VISIBLE);
                TextView spinnerGeneral = (TextView) row.findViewById(R.id.descripcionTextGeneral);
               
                if (equipo.getESCUDO() != null) {
					Bitmap theImage = BitmapFactory
							.decodeByteArray(
									equipo.getESCUDO(), 0,
									equipo.getESCUDO().length);
					imageViewEscudo.setImageBitmap(theImage);
				}else{

                    imageViewEscudo.setImageResource(R.mipmap.ic_escudo_equipo);
				}
                if(spinnerGeneral != null)
                	spinnerGeneral.setText(equipo.getNOMBRE_EQUIPO());
            }
            return row;
        }
    }

