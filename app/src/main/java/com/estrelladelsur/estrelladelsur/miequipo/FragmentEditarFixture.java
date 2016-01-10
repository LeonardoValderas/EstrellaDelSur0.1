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

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.abstracta.Anio;
import com.estrelladelsur.estrelladelsur.abstracta.Division;
import com.estrelladelsur.estrelladelsur.abstracta.Fecha;
import com.estrelladelsur.estrelladelsur.abstracta.Torneo;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerFixture;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerFecha;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerTorneo;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;

public class FragmentEditarFixture extends Fragment {

	private Division division;
	private Torneo torneo;
	private Fecha fecha;
	private Anio anio;
	private ArrayList<Division> divisionArray;
	private ArrayList<Torneo> torneoArray;
	private ArrayList<Fecha> fechaArray;
	private ArrayList<Anio> anioArray;
	//private ArrayList<FixtureRecycler> fixtureArray;
	private Spinner fixtureDivisionSpinner;
	private Spinner fixtureTorneoSpinner;
	private Spinner fixtureFechaSpinner;
	private Spinner fixtureAnioSpinner;
	private AdapterSpinnerTorneo adapterFixtureTorneo;
	private AdapterSpinnerDivision adapterFixtureDivision;
	private AdapterSpinnerFecha adapterFixtureFecha;
	private AdapterSpinnerAnio adapterFixtureAnio;
	private AdaptadorRecyclerFixture adaptadorFixtureEdit;
	private RecyclerView recyclerViewFixture;
	private FloatingActionButton botonFloating;
	private ControladorAdeful controladorAdeful;
	private int CheckedPositionFragment;
	private int divisionSpinner, torneoSpinner, fechaSpinner, anioSpiner;
	private DialogoAlerta dialogoAlerta;

	public static FragmentEditarFixture newInstance() {
		FragmentEditarFixture fragment = new FragmentEditarFixture();
		return fragment;
	}

	public FragmentEditarFixture() {
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
		View v = inflater.inflate(R.layout.fragment_editar_fixture,
				container, false);
		// DIVISION
		fixtureDivisionSpinner = (Spinner) v
				.findViewById(R.id.fixtureDivisionSpinner);
		// TORNEO
		fixtureTorneoSpinner = (Spinner) v
				.findViewById(R.id.fixtureTorneoSpinner);
		// FECHA
		fixtureFechaSpinner = (Spinner) v
				.findViewById(R.id.fixtureFechaSpinner);
		// ANIO
		fixtureAnioSpinner = (Spinner) v.findViewById(R.id.fixtureAnioSpinner);
		// RecyclerView
		recyclerViewFixture = (RecyclerView) v
				.findViewById(R.id.recycleViewGeneral);

		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("curChoice", CheckedPositionFragment);
	}

