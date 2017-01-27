package com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorRecyclerComision extends
        RecyclerView.Adapter<AdaptadorRecyclerComision.ComisionViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Comision> comisionArray;
    private Typeface nombreFont;
    private Typeface cargoPeriodoFont;
    private AuxiliarGeneral auxiliarGeneral;
    private Context context;
    public static class ComisionViewHolder extends RecyclerView.ViewHolder {

        private TextView textRecyclerViewNombre;
        private TextView textRecyclerViewCargo;
        private TextView textRecyclerViewPeriodo;
        private CircleImageView imageRecyclerViewFoto;
        private byte[] foto;


        public ComisionViewHolder(View itemView) {
            super(itemView);

            textRecyclerViewNombre = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewNombre);
            textRecyclerViewCargo = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewCargo);
            textRecyclerViewPeriodo = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewPeriodo);
            imageRecyclerViewFoto = (CircleImageView) itemView
                    .findViewById(R.id.imageRecyclerViewFoto);

        }

        public void bindTitular(Comision comision,Typeface nombre, Typeface cargo, Context context) {
            textRecyclerViewNombre.setText(comision.getNOMBRE_COMISION());
            textRecyclerViewNombre.setTypeface(nombre);
            textRecyclerViewCargo.setText(comision.getCARGO());
            textRecyclerViewCargo.setTypeface(cargo, Typeface.BOLD);
            textRecyclerViewPeriodo.setText(comision.getPERIODO_DESDE() + " - " + comision.getPERIODO_HASTA());
            textRecyclerViewPeriodo.setTypeface(cargo);
            if(!comision.getURL_COMISION().isEmpty())
                Picasso.with(context)
                        .load(comision.getURL_COMISION()).fit()
                        .placeholder(R.mipmap.ic_foto_galery)
                        .into(imageRecyclerViewFoto, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                imageRecyclerViewFoto.setImageResource(R.mipmap.ic_foto_galery);
                            }
                        });
            else
                Picasso.with(context)
                        .load(R.mipmap.ic_foto_galery).fit()
                        .placeholder(R.mipmap.ic_foto_galery)
                        .into(imageRecyclerViewFoto);
        }
    }

    public AdaptadorRecyclerComision(ArrayList<Comision> comisionArray,Context context) {
        this.comisionArray = comisionArray;
        auxiliarGeneral = new AuxiliarGeneral(context);
        this.cargoPeriodoFont = auxiliarGeneral.textFont(context);
        this.nombreFont = auxiliarGeneral.tituloFont(context);
        this.context = context;

    }

    @Override
    public ComisionViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                 int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_comision, viewGroup, false);

        itemView.setOnClickListener(this);
        ComisionViewHolder tvh = new ComisionViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(ComisionViewHolder viewHolder, int pos) {
        Comision item = comisionArray.get(pos);

        viewHolder.bindTitular(item,nombreFont, cargoPeriodoFont, context);
    }

    @Override
    public int getItemCount() {
        return comisionArray.size();
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