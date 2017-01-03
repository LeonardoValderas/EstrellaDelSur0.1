package com.estrelladelsur.estrelladelsur.miequipo.usuario;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.usuario.AdaptadorRecyclerEntrenamientoUsuario;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.usuario.adeful.ControladorUsuarioAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.usuario.DialogoArticulo;
import com.estrelladelsur.estrelladelsur.entidad.EntrenamientoRecycler;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericIndividual;
import com.estrelladelsur.estrelladelsur.webservice.Request;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class FragmentEntrenamientoUsuario extends Fragment {


    private int CheckedPositionFragment;
    private AuxiliarGeneral auxiliarGeneral;
    private ArrayList<EntrenamientoRecycler> entrenamientoArray;
    private ControladorUsuarioAdeful controladorUsuario;
    private AdaptadorRecyclerEntrenamientoUsuario adaptadorRecyclerEntrenamientoUsuario;
    private RecyclerView recycleViewUsuarioGeneral;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyAsyncTaskListener listener;
    private Request request;
    private AdRequest adRequest;
    private AdView mAdView;

    public static FragmentEntrenamientoUsuario newInstance() {
        FragmentEntrenamientoUsuario fragment = new FragmentEntrenamientoUsuario();
        return fragment;
    }

    public FragmentEntrenamientoUsuario() {
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
        View v = inflater.inflate(R.layout.fragment_general_liga_usuario,
                container, false);
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        // RECYCLER
        recycleViewUsuarioGeneral = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
        // CANCHA
        mSwipeRefreshLayout = (SwipeRefreshLayout) v
                .findViewById(R.id.swipeRefreshLayout);

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
        controladorUsuario = new ControladorUsuarioAdeful(getActivity());
        init();
    }

    private void init() {
        BannerAd();
        initRecycler();
        listener = new MyAsyncTaskListener() {
            @Override
            public void onPostExecuteConcluded(boolean result, String mensaje) {
                if (result) {
                    recyclerViewLoadEntrenamiento();
                } else {
                    auxiliarGeneral.errorWebService(getActivity(), mensaje);
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        };
        recyclerViewLoadEntrenamiento();

        recycleViewUsuarioGeneral.addOnItemTouchListener(new
                RecyclerTouchListener(getActivity(),
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
                String fecha = controladorUsuario.selectTabla(AsyncTaskGenericIndividual.TABLA_ENTRENAMIENTO);
                if (fecha != null) {
                    request = new Request();
                    request.setMethod("POST");
                    request.setParametrosDatos("fecha_tabla", fecha);
                    request.setParametrosDatos("tabla", AsyncTaskGenericIndividual.TABLA_ENTRENAMIENTO);
                    request.setParametrosDatos("liga", "ADEFUL");

                    new AsyncTaskGenericIndividual(getActivity(), listener, auxiliarGeneral.getURLSINCRONIZARINDIVIDUAL(), request, AsyncTaskGenericIndividual.ENTRENAMIENTO_ADEFUL);
                }
            }
        });
    }

    public void initRecycler() {
        recycleViewUsuarioGeneral.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewUsuarioGeneral.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewUsuarioGeneral.setItemAnimator(new DefaultItemAnimator());
    }

    public void recyclerViewLoadEntrenamiento() {
        String fecha = auxiliarGeneral.getMounthYearCurrent();
        entrenamientoArray = controladorUsuario.selectListaEntrenamientoUsuario(fecha);
        if (entrenamientoArray != null) {
            adaptadorRecyclerEntrenamientoUsuario = new AdaptadorRecyclerEntrenamientoUsuario(entrenamientoArray, getActivity());
            recycleViewUsuarioGeneral.setAdapter(adaptadorRecyclerEntrenamientoUsuario);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
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
