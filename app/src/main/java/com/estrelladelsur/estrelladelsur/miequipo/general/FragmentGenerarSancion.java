package com.estrelladelsur.estrelladelsur.miequipo.general;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerJugador;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerTorneo;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;

import java.util.ArrayList;

public class FragmentGenerarSancion extends Fragment {

    private Spinner sancionDivisionSpinner;
    private Spinner sancionJugadorSpinner;
    private Spinner sancionAmarillaSpinner;
    private Spinner sancionRojaSpinner;
    private Spinner sancionTorneoSpinner;
    private Spinner sancionAnioSpinner;
    private Spinner cantidadFechasSpinner;
    private EditText observacionesSancion;
    private AdapterSpinnerJugador adapterSpinnerJugador;
    private AdapterSpinnerDivision adapterFixtureDivision;
    private AdapterSpinnerTorneo adapterFixtureTorneo;
    private AdapterSpinnerAnio adapterFixtureAnio;
    private ArrayList<Division> divisionArray;
    private ArrayList<Torneo> torneoArray;
    private ArrayList<Anio> anioArray;
    private ArrayList<Jugador> jugadorArray;
    private Anio anio;
    private Jugador jugadorRecycler;
    private Division division;
    private Torneo torneo;
    private int CheckedPositionFragment;
    private ControladorAdeful controladorAdeful;
    private boolean actualizar = false;
    private boolean insertar = true;
    private int idSancionExtra;
    private ArrayAdapter<String> adaptadorInicial;
    private ArrayAdapter<String> adaptadorTarjeta;
    private int id_division;
    private Sancion sancion;
    private AuxiliarGeneral auxiliarGeneral;
    private Typeface editTextFont;
    private TextView textAmarilla, textRoja, cantidadFecha;


    public static FragmentGenerarSancion newInstance() {
        FragmentGenerarSancion fragment = new FragmentGenerarSancion();
        return fragment;
    }

