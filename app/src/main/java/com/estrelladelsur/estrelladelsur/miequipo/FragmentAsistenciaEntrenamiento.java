package com.estrelladelsur.estrelladelsur.miequipo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerDivisionEntrenamiento;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerFechaEntrenamiento;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerEntrenamiento;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerMes;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.EntrenamientoRecycler;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento_Division;
import com.estrelladelsur.estrelladelsur.entidad.Mes;

import java.util.ArrayList;


public class FragmentAsistenciaEntrenamiento extends Fragment {

    private Spinner entrenamientoAnioSpinner;
    private Spinner entrenamientoMesSpinner;
    private Spinner entrenamientoDivisionSpinner;
    private Spinner entrenamientoFechahsSpinner;
    private ControladorAdeful controladorAdeful;
    private RecyclerView recycleViewGeneral;
    private int CheckedPositionFragment;
    private FloatingActionButton botonFloating;
    private ArrayList<Anio> anioArray;
    private ArrayList<Mes> mesArray;
    private AdapterSpinnerAnio adapterSpinnerAnio;
    private AdapterSpinnerMes adapterSpinnerMes;
    private AdapterSpinnerFechaEntrenamiento adapterSpinnerFechaEntrenamiento;
    //private ArrayList<EntrenamientoRecycler> arrayEntrenamiento;
    private ArrayList<EntrenamientoRecycler> entrenamientoArray;
    private AdaptadorRecyclerEntrenamiento adaptadorEntrenamiento;
    private DialogoAlerta dialogoAlerta;
    private AuxiliarGeneral auxiliarGeneral;
    private ArrayList<Entrenamiento_Division> entrenamientoDivisionArray;
    private AdapterSpinnerDivisionEntrenamiento adapterSpinnerDivisionEntrenamiento;

    public static FragmentAsistenciaEntrenamiento newInstance() {
        FragmentAsistenciaEntrenamiento fragment = new FragmentAsistenciaEntrenamiento();
        return fragment;
    }

