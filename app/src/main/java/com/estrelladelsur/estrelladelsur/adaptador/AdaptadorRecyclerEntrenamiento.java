package com.estrelladelsur.estrelladelsur.adaptador;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.entidad.EntrenamientoRecycler;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento_Division;

import java.util.ArrayList;

public class AdaptadorRecyclerEntrenamiento extends
		RecyclerView.Adapter<AdaptadorRecyclerEntrenamiento.EntrenamientoViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<EntrenamientoRecycler> entrenamientoArray;
	//private ArrayList<Entrenamiento_Division> entrenamientoDivisionArray;
	private Context context;
	//private ControladorAdeful controladorAdeful;

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
        private String divisiones="";
		private ControladorAdeful controladorAdeful;
		private ArrayList<Entrenamiento_Division> entrenamientoDivisionArray;

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

			public void bindTitular(EntrenamientoRecycler entrenamientoRecycler,Context context) {

			textRecyclerViewEquipoL.setText(entrenamientoRecycler.getDIA().toString());
			textRecyclerViewEquipoV.setText(entrenamientoRecycler.getHORA().toString());
			textRecyclerViewDia.setText("División citada: ");
			controladorAdeful = new ControladorAdeful(context);
			controladorAdeful.abrirBaseDeDatos();
			entrenamientoDivisionArray =controladorAdeful.selectListaDivisionEntrenamientoAdefulId(entrenamientoRecycler.getID_ENTRENAMIENTO());
			if(entrenamientoDivisionArray != null) {
				for (int i = 0; i < entrenamientoDivisionArray.size(); i++) {
					divisiones += entrenamientoDivisionArray.get(i).getDESCRIPCION().toString() + " ";
				}

				controladorAdeful.cerrarBaseDeDatos();
			}else{
				controladorAdeful.cerrarBaseDeDatos();
				Toast.makeText(context, context.getResources().getString(R.string.error_data_base),
						Toast.LENGTH_SHORT).show();
			}
			textRecyclerViewHora.setText(divisiones);
			textRecyclerViewCancha.setText(entrenamientoRecycler.getNOMBRE().toString());
		}
	}
		public AdaptadorRecyclerEntrenamiento(ArrayList<EntrenamientoRecycler> entrenamientoArray, Context c) {
			this.entrenamientoArray = entrenamientoArray;
			this.context = c;
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
		viewHolder.bindTitular(item,context);
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
