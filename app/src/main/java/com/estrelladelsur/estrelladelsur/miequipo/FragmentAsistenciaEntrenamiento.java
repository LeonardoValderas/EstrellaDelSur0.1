package com.estrelladelsur.estrelladelsur.miequipo;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerAsistencia;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerDivisionEntrenamiento;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerFechaEntrenamiento;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerMes;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;
import com.estrelladelsur.estrelladelsur.entidad.EntrenamientoRecycler;
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
    private ArrayList<Entrenamiento> arrayAsistencia;
    private ArrayList<Entrenamiento> arrayAsistenciaAnterior;
    private ArrayList<EntrenamientoRecycler> entrenamientoArray;
    private AdaptadorRecyclerAsistencia adaptadorEntrenamiento;
    private DialogoAlerta dialogoAlerta;
    private AuxiliarGeneral auxiliarGeneral;
    private ArrayList<Entrenamiento> entrenamientoDivisionArray;
    private AdapterSpinnerDivisionEntrenamiento adapterSpinnerDivisionEntrenamiento;
    private AdaptadorRecyclerAsistencia adaptadorRecyclerAsistencia;
    private ArrayList<Entrenamiento> listaJugadoresAsistencia;
    private ArrayList<Integer> id_divisiones;
    private Entrenamiento entrenamientoAsistencia;
    private int id_entrenamiento;
    private ArrayList<Integer> id_jugadores_asistencia;

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

        View v = inflater.inflate(R.layout.fragment_editar_entrenamiento_asistencia,
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
        anioArray = controladorAdeful.selectListaAnio();
        if (anioArray != null) {
            // ANIO ADAPTER
            adapterSpinnerAnio = new AdapterSpinnerAnio(getActivity(),
                    R.layout.simple_spinner_dropdown_item, anioArray);
            entrenamientoAnioSpinner.setAdapter(adapterSpinnerAnio);
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
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        // RECYCLER VIEW
        recycleViewGeneral.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewGeneral.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewGeneral.setItemAnimator(new DefaultItemAnimator());

        botonFloating.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String fecha = null;
                fecha = auxiliarGeneral.setFormatoMes(
                        entrenamientoMesSpinner.getSelectedItem().toString()) + "-" + entrenamientoAnioSpinner.getSelectedItem().toString();
                recyclerViewCleanEntrenamiento();
                if (fecha != null && !fecha.equals(""))
                entrenamientoArray = controladorAdeful.selectListaEntrenamientoAdeful(fecha);
                if (entrenamientoArray != null) {
                    // MES ADAPTER
                    adapterSpinnerFechaEntrenamiento = new AdapterSpinnerFechaEntrenamiento(getActivity(),
                            R.layout.simple_spinner_dropdown_item, entrenamientoArray);
                    entrenamientoFechahsSpinner.setAdapter(adapterSpinnerFechaEntrenamiento);

                } else {
                auxiliarGeneral.errorDataBase(getActivity());
                }
            }
            }
        );
        entrenamientoFechahsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {

                id_entrenamiento = entrenamientoArray.get(position).getID_ENTRENAMIENTO();
                id_divisiones = controladorAdeful.selectListaIdDivisionEntrenamientoAdeful(id_entrenamiento);
                if (id_divisiones != null) {

                    arrayAsistencia = controladorAdeful.selectListaJugadoresEntrenamientoAdeful(id_divisiones, id_entrenamiento);
                    if (arrayAsistencia != null) {
                        recyclerViewLoadEntrenamiento(arrayAsistencia);
                    } else {
                auxiliarGeneral.errorDataBase(getActivity());                    }
                } else {
                 auxiliarGeneral.errorDataBase(getActivity());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                    Toast.makeText(getActivity(), "Sin datos.",
                            Toast.LENGTH_SHORT).show();
                }
        });
    }

    public void recyclerViewLoadEntrenamiento(ArrayList<Entrenamiento> arrayAsistencia) {
        adaptadorEntrenamiento = new AdaptadorRecyclerAsistencia(arrayAsistencia);
        recycleViewGeneral.setAdapter(adaptadorEntrenamiento);
    }
    public void recyclerViewCleanEntrenamiento() {
        arrayAsistencia = new ArrayList<Entrenamiento>();
        adaptadorEntrenamiento = new AdaptadorRecyclerAsistencia(arrayAsistencia);
        recycleViewGeneral.setAdapter(adaptadorEntrenamiento);

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
        // menu.getItem(8).setVisible(false);// guardar
        menu.getItem(9).setVisible(false);// Subir
        menu.getItem(10).setVisible(false); // eliminar
        menu.getItem(11).setVisible(false); // consultar
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_usuario) {

            //*Intent usuario = new Intent(getActivity(),
            //		NavigationUsuario.class);
            //	startActivity(usuario);

            return true;
        }

        if (id == R.id.action_permisos) {
            return true;
        }

        if (id == R.id.action_guardar) {


            if (entrenamientoFechahsSpinner.getSelectedItem().toString().equals("")) {
                Toast.makeText(getActivity(), "No seleccionó un entrenamiento.",
                        Toast.LENGTH_SHORT).show();
            } else {
                String usuario = "Administrador";
                String fechaCreacion = controladorAdeful.getFechaOficial();
                String fechaActualizacion = fechaCreacion;

                Boolean insert_ok = true;
                arrayAsistenciaAnterior = controladorAdeful.selectListaJugadoresEntrenamientoAdeful(id_entrenamiento);
                //obtenrmos los id de los judores checked ahora
                listaJugadoresAsistencia = adaptadorEntrenamiento.getJugadoresTrueAsistenciaList();
                if (arrayAsistenciaAnterior.size() > 0) {
                //compramos la lista nueva y la anterior
                    //si hay una checked nuevo se inserta en la base
                    for (int i = 0; i < listaJugadoresAsistencia.size(); i++) {
                        for (int j = 0; j < arrayAsistenciaAnterior.size(); j++) {
                            if (listaJugadoresAsistencia.get(i).getID_JUGADOR() == arrayAsistenciaAnterior.get(j).getID_JUGADOR()) {
                                break;
                            } else {
                                entrenamientoAsistencia = new Entrenamiento(id_entrenamiento,
                                        listaJugadoresAsistencia.get(i).getID_DIVISION(),
                                        listaJugadoresAsistencia.get(i).getID_JUGADOR());
                                if (controladorAdeful.insertAsistenciaEntrenamientoAdeful(entrenamientoAsistencia)) {
                                } else {
                                   auxiliarGeneral.errorDataBase(getActivity());
                                   insert_ok=false;
                                }
                            }
                        }
                    }
                    //comparamos la lista vieja con la nueva
                    //si hay una checked viejo la eliminamos
                    if (listaJugadoresAsistencia.size() > 0) {
                        for (int i = 0; i < arrayAsistenciaAnterior.size(); i++) {
                            for (int j = 0; j < listaJugadoresAsistencia.size(); j++) {
                                if (arrayAsistenciaAnterior.get(i).getID_JUGADOR() == listaJugadoresAsistencia.get(j).getID_JUGADOR()) {
                                    break;
                                } else {
                                    if (controladorAdeful.eliminarAsistenciaEntrenamientoAdeful(id_entrenamiento, arrayAsistenciaAnterior.get(i).getID_JUGADOR())) {
                                    } else {
                                   auxiliarGeneral.errorDataBase(getActivity());
                                   insert_ok = false;
                                    }
                                }
                            }
                        }
                    }else{
                            for (int j = 0; j < arrayAsistenciaAnterior.size(); j++) {
                                    if (controladorAdeful.eliminarAsistenciaEntrenamientoAdeful(id_entrenamiento, arrayAsistenciaAnterior.get(j).getID_JUGADOR())) {
                                    } else {
                                     auxiliarGeneral.errorDataBase(getActivity());
                                     insert_ok = false;
                                    }
                                }
                    }
               }else {
                //no hay lista anterior
                    for (int i = 0; i < listaJugadoresAsistencia.size(); i++) {
                        if (listaJugadoresAsistencia.get(i).isSelected() == true) {
                            entrenamientoAsistencia = new Entrenamiento(id_entrenamiento,
                                    listaJugadoresAsistencia.get(i).getID_DIVISION(),
                                    listaJugadoresAsistencia.get(i).getID_JUGADOR());
                            if (controladorAdeful.insertAsistenciaEntrenamientoAdeful(entrenamientoAsistencia)) {
                            } else {
                                auxiliarGeneral.errorDataBase(getActivity());
                                insert_ok = false;
                            }
                        }
                    }
                }
                if(insert_ok){
                    Toast.makeText(getActivity(), "Asistencia cargada correctamente",
                            Toast.LENGTH_SHORT).show();
                }
            }
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