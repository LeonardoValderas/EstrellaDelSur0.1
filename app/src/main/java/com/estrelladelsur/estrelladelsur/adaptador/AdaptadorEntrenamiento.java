package com.estrelladelsur.estrelladelsur.adaptador;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.EntrenamientoRecycler;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento_Division;
import com.estrelladelsur.estrelladelsur.entidad.FixtureRecycler;
import java.util.ArrayList;

public class AdaptadorEntrenamiento extends
		RecyclerView.Adapter<AdaptadorEntrenamiento.EntrenamientoViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<EntrenamientoRecycler> entrenamientoArray;
	private ArrayList<Entrenamiento_Division> entrenamientoDivisionArray;

	public static class EntrenamientoViewHolder extends RecyclerView.ViewHolder {
		private ImageView imageViewEscudoL;
		private TextView textRecyclerViewEquipoL;
		private ImageView imageViewEscudoV;
		private TextView textRecyclerViewEquipoV;
		private TextView textRecyclerViewDia;
		private TextView textRecyclerViewHora;
		private TextView textRecyclerViewCancha;
		private TextView textRecyclerViewResultadoV;
		private TextView textRecyclerViewResultadoL;
        private String divisiones;
		public EntrenamientoViewHolder(View itemView) {
			super(itemView);

			// ESCUDO LOCAL
			imageViewEscudoL = (ImageView) itemView
					.findViewById(R.id.imageViewEscudoL);
			imageViewEscudoL.setVisibility(View.GONE);
			// ESCUDO VISITA
			imageViewEscudoV = (ImageView) itemView
					.findViewById(R.id.imageViewEscudoV);
			imageViewEscudoV.setVisibility(View.GONE);
			// EQUIPO LOCAL
			textRecyclerViewEquipoL = (TextView) itemView
					.findViewById(R.id.textRecyclerViewEquipoL);
			// EQUIPO VISITA
			textRecyclerViewEquipoV = (TextView) itemView
					.findViewById(R.id.textRecyclerViewEquipoV);
			// DIA
			textRecyclerViewDia = (TextView) itemView
					.findViewById(R.id.textRecyclerViewDia);
			// HORA
			textRecyclerViewHora = (TextView) itemView
					.findViewById(R.id.textRecyclerViewHora);
			// CANCHA
			textRecyclerViewCancha = (TextView) itemView
					.findViewById(R.id.textRecyclerViewCancha);
			// RESULTADO LOCAL
			textRecyclerViewResultadoL = (TextView) itemView
					.findViewById(R.id.textRecyclerViewResultadoL);
			textRecyclerViewResultadoL.setVisibility(View.INVISIBLE);
     		// RESULTADO VISITA
			textRecyclerViewResultadoV = (TextView) itemView
					.findViewById(R.id.textRecyclerViewResultadoV);
			textRecyclerViewResultadoV.setVisibility(View.INVISIBLE);
		}

		//public void bindTitular(EntrenamientoRecycler entrenamientoRecycler,Entrenamiento_Division entrenamiento_division) {
			public void bindTitular(EntrenamientoRecycler entrenamientoRecycler) {

			textRecyclerViewEquipoL.setText(entrenamientoRecycler.getDIA().toString());
			textRecyclerViewEquipoV.setText(entrenamientoRecycler.getHORA().toString());
			textRecyclerViewDia.setText("División citada: ");

			for (int i = 0 ; i < entrenamientoRecycler.getENTRENAMIENTO_DIVISION().size() ; i ++) {
				divisiones = divisiones + entrenamientoRecycler.getENTRENAMIENTO_DIVISION().get(i).getDESCRIPCION().toString()+" ";
			}
				textRecyclerViewHora.setText(divisiones);
			textRecyclerViewCancha.setText(entrenamientoRecycler.getNOMBRE().toString());
		}
	}

//	public AdaptadorEntrenamiento(ArrayList<EntrenamientoRecycler> entrenamientoArray, ArrayList<Entrenamiento_Division> entrenamientoDivisionArray ) {
		public AdaptadorEntrenamiento(ArrayList<EntrenamientoRecycler> entrenamientoArray ) {
			this.entrenamientoArray = entrenamientoArray;
		//this.entrenamientoDivisionArray = entrenamientoDivisionArray;
	}

	@Override
	public EntrenamientoViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_fixture, viewGroup, false);
		itemView.setOnClickListener(this);
		EntrenamientoViewHolder tvh = new EntrenamientoViewHolder(itemView);
		return tvh;
	}

	@Override
	public void onBindViewHolder(EntrenamientoViewHolder viewHolder, int pos) {
		EntrenamientoRecycler item = entrenamientoArray.get(pos);
	//	Entrenamiento_Division item_division = entrenamientoDivisionArray.get(pos);
	//	viewHolder.bindTitular(item,item_division);
		viewHolder.bindTitular(item);
	}

	@Override
	public int getItemCount() {
		return entrenamientoArray.size();
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
