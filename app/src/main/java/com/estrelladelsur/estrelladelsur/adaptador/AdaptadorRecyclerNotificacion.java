package com.estrelladelsur.estrelladelsur.adaptador;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Articulo;
import com.estrelladelsur.estrelladelsur.entidad.Notificacion;

import java.util.ArrayList;

public class AdaptadorRecyclerNotificacion extends
        RecyclerView.Adapter<AdaptadorRecyclerNotificacion.NotificacionViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Notificacion> notificacionArray;

    public static class NotificacionViewHolder extends RecyclerView.ViewHolder {

        private TextView textRecyclerViewTitulo;
        private TextView textRecyclerViewFecha;

        public NotificacionViewHolder(View itemView) {
            super(itemView);

            textRecyclerViewTitulo = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewTitulo);
            textRecyclerViewFecha = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewFecha);
        }
        public void bindTitular(Notificacion notificacion) {
            textRecyclerViewTitulo.setText(notificacion.getTITULO());
            textRecyclerViewFecha.setText("ult.act: "+notificacion.getFECHA_ACTUALIZACION());
        }
    }
    public AdaptadorRecyclerNotificacion(ArrayList<Notificacion> notificacionArray) {
        this.notificacionArray = notificacionArray;
    }

    @Override
    public NotificacionViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                 int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_articulo, viewGroup, false);

        itemView.setOnClickListener(this);
        NotificacionViewHolder tvh = new NotificacionViewHolder(itemView);

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