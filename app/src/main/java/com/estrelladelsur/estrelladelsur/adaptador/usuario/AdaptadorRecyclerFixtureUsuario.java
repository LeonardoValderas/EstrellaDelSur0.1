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
import com.estrelladelsur.estrelladelsur.entidad.Fixture;

import java.util.ArrayList;

public class AdaptadorRecyclerFixtureUsuario extends
		RecyclerView.Adapter<AdaptadorRecyclerFixtureUsuario.FixtureViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Fixture> fixtureArray;
	private Typeface textFont;
	private Typeface equipoFont;
	private AuxiliarGeneral auxiliarGeneral;

	public static class FixtureViewHolder extends RecyclerView.ViewHolder {
		private ImageView imageViewEscudoL;
		private TextView textRecyclerViewEquipoL;
		private ImageView imageViewEscudoV;
		private TextView textRecyclerViewEquipoV;
		private TextView textRecyclerViewFecha;
		private TextView textRecyclerViewAnio;
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
			// HORA
			textRecyclerViewHora = (TextView) itemView
					.findViewById(R.id.textRecyclerViewHora);
			textRecyclerViewFecha = (TextView) itemView
					.findViewById(R.id.textFecha);
		    textRecyclerViewAnio = (TextView) itemView
					.findViewById(R.id.textAnio);
			// CANCHA
			textRecyclerViewCancha = (TextView) itemView
					.findViewById(R.id.textRecyclerViewCancha);
			// RESULTADO LOCAL
			textRecyclerViewResultadoL = (TextView) itemView
					.findViewById(R.id.textRecyclerViewResultadoL);
			//textRecyclerViewResultadoL.setVisibility(View.INVISIBLE);
     		// RESULTADO VISITA
			textRecyclerViewResultadoV = (TextView) itemView
					.findViewById(R.id.textRecyclerViewResultadoV);
			//textRecyclerViewResultadoV.setVisibility(View.INVISIBLE);
		}


		public void bindTitular(Fixture fixtureRecycler , Typeface equipo, Typeface texto) {
			// ESCUDO EQUIPO LOCAL
			byte[] escudoLocal = fixtureRecycler.getESCUDOLOCAL();
			if (escudoLocal == null) {
				imageViewEscudoL.setImageResource(R.mipmap.ic_escudo_cris);
			} else {
				Bitmap escudoLocalBitmap = BitmapFactory.decodeByteArray(
						fixtureRecycler.getESCUDOLOCAL(), 0,
						fixtureRecycler.getESCUDOLOCAL().length);
				escudoLocalBitmap = Bitmap.createScaledBitmap(
						escudoLocalBitmap, 150, 150, true);
				imageViewEscudoL.setImageBitmap(escudoLocalBitmap);
			}
			// ESCUDO EQUIPO VISITA
			byte[] escudovisita = fixtureRecycler.getESCUDOVISITA();
			if (escudovisita == null) {
				imageViewEscudoV.setImageResource(R.mipmap.ic_escudo_cris);
			} else {
				Bitmap escudoVisitaBitmap = BitmapFactory.decodeByteArray(
						fixtureRecycler.getESCUDOVISITA(), 0,
						fixtureRecycler.getESCUDOVISITA().length);
				escudoVisitaBitmap = Bitmap.createScaledBitmap(
						escudoVisitaBitmap, 150, 150, true);
				imageViewEscudoV.setImageBitmap(escudoVisitaBitmap);
			}

			textRecyclerViewEquipoL.setText(fixtureRecycler.getEQUIPO_LOCAL());
			textRecyclerViewEquipoL.setTypeface(equipo, Typeface.BOLD);
			textRecyclerViewEquipoV.setText(fixtureRecycler.getEQUIPO_VISITA());
			textRecyclerViewEquipoV.setTypeface(equipo, Typeface.BOLD);
			textRecyclerViewDia.setText(fixtureRecycler.getDIA());
			textRecyclerViewDia.setTypeface(texto);
			textRecyclerViewHora.setText(fixtureRecycler.getHORA());
			textRecyclerViewHora.setTypeface(texto);
			textRecyclerViewFecha.setText(fixtureRecycler.getFECHA());
			textRecyclerViewAnio.setText(fixtureRecycler.getANIO());
			textRecyclerViewCancha.setText(fixtureRecycler.getCANCHA());
			textRecyclerViewCancha.setTypeface(texto,Typeface.BOLD);

			if(fixtureRecycler.getRESULTADO_LOCAL() != null){
				textRecyclerViewResultadoL.setText(fixtureRecycler.getRESULTADO_LOCAL());
				textRecyclerViewResultadoL.setTypeface(texto,Typeface.BOLD);
			}else{
				textRecyclerViewResultadoL.setText("-");
				textRecyclerViewResultadoL.setTypeface(texto, Typeface.BOLD);
			}
			if(fixtureRecycler.getRESULTADO_VISITA() != null){
				textRecyclerViewResultadoV.setText(fixtureRecycler.getRESULTADO_VISITA());
				textRecyclerViewResultadoV.setTypeface(texto, Typeface.BOLD);
			}else{
				textRecyclerViewResultadoV.setText("-");
				textRecyclerViewResultadoV.setTypeface(texto, Typeface.BOLD);
			}
		}
	}
	public AdaptadorRecyclerFixtureUsuario(ArrayList<Fixture> fixtureArray, Context context) {
		this.fixtureArray = fixtureArray;
		auxiliarGeneral = new AuxiliarGeneral(context);
		textFont = auxiliarGeneral.textFont(context);
		equipoFont = auxiliarGeneral.tituloFont(context);
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
		Fixture item = fixtureArray.get(pos);
		viewHolder.bindTitular(item,textFont, equipoFont);
	}

	@Override
	public int getItemCount() {
		return fixtureArray.size();
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
