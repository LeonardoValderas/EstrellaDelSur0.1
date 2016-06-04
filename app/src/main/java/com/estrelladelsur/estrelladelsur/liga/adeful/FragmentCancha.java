package com.estrelladelsur.estrelladelsur.liga.adeful;

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

import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerCancha;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;

public class FragmentCancha extends Fragment {

	private DialogoAlerta dialogoAlerta;
	private RecyclerView recycleViewCancha;
	private EditText editTextNombre;
	private ImageView imageButtonCancha;
	private ArrayList<Cancha> canchaAdefulArray;
	private AdaptadorRecyclerCancha adaptadorCancha;
	private ControladorAdeful controladorAdeful;
	private int CheckedPositionFragment;
	private AuxiliarGeneral auxiliarGeneral;

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
		editTextNombre.setVisibility(View.GONE);
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
	    auxiliarGeneral = new AuxiliarGeneral(getActivity());
		imageButtonCancha.setImageResource(R.mipmap.ic_mapa_icon);
		imageButtonCancha.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent mapa = new Intent(getActivity(), MapaCancha.class);
				startActivity(mapa);
			}
		});
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
										if(controladorAdeful.eliminarCanchaAdeful(canchaAdefulArray.get(position)
												.getID_CANCHA())) {
											recyclerViewLoadCancha();
											Toast.makeText(
													getActivity(),
													"Cancha eliminada correctamente",
													Toast.LENGTH_SHORT).show();
											dialogoAlerta.alertDialog.dismiss();
										}else{
		                                auxiliarGeneral.errorDataBase(getActivity());								}
									}
								});
						dialogoAlerta.btnCancelar
								.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										dialogoAlerta.alertDialog.dismiss();
									}
								});
					}

					@Override
					public void onClick(View view, int position) {
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

    	canchaAdefulArray = controladorAdeful.selectListaCanchaAdeful();
		if(canchaAdefulArray != null) {
			adaptadorCancha = new AdaptadorRecyclerCancha(canchaAdefulArray, getActivity());
			recycleViewCancha.setAdapter(adaptadorCancha);
		}else{
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
