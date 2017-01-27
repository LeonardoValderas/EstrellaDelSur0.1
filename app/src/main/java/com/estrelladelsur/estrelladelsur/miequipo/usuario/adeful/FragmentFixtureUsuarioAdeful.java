package com.estrelladelsur.estrelladelsur.miequipo.usuario.adeful;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.usuario.AdaptadorRecyclerFixtureUsuario;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.usuario.adeful.ControladorUsuarioAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Fixture;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericIndividual;
import com.estrelladelsur.estrelladelsur.webservice.Request;
import com.estrelladelsur.estrelladelsur.webservice.Variable;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class FragmentFixtureUsuarioAdeful extends Fragment {
    private RecyclerView recycleViewEquipo;
    private ArrayList<Fixture> fixtureAdefulArray;
    private AdaptadorRecyclerFixtureUsuario adaptador;
    private int CheckedPositionFragment;
    private ControladorUsuarioAdeful controladorUsuario;
    private AuxiliarGeneral auxiliarGeneral;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyAsyncTaskListener listener;
    private Request request;
    private AdRequest adRequest;
    private AdView mAdView;
    private Spinner divisionSpinner;
    private Division division;
    private ArrayList<Division> divisionArray;
    private ArrayAdapter<String> adaptadorInicial;
    private AdapterSpinnerDivision adapterSpinnerDivision;
    private int divisionSpinnerInt;

    public static FragmentFixtureUsuarioAdeful newInstance() {
        FragmentFixtureUsuarioAdeful fragment = new FragmentFixtureUsuarioAdeful();
        return fragment;
    }

    public FragmentFixtureUsuarioAdeful() {
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
        View v = inflater.inflate(R.layout.usuario_fragment_float_spinner, container,
                false);
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        recycleViewEquipo = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        divisionSpinner = (Spinner) v.findViewById(R.id.jugadoresDivisionSpinner);
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
        listener = new MyAsyncTaskListener() {
            @Override
            public void onPostExecuteConcluded(boolean result, String mensaje) {
                if (result) {
                    recyclerViewLoadFixture(divisionSpinnerInt);
                } else {
                    auxiliarGeneral.errorWebService(getActivity(), mensaje);
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        };
        setLayoutManager();
        divisionArray = controladorUsuario.selectListaDivisionUsuarioAdeful();
        if (divisionArray != null) {
            // DIVSION SPINNER
            if (divisionArray.size() != 0) {
                adapterSpinnerDivision = new AdapterSpinnerDivision(getActivity(),
                        R.layout.simple_spinner_dropdown_item, divisionArray);
                divisionSpinner.setAdapter(adapterSpinnerDivision);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.vacioSpinnerDivision));
                divisionSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }

        divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (divisionSpinner.getSelectedItem().toString().equals(getResources().
                        getString(R.string.vacioSpinnerDivision))) {
                    if (isAdded() && isVisible() && getUserVisibleHint())
                        Toast.makeText(getActivity(), "No hay datos cargados.",
                                Toast.LENGTH_SHORT).show();
                } else {
                    division = (Division) divisionSpinner.getSelectedItem();
                    divisionSpinnerInt = division.getID_DIVISION();
                    recyclerViewLoadFixture(divisionSpinnerInt);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //  recyclerViewLoadFixture();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String fecha = controladorUsuario.selectTabla(Variable.TABLA_FIXTURE_ADEFUL);
                if (fecha != null) {
                    request = new Request();
                    request.setMethod("POST");
                    request.setParametrosDatos("fecha_tabla", fecha);
                    request.setParametrosDatos("tabla", Variable.TABLA_FIXTURE_ADEFUL);
                    request.setParametrosDatos("liga", "ADEFUL");

                    new AsyncTaskGenericIndividual(getActivity(), listener, auxiliarGeneral.getURLSINCRONIZARINDIVIDUAL(), request, Variable.FIXTURE_ADEFUL);
                }
            }
        });
    }

    public void setLayoutManager(){
        recycleViewEquipo.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewEquipo.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewEquipo.setItemAnimator(new DefaultItemAnimator());
    }

    public void recyclerViewLoadFixture(int id_division) {

         fixtureAdefulArray = controladorUsuario.selectListaFixtureUsuarioAdeful(id_division);
        if (fixtureAdefulArray != null) {
            adaptador = new AdaptadorRecyclerFixtureUsuario(fixtureAdefulArray, getActivity());
            recycleViewEquipo.setAdapter(adaptador);
            if (fixtureAdefulArray.isEmpty())
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