	private void init() {

		/*// DIVISION
		controladorAdeful.abrirBaseDeDatos();
		divisionArray = controladorAdeful.selectListaDivisionAdeful();
		controladorAdeful.cerrarBaseDeDatos();
		// TORNEO
		controladorAdeful.abrirBaseDeDatos();
		torneoArray = controladorAdeful.selectListaTorneoAdeful();
		controladorAdeful.cerrarBaseDeDatos();
		// FECHA
		controladorAdeful.abrirBaseDeDatos();
		fechaArray = controladorAdeful.selectListaFecha();
		controladorAdeful.cerrarBaseDeDatos();
		// ANIO
		controladorAdeful.abrirBaseDeDatos();
		anioArray = controladorAdeful.selectListaAnio();
		controladorAdeful.cerrarBaseDeDatos();

		// DIVSION SPINNER
		adapterFixtureDivision = new AdapterSpinnerDivision(getActivity(),
				R.layout.simple_spinner_dropdown_item, divisionArray);
		fixtureDivisionSpinner.setAdapter(adapterFixtureDivision);
		// TORNEO SPINNER
		adapterFixtureTorneo = new AdapterSpinnerTorneo(getActivity(),
				R.layout.simple_spinner_dropdown_item, torneoArray);
		fixtureTorneoSpinner.setAdapter(adapterFixtureTorneo);
		// FECHA SPINNER
		adapterFixtureFecha = new AdapterSpinnerFecha(getActivity(),
				R.layout.simple_spinner_dropdown_item, fechaArray);
		fixtureFechaSpinner.setAdapter(adapterFixtureFecha);
		// ANIO SPINNER
		adapterFixtureAnio = new AdapterSpinnerAnio(getActivity(),
				R.layout.simple_spinner_dropdown_item, anioArray);
		fixtureAnioSpinner.setAdapter(adapterFixtureAnio);
		// RECLYCLER
		recyclerViewFixture.setLayoutManager(new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false));
		recyclerViewFixture.addItemDecoration(new DividerItemDecoration(
				getActivity(), DividerItemDecoration.VERTICAL_LIST));
		recyclerViewFixture.setItemAnimator(new DefaultItemAnimator());

		botonFloating.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				division = (Division) fixtureDivisionSpinner.getSelectedItem();
				torneo = (Torneo) fixtureTorneoSpinner.getSelectedItem();
				fecha = (Fecha) fixtureFechaSpinner.getSelectedItem();
				anio = (Anio) fixtureAnioSpinner.getSelectedItem();
				
				divisionSpinner=division.getID_DIVISION();
				torneoSpinner =  torneo.getID_TORNEO();
				fechaSpinner =  fecha.getID_FECHA();
				anioSpiner =  anio.getID_ANIO();
				
				recyclerViewLoadDivision(divisionSpinner,torneoSpinner,fechaSpinner,anioSpiner);
				
				
			}
		});
		
		recyclerViewFixture.addOnItemTouchListener(new 
				RecyclerTouchListener(getActivity(), 
						recyclerViewFixture, new ClickListener() {

							@Override
							public void onClick(View view, int position) {
								// TODO Auto-generated method stub
								
								Intent editarFixture = new Intent(getActivity(),
										TabsFixture.class);
								editarFixture.putExtra("actualizar", true);
								editarFixture.putExtra("id_fixture",
										fixtureArray.get(position).getID_FIXTURE());
								editarFixture.putExtra("divisionSpinner",divisionSpinner);
								editarFixture.putExtra("torneoSpinner",torneoSpinner);
								editarFixture.putExtra("fechaSpinner",fechaSpinner);
								editarFixture.putExtra("anioSpiner",anioSpiner);
								editarFixture.putExtra("localSpinner",
										fixtureArray.get(position).getID_EQUIPO_LOCAL());
								editarFixture.putExtra("visitaSpinner",
										fixtureArray.get(position).getID_EQUIPO_VISITA());
								editarFixture.putExtra("canchaSpinner",
										fixtureArray.get(position).getID_CANCHA());
								editarFixture.putExtra("dia",
										fixtureArray.get(position).getDIA());
								editarFixture.putExtra("hora",
										fixtureArray.get(position).getHORA());
								
							
								startActivity(editarFixture);

								
							}

							@Override
							public void onLongClick(View view, final int position) {
								// TODO Auto-generated method stub
								
								
								dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
										"Desea eliminar el Partido?", null, null);
								dialogoAlerta.btnAceptar.setText("Aceptar");
								dialogoAlerta.btnCancelar.setText("Cancelar");

								dialogoAlerta.btnAceptar
										.setOnClickListener(new View.OnClickListener() {

											@SuppressLint("NewApi")
											@Override
											public void onClick(View v) {
												// TODO Auto-generated method stub
												
												controladorAdeful.abrirBaseDeDatos();
												controladorAdeful.eliminarEquipoAdeful(fixtureArray.get(position)
														.getID_FIXTURE());
												controladorAdeful.cerrarBaseDeDatos();

												recyclerViewLoadDivision(divisionSpinner,torneoSpinner,fechaSpinner,anioSpiner);

												Toast.makeText(
														getActivity(),
														"Fixture Eliminado Correctamente",
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

	public void recyclerViewLoadDivision(int division, int torneo, int fecha,
			int anio) {

		controladorAdeful.abrirBaseDeDatos();
		fixtureArray = controladorAdeful.selectListaFixtureAdeful(division,
				torneo, fecha, anio);
		controladorAdeful.cerrarBaseDeDatos();

		adaptadorFixtureEdit = new AdaptadorEditarFixture(fixtureArray);
		adaptadorFixtureEdit.notifyDataSetChanged();
		recyclerViewFixture.setAdapter(adaptadorFixtureEdit);

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
*/
	}
}