package com.estrelladelsur.estrelladelsur.institucion.usuario;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.usuario.AdaptadorRecyclerDireccionUsuario;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.usuario.adeful.ControladorUsuarioAdeful;
import com.estrelladelsur.estrelladelsur.database.usuario.general.ControladorUsuarioGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Direccion;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericIndividual;
import com.estrelladelsur.estrelladelsur.webservice.Request;
import com.estrelladelsur.estrelladelsur.webservice.Variable;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class DireccionUsuario extends AppCompatActivity {

    private Toolbar toolbar;
    private AuxiliarGeneral auxiliarGeneral;
    private ArrayList<Direccion> direccionArray;
    private ControladorUsuarioGeneral controladorUsuario;
    private AdaptadorRecyclerDireccionUsuario adaptadorRecyclerDireccion;
    private RecyclerView recycleViewUsuarioGeneral;
    private TextView txtToolBarTitulo;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyAsyncTaskListener listener;
    private Request request;
    private AdRequest adRequest;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_general);

        auxiliarGeneral = new AuxiliarGeneral(DireccionUsuario.this);
        controladorUsuario = new ControladorUsuarioGeneral(DireccionUsuario.this);
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recycleViewUsuarioGeneral = (RecyclerView) findViewById(R.id.recycleViewUsuarioGeneral);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        txtToolBarTitulo = (TextView) findViewById(R.id.txtToolBarTitulo);
        txtToolBarTitulo.setText("DIRECCION TECNICA");
        mAdView = (AdView) findViewById(R.id.adView);

        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        auxiliarGeneral = new AuxiliarGeneral(DireccionUsuario.this);
        controladorUsuario = new ControladorUsuarioGeneral(DireccionUsuario.this);
        init();
    }

    public void init() {
        BannerAd();
        initRecycler();
        listener = new MyAsyncTaskListener() {
            @Override
            public void onPostExecuteConcluded(boolean result, String mensaje) {
                if (result) {
                    recyclerViewLoadComision();
                } else {
                    auxiliarGeneral.errorWebService(DireccionUsuario.this, mensaje);
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        };
        recyclerViewLoadComision();

        recycleViewUsuarioGeneral.addOnItemTouchListener(new
                RecyclerTouchListener(DireccionUsuario.this,
                recycleViewUsuarioGeneral, new ClickListener() {
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
                String fecha = controladorUsuario.selectTabla(Variable.TABLA_DIRECCION);
                if (fecha != null) {
                    request = new Request();
                    request.setMethod("POST");
                    request.setParametrosDatos("fecha_tabla", fecha);
                    request.setParametrosDatos("tabla", Variable.TABLA_DIRECCION);
                    request.setParametrosDatos("liga", "GENERAL");

                    new AsyncTaskGenericIndividual(DireccionUsuario.this, listener, auxiliarGeneral.getURLSINCRONIZARINDIVIDUAL(), request, Variable.DIRECCION);
                }
            }
        });
    }
    public void initRecycler(){
        recycleViewUsuarioGeneral.setLayoutManager(new LinearLayoutManager(
                DireccionUsuario.this, LinearLayoutManager.VERTICAL, false));
        recycleViewUsuarioGeneral.addItemDecoration(new DividerItemDecoration(
                DireccionUsuario.this, DividerItemDecoration.VERTICAL_LIST));
        recycleViewUsuarioGeneral.setItemAnimator(new DefaultItemAnimator());
    }
    public void recyclerViewLoadComision() {
        direccionArray = controladorUsuario.selectListaDireccionUsuario();
        if(direccionArray != null) {
            adaptadorRecyclerDireccion = new AdaptadorRecyclerDireccionUsuario(direccionArray,DireccionUsuario.this);
            recycleViewUsuarioGeneral.setAdapter(adaptadorRecyclerDireccion);
        }else{
            auxiliarGeneral.errorDataBase(DireccionUsuario.this);
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
