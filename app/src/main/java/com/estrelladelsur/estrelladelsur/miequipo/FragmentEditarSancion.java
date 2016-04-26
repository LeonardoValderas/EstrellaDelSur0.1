package com.estrelladelsur.estrelladelsur.miequipo;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorEditarSancion;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerJugador;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerTorneo;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import java.util.ArrayList;

public class FragmentEditarSancion extends Fragment {

    private Division division;
    private Torneo torneo;
    private Anio anio;
    private Jugador jugadorRecycler;
    private ArrayList<Division> divisionArray;
    private ArrayList<Jugador> jugadorArray;
    private ArrayList<Torneo> torneoArray;
    private ArrayList<Anio> anioArray;
    private ArrayList<Sancion> sancionArray;
    private Spinner sancionDivisionSpinner;
    private Spinner sancionJugadorSpinner;
    private Spinner sancionTorneoSpinner;
    private Spinner fixtureAnioSpinner;
    private AdapterSpinnerJugador adapterSpinnerJugador;
    private AdapterSpinnerDivision adapterFixtureDivision;
    private AdapterSpinnerTorneo adapterFixtureTorneo;
    private AdapterSpinnerAnio adapterFixtureAnio;
    private AdaptadorEditarSancion adaptadorEditarSancion;
    private RecyclerView recyclerViewSancion;
    private FloatingActionButton botonFloating;
    private ControladorAdeful controladorAdeful;
    private int CheckedPositionFragment;
    private int divisionSpinner, jugadorSpinner, torneoSpinner, anioSpiner;
    private DialogoAlerta dialogoAlerta;
    private ArrayAdapter<String> adaptadorInicial;
    private int id_division;
    private AuxiliarGeneral auxiliarGeneral;

    public static FragmentEditarSancion newInstance() {
        FragmentEditarSancion fragment = new FragmentEditarSancion();
        return fragment;
    }

    public FragmentEditarSancion() {
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

        // TORNEO
        sancionTorneoSpinner = (Spinner) v
                .findViewById(R.id.fixtureFechaSpinner);
        // ANIO
        fixtureAnioSpinner = (Spinner) v.findViewById(R.id.fixtureAnioSpinner);

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

    private void init() {
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
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
        // TORNEO
        torneoArray = controladorAdeful.selectListaTorneoAdeful();
        if (torneoArray != null) {
            if (!torneoArray.isEmpty()) {
                // TORNEO SPINNER
                adapterFixtureTorneo = new AdapterSpinnerTorneo(getActivity(),
                        R.layout.simple_spinner_dropdown_item, torneoArray);
                sancionTorneoSpinner.setAdapter(adapterFixtureTorneo);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerTorneo));
                sancionTorneoSpinner.setAdapter(adaptadorInicial);
            }
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

        botonFloating.setOnClickListener(new View.OnClickListener(){

         @Override
         public void onClick(View v) {

             if (sancionDivisionSpinner.getSelectedItem().toString().equals(getResources().
                     getString(R.string.ceroSpinnerDivision))) {
                 Toast.makeText(getActivity(), "Debe agregar un division (Liga).",
                         Toast.LENGTH_SHORT).show();
             } else if (sancionJugadorSpinner.getSelectedItem().toString().equals(getResources().
                     getString(R.string.ceroSpinnerJugador))) {
                 Toast.makeText(getActivity(), "Debe agregar un jugador.",
                         Toast.LENGTH_SHORT).show();
             } else if (sancionTorneoSpinner.getSelectedItem().toString().equals(getResources().
                     getString(R.string.ceroSpinnerTorneo))) {
                 Toast.makeText(getActivity(), "Debe agregar un torneo.",
                         Toast.LENGTH_SHORT).show();
             } else {
                 division = (Division) sancionDivisionSpinner.getSelectedItem();
                 jugadorRecycler = (Jugador) sancionJugadorSpinner.getSelectedItem();
                 torneo = (Torneo) sancionTorneoSpinner.getSelectedItem();
                 anio = (Anio) fixtureAnioSpinner.getSelectedItem();

                 divisionSpinner = division.getID_DIVISION();
                 jugadorSpinner = jugadorRecycler.getID_JUGADOR();
                 torneoSpinner = torneo.getID_TORNEO();
                 anioSpiner = anio.getID_ANIO();

                 recyclerViewLoadSancion(divisionSpinner, jugadorSpinner, torneoSpinner, anioSpiner);
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
                        editarSancion.putExtra("torneoSpinner", torneoSpinner);
                        editarSancion.putExtra("amarillaSpinner", sancionArray.get(position).getAMARILLA());
                        editarSancion.putExtra("rojaSpinner", sancionArray.get(position).getROJA());
                        editarSancion.putExtra("fechaSpinner", sancionArray.get(position).getFECHA_SUSPENSION());
                        editarSancion.putExtra("observaciones", sancionArray.get(position).getOBSERVACIONES());
                        editarSancion.putExtra("anioSpinner", anioSpiner);

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
                                        if (controladorAdeful.eliminarSancionAdeful(sancionArray.get(position)
                                                .getID_SANCION())) {
                                            recyclerViewLoadSancion(divisionSpinner, jugadorSpinner, torneoSpinner, anioSpiner);
                                            Toast.makeText(
                                                    getActivity(),
                                                    "Sanción eliminada correctamente",
                                                    Toast.LENGTH_SHORT).show();
                                            dialogoAlerta.alertDialog.dismiss();
                                        } else {
                                            auxiliarGeneral.errorDataBase(getActivity());
                                        }
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
    //LOAD RECYCLER
    public void recyclerViewLoadSancion(int division, int jugador, int fecha,
                                        int anio) {
        //controladorAdeful.abrirBaseDeDatos();
        sancionArray = controladorAdeful.selectListaSancionAdeful(division,
                id_division, fecha, anio);
        if (sancionArray != null) {
            adaptadorEditarSancion = new AdaptadorEditarSancion(sancionArray,getActivity());
            recyclerViewSancion.setAdapter(adaptadorEditarSancion);
            if(sancionArray.isEmpty())
                Toast.makeText(
                        getActivity(),
                        "Selección sin datos",
                        Toast.LENGTH_SHORT).show();
        } else {
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
        }
    }
}
