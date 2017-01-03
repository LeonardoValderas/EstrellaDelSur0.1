package com.estrelladelsur.estrelladelsur.miequipo.administrador.general;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerDivisionEntrenamiento;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerCancha;
import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericAdeful;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FragmentGenerarEntrenamiento extends Fragment implements MyAsyncTaskListener {

    private int CheckedPositionFragment;
    private SimpleDateFormat formate = new SimpleDateFormat(
            "dd-MM-yyyy");
    private DateFormat form = DateFormat.getTimeInstance(DateFormat.SHORT);
    private Calendar calendar = Calendar.getInstance();
    private Calendar calenda = Calendar.getInstance();
    private Spinner spinnerLugarEntrenamiento;
    private Button buttonFechaEntrenamiento;
    private TextView textViewLugar, textViewDivisionCitada;
    private Button buttonHoraEntrenamiento;
    private ControladorAdeful controladorAdeful;
    private ArrayList<Cancha> canchaArray;
    private AdapterSpinnerCancha adapterFixtureCancha;
    private RecyclerView recycleViewGeneral;
    private ArrayList<Entrenamiento> divisionArray;
    private ArrayList<Entrenamiento> divisionArrayExtra;
    private AdaptadorRecyclerDivisionEntrenamiento adaptadorRecyclerDivisionEntrenamiento;
    private Cancha lugar;
    private Entrenamiento entrenamiento;
    private boolean bandera = false;
    private ArrayAdapter<String> adaptadorInicial;
    private boolean actualizar = false;
    private int idEntrenamientoExtra;
    private boolean insertar = true;
    private ArrayList<Integer> id_divisin_array = null, id_division_add_array = null, id_division_delete_array = null;
    private Typeface editTextFont;
    private Typeface textViewFont;
    private AuxiliarGeneral auxiliarGeneral;
    private Request request;
    private String URL = null, usuario = null;
    private CheckBox checkAll;

    public static FragmentGenerarEntrenamiento newInstance() {
        FragmentGenerarEntrenamiento fragment = new FragmentGenerarEntrenamiento();
        return fragment;
    }

    public FragmentGenerarEntrenamiento() {
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
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        editTextFont = auxiliarGeneral.textFont(getActivity());
        textViewFont = auxiliarGeneral.tituloFont(getActivity());
        // RECYCLER
        recycleViewGeneral = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
        // CANCHA
        spinnerLugarEntrenamiento = (Spinner) v
                .findViewById(R.id.spinnerLugarEntrenamiento);
        // DIA
        buttonFechaEntrenamiento = (Button) v
                .findViewById(R.id.buttonFechaEntrenamiento);
        buttonFechaEntrenamiento.setTypeface(editTextFont, Typeface.BOLD);
        // HORA
        buttonHoraEntrenamiento = (Button) v
                .findViewById(R.id.buttonHoraEntrenamiento);
        buttonHoraEntrenamiento.setTypeface(editTextFont, Typeface.BOLD);
        //TITULO LUGAR
        textViewLugar = (TextView) v
                .findViewById(R.id.textViewLugar);
        textViewLugar.setTypeface(textViewFont);
        //TITULO DIVISIONES
        textViewDivisionCitada = (TextView) v
                .findViewById(R.id.textViewDivisionCitada);
        textViewDivisionCitada.setTypeface(textViewFont);

        checkAll = (CheckBox)v.findViewById(R.id.checkAll);

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
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        // CANCHA
        canchaArray = controladorAdeful.selectListaCanchaAdeful();
        if (canchaArray != null) {
            if (canchaArray.size() != 0) {
                // CANCHA SPINNER
                adapterFixtureCancha = new AdapterSpinnerCancha(getActivity(),
                        R.layout.simple_spinner_dropdown_item, canchaArray);
                spinnerLugarEntrenamiento.setAdapter(adapterFixtureCancha);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerCancha));
                spinnerLugarEntrenamiento.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        //Metodo Extra
        if (actualizar) {

            idEntrenamientoExtra = getActivity().getIntent().getIntExtra("id_entrenamiento", 0);
            //DIA
            buttonFechaEntrenamiento.setText(getActivity().getIntent()
                    .getStringExtra("dia"));
            // HORA
            buttonHoraEntrenamiento.setText(getActivity().getIntent()
                    .getStringExtra("hora"));
            // SPINNER
            spinnerLugarEntrenamiento.setSelection(getPositionCancha(getActivity().getIntent()
                    .getIntExtra("id_cancha", 0)));

            insertar = false;
        }

        // RECLYCLER
        recycleViewGeneral.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewGeneral.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewGeneral.setItemAnimator(new DefaultItemAnimator());

        recyclerViewLoadDivision();
        //DIA
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
                setTime();
            }
        });

        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    adaptadorRecyclerDivisionEntrenamiento.selectAll(true);
                else
                    adaptadorRecyclerDivisionEntrenamiento.selectAll(false);
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

        divisionArray = controladorAdeful
                .selectListaDivisionEntrenamientoAdeful();
        if (divisionArray != null) {
            //EDITAR
            if (!insertar) {
                divisionArrayExtra = controladorAdeful
                        .selectListaDivisionEntrenamientoAdefulId(idEntrenamientoExtra);
                if (divisionArrayExtra != null) {
                    for (int extra = 0; extra < divisionArrayExtra.size(); extra++) {
                        for (int div = 0; div < divisionArray.size(); div++) {
                            if (divisionArrayExtra.get(extra).getID_DIVISION() == divisionArray.get(div).getID_DIVISION()) {
                                divisionArray.get(div).setSelected(true);
                                break;
                            }
                        }
                    }
                } else {
                    auxiliarGeneral.errorDataBase(getActivity());
                }
            }
            //ADAPTER
            adaptadorRecyclerDivisionEntrenamiento = new AdaptadorRecyclerDivisionEntrenamiento(
                    divisionArray, getActivity());
            recycleViewGeneral.setAdapter(adaptadorRecyclerDivisionEntrenamiento);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    private int getPositionCancha(int idCancha) {
        int index = 0;

        for (int i = 0; i < canchaArray.size(); i++) {
            if (canchaArray.get(i).getID_CANCHA() == (idCancha)) {
                index = i;
            }
        }
        return index;
    }

    public void inicializarControles(String mensaje) {
        buttonFechaEntrenamiento.setText("Día");
        buttonHoraEntrenamiento.setText("Hora");
        recyclerViewLoadDivision();
        checkAll.setChecked(false);
        bandera = false;
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void cargarEntidad(int id, ArrayList<Integer> divisionArrayAdd, ArrayList<Integer> divisionArrayDelete) throws JSONException {
        URL = null;
        URL = auxiliarGeneral.getURLENTRENAMIENTOADEFULALL();

        lugar = (Cancha) spinnerLugarEntrenamiento
                .getSelectedItem();

        entrenamiento = new Entrenamiento(id,
                buttonFechaEntrenamiento.getText().toString(),
                buttonHoraEntrenamiento.getText().toString(),
                lugar.getID_CANCHA(), divisionArrayAdd, divisionArrayDelete, usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());

        envioWebService();
    }

    public void envioWebService() throws JSONException {

        JSONArray divisionArrayAdd = new JSONArray();
        JSONArray divisionArrayDelete = new JSONArray();
        request = new Request();
        request.setMethod("POST");

        if (entrenamiento.getDivisionArrayAdd() != null) {
            for (int i = 0; i < entrenamiento.getDivisionArrayAdd().size(); i++) {

                JSONObject divisionIds = new JSONObject();
                divisionIds.put("division", String.valueOf(entrenamiento.getDivisionArrayAdd().get(i)));
                divisionArrayAdd.put(divisionIds);
            }
        }
        request.setParametrosDatos("division", divisionArrayAdd.toString());
        request.setParametrosDatos("dia", entrenamiento.getDIA());
        request.setParametrosDatos("hora", entrenamiento.getHORA());
        request.setParametrosDatos("cancha", String.valueOf(entrenamiento.getID_CANCHA()));

        if (insertar) {
            request.setParametrosDatos("usuario_creador", entrenamiento.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", entrenamiento.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Entrenamiento");
        } else {

            if (entrenamiento.getDivisionArrayDelete() != null) {
                for (int i = 0; i < entrenamiento.getDivisionArrayDelete().size(); i++) {
                    JSONObject divisionIds = new JSONObject();
                    divisionIds.put("delete", String.valueOf(entrenamiento.getDivisionArrayDelete().get(i)));
                    divisionArrayDelete.put(divisionIds);
                }
            }
            request.setParametrosDatos("delete", divisionArrayDelete.toString());
            request.setParametrosDatos("id_entrenamiento", String.valueOf(entrenamiento.getID_ENTRENAMIENTO()));
            request.setParametrosDatos("usuario_actualizacion", entrenamiento.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", entrenamiento.getFECHA_ACTUALIZACION());
            URL = URL + auxiliarGeneral.getUpdatePHP("Entrenamiento");
        }
        if (auxiliarGeneral.isNetworkAvailable(getActivity()))
            new AsyncTaskGenericAdeful(getContext(), this, URL, request, "Entrenamiento", entrenamiento, insertar, "o");
        else
            auxiliarGeneral.errorWebService(getActivity(), getActivity().getResources().getString(R.string.error_without_internet));
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
            ArrayList<Entrenamiento> listaDivisiones = adaptadorRecyclerDivisionEntrenamiento
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
                id_divisin_array = new ArrayList<>();
                for (int i = 0; i < listaDivisiones.size(); i++) {
                    Entrenamiento entrenamientoDivision = listaDivisiones
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

                    if (insertar) {
                        try {
                            cargarEntidad(0, id_divisin_array, null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        id_division_delete_array = new ArrayList<>();
                        id_division_add_array = new ArrayList<>();
                        boolean isAdd = false;
                        boolean isDelete = false;

                        for (int i = 0; i < id_divisin_array.size(); i++) {
                            for (int d = 0; d < divisionArrayExtra.size(); d++) {
                                if (id_divisin_array.get(i) == divisionArrayExtra.get(d).getID_DIVISION()) {
                                    isAdd = false;
                                    break;
                                } else {
                                    isAdd = true;
                                }
                            }
                            if (isAdd)
                                id_division_add_array.add(id_divisin_array.get(i));
                        }
                        for (int i = 0; i < divisionArrayExtra.size(); i++) {
                            for (int d = 0; d < id_divisin_array.size(); d++) {
                                if (divisionArrayExtra.get(i).getID_DIVISION() == id_divisin_array.get(d)) {
                                    isDelete = false;
                                    break;
                                } else {
                                    isDelete = true;
                                }
                            }
                            if (isDelete)
                                id_division_delete_array.add(divisionArrayExtra.get(i).getID_DIVISION());

                        }
                        try {
                            cargarEntidad(idEntrenamientoExtra, id_division_add_array, id_division_delete_array);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
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