    public FragmentGenerarSancion() {
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
        View v = inflater.inflate(R.layout.fragment_generar_sancion, container,
                false);
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        editTextFont = auxiliarGeneral.textFont(getActivity());
        // DIVISION
        sancionTorneoSpinner = (Spinner) v
                .findViewById(R.id.sancionTorneoSpinner);
        // JUGADOR
        sancionAnioSpinner = (Spinner) v
                .findViewById(R.id.sancionAnioSpinner);
        // DIVISION
        sancionDivisionSpinner = (Spinner) v
                .findViewById(R.id.sancionDivisionSpinner);
        // JUGADOR
        sancionJugadorSpinner = (Spinner) v
                .findViewById(R.id.sancionJugadorSpinner);
        // SANCION AMARILLA
        sancionAmarillaSpinner = (Spinner) v
                .findViewById(R.id.sancionAmarillaSpinner);
        // SANCION ROJA
        sancionRojaSpinner = (Spinner) v.findViewById(R.id.sancionRojaSpinner);
        //OBSERVACION
        observacionesSancion = (EditText) v.findViewById(R.id.observacionesSancion);
        observacionesSancion.setTypeface(editTextFont, Typeface.BOLD);
        //CANTIDAD DE FECHAS
        cantidadFechasSpinner = (Spinner) v
                .findViewById(R.id.cantidadFechasSpinner);

        textAmarilla = (TextView) v
                .findViewById(R.id.textAmarilla);
        textAmarilla.setTypeface(editTextFont);
        textRoja = (TextView) v
                .findViewById(R.id.textRoja);
        textRoja.setTypeface(editTextFont);
        cantidadFecha = (TextView) v
                .findViewById(R.id.cantidadFecha);
        cantidadFecha.setTypeface(editTextFont);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {

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
            if (!anioArray.isEmpty()) {
                // ANIO SPINNER
                adapterFixtureAnio = new AdapterSpinnerAnio(getActivity(),
                        R.layout.simple_spinner_dropdown_item, anioArray);
                sancionAnioSpinner.setAdapter(adapterFixtureAnio);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerAnio));
                sancionAnioSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        // DIVISION
        divisionArray = controladorAdeful.selectListaDivisionAdeful();
        if (divisionArray != null) {
            // DIVSION SPINNER
            if (!divisionArray.isEmpty()) {
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

        adaptadorTarjeta = new ArrayAdapter<String>(getActivity(),
                R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.tarjetaArray));
        //AMARILLA
        sancionAmarillaSpinner.setAdapter(adaptadorTarjeta);
        //ROJA
        sancionRojaSpinner.setAdapter(adaptadorTarjeta);
        //FECHA
        cantidadFechasSpinner.setAdapter(adaptadorTarjeta);

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

        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        //Metodo Extra
        if (actualizar) {

            idSancionExtra = getActivity().getIntent().getIntExtra("id_sancion", 0);

            //DIVISION 0
            sancionDivisionSpinner.setSelection(getPositionSpinner(getActivity().getIntent()
                    .getIntExtra("divisionSpinner", 0), 0));
            //Traer array jugadores
            id_division = getActivity().getIntent()
                    .getIntExtra("divisionSpinner", 0);
            populationSpinnerJugador(id_division);
            // JUGADOR 1
            sancionJugadorSpinner.setSelection(getPositionSpinner(getActivity().getIntent()
                    .getIntExtra("jugadorSpinner", 0), 1));
            // AMARILLA 2
            sancionAmarillaSpinner.setSelection(getActivity().getIntent().getIntExtra("amarillaSpinner", 0));
            // ROJA 3
            sancionRojaSpinner.setSelection(getActivity().getIntent().getIntExtra("rojaSpinner", 0));
            // FECHAS 4
            cantidadFechasSpinner.setSelection(getActivity().getIntent().getIntExtra("fechaSpinner", 0));
            // TORNEO 5
            sancionTorneoSpinner.setSelection(getPositionSpinner(getActivity().getIntent().getIntExtra("torneoSpinner", 0), 3));
//            // ROJA 3
//            sancionRojaSpinner.setSelection(getPositionSpinner(getActivity().getIntent().getIntExtra("rojaSpinner", 0), 3));
//            // FECHAS 4
//            cantidadFechasSpinner.setSelection(getPositionSpinner(getActivity().getIntent().getIntExtra("fechaSpinner", 0), 4));
            //AÑO 2
            sancionAnioSpinner.setSelection(getPositionSpinner(getActivity().getIntent().getIntExtra("anioSpinner", 0), 2));
            //OBSERVACIONES
            observacionesSancion.setText(getActivity().getIntent()
                    .getStringExtra("observaciones"));

            insertar = false;
        }
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private int getPositionSpinner(int idSpinner, int spinner) {

        int index = 0;
        switch (spinner) {
            //DIVISION 0
            case 0:
                for (int i = 0; i < divisionArray.size(); i++) {
                    if (divisionArray.get(i).getID_DIVISION() == (idSpinner)) {
                        index = i;
                    }
                }
                break;
            // JUGADOR 1
            case 1:
                for (int i = 0; i < jugadorArray.size(); i++) {
                    if (jugadorArray.get(i).getID_JUGADOR() == (idSpinner)) {
                        index = i;
                    }
                }
                break;
            //Anio
            case 2:
                for (int i = 0; i < anioArray.size(); i++) {
                    if (anioArray.get(i).getID_ANIO() == (idSpinner)) {
                        index = i;
                    }
                }
                break;
            // TORNEO 3
            case 3:
                for (int i = 0; i < torneoArray.size(); i++) {
                    if (torneoArray.get(i).getID_TORNEO() == (idSpinner)) {
                        index = i;
                    }
                }
                break;
        }

        return index;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_administrador_general, menu);
        // menu.getItem(0).setVisible(false);//usuario
        menu.getItem(1).setVisible(false);//permiso
        menu.getItem(2).setVisible(false);//lifuba
        menu.getItem(3).setVisible(false);// adeful
        menu.getItem(4).setVisible(false);// puesto
        menu.getItem(5).setVisible(false);// posicion
        menu.getItem(6).setVisible(false);// cargo
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

            //*Intent usuario = new Intent(getActivity(),
            //		NavigationUsuario.class);
            //	startActivity(usuario);

            return true;
        }

        if (id == R.id.action_permisos) {
            return true;
        }

        if (id == R.id.action_guardar) {

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
                String usuario = "Administrador";
                division = (Division) sancionDivisionSpinner.getSelectedItem();
                torneo = (Torneo) sancionTorneoSpinner.getSelectedItem();
                anio = (Anio) sancionAnioSpinner.getSelectedItem();
                jugadorRecycler = (Jugador) sancionJugadorSpinner.getSelectedItem();

                //SANCION NVO
                if (insertar) {

                    sancion = new Sancion(0, jugadorRecycler.getID_JUGADOR(), torneo.getID_TORNEO(), anio.getID_ANIO(), sancionAmarillaSpinner.getSelectedItemPosition(),
                            sancionRojaSpinner.getSelectedItemPosition(), cantidadFechasSpinner.getSelectedItemPosition(), observacionesSancion.getText().toString(), usuario, auxiliarGeneral.getFechaOficial(),
                            usuario, auxiliarGeneral.getFechaOficial());

                    if (controladorAdeful.insertSancionAdeful(sancion)) {
                        Toast.makeText(getActivity(), "Sanción cargada correctamente",
                                Toast.LENGTH_SHORT).show();
                        observacionesSancion.setText("");
                    } else {
                        auxiliarGeneral.errorDataBase(getActivity());
                    }
                } else { //SANCION ACTUALIZAR

                    sancion = new Sancion(idSancionExtra, jugadorRecycler.getID_JUGADOR(), torneo.getID_TORNEO(), anio.getID_ANIO(), sancionAmarillaSpinner.getSelectedItemPosition(),
                            sancionRojaSpinner.getSelectedItemPosition(), cantidadFechasSpinner.getSelectedItemPosition(), observacionesSancion.getText().toString(), null, null,
                            usuario, auxiliarGeneral.getFechaOficial());

                    if (controladorAdeful.actualizarSancionAdeful(sancion)) {

                        actualizar = false;
                        insertar = true;
                        Toast.makeText(getActivity(), "Sanción actualizada correctamente",
                                Toast.LENGTH_SHORT).show();
                        observacionesSancion.setText("");

                    } else {
                        auxiliarGeneral.errorDataBase(getActivity());
                    }
                }
            }
            return true;
        }

        if (id == R.id.action_lifuba) {

            return true;
        }

        if (id == android.R.id.home) {

            NavUtils.navigateUpFromSameTask(getActivity());

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}