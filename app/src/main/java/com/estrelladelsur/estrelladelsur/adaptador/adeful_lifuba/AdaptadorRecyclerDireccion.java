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
import com.estrelladelsur.estrelladelsur.entidad.Direccion;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorRecyclerDireccion extends
        RecyclerView.Adapter<AdaptadorRecyclerDireccion.DireccionViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Direccion> direccionArray;
    private Typeface nombreFont;
    private Typeface cargoPeriodoFont;
    private AuxiliarGeneral auxiliarGeneral;
    private Context context;

    public static class DireccionViewHolder extends RecyclerView.ViewHolder {

        private TextView textRecyclerViewNombre;
        private TextView textRecyclerViewCargo;
        private TextView textRecyclerViewPeriodo;
        private ImageView imageRecyclerViewFoto;
        private byte[] foto;

        public DireccionViewHolder(View itemView) {
            super(itemView);

            textRecyclerViewNombre = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewNombre);
            textRecyclerViewCargo = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewCargo);
            textRecyclerViewPeriodo = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewPeriodo);
            imageRecyclerViewFoto = (ImageView) itemView
                    .findViewById(R.id.imageRecyclerViewFoto);

        }

        public void bindTitular(Direccion direccion, Typeface nombre, Typeface cargo, Context context) {
            textRecyclerViewNombre.setText(direccion.getNOMBRE_DIRECCION());
            textRecyclerViewNombre.setTypeface(nombre);
            textRecyclerViewCargo.setText(direccion.getCARGO());
            textRecyclerViewCargo.setTypeface(cargo, Typeface.BOLD);
            textRecyclerViewPeriodo.setText(direccion.getPERIODO_DESDE() + " - " + direccion.getPERIODO_HASTA());
            textRecyclerViewPeriodo.setTypeface(cargo);
            if(!direccion.getURL_DIRECCION().isEmpty())
                Picasso.with(context)
                        .load(direccion.getURL_DIRECCION()).fit()
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


//            foto = direccion.getFOTO_DIRECCION();
//            if (foto == null) {
//                imageRecyclerViewFoto.setImageResource(R.mipmap.ic_foto_galery);
//            } else {
//                Bitmap theImage = BitmapFactory.decodeByteArray(
//                        foto, 0, foto.length);
//                imageRecyclerViewFoto.setImageBitmap(theImage);
//            }
        }
    }

    public AdaptadorRecyclerDireccion(ArrayList<Direccion> direccionArray,Context context) {
        this.direccionArray = direccionArray;
        auxiliarGeneral = new AuxiliarGeneral(context);
        this.cargoPeriodoFont = auxiliarGeneral.textFont(context);
        this.nombreFont = auxiliarGeneral.tituloFont(context);
        this.context = context;
    }

    @Override
    public DireccionViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                 int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_comision, viewGroup, false);

        itemView.setOnClickListener(this);
        DireccionViewHolder tvh = new DireccionViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(DireccionViewHolder viewHolder, int pos) {
        Direccion item = direccionArray.get(pos);

        viewHolder.bindTitular(item, nombreFont, cargoPeriodoFont, context);
    }

    @Override
    public int getItemCount() {
        return direccionArray.size();
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