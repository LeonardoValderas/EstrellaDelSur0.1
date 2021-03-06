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
import com.estrelladelsur.estrelladelsur.entidad.Publicidad;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorRecyclerPublicidadUsuario extends
		RecyclerView.Adapter<AdaptadorRecyclerPublicidadUsuario.FotoViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Publicidad> publicidadArray;
	private Typeface nombreFont;
	private Typeface cargoPeriodoFont;
	private AuxiliarGeneral auxiliarGeneral;
	private Context context;

	public static class FotoViewHolder extends RecyclerView.ViewHolder {
		private TextView tituloFoto;
		private TextView publicidadOtros;
		private ImageView imageFoto;
		private byte[] fotoByte;
		private Bitmap fotoBitmap;

		public FotoViewHolder(View itemView) {
			super(itemView);

			tituloFoto = (TextView) itemView
					.findViewById(R.id.tituloFoto);
			publicidadOtros = (TextView) itemView
					.findViewById(R.id.publicidadOtros);
			publicidadOtros.setVisibility(View.VISIBLE);
			imageFoto = (ImageView) itemView
					.findViewById(R.id.imageFoto);
		}

		public void bindTitular(Publicidad publicidad, Typeface nombre, Typeface cargo, AuxiliarGeneral auxiliarGeneral, Context context) {

			// ESCUDO EQUIPO LOCAL
//			fotoByte = publicidad.getLOGO();
//			if (fotoByte == null) {
//				imageFoto.setImageResource(R.mipmap.ic_foto);
//			} else {
//
//				fotoBitmap = auxiliarGeneral.setByteToBitmap(fotoByte, 300, 300);
//     			imageFoto.setImageBitmap(fotoBitmap);
//			}

			if(!publicidad.getURL_FOTO().isEmpty())
				Picasso.with(context)
						.load(publicidad.getURL_FOTO()).fit()
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

			tituloFoto.setText(publicidad.getTITULO());
			publicidadOtros.setText(publicidad.getOTROS());
		}
	}
	public AdaptadorRecyclerPublicidadUsuario(ArrayList<Publicidad> publicidadArray, Context context) {
		this.publicidadArray = publicidadArray;
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
		Publicidad item = publicidadArray.get(pos);

		viewHolder.bindTitular(item,nombreFont, cargoPeriodoFont, auxiliarGeneral, context);
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
