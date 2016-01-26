package com.estrelladelsur.estrelladelsur.adaptador;

import java.util.ArrayList;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Articulo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdaptadorRecyclerArticulo extends
        RecyclerView.Adapter<AdaptadorRecyclerArticulo.ArticuloViewHolder> implements
        View.OnClickListener {

    // private final static Context context;

    private View.OnClickListener listener;
    private ArrayList<Articulo> articuloArray;

    public static class ArticuloViewHolder extends RecyclerView.ViewHolder {

        private TextView textRecyclerViewTitulo;
        private TextView textRecyclerViewFecha;


        public ArticuloViewHolder(View itemView) {
            super(itemView);

            textRecyclerViewTitulo = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewTitulo);
            textRecyclerViewFecha = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewFecha);

        }


        public void bindTitular(Articulo articulo) {
            textRecyclerViewTitulo.setText(articulo.getTITULO());
            textRecyclerViewFecha.setText("ult.act: "+articulo.getFECHA_ACTUALIZACION());

        }
    }

    public AdaptadorRecyclerArticulo(ArrayList<Articulo> articuloArray) {
        this.articuloArray = articuloArray;
    }

    @Override
    public ArticuloViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                 int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_articulo, viewGroup, false);

        itemView.setOnClickListener(this);
        ArticuloViewHolder tvh = new ArticuloViewHolder(itemView);

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