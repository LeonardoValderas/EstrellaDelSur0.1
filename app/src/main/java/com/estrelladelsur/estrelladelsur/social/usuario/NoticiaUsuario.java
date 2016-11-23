package com.estrelladelsur.estrelladelsur.social.usuario;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.webkit.URLUtil;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.usuario.AdaptadorRecyclerNoticiaUsuario;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.usuario.ControladorUsuarioGeneral;
import com.estrelladelsur.estrelladelsur.dialogo.usuario.DialogoArticulo;
import com.estrelladelsur.estrelladelsur.entidad.Noticia;
import com.estrelladelsur.estrelladelsur.navegador.usuario.NavigationUsuario;

import java.util.ArrayList;

public class NoticiaUsuario extends AppCompatActivity {

    private Toolbar toolbar;
    private Typeface titulos;
    private AuxiliarGeneral auxiliarGeneral;
    private ArrayList<Noticia> noticiaArray;
    private ControladorUsuarioGeneral controladorUsuario;
    private AdaptadorRecyclerNoticiaUsuario adaptadorRecyclerNoticia;
    private RecyclerView recycleViewUsuarioGeneral;
    private DialogoArticulo dialogoNoticia;
    private TextView txtToolBarTitulo;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_general);

        auxiliarGeneral = new AuxiliarGeneral(NoticiaUsuario.this);
        controladorUsuario = new ControladorUsuarioGeneral(NoticiaUsuario.this);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtToolBarTitulo = (TextView) findViewById(R.id.txtToolBarTitulo);
        txtToolBarTitulo.setText("NOTICIA");

        init();
    }

    public void init() {
        recycleViewUsuarioGeneral = (RecyclerView) findViewById(R.id.recycleViewUsuarioGeneral);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        initRecycler();
        recyclerViewLoadNoticia();

        recycleViewUsuarioGeneral.addOnItemTouchListener(new
                RecyclerTouchListener(NoticiaUsuario.this,
                recycleViewUsuarioGeneral, new ClickListener() {

            @Override
            public void onClick(View view, final int position) {
                dialogoNoticia = new DialogoArticulo(NoticiaUsuario.this, noticiaArray.get(position).getTITULO(),
                        noticiaArray.get(position).getDESCRIPCION());

                final String link = noticiaArray.get(position).getLINK();

                if (link != null) {
                    if (URLUtil.isValidUrl(link)) {
                        dialogoNoticia.linearLink.setVisibility(View.VISIBLE);
                        dialogoNoticia.btnLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openWebURL(link);
                            }
                        });
                    }
                }
                dialogoNoticia.btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogoNoticia.alertDialog.dismiss();
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
                recyclerViewLoadNoticia();
            }
        });
    }

    public void openWebURL(String inURL) {
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(inURL));
        startActivity(browse);
    }

    public void initRecycler() {
        recycleViewUsuarioGeneral.setLayoutManager(new LinearLayoutManager(
                NoticiaUsuario.this, LinearLayoutManager.VERTICAL, false));
        recycleViewUsuarioGeneral.addItemDecoration(new DividerItemDecoration(
                NoticiaUsuario.this, DividerItemDecoration.VERTICAL_LIST));
        recycleViewUsuarioGeneral.setItemAnimator(new DefaultItemAnimator());
    }

    public void recyclerViewLoadNoticia() {
        noticiaArray = controladorUsuario.selectListaNoticiaUsuario();
        if (noticiaArray != null) {
            adaptadorRecyclerNoticia = new AdaptadorRecyclerNoticiaUsuario(noticiaArray, NoticiaUsuario.this);
            recycleViewUsuarioGeneral.setAdapter(adaptadorRecyclerNoticia);
        } else {
            auxiliarGeneral.errorDataBase(NoticiaUsuario.this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_administrador_general, menu);
        menu.getItem(0).setVisible(false);//usuario
        menu.getItem(1).setVisible(false);// posicion
        menu.getItem(2).setVisible(false);// cargo
        menu.getItem(3).setVisible(false);//cerrar
        menu.getItem(4).setVisible(false);// guardar
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            volver();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        volver();
        super.onBackPressed();
    }

    public void volver() {
        Intent i = new Intent(NoticiaUsuario.this, NavigationUsuario.class);
        startActivity(i);
    }
}
