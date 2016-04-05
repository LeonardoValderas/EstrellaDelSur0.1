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
import com.estrelladelsur.estrelladelsur.entidad.Publicidad;

import java.util.ArrayList;

public class AdaptadorRecyclerPublicidad extends
		RecyclerView.Adapter<AdaptadorRecyclerPublicidad.PublicidadAdefulViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Publicidad> publicidadArray;

	public static class PublicidadAdefulViewHolder extends RecyclerView.ViewHolder {

		private TextView textRecyclerView;
	    private ImageView imageViewPublicidad;

		public PublicidadAdefulViewHolder(View itemView) {
			super(itemView);

			textRecyclerView = (TextView) itemView
					.findViewById(R.id.textRecyclerView);
			imageViewPublicidad = (ImageView) itemView
					.findViewById(R.id.imageViewEscudo);
		}

		public void bindTitular(Publicidad publicidadAdeful) {
			textRecyclerView.setText(publicidadAdeful.getTITULO());

			byte[] image = publicidadAdeful.getLOGO();
			if (image == null) {
				 imageViewPublicidad.setImageResource(R.mipmap.ic_foto);
			} else {
				Bitmap theImage = BitmapFactory.decodeByteArray(
						publicidadAdeful.getLOGO(), 0,
						publicidadAdeful.getLOGO().length);
				theImage = Bitmap.createScaledBitmap(theImage, 150, 150, true);

				imageViewPublicidad.setImageBitmap(theImage);
			}
		}
	}
	public AdaptadorRecyclerPublicidad(ArrayList<Publicidad> publicidadArray) {
		this.publicidadArray = publicidadArray;

	}
	@Override
	public PublicidadAdefulViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_liga, viewGroup, false);
		itemView.setOnClickListener(this);
		PublicidadAdefulViewHolder tvh = new PublicidadAdefulViewHolder(itemView);
		return tvh;
	}
	@Override
	public void onBindViewHolder(PublicidadAdefulViewHolder viewHolder, int pos) {
		Publicidad item = publicidadArray.get(pos);
		viewHolder.bindTitular(item);
	}
	@Override
	public int getItemCount() {
		return publicidadArray.size();
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