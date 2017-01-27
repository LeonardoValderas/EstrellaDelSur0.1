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
import com.estrelladelsur.estrelladelsur.entidad.Fixture;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class AdaptadorRecyclerFixture extends
        RecyclerView.Adapter<AdaptadorRecyclerFixture.FixtureViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Fixture> fixtureArray;
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

        public FixtureViewHolder(View itemView) {
            super(itemView);


            textFecha = (TextView) itemView
                    .findViewById(R.id.textFecha);

            textAnio = (TextView) itemView
                    .findViewById(R.id.textAnio);

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
            // HORA
            textRecyclerViewHora = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewHora);
            // CANCHA
            textRecyclerViewCancha = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewCancha);
            // RESULTADO LOCAL
            textRecyclerViewResultadoL = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewResultadoL);
            //textRecyclerViewResultadoL.setVisibility(View.INVISIBLE);
            // RESULTADO VISITA
            textRecyclerViewResultadoV = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewResultadoV);
            //textRecyclerViewResultadoV.setVisibility(View.INVISIBLE);
        }


        public void bindTitular(Fixture fixtureRecycler, Typeface equipo, Typeface texto, Context context) {
            if (!fixtureRecycler.getESCUDOLOCALURL().isEmpty())
                Picasso.with(context)
                        .load(fixtureRecycler.getESCUDOLOCALURL()).fit()
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

            if (!fixtureRecycler.getESCUDOVISITAURL().isEmpty())
                Picasso.with(context)
                        .load(fixtureRecycler.getESCUDOVISITAURL()).fit()
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

            textFecha.setText(fixtureRecycler.getFECHA());
            textAnio.setText(fixtureRecycler.getANIO());
            textRecyclerViewEquipoL.setText(fixtureRecycler.getEQUIPO_LOCAL());
            textRecyclerViewEquipoL.setTypeface(equipo, Typeface.BOLD);
            textRecyclerViewEquipoV.setText(fixtureRecycler.getEQUIPO_VISITA());
            textRecyclerViewEquipoV.setTypeface(equipo, Typeface.BOLD);
            textRecyclerViewDia.setText(fixtureRecycler.getDIA());
            textRecyclerViewDia.setTypeface(texto);
            textRecyclerViewHora.setText(fixtureRecycler.getHORA());
            textRecyclerViewHora.setTypeface(texto);
            textRecyclerViewCancha.setText(fixtureRecycler.getCANCHA());
            textRecyclerViewCancha.setTypeface(texto, Typeface.BOLD);

            if (fixtureRecycler.getRESULTADO_LOCAL() != null) {
                textRecyclerViewResultadoL.setText(fixtureRecycler.getRESULTADO_LOCAL());
                textRecyclerViewResultadoL.setTypeface(texto, Typeface.BOLD);
            } else {
                textRecyclerViewResultadoL.setText("-");
                textRecyclerViewResultadoL.setTypeface(texto, Typeface.BOLD);
            }
            if (fixtureRecycler.getRESULTADO_VISITA() != null) {
                textRecyclerViewResultadoV.setText(fixtureRecycler.getRESULTADO_VISITA());
                textRecyclerViewResultadoV.setTypeface(texto, Typeface.BOLD);
            } else {
                textRecyclerViewResultadoV.setText("-");
                textRecyclerViewResultadoV.setTypeface(texto, Typeface.BOLD);
            }
        }
    }

    public AdaptadorRecyclerFixture(ArrayList<Fixture> fixtureArray, Context context) {
        this.fixtureArray = fixtureArray;
        auxiliarGeneral = new AuxiliarGeneral(context);
        textFont = auxiliarGeneral.textFont(context);
        equipoFont = auxiliarGeneral.tituloFont(context);
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
        Fixture item = fixtureArray.get(pos);
        viewHolder.bindTitular(item, textFont, equipoFont, context);
    }

    @Override
    public int getItemCount() {
        return fixtureArray.size();
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
