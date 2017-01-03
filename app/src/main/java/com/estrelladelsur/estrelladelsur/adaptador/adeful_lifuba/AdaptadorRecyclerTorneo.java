package com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;

public class AdaptadorRecyclerTorneo extends
		RecyclerView.Adapter<AdaptadorRecyclerTorneo.TorneoViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Torneo> torneoArray;
	private Typeface nombreFont;
	private AuxiliarGeneral auxiliarGeneral;

	public static class TorneoViewHolder extends RecyclerView.ViewHolder {

		private TextView textRecyclerView;
		private ImageView imageViewEscudo;
        private ImageView imageIsActual;
		private LinearLayout linearEscudo;

		public TorneoViewHolder(View itemView, Typeface nombre) {
			super(itemView);

			textRecyclerView = (TextView) itemView
					.findViewById(R.id.textRecyclerView);
			//textRecyclerView.setTypeface(nombre);

			linearEscudo = (LinearLayout) itemView
					.findViewById(R.id.linearEscudo);
			linearEscudo.setVisibility(View.GONE);

//			imageViewEscudo = (ImageView) itemView
//					.findViewById(R.id.imageViewEscudo);
//			imageViewEscudo.setVisibility(View.GONE);
			imageIsActual = (ImageView) itemView
					.findViewById(R.id.imageIsActual);
		}

		public void bindTitular(Torneo torneo) {
			textRecyclerView.setText(torneo.getDESCRIPCION());

			if(torneo.getACTUAL()){
				imageIsActual.setVisibility(View.VISIBLE);
			}
		}
	}

	public AdaptadorRecyclerTorneo(ArrayList<Torneo> torneoArray,Context context) {
		this.torneoArray = torneoArray;
		auxiliarGeneral = new AuxiliarGeneral(context);
		this.nombreFont = auxiliarGeneral.tituloFont(context);
	}

	@Override
	public TorneoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_liga, viewGroup, false);

		itemView.setOnClickListener(this);
		TorneoViewHolder tvh = new TorneoViewHolder(itemView,nombreFont);

		return tvh;
	}
	@Override
	public void onBindViewHolder(TorneoViewHolder viewHolder, int pos) {
		Torneo item = torneoArray.get(pos);

		viewHolder.bindTitular(item);
	}
	@Override
	public int getItemCount() {
		return torneoArray.size();
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