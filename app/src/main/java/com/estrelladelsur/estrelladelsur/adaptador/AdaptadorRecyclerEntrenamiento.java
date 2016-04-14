package com.estrelladelsur.estrelladelsur.adaptador;

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
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;
import com.estrelladelsur.estrelladelsur.entidad.EntrenamientoRecycler;
import java.util.ArrayList;

public class AdaptadorRecyclerEntrenamiento extends
		RecyclerView.Adapter<AdaptadorRecyclerEntrenamiento.EntrenamientoViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<EntrenamientoRecycler> entrenamientoArray;
	private Context context;
	private Typeface textFont;
	private Typeface equipoFont;
	private AuxiliarGeneral auxiliarGeneral;

	public static class EntrenamientoViewHolder extends RecyclerView.ViewHolder {
		private TextView textRecyclerViewEquipoL;
		private TextView textRecyclerViewDia;
		private TextView textRecyclerViewHora;
		private TextView textRecyclerViewCancha;
		private TextView textRecyclerViewResultadoL;
		private LinearLayout linearEscudoL, linearEscudoV, linearVisita;
        private String divisiones="";
		private ControladorAdeful controladorAdeful;
		private ArrayList<Entrenamiento> entrenamientoDivisionArray;


		public EntrenamientoViewHolder(View itemView) {
			super(itemView);

			// lINEAR ESCUDO LOCAL
			linearEscudoL = (LinearLayout) itemView
					.findViewById(R.id.linearEscudoL);
			linearEscudoL.setVisibility(View.GONE);
            // lINEAR ESCUDO VISITA
			linearEscudoV = (LinearLayout) itemView
					.findViewById(R.id.linearEscudoV);
			linearEscudoV.setVisibility(View.GONE);
		   // lINEAR  VISITA
			linearVisita = (LinearLayout) itemView
					.findViewById(R.id.linearVisita);
			linearVisita.setVisibility(View.GONE);

			textRecyclerViewEquipoL = (TextView) itemView
					.findViewById(R.id.textRecyclerViewEquipoL);
	    	// DIA
			textRecyclerViewDia = (TextView) itemView
					.findViewById(R.id.textRecyclerViewDia);
			// HORA
			textRecyclerViewHora = (TextView) itemView
					.findViewById(R.id.textRecyclerViewHora);
			// CANCHA
			textRecyclerViewCancha = (TextView) itemView
					.findViewById(R.id.textRecyclerViewCancha);
			// RESULTADO LOCAL
			textRecyclerViewResultadoL = (TextView) itemView
					.findViewById(R.id.textRecyclerViewResultadoL);

     	}

			public void bindTitular(EntrenamientoRecycler entrenamientoRecycler,Context context,Typeface equipo, Typeface texto, AuxiliarGeneral a) {

			textRecyclerViewEquipoL.setText("Entrenamiento");
			textRecyclerViewEquipoL.setTextSize(25);
			textRecyclerViewEquipoL.setTypeface(texto, Typeface.BOLD);
			textRecyclerViewResultadoL.setText(entrenamientoRecycler.getDIA().toString() + "-" + entrenamientoRecycler.getHORA().toString());
			textRecyclerViewResultadoL.setTypeface(equipo);
			textRecyclerViewDia.setText("División citada: ");
			textRecyclerViewDia.setTypeface(texto);
			controladorAdeful = new ControladorAdeful(context);
			entrenamientoDivisionArray =controladorAdeful.selectListaDivisionEntrenamientoAdefulId(entrenamientoRecycler.getID_ENTRENAMIENTO());
			if(entrenamientoDivisionArray != null) {
		        int cant =entrenamientoDivisionArray.size();
				for (int i = 0; i < cant; i++) {
					if(i+1 == cant){
					divisiones += entrenamientoDivisionArray.get(i).getDESCRIPCION().toString() + ". ";
					}else{
					divisiones += entrenamientoDivisionArray.get(i).getDESCRIPCION().toString() + " - ";
				}}
    		}else{
				a.errorDataBase(context);
			}
			textRecyclerViewHora.setText(divisiones);
			textRecyclerViewHora.setTypeface(equipo);
			textRecyclerViewCancha.setText("Lugar: " + entrenamientoRecycler.getNOMBRE().toString());
			textRecyclerViewCancha.setTextSize(15);
			textRecyclerViewCancha.setTypeface(equipo);
		}
	}
		public AdaptadorRecyclerEntrenamiento(ArrayList<EntrenamientoRecycler> entrenamientoArray, Context c) {
			this.entrenamientoArray = entrenamientoArray;
			this.context = c;
			auxiliarGeneral = new AuxiliarGeneral(c);
			this.textFont = auxiliarGeneral.textFont(c);
			this.equipoFont = auxiliarGeneral.tituloFont(c);
	}
	@Override
	public EntrenamientoViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_fixture, viewGroup, false);
		itemView.setOnClickListener(this);
		EntrenamientoViewHolder tvh = new EntrenamientoViewHolder(itemView);
		return tvh;
	}
	@Override
	public void onBindViewHolder(EntrenamientoViewHolder viewHolder, int pos) {
		EntrenamientoRecycler item = entrenamientoArray.get(pos);
		viewHolder.bindTitular(item,context,textFont, equipoFont, auxiliarGeneral);
	}
	@Override
	public int getItemCount() {
		return entrenamientoArray.size();
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
