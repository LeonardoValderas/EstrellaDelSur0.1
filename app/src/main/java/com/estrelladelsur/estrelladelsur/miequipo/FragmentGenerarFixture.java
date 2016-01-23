package com.estrelladelsur.estrelladelsur.miequipo;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.abstracta.Anio;
import com.estrelladelsur.estrelladelsur.abstracta.Cancha;
import com.estrelladelsur.estrelladelsur.abstracta.Division;
import com.estrelladelsur.estrelladelsur.abstracta.Equipo;
import com.estrelladelsur.estrelladelsur.abstracta.Fecha;
import com.estrelladelsur.estrelladelsur.abstracta.Fixture;
import com.estrelladelsur.estrelladelsur.abstracta.Torneo;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerCancha;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerEquipo;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerFecha;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerTorneo;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;

public class FragmentGenerarFixture extends Fragment {

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
    private DateFormat formate = DateFormat.getDateInstance();
    private DateFormat form = DateFormat.getTimeInstance(DateFormat.SHORT);
    private Calendar calendar = Calendar.getInstance();
    private Calendar calenda = Calendar.getInstance();
    private Button btn_dia;
    private Button btn_hora;
    private Fixture fixture;
    private Division division;
    private Equipo equipol;
    private Equipo equipov;
    private Torneo torneo;
    private Cancha cancha;
    private int CheckedPositionFragment;
    private ControladorAdeful controladorAdeful;
    private boolean actualizar = false;
    private boolean insertar = true;
    private int idFixtureExtra;
    private ArrayAdapter<String> adaptadorInicial;

    public static FragmentGenerarFixture newInstance() {
        FragmentGenerarFixture fragment = new FragmentGenerarFixture();
        return fragment;
    }

