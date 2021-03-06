package com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba;

import java.util.ArrayList;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Articulo;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdaptadorRecyclerArticulo extends
        RecyclerView.Adapter<AdaptadorRecyclerArticulo.ArticuloViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Articulo> articuloArray;
    private Typeface editTextFont;
    private AuxiliarGeneral auxiliarGeneral;

    public static class ArticuloViewHolder extends RecyclerView.ViewHolder {

        private TextView textRecyclerViewTitulo;
        private TextView textRecyclerViewFecha;


        public ArticuloViewHolder(View itemView, Typeface type) {
            super(itemView);

            textRecyclerViewTitulo = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewTitulo);
            textRecyclerViewTitulo.setTypeface(type, Typeface.BOLD);
            textRecyclerViewFecha = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewFecha);
            textRecyclerViewFecha.setTypeface(type);
        }

        public void bindTitular(Articulo articulo, AuxiliarGeneral auxiliarGeneral) {
            textRecyclerViewTitulo.setText(articulo.getTITULO());
            if (articulo.getFECHA_ACTUALIZACION() != null && !articulo.getFECHA_ACTUALIZACION().isEmpty()) {
                if (articulo.getFECHA_ACTUALIZACION().length() >= 8) {
                    String fecha = articulo.getFECHA_ACTUALIZACION().substring(0, 8);
                    fecha = auxiliarGeneral.dateFormate(fecha);
                    textRecyclerViewFecha.setText("ult.act: " + fecha);
                }
            } else if (articulo.getFECHA_CREACION() != null && !articulo.getFECHA_CREACION().isEmpty()) {
                if (articulo.getFECHA_CREACION().length() >= 8) {
                    String fecha = articulo.getFECHA_CREACION().substring(0, 8);
                    fecha = auxiliarGeneral.dateFormate(fecha);
                    textRecyclerViewFecha.setText("ult.act: " + fecha);
                }
            }
        }
    }

    public AdaptadorRecyclerArticulo(ArrayList<Articulo> articuloArray, Context context) {
        this.articuloArray = articuloArray;
        auxiliarGeneral = new AuxiliarGeneral(context);
        editTextFont = auxiliarGeneral.textFont(context);
    }

    @Override
    public ArticuloViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                 int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_articulo, viewGroup, false);

        itemView.setOnClickListener(this);
        ArticuloViewHolder tvh = new ArticuloViewHolder(itemView, editTextFont);

        return tvh;
    }

    @Override
    public void onBindViewHolder(ArticuloViewHolder viewHolder, int pos) {
        Articulo item = articuloArray.get(pos);

        viewHolder.bindTitular(item, auxiliarGeneral);
    }

    @Override
    public int getItemCount() {
        return articuloArray.size();
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