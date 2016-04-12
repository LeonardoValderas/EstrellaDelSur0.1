package com.estrelladelsur.estrelladelsur.dialogo;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;

public class DialogoAlerta {

    Context context;
    public Button btnAceptar = null;
    public Button btnCancelar = null;
    public AlertDialog alertDialog;
    public TextView titulo;
    public TextView mensaje;
    public TextView textErrorVacio;
    public EditText editTextUno;
    public EditText editTextDos;
    private Typeface tituloFont;
    private Typeface textFont;

    public DialogoAlerta(Context context, String tituloA, String mensajeA,
                         String hint, String hint2) {
        this.context = context;
        this.textFont = Typeface.createFromAsset(context.getAssets(), "ATypewriterForMe.ttf");
        this.tituloFont = Typeface.createFromAsset(context.getAssets(), "aspace_demo.otf");

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.dialogo_alerta, null);

        // TITULO
        titulo = (TextView) layout.findViewById(R.id.alertGenericoTitulo);
        titulo.setText(tituloA);
        titulo.setTypeface(tituloFont);
        // MENSAJE
        mensaje = (TextView) layout.findViewById(R.id.alertGenericoTxtMensaje);
        mensaje.setText(mensajeA);
        mensaje.setTypeface(textFont);
        // EDIT OCULTO
        editTextUno = (EditText) layout.findViewById(R.id.editTextUno);
        editTextUno.setHint(hint);
        editTextUno.setTypeface(textFont);
        // EDIT OCULTO
        editTextDos = (EditText) layout.findViewById(R.id.editTextDos);
        editTextDos.setHint(hint2);
        editTextDos.setTypeface(textFont);

        // TEXTO ERROR VACIO
        textErrorVacio = (TextView) layout
                .findViewById(R.id.textErrorVacio);
        textErrorVacio.setTypeface(textFont);
        // BOTON ACEPTAR
        btnAceptar = (Button) layout.findViewById(R.id.alerGenericoBtnAceptar);
        btnAceptar.setTypeface(textFont, Typeface.BOLD);
        // BOTON CANCELAR
        btnCancelar = (Button) layout
                .findViewById(R.id.alerGenericoBtnCancelar);
        btnCancelar.setTypeface(textFont, Typeface.BOLD);

        builder.setView(layout);
        alertDialog = builder.create();

        alertDialog.show();
    }
}
