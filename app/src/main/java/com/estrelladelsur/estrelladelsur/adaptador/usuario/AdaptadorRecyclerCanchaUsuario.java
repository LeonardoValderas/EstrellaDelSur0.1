package com.estrelladelsur.estrelladelsur.adaptador.usuario;

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
import com.estrelladelsur.estrelladelsur.entidad.Equipo;

import java.util.ArrayList;

public class AdaptadorRecyclerCanchaUsuario extends
        RecyclerView.Adapter<AdaptadorRecyclerCanchaUsuario.CanchaAdefulViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Cancha> canchaArrayList;
    private Typeface nombreFont;
    private AuxiliarGeneral auxiliarGeneral;

    public static class CanchaAdefulViewHolder extends RecyclerView.ViewHolder {

        private TextView textRecyclerView;
        private TextView textRecyclerView2;
        private ImageView imageViewEscudo;

        public CanchaAdefulViewHolder(View itemView) {
            super(itemView);

            textRecyclerView = (TextView) itemView
                    .findViewById(R.id.textRecyclerView);
            textRecyclerView2 = (TextView) itemView
                    .findViewById(R.id.textRecyclerView2);
            imageViewEscudo = (ImageView) itemView
                    .findViewById(R.id.imageViewEscudo);
        }

        public void bindTitular(Cancha cancha, Typeface nombre) {
            textRecyclerView.setText(cancha.getNOMBRE());
            textRecyclerView.setTypeface(nombre);
            textRecyclerView2.setVisibility(View.VISIBLE);
            textRecyclerView2.setText(cancha.getDIRECCION());
            imageViewEscudo.setImageResource(R.mipmap.ic_mapa_icon);
        }
    }

    public AdaptadorRecyclerCanchaUsuario(ArrayList<Cancha> canchaArrayList, Context context) {
        this.canchaArrayList = canchaArrayList;
        auxiliarGeneral = new AuxiliarGeneral(context);
        this.nombreFont = auxiliarGeneral.tituloFont(context);
    }

    @Override
    public CanchaAdefulViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                     int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_liga, viewGroup, false);
        itemView.setOnClickListener(this);
        CanchaAdefulViewHolder tvh = new CanchaAdefulViewHolder(itemView);
        return tvh;
    }

    @Override
    public void onBindViewHolder(CanchaAdefulViewHolder viewHolder, int pos) {
        Cancha item = canchaArrayList.get(pos);
        viewHolder.bindTitular(item, nombreFont);
    }

    @Override
    public int getItemCount() {
        return canchaArrayList.size();
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