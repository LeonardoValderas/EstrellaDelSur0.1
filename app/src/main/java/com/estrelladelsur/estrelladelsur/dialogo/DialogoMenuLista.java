package com.estrelladelsur.estrelladelsur.dialogo;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;

public class DialogoMenuLista {

    public Context context;
    public Button btnAceptar = null;
    public Button btnCancelar = null;
    public TextView titulo;
    public ListView listViewGeneral;
    public AlertDialog alertDialog;
    public LinearLayout linear1;
    public ImageButton imageButton1;
    public TextView textViewCargo;
    private Typeface tituloFont;
    private Typeface textFont;

    public DialogoMenuLista(Context context, String tituloA) {
        this.context = context;
        this.textFont = Typeface.createFromAsset(context.getAssets(), "ATypewriterForMe.ttf");
        this.tituloFont = Typeface.createFromAsset(context.getAssets(), "aspace_demo.otf");

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialogo_menu_lista, null);

        // TITULO
        titulo = (TextView) layout.findViewById(R.id.alertGenericoTitulo);
        titulo.setText(tituloA);
        titulo.setTypeface(tituloFont);
        // BOTON ACEPTAR
        btnAceptar = (Button) layout.findViewById(R.id.alerGenericoBtnAceptar);
        btnAceptar.setTypeface(textFont, Typeface.BOLD);
        // BOTON CANCELAR
        btnCancelar = (Button) layout
                .findViewById(R.id.alerGenericoBtnCancelar);
        btnCancelar.setTypeface(textFont, Typeface.BOLD);
        //LISTA VIEW
        listViewGeneral = (ListView) layout.findViewById(R.id.listViewGeneral);

        imageButton1 = (ImageButton) layout.findViewById(R.id.imageButton1);
        textViewCargo = (TextView) layout.findViewById(R.id.textViewGeneral);

        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();
    }
}
