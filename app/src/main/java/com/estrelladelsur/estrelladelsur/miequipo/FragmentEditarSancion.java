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
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorEditarSancion;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerFecha;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerJugador;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.JugadorRecycler;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;

import java.util.ArrayList;

public class FragmentEditarSancion extends Fragment {

    private Division division;
    private Fecha fecha;
    private Anio anio;
    private JugadorRecycler jugadorRecycler;
    private ArrayList<Division> divisionArray;
    private ArrayList<JugadorRecycler> jugadorArray;
    private ArrayList<Fecha> fechaArray;
    private ArrayList<Anio> anioArray;
    private ArrayList<Sancion> sancionArray;
    private Spinner sancionDivisionSpinner;
    private Spinner sancionJugadorSpinner;
    private Spinner fixtureFechaSpinner;
    private Spinner fixtureAnioSpinner;
    private AdapterSpinnerJugador adapterSpinnerJugador;
    private AdapterSpinnerDivision adapterFixtureDivision;
    private AdapterSpinnerFecha adapterFixtureFecha;
    private AdapterSpinnerAnio adapterFixtureAnio;
    private AdaptadorEditarSancion adaptadorEditarSancion;
    private RecyclerView recyclerViewSancion;
    private FloatingActionButton botonFloating;
    private ControladorAdeful controladorAdeful;
    private int CheckedPositionFragment;
    private int divisionSpinner, jugadorSpinner, fechaSpinner, anioSpiner;
    private DialogoAlerta dialogoAlerta;
    private ArrayAdapter<String> adaptadorInicial;
    private int id_division;

    public static FragmentEditarSancion newInstance() {
        FragmentEditarSancion fragment = new FragmentEditarSancion();
        return fragment;
    }

