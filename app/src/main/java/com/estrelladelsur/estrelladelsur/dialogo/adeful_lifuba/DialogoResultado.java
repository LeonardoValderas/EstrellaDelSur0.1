package com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;

public class DialogoResultado {

    public Context context;
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
    private Typeface tituloFont;
    private Typeface textFont;

    public DialogoResultado(Context context, String tituloA, String local, String visita, String rlocal, String rvisita) {
        this.context = context;
        this.textFont = Typeface.createFromAsset(context.getAssets(), "ATypewriterForMe.ttf");
        this.tituloFont = Typeface.createFromAsset(context.getAssets(), "aspace_demo.otf");

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialogo_resultado, null);
        //TITUTLO
        titulo = (TextView) layout
                .findViewById(R.id.alertGenericoTitulo);
        titulo.setText(tituloA);
        titulo.setTypeface(tituloFont);
        // EQUIPO LOCAL
        alertEquipoLocal = (TextView) layout
                .findViewById(R.id.alertEquipoLocal);
        alertEquipoLocal.setText(local);
        alertEquipoLocal.setTypeface(tituloFont);
        // EQUIPO VISITA
        alertEquipoVisita = (TextView) layout
                .findViewById(R.id.alertEquipoVisita);
        alertEquipoVisita.setText(visita);
        alertEquipoVisita.setTypeface(tituloFont);
        // RESULTADO LOCAL
        resultadoLocal = (EditText) layout.findViewById(R.id.resultadoLocal);
        resultadoLocal.setText(rlocal);
        resultadoLocal.setTypeface(textFont);
        // RESULTADO VISITA
        resultadoVisita = (EditText) layout.findViewById(R.id.resultadoVisita);
        resultadoVisita.setText(rvisita);
        resultadoVisita.setTypeface(textFont);
        // ERROR VACIO
        resultadoTextErrorVacio = (TextView) layout
                .findViewById(R.id.resultadoTextErrorVacio);
        resultadoTextErrorVacio.setTypeface(textFont,Typeface.BOLD);

        // BOTON GOLEADOR
        imageButtonGoleadores = (ImageButton) layout
                .findViewById(R.id.imageButtonGoleadores);
        imageButtonGoleadores.setVisibility(View.GONE);

        // ACEPTAR
        btnAceptar = (Button) layout.findViewById(R.id.alerGenericoBtnAceptar);
        btnAceptar.setTypeface(textFont, Typeface.BOLD);
        // CANCELAR
        btnCancelar = (Button) layout
                .findViewById(R.id.alerGenericoBtnCancelar);
        btnCancelar.setTypeface(textFont, Typeface.BOLD);

        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();
    }
}
