package com.estrelladelsur.estrelladelsur.adaptador;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.ResultadoRecycler;

public class AdaptadorRecyclerResultado extends
		RecyclerView.Adapter<AdaptadorRecyclerResultado.FixtureViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<ResultadoRecycler> resultadoArray;

	public static class FixtureViewHolder extends RecyclerView.ViewHolder {
		private ImageView imageViewEscudoL;
		private TextView textRecyclerViewEquipoL;
		private ImageView imageViewEscudoV;
		private TextView textRecyclerViewEquipoV;
		private TextView textRecyclerViewDia;
		private TextView textRecyclerViewHora;
		private TextView textRecyclerViewCancha;
		private TextView textRecyclerViewResultadoV;
		private TextView textRecyclerViewResultadoL;

		public FixtureViewHolder(View itemView) {
			super(itemView);
			// ESCUDO LOCAL
			imageViewEscudoL = (ImageView) itemView
					.findViewById(R.id.imageViewEscudoL);
			// ESCUDO VISITA
			imageViewEscudoV = (ImageView) itemView
					.findViewById(R.id.imageViewEscudoV);
			// EQUIPO LOCAL
			textRecyclerViewEquipoL = (TextView) itemView
					.findViewById(R.id.textRecyclerViewEquipoL);
			// EQUIPO VISITA
			textRecyclerViewEquipoV = (TextView) itemView
					.findViewById(R.id.textRecyclerViewEquipoV);
			// DIA
			textRecyclerViewDia = (TextView) itemView
					.findViewById(R.id.textRecyclerViewDia);
			textRecyclerViewDia.setVisibility(View.INVISIBLE);
			// HORA
			textRecyclerViewHora = (TextView) itemView
					.findViewById(R.id.textRecyclerViewHora);
			textRecyclerViewHora.setVisibility(View.INVISIBLE);
			// CANCHA
			textRecyclerViewCancha = (TextView) itemView
					.findViewById(R.id.textRecyclerViewCancha);
			textRecyclerViewCancha.setVisibility(View.INVISIBLE);
			// RESULTADO LOCAL
			textRecyclerViewResultadoL = (TextView) itemView
					.findViewById(R.id.textRecyclerViewResultadoL);
			// RESULTADO VISITA
			textRecyclerViewResultadoV = (TextView) itemView
					.findViewById(R.id.textRecyclerViewResultadoV);

		}

    	public void bindTitular(ResultadoRecycler resultadoRecycler) {
			// ESCUDO EQUIPO LOCAL
			byte[] escudoLocal = resultadoRecycler.getESCUDOLOCAL();
			if (escudoLocal == null) {
				imageViewEscudoL.setImageResource(R.mipmap.ic_escudo_equipo);
			} else {
				Bitmap escudoLocalBitmap = BitmapFactory.decodeByteArray(
						resultadoRecycler.getESCUDOLOCAL(), 0,
						resultadoRecycler.getESCUDOLOCAL().length);
				escudoLocalBitmap = Bitmap.createScaledBitmap(
						escudoLocalBitmap, 150, 150, true);

				imageViewEscudoL.setImageBitmap(escudoLocalBitmap);
			}
			// ESCUDO EQUIPO VISITA
			byte[] escudovisita = resultadoRecycler.getESCUDOVISITA();
			if (escudovisita == null) {
				imageViewEscudoV.setImageResource(R.mipmap.ic_escudo_equipo);
			} else {
				Bitmap escudoVisitaBitmap = BitmapFactory.decodeByteArray(
						resultadoRecycler.getESCUDOVISITA(), 0,
						resultadoRecycler.getESCUDOVISITA().length);
				escudoVisitaBitmap = Bitmap.createScaledBitmap(
						escudoVisitaBitmap, 150, 150, true);

				imageViewEscudoV.setImageBitmap(escudoVisitaBitmap);
			}
			
			textRecyclerViewEquipoL.setText(resultadoRecycler
					.getEQUIPO_LOCAL());
			textRecyclerViewEquipoV.setText(resultadoRecycler
					.getEQUIPO_VISITA());

			if (resultadoRecycler.getRESULTADO_LOCAL() != null){
				textRecyclerViewResultadoL.setText(resultadoRecycler
						.getRESULTADO_LOCAL());
			}else{
				textRecyclerViewResultadoL.setText("-");
			}
			if (resultadoRecycler.getRESULTADO_VISITA()!=null){
				textRecyclerViewResultadoV.setText(resultadoRecycler
						.getRESULTADO_VISITA());
			}else{
				textRecyclerViewResultadoV.setText("-");
			}
		}
	}

	public AdaptadorRecyclerResultado(ArrayList<ResultadoRecycler> resultadoArray) {
		this.resultadoArray = resultadoArray;
	}

	@Override
	public FixtureViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_fixture, viewGroup, false);

		itemView.setOnClickListener(this);
		FixtureViewHolder tvh = new FixtureViewHolder(itemView);

		return tvh;
	}

	@Override
	public void onBindViewHolder(FixtureViewHolder viewHolder, int pos) {
		ResultadoRecycler item = resultadoArray.get(pos);
		viewHolder.bindTitular(item);
	}

	@Override
	public int getItemCount() {
		return resultadoArray.size();
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
