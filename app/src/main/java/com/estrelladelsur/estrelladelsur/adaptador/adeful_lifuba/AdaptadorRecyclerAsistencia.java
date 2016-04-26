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
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;
import java.util.ArrayList;

public class AdaptadorRecyclerAsistencia extends
		RecyclerView.Adapter<AdaptadorRecyclerAsistencia.AsistenciaViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Entrenamiento> asistenciaArray;
	private ArrayList<Entrenamiento> asistenciaTrueArray = new ArrayList<Entrenamiento>();
	private Typeface textFont;
	private Typeface equipoFont;
	private AuxiliarGeneral auxiliarGeneral;

	public static class AsistenciaViewHolder extends RecyclerView.ViewHolder {
		private TextView editTextDivision;
		private TextView textViewDivision;
		private CheckBox checkBoxEntrenamiento;



		public AsistenciaViewHolder(View itemView) {
			super(itemView);

			// JUGADOR
			editTextDivision = (TextView) itemView
					.findViewById(R.id.editTextDivision);
			// SI
			checkBoxEntrenamiento = (CheckBox) itemView
					.findViewById(R.id.checkBoxEntrenamiento);
			//DIVISION
			textViewDivision = (TextView) itemView
					.findViewById(R.id.textViewDivision);
			textViewDivision.setVisibility(View.VISIBLE);
		}


		public void bindTitular(Entrenamiento entrenamientoAsistencia, Typeface equipo, Typeface texto) {
			editTextDivision.setText(entrenamientoAsistencia.getNOMBRE());
			checkBoxEntrenamiento.setSelected(entrenamientoAsistencia.isSelected());
			textViewDivision.setText(entrenamientoAsistencia.getDESCRIPCION());
		}
	}

	public AdaptadorRecyclerAsistencia(ArrayList<Entrenamiento> asistenciaArray, Context context) {
		this.asistenciaArray = asistenciaArray;
		auxiliarGeneral = new AuxiliarGeneral(context);
		textFont = auxiliarGeneral.textFont(context);
		equipoFont = auxiliarGeneral.tituloFont(context);
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
		viewHolder.editTextDivision.setTypeface(equipoFont, Typeface.BOLD);
		viewHolder.textViewDivision.setText(asistenciaArray.get(position).getDESCRIPCION());
		viewHolder.textViewDivision.setTypeface(textFont, Typeface.BOLD);

		viewHolder.checkBoxEntrenamiento.setChecked(asistenciaArray.get(position).isSelected());
        viewHolder.checkBoxEntrenamiento.setTag(asistenciaArray.get(position));
		viewHolder.checkBoxEntrenamiento.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				Entrenamiento entre = (Entrenamiento) cb.getTag();

				entre.setSelected(cb.isChecked());
				asistenciaArray.get(position).setSelected(cb.isChecked());
			}
		});
	}

	@Override
	public int getItemCount() {
		return asistenciaArray.size();
	}
	public ArrayList<Entrenamiento> getJugadoresAsistenciaList() {
		return asistenciaArray;
	}
	public ArrayList<Entrenamiento> getJugadoresTrueAsistenciaList() {
		for (int i = 0; i < asistenciaArray.size(); i++) {

			if(asistenciaArray.get(i).isSelected()==true){
				asistenciaTrueArray.add(asistenciaArray.get(i));
			}
		}
		return asistenciaTrueArray;
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
