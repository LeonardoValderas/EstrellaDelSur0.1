package com.estrelladelsur.estrelladelsur.dialogo;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;

public class DialogoListCheck {

	Context context;
	public Button btnAceptar = null;
	public Button btnCancelar = null;
	public Button buttonFecha = null;
	public AlertDialog alertDialog;
	public TextView titulo;
	public TextView mensaje;
	public TextView tableroTextErrorVacio;
    public  Spinner spinnerLugar;
    public ListView listViewGeneral;
    public CheckBox checkBoxSuspendido;
    public EditText editTextComentario;
    
	public DialogoListCheck(Context context, String tituloA,  String[] lista,String[] lugar) {
		this.context = context;

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dialogo_menu_lista, null);
		//TITULO
		titulo = (TextView) layout
				.findViewById(R.id.alertGenericoTitulo);
		titulo.setText(tituloA);

		//COMENTARIO
		editTextComentario =(EditText) layout
		.findViewById(R.id.editTextComentario);
		//FECHA
		buttonFecha = (Button) layout
				.findViewById(R.id.buttonFecha);
		//CHECH SUSPENDIDO
		checkBoxSuspendido = (CheckBox) layout
				.findViewById(R.id.checkBoxSuspendido);
       //BOTON ACEPTAR
		btnAceptar = (Button) layout.findViewById(R.id.alerGenericoBtnAceptar);
		btnAceptar.setText("Aceptar");
		//BOTON CANCELAR
		btnCancelar = (Button) layout
				.findViewById(R.id.alerGenericoBtnCancelar);
		btnCancelar.setText("Cancelar");

        // LISTA
		listViewGeneral = (ListView) layout.findViewById(R.id.listViewGeneral);
		
    	builder.setView(layout);
		alertDialog = builder.create();
		alertDialog.show();
	}

}
