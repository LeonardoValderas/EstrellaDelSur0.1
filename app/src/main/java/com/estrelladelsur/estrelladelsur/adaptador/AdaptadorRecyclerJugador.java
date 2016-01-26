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
import com.estrelladelsur.estrelladelsur.entidad.JugadorRecycler;

public class AdaptadorRecyclerJugador extends
		RecyclerView.Adapter<AdaptadorRecyclerJugador.JugadorViewHolder> implements
		View.OnClickListener {

	// private final static Context context;

	private View.OnClickListener listener;
	private ArrayList<JugadorRecycler> jugadorArray;

	public static class JugadorViewHolder extends RecyclerView.ViewHolder {
		private TextView textRecyclerViewNombre;
		private TextView textRecyclerViewPosicion;
		private TextView textRecyclerViewDivision;
		private ImageView imageRecyclerViewFoto;
		private byte[] foto;
		private Bitmap fotoBitmap;

		public JugadorViewHolder(View itemView) {
			super(itemView);

			textRecyclerViewNombre = (TextView) itemView
					.findViewById(R.id.textRecyclerViewNombre);
			textRecyclerViewPosicion = (TextView) itemView
					.findViewById(R.id.textRecyclerViewCargo);
			textRecyclerViewDivision = (TextView) itemView
					.findViewById(R.id.textRecyclerViewPeriodo);
			imageRecyclerViewFoto = (ImageView) itemView
					.findViewById(R.id.imageRecyclerViewFoto);
		}

		public void bindTitular(JugadorRecycler jugador) {
			// ESCUDO EQUIPO LOCAL
			foto = jugador.getFOTO_JUGADOR();
			if (foto == null) {

				imageRecyclerViewFoto.setImageResource(R.mipmap.ic_foto_galery);
			} else {
				fotoBitmap = BitmapFactory.decodeByteArray(
						jugador.getFOTO_JUGADOR(), 0,
						jugador.getFOTO_JUGADOR().length);

				fotoBitmap = Bitmap.createScaledBitmap(
						fotoBitmap, 150, 150, true);

				imageRecyclerViewFoto.setImageBitmap(fotoBitmap);
			}
			textRecyclerViewNombre.setText(jugador.getNOMBRE_JUGADOR());
			textRecyclerViewPosicion.setText(jugador.getNOMBRE_POSICION());
			textRecyclerViewDivision.setText(jugador.getNOMBRE_DIVISION());
		}
	}
	public AdaptadorRecyclerJugador(ArrayList<JugadorRecycler> jugadorArray) {
		this.jugadorArray = jugadorArray;
	}

	@Override
	public JugadorViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_comision, viewGroup, false);

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
