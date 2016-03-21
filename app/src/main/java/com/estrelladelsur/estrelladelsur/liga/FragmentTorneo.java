package com.estrelladelsur.estrelladelsur.liga;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerTorneo;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;

public class FragmentTorneo extends Fragment {

    private DialogoAlerta dialogoAlerta;
    private boolean insertar = true;
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
    private ImageView imageButtonEquipo;
    private boolean isActual;
    private LinearLayout linearTorneoActual;
    private boolean isChecked = false;
    private AuxiliarGeneral auxiliarGeneral;
    private ArrayList<Anio> anioArray;
    private AdapterSpinnerAnio adapterSpinnerAnio;
    private ArrayAdapter<String> adaptadorInicial;
    private Anio anio;
    String usuario = "Administrador";
    String fechaCreacion = null;
    String fechaActualizacion = fechaCreacion;

    public static FragmentTorneo newInstance() {
        FragmentTorneo fragment = new FragmentTorneo();
        return fragment;
    }

    public FragmentTorneo() {
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
        editTextTorneo = (EditText) v.findViewById(
                R.id.editTextDescripcion);

        imageButtonEquipo = (ImageView) v.findViewById(
                R.id.imageButtonEquipo_Cancha);
        checkboxTorneoActual = (CheckBox) v.findViewById(
                R.id.checkboxTorneoActual);
        spinnerAnioTorneoActual = (Spinner) v.findViewById(
                R.id.spinnerAnioTorneoActual);
        linearTorneoActual = (LinearLayout) v.findViewById(
                R.id.linearTorneoActual);
        linearTorneoActual.setVisibility(View.VISIBLE);
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
        fechaCreacion = controladorAdeful.getFechaOficial();
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        editTextTorneo.setHint("Ingrese un Torneo");
        editTextTorneo.setHintTextColor(Color.GRAY);
        //VERICAMOS SI HAY UN TORNEO MARCADO COMO ACTUAL
        isActual = controladorAdeful.selectIsActualTorneoActualAdeful();
        // SI ES TRUE OCULTAMOS
        if (isActual) {
            linearTorneoActual.setVisibility(View.GONE);
        }
        imageButtonEquipo.setVisibility(View.GONE);
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
                                        // TODO Auto-generated method stub
                                        if (controladorAdeful.eliminarTorneoAdeful(
                                                torneoArray.get(position)
                                                        .getID_TORNEO())) {
                                            recyclerViewLoadTorneo();
                                            Toast.makeText(
                                                    getActivity(),
                                                    "Torneo Eliminado Correctamente",
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
                                        // TODO Auto-generated method stub
                                        dialogoAlerta.alertDialog.dismiss();
                                    }
                                });
                    }

                    @Override
                    public void onClick(View view, int position) {
                        // TODO Auto-generated method stub

                        insertar = false;
                        isChecked = torneoArray.get(position).getACTUAL();
                        if (isChecked) {
                            linearTorneoActual.setVisibility(View.VISIBLE);
                            checkboxTorneoActual.setChecked(isChecked);
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
            adaptadorTorneo = new AdaptadorRecyclerTorneo(torneoArray);
            adaptadorTorneo.notifyDataSetChanged();
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

    public void guardarTorneo() {

        anio = (Anio) spinnerAnioTorneoActual.getSelectedItem();
        torneo = new Torneo(0, editTextTorneo.getText()
                .toString(), isChecked, anio.getID_ANIO(), usuario, fechaCreacion, usuario,
                fechaActualizacion);
        if (controladorAdeful.insertTorneoAdeful(torneo)) {
            recyclerViewLoadTorneo();
            editTextTorneo.setText("");
            if(checkboxTorneoActual.isChecked()){
                checkboxTorneoActual.setChecked(false);
                linearTorneoActual.setVisibility(View.GONE);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    public void actualizarTorneo() {
        anio = (Anio) spinnerAnioTorneoActual.getSelectedItem();
        torneo = new Torneo(torneoArray.get(posicion)
                .getID_TORNEO(), editTextTorneo.getText()
                .toString(), isChecked, anio.getID_ANIO(), null, null, usuario, fechaActualizacion);
        if (controladorAdeful.actualizarTorneoAdeful(torneo)) {
            recyclerViewLoadTorneo();
            editTextTorneo.setText("");
            insertar = true;
            Toast.makeText(getActivity(),
                    "Torneo actualizado correctamente.",
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
            // TODO Auto-generated method stub
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
        // menu.getItem(6).setVisible(false);// cargo
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
                    if (insertar) {
                        guardarTorneo();
                    } else {
                        actualizarTorneo();
                    }
                }
            } else {

                if (insertar) {
                    guardarTorneo();
                } else {
                    actualizarTorneo();
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