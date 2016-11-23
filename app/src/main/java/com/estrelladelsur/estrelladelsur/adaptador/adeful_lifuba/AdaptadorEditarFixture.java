package com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba;

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
import com.estrelladelsur.estrelladelsur.entidad.Fixture;

public class AdaptadorEditarFixture extends
		RecyclerView.Adapter<AdaptadorEditarFixture.FixtureViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Fixture> fixtureArray;

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
		private TextView textFecha;
		private TextView textAnio;

		public FixtureViewHolder(View itemView) {
			super(itemView);

			// EQUIPO LOCAL
			textFecha = (TextView) itemView
					.findViewById(R.id.textFecha);
			// EQUIPO LOCAL
			textAnio = (TextView) itemView
					.findViewById(R.id.textAnio);

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

		public void bindTitular(Fixture fixtureRecycler) {
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
			textRecyclerViewEquipoV.setText(fixtureRecycler.getEQUIPO_VISITA());
			textRecyclerViewDia.setText(fixtureRecycler.getDIA());
			textRecyclerViewHora.setText(fixtureRecycler.getHORA());
			textRecyclerViewCancha.setText(fixtureRecycler.getCANCHA());
			textAnio.setText(fixtureRecycler.getANIO());
			textFecha.setText(fixtureRecycler.getFECHA());
		}
	}

	public AdaptadorEditarFixture(ArrayList<Fixture> fixtureArray) {
		this.fixtureArray = fixtureArray;
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
		viewHolder.bindTitular(item);
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
