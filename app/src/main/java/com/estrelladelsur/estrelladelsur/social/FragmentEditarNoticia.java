package com.estrelladelsur.estrelladelsur.social;

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
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerNoticia;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Noticia;

import java.util.ArrayList;


public class FragmentEditarNoticia extends Fragment {

    private int CheckedPositionFragment;
    private RecyclerView recyclerArticulo;
    private ControladorAdeful controladorAdeful;
    private ArrayList<Noticia> noticiaArray;
    private AdaptadorRecyclerNoticia adaptadorRecyclerNoticia;
    private DialogoAlerta dialogoAlerta;
    private AuxiliarGeneral auxiliarGeneral;

    public static FragmentEditarNoticia newInstance() {
        FragmentEditarNoticia fragment = new FragmentEditarNoticia();
        return fragment;
    }

    public FragmentEditarNoticia() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        controladorAdeful = new ControladorAdeful(getActivity());
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
                                if (controladorAdeful.eliminarNoticiaAdeful(noticiaArray.get(position)
                                        .getID_NOTICIA())) {
                                    recyclerViewLoadNoticia();
                                    Toast.makeText(
                                            getActivity(),
                                            "Noticia eliminado correctamente",
                                            Toast.LENGTH_SHORT).show();
                                    dialogoAlerta.alertDialog.dismiss();
                                } else {
                                    auxiliarGeneral.errorDataBase(getActivity());
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void recyclerViewLoadNoticia() {
        noticiaArray = controladorAdeful.selectListaNoticiaAdeful();
        if(noticiaArray != null) {
            adaptadorRecyclerNoticia = new AdaptadorRecyclerNoticia(noticiaArray, getActivity());
            adaptadorRecyclerNoticia.notifyDataSetChanged();
            recyclerArticulo.setAdapter(adaptadorRecyclerNoticia);
        }else{
            auxiliarGeneral.errorDataBase(getActivity());
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
        // menu.getItem(1).setVisible(false);//permiso
        // menu.getItem(2).setVisible(false);//lifuba
        menu.getItem(3).setVisible(false);// adeful
        menu.getItem(4).setVisible(false);// puesto
        menu.getItem(5).setVisible(false);// posicion
        menu.getItem(6).setVisible(false);// cargo
        // menu.getItem(7).setVisible(false);//cerrar
        menu.getItem(8).setVisible(false);// guardar
        menu.getItem(9).setVisible(false);// Subir
        menu.getItem(10).setVisible(false); // eliminar
        menu.getItem(11).setVisible(false); // consultar
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        // noinspection SimplifiableIfStatement
        if (id == R.id.action_usuario) {

            /*Intent usuario = new Intent(getActivity(),
                    NavigationDrawerUsuario.class);
            startActivity(usuario);*/

            return true;
        }

        if (id == R.id.action_permisos) {
            return true;
        }


        if (id == R.id.action_lifuba) {

            return true;
        }

        if (id == android.R.id.home) {

            NavUtils.navigateUpFromSameTask(getActivity());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

