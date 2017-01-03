package com.estrelladelsur.estrelladelsur.adaptador.usuario;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Noticia;
import com.estrelladelsur.estrelladelsur.entidad.Notificacion;

import java.util.ArrayList;

public class AdaptadorRecyclerNoticiaUsuario extends
        RecyclerView.Adapter<AdaptadorRecyclerNoticiaUsuario.NoticiaViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Noticia> noticiaArrayList;
    private Typeface editTextFont;
    private AuxiliarGeneral auxiliarGeneral;

    public static class NoticiaViewHolder extends RecyclerView.ViewHolder {

        private TextView textRecyclerViewTitulo;

        public NoticiaViewHolder(View itemView, Typeface type) {
            super(itemView);

            textRecyclerViewTitulo = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewTitulo);
         //   textRecyclerViewTitulo.setTypeface(type,Typeface.BOLD);
        }
        public void bindTitular(Noticia noticia) {
            textRecyclerViewTitulo.setText(noticia.getTITULO());
        }
    }

    public AdaptadorRecyclerNoticiaUsuario(ArrayList<Noticia> noticiaArray, Context context) {
        this.noticiaArrayList = noticiaArray;
        auxiliarGeneral = new AuxiliarGeneral(context);
        editTextFont = auxiliarGeneral.tituloFont(context);
       }

    @Override
    public NoticiaViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_titulo, viewGroup, false);

        itemView.setOnClickListener(this);
        NoticiaViewHolder tvh = new NoticiaViewHolder(itemView,editTextFont);

        return tvh;
    }

    @Override
    public void onBindViewHolder(NoticiaViewHolder viewHolder, int pos) {
        Noticia item = noticiaArrayList.get(pos);

        viewHolder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return noticiaArrayList.size();
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