package com.estrelladelsur.estrelladelsur.liga;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerCancha;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;

public class FragmentCancha extends Fragment {

	private DialogoAlerta dialogoAlerta;
	private RecyclerView recycleViewCancha;
	private EditText editTextNombre;
	private ImageView imageButtonCancha;
	private ArrayList<Cancha> canchaAdefulArray;
	private AdaptadorRecyclerCancha adaptadorCancha;
	private ControladorAdeful controladorAdeful;
	private int CheckedPositionFragment;
	
	public static FragmentCancha newInstance() {
		FragmentCancha fragment = new FragmentCancha();
		return fragment;
	}

	public FragmentCancha() {
		// Required empty public constructor
	}

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
		controladorAdeful = new ControladorAdeful(getActivity());
		 if (state != null) {
			 CheckedPositionFragment = state.getInt("curChoice", 0);
			}else{
			init();
			}
		}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v= inflater.inflate(R.layout.fragment_general_liga, container,
				false);
		imageButtonCancha = (ImageView) v.findViewById(
				R.id.imageButtonEquipo_Cancha);
		editTextNombre = (EditText) v.findViewById(
				R.id.editTextDescripcion);

		recycleViewCancha = (RecyclerView) v.findViewById(
				R.id.recycleViewGeneral);
		 return v;

	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putInt("curChoice", CheckedPositionFragment);
	}
	private void init() {

		/***
		 * imageButton que busca imagen que activa el mapa
		 */
	
		imageButtonCancha.setImageResource(R.drawable.ic_mapa_icono);
		imageButtonCancha.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//
				Intent mapa = new Intent(getActivity(), MapaCancha.class);
				startActivity(mapa);
			}
		});

		editTextNombre.setVisibility(View.GONE);
		recyclerViewLoadCancha();
		recycleViewCancha.addOnItemTouchListener(new RecyclerTouchListener(
				getActivity(), recycleViewCancha, new ClickListener() {

					@Override
					public void onLongClick(View view, final int position) {

						dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
								"Desea eliminar la cancha?", null, null);
						dialogoAlerta.btnAceptar.setText("Aceptar");
						dialogoAlerta.btnCancelar.setText("Cancelar");

						dialogoAlerta.btnAceptar
								.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										controladorAdeful.abrirBaseDeDatos();
										if(controladorAdeful.eliminarCanchaAdeful(canchaAdefulArray.get(position)
												.getID_CANCHA())) {
											controladorAdeful.cerrarBaseDeDatos();
											recyclerViewLoadCancha();
											Toast.makeText(
													getActivity(),
													"Cancha Eliminada Correctamente",
													Toast.LENGTH_SHORT).show();
											dialogoAlerta.alertDialog.dismiss();
										}else{
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

						Intent mapa = new Intent(getActivity(),
								MapaCancha.class);
						mapa.putExtra("actualizar", true);
						mapa.putExtra("nombre", canchaAdefulArray.get(position)
								.getNOMBRE());
						mapa.putExtra("longitud",
								canchaAdefulArray.get(position).getLONGITUD());
						mapa.putExtra("latitud", canchaAdefulArray
								.get(position).getLATITUD());
						mapa.putExtra("direccion",
								canchaAdefulArray.get(position).getDIRECCION());
						mapa.putExtra("posicion", position);

						startActivity(mapa);

					}
				}));
	}

	public void recyclerViewLoadCancha() {
		recycleViewCancha.setLayoutManager(new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false));
		recycleViewCancha.addItemDecoration(new DividerItemDecoration(
				getActivity(), DividerItemDecoration.VERTICAL_LIST));
		recycleViewCancha.setItemAnimator(new DefaultItemAnimator());

		controladorAdeful.abrirBaseDeDatos();
		canchaAdefulArray = controladorAdeful.selectListaCanchaAdeful();
		if(canchaAdefulArray != null) {
			controladorAdeful.cerrarBaseDeDatos();

			adaptadorCancha = new AdaptadorRecyclerCancha(canchaAdefulArray);
			adaptadorCancha.notifyDataSetChanged();
			recycleViewCancha.setAdapter(adaptadorCancha);
		}else{
			controladorAdeful.cerrarBaseDeDatos();
			Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
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
