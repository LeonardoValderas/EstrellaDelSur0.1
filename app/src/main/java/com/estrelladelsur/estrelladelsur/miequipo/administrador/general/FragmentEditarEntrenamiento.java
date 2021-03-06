package com.estrelladelsur.estrelladelsur.miequipo.administrador.general;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerEntrenamiento;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerMes;
import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.EntrenamientoRecycler;
import com.estrelladelsur.estrelladelsur.entidad.Mes;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.miequipo.administrador.tabs_general.TabsEntrenamiento;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericAdeful;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import java.util.ArrayList;
import java.util.List;

public class FragmentEditarEntrenamiento extends Fragment implements MyAsyncTaskListener {

    private Spinner entrenamientoAnioSpinner;
    private Spinner entrenamientoMesSpinner;
    private ControladorAdeful controladorAdeful;
    private RecyclerView recycleViewGeneral;
    private int CheckedPositionFragment;
    private FloatingActionButton botonFloating;
    private ArrayList<Anio> anioArray;
    private ArrayList<Mes> mesArray;
    private AdapterSpinnerAnio adapterSpinnerAnio;
    private AdapterSpinnerMes adapterSpinnerMes;
    private ArrayList<EntrenamientoRecycler> entrenamientoArray;
    private AdaptadorRecyclerEntrenamiento adaptadorEntrenamiento;
    private DialogoAlerta dialogoAlerta;
    private AuxiliarGeneral auxiliarGeneral;
    private String URL = null;
    private Request request = new Request();
    private int posicion = 0;

    public static FragmentEditarEntrenamiento newInstance() {
        FragmentEditarEntrenamiento fragment = new FragmentEditarEntrenamiento();
        return fragment;
    }

    public FragmentEditarEntrenamiento() {
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

        View v = inflater.inflate(R.layout.fragment_editar_entrenamiento,
                container, false);
        // RECYCLER
        recycleViewGeneral = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
        // CANCHA
        entrenamientoAnioSpinner = (Spinner) v
                .findViewById(R.id.entrenamientoAnioSpinner);
        // DIA
        entrenamientoMesSpinner = (Spinner) v
                .findViewById(R.id.entrenamientoMesSpinner);
        //BOTON FLOATING
        botonFloating = (FloatingActionButton) v
                .findViewById(R.id.botonFloating);

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
        controladorAdeful = new ControladorAdeful(getActivity());
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        anioArray = controladorAdeful.selectListaAnio();
        mesArray = controladorAdeful.selectListaMes();
    }

