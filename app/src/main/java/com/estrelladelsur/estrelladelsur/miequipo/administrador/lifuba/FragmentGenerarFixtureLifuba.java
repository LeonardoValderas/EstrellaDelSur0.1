package com.estrelladelsur.estrelladelsur.miequipo.administrador.lifuba;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerCancha;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerEquipo;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerFecha;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerTorneo;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.database.administrador.lifuba.ControladorLifuba;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.Fixture;
import com.estrelladelsur.estrelladelsur.entidad.Mes;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericLifuba;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FragmentGenerarFixtureLifuba extends Fragment implements MyAsyncTaskListener {

    private Spinner fixtureDivisionSpinner;
    private Spinner fixtureTorneoSpinner;
    private Spinner fixtureFechaSpinner;
    private Spinner fixtureCanchaSpinner;
    private Spinner fixtureAnioSpinner;
    private Spinner fixtureLocalSpinner;
    private Spinner fixtureVisitaSpinner;
    private AdapterSpinnerTorneo adapterFixtureTorneo;
    private AdapterSpinnerDivision adapterFixtureDivision;
    private AdapterSpinnerFecha adapterFixtureFecha;
    private AdapterSpinnerAnio adapterFixtureAnio;
    private AdapterSpinnerCancha adapterFixtureCancha;
    private AdapterSpinnerEquipo adapterFixtureEquipo;
    private ArrayList<Division> divisionArray;
    private ArrayList<Torneo> torneoArray;
    private ArrayList<Fecha> fechaArray;
    private ArrayList<Anio> anioArray;
    private ArrayList<Cancha> canchaArray;
    private ArrayList<Equipo> equipoArray;
    private Fecha fecha;
    private Anio anio;
    private Mes mes;
    private SimpleDateFormat formate = new SimpleDateFormat(
            "dd-MM-yyyy");
    private DateFormat form = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.UK );
    private Calendar calendar = Calendar.getInstance();
    private Calendar calenda = Calendar.getInstance();
    private Button btn_dia;
    private Button btn_hora;
    private Fixture fixture;
    private Division division;
    private Equipo equipol;
    private Equipo equipov;
    private Torneo torneo;
    private Torneo torneoActual;
    private Cancha cancha;
    private int CheckedPositionFragment;
    private ControladorLifuba controladorLifuba;
    private ControladorAdeful controladorAdeful;
    private boolean actualizar = false;
    private boolean insertar = true;
    private int idFixtureExtra;
    private ArrayAdapter<String> adaptadorInicial;
    private AuxiliarGeneral auxiliarGeneral;
    private Typeface editTextFont;
    private Request request;
    private String URL = null, usuario = null;

    public static FragmentGenerarFixtureLifuba newInstance() {
        FragmentGenerarFixtureLifuba fragment = new FragmentGenerarFixtureLifuba();
        return fragment;
    }

    public FragmentGenerarFixtureLifuba() {
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        controladorLifuba = new ControladorLifuba(getActivity());
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
        View v = inflater.inflate(R.layout.fragment_generar_fixture, container,
                false);
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        editTextFont = auxiliarGeneral.textFont(getActivity());
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
        // CANCHA
        fixtureCanchaSpinner = (Spinner) v
                .findViewById(R.id.fixtureCanchaSpinner);
        // LOCAL
        fixtureLocalSpinner = (Spinner) v
                .findViewById(R.id.fixtureLocalSpinner);
        // VISITA
        fixtureVisitaSpinner = (Spinner) v
                .findViewById(R.id.fixtureVisitaSpinner);
        // DIA
        btn_dia = (Button) v.findViewById(R.id.btn_dia);
        btn_dia.setTypeface(editTextFont, Typeface.BOLD);
        // HORA
        btn_hora = (Button) v.findViewById(R.id.btn_hora);
        btn_hora.setTypeface(editTextFont, Typeface.BOLD);
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
        controladorLifuba = new ControladorLifuba(getActivity());
        controladorAdeful = new ControladorAdeful(getActivity());
        init();
    }

    private void init() {
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
        // DIVISION
        divisionArray = controladorAdeful.selectListaDivisionAdeful();
        if (divisionArray != null) {
            // DIVSION SPINNER
            if (divisionArray.size() != 0) {
                adapterFixtureDivision = new AdapterSpinnerDivision(getActivity(),
                        R.layout.simple_spinner_dropdown_item, divisionArray);
                fixtureDivisionSpinner.setAdapter(adapterFixtureDivision);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerDivision));
                fixtureDivisionSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        // TORNEO
        torneoArray = controladorLifuba.selectListaTorneoLifuba();
        if (torneoArray != null) {
            if (torneoArray.size() != 0) {
                // TORNEO SPINNER
                adapterFixtureTorneo = new AdapterSpinnerTorneo(getActivity(),
                        R.layout.simple_spinner_dropdown_item, torneoArray);
                fixtureTorneoSpinner.setAdapter(adapterFixtureTorneo);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerTorneo));
                fixtureTorneoSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }

        // FECHA
        fechaArray = controladorLifuba.selectListaFecha();
        if (fechaArray != null) {
            if (!fechaArray.isEmpty()) {
                // FECHA SPINNER
                adapterFixtureFecha = new AdapterSpinnerFecha(getActivity(),
                        R.layout.simple_spinner_dropdown_item, fechaArray);
                fixtureFechaSpinner.setAdapter(adapterFixtureFecha);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerFecha));
                fixtureFechaSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }

        // ANIO
        anioArray = controladorLifuba.selectListaAnio();
        if (anioArray != null) {
            if (!anioArray.isEmpty()) {
                // ANIO SPINNER
                adapterFixtureAnio = new AdapterSpinnerAnio(getActivity(),
                        R.layout.simple_spinner_dropdown_item, anioArray);
                fixtureAnioSpinner.setAdapter(adapterFixtureAnio);
                fixtureAnioSpinner.setSelection(getPositionYearSpinner(anioArray));
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerAnio));
                fixtureAnioSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        // CANCHA
        canchaArray = controladorLifuba.selectListaCanchaLifuba();
        if (canchaArray != null) {
            if (!canchaArray.isEmpty()) {
                // CANCHA SPINNER
                adapterFixtureCancha = new AdapterSpinnerCancha(getActivity(),
                        R.layout.simple_spinner_dropdown_item, canchaArray);
                fixtureCanchaSpinner.setAdapter(adapterFixtureCancha);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerCancha));
                fixtureCanchaSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        // EQUIPO
        equipoArray = controladorLifuba.selectListaEquipoLifuba();
        if (equipoArray != null) {
            if (!equipoArray.isEmpty()) {
                // LOCAL SPINNER
                adapterFixtureEquipo = new AdapterSpinnerEquipo(getActivity(),
                        R.layout.simple_spinner_dropdown_item, equipoArray);
                fixtureLocalSpinner.setAdapter(adapterFixtureEquipo);

                // VISITA SPINNER
                adapterFixtureEquipo = new AdapterSpinnerEquipo(getActivity(),
                        R.layout.simple_spinner_dropdown_item, equipoArray);
                fixtureVisitaSpinner.setAdapter(adapterFixtureEquipo);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerEquipo));
                fixtureLocalSpinner.setAdapter(adaptadorInicial);
                fixtureVisitaSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        // DIA
        btn_dia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setDate();
            }
        });
        // HORA
        btn_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setTime();
            }
        });

        actualizar = getActivity().getIntent().getBooleanExtra("actualizarLifuba",
                false);
        //Metodo Extra
        if (actualizar) {
            idFixtureExtra = getActivity().getIntent().getIntExtra("id_fixture", 0);

            //DIVISION 0
            fixtureDivisionSpinner.setSelection(getPositionSpinner(getActivity().getIntent()
                    .getIntExtra("divisionSpinner", 0), 0));
            // TORNEO 1
            fixtureTorneoSpinner.setSelection(getPositionSpinner(getActivity().getIntent()
                    .getIntExtra("torneoSpinner", 0), 1));
            // FECHA 2
            fixtureFechaSpinner.setSelection(getPositionSpinner(getActivity().getIntent()
                    .getIntExtra("fechaSpinner", 0), 2));
            // ANIO 3
            fixtureAnioSpinner.setSelection(getPositionSpinner(getActivity().getIntent()
                    .getIntExtra("anioSpiner", 0), 3));
            // CANCHA 4
            fixtureCanchaSpinner.setSelection(getPositionSpinner(getActivity().getIntent()
                    .getIntExtra("canchaSpinner", 0), 4));
            // LOCAL 5
            fixtureLocalSpinner.setSelection(getPositionSpinner(getActivity().getIntent()
                    .getIntExtra("localSpinner", 0), 5));
            // VISITA 6
            fixtureVisitaSpinner.setSelection(getPositionSpinner(getActivity().getIntent()
                    .getIntExtra("visitaSpinner", 0), 5));
            //DIA
            btn_dia.setText(getActivity().getIntent()
                    .getStringExtra("dia"));
            //HORA
            btn_hora.setText(getActivity().getIntent()
                    .getStringExtra("hora"));
            insertar = false;
        }
    }

    public void updatedate() {
        btn_dia.setText(formate.format(calendar.getTime()));
    }

    public void updatetime() {
        btn_hora.setText(form.format(calenda.getTime()));
    }

    public void setDate() {
        new DatePickerDialog(getActivity(), d, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void setTime() {
        new TimePickerDialog(getActivity(), t,
                calenda.get(Calendar.HOUR_OF_DAY),
                calenda.get(Calendar.MINUTE), true).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updatedate();
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calenda.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calenda.set(Calendar.MINUTE, minute);
            updatetime();
        }
    };

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
            // TORNEO 1
            case 1:
                for (int i = 0; i < torneoArray.size(); i++) {
                    if (torneoArray.get(i).getID_TORNEO() == (idSpinner)) {
                        index = i;
                    }
                }
                break;
            // FECHA 2
            case 2:
                for (int i = 0; i < fechaArray.size(); i++) {
                    if (fechaArray.get(i).getID_FECHA() == (idSpinner)) {
                        index = i;
                    }
                }
                break;
            // ANIO 3
            case 3:
                for (int i = 0; i < anioArray.size(); i++) {
                    if (anioArray.get(i).getID_ANIO() == (idSpinner)) {
                        index = i;
                    }
                }
                break;
            // CANCHA 4
            case 4:
                for (int i = 0; i < canchaArray.size(); i++) {
                    if (canchaArray.get(i).getID_CANCHA() == (idSpinner)) {
                        index = i;
                    }
                }
                break;
            // LOCAL 5
            case 5:
                for (int i = 0; i < equipoArray.size(); i++) {
                    if (equipoArray.get(i).getID_EQUIPO() == (idSpinner)) {
                        index = i;
                    }
                }
                break;
        }
        return index;
    }

    private int getPositionYearSpinner(List<Anio> anios) {
        String anio = auxiliarGeneral.getYearSpinner();
        int index = 0;
        for (int i = 0; i < anios.size(); i++) {
            if (anios.get(i).getANIO().equals(anio)) {
                index = i;
            }
        }
        return index;
    }

    public void showToast(String mgs) {
        Toast.makeText(getActivity(), mgs,
                Toast.LENGTH_SHORT).show();
    }

    public void cargarEntidad(int id) {
        URL = null;
        URL = auxiliarGeneral.getURLFIXTURELIFUBAAll();

        division = (Division) fixtureDivisionSpinner.getSelectedItem();
        equipol = (Equipo) fixtureLocalSpinner.getSelectedItem();
        equipov = (Equipo) fixtureVisitaSpinner.getSelectedItem();
        torneo = (Torneo) fixtureTorneoSpinner.getSelectedItem();
        cancha = (Cancha) fixtureCanchaSpinner.getSelectedItem();
        fecha = (Fecha) fixtureFechaSpinner.getSelectedItem();
        anio = (Anio) fixtureAnioSpinner.getSelectedItem();

        fixture = new Fixture(id, equipol.getID_EQUIPO(),
                equipov.getID_EQUIPO(), division.getID_DIVISION(),
                torneo.getID_TORNEO(), cancha.getID_CANCHA(),
                fecha.getID_FECHA(), anio.getID_ANIO(), btn_dia.getText().toString()
                , btn_hora.getText().toString(), "-", "-",
                usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());

        envioWebService();
    }

    public void envioWebService() {
        request = new Request();
        request.setMethod("POST");
        request.setParametrosDatos("equipol", String.valueOf(fixture.getID_EQUIPO_LOCAL()));
        request.setParametrosDatos("equipov", String.valueOf(fixture.getID_EQUIPO_VISITA()));
        request.setParametrosDatos("division", String.valueOf(fixture.getID_DIVISION()));
        request.setParametrosDatos("torneo", String.valueOf(fixture.getID_TORNEO()));
        request.setParametrosDatos("cancha", String.valueOf(fixture.getID_CANCHA()));
        request.setParametrosDatos("fecha", String.valueOf(fixture.getID_FECHA()));
        request.setParametrosDatos("anio", String.valueOf(fixture.getID_ANIO()));
        request.setParametrosDatos("dia", fixture.getDIA());
        request.setParametrosDatos("hora", fixture.getHORA());
        if (insertar) {
            request.setParametrosDatos("usuario_creador", fixture.getUSUARIO_CREACION());
            request.setParametrosDatos("fecha_creacion", fixture.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Fixture");
        } else {
            request.setParametrosDatos("id_fixture", String.valueOf(fixture.getID_FIXTURE()));
            request.setParametrosDatos("usuario_actualizacion", fixture.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", fixture.getFECHA_ACTUALIZACION());
            URL = URL + auxiliarGeneral.getUpdatePHP("Fixture");
        }

        if (auxiliarGeneral.isNetworkAvailable(getActivity()))
            new AsyncTaskGenericLifuba(getContext(), this, URL, request, "Fixture", fixture, insertar, "o");
        else
            auxiliarGeneral.errorWebService(getActivity(), getActivity().getResources().getString(R.string.error_without_internet));
    }

    @Override
    public void onPostExecuteConcluded(boolean result, String mensaje) {
        if (result) {
            if (!insertar) {
                actualizar = false;
                insertar = true;
            }
            showToast(mensaje);
        } else {
            auxiliarGeneral.errorWebService(getActivity(), mensaje);
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

            if (fixtureDivisionSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerDivision))) {
                showToast("Debe agregar un division (Liga).");
            } else if (fixtureTorneoSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerTorneo))) {
                showToast("Debe agregar un torneo (Liga).");
            } else if (fixtureFechaSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerFecha))) {
                showToast("Debe agregar una fecha.");
            } else if (fixtureAnioSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerAnio))) {
                showToast("Debe agregar un año.");
            } else if (fixtureLocalSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerEquipo)) || fixtureVisitaSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerEquipo))) {
                showToast("Debe agregar un equipo (Liga).");
            } else if (fixtureCanchaSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerCancha))) {
                showToast("Debe agregar una cancha (Liga).");
            } else if (btn_dia.getText()
                    .toString().equals("Día")) {
                showToast("Debe ingresar el día.");
            } else if (btn_hora.getText()
                    .toString().equals("Hora")) {
                showToast("Debe ingresar la hora.");
            } else {

                torneoActual = controladorLifuba.selectActualTorneoLifuba();

                if (torneoActual.getISACTUAL()) {
                    //FIXTURE NVO
                    if (insertar) {
                        cargarEntidad(0);
                    } else { //FIXTURE ACTUALIZAR
                        cargarEntidad(idFixtureExtra);
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

}