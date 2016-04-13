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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerJugador;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;

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
    private ArrayList<Jugador> jugadorArray;
    private DialogoAlerta dialogoAlerta;
    private AdaptadorRecyclerJugador adaptadorEditarJugador;
	private ArrayAdapter<String> adaptadorInicial;
	private AuxiliarGeneral auxiliarGeneral;

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
      auxiliarGeneral = new AuxiliarGeneral(getActivity());
		// DIVISION
		divisionArray = controladorAdeful.selectListaDivisionAdeful();
		if(divisionArray != null) {
			// DIVSION SPINNER
			if (divisionArray.size() != 0) {
				adapterSpinnerDivision = new AdapterSpinnerDivision(getActivity(),
						R.layout.simple_spinner_dropdown_item, divisionArray);
				jugadoresDivisionSpinner.setAdapter(adapterSpinnerDivision);
			} else {
				//SPINNER HINT
				adaptadorInicial = new ArrayAdapter<String>(getActivity(),
						R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerDivision));
				jugadoresDivisionSpinner.setAdapter(adaptadorInicial);
			}
		}else{
		auxiliarGeneral.errorDataBase(getActivity());
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
						if (jugadoresDivisionSpinner.getSelectedItem().toString().equals(getResources().
								getString(R.string.ceroSpinnerDivision))) {
							Toast.makeText(getActivity(), "Debe agregar un division (Liga).",
									Toast.LENGTH_SHORT).show();
						}else {
							division = (Division) jugadoresDivisionSpinner.getSelectedItem();
							divisionSpinner = division.getID_DIVISION();
							recyclerViewLoadJugador(divisionSpinner);
						}
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
												"Desea eliminar el jugador?", null, null);
										dialogoAlerta.btnAceptar.setText("Aceptar");
										dialogoAlerta.btnCancelar.setText("Cancelar");

										dialogoAlerta.btnAceptar
												.setOnClickListener(new View.OnClickListener() {

													@Override
													public void onClick(View v) {
										controladorAdeful.eliminarJugadorAdeful(jugadorArray.get(position)
																.getID_JUGADOR());
										recyclerViewLoadJugador(divisionSpinner);
														Toast.makeText(
																getActivity(),
																"Jugador eliminado correctamente",
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
		jugadorArray = controladorAdeful.selectListaJugadorAdeful(division);
		if(jugadorArray != null) {
			adaptadorEditarJugador = new AdaptadorRecyclerJugador(jugadorArray, getActivity());
			recyclerViewJugador.setAdapter(adaptadorEditarJugador);
			if(jugadorArray.isEmpty())
				Toast.makeText(
						getActivity(),
						"Selección sin datos",
						Toast.LENGTH_SHORT).show();
		}else {
        auxiliarGeneral.errorDataBase(getActivity());
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