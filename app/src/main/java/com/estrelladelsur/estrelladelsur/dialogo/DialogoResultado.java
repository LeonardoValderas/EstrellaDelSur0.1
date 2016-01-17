package com.estrelladelsur.estrelladelsur.dialogo;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;

public class DialogoResultado {

    Context context;
    public Button btnAceptar = null;
    public Button btnCancelar = null;
    public AlertDialog alertDialog;
    public TextView titulo;
    public TextView alertEquipoLocal;
    public TextView resultadoTextErrorVacio;
    public TextView alertEquipoVisita;
    public EditText resultadoLocal;
    public EditText resultadoVisita;
    public ImageButton imageButtonGoleadores;

    public DialogoResultado(Context context, String tituloA, String local, String visita, String rlocal, String rvisita) {
        this.context = context;

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialogo_resultado, null);
        //TITUTLO
        titulo = (TextView) layout
                .findViewById(R.id.alertGenericoTitulo);
        titulo.setText(tituloA);
        // EQUIPO LOCAL
        alertEquipoLocal = (TextView) layout
                .findViewById(R.id.alertEquipoLocal);
        alertEquipoLocal.setText(local);
        // EQUIPO VISITA
        alertEquipoVisita = (TextView) layout
                .findViewById(R.id.alertEquipoVisita);
        alertEquipoVisita.setText(visita);
        // RESULTADO LOCAL
        resultadoLocal = (EditText) layout.findViewById(R.id.resultadoLocal);
        resultadoLocal.setText(rlocal);
        // RESULTADO VISITA
        resultadoVisita = (EditText) layout.findViewById(R.id.resultadoVisita);
        resultadoVisita.setText(rvisita);
        // ERROR VACIO
        resultadoTextErrorVacio = (TextView) layout
                .findViewById(R.id.resultadoTextErrorVacio);
        // BOTON GOLEADOR
        imageButtonGoleadores = (ImageButton) layout
                .findViewById(R.id.imageButtonGoleadores);
        // ACEPTAR
        btnAceptar = (Button) layout.findViewById(R.id.alerGenericoBtnAceptar);
        // CANCELAR
        btnCancelar = (Button) layout
                .findViewById(R.id.alerGenericoBtnCancelar);

        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();
    }
}
