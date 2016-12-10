package com.estrelladelsur.estrelladelsur.miequipo.administrador.adeful;

import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorEditarSancion;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerJugador;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.miequipo.administrador.tabs_adm.TabsSancion;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericAdeful;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import java.util.ArrayList;

public class FragmentEditarSancionAdeful extends Fragment implements MyAsyncTaskListener {

    private Division division;
    private Jugador jugadorRecycler;
    private ArrayList<Division> divisionArray;
    private ArrayList<Jugador> jugadorArray;
    private ArrayList<Sancion> sancionArray;
    private Spinner sancionDivisionSpinner;
    private Spinner sancionJugadorSpinner;
    private AdapterSpinnerJugador adapterSpinnerJugador;
    private AdapterSpinnerDivision adapterFixtureDivision;
    private AdaptadorEditarSancion adaptadorEditarSancion;
    private RecyclerView recyclerViewSancion;
    private FloatingActionButton botonFloating;
    private ControladorAdeful controladorAdeful;
    private int CheckedPositionFragment;
    private int divisionSpinner, jugadorSpinner;
    private DialogoAlerta dialogoAlerta;
    private ArrayAdapter<String> adaptadorInicial;
    private int id_division;
    private AuxiliarGeneral auxiliarGeneral;
    private LinearLayout linearTorneo;
    private Torneo torneoActual;
    private int posicion = 0;
    private String URL = null;
    private Request request = new Request();

    public static FragmentEditarSancionAdeful newInstance() {
        FragmentEditarSancionAdeful fragment = new FragmentEditarSancionAdeful();
        return fragment;
    }

