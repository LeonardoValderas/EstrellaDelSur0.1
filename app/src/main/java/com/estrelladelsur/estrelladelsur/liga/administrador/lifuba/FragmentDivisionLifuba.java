package com.estrelladelsur.estrelladelsur.liga.administrador.lifuba;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerDivision;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.administrador.lifuba.ControladorLifuba;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericAdeful;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericLifuba;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import java.util.ArrayList;

public class FragmentDivisionLifuba extends Fragment implements MyAsyncTaskListener {

    private DialogoAlerta dialogoAlerta;
    private RecyclerView recycleViewDivision;
    private ArrayList<Division> divisionArray;
    private Division division;
    private AdaptadorRecyclerDivision adaptadorDivision;
    private EditText editTextDivision;
    private int gestion = 0;//0-insert //1-update//2-delete
    private int posicion;
    private String URL = null, divisionText = null, usuario = null;
    private ControladorLifuba controladorLifuba;
    private int CheckedPositionFragment;
    private ImageView imageButtonEquipo;
    private TextInputLayout editTextInputDescripcion;
    private Typeface editTextFont;
    private AuxiliarGeneral auxiliarGeneral;
    private Request request;
    private boolean isInsert = true, isDelete = false;

    public static FragmentDivisionLifuba newInstance() {
        FragmentDivisionLifuba fragment = new FragmentDivisionLifuba();
        return fragment;
    }

    public FragmentDivisionLifuba() {
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        controladorLifuba = new ControladorLifuba(getActivity());
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
        editTextDivision = (EditText) v.findViewById(
                R.id.editTextDescripcion);
        editTextDivision.setTypeface(editTextFont);

        editTextInputDescripcion = (TextInputLayout) v.findViewById(R.id.editTextInputDescripcion);

        imageButtonEquipo = (ImageView) v.findViewById(
                R.id.imageButtonEquipo_Cancha);
        imageButtonEquipo.setVisibility(View.GONE);
        recycleViewDivision = (RecyclerView) v.findViewById(
                R.id.recycleViewGeneral);
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
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
        recyclerViewLoadDivision();
    }

    private void init() {

        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
        editTextInputDescripcion.setHint("Ingrese una división");
        recyclerViewLoadDivision();
        recycleViewDivision.addOnItemTouchListener(new RecyclerTouchListener(
                getActivity(), recycleViewDivision, new ClickListener() {
            @Override
            public void onLongClick(View view, final int position) {

                dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                        "Desea eliminar la división?", null, null);
                dialogoAlerta.btnAceptar.setText("Aceptar");
                dialogoAlerta.btnCancelar.setText("Cancelar");

                dialogoAlerta.btnAceptar
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                gestion = 2;
                                cargarEntidad(divisionArray
                                        .get(position)
                                        .getID_DIVISION(), 3);
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
                //  insertar = false;
                editTextDivision.setText(divisionArray.get(position)
                        .getDESCRIPCION());
                posicion = position;
            }
        }));
    }

    public void recyclerViewLoadDivision() {
        recycleViewDivision.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewDivision.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewDivision.setItemAnimator(new DefaultItemAnimator());

        divisionArray = controladorLifuba.selectListaDivisionLifuba();
        if (divisionArray != null) {
            adaptadorDivision = new AdaptadorRecyclerDivision(divisionArray, getActivity());
            recycleViewDivision.setAdapter(adaptadorDivision);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
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
        }
    }

    public void inicializarControles(String mensaje) {
        recyclerViewLoadDivision();
        editTextDivision.setText("");
        Toast.makeText(getActivity(),
                mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void cargarEntidad(int id, int ws) {
        URL = null;
        URL = auxiliarGeneral.getURLDIVISIONLIFUBAALL();
        if (ws != 3) {
            division = null;
            divisionText = editTextDivision.getText()
                    .toString();
        }
        division = new Division(id, divisionText, usuario, auxiliarGeneral.getFechaOficial(),
                usuario, auxiliarGeneral.getFechaOficial());
        envioWebService(ws);
    }

    public void envioWebService(int tipo) {
        String fecha = auxiliarGeneral.getFechaOficial();
        request = new Request();
        request.setMethod("POST");

        //0 = insert // 1 = update // 2 = delete
        if (tipo == 0) {
            isInsert = true;
            request.setParametrosDatos("division", division.getDESCRIPCION());
            request.setParametrosDatos("usuario_creador", division.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", division.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Division");
        } else if (tipo == 1) {
            isInsert = false;
            request.setParametrosDatos("division", division.getDESCRIPCION());
            request.setParametrosDatos("id_division", String.valueOf(division.getID_DIVISION()));
            request.setParametrosDatos("usuario_actualizacion", division.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", division.getFECHA_ACTUALIZACION());
            URL = URL + auxiliarGeneral.getUpdatePHP("Division");
        } else {
            isDelete = true;
            request.setParametrosDatos("id_division", String.valueOf(division.getID_DIVISION()));
            request.setParametrosDatos("fecha_actualizacion", fecha);
            URL = URL + auxiliarGeneral.getDeletePHP("Division");
        }
        if (auxiliarGeneral.isNetworkAvailable(getActivity())) {
            if (isDelete)
                new AsyncTaskGenericLifuba(getContext(), this, URL, request, "División", division, isInsert, isDelete, division.getID_DIVISION(), "a", false, fecha);
            else
                new AsyncTaskGenericLifuba(getContext(), this, URL, request, "División", division, isInsert, isDelete, division.getID_DIVISION(), "a", false);
        } else
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
            if (editTextDivision.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Ingrese una división.",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (gestion == 0) {
                    cargarEntidad(0, 0);
                } else if (gestion == 1) {
                    cargarEntidad(divisionArray.get(
                            posicion).getID_DIVISION(), 1);
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