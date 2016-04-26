package com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import java.util.ArrayList;

public class DialogoGoleador {

	Context context;
	public Button btnAceptar = null;
	public Button btnCancelar = null;
	public Button buttonFecha = null;
	public Spinner spinnerLugar;
	public AlertDialog alertDialog;
	public TextView titulo;
	public TextView mensaje;
	public TextView tableroTextErrorVacio;
	public TextView textViewLugar;
	public TextView textViewDivision; 
    public  EditText tableroEditActualizarBolilla;
    public ListView listViewGeneral;
    public CheckBox checkBoxSuspendido;
    public LinearLayout layoutGoleadores;
    
	public DialogoGoleador(Context context, String tituloA, String cantidad,  ArrayList<Jugador> jugadoresArray) {
		this.context = context;

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dialogo_menu_lista, null);
		// TITULO
		titulo = (TextView) layout
				.findViewById(R.id.alertGenericoTitulo);
		titulo.setText(tituloA);
		//CHECK ENTRENAMIENTO
		checkBoxSuspendido = (CheckBox) layout
				.findViewById(R.id.checkBoxSuspendido);
		checkBoxSuspendido.setVisibility(View.GONE);
		//FECHA ENTRENAMIENTO
		buttonFecha = (Button) layout
				.findViewById(R.id.buttonFecha);
		buttonFecha.setVisibility(View.GONE);
		// LUGAR ENTRENAMIENTO / CANTIDAD GOLES
		textViewLugar = (TextView) layout
				.findViewById(R.id.textViewLugar);
		textViewLugar.setText(cantidad + " gol");
		//LAYOUT GOLEADORES
		layoutGoleadores = (LinearLayout) layout
				.findViewById(R.id.layoutGoleadores);
		layoutGoleadores.setVisibility(View.VISIBLE);
		// DIVISION ENTRENAMIENTO
		textViewDivision = (TextView) layout
				.findViewById(R.id.textViewDivision);
		textViewDivision.setVisibility(View.GONE);
       //LISTVIEW JUGADORES
		listViewGeneral = (ListView) layout.findViewById(R.id.listViewGeneral);
		listViewGeneral.setVisibility(View.GONE);
        // ACEPTAR
		btnAceptar = (Button) layout.findViewById(R.id.alerGenericoBtnAceptar);
		btnAceptar.setText("Guarda");
		//CANCELAR
		btnCancelar = (Button) layout
				.findViewById(R.id.alerGenericoBtnCancelar);
		btnCancelar.setText("Cerrar");

    	builder.setView(layout);
		alertDialog = builder.create();
		alertDialog.show();
	}

}
