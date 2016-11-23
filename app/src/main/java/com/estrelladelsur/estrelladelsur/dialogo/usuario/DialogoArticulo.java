package com.estrelladelsur.estrelladelsur.dialogo.usuario;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;

public class DialogoArticulo {

    Context context;
    public ImageButton btnCerrar = null;
    public Button btnLink = null;
    public AlertDialog alertDialog;
    public LinearLayout linearLink;
    public TextView titulo;
    public TextView subTitulo;
    public TextView detalle;
    private Typeface tituloFont;
    private Typeface textFont;

    public DialogoArticulo(Context context, String tituloS,
                           String mensaje) {

        this.context = context;
        this.textFont = Typeface.createFromAsset(context.getAssets(), "ATypewriterForMe.ttf");
        this.tituloFont = Typeface.createFromAsset(context.getAssets(), "aspace_demo.otf");

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.dialogo_usuario_general, null);

        // TITULO
        titulo = (TextView) layout.findViewById(R.id.alertGenericoTitulo);
        titulo.setText(tituloS);
        titulo.setTypeface(tituloFont, Typeface.BOLD);

        detalle = (TextView) layout
                .findViewById(R.id.alertGenericoDetalle);
        detalle.setText(mensaje);
        detalle.setTypeface(textFont);
        // BOTON CERRAE
        btnCerrar = (ImageButton) layout.findViewById(R.id.cerrarDialogo);

        btnLink = (Button)layout.findViewById(R.id.linkDialogo);

        linearLink = (LinearLayout) layout.findViewById(R.id.linearLink);
        builder.setView(layout);
        alertDialog = builder.create();

        alertDialog.show();
    }
}
