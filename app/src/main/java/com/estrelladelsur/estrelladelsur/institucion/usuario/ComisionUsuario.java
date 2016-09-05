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
import com.estrelladelsur.estrelladelsur.adaptador.usuario.AdaptadorRecyclerComisionUsuario;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.usuario.ControladorUsuarioAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import java.util.ArrayList;

public class ComisionUsuario extends AppCompatActivity {

    private Toolbar toolbar;
    private AuxiliarGeneral auxiliarGeneral;
    private ArrayList<Comision> comisionArray;
    private ControladorUsuarioAdeful controladorUsuario;
    private AdaptadorRecyclerComisionUsuario adaptadorRecyclerComisionDireccion;
    private RecyclerView recycleViewUsuarioGeneral;
    private TextView txtToolBarTitulo;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_general);

        auxiliarGeneral = new AuxiliarGeneral(ComisionUsuario.this);
        controladorUsuario = new ControladorUsuarioAdeful(ComisionUsuario.this);
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtToolBarTitulo = (TextView) findViewById(R.id.txtToolBarTitulo);
        txtToolBarTitulo.setText("COMISIÓN DIRECTIVA");
        init();
    }

    public void init() {
        recycleViewUsuarioGeneral = (RecyclerView) findViewById(R.id.recycleViewUsuarioGeneral);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        initRecycler();
        recyclerViewLoadComision();

        recycleViewUsuarioGeneral.addOnItemTouchListener(new
                RecyclerTouchListener(ComisionUsuario.this,
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
                //Refreshing data on server
                // new DownloadFilesTask().execute(feedUrl);
                recyclerViewLoadComision();
            }
        });
    }

    public void initRecycler(){
        recycleViewUsuarioGeneral.setLayoutManager(new LinearLayoutManager(
                ComisionUsuario.this, LinearLayoutManager.VERTICAL, false));
        recycleViewUsuarioGeneral.addItemDecoration(new DividerItemDecoration(
                ComisionUsuario.this, DividerItemDecoration.VERTICAL_LIST));
        recycleViewUsuarioGeneral.setItemAnimator(new DefaultItemAnimator());
    }

    public void recyclerViewLoadComision() {
        comisionArray = controladorUsuario.selectListaComisionUsuario();
        if(comisionArray != null) {
            adaptadorRecyclerComisionDireccion = new AdaptadorRecyclerComisionUsuario(comisionArray,ComisionUsuario.this);
            recycleViewUsuarioGeneral.setAdapter(adaptadorRecyclerComisionDireccion);
        }else{
            auxiliarGeneral.errorDataBase(ComisionUsuario.this);
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
