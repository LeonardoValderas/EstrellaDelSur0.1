package com.estrelladelsur.estrelladelsur.miequipo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerCancha;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerEquipo;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerFecha;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerJugador;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerTorneo;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.Fixture;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.estrelladelsur.estrelladelsur.entidad.JugadorRecycler;
import com.estrelladelsur.estrelladelsur.entidad.Mes;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

    private ArrayList<JugadorRecycler> jugadorArray;
    private Anio anio;
    private JugadorRecycler jugadorRecycler;
    private SimpleDateFormat formate = new SimpleDateFormat(
            "dd-MM-yyyy");
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

    public static FragmentGenerarSancion newInstance() {
        FragmentGenerarSancion fragment = new FragmentGenerarSancion();
        return fragment;
    }

    public FragmentGenerarSancion() {
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
        View v = inflater.inflate(R.layout.fragment_generar_sancion, container,
                false);

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
        //CANTIDAD DE FECHAS
        cantidadFechasSpinner = (Spinner) v
                .findViewById(R.id.cantidadFechasSpinner);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        // TORNEO
        torneoArray = controladorAdeful.selectListaTorneoAdeful();
        if (torneoArray != null) {
            if (torneoArray.size() != 0) {
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
            if (anioArray.size() != 0) {
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
                if (divisionArray.size() > 0) {
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
            id_division =getActivity().getIntent()
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
            // AMARILLA 2
//            sancionAmarillaSpinner.setSelection(getPositionSpinner(getActivity().getIntent().getIntExtra("amarillaSpinner", 0), 2));
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
            //
            case 2:
                for (int i = 0; i < anioArray.size(); i++) {
                    if (anioArray.get(i).getID_ANIO() == (idSpinner)) {
                        index = i;
                    }
                }
                break;
//
//            case 3:
//                for (int i = 0; i < jugadorArray.size(); i++) {
//                    if (jugadorArray.get(i).getID_DIVISION() == (idSpinner)) {
//                        index = i;
//                    }
//                }
//                break;
//
//            case 4:
//                for (int i = 0; i < jugadorArray.size(); i++) {
//                    if (jugadorArray.get(i).getID_DIVISION() == (idSpinner)) {
//                        index = i;
//                    }
//                }
//                break;
//
//            case 5:
//                for (int i = 0; i < jugadorArray.size(); i++) {
//                    if (jugadorArray.get(i).getID_DIVISION() == (idSpinner)) {
//                        index = i;
//                    }
//                }
//                break;
//

        }

        return index;
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
//            } else if (sancionAmarillaSpinner.getSelectedItem().toString().equals(getResources().
//                    getString(R.string.ceroSpinnerFecha))) {
//                Toast.makeText(getActivity(), "Debe agregar una fecha.",
//                        Toast.LENGTH_SHORT).show();
//            } else if (sancionRojaSpinner.getSelectedItem().toString().equals(getResources().
//                    getString(R.string.ceroSpinnerAnio))) {
//                Toast.makeText(getActivity(), "Debe agregar un año.",
//                        Toast.LENGTH_SHORT).show();
            } else {
                String usuario = "Administrador";
                String fechaCreacion = controladorAdeful.getFechaOficial();
                String fechaActualizacion = fechaCreacion;

                division = (Division) sancionDivisionSpinner.getSelectedItem();
                torneo = (Torneo) sancionTorneoSpinner.getSelectedItem();
                anio = (Anio) sancionAnioSpinner.getSelectedItem();
                jugadorRecycler = (JugadorRecycler) sancionJugadorSpinner.getSelectedItem();

                //SANCION NVO
                if (insertar) {

                    sancion = new Sancion(0, jugadorRecycler.getID_JUGADOR(), torneo.getID_TORNEO(), anio.getID_ANIO(), sancionAmarillaSpinner.getSelectedItemPosition(),
                            sancionRojaSpinner.getSelectedItemPosition(), cantidadFechasSpinner.getSelectedItemPosition(), observacionesSancion.getText().toString(), usuario, fechaCreacion,
                            usuario, fechaActualizacion);

                    if (controladorAdeful.insertSancionAdeful(sancion)) {
                        Toast.makeText(getActivity(), "Sanción cargada correctamente",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        auxiliarGeneral.errorDataBase(getActivity());
                    }
                } else { //SANCION ACTUALIZAR

                    sancion = new Sancion(idSancionExtra, jugadorRecycler.getID_JUGADOR(), torneo.getID_TORNEO(), anio.getID_ANIO(), sancionAmarillaSpinner.getSelectedItemPosition(),
                            sancionRojaSpinner.getSelectedItemPosition(), cantidadFechasSpinner.getSelectedItemPosition(), observacionesSancion.getText().toString(), null, null,
                            usuario, fechaActualizacion);

                    if (controladorAdeful.actualizarSancionAdeful(sancion)) {

                        actualizar = false;
                        insertar = true;
                        Toast.makeText(getActivity(), "Sanción actualizada correctamente",
                                Toast.LENGTH_SHORT).show();

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