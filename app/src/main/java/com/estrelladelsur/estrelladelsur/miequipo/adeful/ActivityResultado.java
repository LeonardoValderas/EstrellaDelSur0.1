package com.estrelladelsur.estrelladelsur.miequipo.adeful;

import com.estrelladelsur.estrelladelsur.R;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.Resultado;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerResultado;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerFecha;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerTorneo;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoResultado;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGeneric;
import com.estrelladelsur.estrelladelsur.webservice.Request;


public class ActivityResultado extends AppCompatActivity implements MyAsyncTaskListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);


        auxiliarGeneral = new AuxiliarGeneral(ActivityResultado.this);
        titulos = auxiliarGeneral.tituloFont(ActivityResultado.this);

        controladorAdeful = new ControladorAdeful(this);
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTitulo = (TextView) toolbar.findViewById(R.id.txtToolBarTitulo);
        txtTitulo.setText("RESULTADO");
        txtTitulo.setTypeface(titulos);


        // SPINNER DIVISION
        resultadoDivisionSpinner = (Spinner) findViewById(R.id.resultadoDivisionSpinner);
        // SPINNER TORNEO
        resultadoTorneoSpinner = (Spinner) findViewById(R.id.resultadoTorneoSpinner);
        // SPINNER FECHA
        resultadoFechaSpinner = (Spinner) findViewById(R.id.resultadoFechaSpinner);
        // SPINNER ANIO
        resultadoAnioSpinner = (Spinner) findViewById(R.id.resultadoAnioSpinner);

        // RECLYCLER

        recyclerViewResultado = (RecyclerView) findViewById(R.id.recycleViewResultado);
        recyclerViewResultado.setLayoutManager(new LinearLayoutManager(
                ActivityResultado.this, LinearLayoutManager.VERTICAL, false));
        recyclerViewResultado.addItemDecoration(new DividerItemDecoration(
                ActivityResultado.this, DividerItemDecoration.VERTICAL_LIST));
        recyclerViewResultado.setItemAnimator(new DefaultItemAnimator());

        // BUTTONFLOATING
        botonFloating = (FloatingActionButton) findViewById(R.id.botonFloating);

        init();

    }

    private void init() {
        auxiliarGeneral = new AuxiliarGeneral(ActivityResultado.this);
        usuario = auxiliarGeneral.getUsuarioPreferences(ActivityResultado.this);
        // DIVISION
        divisionArray = controladorAdeful.selectListaDivisionAdeful();
        if (divisionArray != null) {
            if (divisionArray.size() != 0) {
                // DIVSION SPINNER
                adapterFixtureDivision = new AdapterSpinnerDivision(
                        ActivityResultado.this, R.layout.simple_spinner_dropdown_item,
                        divisionArray);
                resultadoDivisionSpinner.setAdapter(adapterFixtureDivision);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(ActivityResultado.this,
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerDivision));
                resultadoDivisionSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(ActivityResultado.this);
        }
        // TORNEO
        torneoArray = controladorAdeful.selectListaTorneoAdeful();
        if (torneoArray != null) {
            if (torneoArray.size() != 0) {
                // TORNEO SPINNER
                adapterFixtureTorneo = new AdapterSpinnerTorneo(ActivityResultado.this,
                        R.layout.simple_spinner_dropdown_item, torneoArray);
                resultadoTorneoSpinner.setAdapter(adapterFixtureTorneo);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(ActivityResultado.this,
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerTorneo));
                resultadoTorneoSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(ActivityResultado.this);
        }
        // FECHA
        fechaArray = controladorAdeful.selectListaFecha();
        if (fechaArray != null) {
            // FECHA SPINNER
            adapterFixtureFecha = new AdapterSpinnerFecha(ActivityResultado.this,
                    R.layout.simple_spinner_dropdown_item, fechaArray);
            resultadoFechaSpinner.setAdapter(adapterFixtureFecha);
        } else {
            auxiliarGeneral.errorDataBase(ActivityResultado.this);
        }
        // ANIO
        anioArray = controladorAdeful.selectListaAnio();
        if (anioArray != null) {
            // ANIO SPINNER
            adapterFixtureAnio = new AdapterSpinnerAnio(ActivityResultado.this,
                    R.layout.simple_spinner_dropdown_item, anioArray);
            resultadoAnioSpinner.setAdapter(adapterFixtureAnio);
        } else {
            auxiliarGeneral.errorDataBase(ActivityResultado.this);
        }
        botonFloating.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (resultadoDivisionSpinner.getSelectedItem().toString().equals(getResources().
                        getString(R.string.ceroSpinnerDivision))) {
                    Toast.makeText(ActivityResultado.this, "Debe agregar un division (Liga).",
                            Toast.LENGTH_SHORT).show();
                } else if (resultadoTorneoSpinner.getSelectedItem().toString().equals(getResources().
                        getString(R.string.ceroSpinnerTorneo))) {
                    Toast.makeText(ActivityResultado.this, "Debe agregar un torneo (Liga).",
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
                ActivityResultado.this, recyclerViewResultado,
                new ClickListener() {

                    @Override
                    public void onClick(View view, int position) {
                        // TODO Auto-generated method stub
                        posicionRecycler = position;
                        if (!arrayResultado.get(position).getRESULTADO_LOCAL().equals("-")
                                && !arrayResultado.get(position)
                                .getRESULTADO_VISITA().equals("-")) {
                            dialogoResultado = new DialogoResultado(
                                    ActivityResultado.this, "RESULTADO",
                                    arrayResultado.get(position)
                                            .getEQUIPO_LOCAL(), arrayResultado
                                    .get(position).getEQUIPO_VISITA(),
                                    arrayResultado.get(position)
                                            .getRESULTADO_LOCAL(),
                                    arrayResultado.get(position)
                                            .getRESULTADO_VISITA());
                        } else {
                            dialogoResultado = new DialogoResultado(
                                    ActivityResultado.this, "RESULTADO",
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

                        dialogoAlerta = new DialogoAlerta(ActivityResultado.this,
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
            adaptadorResultado = new AdaptadorRecyclerResultado(arrayResultado, ActivityResultado.this);
            recyclerViewResultado.setAdapter(adaptadorResultado);
            if (arrayResultado.isEmpty()) {
                Toast.makeText(
                        ActivityResultado.this,
                        "Selección sin datos",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            auxiliarGeneral.errorDataBase(ActivityResultado.this);
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
        Toast.makeText(ActivityResultado.this, mensaje,
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

        new AsyncTaskGeneric(ActivityResultado.this, this, URL, request, "Resultado", resultado, false, "o");
    }

    @Override
    public void onPostExecuteConcluded(boolean result, String mensaje) {
        if (result) {
            inicializarControles(mensaje);
        } else {
            auxiliarGeneral.errorWebService(ActivityResultado.this, mensaje);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_administrador_general, menu);


        menu.getItem(1).setVisible(false);//permiso
        menu.getItem(2).setVisible(false);//lifuba
        menu.getItem(3).setVisible(false);// adeful
        menu.getItem(4).setVisible(false);// puesto
        menu.getItem(5).setVisible(false);// posicion
        menu.getItem(6).setVisible(false);// cargo
        // menu.getItem(7).setVisible(false);//cerrar
        menu.getItem(8).setVisible(false);// guardar
        menu.getItem(9).setVisible(false);// Subir
        menu.getItem(10).setVisible(false); // eliminar
        menu.getItem(11).setVisible(false); // consultar
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        // noinspection SimplifiableIfStatement
        if (id == R.id.action_usuario) {

//			Intent usuario = new Intent(ActivityResultado.this,
//					NavigationDrawerUsuario.class);
//			startActivity(usuario);

            return true;
        }

        if (id == R.id.action_permisos) {
            return true;
        }

        if (id == R.id.action_guardar) {

            return true;
        }

        if (id == R.id.action_subir) {

            return true;
        }

        if (id == R.id.action_eliminar) {

            return true;
        }
        if (id == R.id.action_adeful) {

            return true;
        }

        if (id == R.id.action_lifuba) {

            return true;
        }

        if (id == R.id.action_puesto) {

            return true;
        }

        if (id == R.id.action_cargo) {

            return true;
        }

        if (id == android.R.id.home) {

            NavUtils.navigateUpFromSameTask(this);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
