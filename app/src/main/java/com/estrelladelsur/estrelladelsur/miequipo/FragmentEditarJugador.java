package com.estrelladelsur.estrelladelsur.miequipo;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.JugadorRecycler;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerJugador;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;

public class FragmentEditarJugador extends Fragment {

	private Spinner jugadoresDivisionSpinner;
	private ControladorAdeful controladorAdeful;
	private int CheckedPositionFragment;
	private ArrayList<Division> divisionArray;
	private AdapterSpinnerDivision adapterSpinnerDivision;
	private RecyclerView recyclerViewJugador;
	private FloatingActionButton botonFloating;
    private Division division;
    private int divisionSpinner;
    private ArrayList<JugadorRecycler> jugadorArray;
    private DialogoAlerta dialogoAlerta;
    private AdaptadorRecyclerJugador adaptadorEditarJugador;

	public static FragmentEditarJugador newInstance() {
		FragmentEditarJugador fragment = new FragmentEditarJugador();
		return fragment;
	}

	public FragmentEditarJugador() {
		// Required empty public constructor
	}
	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);

		controladorAdeful = new ControladorAdeful(getActivity());

		if (state != null) {
			CheckedPositionFragment = state.getInt("curChoice", 0);
		} else {
			init();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_editar_jugador,
				container, false);

		// DIVISION
		jugadoresDivisionSpinner = (Spinner) v
				.findViewById(R.id.jugadoresDivisionSpinner);
		// RECYCLER
		recyclerViewJugador = (RecyclerView) v
				.findViewById(R.id.recycleViewGeneral);
		// BUTTON
		botonFloating = (FloatingActionButton) v
				.findViewById(R.id.botonFloating);

		return v;

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("curChoice", CheckedPositionFragment);
	}

	private void init() {

		// DIVISION
		controladorAdeful.abrirBaseDeDatos();
		divisionArray = controladorAdeful.selectListaDivisionAdeful();
		if(divisionArray != null) {
			controladorAdeful.cerrarBaseDeDatos();

			// DIVSION SPINNER
			adapterSpinnerDivision = new AdapterSpinnerDivision(getActivity(),
					R.layout.simple_spinner_dropdown_item, divisionArray);
			jugadoresDivisionSpinner.setAdapter(adapterSpinnerDivision);
		}else{
			controladorAdeful.cerrarBaseDeDatos();
			Toast.makeText(getActivity(), "Error en la base de datos interna, vuelva a intentar." +
							"\nSi el error persiste comuniquese con soporte.",
					Toast.LENGTH_SHORT).show();
		}
		// RECLYCLER
		recyclerViewJugador.setLayoutManager(new LinearLayoutManager(
						getActivity(), LinearLayoutManager.VERTICAL, false));
		recyclerViewJugador.addItemDecoration(new DividerItemDecoration(
						getActivity(), DividerItemDecoration.VERTICAL_LIST));
		recyclerViewJugador.setItemAnimator(new DefaultItemAnimator());

				botonFloating.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						division = (Division) jugadoresDivisionSpinner.getSelectedItem();
						divisionSpinner=division.getID_DIVISION();
						recyclerViewLoadJugador(divisionSpinner);
	      			}
				});
				
				recyclerViewJugador.addOnItemTouchListener(new 
						RecyclerTouchListener(getActivity(), 
								recyclerViewJugador, new ClickListener() {

									@Override
									public void onClick(View view, int position) {
										// TODO Auto-generated method stub
										
										Intent editarJugador = new Intent(getActivity(),
												TabsJugador.class);
										editarJugador.putExtra("actualizar", true);
										editarJugador.putExtra("id_jugador",
												jugadorArray.get(position).getID_JUGADOR());
										editarJugador.putExtra("divisionSpinner",divisionSpinner);
										editarJugador.putExtra("posicionSpinner",
												jugadorArray.get(position).getID_POSICION());
										editarJugador.putExtra("nombre",
												jugadorArray.get(position).getNOMBRE_JUGADOR());
										editarJugador.putExtra("foto",
												jugadorArray.get(position).getFOTO_JUGADOR());
																			
										startActivity(editarJugador);

									}

									@Override
									public void onLongClick(View view, final int position) {
										// TODO Auto-generated method stub
										
										
										dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
												"Desea Eliminar el Jugador?", null, null);
										dialogoAlerta.btnAceptar.setText("Aceptar");
										dialogoAlerta.btnCancelar.setText("Cancelar");

										dialogoAlerta.btnAceptar
												.setOnClickListener(new View.OnClickListener() {

													@Override
													public void onClick(View v) {
														// TODO Auto-generated method stub
														
														controladorAdeful.abrirBaseDeDatos();
														controladorAdeful.eliminarJugadorAdeful(jugadorArray.get(position)
																.getID_JUGADOR());
														controladorAdeful.cerrarBaseDeDatos();
														recyclerViewLoadJugador(divisionSpinner);
														Toast.makeText(
																getActivity(),
																"Jugador Eliminado Correctamente",
																Toast.LENGTH_SHORT).show();
        												dialogoAlerta.alertDialog.dismiss();
													}
												});
										dialogoAlerta.btnCancelar
												.setOnClickListener(new View.OnClickListener() {

													@Override
													public void onClick(View v) {
														// TODO Auto-generated method stub
														dialogoAlerta.alertDialog.dismiss();
													}
												});
									}
						}));
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	
	public void recyclerViewLoadJugador(int division) {

		controladorAdeful.abrirBaseDeDatos();
		jugadorArray = controladorAdeful.selectListaJugadorAdeful(division);
		if(jugadorArray != null) {
			controladorAdeful.cerrarBaseDeDatos();

			adaptadorEditarJugador = new AdaptadorRecyclerJugador(jugadorArray);
			adaptadorEditarJugador.notifyDataSetChanged();
			recyclerViewJugador.setAdapter(adaptadorEditarJugador);
		}else {
			controladorAdeful.cerrarBaseDeDatos();
			Toast.makeText(getActivity(), "Error en la base de datos interna, vuelva a intentar." +
							"\nSi el error persiste comuniquese con soporte.",
					Toast.LENGTH_SHORT).show();
		}
	}

	public static interface ClickListener {
		public void onClick(View view, int position);
		public void onLongClick(View view, int position);

	}

	static class RecyclerTouchListener implements
			RecyclerView.OnItemTouchListener {

		private GestureDetector detector;
		private ClickListener clickListener;

		public RecyclerTouchListener(Context context,
				final RecyclerView recyclerView,
				final ClickListener clickListener) {
			this.clickListener = clickListener;
			detector = new GestureDetector(context,
					new GestureDetector.SimpleOnGestureListener() {

						@Override
						public boolean onSingleTapUp(MotionEvent e) {
							return true;
						}

						@Override
						public void onLongPress(MotionEvent e) {
							View child = recyclerView.findChildViewUnder(
									e.getX(), e.getY());
							if (child != null && clickListener != null) {
								clickListener.onLongClick(child,
										recyclerView.getChildPosition(child));
							}
						}
					});
		}

		@Override
		public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
			// TODO Auto-generated method stub
			View child = rv.findChildViewUnder(e.getX(), e.getY());
			if (child != null && clickListener != null
					&& detector.onTouchEvent(e)) {
				clickListener.onClick(child, rv.getChildPosition(child));
			}
			return false;
		}

		@Override
		public void onTouchEvent(RecyclerView rv, MotionEvent e) {

		}

		@Override
		public void onRequestDisallowInterceptTouchEvent(boolean arg0) {
			// TODO Auto-generated method stub
		}
	}
}