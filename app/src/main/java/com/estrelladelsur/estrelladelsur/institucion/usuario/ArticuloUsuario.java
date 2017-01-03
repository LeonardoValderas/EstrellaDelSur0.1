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
import com.estrelladelsur.estrelladelsur.adaptador.usuario.AdaptadorRecyclerArticuloUsuario;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.usuario.general.ControladorUsuarioGeneral;
import com.estrelladelsur.estrelladelsur.dialogo.usuario.DialogoArticulo;
import com.estrelladelsur.estrelladelsur.entidad.Articulo;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericIndividual;
import com.estrelladelsur.estrelladelsur.webservice.Request;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class ArticuloUsuario extends AppCompatActivity {

    private Toolbar toolbar;
    private AuxiliarGeneral auxiliarGeneral;
    private ArrayList<Articulo> articuloArray;
    private ControladorUsuarioGeneral controladorUsuario;
    private AdaptadorRecyclerArticuloUsuario adaptadorRecyclerTitulo;
    private RecyclerView recycleViewUsuarioGeneral;
    private DialogoArticulo dialogoArticulo;
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

        auxiliarGeneral = new AuxiliarGeneral(ArticuloUsuario.this);
        controladorUsuario = new ControladorUsuarioGeneral(ArticuloUsuario.this);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txtToolBarTitulo = (TextView) findViewById(R.id.txtToolBarTitulo);
        txtToolBarTitulo.setText("ARTICULO");
        recycleViewUsuarioGeneral = (RecyclerView) findViewById(R.id.recycleViewUsuarioGeneral);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mAdView = (AdView) findViewById(R.id.adView);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        controladorUsuario = new ControladorUsuarioGeneral(ArticuloUsuario.this);
        initRecycler();
        recyclerViewLoadArticulo();

    }

    public void init() {
        BannerAd();
        initRecycler();
        listener = new MyAsyncTaskListener() {
            @Override
            public void onPostExecuteConcluded(boolean result, String mensaje) {
                if (result) {
                    recyclerViewLoadArticulo();
                } else {
                    auxiliarGeneral.errorWebService(ArticuloUsuario.this, mensaje);
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        };
        recyclerViewLoadArticulo();

        recycleViewUsuarioGeneral.addOnItemTouchListener(new
                RecyclerTouchListener(ArticuloUsuario.this,
                recycleViewUsuarioGeneral, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                dialogoArticulo = new DialogoArticulo(ArticuloUsuario.this, articuloArray.get(position).getTITULO(),
                        articuloArray.get(position).getARTICULO());

                dialogoArticulo.btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogoArticulo.alertDialog.dismiss();
                    }
                });
            }

            @Override
            public void onLongClick(View view, final int position) {
            }
        }));


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String fecha = controladorUsuario.selectTabla(AsyncTaskGenericIndividual.TABLA_ARTICULO);
                if (fecha != null) {
                    request = new Request();
                    request.setMethod("POST");
                    request.setParametrosDatos("fecha_tabla", fecha);
                    request.setParametrosDatos("tabla", AsyncTaskGenericIndividual.TABLA_ARTICULO);
                    request.setParametrosDatos("liga", "GENERAL");

                    new AsyncTaskGenericIndividual(ArticuloUsuario.this, listener, auxiliarGeneral.getURLSINCRONIZARINDIVIDUAL(), request, AsyncTaskGenericIndividual.ARTICULO);
                }
            }
        });
    }

    public void initRecycler() {
        recycleViewUsuarioGeneral.setLayoutManager(new LinearLayoutManager(
                ArticuloUsuario.this, LinearLayoutManager.VERTICAL, false));
        recycleViewUsuarioGeneral.addItemDecoration(new DividerItemDecoration(
                ArticuloUsuario.this, DividerItemDecoration.VERTICAL_LIST));
        recycleViewUsuarioGeneral.setItemAnimator(new DefaultItemAnimator());
    }

    public void recyclerViewLoadArticulo() {
        articuloArray = controladorUsuario.selectListaArticuloAdeful();
        if (articuloArray != null) {
            adaptadorRecyclerTitulo = new AdaptadorRecyclerArticuloUsuario(articuloArray, ArticuloUsuario.this);
            recycleViewUsuarioGeneral.setAdapter(adaptadorRecyclerTitulo);
        } else {
            auxiliarGeneral.errorDataBase(ArticuloUsuario.this);
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
