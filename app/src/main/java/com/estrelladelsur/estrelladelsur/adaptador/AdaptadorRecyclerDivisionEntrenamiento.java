package com.estrelladelsur.estrelladelsur.adaptador;

import java.util.ArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;

public class AdaptadorRecyclerDivisionEntrenamiento extends
		RecyclerView.Adapter<AdaptadorRecyclerDivisionEntrenamiento.DivisionViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Entrenamiento> divisionArray;

	public static class DivisionViewHolder extends RecyclerView.ViewHolder {

		private TextView textViewDivision;
		private CheckBox checkBoxEntrenamiento;

			public DivisionViewHolder(View itemView) {
			super(itemView);

			textViewDivision = (TextView) itemView
					.findViewById(R.id.editTextDivision);

			checkBoxEntrenamiento = (CheckBox) itemView
					.findViewById(R.id.checkBoxEntrenamiento);
		}

		public void bindTitular(Entrenamiento entrenamientoXDivision) {
			textViewDivision.setText(entrenamientoXDivision.getDESCRIPCION());
			checkBoxEntrenamiento.setSelected(entrenamientoXDivision.isSelected());
		}
	}

	public AdaptadorRecyclerDivisionEntrenamiento(ArrayList<Entrenamiento> divisionArray) {
		this.divisionArray = divisionArray;
	}
	@Override
	public DivisionViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_check, viewGroup, false);

		itemView.setOnClickListener(this);
		DivisionViewHolder tvh = new DivisionViewHolder(itemView);

		return tvh;
	}

	@Override
	public void onBindViewHolder(DivisionViewHolder viewHolder, int pos) {
		//EntrenamientoXDivision item = divisionArray.get(pos);
		final int position = pos;

		viewHolder.textViewDivision.setText(divisionArray.get(position).getDESCRIPCION());

		viewHolder.checkBoxEntrenamiento.setChecked(divisionArray.get(position).isSelected());

		viewHolder.checkBoxEntrenamiento.setTag(divisionArray.get(position));

		viewHolder.checkBoxEntrenamiento.setOnClickListener(new View.OnClickListener() {
			   public void onClick(View v) {
			    CheckBox cb = (CheckBox) v;
				   Entrenamiento entre = (Entrenamiento) cb.getTag();

			    entre.setSelected(cb.isChecked());
			    divisionArray.get(position).setSelected(cb.isChecked());
			   }
			  });
	}

	@Override
	public int getItemCount() {
		return divisionArray.size();
	}

	public ArrayList<Entrenamiento> getEntrenamientoList() {
		  return divisionArray;
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