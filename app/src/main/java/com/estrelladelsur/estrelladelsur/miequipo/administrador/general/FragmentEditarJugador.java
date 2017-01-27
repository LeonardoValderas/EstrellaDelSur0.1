package com.estrelladelsur.estrelladelsur.miequipo.administrador.general;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerJugador;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.miequipo.administrador.tabs_general.TabsJugador;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericAdeful;
import com.estrelladelsur.estrelladelsur.webservice.Request;
import com.google.android.gms.ads.AdView;

public class FragmentEditarJugador extends Fragment implements MyAsyncTaskListener {

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
    private String URL = null, nombre_foto = null;
    private Request request = new Request();
    private int posicion = 0;
    private AdView mAdView;

    public static FragmentEditarJugador newInstance() {
        FragmentEditarJugador fragment = new FragmentEditarJugador();
        return fragment;
    }

    public FragmentEditarJugador() {
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
        mAdView = (AdView) v.findViewById(R.id.adView);
        mAdView.setVisibility(View.GONE);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        controladorAdeful = new ControladorAdeful(getActivity());
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        divisionArray = controladorAdeful.selectListaDivisionAdeful();
    }

    private void init() {
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        // DIVISION
        divisionArray = controladorAdeful.selectListaDivisionAdeful();
        if (divisionArray != null) {
            // DIVSION SPINNER
            if (divisionArray.size() != 0) {
                adapterSpinnerDivision = new AdapterSpinnerDivision(getActivity(),
                        R.layout.simple_spinner_dropdown_item, divisionArray);
                jugadoresDivisionSpinner.setAdapter(adapterSpinnerDivision);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerDivision));
                jugadoresDivisionSpinner.setAdapter(adaptadorInicial);
            }
        } else {
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
                } else {
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
                editarJugador.putExtra("divisionSpinner", divisionSpinner);
                editarJugador.putExtra("posicionSpinner",
                        jugadorArray.get(position).getID_POSICION());
                editarJugador.putExtra("nombre",
                        jugadorArray.get(position).getNOMBRE_JUGADOR());
                editarJugador.putExtra("nombre_foto",
                        jugadorArray.get(position).getNOMBRE_FOTO());
                editarJugador.putExtra("foto",
                        jugadorArray.get(position).getURL_JUGADOR());

                startActivity(editarJugador);

            }

            @Override
            public void onLongClick(View view, final int position) {
                dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                        "Desea eliminar el jugador?", null, null);
                dialogoAlerta.btnAceptar.setText("Aceptar");
                dialogoAlerta.btnCancelar.setText("Cancelar");

                dialogoAlerta.btnAceptar
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                posicion = jugadorArray.get(position)
                                        .getID_JUGADOR();
                                nombre_foto = jugadorArray.get(position)
                                        .getNOMBRE_FOTO();
                                envioWebService();
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

    public void envioWebService() {
        String fecha = auxiliarGeneral.getFechaOficial();
        request.setMethod("POST");
        request.setParametrosDatos("id_jugador", String.valueOf(posicion));

        if(nombre_foto != null)
        request.setParametrosDatos("nombre_foto",nombre_foto);

        request.setParametrosDatos("fecha_actualizacion", fecha);
        URL = null;
        URL = auxiliarGeneral.getURLJUGADORADEFULAll();
        URL = URL + auxiliarGeneral.getDeletePHP("Jugador");

        if (auxiliarGeneral.isNetworkAvailable(getActivity()))
            new AsyncTaskGenericAdeful(getActivity(), this, URL, request, "Jugador", true, posicion, "o", fecha);
        else
            auxiliarGeneral.errorWebService(getActivity(), getActivity().getResources().getString(R.string.error_without_internet));
    }

    public void inicializarControles(String mensaje) {
        recyclerViewLoadJugador(divisionSpinner);
        posicion = 0;
        nombre_foto = null;
        dialogoAlerta.alertDialog.dismiss();
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void recyclerViewLoadJugador(int division) {
        jugadorArray = controladorAdeful.selectListaJugadorAdeful(division);
        if (jugadorArray != null) {
            adaptadorEditarJugador = new AdaptadorRecyclerJugador(jugadorArray, getActivity());
            recyclerViewJugador.setAdapter(adaptadorEditarJugador);
            if (jugadorArray.isEmpty())
                Toast.makeText(
                        getActivity(),
                        "Selección sin datos",
                        Toast.LENGTH_SHORT).show();
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    @Override
    public void onPostExecuteConcluded(boolean result, String mensaje) {
        if (result) {
            inicializarControles(mensaje);
        } else {
            auxiliarGeneral.errorWebService(getActivity(), mensaje);
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
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_administrador_general, menu);
        menu.getItem(1).setVisible(false);// posicion
        menu.getItem(2).setVisible(false);// cargo
        menu.getItem(4).setVisible(false);// guardar

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_usuario) {
            auxiliarGeneral.goToUser(getActivity());
            return true;
        }
        if (id == R.id.action_cerrar) {
            auxiliarGeneral.close(getActivity());
        }
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(getActivity());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}