package com.estrelladelsur.estrelladelsur.adaptador.usuario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorSancionUsuario extends
		RecyclerView.Adapter<AdaptadorSancionUsuario.SancionViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Sancion> sancionArray;
	private Typeface textFont;
	private Typeface equipoFont;
	private AuxiliarGeneral auxiliarGeneral;
	private Context context;

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

		public void bindTitular(Sancion sancion, Typeface equipo, Typeface texto, Context context) {
			// Jugador
//			byte[] foto = sancion.getFOTO_JUGADOR();
//			if (foto == null) {
//				imageViewFotoJugador.setImageResource(R.mipmap.ic_foto_galery);
//			} else {
//				Bitmap escudoLocalBitmap = BitmapFactory.decodeByteArray(
//						foto, 0,
//						foto.length);
//				escudoLocalBitmap = Bitmap.createScaledBitmap(
//						escudoLocalBitmap, 150, 150, true);
//				imageViewFotoJugador.setImageBitmap(escudoLocalBitmap);
//			}

			if(!sancion.getFOTO_JUGADOR_URL().isEmpty())
				Picasso.with(context)
						.load(sancion.getFOTO_JUGADOR_URL()).fit()
						.placeholder(R.mipmap.ic_foto_galery)
						.into(imageViewFotoJugador, new Callback() {
							@Override
							public void onSuccess() {

							}

							@Override
							public void onError() {
								imageViewFotoJugador.setImageResource(R.mipmap.ic_foto_galery);
							}
						});
			else
				Picasso.with(context)
						.load(R.mipmap.ic_foto_galery).fit()
						.placeholder(R.mipmap.ic_foto_galery)
						.into(imageViewFotoJugador);

			textRecyclerViewNombre.setText(sancion.getNOMBRE_JUGADOR());
		//	textRecyclerViewNombre.setTypeface(texto, Typeface.BOLD);
			String tarjetas = "Amarrilas: "+sancion.getAMARILLA()+" Rojas: "+sancion.getROJA();
			textRecyclerViewTarjetas.setText(tarjetas);
			textRecyclerViewTarjetas.setTypeface(equipo);
			textRecyclerViewFecha.setText("Fecha Sanción: " + sancion.getFECHA_SUSPENSION());
			textRecyclerViewFecha.setTypeface(texto, Typeface.BOLD);
			textRecyclerViewObs.setText(sancion.getOBSERVACIONES());
			//textRecyclerViewObs.setTypeface(texto);
		}
	}

	public AdaptadorSancionUsuario(ArrayList<Sancion> sancionArray, Context context) {
		this.sancionArray = sancionArray;
		auxiliarGeneral = new AuxiliarGeneral(context);
		textFont = auxiliarGeneral.textFont(context);
		equipoFont = auxiliarGeneral.tituloFont(context);
		this.context = context;
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
		viewHolder.bindTitular(item,textFont, equipoFont, context);
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