    private void init() {

        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        // ANIO SPINNER
        anioArray = controladorAdeful.selectListaAnio();
        if (anioArray != null) {
            // ANIO ADAPTER
            adapterSpinnerAnio = new AdapterSpinnerAnio(getActivity(),
                    R.layout.simple_spinner_dropdown_item, anioArray);
            entrenamientoAnioSpinner.setAdapter(adapterSpinnerAnio);
            entrenamientoAnioSpinner.setSelection(getPositionYearSpinner(anioArray));
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        // MES SPINNER
        mesArray = controladorAdeful.selectListaMes();
        if (mesArray != null) {
            // MES ADAPTER
            adapterSpinnerMes = new AdapterSpinnerMes(getActivity(),
                    R.layout.simple_spinner_dropdown_item, mesArray);
            entrenamientoMesSpinner.setAdapter(adapterSpinnerMes);
            entrenamientoMesSpinner.setSelection(auxiliarGeneral.getMounthCurrent());
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        // RECYCLER VIEW
        recycleViewGeneral.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewGeneral.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewGeneral.setItemAnimator(new DefaultItemAnimator());
        recycleViewGeneral.addOnItemTouchListener(new RecyclerTouchListener(
                getActivity(), recycleViewGeneral, new ClickListener() {

            @Override
            public void onLongClick(View view, final int position) {

                dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                        "Desea eliminar el entrenamiento?", null, null);
                dialogoAlerta.btnAceptar.setText("Aceptar");
                dialogoAlerta.btnCancelar.setText("Cancelar");

                dialogoAlerta.btnAceptar
                        .setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    posicion = entrenamientoArray.get(position).getID_ENTRENAMIENTO();
                                                    envioWebService();
                                                }
                                            }
                        );
                dialogoAlerta.btnCancelar
                        .setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    // TODO Auto-generated method stub
                                                    dialogoAlerta.alertDialog.dismiss();
                                                }
                                            }
                        );
            }

            @Override
            public void onClick(View view, int position) {

                Intent editarEntrenamiento = new Intent(getActivity(),
                        TabsEntrenamiento.class);
                editarEntrenamiento.putExtra("actualizar", true);
                editarEntrenamiento.putExtra("id_entrenamiento",
                        entrenamientoArray.get(position).getID_ENTRENAMIENTO());
                editarEntrenamiento.putExtra("dia", entrenamientoArray.get(position).getDIA());
                editarEntrenamiento.putExtra("hora", entrenamientoArray.get(position).getHORA());
                editarEntrenamiento.putExtra("id_cancha", entrenamientoArray.get(position).getID_CANCHA());
                editarEntrenamiento.putExtra("cancha", entrenamientoArray.get(position).getNOMBRE());

                startActivity(editarEntrenamiento);
            }
        }
        ));

        botonFloating.setOnClickListener(new View.OnClickListener() {
                                             public void onClick(View view) {
                                                 if (fechaSpinner() != null)
                                                     recyclerViewLoadEntrenamiento(fechaSpinner());
                                             }
                                         }
        );
    }

    public String fechaSpinner() {
        String fecha = null;
        fecha = auxiliarGeneral.setFormatoMes(
                entrenamientoMesSpinner.getSelectedItem().toString()) + "-" + entrenamientoAnioSpinner.getSelectedItem().toString();
        return fecha;
    }

    public void inicializarControles(String mensaje) {
        recyclerViewLoadEntrenamiento(fechaSpinner());
        posicion = 0;
        dialogoAlerta.alertDialog.dismiss();
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void envioWebService() {
        String fecha = auxiliarGeneral.getFechaOficial();
        request.setMethod("POST");
        request.setParametrosDatos("id_entrenamiento", String.valueOf(posicion));
        request.setParametrosDatos("fecha_actualizacion", fecha);
        URL = null;
        URL = auxiliarGeneral.getURLENTRENAMIENTOADEFULALL();
        URL = URL + auxiliarGeneral.getDeletePHP("Entrenamiento");

        if (auxiliarGeneral.isNetworkAvailable(getActivity()))
            new AsyncTaskGenericAdeful(getActivity(), this, URL, request, "Entrenamiento", true, posicion, "o", fecha);
        else
            auxiliarGeneral.errorWebService(getActivity(), getActivity().getResources().getString(R.string.error_without_internet));
    }

    public void recyclerViewLoadEntrenamiento(String fecha) {
        entrenamientoArray = controladorAdeful.selectListaEntrenamientoAdeful(fecha);
        if (entrenamientoArray != null) {
            adaptadorEntrenamiento = new AdaptadorRecyclerEntrenamiento(entrenamientoArray, getActivity());
            recycleViewGeneral.setAdapter(adaptadorEntrenamiento);
            if (entrenamientoArray.isEmpty())
                Toast.makeText(
                        getActivity(),
                        "Selección sin datos",
                        Toast.LENGTH_SHORT).show();
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    private int getPositionYearSpinner(List<Anio> anios) {
        String anio = auxiliarGeneral.getYearSpinner();
        int index = 0;
        for (int i = 0; i < anios.size(); i++) {
            if (anios.get(i).getANIO().equals(anio)) {
                index = i;
            }
        }
        return index;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        inflater.inflate(R.menu.menu_administrador_general, menu);
        menu.getItem(1).setVisible(false);// posicion
        menu.getItem(2).setVisible(false);// cargo
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