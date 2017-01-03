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
import com.estrelladelsur.estrelladelsur.entidad.Noticia;
import java.util.ArrayList;

public class AdaptadorRecyclerNoticia extends
        RecyclerView.Adapter<AdaptadorRecyclerNoticia.NoticiaViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Noticia> noticiaArray;
    private Typeface editTextFont;
    private AuxiliarGeneral auxiliarGeneral;

    public static class NoticiaViewHolder extends RecyclerView.ViewHolder {

        private TextView textRecyclerViewTitulo;
        private TextView textRecyclerViewLink;

        public NoticiaViewHolder(View itemView, Typeface type) {
            super(itemView);

            textRecyclerViewTitulo = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewTitulo);
            //textRecyclerViewTitulo.setTypeface(type,Typeface.BOLD);
            textRecyclerViewLink = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewFecha);
            textRecyclerViewLink.setTypeface(type);
        }
        public void bindTitular(Noticia noticia) {
            textRecyclerViewTitulo.setText(noticia.getTITULO());
            textRecyclerViewLink.setText(noticia.getLINK());
        }
    }
    public AdaptadorRecyclerNoticia(ArrayList<Noticia> noticiaArray, Context context) {
        this.noticiaArray = noticiaArray;
        auxiliarGeneral = new AuxiliarGeneral(context);
        this.editTextFont = auxiliarGeneral.textFont(context);
    }

    @Override
    public NoticiaViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                 int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_articulo, viewGroup, false);

        itemView.setOnClickListener(this);
        NoticiaViewHolder tvh = new NoticiaViewHolder(itemView, editTextFont);

        return tvh;
    }

    @Override
    public void onBindViewHolder(NoticiaViewHolder viewHolder, int pos) {
        Noticia item = noticiaArray.get(pos);

        viewHolder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return noticiaArray.size();
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