package com.estrelladelsur.estrelladelsur.miequipo.administrador.general;

import android.content.Context;
import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerAsistencia;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerFechaEntrenamiento;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerAnio;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerMes;
import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Entrenamiento;
import com.estrelladelsur.estrelladelsur.entidad.EntrenamientoRecycler;
import com.estrelladelsur.estrelladelsur.entidad.Mes;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGeneric;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentAsistencia extends Fragment implements MyAsyncTaskListener {

    private Spinner entrenamientoAnioSpinner;
    private Spinner entrenamientoMesSpinner;
    private Spinner entrenamientoDivisionSpinner;
    private Spinner entrenamientoFechahsSpinner;
    private ControladorAdeful controladorAdeful;
    private RecyclerView recycleViewGeneral;
    private int CheckedPositionFragment;
    private TextView textViewJuagdor, textViewAsistencia;
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
    private AuxiliarGeneral auxiliarGeneral;
    private ArrayList<Entrenamiento> listaJugadoresAsistencia;
    private ArrayList<Integer> id_divisiones;
    private int id_entrenamiento;
    private Typeface editTextFont;
    private ArrayList<Integer> id_divisin_array = null, id_jugador_add_array = null, id_jugador_delete_array = null;
    private Request request;
    private String URL = null, usuario = null;
    private Entrenamiento entrenamiento;

    public static FragmentAsistencia newInstance() {
        FragmentAsistencia fragment = new FragmentAsistencia();
        return fragment;
    }

    public FragmentAsistencia() {
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
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        editTextFont = auxiliarGeneral.textFont(getActivity());
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
        entrenamientoFechahsSpinner.setVisibility(View.INVISIBLE);
        // DIVISION
        entrenamientoDivisionSpinner = (Spinner) v
                .findViewById(R.id.entrenamientoDivisionSpinner);
        //BOTON FLOATING
        botonFloating = (FloatingActionButton) v
                .findViewById(R.id.botonFloating);

        textViewJuagdor = (TextView) v
                .findViewById(R.id.textViewJuagdor);
        textViewJuagdor.setTypeface(editTextFont);
        textViewAsistencia = (TextView) v
                .findViewById(R.id.textViewAsistencia);
        textViewAsistencia.setTypeface(editTextFont);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
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
                                                     if (entrenamientoArray.isEmpty()) {
                                                         entrenamientoFechahsSpinner.setVisibility(View.INVISIBLE);
                                                         showMessage("Selección sin datos");
                                                     } else {
                                                         entrenamientoFechahsSpinner.setVisibility(View.VISIBLE);
                                                         // MES ADAPTER
                                                         adapterSpinnerFechaEntrenamiento = new AdapterSpinnerFechaEntrenamiento(getActivity(),
                                                                 R.layout.simple_spinner_dropdown_item, entrenamientoArray);
                                                         entrenamientoFechahsSpinner.setAdapter(adapterSpinnerFechaEntrenamiento);
                                                     }
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
                        if (arrayAsistencia.isEmpty())
                            showMessage("Selección sin datos");
                    } else {
                        auxiliarGeneral.errorDataBase(getActivity());
                    }
                } else {
                    auxiliarGeneral.errorDataBase(getActivity());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                showMessage("Selección sin datos");
            }
        });
    }

    public void recyclerViewLoadEntrenamiento(ArrayList<Entrenamiento> arrayAsistencia) {
        adaptadorEntrenamiento = new AdaptadorRecyclerAsistencia(arrayAsistencia, getActivity());
        recycleViewGeneral.setAdapter(adaptadorEntrenamiento);
    }

    public void recyclerViewCleanEntrenamiento() {
        arrayAsistencia = new ArrayList<>();
        adaptadorEntrenamiento = new AdaptadorRecyclerAsistencia(arrayAsistencia, getActivity());
        recycleViewGeneral.setAdapter(adaptadorEntrenamiento);

    }

    public void cargarEntidad(int id, ArrayList<Integer> jugadorArrayAdd, ArrayList<Integer> jugadorArrayDelete) throws JSONException {
        URL = null;
        URL = auxiliarGeneral.getURLENTRENAMIENTOASISTENCIAADEFULALL();

        entrenamiento = new Entrenamiento(id,
                jugadorArrayAdd,
                jugadorArrayDelete,
                usuario, auxiliarGeneral.getFechaOficial());

        envioWebService();
    }

    public void envioWebService() throws JSONException {

        JSONArray jugadorArrayAdd = new JSONArray();
        JSONArray jugadorArrayDelete = new JSONArray();
        request = new Request();
        request.setMethod("POST");

        request.setParametrosDatos("id_entrenamiento", String.valueOf(entrenamiento.getID_ENTRENAMIENTO()));
        if (entrenamiento.getJugadorArrayAdd() != null) {
            for (int i = 0; i < entrenamiento.getJugadorArrayAdd().size(); i++) {

                JSONObject jugadorIds = new JSONObject();
                jugadorIds.put("jugador", String.valueOf(entrenamiento.getJugadorArrayAdd().get(i)));
                jugadorArrayAdd.put(jugadorIds);
            }
        }
        request.setParametrosDatos("jugador", jugadorArrayAdd.toString());

        if (entrenamiento.getJugadorArrayDelete() != null) {
            for (int i = 0; i < entrenamiento.getJugadorArrayDelete().size(); i++) {
                JSONObject jugadorIds = new JSONObject();
                jugadorIds.put("delete", String.valueOf(entrenamiento.getJugadorArrayDelete().get(i)));
                jugadorArrayDelete.put(jugadorIds);
            }
        }
        request.setParametrosDatos("delete", jugadorArrayDelete.toString());

        request.setParametrosDatos("usuario_creador", entrenamiento.getUSUARIO_CREADOR());
        request.setParametrosDatos("fecha_creacion", entrenamiento.getFECHA_CREACION());
        URL = URL + auxiliarGeneral.getInsertPHP("Asistencia");

        new AsyncTaskGeneric(getContext(), this, URL, request, "Asistencia", entrenamiento, true, "a");
    }

    public void showMessage(String msg) {
        Toast.makeText(
                getActivity(),
                msg,
                Toast.LENGTH_SHORT).show();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPostExecuteConcluded(boolean result, String mensaje) {
        if (result) {
            recyclerViewCleanEntrenamiento();
            showMessage(mensaje);
        } else
            auxiliarGeneral.errorWebService(getActivity(), mensaje);

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
            // TODO Auto-generated method stub
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_administrador_general, menu);
        // menu.getItem(0).setVisible(false);//usuario
        menu.getItem(1).setVisible(false);// posicion
        menu.getItem(2).setVisible(false);// cargo
        // menu.getItem(3).setVisible(false);//cerrar
        // menu.getItem(4).setVisible(false);// guardar
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

        if (id == R.id.action_guardar) {


            if (entrenamientoFechahsSpinner.getSelectedItem().toString().equals("")) {
                Toast.makeText(getActivity(), "No seleccionó un entrenamiento.",
                        Toast.LENGTH_SHORT).show();
            } else {

                boolean isAdd = false;
                boolean isDelete = false;
                id_jugador_delete_array = new ArrayList<>();
                id_jugador_add_array = new ArrayList<>();

                arrayAsistenciaAnterior = controladorAdeful.selectListaJugadoresEntrenamientoAdeful(id_entrenamiento);
                //obtenrmos los id de los judores checked ahora
                listaJugadoresAsistencia = adaptadorEntrenamiento.getJugadoresTrueAsistenciaList();
                if (arrayAsistenciaAnterior.size() > 0) {
                    //compramos la lista nueva y la anterior
                    //si hay una checked nuevo se inserta en la base
                    for (int i = 0; i < listaJugadoresAsistencia.size(); i++) {
                        for (int j = 0; j < arrayAsistenciaAnterior.size(); j++) {
                            if (listaJugadoresAsistencia.get(i).getID_JUGADOR() == arrayAsistenciaAnterior.get(j).getID_JUGADOR()) {
                                isAdd = false;
                                break;
                            } else {
                                isAdd = true;
                            }
                        }
                        if (isAdd)
                            id_jugador_add_array.add(listaJugadoresAsistencia.get(i).getID_JUGADOR());
                    }
                    //comparamos la lista vieja con la nueva
                    //si hay una checked viejo la eliminamos
                    if (listaJugadoresAsistencia.size() > 0) {
                        for (int i = 0; i < arrayAsistenciaAnterior.size(); i++) {
                            for (int j = 0; j < listaJugadoresAsistencia.size(); j++) {
                                if (arrayAsistenciaAnterior.get(i).getID_JUGADOR() == listaJugadoresAsistencia.get(j).getID_JUGADOR()) {
                                    isDelete = false;
                                    break;
                                } else {
                                    isDelete = true;
                                }
                            }
                            if (isDelete)
                                id_jugador_delete_array.add(arrayAsistenciaAnterior.get(i).getID_JUGADOR());
                        }
                    } else {
                        for (int j = 0; j < arrayAsistenciaAnterior.size(); j++) {
                            id_jugador_delete_array.add(arrayAsistenciaAnterior.get(j).getID_JUGADOR());
                        }
                    }
                } else {
                    //no hay lista anterior
                    for (int i = 0; i < listaJugadoresAsistencia.size(); i++) {
                        if (listaJugadoresAsistencia.get(i).isSelected() == true) {
                            id_jugador_add_array.add(listaJugadoresAsistencia.get(i).getID_JUGADOR());
                        }
                    }
                }

                try {
                    cargarEntidad(id_entrenamiento, id_jugador_add_array, id_jugador_delete_array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        if (id == android.R.id.home) {

            NavUtils.navigateUpFromSameTask(getActivity());

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}