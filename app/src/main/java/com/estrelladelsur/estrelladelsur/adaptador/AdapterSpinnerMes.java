package com.estrelladelsur.estrelladelsur.adaptador;

import java.util.ArrayList;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Mes;

public class AdapterSpinnerMes extends ArrayAdapter<Mes> {
	private Activity context;
	ArrayList<Mes> mesArray = null;

	public AdapterSpinnerMes(Activity context, int resource,
			ArrayList<Mes> mesArray) {
		super(context, resource, mesArray);
		this.context = context;
		this.mesArray = mesArray;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return super.getView(position, convertView, parent);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) { // This
		
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			row = inflater
					.inflate(R.layout.spinner_item_general, parent, false);
		}

		Mes mes = mesArray.get(position);

		if (mes != null) {
			TextView spinnerGeneral = (TextView) row
					.findViewById(R.id.descripcionTextGeneral);
			if (spinnerGeneral != null)
				spinnerGeneral.setText(mes.getMES());
		}
		return row;
	}
}
