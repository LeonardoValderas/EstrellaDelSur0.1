package com.estrelladelsur.estrelladelsur.adaptador;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.EntrenamientoAsistencia;

import java.util.ArrayList;

public class AdaptadorRecyclerJugadorEntrenamiento extends
		RecyclerView.Adapter<AdaptadorRecyclerJugadorEntrenamiento.JugadorEntrenamientoViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<EntrenamientoAsistencia> jugadorArray;

	public static class JugadorEntrenamientoViewHolder extends RecyclerView.ViewHolder {

		private TextView textViewJugador;
		private CheckBox checkBoxEntrenamientoJugador;

			public JugadorEntrenamientoViewHolder(View itemView) {
			super(itemView);

			textViewJugador = (TextView) itemView
					.findViewById(R.id.editTextDivision);

			checkBoxEntrenamientoJugador = (CheckBox) itemView
					.findViewById(R.id.checkBoxEntrenamiento);
		}


		public void bindTitular(EntrenamientoAsistencia entrenamientoAsistenciaJuagador) {
			textViewJugador.setText(entrenamientoAsistenciaJuagador.getNOMBRE());
			checkBoxEntrenamientoJugador.setSelected(entrenamientoAsistenciaJuagador.isSelected());
		}
	}

	public AdaptadorRecyclerJugadorEntrenamiento(ArrayList<EntrenamientoAsistencia> jugadorArray) {
		this.jugadorArray = jugadorArray;
	}

	@Override
	public JugadorEntrenamientoViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_check_jugador, viewGroup, false);

		itemView.setOnClickListener(this);
		JugadorEntrenamientoViewHolder tvh = new JugadorEntrenamientoViewHolder(itemView);

		return tvh;
	}

	@Override
	public void onBindViewHolder(JugadorEntrenamientoViewHolder viewHolder, int pos) {
		//EntrenamientoXDivision item = divisionArray.get(pos);
		final int position = pos;

		viewHolder.textViewJugador.setText(jugadorArray.get(position).getNOMBRE());

		viewHolder.checkBoxEntrenamientoJugador.setChecked(jugadorArray.get(position).isSelected());

		viewHolder.checkBoxEntrenamientoJugador.setTag(jugadorArray.get(position));

		viewHolder.checkBoxEntrenamientoJugador.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				EntrenamientoAsistencia entre = (EntrenamientoAsistencia) cb.getTag();

				entre.setSelected(cb.isChecked());
				jugadorArray.get(position).setSelected(cb.isChecked());
			}
		});
	}

	@Override
	public int getItemCount() {
		return jugadorArray.size();
	}

	public ArrayList<EntrenamientoAsistencia> getEntrenamientoList() {
		  return jugadorArray;
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