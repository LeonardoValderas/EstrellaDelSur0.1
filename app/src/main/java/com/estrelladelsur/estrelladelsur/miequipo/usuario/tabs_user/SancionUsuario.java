package com.estrelladelsur.estrelladelsur.miequipo.usuario.tabs_user;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.usuario.AdaptadorSancionUsuario;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.usuario.adeful.ControladorUsuarioAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;

import java.util.ArrayList;

public class SancionUsuario extends AppCompatActivity {

    private Toolbar toolbar;
    private AuxiliarGeneral auxiliarGeneral;
    private ArrayList<Sancion> sancionArray;
    private ControladorUsuarioAdeful controladorUsuarioAdeful;
    private AdapterSpinnerDivision adapterSpinnerDivision;
    private ArrayAdapter<String> adaptadorInicial;
    private Division division;
    private int divisionSpinner;
    private AdaptadorSancionUsuario adaptadorSancionUsuario;
    private TextView txtToolBarTitulo;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Spinner jugadoresDivisionSpinner;
    private RecyclerView recyclerViewSancion;
    private FloatingActionButton botonFloating;
    private ArrayList<Division> divisionArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_general_float_spinner);

        auxiliarGeneral = new AuxiliarGeneral(SancionUsuario.this);
        controladorUsuarioAdeful = new ControladorUsuarioAdeful(SancionUsuario.this);
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtToolBarTitulo = (TextView) findViewById(R.id.txtToolBarTitulo);
        txtToolBarTitulo.setText("SANCION");
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        auxiliarGeneral = new AuxiliarGeneral(SancionUsuario.this);
        controladorUsuarioAdeful = new ControladorUsuarioAdeful(SancionUsuario.this);
        init();
    }

    public void init() {

        // DIVISION
        jugadoresDivisionSpinner = (Spinner) findViewById(R.id.jugadoresDivisionSpinner);
        // RECYCLER
        recyclerViewSancion = (RecyclerView) findViewById(R.id.recycleViewGeneral);
        // BUTTON
        botonFloating = (FloatingActionButton) findViewById(R.id.botonFloating);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        initRecycler();

        divisionArray = controladorUsuarioAdeful.selectListaDivisionUsuarioAdeful();
        if (divisionArray != null) {
            // DIVSION SPINNER
            if (divisionArray.size() != 0) {
                adapterSpinnerDivision = new AdapterSpinnerDivision(SancionUsuario.this,
                        R.layout.simple_spinner_dropdown_item, divisionArray);
                jugadoresDivisionSpinner.setAdapter(adapterSpinnerDivision);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<>(SancionUsuario.this,
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.vacioSpinnerDivision));
                jugadoresDivisionSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(SancionUsuario.this);
        }

        botonFloating.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (jugadoresDivisionSpinner.getSelectedItem().toString().equals(getResources().
                        getString(R.string.vacioSpinnerDivision))) {
                    Toast.makeText(SancionUsuario.this, "No hay datos cargados.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    division = (Division) jugadoresDivisionSpinner.getSelectedItem();
                    divisionSpinner = division.getID_DIVISION();
                    recyclerViewLoadSancion(divisionSpinner);
                }
            }
        });

        recyclerViewSancion.addOnItemTouchListener(new
                RecyclerTouchListener(SancionUsuario.this,
                recyclerViewSancion, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }
            @Override
            public void onLongClick(View view, final int position) {
            }
        }));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                recyclerViewLoadSancion(divisionSpinner);
            }
        });
    }

    public void initRecycler(){
        recyclerViewSancion.setLayoutManager(new LinearLayoutManager(
                SancionUsuario.this, LinearLayoutManager.VERTICAL, false));
        recyclerViewSancion.addItemDecoration(new DividerItemDecoration(
                SancionUsuario.this, DividerItemDecoration.VERTICAL_LIST));
        recyclerViewSancion.setItemAnimator(new DefaultItemAnimator());
    }

    public void recyclerViewLoadSancion(int division) {
        sancionArray = controladorUsuarioAdeful.selectListaSancionUsuarioAdeful(division);
        if (sancionArray != null) {
            adaptadorSancionUsuario = new AdaptadorSancionUsuario(sancionArray, SancionUsuario.this);
            recyclerViewSancion.setAdapter(adaptadorSancionUsuario);
            if (sancionArray.isEmpty())
                Toast.makeText(
                        SancionUsuario.this,
                        "Selección sin datos",
                        Toast.LENGTH_SHORT).show();
        } else {
            auxiliarGeneral.errorDataBase(SancionUsuario.this);
        }
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
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
        }
    }
}
