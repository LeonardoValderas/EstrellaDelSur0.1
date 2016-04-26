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
import com.estrelladelsur.estrelladelsur.entidad.Articulo;
import java.util.ArrayList;

public class AdaptadorRecyclerTitulo extends
        RecyclerView.Adapter<AdaptadorRecyclerTitulo.ArticuloViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Articulo> articuloArray;
    private Typeface editTextFont;
    private AuxiliarGeneral auxiliarGeneral;

    public static class ArticuloViewHolder extends RecyclerView.ViewHolder {

        private TextView textRecyclerViewTitulo;

        public ArticuloViewHolder(View itemView, Typeface type) {
            super(itemView);

            textRecyclerViewTitulo = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewTitulo);
            textRecyclerViewTitulo.setTypeface(type,Typeface.BOLD);
        }
        public void bindTitular(Articulo articulo) {
            textRecyclerViewTitulo.setText(articulo.getTITULO());
        }
    }

    public AdaptadorRecyclerTitulo(ArrayList<Articulo> articuloArray, Context context) {
        this.articuloArray = articuloArray;
        auxiliarGeneral = new AuxiliarGeneral(context);
        editTextFont = auxiliarGeneral.tituloFont(context);
       }

    @Override
    public ArticuloViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                 int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_titulo, viewGroup, false);

        itemView.setOnClickListener(this);
        ArticuloViewHolder tvh = new ArticuloViewHolder(itemView,editTextFont);

        return tvh;
    }

    @Override
    public void onBindViewHolder(ArticuloViewHolder viewHolder, int pos) {
        Articulo item = articuloArray.get(pos);

        viewHolder.bindTitular(item);
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