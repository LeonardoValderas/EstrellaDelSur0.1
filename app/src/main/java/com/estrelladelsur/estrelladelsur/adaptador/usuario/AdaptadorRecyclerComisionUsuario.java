package com.estrelladelsur.estrelladelsur.adaptador.usuario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
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
import java.util.ArrayList;

public class AdaptadorRecyclerComisionUsuario extends
        RecyclerView.Adapter<AdaptadorRecyclerComisionUsuario.ComisionViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Comision> comisionArray;
    private Typeface nombreFont;
    private Typeface cargoPeriodoFont;
    private AuxiliarGeneral auxiliarGeneral;
    public static class ComisionViewHolder extends RecyclerView.ViewHolder {

        private TextView textRecyclerViewNombre;
        private TextView textRecyclerViewCargo;
        private TextView textRecyclerViewPeriodo;
        private TextView textRecyclerViewPeriodoText;
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
            textRecyclerViewPeriodoText = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewPeriodoText);
            textRecyclerViewPeriodoText.setPaintFlags(textRecyclerViewPeriodoText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            imageRecyclerViewFoto = (ImageView) itemView
                    .findViewById(R.id.imageRecyclerViewFoto);

        }

        public void bindTitular(Comision comision,Typeface nombre, Typeface cargo) {

            textRecyclerViewNombre.setText(comision.getNOMBRE_COMISION().toString());
            textRecyclerViewNombre.setTypeface(nombre);
            textRecyclerViewCargo.setText(comision.getCARGO().toString());
            textRecyclerViewCargo.setTypeface(cargo, Typeface.BOLD);
            textRecyclerViewPeriodo.setText(comision.getPERIODO_DESDE().toString() + " - " + comision.getPERIODO_HASTA().toString());
            textRecyclerViewPeriodo.setTypeface(cargo);
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
    public AdaptadorRecyclerComisionUsuario(ArrayList<Comision> comisionArray, Context context) {
        this.comisionArray = comisionArray;
        auxiliarGeneral = new AuxiliarGeneral(context);
        this.cargoPeriodoFont = auxiliarGeneral.textFont(context);
        this.nombreFont = auxiliarGeneral.tituloFont(context);
    }
    @Override
    public ComisionViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                 int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_comision_direccion, viewGroup, false);

        itemView.setOnClickListener(this);
        ComisionViewHolder tvh = new ComisionViewHolder(itemView);

        return tvh;
    }
    @Override
    public void onBindViewHolder(ComisionViewHolder viewHolder, int pos) {
        Comision item = comisionArray.get(pos);
        viewHolder.bindTitular(item,nombreFont,cargoPeriodoFont);
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