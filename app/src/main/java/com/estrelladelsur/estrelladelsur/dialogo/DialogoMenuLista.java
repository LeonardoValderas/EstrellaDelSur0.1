package com.estrelladelsur.estrelladelsur.dialogo;


import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.abstracta.Cargo;

public class DialogoMenuLista {

    public Context context;
    public Button btnAceptar = null;
    public Button btnCancelar = null;
    public TextView titulo;
    public ListView listViewGeneral;
    public AlertDialog alertDialog;
    public LinearLayout linear1;
    public ImageButton imageButton1;
    public ArrayAdapter adapterList;
    public TextView textViewCargo;

    public DialogoMenuLista(Context context, String tituloA) {
        this.context = context;

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialogo_menu_lista, null);
        linear1 = (LinearLayout) layout.findViewById(R.id.linear1);
        // TITULO
        titulo = (TextView) layout.findViewById(R.id.alertGenericoTitulo);
        titulo.setText(tituloA);
        // BOTON ACEPTAR
        btnAceptar = (Button) layout.findViewById(R.id.alerGenericoBtnAceptar);
        // BOTON CANCELAR
        btnCancelar = (Button) layout
                .findViewById(R.id.alerGenericoBtnCancelar);
        //LISTA VIEW
        listViewGeneral = (ListView) layout.findViewById(R.id.listViewGeneral);

        imageButton1 = (ImageButton) layout.findViewById(R.id.imageButton1);
        textViewCargo = (TextView) layout.findViewById(R.id.textViewGeneral);

//        adapterList = new ArrayAdapter<Cargo>(context,
//                R.layout.listview_item_dialogo, R.id.textViewGeneral, cagosArray);
//
//        listViewGeneral.setAdapter(adapterList);

        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();
    }



}
