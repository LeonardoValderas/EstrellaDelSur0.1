package com.estrelladelsur.estrelladelsur.social.administrador;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerNoticia;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Noticia;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.social.administrador.tabs.TabsNoticia;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericAdeful;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import java.util.ArrayList;


public class FragmentEditarNoticia extends Fragment implements MyAsyncTaskListener {

    private int CheckedPositionFragment;
    private RecyclerView recyclerArticulo;
    private ControladorGeneral controladorGeneral;
    private ArrayList<Noticia> noticiaArray;
    private AdaptadorRecyclerNoticia adaptadorRecyclerNoticia;
    private DialogoAlerta dialogoAlerta;
    private AuxiliarGeneral auxiliarGeneral;
    private int posicion = 0;
    private String URL = null;
    private Request request = new Request();

    public static FragmentEditarNoticia newInstance() {
        FragmentEditarNoticia fragment = new FragmentEditarNoticia();
        return fragment;
    }

    public FragmentEditarNoticia() {
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        controladorGeneral = new ControladorGeneral(getActivity());
        if (state != null) {
            CheckedPositionFragment = state.getInt("curChoice", 0);
        } else {
            init();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_editar_recyclerview, container, false);

        // RECYCLER NOTIFICACION
        recyclerArticulo = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
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
        controladorGeneral = new ControladorGeneral(getActivity());
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        recyclerViewLoadNoticia();
    }

    private void init() {
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        recyclerArticulo.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerArticulo.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerArticulo.setItemAnimator(new DefaultItemAnimator());

        recyclerViewLoadNoticia();

        recyclerArticulo.addOnItemTouchListener(new
                RecyclerTouchListener(getActivity(),
                recyclerArticulo, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                // TODO Auto-generated method stub

                Intent editarNoticia = new Intent(getActivity(),
                        TabsNoticia.class);
                editarNoticia.putExtra("actualizar", true);
                editarNoticia.putExtra("id_noticia",
                        noticiaArray.get(position).getID_NOTICIA());
                editarNoticia.putExtra("titulo", noticiaArray.get(position).getTITULO());
                editarNoticia.putExtra("descripcion", noticiaArray.get(position).getDESCRIPCION());
                editarNoticia.putExtra("link", noticiaArray.get(position).getLINK());

                startActivity(editarNoticia);
            }

            @Override
            public void onLongClick(View view, final int position) {
                // TODO Auto-generated method stub


                dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                        "Desea eliminar la noticia?", null, null);
                dialogoAlerta.btnAceptar.setText("Aceptar");
                dialogoAlerta.btnCancelar.setText("Cancelar");

                dialogoAlerta.btnAceptar
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                posicion = noticiaArray.get(position).getID_NOTICIA();
                                envioWebService();
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

    public void envioWebService() {
        String fecha = auxiliarGeneral.getFechaOficial();
        request.setMethod("POST");
        request.setParametrosDatos("id_noticia", String.valueOf(posicion));
        request.setParametrosDatos("fecha_actualizacion", fecha);
        URL = null;
        URL = auxiliarGeneral.getURLNOTICIAALL();
        URL = URL + auxiliarGeneral.getDeletePHP("Noticia");

        if (auxiliarGeneral.isNetworkAvailable(getActivity()))
            new AsyncTaskGenericAdeful(getActivity(), this, URL, request, "Noticia", true, posicion, "a", fecha);
        else
            auxiliarGeneral.errorWebService(getActivity(), getActivity().getResources().getString(R.string.error_without_internet));
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void inicializarControles(String msg) {
        recyclerViewLoadNoticia();
        Toast.makeText(
                getActivity(),
                msg,
                Toast.LENGTH_SHORT).show();
        dialogoAlerta.alertDialog.dismiss();
    }

    public void recyclerViewLoadNoticia() {
        noticiaArray = controladorGeneral.selectListaNoticia();
        if (noticiaArray != null) {
            adaptadorRecyclerNoticia = new AdaptadorRecyclerNoticia(noticiaArray, getActivity());
            adaptadorRecyclerNoticia.notifyDataSetChanged();
            recyclerArticulo.setAdapter(adaptadorRecyclerNoticia);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
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
        }
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

