package com.estrelladelsur.estrelladelsur.adaptador;

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
import com.estrelladelsur.estrelladelsur.entidad.Equipo;

public class AdaptadorRecyclerEquipo extends
		RecyclerView.Adapter<AdaptadorRecyclerEquipo.EquipoAdefulViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Equipo> datosEquipoArray;

	public static class EquipoAdefulViewHolder extends RecyclerView.ViewHolder {

		private TextView textRecyclerView;
	    private ImageView imageViewEscudo;

		public EquipoAdefulViewHolder(View itemView) {
			super(itemView);

			textRecyclerView = (TextView) itemView
					.findViewById(R.id.textRecyclerView);
			imageViewEscudo = (ImageView) itemView
					.findViewById(R.id.imageViewEscudo);
		}

		public void bindTitular(Equipo equipoAdeful) {
			textRecyclerView.setText(equipoAdeful.getNOMBRE_EQUIPO());
			// txtSubtitulo.setText(t.getSubtitulo());

			byte[] image = equipoAdeful.getESCUDO();
			if (image == null) {
				 imageViewEscudo.setImageResource(R.mipmap.ic_escudo_equipo);
			} else {
				Bitmap theImage = BitmapFactory.decodeByteArray(
						equipoAdeful.getESCUDO(), 0,
						equipoAdeful.getESCUDO().length);
				theImage = Bitmap.createScaledBitmap(theImage, 150, 150, true);
				
				imageViewEscudo.setImageBitmap(theImage);
			}
		}
	}

	public AdaptadorRecyclerEquipo(ArrayList<Equipo> datosEquipoArray) {
		this.datosEquipoArray = datosEquipoArray;
		// notifyItemInserted(datosEquipoArray);
	}
	@Override
	public EquipoAdefulViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_liga, viewGroup, false);
		itemView.setOnClickListener(this);
		EquipoAdefulViewHolder tvh = new EquipoAdefulViewHolder(itemView);
		return tvh;
	}
	@Override
	public void onBindViewHolder(EquipoAdefulViewHolder viewHolder, int pos) {
		Equipo item = datosEquipoArray.get(pos);
		viewHolder.bindTitular(item);
	}
	@Override
	public int getItemCount() {
		return datosEquipoArray.size();
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