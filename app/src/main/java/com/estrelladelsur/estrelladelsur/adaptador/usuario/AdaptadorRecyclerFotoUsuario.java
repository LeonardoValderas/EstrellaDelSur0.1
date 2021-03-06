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
import com.estrelladelsur.estrelladelsur.entidad.Foto;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorRecyclerFotoUsuario extends
		RecyclerView.Adapter<AdaptadorRecyclerFotoUsuario.FotoViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Foto> fotoArray;
	private Typeface nombreFont;
	private Typeface cargoPeriodoFont;
	private AuxiliarGeneral auxiliarGeneral;
	private Context context;

	public static class FotoViewHolder extends RecyclerView.ViewHolder {
		private TextView tituloFoto;
		private ImageView imageFoto;
		private byte[] fotoByte;
		private Bitmap fotoBitmap;

		public FotoViewHolder(View itemView) {
			super(itemView);

			tituloFoto = (TextView) itemView
					.findViewById(R.id.tituloFoto);
			imageFoto = (ImageView) itemView
					.findViewById(R.id.imageFoto);
		}

		public void bindTitular(Foto foto, Typeface nombre, Typeface cargo, AuxiliarGeneral auxiliarGeneral, Context context) {
			// ESCUDO EQUIPO LOCAL
//			fotoByte = foto.getFOTO();
//			if (fotoByte == null) {
//				imageFoto.setImageResource(R.mipmap.ic_foto);
//			} else {
//
//				fotoBitmap = auxiliarGeneral.setByteToBitmap(fotoByte, 300, 300);
//				imageFoto.setImageBitmap(fotoBitmap);
//			}
			if(!foto.getURL_FOTO().isEmpty())
				Picasso.with(context)
						.load(foto.getURL_FOTO()).fit()
						.placeholder(R.mipmap.ic_foto)
						.into(imageFoto, new Callback() {
							@Override
							public void onSuccess() {

							}

							@Override
							public void onError() {
								imageFoto.setImageResource(R.mipmap.ic_foto);
							}
						});
			else
				Picasso.with(context)
						.load(R.mipmap.ic_foto).fit()
						.placeholder(R.mipmap.ic_foto)
						.into(imageFoto);

			tituloFoto.setText(foto.getTITULO());
		}
	}
	public AdaptadorRecyclerFotoUsuario(ArrayList<Foto> fotoArray, Context context) {
		this.fotoArray = fotoArray;
		auxiliarGeneral = new AuxiliarGeneral(context);
		this.cargoPeriodoFont = auxiliarGeneral.textFont(context);
		this.nombreFont = auxiliarGeneral.tituloFont(context);
		this.context = context;
	}

	@Override
	public FotoViewHolder onCreateViewHolder(ViewGroup viewGroup,
											 int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_foto_publicidad, viewGroup, false);

		itemView.setOnClickListener(this);
		FotoViewHolder tvh = new FotoViewHolder(itemView);

		return tvh;
	}

	@Override
	public void onBindViewHolder(FotoViewHolder viewHolder, int pos) {
		Foto item = fotoArray.get(pos);

		viewHolder.bindTitular(item, nombreFont, cargoPeriodoFont, auxiliarGeneral, context);
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
