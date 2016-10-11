package com.estrelladelsur.estrelladelsur.miequipo.adeful;

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
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.Fixture;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerFixture;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerFecha;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerTorneo;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.miequipo.tabs_adm.TabsFixture;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGeneric;
import com.estrelladelsur.estrelladelsur.webservice.Request;

public class FragmentEditarFixtureAdeful extends Fragment implements MyAsyncTaskListener {

    private Division division;
    private Torneo torneo;
    private Fecha fecha;
    private Anio anio;
    private ArrayList<Division> divisionArray;
    private ArrayList<Torneo> torneoArray;
    private ArrayList<Fecha> fechaArray;
    private ArrayList<Anio> anioArray;
    private ArrayList<Fixture> fixtureArray;
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
    private ArrayAdapter<String> adaptadorInicial;
    private AuxiliarGeneral auxiliarGeneral;
    private String URL = null;
    private Request request = new Request();
    private int posicion = 0;

    public static FragmentEditarFixtureAdeful newInstance() {
        FragmentEditarFixtureAdeful fragment = new FragmentEditarFixtureAdeful();
        return fragment;
    }

    public FragmentEditarFixtureAdeful() {
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
        // FLOATING BUTTON
        botonFloating = (FloatingActionButton) v.findViewById(R.id.botonFloating);
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
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        // DIVISION
        divisionArray = controladorAdeful.selectListaDivisionAdeful();
        if (divisionArray != null) {
            // DIVSION SPINNER
            if (!divisionArray.isEmpty()) {
                adapterFixtureDivision = new AdapterSpinnerDivision(getActivity(),
                        R.layout.simple_spinner_dropdown_item, divisionArray);
                fixtureDivisionSpinner.setAdapter(adapterFixtureDivision);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerDivision));
                fixtureDivisionSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        // TORNEO
        torneoArray = controladorAdeful.selectListaTorneoAdeful();
        if (torneoArray != null) {
            // TORNEO SPINNER
            if (!torneoArray.isEmpty()) {
                // TORNEO SPINNER
                adapterFixtureTorneo = new AdapterSpinnerTorneo(getActivity(),
                        R.layout.simple_spinner_dropdown_item, torneoArray);
                fixtureTorneoSpinner.setAdapter(adapterFixtureTorneo);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerTorneo));
                fixtureTorneoSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        // FECHA
        fechaArray = controladorAdeful.selectListaFecha();
        if (fechaArray != null) {
            // FECHA SPINNER
            adapterFixtureFecha = new AdapterSpinnerFecha(getActivity(),
                    R.layout.simple_spinner_dropdown_item, fechaArray);
            fixtureFechaSpinner.setAdapter(adapterFixtureFecha);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        // ANIO
        anioArray = controladorAdeful.selectListaAnio();
        if (anioArray != null) {
            // ANIO SPINNER
            adapterFixtureAnio = new AdapterSpinnerAnio(getActivity(),
                    R.layout.simple_spinner_dropdown_item, anioArray);
            fixtureAnioSpinner.setAdapter(adapterFixtureAnio);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }

        // RECLYCLER
        recyclerViewFixture.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewFixture.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerViewFixture.setItemAnimator(new DefaultItemAnimator());

        botonFloating.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (fixtureDivisionSpinner.getSelectedItem().toString().equals(getResources().
                        getString(R.string.ceroSpinnerDivision))) {
                    Toast.makeText(getActivity(), "Debe agregar un division (Liga).",
                            Toast.LENGTH_SHORT).show();
                } else if (fixtureTorneoSpinner.getSelectedItem().toString().equals(getResources().
                        getString(R.string.ceroSpinnerTorneo))) {
                    Toast.makeText(getActivity(), "Debe agregar un torneo (Liga).",
                            Toast.LENGTH_SHORT).show();
                } else {
                    division = (Division) fixtureDivisionSpinner.getSelectedItem();
                    torneo = (Torneo) fixtureTorneoSpinner.getSelectedItem();
                    fecha = (Fecha) fixtureFechaSpinner.getSelectedItem();
                    anio = (Anio) fixtureAnioSpinner.getSelectedItem();

                    divisionSpinner = division.getID_DIVISION();
                    torneoSpinner = torneo.getID_TORNEO();
                    fechaSpinner = fecha.getID_FECHA();
                    anioSpiner = anio.getID_ANIO();

                    recyclerViewLoadDivision(divisionSpinner, torneoSpinner, fechaSpinner, anioSpiner);
                }
            }
        });

        recyclerViewFixture.addOnItemTouchListener(new
                RecyclerTouchListener(getActivity(),

                recyclerViewFixture, new ClickListener() {

            @Override
            public void onClick(View view, int position) {

                Intent editarFixture = new Intent(getActivity(),
                        TabsFixture.class);
                editarFixture.putExtra("actualizar", true);
                editarFixture.putExtra("id_fixture",
                        fixtureArray.get(position).getID_FIXTURE());
                editarFixture.putExtra("divisionSpinner", divisionSpinner);
                editarFixture.putExtra("torneoSpinner", torneoSpinner);
                editarFixture.putExtra("fechaSpinner", fechaSpinner);
                editarFixture.putExtra("anioSpiner", anioSpiner);
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
                        "Desea eliminar el partido?", null, null);
                dialogoAlerta.btnAceptar.setText("Aceptar");
                dialogoAlerta.btnCancelar.setText("Cancelar");

                dialogoAlerta.btnAceptar
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                posicion = fixtureArray.get(position)
                                        .getID_FIXTURE();
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

    //LOAD RECYCLER
    public void recyclerViewLoadDivision(int division, int torneo, int fecha,
                                         int anio) {
        fixtureArray = controladorAdeful.selectListaFixtureAdeful(division,
                torneo, fecha, anio);
        if (fixtureArray != null) {
            adaptadorFixtureEdit = new AdaptadorRecyclerFixture(fixtureArray, getActivity());
            recyclerViewFixture.setAdapter(adaptadorFixtureEdit);
            if (fixtureArray.isEmpty()) {
                Toast.makeText(
                        getActivity(),
                        "Selección sin datos",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    public void envioWebService() {
        request.setMethod("POST");
        request.setParametrosDatos("id_fixture", String.valueOf(posicion));
        request.setParametrosDatos("fecha_actualizacion", auxiliarGeneral.getFechaOficial());
        URL = null;
        URL = auxiliarGeneral.getURLFIXTUREADEFULAll();
        URL = URL + auxiliarGeneral.getDeletePHP("Fixture");

        new AsyncTaskGeneric(getActivity(), this, URL, request, "Fixture", true, posicion, "o");
    }

    public void inicializarControles(String mensaje) {
        recyclerViewLoadDivision(divisionSpinner, torneoSpinner, fechaSpinner, anioSpiner);
        posicion = 0;
        dialogoAlerta.alertDialog.dismiss();
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
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
}