    public FragmentEditarSancion() {
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

        // FECHA
        fixtureFechaSpinner = (Spinner) v
                .findViewById(R.id.fixtureFechaSpinner);
        // ANIO
        fixtureAnioSpinner = (Spinner) v.findViewById(R.id.fixtureAnioSpinner);

        // DIVISION
        sancionDivisionSpinner = (Spinner) v
                .findViewById(R.id.fixtureDivisionSpinner);
        // TORNEO
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

        // DIVISION
        controladorAdeful.abrirBaseDeDatos();
        divisionArray = controladorAdeful.selectListaDivisionAdeful();
        if (divisionArray != null) {
            controladorAdeful.cerrarBaseDeDatos();
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
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
        }


        // FECHA
        controladorAdeful.abrirBaseDeDatos();
        fechaArray = controladorAdeful.selectListaFecha();
        if (fechaArray != null) {
            controladorAdeful.cerrarBaseDeDatos();
            // FECHA SPINNER
            adapterFixtureFecha = new AdapterSpinnerFecha(getActivity(),
                    R.layout.simple_spinner_dropdown_item, fechaArray);
            fixtureFechaSpinner.setAdapter(adapterFixtureFecha);
        } else {
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
        }
        // ANIO
        controladorAdeful.abrirBaseDeDatos();
        anioArray = controladorAdeful.selectListaAnio();
        if (anioArray != null) {
            controladorAdeful.cerrarBaseDeDatos();
            // ANIO SPINNER
            adapterFixtureAnio = new AdapterSpinnerAnio(getActivity(),
                    R.layout.simple_spinner_dropdown_item, anioArray);
            fixtureAnioSpinner.setAdapter(adapterFixtureAnio);
        } else {
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
        }
        //POPULATION SPINNER JUGADOR X ID DIVISION
        sancionDivisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                if (divisionArray.size() > 0) {
                    id_division = divisionArray.get(position).getID_DIVISION();
                    controladorAdeful.abrirBaseDeDatos();
                    jugadorArray = controladorAdeful.selectListaJugadorAdeful(id_division);
                    if (jugadorArray != null) {
                        controladorAdeful.cerrarBaseDeDatos();
                        if (jugadorArray.size() != 0) {
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
                        controladorAdeful.cerrarBaseDeDatos();
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                                Toast.LENGTH_SHORT).show();
                    }
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

        botonFloating.setOnClickListener(new View.OnClickListener()

        {

        @Override
        public void onClick(View v) {

            if (sancionDivisionSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerDivision))) {
                Toast.makeText(getActivity(), "Debe agregar un division (Liga).",
                        Toast.LENGTH_SHORT).show();
            } else if (sancionJugadorSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerTorneo))) {
                Toast.makeText(getActivity(), "Debe agregar un jugador.",
                        Toast.LENGTH_SHORT).show();
            } else {
                division = (Division) sancionDivisionSpinner.getSelectedItem();
                jugadorRecycler = (JugadorRecycler) sancionJugadorSpinner.getSelectedItem();
                fecha = (Fecha) fixtureFechaSpinner.getSelectedItem();
                anio = (Anio) fixtureAnioSpinner.getSelectedItem();

                divisionSpinner = division.getID_DIVISION();
                jugadorSpinner = jugadorRecycler.getID_JUGADOR();
                fechaSpinner = fecha.getID_FECHA();
                anioSpiner = anio.getID_ANIO();

                recyclerViewLoadSancion(divisionSpinner, jugadorSpinner, fechaSpinner, anioSpiner);

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
                                TabsFixture.class);
                        editarSancion.putExtra("actualizar", true);
                        editarSancion.putExtra("id_sancion",
                                sancionArray.get(position).getID_SANCION());
                        editarSancion.putExtra("divisionSpinner", divisionSpinner);
                        editarSancion.putExtra("jugadorSpinner", jugadorSpinner);
                        editarSancion.putExtra("amarillaSpinner", sancionArray.get(position).getAMARILLA());
                        editarSancion.putExtra("rojaSpinner", sancionArray.get(position).getROJA());
                        editarSancion.putExtra("fechaSpinner", sancionArray.get(position).getFECHA_SUSPENSION());
                        editarSancion.putExtra("observaciones", sancionArray.get(position).getOBSERVACIONES());
                        // editarSancion.putExtra("anioSpiner", anioSpiner);

                        startActivity(editarSancion);
                    }

                    @Override
                    public void onLongClick(View view, final int position) {
                        // TODO Auto-generated method stub

                        dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                                "Desea eliminar la sanción?", null, null);
                        dialogoAlerta.btnAceptar.setText("Aceptar");
                        dialogoAlerta.btnCancelar.setText("Cancelar");

                        dialogoAlerta.btnAceptar
                                .setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub

                                        //controladorAdeful.abrirBaseDeDatos();
                                        if (controladorAdeful.eliminarSancionAdeful(sancionArray.get(position)
                                                .getID_SANCION())) {
                                            //  controladorAdeful.cerrarBaseDeDatos();
                                            recyclerViewLoadSancion(divisionSpinner, jugadorSpinner, fechaSpinner, anioSpiner);
                                            Toast.makeText(
                                                    getActivity(),
                                                    "Sanción eliminada correctamente",
                                                    Toast.LENGTH_SHORT).show();
                                            dialogoAlerta.alertDialog.dismiss();
                                        } else {
                                            //  controladorAdeful.cerrarBaseDeDatos();
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
                }

        ));
    }

    //LOAD RECYCLER

    public void recyclerViewLoadSancion(int division, int jugador, int fecha,
                                        int anio) {

        //controladorAdeful.abrirBaseDeDatos();
      sancionArray = controladorAdeful.selectListaSancionAdeful(division,
                id_division, fecha, anio);
        if (sancionArray != null) {
            //controladorAdeful.cerrarBaseDeDatos();

            adaptadorEditarSancion = new AdaptadorEditarSancion(sancionArray);
            recyclerViewSancion.setAdapter(adaptadorEditarSancion);
        } else {
           // controladorAdeful.cerrarBaseDeDatos();
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
}