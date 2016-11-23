package com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba;

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

public class AdaptadorRecyclerNotificacion extends
        RecyclerView.Adapter<AdaptadorRecyclerNotificacion.NotificacionViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Notificacion> notificacionArray;
    private Typeface editTextFont;
    private AuxiliarGeneral auxiliarGeneral;
    public static class NotificacionViewHolder extends RecyclerView.ViewHolder {

        private TextView textRecyclerViewTitulo;
        private TextView textRecyclerViewFecha;

        public NotificacionViewHolder(View itemView, Typeface type) {
            super(itemView);

            textRecyclerViewTitulo = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewTitulo);
            textRecyclerViewTitulo.setTypeface(type,Typeface.BOLD);
            textRecyclerViewFecha = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewFecha);
            textRecyclerViewFecha.setTypeface(type);
        }
        public void bindTitular(Notificacion notificacion, AuxiliarGeneral auxiliarGeneral) {
            textRecyclerViewTitulo.setText(notificacion.getTITULO());
            if(notificacion.getFECHA_ACTUALIZACION() != null && !notificacion.getFECHA_ACTUALIZACION().isEmpty()) {
                String fecha = notificacion.getFECHA_ACTUALIZACION().substring(0, 8);
                fecha = auxiliarGeneral.dateFormate(fecha);
                textRecyclerViewFecha.setText("ult.act: " + fecha);
            }
        }
    }
    public AdaptadorRecyclerNotificacion(ArrayList<Notificacion> notificacionArray, Context context) {
        this.notificacionArray = notificacionArray;

        auxiliarGeneral = new AuxiliarGeneral(context);
        this.editTextFont = auxiliarGeneral.textFont(context);
    }

    @Override
    public NotificacionViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                 int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_articulo, viewGroup, false);

        itemView.setOnClickListener(this);
        NotificacionViewHolder tvh = new NotificacionViewHolder(itemView, editTextFont);

        return tvh;
    }

    @Override
    public void onBindViewHolder(NotificacionViewHolder viewHolder, int pos) {
        Notificacion item = notificacionArray.get(pos);

        viewHolder.bindTitular(item, auxiliarGeneral);
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