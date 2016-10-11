package com.estrelladelsur.estrelladelsur.liga.adeful;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerTorneo;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGeneric;
import com.estrelladelsur.estrelladelsur.webservice.JsonParsing;
import com.estrelladelsur.estrelladelsur.webservice.Request;

public class FragmentTorneoAdeful extends Fragment implements MyAsyncTaskListener {

    private DialogoAlerta dialogoAlerta;
    private ProgressDialog dialog;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private JsonParsing jsonParsing = new JsonParsing(getActivity());
    private static final String TAG_ID = "id";
    //  private boolean insertar = true;
    private int posicion;
    private RecyclerView recycleViewTorneo;
    private ArrayList<Torneo> torneoArray;
    private Torneo torneo;
    private AdaptadorRecyclerTorneo adaptadorTorneo;
    private EditText editTextTorneo;
    private CheckBox checkboxTorneoActual;
    private Spinner spinnerAnioTorneoActual;
    private ControladorAdeful controladorAdeful;
    private int CheckedPositionFragment;
    private ImageView imageButtonActual;
    private Torneo torneoActual;
    private LinearLayout linearTorneoActual;
    private boolean isChecked = false;
    private ArrayList<Anio> anioArray;
    private AdapterSpinnerAnio adapterSpinnerAnio;
    private ArrayAdapter<String> adaptadorInicial;
    private Anio anio;
    private int gestion = 0;//0-insert //1-update//2-delete
    private String usuario = null, URL = null, torneoText = null, mensaje = null;
    private boolean checkedAnterior = false;
    // boolean isActual;
    private String GUARDAR = "Torneo cargado correctamente";
    private String ACTUALIZAR = "Torneo actualizado correctamente";
    private String ELIMINAR = "Torneo eliminado correctamente";
    private Typeface editTextFont;
    private AuxiliarGeneral auxiliarGeneral;
    private TextView torneoActualtext;
    private TextInputLayout editTextInputDescripcion;
    private Request request;
    private boolean isInsert = true, isDelete = false;

    public static FragmentTorneoAdeful newInstance() {
        FragmentTorneoAdeful fragment = new FragmentTorneoAdeful();
        return fragment;
    }

