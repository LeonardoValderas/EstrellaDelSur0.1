package com.estrelladelsur.estrelladelsur.adaptador;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;
import com.estrelladelsur.estrelladelsur.entidad.Permiso;

import java.util.ArrayList;

public class AdaptadorRecyclerPermiso extends
		RecyclerView.Adapter<AdaptadorRecyclerPermiso.PermisoViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Permiso> permisoArray;

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

		public void bindTitular(Permiso permiso) {
			textViewModulo.setText(permiso.getMODULO());
			checkBoxPermiso.setSelected(permiso.ISSELECTED());
		}
	}

	public AdaptadorRecyclerPermiso(ArrayList<Permiso> permisoArray) {
		this.permisoArray = permisoArray;
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
		//EntrenamientoXDivision item = permisoArray.get(pos);
		final int position = pos;

		viewHolder.textViewModulo.setText(permisoArray.get(position).getMODULO());

		viewHolder.checkBoxPermiso.setChecked(permisoArray.get(position).ISSELECTED());

		viewHolder.checkBoxPermiso.setTag(permisoArray.get(position));

		viewHolder.checkBoxPermiso.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				Entrenamiento entre = (Entrenamiento) cb.getTag();

				entre.setSelected(cb.isChecked());
				permisoArray.get(position).setISSELECTED(cb.isChecked());
			}
		});
	}

	@Override
	public int getItemCount() {
		return permisoArray.size();
	}

	public ArrayList<Permiso> getPermisoList() {
		  return permisoArray;
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