package com.estrelladelsur.estrelladelsur.miequipo.usuario;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.usuario.AdaptadorRecyclerFixtureUsuario;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.usuario.ControladorUsuarioAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Fixture;

import java.util.ArrayList;

public class FragmentFixtureUsuario extends Fragment {
    private RecyclerView recycleViewEquipo;
    private ArrayList<Fixture> fixtureAdefulArray;
    private AdaptadorRecyclerFixtureUsuario adaptador;
    private int CheckedPositionFragment;
    private ControladorUsuarioAdeful controladorUsuario;
    private AuxiliarGeneral auxiliarGeneral;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public static FragmentFixtureUsuario newInstance() {
        FragmentFixtureUsuario fragment = new FragmentFixtureUsuario();
        return fragment;
    }

    public FragmentFixtureUsuario() {
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        controladorUsuario = new ControladorUsuarioAdeful(getActivity());
        if (state != null) {
            CheckedPositionFragment = state.getInt("curChoice", 0);
        } else {
            init();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_general_liga_usuario, container,
                false);
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        recycleViewEquipo = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
        mSwipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipeRefreshLayout);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        recyclerViewLoadFixture();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Refreshing data on server
                // new DownloadFilesTask().execute(feedUrl);
                recyclerViewLoadFixture();
            }
        });
    }

    public void recyclerViewLoadFixture() {

        recycleViewEquipo.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewEquipo.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewEquipo.setItemAnimator(new DefaultItemAnimator());

       fixtureAdefulArray = controladorUsuario.selectListaFixtureUsuarioAdeful();
        if (fixtureAdefulArray != null) {
            adaptador = new AdaptadorRecyclerFixtureUsuario(fixtureAdefulArray, getActivity());
            recycleViewEquipo.setAdapter(adaptador);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        inflater.inflate(R.menu.menu_administrador_general, menu);
//        menu.getItem(0).setVisible(false);//usuario
//        menu.getItem(1).setVisible(false);//permiso
//        menu.getItem(2).setVisible(false);//lifuba
//        menu.getItem(3).setVisible(false);// adeful
//        menu.getItem(4).setVisible(false);// puesto
//        menu.getItem(5).setVisible(false);// posicion
//        menu.getItem(6).setVisible(false);// cargo
//        // menu.getItem(7).setVisible(false);//cerrar
//        menu.getItem(8).setVisible(false);// guardar
//        menu.getItem(9).setVisible(false);// Subir
//        menu.getItem(10).setVisible(false); // eliminar
//        menu.getItem(11).setVisible(false); // consultar
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
////
////        if (id == R.id.action_usuario) {
////
////            /*Intent usuario = new Intent(getActivity(),
////                    NavigationDrawerUsuario.class);
////            startActivity(usuario);*/
////
////            return true;
////        }
//
////        if (id == R.id.action_permisos) {
////            return true;
////        }
////
////        if (id == R.id.action_guardar) {
////
////            if (editTextNombre.getText().toString().equals("")) {
////                Toast.makeText(getActivity(),
////                        "Ingrese el nombre del equipo.", Toast.LENGTH_SHORT)
////                        .show();
////            } else {
////                if (gestion == 0) {
////                    cargarEntidad(0, 0);
////                } else if (gestion == 1) {
////                    cargarEntidad(equipoAdefulArray.get(
////                            posicion).getID_EQUIPO(), 1);
////                }
////            }
////            return true;
////        }
//        if (id == android.R.id.home) {
//
//            NavUtils.navigateUpFromSameTask(getActivity());
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}