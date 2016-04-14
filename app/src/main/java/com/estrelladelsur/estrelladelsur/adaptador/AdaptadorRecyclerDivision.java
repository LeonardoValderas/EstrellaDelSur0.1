package com.estrelladelsur.estrelladelsur.adaptador;

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
import com.estrelladelsur.estrelladelsur.entidad.Division;

public class AdaptadorRecyclerDivision extends
		RecyclerView.Adapter<AdaptadorRecyclerDivision.DivisionViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Division> divisionArray;
	private Typeface nombreFont;
	private AuxiliarGeneral auxiliarGeneral;

	public static class DivisionViewHolder extends RecyclerView.ViewHolder {

		private TextView textRecyclerView;
		private LinearLayout linearEscudo;

		public DivisionViewHolder(View itemView, Typeface nombre) {
			super(itemView);

			textRecyclerView = (TextView) itemView
					.findViewById(R.id.textRecyclerView);
			textRecyclerView.setTypeface(nombre);
			linearEscudo = (LinearLayout) itemView.findViewById(
					R.id.linearEscudo);
			linearEscudo.setVisibility(View.GONE);
		}

		public void bindTitular(Division division) {
			textRecyclerView.setText(division.getDESCRIPCION());
		}
	}

	public AdaptadorRecyclerDivision(ArrayList<Division> divisionArray,Context context) {
		this.divisionArray = divisionArray;
		auxiliarGeneral = new AuxiliarGeneral(context);
		this.nombreFont = auxiliarGeneral.tituloFont(context);
	}

	@Override
	public DivisionViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_liga, viewGroup, false);

		itemView.setOnClickListener(this);
		DivisionViewHolder tvh = new DivisionViewHolder(itemView,nombreFont);
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