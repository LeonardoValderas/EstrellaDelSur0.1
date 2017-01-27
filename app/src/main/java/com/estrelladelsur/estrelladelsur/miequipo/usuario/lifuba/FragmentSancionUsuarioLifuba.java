package com.estrelladelsur.estrelladelsur.miequipo.usuario.lifuba;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.usuario.AdaptadorSancionUsuario;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.usuario.lifuba.ControladorUsuarioLifuba;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericIndividual;
import com.estrelladelsur.estrelladelsur.webservice.Request;
import com.estrelladelsur.estrelladelsur.webservice.Variable;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class FragmentSancionUsuarioLifuba extends Fragment {
    private AdapterSpinnerDivision adapterSpinnerDivision;
    private RecyclerView recyclerViewSancion;
    private ArrayAdapter<String> adaptadorInicial;
    private int CheckedPositionFragment;
    private ControladorUsuarioLifuba controladorUsuarioLifuba;
    private AuxiliarGeneral auxiliarGeneral;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Spinner jugadoresDivisionSpinner;
  //  private FloatingActionButton botonFloating;
    private ArrayList<Sancion> sancionArray;
    private ArrayList<Division> divisionArray;
    private Division division;
    private int divisionSpinner;
    private AdaptadorSancionUsuario adaptadorSancionUsuario;
    private MyAsyncTaskListener listener;
    private Request request;
    private AdRequest adRequest;
    private AdView mAdView;

    public static FragmentSancionUsuarioLifuba newInstance() {
        FragmentSancionUsuarioLifuba fragment = new FragmentSancionUsuarioLifuba();
        return fragment;
    }

    public FragmentSancionUsuarioLifuba() {
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
        View v = inflater.inflate(R.layout.usuario_fragment_float_spinner, container,
                false);
        auxiliarGeneral = new AuxiliarGeneral(getActivity());

        recyclerViewSancion = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        jugadoresDivisionSpinner = (Spinner) v.findViewById(R.id.jugadoresDivisionSpinner);
      //  botonFloating = (FloatingActionButton) v.findViewById(R.id.botonFloating);
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
        initRecycler();

        divisionArray = controladorUsuarioLifuba.selectListaDivisionUsuarioLifuba();
        if (divisionArray != null) {
            // DIVSION SPINNER
            if (divisionArray.size() != 0) {
                adapterSpinnerDivision = new AdapterSpinnerDivision(getActivity(),
                        R.layout.simple_spinner_dropdown_item, divisionArray);
                jugadoresDivisionSpinner.setAdapter(adapterSpinnerDivision);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.vacioSpinnerDivision));
                jugadoresDivisionSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }

        jugadoresDivisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (jugadoresDivisionSpinner.getSelectedItem().toString().equals(getResources().
                        getString(R.string.vacioSpinnerDivision))) {
                    if (isAdded() && isVisible() && getUserVisibleHint())
                        Toast.makeText(getActivity(), "No hay datos cargados.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    division = (Division) jugadoresDivisionSpinner.getSelectedItem();
                    divisionSpinner = division.getID_DIVISION();
                    recyclerViewLoadSancion(divisionSpinner);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        botonFloating.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (jugadoresDivisionSpinner.getSelectedItem().toString().equals(getResources().
//                        getString(R.string.vacioSpinnerDivision))) {
//                    Toast.makeText(getActivity(), "No hay datos cargados.",
//                            Toast.LENGTH_SHORT).show();
//                } else {
//                    division = (Division) jugadoresDivisionSpinner.getSelectedItem();
//                    divisionSpinner = division.getID_DIVISION();
//                    recyclerViewLoadSancion(divisionSpinner);
//                }
//            }
//        });
        listener = new MyAsyncTaskListener() {
            @Override
            public void onPostExecuteConcluded(boolean result, String mensaje) {
                if (result) {
                    recyclerViewLoadSancion(divisionSpinner);
                } else {
                    auxiliarGeneral.errorWebService(getActivity(), mensaje);
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        };

        recyclerViewSancion.addOnItemTouchListener(new
                RecyclerTouchListener(getActivity(),
                recyclerViewSancion, new ClickListener() {
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
                String fecha = controladorUsuarioLifuba.selectTabla(Variable.TABLA_SANCION_LIFUBA);
                if (fecha != null) {
                    request = new Request();
                    request.setMethod("POST");
                    request.setParametrosDatos("fecha_tabla", fecha);
                    request.setParametrosDatos("tabla", Variable.TABLA_SANCION_LIFUBA);
                    request.setParametrosDatos("liga", "LIFUBA");

                    new AsyncTaskGenericIndividual(getActivity(), listener, auxiliarGeneral.getURLSINCRONIZARINDIVIDUAL(), request, Variable.SANCION_LIFUBA);
                }
            }
        });
    }

    public void recyclerViewLoadSancion(int division) {
        sancionArray = controladorUsuarioLifuba.selectListaSancionUsuarioLifuba(division);
        if (sancionArray != null) {
            adaptadorSancionUsuario = new AdaptadorSancionUsuario(sancionArray, getActivity());
            recyclerViewSancion.setAdapter(adaptadorSancionUsuario);
            if (sancionArray.isEmpty())
                if (isAdded() && isVisible() && getUserVisibleHint())
                    Toast.makeText(
                        getActivity(),
                        "Selección sin datos",
                        Toast.LENGTH_SHORT).show();
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public void initRecycler() {
        recyclerViewSancion.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewSancion.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerViewSancion.setItemAnimator(new DefaultItemAnimator());
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