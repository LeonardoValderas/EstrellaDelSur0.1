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
import com.estrelladelsur.estrelladelsur.entidad.Notificacion;

import java.util.ArrayList;

public class AdaptadorRecyclerNotificacionUsuario extends
        RecyclerView.Adapter<AdaptadorRecyclerNotificacionUsuario.NotificacionViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Notificacion> notificacionArray;
    private Typeface editTextFont;
    private AuxiliarGeneral auxiliarGeneral;

    public static class NotificacionViewHolder extends RecyclerView.ViewHolder {

        private TextView textRecyclerViewTitulo;

        public NotificacionViewHolder(View itemView, Typeface type) {
            super(itemView);

            textRecyclerViewTitulo = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewTitulo);
            textRecyclerViewTitulo.setTypeface(type,Typeface.BOLD);
        }
        public void bindTitular(Notificacion notificacion) {
            textRecyclerViewTitulo.setText(notificacion.getTITULO());
        }
    }

    public AdaptadorRecyclerNotificacionUsuario(ArrayList<Notificacion> notificacionArray, Context context) {
        this.notificacionArray = notificacionArray;
        auxiliarGeneral = new AuxiliarGeneral(context);
        editTextFont = auxiliarGeneral.tituloFont(context);
       }

    @Override
    public NotificacionViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                     int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_titulo, viewGroup, false);

        itemView.setOnClickListener(this);
        NotificacionViewHolder tvh = new NotificacionViewHolder(itemView,editTextFont);

        return tvh;
    }

    @Override
    public void onBindViewHolder(NotificacionViewHolder viewHolder, int pos) {
        Notificacion item = notificacionArray.get(pos);

        viewHolder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return notificacionArray.size();
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