    public FragmentAsistenciaEntrenamiento() {
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

        View v = inflater.inflate(R.layout.fragment_editar_entrenamiento,
                container, false);
        // RECYCLER
        recycleViewGeneral = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
        // ANIO
        entrenamientoAnioSpinner = (Spinner) v
                .findViewById(R.id.entrenamientoAnioSpinner);
        // DIA
        entrenamientoMesSpinner = (Spinner) v
                .findViewById(R.id.entrenamientoMesSpinner);
        //FECHA
        entrenamientoFechahsSpinner = (Spinner) v
                .findViewById(R.id.entrenamientoFechahsSpinner);
        entrenamientoFechahsSpinner.setVisibility(View.VISIBLE);
        // DIVISION
        entrenamientoDivisionSpinner = (Spinner) v
                .findViewById(R.id.entrenamientoDivisionSpinner);
        entrenamientoDivisionSpinner.setVisibility(View.VISIBLE);


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

    private void init() {

        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        // ANIO SPINNER
        controladorAdeful.abrirBaseDeDatos();
        anioArray = controladorAdeful.selectListaAnio();
        if (anioArray != null) {
            controladorAdeful.cerrarBaseDeDatos();

            // ANIO ADAPTER
            adapterSpinnerAnio = new AdapterSpinnerAnio(getActivity(),
                    R.layout.simple_spinner_dropdown_item, anioArray);
            entrenamientoAnioSpinner.setAdapter(adapterSpinnerAnio);
        } else {
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
        }
        // MES SPINNER
        controladorAdeful.abrirBaseDeDatos();
        mesArray = controladorAdeful.selectListaMes();
        if (mesArray != null) {
            controladorAdeful.cerrarBaseDeDatos();

            // MES ADAPTER
            adapterSpinnerMes = new AdapterSpinnerMes(getActivity(),
                    R.layout.simple_spinner_dropdown_item, mesArray);
            entrenamientoMesSpinner.setAdapter(adapterSpinnerMes);
        } else {
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
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
                                                    controladorAdeful.abrirBaseDeDatos();
                                                    if (controladorAdeful.eliminarEntrenamientoAdeful(entrenamientoArray.get(position).getID_ENTRENAMIENTO())) {
                                                        controladorAdeful.cerrarBaseDeDatos();
                                                        recyclerViewLoadEntrenamiento("01");
                                                        Toast.makeText(
                                                                getActivity(),
                                                                "Entrenamiento Eliminado Correctamente",
                                                                Toast.LENGTH_SHORT).show();

                                                        dialogoAlerta.alertDialog.dismiss();
                                                    } else {
                                                        controladorAdeful.cerrarBaseDeDatos();
                                                        Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                                                                Toast.LENGTH_SHORT).show();
                                                    }
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
                // TODO Auto-generated method stub

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
                                                 String fecha = null;
                                                 fecha = auxiliarGeneral.setFormatoMes(
                                                         entrenamientoMesSpinner.getSelectedItem().toString()) + "-" + entrenamientoAnioSpinner.getSelectedItem().toString();
                                                 if (fecha != null)
                                                     //     recyclerViewLoadEntrenamiento(fecha);


                                                     controladorAdeful.abrirBaseDeDatos();
                                                 entrenamientoArray = controladorAdeful.selectListaEntrenamientoAdeful(fecha);
                                                 if (entrenamientoArray != null) {
                                                     controladorAdeful.cerrarBaseDeDatos();

                                                     // MES ADAPTER
                                                     adapterSpinnerFechaEntrenamiento = new AdapterSpinnerFechaEntrenamiento(getActivity(),
                                                             R.layout.simple_spinner_dropdown_item, entrenamientoArray);
                                                     entrenamientoFechahsSpinner.setAdapter(adapterSpinnerFechaEntrenamiento);
                                                 } else {
                                                     controladorAdeful.cerrarBaseDeDatos();
                                                     Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                                                             Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                         }
        );


        entrenamientoFechahsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {

                controladorAdeful.abrirBaseDeDatos();
                entrenamientoDivisionArray = controladorAdeful.selectListaDivisionEntrenamientoAdefulId(entrenamientoArray.get(position).getID_ENTRENAMIENTO());
                if (entrenamientoDivisionArray != null) {
                    controladorAdeful.cerrarBaseDeDatos();
                    // MES ADAPTER
                    adapterSpinnerDivisionEntrenamiento = new AdapterSpinnerDivisionEntrenamiento(getActivity(),
                            R.layout.simple_spinner_dropdown_item, entrenamientoDivisionArray);
                    entrenamientoDivisionSpinner.setAdapter(adapterSpinnerDivisionEntrenamiento);


                } else {
                    controladorAdeful.cerrarBaseDeDatos();
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        entrenamientoDivisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {

                Toast.makeText(getActivity(),""+entrenamientoDivisionArray.get(position).getID_DIVISION(),Toast.LENGTH_SHORT).show();

//                controladorAdeful.abrirBaseDeDatos();
//                entrenamientoDivisionArray = controladorAdeful.selectListaDivisionEntrenamientoAdefulId(entrenamientoArray.get(position).getID_ENTRENAMIENTO());
//                if (entrenamientoDivisionArray != null) {
//                    controladorAdeful.cerrarBaseDeDatos();
//                    // MES ADAPTER
//                    adapterSpinnerDivisionEntrenamiento = new AdapterSpinnerDivisionEntrenamiento(getActivity(),
//                            R.layout.simple_spinner_dropdown_item, entrenamientoDivisionArray);
//                    entrenamientoDivisionSpinner.setAdapter(adapterSpinnerDivisionEntrenamiento);
//
//
//                } else {
//                    controladorAdeful.cerrarBaseDeDatos();
//                    Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
//                            Toast.LENGTH_SHORT).show();
//                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }


    public void recyclerViewLoadEntrenamiento(String fecha) {

        controladorAdeful.abrirBaseDeDatos();
        entrenamientoArray = controladorAdeful.selectListaEntrenamientoAdeful(fecha);
        if (entrenamientoArray != null) {
            controladorAdeful.cerrarBaseDeDatos();

            adaptadorEntrenamiento = new AdaptadorRecyclerEntrenamiento(entrenamientoArray, getActivity());
            recycleViewGeneral.setAdapter(adaptadorEntrenamiento);
        } else {
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(getActivity(), getResources().getString(R.string.error_data_base),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Metodo click item recycler
     *
     * @author LEO
     */

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

}