package com.estrelladelsur.estrelladelsur.miequipo.administrador.lifuba;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerResultado;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerFecha;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerTorneo;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoResultado;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.Resultado;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGeneric;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import java.util.ArrayList;

public class FragmentGenerarResultadoLifuba extends Fragment implements MyAsyncTaskListener {

    private int CheckedPositionFragment;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private int divisionSpinner, torneoSpinner, fechaSpinner, anioSpiner;
    private Spinner resultadoDivisionSpinner;
    private Spinner resultadoTorneoSpinner;
    private Spinner resultadoFechaSpinner;
    private Spinner resultadoAnioSpinner;
    private RecyclerView recyclerViewResultado;
    private FloatingActionButton botonFloating;
    private ControladorAdeful controladorAdeful;
    private TextView txtTitulo;
    private DialogoResultado dialogoResultado;
    private Division division;
    private Torneo torneo;
    private Fecha fecha;
    private Anio anio;
    private ArrayList<Division> divisionArray;
    private ArrayList<Torneo> torneoArray;
    private ArrayList<Fecha> fechaArray;
    private ArrayList<Anio> anioArray;
    private AdapterSpinnerTorneo adapterFixtureTorneo;
    private AdapterSpinnerDivision adapterFixtureDivision;
    private AdapterSpinnerFecha adapterFixtureFecha;
    private AdapterSpinnerAnio adapterFixtureAnio;
    private AdaptadorRecyclerResultado adaptadorResultado;
    private int posicionRecycler;
    private ArrayList<Resultado> arrayResultado;
    private DialogoAlerta dialogoAlerta;
    private ArrayAdapter<String> adaptadorInicial;
    private AuxiliarGeneral auxiliarGeneral;
    private Typeface titulos;
    private String URL = null, usuario = null;
    private Resultado resultado;
    private Request request;
    private boolean isReset = false;

    public static FragmentGenerarResultadoLifuba newInstance() {
        FragmentGenerarResultadoLifuba fragment = new FragmentGenerarResultadoLifuba();
        return fragment;
    }

