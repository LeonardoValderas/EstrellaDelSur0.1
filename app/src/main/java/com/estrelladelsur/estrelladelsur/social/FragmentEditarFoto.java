package com.estrelladelsur.estrelladelsur.social;

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
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerFoto;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Foto;

import java.util.ArrayList;

public class FragmentEditarFoto extends Fragment {

	private ControladorAdeful controladorAdeful;
	private int CheckedPositionFragment;
	private RecyclerView recyclerViewFoto;
    private ArrayList<Foto> fotoArray;
    private DialogoAlerta dialogoAlerta;
    private AdaptadorRecyclerFoto adaptadorEditarFoto;
	private AuxiliarGeneral auxiliarGeneral;

	public static FragmentEditarFoto newInstance() {
		FragmentEditarFoto fragment = new FragmentEditarFoto();
		return fragment;
	}

	public FragmentEditarFoto() {
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
		View v = inflater.inflate(R.layout.fragment_editar_recyclerview,
				container, false);
		// RECYCLER
		recyclerViewFoto = (RecyclerView) v
				.findViewById(R.id.recycleViewGeneral);
		return v;
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("curChoice", CheckedPositionFragment);
	}

	private void init() {
      auxiliarGeneral = new AuxiliarGeneral(getActivity());
		// RECLYCLER
		recyclerViewFoto.setLayoutManager(new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false));
		recyclerViewFoto.addItemDecoration(new DividerItemDecoration(
				getActivity(), DividerItemDecoration.VERTICAL_LIST));
		recyclerViewFoto.setItemAnimator(new DefaultItemAnimator());

        recyclerViewLoadFoto();

    	recyclerViewFoto.addOnItemTouchListener(new
				RecyclerTouchListener(getActivity(),
				recyclerViewFoto, new ClickListener() {

			@Override
			public void onClick(View view, int position) {
				// TODO Auto-generated method stub

				Intent editarFoto = new Intent(getActivity(),
						TabsFoto.class);
				editarFoto.putExtra("actualizar", true);
				editarFoto.putExtra("id_foto",
						fotoArray.get(position).getID_FOTO());
				editarFoto.putExtra("titulo",
						fotoArray.get(position).getTITULO());
				editarFoto.putExtra("foto",
						fotoArray.get(position).getFOTO());

				startActivity(editarFoto);
			}

			@Override
			public void onLongClick(View view, final int position) {
				// TODO Auto-generated method stub


				dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
						"Desea eliminar la foto?", null, null);
				dialogoAlerta.btnAceptar.setText("Aceptar");
				dialogoAlerta.btnCancelar.setText("Cancelar");

				dialogoAlerta.btnAceptar
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								controladorAdeful.eliminarFotoAdeful(fotoArray.get(position)
										.getID_FOTO());
								recyclerViewLoadFoto();
								Toast.makeText(
										getActivity(),
										"Foto eliminada correctamente",
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

	public void recyclerViewLoadFoto() {

		fotoArray = controladorAdeful.selectListaFotoAdeful();
		if(fotoArray != null) {
			adaptadorEditarFoto = new AdaptadorRecyclerFoto(fotoArray, getActivity());
			recyclerViewFoto.setAdapter(adaptadorEditarFoto);
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