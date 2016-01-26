package com.estrelladelsur.estrelladelsur.miequipo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerDivisionEntrenamiento;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerCancha;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoListCheck;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento_Division;

public class FragmentGenerarEntrenamiento extends Fragment {

    private ImageButton imageButton1;
    private DialogoListCheck dialogoListCheck;
    private ListView entrenamientolistView;
    private int CheckedPositionFragment;
   // private DateFormat formate = DateFormat.getDateInstance();
    private SimpleDateFormat formate = new SimpleDateFormat(
            "dd-MM-yyyy");
    private DateFormat form = DateFormat.getTimeInstance(DateFormat.SHORT);
    private Calendar calendar = Calendar.getInstance();
    private Calendar calenda = Calendar.getInstance();
    private Spinner spinnerLugarEntrenamiento;
    private Button buttonFechaEntrenamiento;
    private Button buttonHoraEntrenamiento;
    private ControladorAdeful controladorAdeful;
    private ArrayList<Cancha> canchaArray;
    private AdapterSpinnerCancha adapterFixtureCancha;
    private RecyclerView recycleViewGeneral;
    private ArrayList<Entrenamiento_Division> divisionArray;
    private AdaptadorRecyclerDivisionEntrenamiento adaptadorRecyclerDivisionEntrenamiento;
    private Cancha lugar;
    private int id_x_division;
    private Entrenamiento_Division entrenamiento_Division;
    private Entrenamiento entrenamiento;
    private boolean bandera = true;
    private ArrayAdapter<String> adaptadorInicial;

    public static FragmentGenerarEntrenamiento newInstance() {
        FragmentGenerarEntrenamiento fragment = new FragmentGenerarEntrenamiento();
        return fragment;
    }

    public FragmentGenerarEntrenamiento() {
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
        View v = inflater.inflate(R.layout.fragment_generar_entrenamiento,
                container, false);
        // RECYCLER
        recycleViewGeneral = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
        // CANCHA
        spinnerLugarEntrenamiento = (Spinner) v
                .findViewById(R.id.spinnerLugarEntrenamiento);
        // DIA
        buttonFechaEntrenamiento = (Button) v
                .findViewById(R.id.buttonFechaEntrenamiento);
        // HORA
        buttonHoraEntrenamiento = (Button) v
                .findViewById(R.id.buttonHoraEntrenamiento);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        // CANCHA

        controladorAdeful.abrirBaseDeDatos();
        canchaArray = controladorAdeful.selectListaCanchaAdeful();
        if(canchaArray != null) {
            controladorAdeful.cerrarBaseDeDatos();
            if (canchaArray.size() != 0) {
                // CANCHA SPINNER
                adapterFixtureCancha = new AdapterSpinnerCancha(getActivity(),
                        R.layout.simple_spinner_dropdown_item, canchaArray);
                spinnerLugarEntrenamiento.setAdapter(adapterFixtureCancha);
            }else{
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerCancha));
                spinnerLugarEntrenamiento.setAdapter(adaptadorInicial);
            }
        }else{
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
        }
        // RECLYCLER
        recycleViewGeneral.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewGeneral.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewGeneral.setItemAnimator(new DefaultItemAnimator());

        recyclerViewLoadDivision();

        buttonFechaEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setDate();
            }
        });
        // HORA
        buttonHoraEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                setTime();
            }
        });
    }

    public void updatedate() {
        buttonFechaEntrenamiento.setText(formate.format(calendar.getTime()));
    }

    public void updatetime() {
        buttonHoraEntrenamiento.setText(form.format(calenda.getTime()));
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

    public void recyclerViewLoadDivision() {

        controladorAdeful.abrirBaseDeDatos();
        divisionArray = controladorAdeful
                .selectListaDivisionEntrenamientoAdeful();
        if(divisionArray != null) {
            controladorAdeful.cerrarBaseDeDatos();

            adaptadorRecyclerDivisionEntrenamiento = new AdaptadorRecyclerDivisionEntrenamiento(
                    divisionArray);
            adaptadorRecyclerDivisionEntrenamiento.notifyDataSetChanged();
            recycleViewGeneral.setAdapter(adaptadorRecyclerDivisionEntrenamiento);
        }else{
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
        }
    }

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

            ArrayList<Entrenamiento_Division> listaDivisiones = adaptadorRecyclerDivisionEntrenamiento
                    .getEntrenamientoList();

            if (spinnerLugarEntrenamiento.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerCancha))) {
                Toast.makeText(getActivity(),
                        "Debe Cargar un Lugar de Emtrenamiento.(Liga)",
                        Toast.LENGTH_SHORT).show();
            } else if (buttonFechaEntrenamiento.getText().toString()
                    .equals("Día")) {
                Toast.makeText(getActivity(),
                        "Debe Seleccionar una Fecha.", Toast.LENGTH_SHORT)
                        .show();
            } else if (buttonHoraEntrenamiento.getText().toString()
                    .equals("Hora")) {
                Toast.makeText(getActivity(), "Debe Seleccionar una Hora.",
                        Toast.LENGTH_SHORT).show();
            } else {

                ArrayList<Integer> id_divisin_array = new ArrayList<Integer>();
                for (int i = 0; i < listaDivisiones.size(); i++) {
                    Entrenamiento_Division entrenamientoDivision = listaDivisiones
                            .get(i);
                    if (entrenamientoDivision.isSelected() == true) {
                        bandera = true;
                        id_divisin_array.add(entrenamientoDivision
                                .getID_DIVISION());
                    }
                }
                if (!bandera) {
                    Toast.makeText(getActivity(),
                            "Seleccione la/s División/es Citada/s.",
                            Toast.LENGTH_SHORT).show();

                } else {
                    String usuario = "Administrador";
                    String fechaCreacion = controladorAdeful.getFechaOficial();
                    String fechaActualizacion = fechaCreacion;

                    lugar = (Cancha) spinnerLugarEntrenamiento
                            .getSelectedItem();
                    entrenamiento = new Entrenamiento(0,
                            buttonFechaEntrenamiento.getText().toString(),
                            buttonHoraEntrenamiento.getText().toString(),
                            lugar.getID_CANCHA(),usuario,fechaCreacion,usuario,fechaActualizacion);

                   // inserto entrenamiento y verifico el id
                    controladorAdeful.abrirBaseDeDatos();
                    int id_entrenamiento = controladorAdeful
                            .insertEntrenamientoAdeful(entrenamiento);
                    controladorAdeful.cerrarBaseDeDatos();

                    if (id_entrenamiento > 0) {
                        for (int i = 0; i < id_divisin_array.size(); i++) {

                            entrenamiento_Division = new Entrenamiento_Division(
                                    0, id_entrenamiento, id_divisin_array
                                    .get(i), "", true);
                            controladorAdeful.abrirBaseDeDatos();
                            if (!controladorAdeful
                                    .insertEntrenamientoDivisionAdeful(entrenamiento_Division))
                                break;
                            controladorAdeful.cerrarBaseDeDatos();
                        }
                        buttonFechaEntrenamiento.setText("Día");
                        buttonHoraEntrenamiento.setText("Hora");
                        recyclerViewLoadDivision();
                        Toast.makeText(
                                getActivity(),
                                "Entrenamiento cargado correctamente.",
                                Toast.LENGTH_SHORT).show();
                        controladorAdeful.cerrarBaseDeDatos();

                    } else {
                        Toast.makeText(
                                getActivity(),
                                getResources().getString(R.string.error_data_base),
                                Toast.LENGTH_SHORT).show();
                        controladorAdeful.cerrarBaseDeDatos();
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