    public FragmentGenerarResultadoLifuba() {
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
        View v = inflater.inflate(R.layout.fragment_resultado, container,
                false);

        // SPINNER DIVISION
        resultadoDivisionSpinner = (Spinner) v.findViewById(R.id.resultadoDivisionSpinner);
        // SPINNER TORNEO
        resultadoTorneoSpinner = (Spinner) v.findViewById(R.id.resultadoTorneoSpinner);
        // SPINNER FECHA
        resultadoFechaSpinner = (Spinner)v.findViewById(R.id.resultadoFechaSpinner);
        // SPINNER ANIO
        resultadoAnioSpinner = (Spinner) v.findViewById(R.id.resultadoAnioSpinner);
        // RECLYCLER
        recyclerViewResultado = (RecyclerView) v.findViewById(R.id.recycleViewResultado);
        // BUTTONFLOATING
        botonFloating = (FloatingActionButton) v.findViewById(R.id.botonFloating);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
        // DIVISION
        divisionArray = controladorAdeful.selectListaDivisionAdeful();
        if (divisionArray != null) {
            if (divisionArray.size() != 0) {
                // DIVSION SPINNER
                adapterFixtureDivision = new AdapterSpinnerDivision(
                        getActivity(), R.layout.simple_spinner_dropdown_item,
                        divisionArray);
                resultadoDivisionSpinner.setAdapter(adapterFixtureDivision);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerDivision));
                resultadoDivisionSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        // TORNEO
        torneoArray = controladorAdeful.selectListaTorneoAdeful();
        if (torneoArray != null) {
            if (torneoArray.size() != 0) {
                // TORNEO SPINNER
                adapterFixtureTorneo = new AdapterSpinnerTorneo(getActivity(),
                        R.layout.simple_spinner_dropdown_item, torneoArray);
                resultadoTorneoSpinner.setAdapter(adapterFixtureTorneo);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerTorneo));
                resultadoTorneoSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        // FECHA
        fechaArray = controladorAdeful.selectListaFecha();
        if (fechaArray != null) {
            // FECHA SPINNER
            adapterFixtureFecha = new AdapterSpinnerFecha(getActivity(),
                    R.layout.simple_spinner_dropdown_item, fechaArray);
            resultadoFechaSpinner.setAdapter(adapterFixtureFecha);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        // ANIO
        anioArray = controladorAdeful.selectListaAnio();
        if (anioArray != null) {
            // ANIO SPINNER
            adapterFixtureAnio = new AdapterSpinnerAnio(getActivity(),
                    R.layout.simple_spinner_dropdown_item, anioArray);
            resultadoAnioSpinner.setAdapter(adapterFixtureAnio);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        botonFloating.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (resultadoDivisionSpinner.getSelectedItem().toString().equals(getResources().
                        getString(R.string.ceroSpinnerDivision))) {
                    Toast.makeText(getActivity(), "Debe agregar un division (Liga).",
                            Toast.LENGTH_SHORT).show();
                } else if (resultadoTorneoSpinner.getSelectedItem().toString().equals(getResources().
                        getString(R.string.ceroSpinnerTorneo))) {
                    Toast.makeText(getActivity(), "Debe agregar un torneo (Liga).",
                            Toast.LENGTH_SHORT).show();
                } else {
                    division = (Division) resultadoDivisionSpinner
                            .getSelectedItem();
                    torneo = (Torneo) resultadoTorneoSpinner.getSelectedItem();
                    fecha = (Fecha) resultadoFechaSpinner.getSelectedItem();
                    anio = (Anio) resultadoAnioSpinner.getSelectedItem();

                    divisionSpinner = division.getID_DIVISION();
                    torneoSpinner = torneo.getID_TORNEO();
                    fechaSpinner = fecha.getID_FECHA();
                    anioSpiner = anio.getID_ANIO();
                    //LOAD RECYCLER
                    recyclerViewLoadResultado(divisionSpinner, torneoSpinner,
                            fechaSpinner, anioSpiner);
                }
            }
        });

