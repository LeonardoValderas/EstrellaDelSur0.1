package com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.entidad.SubModulo;
import java.util.ArrayList;

public class AdaptadorRecyclerCrearPermiso extends
		RecyclerView.Adapter<AdaptadorRecyclerCrearPermiso.PermisoViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<SubModulo> submoduloArray;
	private ArrayList<SubModulo> submoduloTrueArray = new ArrayList<SubModulo>();
	private ArrayList<SubModulo> submoduloFalseArray = new ArrayList<SubModulo>();
    private Typeface moduloFont;
	private AuxiliarGeneral auxiliarGeneral;
	public static class PermisoViewHolder extends RecyclerView.ViewHolder {

		private TextView textViewModulo;
		private CheckBox checkBoxPermiso;

			public PermisoViewHolder(View itemView) {
			super(itemView);

			textViewModulo = (TextView) itemView
					.findViewById(R.id.editTextDivision);

			checkBoxPermiso = (CheckBox) itemView
					.findViewById(R.id.checkBoxEntrenamiento);
		}

		public void bindTitular(SubModulo subModulo) {
			textViewModulo.setText(subModulo.getSUBMODULO());
			checkBoxPermiso.setSelected(subModulo.ISSELECTED());
		}
	}

	public AdaptadorRecyclerCrearPermiso(ArrayList<SubModulo> submoduloArray, Context context) {
		this.submoduloArray = submoduloArray;
		this.auxiliarGeneral = new AuxiliarGeneral(context);
		moduloFont = auxiliarGeneral.tituloFont(context);
		for (int i = 0; i <submoduloArray.size() ; i++) {
			if(submoduloArray.get(i).ISSELECTED()) {
				submoduloTrueArray.add(submoduloArray.get(i));
			}else{
				submoduloFalseArray.add(submoduloArray.get(i));
			}
		}
	}
	@Override
	public PermisoViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_check, viewGroup, false);

		itemView.setOnClickListener(this);
		PermisoViewHolder tvh = new PermisoViewHolder(itemView);

		return tvh;
	}

	@Override
	public void onBindViewHolder(PermisoViewHolder viewHolder, int pos) {

		final int position = pos;

		viewHolder.textViewModulo.setText(submoduloArray.get(position).getSUBMODULO());
		viewHolder.textViewModulo.setTypeface(moduloFont);
		viewHolder.checkBoxPermiso.setChecked(submoduloArray.get(position).ISSELECTED());
		viewHolder.checkBoxPermiso.setTag(submoduloArray.get(position));
		viewHolder.checkBoxPermiso.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				SubModulo perm = (SubModulo) cb.getTag();

				perm.setISSELECTED(cb.isChecked());
				submoduloArray.get(position).setISSELECTED(cb.isChecked());
				if(submoduloArray.get(position).ISSELECTED()) {
					submoduloTrueArray.add(submoduloArray.get(position));
					submoduloFalseArray.remove(submoduloArray.get(position));
				}else{
					submoduloFalseArray.add(submoduloArray.get(position));
					submoduloTrueArray.remove(submoduloArray.get(position));
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return submoduloArray.size();
	}

	public ArrayList<SubModulo> getPermisoList() {
		  return submoduloArray;
		 }

	public ArrayList<SubModulo> getSubmoduloTrueArray() {
		return submoduloTrueArray;
	}
	public ArrayList<SubModulo> getSubmoduloFalseArray() {
		return submoduloFalseArray;
	}
	public void setOnClickListener(View.OnClickListener listener) {
		this.listener = listener;
	}

	@Override
	public void onClick(View view) {
		if (listener != null)
			listener.onClick(view);
	}
}