    public FragmentGenerarFixture() {
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
        View v = inflater.inflate(R.layout.fragment_generar_fixture, container,
                false);
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
        // HORA
        btn_hora = (Button) v.findViewById(R.id.btn_hora);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {

//		// Fecha ver donde implementar
        for (int i = 0; i < getResources().getStringArray(R.array.fechaArray).length; i++) {

            fecha = new Fecha(i, getResources().getStringArray(
                    R.array.fechaArray)[i]);
            controladorAdeful.abrirBaseDeDatos();
            controladorAdeful.insertFecha(fecha);
            controladorAdeful.cerrarBaseDeDatos();
        }
//		// Anio ver donde implementar
        for (int i = 0; i < getResources().getStringArray(R.array.anioArray).length; i++) {

            anio = new Anio(i,
                    getResources().getStringArray(R.array.anioArray)[i]);

            controladorAdeful.abrirBaseDeDatos();
            controladorAdeful.insertAnio(anio);
            controladorAdeful.cerrarBaseDeDatos();
        }

        // DIVISION
        controladorAdeful.abrirBaseDeDatos();
        divisionArray = controladorAdeful.selectListaDivisionAdeful();
        if (divisionArray != null) {
            controladorAdeful.cerrarBaseDeDatos();
            // DIVSION SPINNER
            if (divisionArray.size() != 0) {
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
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
        }
        // TORNEO
        controladorAdeful.abrirBaseDeDatos();
        torneoArray = controladorAdeful.selectListaTorneoAdeful();
        if (torneoArray != null) {
            controladorAdeful.cerrarBaseDeDatos();
            if (torneoArray.size() != 0) {
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
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
        }
        // FECHA
        controladorAdeful.abrirBaseDeDatos();
        fechaArray = controladorAdeful.selectListaFecha();
        if (fechaArray != null) {
            controladorAdeful.cerrarBaseDeDatos();
            if (fechaArray.size() != 0) {
                // FECHA SPINNER
                adapterFixtureFecha = new AdapterSpinnerFecha(getActivity(),
                        R.layout.simple_spinner_dropdown_item, fechaArray);
                fixtureFechaSpinner.setAdapter(adapterFixtureFecha);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerFecha));
                fixtureFechaSpinner.setAdapter(adaptadorInicial);
            }
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
            if (anioArray.size() != 0) {
                // ANIO SPINNER
                adapterFixtureAnio = new AdapterSpinnerAnio(getActivity(),
                        R.layout.simple_spinner_dropdown_item, anioArray);
                fixtureAnioSpinner.setAdapter(adapterFixtureAnio);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerAnio));
                fixtureAnioSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
        }
        // CANCHA
        controladorAdeful.abrirBaseDeDatos();
        canchaArray = controladorAdeful.selectListaCanchaAdeful();
        if (canchaArray != null) {
            controladorAdeful.cerrarBaseDeDatos();
            if (canchaArray.size() != 0) {
                // CANCHA SPINNER
                adapterFixtureCancha = new AdapterSpinnerCancha(getActivity(),
                        R.layout.simple_spinner_dropdown_item, canchaArray);
                fixtureCanchaSpinner.setAdapter(adapterFixtureCancha);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerCancha));
                fixtureCanchaSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
        }
        // EQUIPO
        controladorAdeful.abrirBaseDeDatos();
        equipoArray = controladorAdeful.selectListaEquipoAdeful();
        if (equipoArray != null) {
            controladorAdeful.cerrarBaseDeDatos();
            if (equipoArray.size() != 0) {
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
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
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
                // TODO Auto-generated method stub
                setTime();
            }
        });

        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        //Metodo Extra
        if (actualizar) {

            idFixtureExtra = getActivity().getIntent().getIntExtra("id_fixture", 0);

            //DIVISION
            fixtureDivisionSpinner.setSelection(getActivity().getIntent()
                    .getIntExtra("divisionSpinner", 0) - 1);
            // TORNEO
            fixtureTorneoSpinner.setSelection(getActivity().getIntent()
                    .getIntExtra("torneoSpinner", 0) - 1);
            // FECHA
            fixtureFechaSpinner.setSelection(getActivity().getIntent()
                    .getIntExtra("fechaSpinner", 0) - 1);
            // ANIO
            fixtureAnioSpinner.setSelection(getActivity().getIntent()
                    .getIntExtra("anioSpiner", 0) - 1);
            // CANCHA
            fixtureCanchaSpinner.setSelection(getActivity().getIntent()
                    .getIntExtra("canchaSpinner", 0) - 1);
            // LOCAL
            fixtureLocalSpinner.setSelection(getActivity().getIntent()
                    .getIntExtra("localSpinner", 0) - 1);
            // VISITA
            fixtureVisitaSpinner.setSelection(getActivity().getIntent()
                    .getIntExtra("visitaSpinner", 0) - 1);
            btn_dia.setText(getActivity().getIntent()
                    .getStringExtra("dia"));
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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


            if (fixtureDivisionSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerDivision))) {
                Toast.makeText(getActivity(), "Debe agregar un division (Liga).",
                        Toast.LENGTH_SHORT).show();
            }else if (fixtureTorneoSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerTorneo))) {
                Toast.makeText(getActivity(), "Debe agregar un torneo (Liga).",
                        Toast.LENGTH_SHORT).show();
            }else if (fixtureFechaSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerFecha))) {
                Toast.makeText(getActivity(), "Debe agregar una fecha.",
                        Toast.LENGTH_SHORT).show();
            }else if (fixtureAnioSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerAnio))) {
                Toast.makeText(getActivity(), "Debe agregar un año.",
                        Toast.LENGTH_SHORT).show();
            }else if (fixtureLocalSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerEquipo)) || fixtureVisitaSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerEquipo))) {
                Toast.makeText(getActivity(), "Debe agregar un equipo (Liga).",
                        Toast.LENGTH_SHORT).show();
            }else if (fixtureCanchaSpinner.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerCancha))) {
                Toast.makeText(getActivity(), "Debe agregar una cancha (Liga).",
                        Toast.LENGTH_SHORT).show();
            } else if (btn_dia.getText()
                    .toString().equals("Día")) {
                    Toast.makeText(getActivity(), "Debe ingresar el día.",
                            Toast.LENGTH_SHORT).show();
            } else if (btn_hora.getText()
                    .toString().equals("Hora")) {
                Toast.makeText(getActivity(), "Debe ingresar la hora.",
                        Toast.LENGTH_SHORT).show();
            }else {
                String usuario = "Administrador";
                String fechaCreacion = controladorAdeful.getFechaOficial();
                String fechaActualizacion = fechaCreacion;

                division = (Division) fixtureDivisionSpinner.getSelectedItem();
                equipol = (Equipo) fixtureLocalSpinner.getSelectedItem();
                equipov = (Equipo) fixtureVisitaSpinner.getSelectedItem();
                torneo = (Torneo) fixtureTorneoSpinner.getSelectedItem();
                cancha = (Cancha) fixtureCanchaSpinner.getSelectedItem();
                fecha = (Fecha) fixtureFechaSpinner.getSelectedItem();
                anio = (Anio) fixtureAnioSpinner.getSelectedItem();

                //FIXTURE NVO
                if (insertar) {
                    fixture = new Fixture(0, equipol.getID_EQUIPO(),
                            equipov.getID_EQUIPO(), division.getID_DIVISION(),
                            torneo.getID_TORNEO(), cancha.getID_CANCHA(),
                            fecha.getID_FECHA(), anio.getID_ANIO(), btn_dia.getText()
                            .toString(), btn_hora.getText().toString(),
                            usuario, fechaCreacion, usuario, fechaActualizacion);

                    controladorAdeful.abrirBaseDeDatos();
                    if (controladorAdeful.insertFixtureAdeful(fixture)) {
                        controladorAdeful.cerrarBaseDeDatos();

                        Toast.makeText(getActivity(), "Partido Cargado Correctamente",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        controladorAdeful.cerrarBaseDeDatos();
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                                Toast.LENGTH_SHORT).show();
                    }
                } else { //FIXTURE ACTUALIZAR

                    fixture = new Fixture(idFixtureExtra, equipol.getID_EQUIPO(),
                            equipov.getID_EQUIPO(), division.getID_DIVISION(),
                            torneo.getID_TORNEO(), cancha.getID_CANCHA(),
                            fecha.getID_FECHA(), anio.getID_ANIO(), btn_dia.getText()
                            .toString(), btn_hora.getText().toString(),
                            null, null, usuario, fechaActualizacion);

                    controladorAdeful.abrirBaseDeDatos();
                    if (controladorAdeful.actualizarFixtureAdeful(fixture)) {
                        controladorAdeful.cerrarBaseDeDatos();

                        actualizar = false;
                        insertar = true;
                        Toast.makeText(getActivity(), "Partido Actualizado Correctamente",
                                Toast.LENGTH_SHORT).show();

                    } else {
                        controladorAdeful.cerrarBaseDeDatos();
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                                Toast.LENGTH_SHORT).show();
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