    public FragmentTorneoAdeful() {
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
        View v = inflater.inflate(R.layout.fragment_general_liga, container,
                false);
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        editTextFont = auxiliarGeneral.textFont(getActivity());
        editTextTorneo = (EditText) v.findViewById(
                R.id.editTextDescripcion);
        editTextTorneo.setTypeface(editTextFont);
        editTextInputDescripcion = (TextInputLayout) v.findViewById(R.id.editTextInputDescripcion);
        imageButtonActual = (ImageView) v.findViewById(
                R.id.imageButtonEquipo_Cancha);
        imageButtonActual.setVisibility(View.GONE);
        checkboxTorneoActual = (CheckBox) v.findViewById(
                R.id.checkboxTorneoActual);
        spinnerAnioTorneoActual = (Spinner) v.findViewById(
                R.id.spinnerAnioTorneoActual);
        linearTorneoActual = (LinearLayout) v.findViewById(
                R.id.linearTorneoActual);
        linearTorneoActual.setVisibility(View.VISIBLE);
        torneoActualtext = (TextView) v.findViewById(
                R.id.torneoActual);
        torneoActualtext.setTypeface(editTextFont);
        recycleViewTorneo = (RecyclerView) v.findViewById(
                R.id.recycleViewGeneral);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        editTextInputDescripcion.setHint("Ingrese un torneo");
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
        //VERICAMOS SI HAY UN TORNEO MARCADO COMO ACTUAL
        getIsActual();
        loadSpinnerAnio();
        checkboxTorneoActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked) {
                    spinnerAnioTorneoActual.setVisibility(View.GONE);
                    isChecked = false;
                } else {
                    spinnerAnioTorneoActual.setVisibility(View.VISIBLE);
                    isChecked = true;
                }
            }
        });
        recyclerViewLoadTorneo();
        recycleViewTorneo.addOnItemTouchListener(new
                RecyclerTouchListener(getActivity(), recycleViewTorneo,
                new ClickListener() {
                    @Override
                    public void onLongClick(View view, final int position) {

                        dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                                "Desea eliminar el torneo?", null, null);
                        dialogoAlerta.btnAceptar.setText("Aceptar");
                        dialogoAlerta.btnCancelar.setText("Cancelar");

                        dialogoAlerta.btnAceptar
                                .setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        gestion = 2;
                                        isChecked = torneoArray.get(position).getACTUAL();

                                        cargarEntidad(torneoArray
                                                .get(position)
                                                .getID_TORNEO(), 2);
                                        dialogoAlerta.alertDialog.dismiss();
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

                    @Override
                    public void onClick(View view, int position) {
                        gestion = 1;
                        // insertar = false;
                        isChecked = torneoArray.get(position).getACTUAL();
                        checkedAnterior = torneoArray.get(position).getACTUAL();
                        if (isChecked) {
                            linearTorneoActual.setVisibility(View.VISIBLE);
                            checkboxTorneoActual.setChecked(isChecked);
                            spinnerAnioTorneoActual.setVisibility(View.VISIBLE);
                            //ANIO
                            spinnerAnioTorneoActual.setSelection(getPositionSpinner(torneoActual.getFECHA_ANIO()));
                        }
                        editTextTorneo.setText(torneoArray.get(position)
                                .getDESCRIPCION());
                        posicion = position;
                    }
                }
        ));
    }

    public void recyclerViewLoadTorneo() {

        recycleViewTorneo.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewTorneo.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewTorneo.setItemAnimator(new DefaultItemAnimator());

        torneoArray = controladorAdeful.selectListaTorneoAdeful();
        if (torneoArray != null) {
            adaptadorTorneo = new AdaptadorRecyclerTorneo(torneoArray, getActivity());
            recycleViewTorneo.setAdapter(adaptadorTorneo);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    public void loadSpinnerAnio() {
        anioArray = controladorAdeful.selectListaAnio();
        if (anioArray != null) {
            controladorAdeful.cerrarBaseDeDatos();
            if (anioArray.size() != 0) {
                // ANIO SPINNER
                adapterSpinnerAnio = new AdapterSpinnerAnio(getActivity(),
                        R.layout.simple_spinner_dropdown_item, anioArray);
                spinnerAnioTorneoActual.setAdapter(adapterSpinnerAnio);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerAnio));
                spinnerAnioTorneoActual.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    private int getPositionSpinner(int anio) {

        int index = 0;
        for (int i = 0; i < anioArray.size(); i++) {
            if (anioArray.get(i).getID_ANIO() == anio) {
                index = i;
            }
        }

        return index;
    }

    public void inicializarControles(String mensaje) {
        recyclerViewLoadTorneo();
        editTextTorneo.setText("");
        if (checkboxTorneoActual.isChecked()) {
            OcultarLayoutIsActual();
            getIsActual();
        }
        Toast.makeText(getActivity(),
                mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void OcultarLayoutIsActual() {
        checkboxTorneoActual.setChecked(false);
        linearTorneoActual.setVisibility(View.GONE);
        spinnerAnioTorneoActual.setVisibility(View.GONE);
    }

    public void getIsActual() {
        torneoActual = controladorAdeful.selectActualTorneoAdeful();
        if (torneoActual != null) {
            // SI ES TRUE OCULTAMOS
            if (torneoActual.getISACTUAL()) {
                linearTorneoActual.setVisibility(View.GONE);
                isChecked = false;
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    public void setVisibilityActual() {
        linearTorneoActual.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostExecuteConcluded(boolean result, String mensaje) {
        if (result) {
            if (isDelete) {
                isDelete = false;
                gestion = 0;
                inicializarControles(mensaje);
            } else {
                if (isInsert) {
                    inicializarControles(mensaje);
                } else {
                    isInsert = true;
                    gestion = 0;
                    inicializarControles(mensaje);
                }
            }
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
            // TODO Auto-generated method stub
        }
    }

    public void cargarEntidad(int id, int ws) {
        URL = null;
        URL = auxiliarGeneral.getURL() + auxiliarGeneral.getURLTORNEOADEFUL();
        if (ws != 2) {
            torneoText = null;
            torneoText = editTextTorneo.getText()
                    .toString();
            anio = (Anio) spinnerAnioTorneoActual.getSelectedItem();
            torneo = new Torneo(id, editTextTorneo.getText()
                    .toString(), isChecked, checkedAnterior, anio.getID_ANIO(), usuario, auxiliarGeneral.getFechaOficial(), usuario,
                    auxiliarGeneral.getFechaOficial());

        } else {
            torneo = new Torneo(id, isChecked);
        }
        envioWebService(ws);
    }

    public void envioWebService(int tipo) {
        request = new Request();
        request.setMethod("POST");

        if (torneo.getACTUAL() && tipo != 2) {
            request.setParametrosDatos("id_anio", String.valueOf(torneo.getFECHA_ANIO()));
        }
        request.setParametrosDatos("actual", String.valueOf(torneo.getACTUAL()));
        //0 = insert // 1 = update // 2 = delete
        if (tipo == 0) {
            isInsert = true;
            request.setParametrosDatos("descripcion", torneo.getDESCRIPCION());
            request.setParametrosDatos("usuario_creador", torneo.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", torneo.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Torneo");
        } else if (tipo == 1) {
            isInsert = false;
            request.setParametrosDatos("descripcion", torneo.getDESCRIPCION());
            request.setParametrosDatos("id_torneo", String.valueOf(torneo.getID_TORNEO()));
            request.setParametrosDatos("actual_anterior", String.valueOf(torneo.getISACTUAL_ANTERIOR()));
            request.setParametrosDatos("usuario_actualizacion", torneo.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", torneo.getFECHA_ACTUALIZACION());
            URL = URL + auxiliarGeneral.getUpdatePHP("Torneo");
        } else {
            isDelete = true;
            request.setParametrosDatos("id_torneo", String.valueOf(torneo.getID_TORNEO()));
            request.setParametrosDatos("fecha_actualizacion", auxiliarGeneral.getFechaOficial());
            URL = URL + auxiliarGeneral.getDeletePHP("Torneo");
        }
        new AsyncTaskGeneric(getContext(), this, URL, request, "Torneo", torneo, isInsert, isDelete, torneo.getID_TORNEO(), "o", false);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

            /*Intent usuario = new Intent(getActivity(),
                    NavigationDrawerUsuario.class);
            startActivity(usuario);*/

            return true;
        }

        if (id == R.id.action_permisos) {
            return true;
        }

        if (id == R.id.action_guardar) {

            if (editTextTorneo.getText().toString().equals("")) {
                Toast.makeText(getActivity(),
                        "Ingrese el nombre del torneo.", Toast.LENGTH_SHORT)
                        .show();
            } else if (checkboxTorneoActual.isChecked()) {
                if (spinnerAnioTorneoActual.getSelectedItem().toString().equals(getResources().
                        getString(R.string.ceroSpinnerAnio))) {
                    Toast.makeText(getActivity(), "Debe agregar un año.",
                            Toast.LENGTH_SHORT).show();
                } else {

                    if (gestion == 0) {
                        cargarEntidad(0, 0);
                    } else if (gestion == 1) {
                        cargarEntidad(torneoArray.get(
                                posicion).getID_TORNEO(), 1);
                    }
                }
            } else {

                if (gestion == 0) {
                    cargarEntidad(0, 0);
                } else if (gestion == 1) {
                    cargarEntidad(torneoArray.get(
                            posicion).getID_TORNEO(), 1);
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