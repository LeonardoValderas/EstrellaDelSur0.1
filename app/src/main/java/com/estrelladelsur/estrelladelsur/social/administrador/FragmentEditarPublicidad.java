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
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerPublicidad;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Publicidad;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.social.administrador.tabs.TabsPublicidad;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGeneric;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import java.util.ArrayList;

public class FragmentEditarPublicidad extends Fragment implements MyAsyncTaskListener {

    private ControladorGeneral controladorGeneral;
    private int CheckedPositionFragment;
    private RecyclerView recyclerViewPublicidad;
    private ArrayList<Publicidad> publicidadArray;
    private DialogoAlerta dialogoAlerta;
    private AdaptadorRecyclerPublicidad adaptadorEditarPublicidad;
    private AuxiliarGeneral auxiliarGeneral;
    private String URL = null;
    private Request request = new Request();
    private int posicion = 0;
    private String nombre_foto = null;

    public static FragmentEditarPublicidad newInstance() {
        FragmentEditarPublicidad fragment = new FragmentEditarPublicidad();
        return fragment;
    }

    public FragmentEditarPublicidad() {
        // Required empty public constructor
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
        View v = inflater.inflate(R.layout.fragment_editar_recyclerview,
                container, false);
        // RECYCLER
        recyclerViewPublicidad = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        // RECLYCLER
        recyclerViewPublicidad.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewPublicidad.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerViewPublicidad.setItemAnimator(new DefaultItemAnimator());

        recyclerViewLoadPublicidad();

        recyclerViewPublicidad.addOnItemTouchListener(new
                RecyclerTouchListener(getActivity(),
                recyclerViewPublicidad, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                // TODO Auto-generated method stub

                Intent editarPublicidad = new Intent(getActivity(),
                        TabsPublicidad.class);
                editarPublicidad.putExtra("actualizar", true);
                editarPublicidad.putExtra("id_publicidad",
                        publicidadArray.get(position).getID_PUBLICIDAD());
                editarPublicidad.putExtra("titulo",
                        publicidadArray.get(position).getTITULO());
                editarPublicidad.putExtra("logo",
                        publicidadArray.get(position).getLOGO());
                editarPublicidad.putExtra("nombre_foto",
                        publicidadArray.get(position).getNOMBRE_FOTO());
                editarPublicidad.putExtra("otros",
                        publicidadArray.get(position).getOTROS());


                startActivity(editarPublicidad);
            }

            @Override
            public void onLongClick(View view, final int position) {
                // TODO Auto-generated method stub


                dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                        "Desea eliminar la publicidad?", null, null);
                dialogoAlerta.btnAceptar.setText("Aceptar");
                dialogoAlerta.btnCancelar.setText("Cancelar");

                dialogoAlerta.btnAceptar
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                posicion = publicidadArray.get(position)
                                        .getID_PUBLICIDAD();

                                nombre_foto = publicidadArray.get(position)
                                        .getNOMBRE_FOTO();

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void inicializarControles(String mensaje) {

        recyclerViewLoadPublicidad();
        posicion = 0;
        dialogoAlerta.alertDialog.dismiss();
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void envioWebService() {
        request.setMethod("POST");
        request.setParametrosDatos("id_publicidad", String.valueOf(posicion));
        request.setParametrosDatos("nombre_foto", nombre_foto);
        request.setParametrosDatos("fecha_actualizacion", auxiliarGeneral.getFechaOficial());
        URL = null;
        URL = auxiliarGeneral.getURLPUBLICIDADALL();
        URL = URL + auxiliarGeneral.getDeletePHP("Publicidad");

        new AsyncTaskGeneric(getActivity(), this, URL, request, "Publicidad", true, posicion, "a");
    }

    public void recyclerViewLoadPublicidad() {

        publicidadArray = controladorGeneral.selectListaPublicidad();
        if (publicidadArray != null) {
            adaptadorEditarPublicidad = new AdaptadorRecyclerPublicidad(publicidadArray, getActivity());
            recyclerViewPublicidad.setAdapter(adaptadorEditarPublicidad);
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
            // TODO Auto-generated method stub
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