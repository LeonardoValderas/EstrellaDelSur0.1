package com.estrelladelsur.estrelladelsur.liga.usuario.lifuba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.usuario.AdaptadorRecyclerCanchaUsuario;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.usuario.lifuba.ControladorUsuarioLifuba;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericIndividual;
import com.estrelladelsur.estrelladelsur.webservice.Request;
import com.estrelladelsur.estrelladelsur.webservice.Variable;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class FragmentCanchaUsuario extends Fragment {

    private RecyclerView recycleViewCancha;
    private ArrayList<Cancha> canchaAdefulArray;
    private AdaptadorRecyclerCanchaUsuario adaptadorCancha;
    private int CheckedPositionFragment;
    private AuxiliarGeneral auxiliarGeneral;
    private ControladorUsuarioLifuba controladorUsuarioLifuba;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyAsyncTaskListener listener;
    private Request request;
    private AdRequest adRequest;
    private AdView mAdView;

    public static FragmentCanchaUsuario newInstance() {
        FragmentCanchaUsuario fragment = new FragmentCanchaUsuario();
        return fragment;
    }

    public FragmentCanchaUsuario() {
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
        recycleViewCancha = (RecyclerView) v.findViewById(R.id.recycleViewGeneral);
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
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        listener = new MyAsyncTaskListener() {
            @Override
            public void onPostExecuteConcluded(boolean result, String mensaje) {
                if (result) {
                    recyclerViewLoadCancha();
                } else {
                    auxiliarGeneral.errorWebService(getActivity(), mensaje);
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        };
        recyclerViewLoadCancha();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String fecha = controladorUsuarioLifuba.selectTabla(Variable.TABLA_CANCHA_LIFUBA);
                if (fecha != null) {
                    request = new Request();
                    request.setMethod("POST");
                    request.setParametrosDatos("fecha_tabla", fecha);
                    request.setParametrosDatos("tabla", Variable.TABLA_CANCHA_LIFUBA);
                    request.setParametrosDatos("liga", "LIFUBA");

                    new AsyncTaskGenericIndividual(getActivity(), listener, auxiliarGeneral.getURLSINCRONIZARINDIVIDUAL(), request, Variable.CANCHA_LIFUBA);
                }
            }
        });
        recycleViewCancha.addOnItemTouchListener(new
                RecyclerTouchListener(getActivity(),
                recycleViewCancha, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                setIntentMap(position);
            }

            @Override
            public void onLongClick(View view, final int position) {
            }
        }));

    }

    public void recyclerViewLoadCancha() {
        recycleViewCancha.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewCancha.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewCancha.setItemAnimator(new DefaultItemAnimator());

        canchaAdefulArray = controladorUsuarioLifuba.selectListaCanchaUsuarioLifuba();
        if (canchaAdefulArray != null) {
            adaptadorCancha = new AdaptadorRecyclerCanchaUsuario(canchaAdefulArray, getActivity());
            recycleViewCancha.setAdapter(adaptadorCancha);
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

    public void setIntentMap(int position) {

        Intent mapa = new Intent(getActivity(),
                MapaCanchaUsuario.class);

        mapa.putExtra("nombre", canchaAdefulArray.get(position)
                .getNOMBRE());
        mapa.putExtra("longitud",
                canchaAdefulArray.get(position).getLONGITUD());
        mapa.putExtra("latitud", canchaAdefulArray
                .get(position).getLATITUD());
        mapa.putExtra("direccion",
                canchaAdefulArray.get(position).getDIRECCION());

        startActivity(mapa);
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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_administrador_general, menu);
        menu.getItem(1).setVisible(false);//permiso
        menu.getItem(2).setVisible(false);//lifuba
        menu.getItem(3).setVisible(false);// adeful
        menu.getItem(4).setVisible(false);// puesto
        menu.getItem(5).setVisible(false);// posicion
        menu.getItem(6).setVisible(false);// cargo
        menu.getItem(8).setVisible(false);// guardar
        menu.getItem(9).setVisible(false);// Subir
        menu.getItem(10).setVisible(false); // eliminar
        menu.getItem(11).setVisible(false); // consultar
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(getActivity());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
