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
import com.estrelladelsur.estrelladelsur.abstracta.JugadorRecycler;

public class AdaptadorEditarJugador extends
		RecyclerView.Adapter<AdaptadorEditarJugador.JugadorViewHolder> implements
		View.OnClickListener {

	// private final static Context context;

	private View.OnClickListener listener;
	private ArrayList<JugadorRecycler> jugadorArray;

	public static class JugadorViewHolder extends RecyclerView.ViewHolder {
		private ImageView imageViewEscudoL;
		private TextView textRecyclerViewEquipoL;
		private ImageView imageViewEscudoV;
		private TextView textRecyclerViewEquipoV;
		private TextView textRecyclerViewDia;
		private TextView textRecyclerViewHora;
		private TextView textRecyclerViewCancha;
		private TextView textRecyclerViewResultadoV;
		private TextView textRecyclerViewResultadoL;

		public JugadorViewHolder(View itemView) {
			super(itemView);

			// ESCUDO LOCAL
			imageViewEscudoL = (ImageView) itemView
					.findViewById(R.id.imageViewEscudoL);
			// ESCUDO VISITA
			imageViewEscudoV = (ImageView) itemView
					.findViewById(R.id.imageViewEscudoV);
			imageViewEscudoV.setVisibility(View.INVISIBLE);
			// EQUIPO LOCAL
			textRecyclerViewEquipoL = (TextView) itemView
					.findViewById(R.id.textRecyclerViewEquipoL);
			// EQUIPO VISITA
			textRecyclerViewEquipoV = (TextView) itemView
					.findViewById(R.id.textRecyclerViewEquipoV);
			textRecyclerViewEquipoV.setVisibility(View.INVISIBLE);
			// DIA
			textRecyclerViewDia = (TextView) itemView
					.findViewById(R.id.textRecyclerViewDia);
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
			textRecyclerViewResultadoL.setVisibility(View.INVISIBLE);
     		// RESULTADO VISITA
			textRecyclerViewResultadoV = (TextView) itemView
					.findViewById(R.id.textRecyclerViewResultadoV);
			textRecyclerViewResultadoV.setVisibility(View.INVISIBLE);
		}

		public void bindTitular(JugadorRecycler jugador) {
			// ESCUDO EQUIPO LOCAL
			byte[] fotoJugador = jugador.getFOTO_JUGADOR();
			if (fotoJugador == null) {

				imageViewEscudoL.setImageResource(R.mipmap.ic_escudo_equipo);
			} else {
				Bitmap fotoBitmap = BitmapFactory.decodeByteArray(
						jugador.getFOTO_JUGADOR(), 0,
						jugador.getFOTO_JUGADOR().length);

				fotoBitmap = Bitmap.createScaledBitmap(
						fotoBitmap, 150, 150, true);

				imageViewEscudoL.setImageBitmap(fotoBitmap);
			}
		
			textRecyclerViewEquipoL.setText(jugador.getNOMBRE_JUGADOR());
			//textRecyclerViewEquipoV.setText(jugador.getEQUIPO_VISITA());
			textRecyclerViewDia.setText(jugador.getNOMBRE_POSICION());
//			textRecyclerViewHora.setText(jugador.getHORA());
//			textRecyclerViewCancha.setText(jugador.getCANCHA());
		}
	}
	public AdaptadorEditarJugador(ArrayList<JugadorRecycler> jugadorArray) {
		this.jugadorArray = jugadorArray;
	}

	@Override
	public JugadorViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_fixture, viewGroup, false);

		itemView.setOnClickListener(this);
		JugadorViewHolder tvh = new JugadorViewHolder(itemView);

		return tvh;
	}

	@Override
	public void onBindViewHolder(JugadorViewHolder viewHolder, int pos) {
		JugadorRecycler item = jugadorArray.get(pos);

		viewHolder.bindTitular(item);
	}

	@Override
	public int getItemCount() {
		return jugadorArray.size();
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
