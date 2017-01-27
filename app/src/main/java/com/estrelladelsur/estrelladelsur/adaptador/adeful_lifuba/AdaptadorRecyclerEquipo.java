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
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class AdaptadorRecyclerEquipo extends
        RecyclerView.Adapter<AdaptadorRecyclerEquipo.EquipoAdefulViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Equipo> datosEquipoArray;
    private Typeface nombreFont;
    private AuxiliarGeneral auxiliarGeneral;
    private Context context;

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

        public void bindTitular(Equipo equipoAdeful, Typeface nombre, Context context) {
            textRecyclerView.setText(equipoAdeful.getNOMBRE_EQUIPO());

            if (!equipoAdeful.getURL_ESCUDO().isEmpty())
                Picasso.with(context)
                        .load(equipoAdeful.getURL_ESCUDO()).fit()
                        .placeholder(R.mipmap.ic_escudo_cris)
                        .into(imageViewEscudo, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                imageViewEscudo.setImageResource(R.mipmap.ic_escudo_cris);
                            }
                        });
            else
                Picasso.with(context)
                        .load(R.mipmap.ic_escudo_cris).fit()
                        .placeholder(R.mipmap.ic_escudo_cris)
                        .into(imageViewEscudo);
        }
    }

    public AdaptadorRecyclerEquipo(ArrayList<Equipo> datosEquipoArray, Context context) {
        this.datosEquipoArray = datosEquipoArray;
        auxiliarGeneral = new AuxiliarGeneral(context);
        this.nombreFont = auxiliarGeneral.tituloFont(context);
        this.context = context;
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
        viewHolder.bindTitular(item, nombreFont, context);
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