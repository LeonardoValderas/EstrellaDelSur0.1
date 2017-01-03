package com.estrelladelsur.estrelladelsur.adaptador.usuario;

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
import com.estrelladelsur.estrelladelsur.database.usuario.adeful.ControladorUsuarioAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;

import java.util.ArrayList;

public class AdaptadorRecyclerAsistenciaUsuario extends
        RecyclerView.Adapter<AdaptadorRecyclerAsistenciaUsuario.JugadorViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Jugador> jugadorArray;
    private Typeface nombreFont;
    private Typeface cargoPeriodoFont;
    private AuxiliarGeneral auxiliarGeneral;
    private int cantidad;
    private ControladorUsuarioAdeful controladorUsuarioAdeful;

    public static class JugadorViewHolder extends RecyclerView.ViewHolder {
        private TextView textRecyclerViewNombre;
        private TextView textRecyclerViewPosicion;
        private TextView textRecyclerViewPeriodoText;
        private TextView textRecyclerViewDivision;
        private ImageView imageRecyclerViewFoto;
        private byte[] foto;
        private Bitmap fotoBitmap;


        public JugadorViewHolder(View itemView) {
            super(itemView);

            textRecyclerViewNombre = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewNombre);
            textRecyclerViewDivision = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewCargo);
            textRecyclerViewPosicion = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewPeriodo);
            textRecyclerViewPeriodoText = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewPeriodoText);
            imageRecyclerViewFoto = (ImageView) itemView
                    .findViewById(R.id.imageRecyclerViewFoto);
        }

        public void bindTitular(Jugador jugador, Typeface nombre, Typeface cargo, ControladorUsuarioAdeful controladorUsuarioAdeful, int cantidad) {
            // ESCUDO EQUIPO LOCAL

            int cant = controladorUsuarioAdeful.selectCountEntrenamientoJugador(jugador.getID_JUGADOR());
            int porcentaje = 0;
            if (cant > 0)
                porcentaje = cant * 100 / cantidad;

            foto = jugador.getFOTO_JUGADOR();
            if (foto == null) {
                imageRecyclerViewFoto.setImageResource(R.mipmap.ic_foto_galery);
            } else {
                fotoBitmap = BitmapFactory.decodeByteArray(
                        jugador.getFOTO_JUGADOR(), 0,
                        jugador.getFOTO_JUGADOR().length);

                fotoBitmap = Bitmap.createScaledBitmap(
                        fotoBitmap, 150, 150, true);

                imageRecyclerViewFoto.setImageBitmap(fotoBitmap);
            }
            textRecyclerViewNombre.setText(jugador.getNOMBRE_JUGADOR());
            textRecyclerViewNombre.setTypeface(nombre, Typeface.BOLD);
            textRecyclerViewDivision.setText(String.valueOf(porcentaje) + "%");
            textRecyclerViewDivision.setTextSize(20);
            textRecyclerViewDivision.setTypeface(nombre);
            textRecyclerViewPosicion.setText("");
            //textRecyclerViewPosicion.setTypeface(cargo,Typeface.BOLD);
            textRecyclerViewPeriodoText.setText("De " + cantidad + " entrenamientos,\nasistió a " + cant + ".");
        }
    }

    public AdaptadorRecyclerAsistenciaUsuario(ArrayList<Jugador> jugadorArray, int cantidad, Context context) {
        this.jugadorArray = jugadorArray;
        this.cantidad = cantidad;
        auxiliarGeneral = new AuxiliarGeneral(context);
        this.cargoPeriodoFont = auxiliarGeneral.textFont(context);
        this.nombreFont = auxiliarGeneral.tituloFont(context);
        controladorUsuarioAdeful = new ControladorUsuarioAdeful(context);
    }

    @Override
    public JugadorViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_comision, viewGroup, false);

        itemView.setOnClickListener(this);
        JugadorViewHolder tvh = new JugadorViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(JugadorViewHolder viewHolder, int pos) {
        Jugador item = jugadorArray.get(pos);

        viewHolder.bindTitular(item, nombreFont, cargoPeriodoFont, controladorUsuarioAdeful, cantidad);
    }

    @Override
    public int getItemCount() {
        return jugadorArray.size();
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
