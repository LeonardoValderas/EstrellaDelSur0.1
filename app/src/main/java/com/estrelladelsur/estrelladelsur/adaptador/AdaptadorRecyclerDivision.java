package com.estrelladelsur.estrelladelsur.adaptador;

import java.util.ArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.abstracta.Division;

public class AdaptadorRecyclerDivision extends
		RecyclerView.Adapter<AdaptadorRecyclerDivision.DivisionViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Division> divisionArray;

	public static class DivisionViewHolder extends RecyclerView.ViewHolder {

		private TextView textRecyclerView;
		private ImageView imageViewEscudo;

		public DivisionViewHolder(View itemView) {
			super(itemView);

			textRecyclerView = (TextView) itemView
					.findViewById(R.id.textRecyclerView);
			imageViewEscudo = (ImageView) itemView
					.findViewById(R.id.imageViewEscudo);
			imageViewEscudo.setVisibility(View.GONE);
		}

		public void bindTitular(Division division) {
			textRecyclerView.setText(division.getDESCRIPCION());
		}
	}

	public AdaptadorRecyclerDivision(ArrayList<Division> divisionArray) {
		this.divisionArray = divisionArray;
	}

	@Override
	public DivisionViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_liga, viewGroup, false);

		itemView.setOnClickListener(this);
		DivisionViewHolder tvh = new DivisionViewHolder(itemView);
		return tvh;
	}

	@Override
	public void onBindViewHolder(DivisionViewHolder viewHolder, int pos) {
		Division item = divisionArray.get(pos);
		viewHolder.bindTitular(item);
	}

	@Override
	public int getItemCount() {
		return divisionArray.size();
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