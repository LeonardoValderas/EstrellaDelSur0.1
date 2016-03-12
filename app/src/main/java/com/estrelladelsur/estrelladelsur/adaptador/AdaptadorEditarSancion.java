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
		private ImageView imageViewEscudoV; // no se usa
		private TextView textRecyclerViewEquipoV; // no se usa
		private TextView textRecyclerViewTarjetas;
		private TextView textRecyclerViewFecha;
		private TextView textRecyclerViewObs;
		private TextView textRecyclerViewResultadoV;// no se usa
		private TextView textRecyclerViewResultadoL;// no se usa

		public SancionViewHolder(View itemView) {
			super(itemView);

			// FOTO JUGADOR
			imageViewFotoJugador = (ImageView) itemView
					.findViewById(R.id.imageViewEscudoL);
            // NOMBRE JUGADOR
			textRecyclerViewNombre = (TextView) itemView
					.findViewById(R.id.textRecyclerViewEquipoL);
		   // TARJETAS
			textRecyclerViewTarjetas = (TextView) itemView
					.findViewById(R.id.textRecyclerViewDia);
			// FECHAS
			textRecyclerViewFecha = (TextView) itemView
					.findViewById(R.id.textRecyclerViewHora);
			// OBSERVACIONES
			textRecyclerViewObs = (TextView) itemView
					.findViewById(R.id.textRecyclerViewCancha);
			// RESULTADO LOCAL
			textRecyclerViewResultadoL = (TextView) itemView
					.findViewById(R.id.textRecyclerViewResultadoL);
			textRecyclerViewResultadoL.setVisibility(View.GONE);
			// RESULTADO VISITA
			textRecyclerViewResultadoV = (TextView) itemView
					.findViewById(R.id.textRecyclerViewResultadoV);
			textRecyclerViewResultadoV.setVisibility(View.GONE);
			// ESCUDO VISITA
			imageViewEscudoV = (ImageView) itemView
					.findViewById(R.id.imageViewEscudoV);
			imageViewEscudoV.setVisibility(View.GONE);
			// EQUIPO VISITA
			textRecyclerViewEquipoV = (TextView) itemView
					.findViewById(R.id.textRecyclerViewEquipoV);
			textRecyclerViewEquipoV.setVisibility(View.GONE);
		}

		public void bindTitular(Sancion sancion) {
			// ESCUDO EQUIPO LOCAL
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
			String tarjetas = "Amarrilas: "+sancion.getAMARILLA()+" rojas: "+sancion.getROJA();
			textRecyclerViewTarjetas.setText(tarjetas);
			textRecyclerViewFecha.setText(sancion.getFECHA_ACTUALIZACION());
			textRecyclerViewObs.setText(sancion.getOBSERVACIONES());
		}
	}

	public AdaptadorEditarSancion(ArrayList<Sancion> sancionArray) {
		this.sancionArray = sancionArray;
	}

	@Override
	public SancionViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_fixture, viewGroup, false);
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
