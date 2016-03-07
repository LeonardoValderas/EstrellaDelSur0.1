package com.estrelladelsur.estrelladelsur.adaptador;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;
import com.estrelladelsur.estrelladelsur.entidad.EntrenamientoAsistencia;

import java.util.ArrayList;

public class AdaptadorRecyclerAsistencia extends
		RecyclerView.Adapter<AdaptadorRecyclerAsistencia.AsistenciaViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<EntrenamientoAsistencia> asistenciaArray;
	private ArrayList<EntrenamientoAsistencia> noAsistenciaArray;
	public static class AsistenciaViewHolder extends RecyclerView.ViewHolder {
		private TextView editTextDivision;
		private CheckBox checkBoxEntrenamiento;
		private CheckBox checkBoxEntrenamiento2;

		public AsistenciaViewHolder(View itemView) {
			super(itemView);

			// JUGADOR
			editTextDivision = (TextView) itemView
					.findViewById(R.id.editTextDivision);
			// SI
			checkBoxEntrenamiento = (CheckBox) itemView
					.findViewById(R.id.checkBoxEntrenamiento);
//			// NO
//			checkBoxEntrenamiento2 = (CheckBox) itemView
//					.findViewById(R.id.checkBoxEntrenamiento2);
//			checkBoxEntrenamiento2.setVisibility(View.VISIBLE);
		}


		public void bindTitular(EntrenamientoAsistencia entrenamientoAsistencia) {

			editTextDivision.setText(entrenamientoAsistencia.getNOMBRE());
			//checkBoxEntrenamiento.setText("SI");
			//checkBoxEntrenamiento2.setText("NO");
			checkBoxEntrenamiento.setSelected(entrenamientoAsistencia.isSelected());
		//	checkBoxEntrenamiento2.setSelected(entrenamientoAsistencia.isSelected());
		}
	}

	public AdaptadorRecyclerAsistencia(ArrayList<EntrenamientoAsistencia> asistenciaArray) {
		this.asistenciaArray = asistenciaArray;
		//this.noAsistenciaArray = asistenciaArray;
	}

	@Override
	public AsistenciaViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_check, viewGroup, false);
		itemView.setOnClickListener(this);
		AsistenciaViewHolder tvh = new AsistenciaViewHolder(itemView);

		return tvh;
	}

	@Override
	public void onBindViewHolder(AsistenciaViewHolder viewHolder, int pos) {

		final int position = pos;

		viewHolder.editTextDivision.setText(asistenciaArray.get(position).getNOMBRE());

		viewHolder.checkBoxEntrenamiento.setChecked(asistenciaArray.get(position).isSelected());

		viewHolder.checkBoxEntrenamiento.setTag(asistenciaArray.get(position));

		viewHolder.checkBoxEntrenamiento.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				EntrenamientoAsistencia entre = (EntrenamientoAsistencia) cb.getTag();

				entre.setSelected(cb.isChecked());
				asistenciaArray.get(position).setSelected(cb.isChecked());
			}
		});
//	    viewHolder.checkBoxEntrenamiento2.setChecked(asistenciaArray.get(position).isSelected());
//
//		viewHolder.checkBoxEntrenamiento2.setTag(asistenciaArray.get(position));
//
//		viewHolder.checkBoxEntrenamiento2.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				CheckBox cb = (CheckBox) v;
//				EntrenamientoAsistencia entre = (EntrenamientoAsistencia) cb.getTag();
//
//				entre.setSelected(cb.isChecked());
//				noAsistenciaArray.get(position).setSelected(cb.isChecked());
//			}
//		});

	}

	@Override
	public int getItemCount() {
		return asistenciaArray.size();
	}


	public ArrayList<EntrenamientoAsistencia> getJugadoresAsistenciaList() {
		return asistenciaArray;
	}
	//public ArrayList<EntrenamientoAsistencia> getJugadoresNoAsistenciaList() {
//		return noAsistenciaArray;
//	}
	public void setOnClickListener(View.OnClickListener listener) {
		this.listener = listener;
	}

	@Override
	public void onClick(View view) {
		if (listener != null)
			listener.onClick(view);
	}
}
