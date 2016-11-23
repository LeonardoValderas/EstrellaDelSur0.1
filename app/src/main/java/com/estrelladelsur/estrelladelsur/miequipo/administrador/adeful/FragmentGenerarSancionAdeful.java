package com.estrelladelsur.estrelladelsur.miequipo.administrador.adeful;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerJugador;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerTorneo;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGeneric;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import java.util.ArrayList;

public class FragmentGenerarSancionAdeful extends Fragment implements MyAsyncTaskListener {

    private Spinner sancionDivisionSpinner;
    private Spinner sancionJugadorSpinner;
    private Spinner sancionAmarillaSpinner;
    private Spinner sancionRojaSpinner;
    private Torneo torneoActual;
    //private Spinner sancionTorneoSpinner;
    //  private Spinner sancionAnioSpinner;
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
    //    private Anio anio;
    private Jugador jugadorRecycler;
    private Division division;
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
    private Request request = new Request();
    private String URL = null, usuario = null;
    private LinearLayout linearTorneo;


    public static FragmentGenerarSancionAdeful newInstance() {
        FragmentGenerarSancionAdeful fragment = new FragmentGenerarSancionAdeful();
        return fragment;
    }

    public FragmentGenerarSancionAdeful() {
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

        linearTorneo = (LinearLayout) v.findViewById(R.id.linearTorneo);
        linearTorneo.setVisibility(View.GONE);
//        // DIVISION
//        sancionTorneoSpinner = (Spinner) v
//                .findViewById(R.id.sancionTorneoSpinner);
//        // JUGADOR
//        sancionAnioSpinner = (Spinner) v
//                .findViewById(R.id.sancionAnioSpinner);
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
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
        // TORNEO
//        torneoArray = controladorAdeful.selectListaTorneoAdeful();
//        if (torneoArray != null) {
//            if (!torneoArray.isEmpty()) {
//                // TORNEO SPINNER
//                adapterFixtureTorneo = new AdapterSpinnerTorneo(getActivity(),
//                        R.layout.simple_spinner_dropdown_item, torneoArray);
//                sancionTorneoSpinner.setAdapter(adapterFixtureTorneo);
//            } else {
//                //SPINNER HINT
//                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
//                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerTorneo));
//                sancionTorneoSpinner.setAdapter(adaptadorInicial);
//            }
//        } else {
//            auxiliarGeneral.errorDataBase(getActivity());
//        }
        // ANIO
//        anioArray = controladorAdeful.selectListaAnio();
//        if (anioArray != null) {
//            if (!anioArray.isEmpty()) {
//                // ANIO SPINNER
//                adapterFixtureAnio = new AdapterSpinnerAnio(getActivity(),
//                        R.layout.simple_spinner_dropdown_item, anioArray);
//                sancionAnioSpinner.setAdapter(adapterFixtureAnio);
//            } else {
//                //SPINNER HINT
//                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
//                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerAnio));
//                sancionAnioSpinner.setAdapter(adaptadorInicial);
//            }
//        } else {
//            auxiliarGeneral.errorDataBase(getActivity());
//        }
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

        adaptadorTarjeta = new ArrayAdapter<>(getActivity(),
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
            //  sancionTorneoSpinner.setSelection(getPositionSpinner(getActivity().getIntent().getIntExtra("torneoSpinner", 0), 3));
//            // ROJA 3
//            sancionRojaSpinner.setSelection(getPositionSpinner(getActivity().getIntent().getIntExtra("rojaSpinner", 0), 3));
//            // FECHAS 4
//            cantidadFechasSpinner.setSelection(getPositionSpinner(getActivity().getIntent().getIntExtra("fechaSpinner", 0), 4));
            //AÑO 2
            // sancionAnioSpinner.setSelection(getPositionSpinner(getActivity().getIntent().getIntExtra("anioSpinner", 0), 2));
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
        }

        return index;
    }

