package com.estrelladelsur.estrelladelsur.liga;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.abstracta.Division;
import com.estrelladelsur.estrelladelsur.abstracta.Equipo;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerDivision;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;

public class FragmentDivision extends Fragment {

	private DialogoAlerta dialogoAlerta;
	private RecyclerView recycleViewDivision;
	private ArrayList<Division> divisionArray;
	private Division division;
	private AdaptadorRecyclerDivision adaptadorDivision;
	private EditText editTextDivision;
	private boolean insertar = true;
	private int posicion;
	private ControladorAdeful controladorAdeful;
	private int CheckedPositionFragment;
	private ImageView imageButtonEquipo ;
	
	public static FragmentDivision newInstance() {
		FragmentDivision fragment = new FragmentDivision();
		return fragment;
	}

	public FragmentDivision() {
		// Required empty public constructor
	}

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
		controladorAdeful = new ControladorAdeful(getActivity());
		 if (state != null) {
			 CheckedPositionFragment = state.getInt("curChoice", 0);
			}
			else{
			init();
			}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v= inflater.inflate(R.layout.fragment_general_liga, container,
				false);
		editTextDivision = (EditText) v.findViewById(
				R.id.editTextDescripcion);
		imageButtonEquipo= (ImageView) v.findViewById(
				R.id.imageButtonEquipo_Cancha);
		recycleViewDivision = (RecyclerView) v.findViewById(
				R.id.recycleViewGeneral);
		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putInt("curChoice", CheckedPositionFragment);
	}
	
	
	private void init() {

		editTextDivision.setHint("Ingrese una División");
		editTextDivision.setHintTextColor(Color.GRAY);
		imageButtonEquipo.setVisibility(View.GONE);

		recyclerViewLoadDivision();
		recycleViewDivision.addOnItemTouchListener(new RecyclerTouchListener(
				getActivity(), recycleViewDivision, new ClickListener() {

			@Override
			public void onLongClick(View view, final int position) {

				dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
						"Desea eliminar la división?", null, null);
				dialogoAlerta.btnAceptar.setText("Aceptar");
				dialogoAlerta.btnCancelar.setText("Cancelar");

				dialogoAlerta.btnAceptar
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								controladorAdeful.abrirBaseDeDatos();
								if (controladorAdeful.eliminarDivisionAdeful(divisionArray.get(position)
										.getID_DIVISION())) {
									controladorAdeful.cerrarBaseDeDatos();
									recyclerViewLoadDivision();
									Toast.makeText(
											getActivity(),
											"División Eliminada Correctamente",
											Toast.LENGTH_SHORT).show();
									dialogoAlerta.alertDialog.dismiss();

								} else {
									controladorAdeful.cerrarBaseDeDatos();
									Toast.makeText(getActivity(), "Error en la base de datos interna, vuelva a intentar." +
													"\n Si el error persiste comuniquese con soporte.",
											Toast.LENGTH_SHORT).show();
								}
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

			@Override
			public void onClick(View view, int position) {
				// TODO Auto-generated method stub

				insertar = false;
				editTextDivision.setText(divisionArray.get(position)
						.getDESCRIPCION());

				posicion = position;
			}
		}));
		}

	public void recyclerViewLoadDivision() {
		recycleViewDivision.setLayoutManager(new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false));
		recycleViewDivision.addItemDecoration(new DividerItemDecoration(
				getActivity(), DividerItemDecoration.VERTICAL_LIST));
		recycleViewDivision.setItemAnimator(new DefaultItemAnimator());

		controladorAdeful.abrirBaseDeDatos();
		divisionArray = controladorAdeful.selectListaDivisionAdeful();
		if(divisionArray != null) {
			controladorAdeful.cerrarBaseDeDatos();

			adaptadorDivision = new AdaptadorRecyclerDivision(divisionArray);
			adaptadorDivision.notifyDataSetChanged();
			recycleViewDivision.setAdapter(adaptadorDivision);
		}else{
			controladorAdeful.cerrarBaseDeDatos();
			Toast.makeText(getActivity(), "Error en la base de datos interna, vuelva a intentar." +
							"\n Si el error persiste comuniquese con soporte.",
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
		inflater.inflate(R.menu.menu_administrador_general, menu);
		// menu.getItem(0).setVisible(false);//usuario
		// menu.getItem(1).setVisible(false);//permiso
		// menu.getItem(2).setVisible(false);//lifuba
		menu.getItem(3).setVisible(false);// adeful
		menu.getItem(4).setVisible(false);// puesto
		menu.getItem(5).setVisible(false);// posicion
		// menu.getItem(6).setVisible(false);// cargo
		// menu.getItem(7).setVisible(false);//cerrar
		// menu.getItem(8).setVisible(false);// guardar
		menu.getItem(9).setVisible(false);// Subir
		menu.getItem(10).setVisible(false); // eliminar
		menu.getItem(11).setVisible(false); // consultar
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == R.id.action_usuario) {

            /*Intent usuario = new Intent(getActivity(),
                    NavigationDrawerUsuario.class);
            startActivity(usuario);*/

			return true;
		}

		if (id == R.id.action_permisos) {
			return true;
		}

		if (id == R.id.action_guardar) {
			if (editTextDivision.getText().toString().equals("")) {
				Toast.makeText(getActivity(), "Ingrese la Divisi�n.",
						Toast.LENGTH_SHORT).show();

			} else {

				if (insertar) {

					String usuario = "Administrador";
					String fechaCreacion = controladorAdeful.getFechaOficial();
					String fechaActualizacion = fechaCreacion;


					division = new Division(0, editTextDivision.getText()
							.toString(), usuario, fechaCreacion,
							usuario,fechaActualizacion);

					controladorAdeful.abrirBaseDeDatos();
					if(controladorAdeful.insertDivisionAdeful(division)) {
						controladorAdeful.cerrarBaseDeDatos();
						recyclerViewLoadDivision();
						editTextDivision.setText("");
						Toast.makeText(getActivity(),
								"Divisi�n cargada correctamente.",
								Toast.LENGTH_SHORT).show();
					}else{
						controladorAdeful.cerrarBaseDeDatos();
						Toast.makeText(getActivity(), "Error en la base de datos interna, vuelva a intentar." +
										"\n Si el error persiste comuniquese con soporte.",
								Toast.LENGTH_SHORT).show();
					}
				} else {

					String usuario = "Administrador";
					String fechaActualizacion = controladorAdeful
							.getFechaOficial();

					division = new Division(divisionArray.get(posicion)
							.getID_DIVISION(), editTextDivision.getText()
							.toString(), null, null, usuario,fechaActualizacion);

					controladorAdeful.abrirBaseDeDatos();
					if(controladorAdeful.actualizarDivisionAdeful(division)) {
						controladorAdeful.cerrarBaseDeDatos();
						recyclerViewLoadDivision();
						editTextDivision.setText("");
						insertar = true;
						Toast.makeText(getActivity(),
								"Divisi�n actualizada correctamente.",
								Toast.LENGTH_SHORT).show();
					}else{
						controladorAdeful.cerrarBaseDeDatos();
						Toast.makeText(getActivity(), "Error en la base de datos interna, vuelva a intentar." +
										"\n Si el error persiste comuniquese con soporte.",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
			return true;
		}
		if (id == android.R.id.home) {

			NavUtils.navigateUpFromSameTask(getActivity());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}