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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.usuario.AdaptadorRecyclerJugadorUsuario;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.usuario.adeful.ControladorUsuarioAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class JugadorUsuario extends AppCompatActivity {

    private Toolbar toolbar;
    private AuxiliarGeneral auxiliarGeneral;
    private ArrayList<Jugador> jugadorArray;
    private ControladorUsuarioAdeful controladorUsuarioAdeful;
    private AdapterSpinnerDivision adapterSpinnerDivision;
    private ArrayAdapter<String> adaptadorInicial;
    private Division division;
    private int divisionSpinner;
    private AdaptadorRecyclerJugadorUsuario adaptadorRecyclerJugadorUsuario;
    private TextView txtToolBarTitulo;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Spinner jugadoresDivisionSpinner;
    private RecyclerView recyclerViewJugador;
   //private FloatingActionButton botonFloating;
    private ArrayList<Division> divisionArray;
    private AdRequest adRequest;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_general_float_spinner);

        auxiliarGeneral = new AuxiliarGeneral(JugadorUsuario.this);
        controladorUsuarioAdeful = new ControladorUsuarioAdeful(JugadorUsuario.this);
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAdView = (AdView) findViewById(R.id.adView);
        txtToolBarTitulo = (TextView) findViewById(R.id.txtToolBarTitulo);
        txtToolBarTitulo.setText("JUGADOR");
        // DIVISION
        jugadoresDivisionSpinner = (Spinner) findViewById(R.id.jugadoresDivisionSpinner);
        // RECYCLER
        recyclerViewJugador = (RecyclerView) findViewById(R.id.recycleViewGeneral);
        // BUTTON
//        botonFloating = (FloatingActionButton) findViewById(R.id.botonFloating);
//
//        botonFloating.setVisibility(View.GONE);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        auxiliarGeneral = new AuxiliarGeneral(JugadorUsuario.this);
        controladorUsuarioAdeful = new ControladorUsuarioAdeful(JugadorUsuario.this);
        init();
    }

    public void init() {
        BannerAd();
            initRecycler();

        divisionArray = controladorUsuarioAdeful.selectListaDivisionUsuarioAdeful();
        if (divisionArray != null) {
            // DIVSION SPINNER
            if (divisionArray.size() != 0) {
                adapterSpinnerDivision = new AdapterSpinnerDivision(JugadorUsuario.this,
                        R.layout.simple_spinner_dropdown_item, divisionArray);
                jugadoresDivisionSpinner.setAdapter(adapterSpinnerDivision);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<>(JugadorUsuario.this,
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.vacioSpinnerDivision));
                jugadoresDivisionSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(JugadorUsuario.this);
        }

        jugadoresDivisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (jugadoresDivisionSpinner.getSelectedItem().toString().equals(getResources().
                        getString(R.string.vacioSpinnerDivision))) {
                    Toast.makeText(JugadorUsuario.this, "No hay datos cargados.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    division = (Division) jugadoresDivisionSpinner.getSelectedItem();
                    divisionSpinner = division.getID_DIVISION();
                    recyclerViewLoadJugador(divisionSpinner);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



//        botonFloating.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (jugadoresDivisionSpinner.getSelectedItem().toString().equals(getResources().
//                        getString(R.string.vacioSpinnerDivision))) {
//                    Toast.makeText(JugadorUsuario.this, "No hay datos cargados.",
//                            Toast.LENGTH_SHORT).show();
//                } else {
//                    division = (Division) jugadoresDivisionSpinner.getSelectedItem();
//                    divisionSpinner = division.getID_DIVISION();
//                    recyclerViewLoadJugador(divisionSpinner);
//                }
//            }
//        });

        recyclerViewJugador.addOnItemTouchListener(new
                RecyclerTouchListener(JugadorUsuario.this,
                recyclerViewJugador, new ClickListener() {
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

                recyclerViewLoadJugador(divisionSpinner);
            }
        });
    }

    public void initRecycler() {
        recyclerViewJugador.setLayoutManager(new LinearLayoutManager(
                JugadorUsuario.this, LinearLayoutManager.VERTICAL, false));
        recyclerViewJugador.addItemDecoration(new DividerItemDecoration(
                JugadorUsuario.this, DividerItemDecoration.VERTICAL_LIST));
        recyclerViewJugador.setItemAnimator(new DefaultItemAnimator());
    }

    public void recyclerViewLoadJugador(int division) {
        jugadorArray = controladorUsuarioAdeful.selectListaJugadorUsuarioAdeful(division);
        if (jugadorArray != null) {
            adaptadorRecyclerJugadorUsuario = new AdaptadorRecyclerJugadorUsuario(jugadorArray, JugadorUsuario.this);
            recyclerViewJugador.setAdapter(adaptadorRecyclerJugadorUsuario);
            if (jugadorArray.isEmpty())
                Toast.makeText(
                        JugadorUsuario.this,
                        "Selección sin datos",
                        Toast.LENGTH_SHORT).show();
        } else {
            auxiliarGeneral.errorDataBase(JugadorUsuario.this);
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

    public void BannerAd() {
        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("B52960D9E6A2A5833E82FEA8ACD4B80C")
                .build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }
}