        recyclerViewResultado.addOnItemTouchListener(new RecyclerTouchListener(
                getActivity(), recyclerViewResultado,
                new ClickListener() {

                    @Override
                    public void onClick(View view, int position) {
                        // TODO Auto-generated method stub
                        posicionRecycler = position;
                        if (!arrayResultado.get(position).getRESULTADO_LOCAL().equals("-")
                                && !arrayResultado.get(position)
                                .getRESULTADO_VISITA().equals("-")) {
                            dialogoResultado = new DialogoResultado(
                                    getActivity(), "RESULTADO",
                                    arrayResultado.get(position)
                                            .getEQUIPO_LOCAL(), arrayResultado
                                    .get(position).getEQUIPO_VISITA(),
                                    arrayResultado.get(position)
                                            .getRESULTADO_LOCAL(),
                                    arrayResultado.get(position)
                                            .getRESULTADO_VISITA());
                        } else {
                            dialogoResultado = new DialogoResultado(
                                    getActivity(), "RESULTADO",
                                    arrayResultado.get(position)
                                            .getEQUIPO_LOCAL(), arrayResultado
                                    .get(position).getEQUIPO_VISITA(),
                                    "", "");
                        }
                        dialogoResultado.imageButtonGoleadores
                                .setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                    }
                                });

                        dialogoResultado.btnAceptar
                                .setOnClickListener(new View.OnClickListener() {

                                                        @Override
                                                        public void onClick(View v) {

                                                            if (!dialogoResultado.resultadoLocal
                                                                    .getText().toString()
                                                                    .equals("")
                                                                    && !dialogoResultado.resultadoVisita
                                                                    .getText().toString()
                                                                    .equals("")) {

                                                                cargarEntidad(arrayResultado
                                                                                .get(posicionRecycler)
                                                                                .getID_FIXTURE(), dialogoResultado.resultadoLocal
                                                                                .getText()
                                                                                .toString(),
                                                                        dialogoResultado.resultadoVisita
                                                                                .getText()
                                                                                .toString());
                                                            } else {

                                                                dialogoResultado.resultadoTextErrorVacio
                                                                        .setVisibility(View.VISIBLE);
                                                                dialogoResultado.resultadoTextErrorVacio
                                                                        .setText("Ingrese ambos resultados.");
                                                            }
                                                        }
                                                    }
                                );

                        dialogoResultado.btnCancelar
                                .setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            dialogoResultado.alertDialog.dismiss();
                                                        }
                                                    }
                                );
                    }

                    @Override
                    public void onLongClick(View view, final int position) {

                        dialogoAlerta = new DialogoAlerta(getActivity(),
                                "ALERTA", "Desea resetear el resultado?", null,
                                null);
                        dialogoAlerta.btnAceptar.setText("Aceptar");
                        dialogoAlerta.btnCancelar.setText("Cancelar");

                        dialogoAlerta.btnAceptar
                                .setOnClickListener(new View.OnClickListener() {

                                    // @SuppressLint("NewApi")
                                    @Override
                                    public void onClick(View v) {
                                        isReset = true;
                                        cargarEntidad(arrayResultado
                                                        .get(posicionRecycler)
                                                        .getID_FIXTURE(), "-",
                                                "-");
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
                }

        ));
    }

    // POPULATION RECYCLER
    public void recyclerViewLoadResultado(int division, int torneo, int fecha,
                                          int anio) {
        arrayResultado = controladorAdeful.selectListaResultadoAdeful(division,
                torneo, fecha, anio);
        if (arrayResultado != null) {
            adaptadorResultado = new AdaptadorRecyclerResultado(arrayResultado, getActivity());
            recyclerViewResultado.setAdapter(adaptadorResultado);
            if (arrayResultado.isEmpty()) {
                Toast.makeText(
                        getActivity(),
                        "Selección sin datos",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    public void inicializarControles(String mensaje) {
        recyclerViewLoadResultado(
                divisionSpinner,
                torneoSpinner,
                fechaSpinner, anioSpiner);
        if(isReset)
            dialogoAlerta.alertDialog.dismiss();
        else
            dialogoResultado.alertDialog
                    .dismiss();
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
        isReset = false;
    }

    public void cargarEntidad(int id, String rlocal, String rVisita) {
        URL = null;
        URL = auxiliarGeneral.getURLRESULTADOADEFULAll();

        resultado = new Resultado(id, rlocal, rVisita, usuario, auxiliarGeneral.getFechaOficial());
        envioWebService();
    }

    public void envioWebService() {
        request = new Request();
        request.setMethod("POST");
        request.setParametrosDatos("id_fixture", String.valueOf(resultado.getID_FIXTURE()));
        request.setParametrosDatos("rlocal", resultado.getRESULTADO_LOCAL());
        request.setParametrosDatos("rvisita", resultado.getRESULTADO_VISITA());
        request.setParametrosDatos("usuario_actualizacion", resultado.getUSUARIO_ACTUALIZACION());
        request.setParametrosDatos("fecha_actualizacion", resultado.getFECHA_ACTUALIZACION());
        URL = URL + auxiliarGeneral.getUpdatePHP("Resultado");

        new AsyncTaskGeneric(getActivity(), this, URL, request, "Resultado", resultado, false, "o");
    }

    @Override
    public void onPostExecuteConcluded(boolean result, String mensaje) {
        if (result) {
            inicializarControles(mensaje);
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
        menu.getItem(4).setVisible(false);// guardar
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

        if (id == android.R.id.home) {

            NavUtils.navigateUpFromSameTask(getActivity());

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}