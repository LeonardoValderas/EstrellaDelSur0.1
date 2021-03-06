package com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Resultado;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class AdaptadorRecyclerResultado extends
        RecyclerView.Adapter<AdaptadorRecyclerResultado.FixtureViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Resultado> resultadoArray;
    private Typeface textFont;
    private Typeface equipoFont;
    private AuxiliarGeneral auxiliarGeneral;
    private Context context;

    public static class FixtureViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewEscudoL;
        private TextView textRecyclerViewEquipoL;
        private ImageView imageViewEscudoV;
        private TextView textRecyclerViewEquipoV;
        private TextView textRecyclerViewDia;
        private TextView textRecyclerViewHora;
        private TextView textRecyclerViewCancha;
        private TextView textRecyclerViewResultadoV;
        private TextView textRecyclerViewResultadoL;
        private TextView textFecha;
        private TextView textAnio;
        private LinearLayout linearCancha;
        private LinearLayout linearDiaHora;

        public FixtureViewHolder(View itemView) {
            super(itemView);
            // ESCUDO LOCAL
            imageViewEscudoL = (ImageView) itemView
                    .findViewById(R.id.imageViewEscudoL);
            // ESCUDO VISITA
            imageViewEscudoV = (ImageView) itemView
                    .findViewById(R.id.imageViewEscudoV);
            // EQUIPO LOCAL
            textRecyclerViewEquipoL = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewEquipoL);
            // EQUIPO VISITA
            textRecyclerViewEquipoV = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewEquipoV);
            // DIA
            textRecyclerViewDia = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewDia);
            textRecyclerViewDia.setVisibility(View.INVISIBLE);
            // HORA
            textRecyclerViewHora = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewHora);
            textRecyclerViewHora.setVisibility(View.INVISIBLE);
            // CANCHA
            textRecyclerViewCancha = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewCancha);
            textRecyclerViewCancha.setVisibility(View.INVISIBLE);
            // RESULTADO LOCAL
            textRecyclerViewResultadoL = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewResultadoL);
            // RESULTADO VISITA
            textRecyclerViewResultadoV = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewResultadoV);
            linearCancha = (LinearLayout) itemView
                    .findViewById(R.id.linearCancha);
            linearCancha.setVisibility(View.GONE);
            linearDiaHora = (LinearLayout) itemView
                    .findViewById(R.id.linearDiaHora);
            linearDiaHora.setVisibility(View.GONE);

            textFecha = (TextView) itemView
                    .findViewById(R.id.textFecha);
            textAnio = (TextView) itemView
                    .findViewById(R.id.textAnio);

        }

        public void bindTitular(Resultado resultadoRecycler, Typeface equipo, Typeface texto, Context context) {
            if (!resultadoRecycler.getESCUDOLOCALURL().isEmpty())
                Picasso.with(context)
                        .load(resultadoRecycler.getESCUDOLOCALURL()).fit()
                        .placeholder(R.mipmap.ic_escudo_cris)
                        .into(imageViewEscudoL, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                imageViewEscudoL.setImageResource(R.mipmap.ic_escudo_cris);
                            }
                        });
            else
                Picasso.with(context)
                        .load(R.mipmap.ic_escudo_cris).fit()
                        .placeholder(R.mipmap.ic_escudo_cris)
                        .into(imageViewEscudoL);

            if (!resultadoRecycler.getESCUDOVISITAURL().isEmpty())
                Picasso.with(context)
                        .load(resultadoRecycler.getESCUDOVISITAURL()).fit()
                        .placeholder(R.mipmap.ic_escudo_cris)
                        .into(imageViewEscudoV, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                imageViewEscudoV.setImageResource(R.mipmap.ic_escudo_cris);
                            }
                        });
            else
                Picasso.with(context)
                        .load(R.mipmap.ic_escudo_cris).fit()
                        .placeholder(R.mipmap.ic_escudo_cris)
                        .into(imageViewEscudoV);

            textRecyclerViewEquipoL.setText(resultadoRecycler
                    .getEQUIPO_LOCAL());
            textRecyclerViewEquipoL.setTypeface(equipo, Typeface.BOLD);
            textRecyclerViewEquipoV.setText(resultadoRecycler
                    .getEQUIPO_VISITA());
            textRecyclerViewEquipoV.setTypeface(equipo, Typeface.BOLD);

            if (resultadoRecycler.getRESULTADO_LOCAL() != null) {
                textRecyclerViewResultadoL.setText(resultadoRecycler
                        .getRESULTADO_LOCAL());
                textRecyclerViewResultadoL.setTypeface(texto, Typeface.BOLD);
            } else {
                textRecyclerViewResultadoL.setText("-");
                textRecyclerViewResultadoL.setTypeface(texto, Typeface.BOLD);
            }
            if (resultadoRecycler.getRESULTADO_VISITA() != null) {
                textRecyclerViewResultadoV.setText(resultadoRecycler
                        .getRESULTADO_VISITA());
                textRecyclerViewResultadoV.setTypeface(texto, Typeface.BOLD);
            } else {
                textRecyclerViewResultadoV.setText("-");
                textRecyclerViewResultadoV.setTypeface(texto, Typeface.BOLD);
            }

            textAnio.setText(resultadoRecycler.getANIO());
            textFecha.setText(resultadoRecycler.getFECHA());
        }
    }

    public AdaptadorRecyclerResultado(ArrayList<Resultado> resultadoArray, Context context) {
        this.resultadoArray = resultadoArray;
        this.auxiliarGeneral = new AuxiliarGeneral(context);
        this.textFont = auxiliarGeneral.textFont(context);
        this.equipoFont = auxiliarGeneral.tituloFont(context);
        this.context = context;
    }

    @Override
    public FixtureViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_fixture, viewGroup, false);

        itemView.setOnClickListener(this);
        FixtureViewHolder tvh = new FixtureViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(FixtureViewHolder viewHolder, int pos) {
        Resultado item = resultadoArray.get(pos);
        viewHolder.bindTitular(item, textFont, equipoFont, context);
    }

    @Override
    public int getItemCount() {
        return resultadoArray.size();
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
