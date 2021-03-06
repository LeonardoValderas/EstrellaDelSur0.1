package com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Permiso;

import java.util.ArrayList;

public class AdaptadorRecyclerEditarPermiso extends
        RecyclerView.Adapter<AdaptadorRecyclerEditarPermiso.PermisoViewHolder> implements
        View.OnClickListener {

    private View.OnClickListener listener;
    private ArrayList<Permiso> permisoArray;
    private Context context;
    private AuxiliarGeneral auxiliarGeneral;
    private Typeface usuarioFont, modulosfont;

    public static class PermisoViewHolder extends RecyclerView.ViewHolder {
        private TextView textRecyclerViewEquipoL;
        private TextView textRecyclerViewDia;
        private TextView textRecyclerViewHora;
        private TextView textRecyclerViewCancha;
        private TextView textRecyclerViewResultadoL;
        private String permisos = "";
        private ControladorGeneral controladorGeneral;
        private ArrayList<Permiso> permisoDivisionArray;

        public PermisoViewHolder(View itemView) {
            super(itemView);

            // EQUIPO LOCAL
            textRecyclerViewEquipoL = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewEquipoL);
            // EQUIPO VISITA
            // DIA
            textRecyclerViewDia = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewDia);
            // HORA
            textRecyclerViewHora = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewHora);
            // CANCHA
            textRecyclerViewCancha = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewCancha);
            textRecyclerViewCancha.setVisibility(View.GONE);
            // RESULTADO LOCAL
            textRecyclerViewResultadoL = (TextView) itemView
                    .findViewById(R.id.textRecyclerViewResultadoL);
            textRecyclerViewResultadoL.setVisibility(View.GONE);
        }

        public void bindTitular(Permiso permiso, Context context, Typeface u, Typeface m) {

            textRecyclerViewEquipoL.setText(permiso.getUSUARIO().toString());
            textRecyclerViewEquipoL.setTextSize(30);
            textRecyclerViewEquipoL.setTypeface(u, Typeface.BOLD);
            textRecyclerViewDia.setText("Permisos:");
            textRecyclerViewDia.setTypeface(m, Typeface.BOLD);
            controladorGeneral = new ControladorGeneral(context);
            permisoDivisionArray = controladorGeneral.selectListaPermisoId(permiso.getID_PERMISO());
            if (permisoDivisionArray != null) {
                int dato = permisoDivisionArray.size();
                for (int i = 0; i < dato; i++) {
                    if (dato != i + 1) {
                        permisos += permisoDivisionArray.get(i).getSUBMODULO().toString() + "-";
                    } else {
                        permisos += permisoDivisionArray.get(i).getSUBMODULO().toString() + ".";
                    }
                }
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.error_data_base),
                        Toast.LENGTH_SHORT).show();
            }
            textRecyclerViewHora.setText(permisos);
            textRecyclerViewHora.setTypeface(m);
        }
    }

    public AdaptadorRecyclerEditarPermiso(ArrayList<Permiso> permisoArray, Context c) {
        this.permisoArray = permisoArray;
        this.context = c;
        auxiliarGeneral = new AuxiliarGeneral(c);
        usuarioFont = auxiliarGeneral.tituloFont(c);
        modulosfont = auxiliarGeneral.textFont(c);

    }

    @Override
    public PermisoViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item_entrenamiento, viewGroup, false);
        itemView.setOnClickListener(this);
        PermisoViewHolder tvh = new PermisoViewHolder(itemView);
        return tvh;
    }

    @Override
    public void onBindViewHolder(PermisoViewHolder viewHolder, int pos) {
        Permiso item = permisoArray.get(pos);
        viewHolder.bindTitular(item, context, usuarioFont, modulosfont);
    }

    @Override
    public int getItemCount() {
        return permisoArray.size();
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
