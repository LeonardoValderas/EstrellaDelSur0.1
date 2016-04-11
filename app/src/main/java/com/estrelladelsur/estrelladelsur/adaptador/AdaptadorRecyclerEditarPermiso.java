package com.estrelladelsur.estrelladelsur.adaptador;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Permiso;
import java.util.ArrayList;

public class AdaptadorRecyclerEditarPermiso extends
		RecyclerView.Adapter<AdaptadorRecyclerEditarPermiso.PermisoViewHolder> implements
		View.OnClickListener {

	private View.OnClickListener listener;
	private ArrayList<Permiso> permisoArray;
	private Context context;

	public static class PermisoViewHolder extends RecyclerView.ViewHolder {
		private ImageView imageViewEscudoL;
		private TextView textRecyclerViewEquipoL;
		private ImageView imageViewEscudoV;
		private TextView textRecyclerViewEquipoV;
		private TextView textRecyclerViewDia;
		private TextView textRecyclerViewHora;
		private TextView textRecyclerViewCancha;
		private TextView textRecyclerViewResultadoV;
		private TextView textRecyclerViewResultadoL;
        private String permisos ="";
		private ControladorAdeful controladorAdeful;
		private ArrayList<Permiso> permisoDivisionArray;

		public PermisoViewHolder(View itemView) {
			super(itemView);

			// ESCUDO LOCAL
			imageViewEscudoL = (ImageView) itemView
					.findViewById(R.id.imageViewEscudoL);
			imageViewEscudoL.setVisibility(View.GONE);
			// ESCUDO VISITA
			imageViewEscudoV = (ImageView) itemView
					.findViewById(R.id.imageViewEscudoV);
			imageViewEscudoV.setVisibility(View.GONE);
			// EQUIPO LOCAL
			textRecyclerViewEquipoL = (TextView) itemView
					.findViewById(R.id.textRecyclerViewEquipoL);
			// EQUIPO VISITA
			textRecyclerViewEquipoV = (TextView) itemView
					.findViewById(R.id.textRecyclerViewEquipoV);
			textRecyclerViewEquipoV.setVisibility(View.GONE);
			// DIA
			textRecyclerViewDia = (TextView) itemView
					.findViewById(R.id.textRecyclerViewDia);

			// HORA
			textRecyclerViewHora = (TextView) itemView
					.findViewById(R.id.textRecyclerViewHora);
		   // CANCHA
			textRecyclerViewCancha = (TextView) itemView
					.findViewById(R.id.textRecyclerViewCancha);
			textRecyclerViewCancha.setVisibility(View.GONE);
			// RESULTADO LOCAL
			textRecyclerViewResultadoL = (TextView) itemView
					.findViewById(R.id.textRecyclerViewResultadoL);
			textRecyclerViewResultadoL.setVisibility(View.GONE);
     		// RESULTADO VISITA
			textRecyclerViewResultadoV = (TextView) itemView
					.findViewById(R.id.textRecyclerViewResultadoV);
			textRecyclerViewResultadoV.setVisibility(View.GONE);
		}

			public void bindTitular(Permiso permiso,Context context) {

			textRecyclerViewEquipoL.setText(permiso.getUSUARIO().toString());
			textRecyclerViewDia.setText("Permisos:");
			controladorAdeful = new ControladorAdeful(context);
			permisoDivisionArray =controladorAdeful.selectListaPermisoAdefulId(permiso.getID_PERMISO());
			if(permisoDivisionArray != null) {
				int dato = permisoDivisionArray.size();
				for (int i = 0; i < dato; i++) {
					if(dato!=i+1) {
						permisos += permisoDivisionArray.get(i).getSUBMODULO().toString() + "-";
					}else{
						permisos += permisoDivisionArray.get(i).getSUBMODULO().toString() + ".";
					}
				}
			}else{
				Toast.makeText(context, context.getResources().getString(R.string.error_data_base),
						Toast.LENGTH_SHORT).show();
			}
			textRecyclerViewHora.setText(permisos);
		}
	}
		public AdaptadorRecyclerEditarPermiso(ArrayList<Permiso> permisoArray, Context c) {
			this.permisoArray = permisoArray;
			this.context = c;
	}
	@Override
	public PermisoViewHolder onCreateViewHolder(ViewGroup viewGroup,
			int viewType) {
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.recyclerview_item_fixture, viewGroup, false);
		itemView.setOnClickListener(this);
		PermisoViewHolder tvh = new PermisoViewHolder(itemView);
		return tvh;
	}
	@Override
	public void onBindViewHolder(PermisoViewHolder viewHolder, int pos) {
		Permiso item = permisoArray.get(pos);
		viewHolder.bindTitular(item,context);
	}
	@Override
	public int getItemCount() {
		return permisoArray.size();
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
