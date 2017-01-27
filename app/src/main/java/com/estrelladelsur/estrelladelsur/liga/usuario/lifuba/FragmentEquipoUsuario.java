package com.estrelladelsur.estrelladelsur.liga.usuario.lifuba;

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
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerEquipo;
import com.estrelladelsur.estrelladelsur.adaptador.usuario.AdaptadorRecyclerEquipoUsuario;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.usuario.lifuba.ControladorUsuarioLifuba;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericIndividual;
import com.estrelladelsur.estrelladelsur.webservice.Request;
import com.estrelladelsur.estrelladelsur.webservice.Variable;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class FragmentEquipoUsuario extends Fragment {

    private RecyclerView recycleViewEquipo;
    private ArrayList<Equipo> equipoAdefulArray;
    private AdaptadorRecyclerEquipoUsuario adaptador;
    private int CheckedPositionFragment;
    private ControladorUsuarioLifuba controladorUsuarioLifuba;
    private AuxiliarGeneral auxiliarGeneral;
    private String usuario = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyAsyncTaskListener listener;
    private Request request;
    private AdRequest adRequest;
    private AdView mAdView;

    public static FragmentEquipoUsuario newInstance() {
        FragmentEquipoUsuario fragment = new FragmentEquipoUsuario();
        return fragment;
    }

    public FragmentEquipoUsuario() {
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        controladorUsuarioLifuba = new ControladorUsuarioLifuba(getActivity());
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
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        mAdView = (AdView) v.findViewById(R.id.adView);

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
        if (mAdView != null) {
            mAdView.resume();
        }
        controladorUsuarioLifuba = new ControladorUsuarioLifuba(getActivity());
        init();
    }

    private void init() {
        BannerAd();
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
        listener = new MyAsyncTaskListener() {
            @Override
            public void onPostExecuteConcluded(boolean result, String mensaje) {
                if (result) {
                    recyclerViewLoadEquipo();
                } else {
                    auxiliarGeneral.errorWebService(getActivity(), mensaje);
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        };
        recyclerViewLoadEquipo();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String fecha = controladorUsuarioLifuba.selectTabla(Variable.TABLA_EQUIPO_LIFUBA);
                if (fecha != null) {
                    request = new Request();
                    request.setMethod("POST");
                    request.setParametrosDatos("fecha_tabla", fecha);
                    request.setParametrosDatos("tabla", Variable.TABLA_EQUIPO_LIFUBA);
                    request.setParametrosDatos("liga", "LIFUBA");

                    new AsyncTaskGenericIndividual(getActivity(), listener, auxiliarGeneral.getURLSINCRONIZARINDIVIDUAL(), request, Variable.EQUIPO_LIFUBA);
                }
            }
        });
    }

    public void recyclerViewLoadEquipo() {

        recycleViewEquipo.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewEquipo.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewEquipo.setItemAnimator(new DefaultItemAnimator());

        equipoAdefulArray = controladorUsuarioLifuba.selectListaEquipoUsuarioLifuba();
        if (equipoAdefulArray != null) {
            adaptador = new AdaptadorRecyclerEquipoUsuario(equipoAdefulArray, getActivity());
            recycleViewEquipo.setAdapter(adaptador);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
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