package com.estrelladelsur.estrelladelsur.adaptador;

import java.util.ArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.abstracta.Cancha;

public class AdaptadorRecyclerCancha extends
		RecyclerView.Adapter<AdaptadorRecyclerCancha.CanchaViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Cancha> canchaArray;

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

		public void bindTitular(Cancha cancha) {
			textRecyclerView.setText(cancha.getNOMBRE());
			textRecyclerView2.setText(cancha.getDIRECCION());
		}
	}

	public AdaptadorRecyclerCancha(ArrayList<Cancha> canchaArray) {
		this.canchaArray = canchaArray;
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
		viewHolder.bindTitular(item);
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