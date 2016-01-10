package com.estrelladelsur.estrelladelsur.adaptador;

import java.util.ArrayList;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.abstracta.Torneo;


public class AdapterSpinnerTorneo extends ArrayAdapter<Torneo>
    {
        private Activity context;
        ArrayList<Torneo> torneoArray = null;

        public AdapterSpinnerTorneo(Activity context, int resource, ArrayList<Torneo> torneoArray)
        {
            super(context, resource, torneoArray);
            this.context = context;
            this.torneoArray = torneoArray;
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

            Torneo torneo = torneoArray.get(position);

            if(torneo != null)
            {   // Parse the data from each object and set it.
              // ImageView myFlag = (ImageView) row.findViewById(R.id.imageIcon);
                TextView spinnerGeneral = (TextView) row.findViewById(R.id.descripcionTextGeneral);
//                if(myFlag != null)
//                {
//                    myFlag.setBackgroundDrawable(getResources().getDrawable(item.getCountryFlag()));
//                }
                if(spinnerGeneral != null)
                	spinnerGeneral.setText(torneo.getDESCRIPCION());

            }

            return row;
        }
    }

