package com.estrelladelsur.estrelladelsur.adaptador;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import java.util.ArrayList;

public class AdaptadorRecyclerComision extends
        RecyclerView.Adapter<AdaptadorRecyclerComision.ComisionViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Comision> comisionArray;

    public static class ComisionViewHolder extends RecyclerView.ViewHolder {

        private TextView textRecyclerViewNombre;
        private TextView textRecyclerViewCargo;
        private TextView textRecyclerViewPeriodo;
        private ImageView imageRecyclerViewFoto;
        private byte[] foto;


        public ComisionViewHolder(View itemView) {
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

        public void bindTitular(Comision comision) {
            textRecyclerViewNombre.setText(comision.getNOMBRE_COMISION().toString());
            textRecyclerViewCargo.setText(comision.getCARGO().toString());
            textRecyclerViewPeriodo.setText(comision.getPERIODO_DESDE().toString() + " - " + comision.getPERIODO_HASTA().toString());
            foto = comision.getFOTO_COMISION();

            if (foto == null) {
                imageRecyclerViewFoto.setImageResource(R.mipmap.ic_foto_galery);
            } else {
                Bitmap theImage = BitmapFactory.decodeByteArray(
                        foto, 0, foto.length);
                imageRecyclerViewFoto.setImageBitmap(theImage);
            }
        }
    }

    public AdaptadorRecyclerComision(ArrayList<Comision> comisionArray) {
        this.comisionArray = comisionArray;
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

        viewHolder.bindTitular(item);
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