package com.estrelladelsur.estrelladelsur.dialogo;

import android.app.AlertDialog;
import android.content.Context;
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

    public DialogoAlerta(Context context, String tituloA, String mensajeA,
                         String hint, String hint2) {
        this.context = context;

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialogo_alerta, null);

        // TITULO
        titulo = (TextView) layout.findViewById(R.id.alertGenericoTitulo);
        titulo.setText(tituloA);
        // MENSAJE
        mensaje = (TextView) layout.findViewById(R.id.alertGenericoTxtMensaje);
        mensaje.setText(mensajeA);
        // EDIT OCULTO
        editTextUno = (EditText) layout.findViewById(R.id.editTextUno);
        editTextUno.setHint(hint);
        // EDIT OCULTO
        editTextDos = (EditText) layout.findViewById(R.id.editTextDos);
        editTextDos.setHint(hint2);
        // TEXTO ERROR VACIO
        textErrorVacio = (TextView) layout
                .findViewById(R.id.textErrorVacio);
        // BOTON ACEPTAR
        btnAceptar = (Button) layout.findViewById(R.id.alerGenericoBtnAceptar);
        // BOTON CANCELAR
        btnCancelar = (Button) layout
                .findViewById(R.id.alerGenericoBtnCancelar);

        builder.setView(layout);
        alertDialog = builder.create();

        alertDialog.show();
    }
}
