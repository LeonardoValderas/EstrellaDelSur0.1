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
import com.estrelladelsur.estrelladelsur.entidad.Sancion;

import java.util.ArrayList;

public class AdaptadorEditarSancion extends
		RecyclerView.Adapter<AdaptadorEditarSancion.SancionViewHolder> implements
		View.OnClickListener {

	// private final static Context context;

	private View.OnClickListener listener;
	private ArrayList<Sancion> sancionArray;

	public static class SancionViewHolder extends RecyclerView.ViewHolder {
		private ImageView imageViewFotoJugador;
		private TextView textRecyclerViewNombre;
		private TextView textRecyclerViewTarjetas;
		private TextView textRecyclerViewFecha;
		private TextView textRecyclerViewObs;

		public SancionViewHolder(View itemView) {
			super(itemView);

			// FOTO JUGADOR
			imageViewFotoJugador = (ImageView) itemView
					.findViewById(R.id.imageViewJugador);
            // NOMBRE JUGADOR
			textRecyclerViewNombre = (TextView) itemView
					.findViewById(R.id.textRecyclerViewNombre);
		   // TARJETAS
			textRecyclerViewTarjetas = (TextView) itemView
					.findViewById(R.id.textRecyclerViewTarjetas);
			// FECHAS
			textRecyclerViewFecha = (TextView) itemView
					.findViewById(R.id.textRecyclerViewFechas);
			// OBSERVACIONES
			textRecyclerViewObs = (TextView) itemView
					.findViewById(R.id.textRecyclerViewObservacion);
		}

		public void bindTitular(Sancion sancion) {
			// Jugador
			byte[] foto = sancion.getFOTO_JUGADOR();
			if (foto == null) {
				imageViewFotoJugador.setImageResource(R.mipmap.ic_foto_galery);
			} else {
				Bitmap escudoLocalBitmap = BitmapFactory.decodeByteArray(
						foto, 0,
						foto.length);
				escudoLocalBitmap = Bitmap.createScaledBitmap(
						escudoLocalBitmap, 150, 150, true);
				imageViewFotoJugador.setImageBitmap(escudoLocalBitmap);
			}
			textRecyclerViewNombre.setText(sancion.getNOMBRE_JUGADOR());
			String tarjetas = "Amarrilas: "+sancion.getAMARILLA()+" Rojas: "+sancion.getROJA();
			textRecyclerViewTarjetas.setText(tarjetas);
			textRecyclerViewFecha.setText("Fechas: "+sancion.getFECHA_SUSPENSION());
			textRecyclerViewObs.setText("*"+sancion.getOBSERVACIONES());
		}
	}

	public AdaptadorEditarSancion(ArrayList<Sancion> sancionArray) {
		this.sancionArray = sancionArray;
	}

	@Override
	public SancionViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_sancion, viewGroup, false);
		itemView.setOnClickListener(this);
		SancionViewHolder tvh = new SancionViewHolder(itemView);
		return tvh;
	}

	@Override
	public void onBindViewHolder(SancionViewHolder viewHolder, int pos) {
		Sancion item = sancionArray.get(pos);
		viewHolder.bindTitular(item);
	}

	@Override
	public int getItemCount() {
		return sancionArray.size();
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