    public void cargarEntidad(int id, int ws) {

        URL = null;
        URL = auxiliarGeneral.getURLSANCIONADEFULALL();

        sancion = new Sancion(id, jugadorRecycler.getID_JUGADOR(), torneoActual.getID_TORNEO(), sancionAmarillaSpinner.getSelectedItemPosition(),
                sancionRojaSpinner.getSelectedItemPosition(), cantidadFechasSpinner.getSelectedItemPosition(), observacionesSancion.getText().toString(), usuario, auxiliarGeneral.getFechaOficial(),
                usuario, auxiliarGeneral.getFechaOficial());


        envioWebService(ws);
    }

    public void envioWebService(int tipo) {
        request.setMethod("POST");
        request.setParametrosDatos("id_jugador", String.valueOf(sancion.getID_JUGADOR()));
        request.setParametrosDatos("id_torneo", String.valueOf(sancion.getID_TORNEO()));
        //    request.setParametrosDatos("id_anio", String.valueOf(sancion.getID_ANIO()));
        request.setParametrosDatos("amarilla", String.valueOf(sancion.getAMARILLA()));
        request.setParametrosDatos("roja", String.valueOf(sancion.getROJA()));
        request.setParametrosDatos("fecha", String.valueOf(sancion.getFECHA_SUSPENSION()));
        request.setParametrosDatos("observacion", sancion.getOBSERVACIONES());

        if (tipo == 0) {
            //request.setQuery("SUBIR");
            request.setParametrosDatos("usuario_creador", sancion.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", sancion.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Sancion");

        } else {
            //request.setQuery("EDITAR");
            request.setParametrosDatos("id_sancion", String.valueOf(sancion.getID_SANCION()));
            request.setParametrosDatos("usuario_actualizacion", sancion.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", sancion.getFECHA_ACTUALIZACION());

            URL = URL + auxiliarGeneral.getUpdatePHP("Sancion");
        }
        new AsyncTaskGeneric(getActivity(), this, URL, request, "Sancion", sancion, insertar, "a");
    }

    public void inicializarControles(String mensaje) {
        observacionesSancion.setText("");
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void showToast(String mgs) {
        Toast.makeText(getActivity(), mgs,
                Toast.LENGTH_SHORT).show();
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_administrador_general, menu);
        // menu.getItem(0).setVisible(false);//usuario
        menu.getItem(1).setVisible(false);// posicion
        menu.getItem(2).setVisible(false);// cargo
        // menu.getItem(3).setVisible(false);//cerrar
        // menu.getItem(4).setVisible(false);// guardar
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

        if (id == R.id.action_guardar) {

            if (sancionDivisionSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerDivision))) {
                showToast("Debe agregar un division (Liga).");
            } else if (sancionJugadorSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerJugador))) {
                showToast("Debe agregar un jugador.");
            } else if (cantidadFechasSpinner.getSelectedItem().toString().equals("0") && sancionRojaSpinner.getSelectedItem().toString().equals("0") && sancionAmarillaSpinner.getSelectedItem().toString().equals("0")) {
                showToast("Ingrese todos los campos.");
            } else if (cantidadFechasSpinner.getSelectedItem().toString().equals("0")) {
                showToast("Ingrese las fechas de suspensión.");
            } else {
                division = (Division) sancionDivisionSpinner.getSelectedItem();
                jugadorRecycler = (Jugador) sancionJugadorSpinner.getSelectedItem();
                torneoActual = controladorAdeful.selectActualTorneoAdeful();

                if (torneoActual.getISACTUAL()) {
                    //SANCION NVO
                    if (insertar) {
                        cargarEntidad(0, 0);
                    } else { //SANCION ACTUALIZAR
                        cargarEntidad(idSancionExtra, 1);
                    }
                } else {
                    showToast("Debe seleccionar un torneo actual.");
                }
            }
            return true;
        }

        if (id == android.R.id.home) {

            NavUtils.navigateUpFromSameTask(getActivity());

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostExecuteConcluded(boolean result, String mensaje) {
        if (result) {
            if (!insertar) {
                actualizar = false;
                insertar = true;
            }
            inicializarControles(mensaje);
        } else {
            auxiliarGeneral.errorWebService(getActivity(), mensaje);
        }
    }
}