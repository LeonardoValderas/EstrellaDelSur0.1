package com.estrelladelsur.estrelladelsur.miequipo;

import com.estrelladelsur.estrelladelsur.R;

import java.util.ArrayList;

import android.content.Context;
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

import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.ResultadoRecycler;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerResultado;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerFecha;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerTorneo;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoGoleador;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoResultado;


public class ActivityResultado extends AppCompatActivity {

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
    private TextView txtAbTitulo;
    private TextView txtAbSubTitulo;
    private DialogoResultado dialogoResultado;
    private DialogoGoleador dialogoGoleador;
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
    private ArrayList<ResultadoRecycler> arrayResultado;
    private DialogoAlerta dialogoAlerta;
    private ArrayAdapter<String> adaptadorInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        controladorAdeful = new ControladorAdeful(this);
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtAbTitulo = (TextView) toolbar.findViewById(R.id.txtAbTitulo);
        txtAbTitulo.setVisibility(View.GONE);

        txtAbSubTitulo = (TextView) findViewById(R.id.txtAbSubTitulo);
        txtAbSubTitulo.setText("RESULTADO");

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

        // DIVISION
        controladorAdeful.abrirBaseDeDatos();
        divisionArray = controladorAdeful.selectListaDivisionAdeful();
        if (divisionArray != null) {
            controladorAdeful.cerrarBaseDeDatos();
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
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(this, getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
        }
        // TORNEO
        controladorAdeful.abrirBaseDeDatos();
        torneoArray = controladorAdeful.selectListaTorneoAdeful();
        if (torneoArray != null) {
            controladorAdeful.cerrarBaseDeDatos();

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
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(this, getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
        }
        // FECHA
        controladorAdeful.abrirBaseDeDatos();
        fechaArray = controladorAdeful.selectListaFecha();
        if (fechaArray != null) {
            controladorAdeful.cerrarBaseDeDatos();
            // FECHA SPINNER
            adapterFixtureFecha = new AdapterSpinnerFecha(ActivityResultado.this,
                    R.layout.simple_spinner_dropdown_item, fechaArray);
            resultadoFechaSpinner.setAdapter(adapterFixtureFecha);
        } else {
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(this, getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
        }
        // ANIO
        controladorAdeful.abrirBaseDeDatos();
        anioArray = controladorAdeful.selectListaAnio();
        if (anioArray != null) {
            controladorAdeful.cerrarBaseDeDatos();
            // ANIO SPINNER
            adapterFixtureAnio = new AdapterSpinnerAnio(ActivityResultado.this,
                    R.layout.simple_spinner_dropdown_item, anioArray);
            resultadoAnioSpinner.setAdapter(adapterFixtureAnio);
        } else {
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(this, getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
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
                        if (arrayResultado.get(position).getRESULTADO_LOCAL() != "-"
                                && arrayResultado.get(position)
                                .getRESULTADO_VISITA() != "-") {
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
                                        // TODO Auto-generated method stub
//										dialogoGoleador = new DialogoGoleador(
//												ActivityResultado.this,
//												"GOLEADORES", "1", jugadores);
//										dialogoGoleador.btnAceptar
//												.setOnClickListener(new View.OnClickListener() {
//
//													@Override
//													public void onClick(View v) {
//														// TODO Auto-generated
//														// method stub
//														dialogoGoleador.alertDialog
//																.dismiss();
//													}
//												});
                                    }
                                });

                        dialogoResultado.btnAceptar
                                .setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub

                                        if (!dialogoResultado.resultadoLocal
                                                .getText().toString()
                                                .equals("")
                                                && !dialogoResultado.resultadoVisita
                                                .getText().toString()
                                                .equals("")) {
                                            String usuario = "Administrador";
                                            String fechaCreacion = controladorAdeful.getFechaOficial();
                                            String fechaActualizacion = fechaCreacion;
                                            controladorAdeful
                                                    .abrirBaseDeDatos();
                                            if(controladorAdeful
                                                    .actualizarResultadoAdeful(
                                                            arrayResultado
                                                                    .get(posicionRecycler)
                                                                    .getID_FIXTURE(),
                                                            dialogoResultado.resultadoLocal
                                                                    .getText()
                                                                    .toString(),
                                                            dialogoResultado.resultadoVisita
                                                                    .getText()
                                                                    .toString(), usuario, fechaActualizacion)) {
                                                controladorAdeful
                                                        .cerrarBaseDeDatos();
                                                recyclerViewLoadResultado(
                                                        divisionSpinner,
                                                        torneoSpinner,
                                                        fechaSpinner, anioSpiner);
                                                dialogoResultado.alertDialog
                                                        .dismiss();
                                            }else{
                                                controladorAdeful.cerrarBaseDeDatos();
                                                Toast.makeText(ActivityResultado.this,getResources().getString(R.string.error_data_base),
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        } else {

                                            dialogoResultado.resultadoTextErrorVacio
                                                    .setVisibility(View.VISIBLE);
                                            dialogoResultado.resultadoTextErrorVacio
                                                    .setText("Ingrese Ambos Resultados.");
                                        }
                                    }
                                });

                        dialogoResultado.btnCancelar
                                .setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub
                                        dialogoResultado.alertDialog.dismiss();
                                    }
                                });
                    }

                    @Override
                    public void onLongClick(View view, final int position) {
                        // TODO Auto-generated method stub

                        dialogoAlerta = new DialogoAlerta(ActivityResultado.this,
                                "ALERTA", "Desea Resetear el Resultado?", null,
                                null);
                        dialogoAlerta.btnAceptar.setText("Aceptar");
                        dialogoAlerta.btnCancelar.setText("Cancelar");

                        dialogoAlerta.btnAceptar
                                .setOnClickListener(new View.OnClickListener() {

                                    // @SuppressLint("NewApi")
                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub
                                        String usuario = "Administrador";
                                        String fechaCreacion = controladorAdeful.getFechaOficial();
                                        String fechaActualizacion = fechaCreacion;

                                        controladorAdeful.abrirBaseDeDatos();
                                        if (controladorAdeful
                                                .actualizarResultadoAdeful(
                                                        arrayResultado
                                                                .get(position)
                                                                .getID_FIXTURE(),
                                                        null, null, usuario, fechaActualizacion)){
                                        controladorAdeful.cerrarBaseDeDatos();

                                            recyclerViewLoadResultado(
                                                    divisionSpinner, torneoSpinner,
                                                    fechaSpinner, anioSpiner);
                                            dialogoAlerta.alertDialog.dismiss();
                                        }else{
                                            controladorAdeful.cerrarBaseDeDatos();
                                            Toast.makeText(ActivityResultado.this, getResources().getString(R.string.error_data_base),
                                                    Toast.LENGTH_SHORT).show();
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
                }));
    }

    // POPULATION RECYCLER
    public void recyclerViewLoadResultado(int division, int torneo, int fecha,
                                          int anio) {

        controladorAdeful.abrirBaseDeDatos();
        arrayResultado = controladorAdeful.selectListaResultadoAdeful(division,
                torneo, fecha, anio);
        if (arrayResultado != null) {
            controladorAdeful.cerrarBaseDeDatos();

            adaptadorResultado = new AdaptadorRecyclerResultado(arrayResultado);
            adaptadorResultado.notifyDataSetChanged();
            recyclerViewResultado.setAdapter(adaptadorResultado);
        } else {
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(ActivityResultado.this,getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
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

        menu.getItem(7).setVisible(false);
        menu.getItem(8).setVisible(false);
        menu.getItem(9).setVisible(false);
        menu.getItem(10).setVisible(false); // eliminar
        menu.getItem(11).setVisible(false); // consultar
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // if (drawerToggle.onOptionsItemSelected(item)) {
        // return true;
        // }

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