    public FragmentEditarSancionAdeful() {
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
        linearTorneo = (LinearLayout) v.findViewById(R.id.linearTorneo);
        linearTorneo.setVisibility(View.GONE);

        // DIVISION
        sancionDivisionSpinner = (Spinner) v
                .findViewById(R.id.fixtureDivisionSpinner);
        // JUGADOR
        sancionJugadorSpinner = (Spinner) v
                .findViewById(R.id.fixtureTorneoSpinner);
        // FLOATING BUTTON
        botonFloating = (FloatingActionButton) v.findViewById(R.id.botonFloating);
        // RecyclerView
        recyclerViewSancion = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);

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
        init();
    }

    private void init() {
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        torneoActual = controladorAdeful.selectActualTorneoAdeful();

        // DIVISION
        divisionArray = controladorAdeful.selectListaDivisionAdeful();
        if (divisionArray != null) {
            // DIVSION SPINNER
            if (divisionArray.size() != 0) {
                adapterFixtureDivision = new AdapterSpinnerDivision(getActivity(),
                        R.layout.simple_spinner_dropdown_item, divisionArray);
                sancionDivisionSpinner.setAdapter(adapterFixtureDivision);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerDivision));
                sancionDivisionSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        //POPULATION SPINNER JUGADOR X ID DIVISION
        sancionDivisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                if (!divisionArray.isEmpty()) {
                    id_division = divisionArray.get(position).getID_DIVISION();
                    populationSpinnerJugador(id_division);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // RECLYCLER
        recyclerViewSancion.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewSancion.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerViewSancion.setItemAnimator(new DefaultItemAnimator());

        botonFloating.setOnClickListener(new View.OnClickListener() {

                                             @Override
                                             public void onClick(View v) {


                                                 if (torneoActual.getISACTUAL()) {

                                                     if (sancionDivisionSpinner.getSelectedItem().toString().equals(getResources().
                                                             getString(R.string.ceroSpinnerDivision))) {
                                                         showToast("Debe agregar un division (Liga).");
                                                     } else if (sancionJugadorSpinner.getSelectedItem().toString().equals(getResources().
                                                             getString(R.string.ceroSpinnerJugador))) {
                                                         showToast("Debe agregar un jugador.");
                                                     } else {
                                                         division = (Division) sancionDivisionSpinner.getSelectedItem();
                                                         jugadorRecycler = (Jugador) sancionJugadorSpinner.getSelectedItem();

                                                         divisionSpinner = division.getID_DIVISION();
                                                         jugadorSpinner = jugadorRecycler.getID_JUGADOR();

                                                         recyclerViewLoadSancion(divisionSpinner, jugadorSpinner, torneoActual.getID_TORNEO());
                                                     }
                                                 } else {
                                                     showToast("Debe seleccionar un torneo actual.");

                                                 }
                                             }
                                         }
        );
        recyclerViewSancion.addOnItemTouchListener(new
                RecyclerTouchListener(getActivity(),
                recyclerViewSancion, new
                ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        // TODO Auto-generated method stub

                        Intent editarSancion = new Intent(getActivity(),
                                TabsSancion.class);
                        editarSancion.putExtra("actualizar", true);
                        editarSancion.putExtra("id_sancion",
                                sancionArray.get(position).getID_SANCION());
                        editarSancion.putExtra("divisionSpinner", divisionSpinner);
                        editarSancion.putExtra("jugadorSpinner", jugadorSpinner);
                        editarSancion.putExtra("torneoSpinner", torneoActual.getID_TORNEO());
                        editarSancion.putExtra("amarillaSpinner", sancionArray.get(position).getAMARILLA());
                        editarSancion.putExtra("rojaSpinner", sancionArray.get(position).getROJA());
                        editarSancion.putExtra("fechaSpinner", sancionArray.get(position).getFECHA_SUSPENSION());
                        editarSancion.putExtra("observaciones", sancionArray.get(position).getOBSERVACIONES());

                        startActivity(editarSancion);
                    }

                    @Override
                    public void onLongClick(View view, final int position) {
                        dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                                "Desea eliminar la sanción?", null, null);
                        dialogoAlerta.btnAceptar.setText("Aceptar");
                        dialogoAlerta.btnCancelar.setText("Cancelar");
                        dialogoAlerta.btnAceptar
                                .setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        posicion = sancionArray.get(position)
                                                .getID_SANCION();
                                        envioWebService();
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
                }
        ));
    }

    public void envioWebService() {
        String fecha = auxiliarGeneral.getFechaOficial();
        request.setMethod("POST");
        request.setParametrosDatos("id_sancion", String.valueOf(posicion));
        request.setParametrosDatos("fecha_actualizacion", fecha);
        URL = null;
        URL = auxiliarGeneral.getURLSANCIONADEFULALL();
        URL = URL + auxiliarGeneral.getDeletePHP("Sancion");

        new AsyncTaskGenericAdeful(getActivity(), this, URL, request, "Sancion", true, posicion, "a", fecha);
    }

    public void populationSpinnerJugador(int id_division) {

        jugadorArray = controladorAdeful.selectListaJugadorAdeful(id_division);
        if (jugadorArray != null) {
            if (!jugadorArray.isEmpty()) {
                adapterSpinnerJugador = new AdapterSpinnerJugador(getActivity(),
                        R.layout.simple_spinner_dropdown_item, jugadorArray);
                sancionJugadorSpinner.setAdapter(adapterSpinnerJugador);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerJugador));
                sancionJugadorSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    public void showToast(String mgs) {
        Toast.makeText(getActivity(), mgs,
                Toast.LENGTH_SHORT).show();
    }

    //LOAD RECYCLER
    public void recyclerViewLoadSancion(int division, int jugador, int torneo) {
        sancionArray = controladorAdeful.selectListaSancionAdeful(division,
                jugador, torneo);
        if (sancionArray != null) {
            adaptadorEditarSancion = new AdaptadorEditarSancion(sancionArray, getActivity());
            recyclerViewSancion.setAdapter(adaptadorEditarSancion);
            if (sancionArray.isEmpty())
                showToast("Selección sin datos");
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    public void inicializarControles(String mensaje) {
        recyclerViewLoadSancion(divisionSpinner, jugadorSpinner, torneoActual.getID_TORNEO());
        showToast(mensaje);
        dialogoAlerta.alertDialog.dismiss();
        posicion = 0;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
