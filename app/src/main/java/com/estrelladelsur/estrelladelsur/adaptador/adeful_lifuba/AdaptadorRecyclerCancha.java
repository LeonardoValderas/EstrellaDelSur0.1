package com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;

public class AdaptadorRecyclerCancha extends
		RecyclerView.Adapter<AdaptadorRecyclerCancha.CanchaViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Cancha> canchaArray;
	private Typeface nombreFont;
	private Typeface direFont;
	private AuxiliarGeneral auxiliarGeneral;
	public static class CanchaViewHolder extends RecyclerView.ViewHolder {

		private TextView textRecyclerView;
		private TextView textRecyclerView2;
		private ImageView imageViewEscudo;
		private ImageView imageViewMapa;

		public CanchaViewHolder(View itemView) {
			super(itemView);

			textRecyclerView = (TextView) itemView
					.findViewById(R.id.textRecyclerView);
			imageViewMapa = (ImageView) itemView
					.findViewById(R.id.imageViewMapa);
			imageViewMapa.setVisibility(View.VISIBLE);
			textRecyclerView2 = (TextView) itemView
					.findViewById(R.id.textRecyclerView2);
			textRecyclerView2.setVisibility(View.VISIBLE);
			imageViewEscudo = (ImageView) itemView
					.findViewById(R.id.imageViewEscudo);
			imageViewEscudo.setVisibility(View.GONE);
		}

		public void bindTitular(Cancha cancha, Typeface nombre, Typeface dire) {
			textRecyclerView.setText(cancha.getNOMBRE());
			//textRecyclerView.setTypeface(nombre, Typeface.BOLD);
			textRecyclerView2.setText(cancha.getDIRECCION());
			textRecyclerView2.setTextSize(15);
			textRecyclerView2.setTypeface(dire);
		}
	}

	public AdaptadorRecyclerCancha(ArrayList<Cancha> canchaArray, Context context) {
		this.canchaArray = canchaArray;
		auxiliarGeneral = new AuxiliarGeneral(context);
		direFont = auxiliarGeneral.textFont(context);
		nombreFont = auxiliarGeneral.tituloFont(context);
	}

	@Override
	public CanchaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_liga, viewGroup, false);

		itemView.setOnClickListener(this);
		CanchaViewHolder tvh = new CanchaViewHolder(itemView);

		return tvh;
	}

	@Override
	public void onBindViewHolder(CanchaViewHolder viewHolder, int pos) {
		Cancha item = canchaArray.get(pos);
		viewHolder.bindTitular(item, nombreFont,direFont);
	}
	@Override
	public int getItemCount() {
		return canchaArray.size();
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