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
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.entidad.Foto;

import java.util.ArrayList;

public class AdaptadorRecyclerFoto extends
		RecyclerView.Adapter<AdaptadorRecyclerFoto.FotoAdefulViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Foto> fotoArray;

	public static class FotoAdefulViewHolder extends RecyclerView.ViewHolder {

		private TextView textRecyclerView;
	    private ImageView imageViewFoto;

		public FotoAdefulViewHolder(View itemView) {
			super(itemView);

			textRecyclerView = (TextView) itemView
					.findViewById(R.id.textRecyclerView);
			imageViewFoto = (ImageView) itemView
					.findViewById(R.id.imageViewEscudo);
		}

		public void bindTitular(Foto fotoAdeful) {
			textRecyclerView.setText(fotoAdeful.getTITULO());
			// txtSubtitulo.setText(t.getSubtitulo());

			byte[] image = fotoAdeful.getFOTO();
			if (image == null) {
				 imageViewFoto.setImageResource(R.mipmap.ic_foto);
			} else {
				Bitmap theImage = BitmapFactory.decodeByteArray(
						fotoAdeful.getFOTO(), 0,
						fotoAdeful.getFOTO().length);
				theImage = Bitmap.createScaledBitmap(theImage, 150, 150, true);

				imageViewFoto.setImageBitmap(theImage);
			}
		}
	}

	public AdaptadorRecyclerFoto(ArrayList<Foto> fotoArray) {
		this.fotoArray = fotoArray;
		// notifyItemInserted(fotoArray);
	}
	@Override
	public FotoAdefulViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_liga, viewGroup, false);
		itemView.setOnClickListener(this);
		FotoAdefulViewHolder tvh = new FotoAdefulViewHolder(itemView);
		return tvh;
	}
	@Override
	public void onBindViewHolder(FotoAdefulViewHolder viewHolder, int pos) {
		Foto item = fotoArray.get(pos);
		viewHolder.bindTitular(item);
	}
	@Override
	public int getItemCount() {
		return fotoArray.size();
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