package com.estrelladelsur.estrelladelsur.permiso;

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

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerEditarPermiso;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Permiso;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.navegador.administrador.Navigation;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericAdeful;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentEditarPermiso extends Fragment implements MyAsyncTaskListener {

    private Spinner entrenamientoAnioSpinner;
    private Spinner entrenamientoMesSpinner;
    private ControladorGeneral controladorGeneral;
    private RecyclerView recycleViewGeneral;
    private int CheckedPositionFragment;
    private FloatingActionButton botonFloating;
    private ArrayList<Permiso> permisoArray;
    private AdaptadorRecyclerEditarPermiso adaptadorPermiso;
    private DialogoAlerta dialogoAlerta;
    private AuxiliarGeneral auxiliarGeneral;
    private Communicator communicator;
    private String usuarioCreador = null;
    private Request request;
    private String URL = null;
    private Permiso permiso;

    public static FragmentEditarPermiso newInstance() {
        FragmentEditarPermiso fragment = new FragmentEditarPermiso();
        return fragment;
    }

    public FragmentEditarPermiso() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        controladorGeneral = new ControladorGeneral(getActivity());
        communicator = (Communicator) getActivity();
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
        entrenamientoAnioSpinner.setVisibility(View.GONE);
        // DIA
        entrenamientoMesSpinner = (Spinner) v
                .findViewById(R.id.entrenamientoMesSpinner);
        entrenamientoMesSpinner.setVisibility(View.GONE);
        //BOTON FLOATING
        botonFloating = (FloatingActionButton) v
                .findViewById(R.id.botonFloating);
        botonFloating.setVisibility(View.GONE);
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
        controladorGeneral = new ControladorGeneral(getActivity());
        communicator = (Communicator) getActivity();
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        usuarioCreador = auxiliarGeneral.getUsuarioPreferences(getActivity());
        recyclerViewLoadPermiso();
    }

    private void init() {

        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        usuarioCreador = auxiliarGeneral.getUsuarioPreferences(getActivity());
        // RECYCLER VIEW
        recycleViewGeneral.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewGeneral.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewGeneral.setItemAnimator(new DefaultItemAnimator());
        recyclerViewLoadPermiso();


        recycleViewGeneral.addOnItemTouchListener(new RecyclerTouchListener(
                getActivity(), recycleViewGeneral, new ClickListener() {

            @Override
            public void onLongClick(View view, final int position) {

                dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                        "Desea eliminar el permiso?", null, null);
                dialogoAlerta.btnAceptar.setText("Aceptar");
                dialogoAlerta.btnCancelar.setText("Cancelar");

                dialogoAlerta.btnAceptar
                        .setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View v) {
                                                    boolean isSalving = true;
                                                    int id_permiso = permisoArray.get(position).getID_PERMISO();
                                                    ArrayList<Integer> idSubmodulo = controladorGeneral.selectListaIdModulosId(id_permiso);

                                                    if (idSubmodulo == null) {
                                                        auxiliarGeneral.errorDataBase(getActivity());
                                                    } else {

                                                        try {
                                                            cargarEntidad(id_permiso, idSubmodulo);
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            }
                        );
                dialogoAlerta.btnCancelar
                        .setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialogoAlerta.alertDialog.dismiss();
                                                }
                                            }
                        );
            }

            @Override
            public void onClick(View view, int position) {
                // TODO Auto-generated method stub

                Intent editarPermiso = new Intent(getActivity(),
                        TabsPermiso.class);
                editarPermiso.putExtra("actualizar", true);
                editarPermiso.putExtra("id_usuario",
                        permisoArray.get(position).getID_USUARIO());
                editarPermiso.putExtra("id_permiso",
                        permisoArray.get(position).getID_PERMISO());
                startActivity(editarPermiso);
            }
        }
        ));
    }

    public void recyclerViewLoadPermiso() {
        permisoArray = controladorGeneral.selectListaPermiso();
        if (permisoArray != null) {
            adaptadorPermiso = new AdaptadorRecyclerEditarPermiso(permisoArray, getActivity());
            recycleViewGeneral.setAdapter(adaptadorPermiso);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    public void cargarEntidad(int id, ArrayList<Integer> subModulosDelte) throws JSONException {
        URL = null;
        URL = auxiliarGeneral.getURLPERMISOALL();

        permiso = new Permiso(id, subModulosDelte, auxiliarGeneral.getFechaOficial());


        envioWebService();
    }

    public void envioWebService() throws JSONException {

        JSONArray subModuloArrayDelete = new JSONArray();
        String fecha = permiso.getFECHA_ACTUALIZACION();
        request = new Request();
        request.setMethod("POST");

        if (permiso.getSubModulosdelete() != null) {
            for (int i = 0; i < permiso.getSubModulosdelete().size(); i++) {

                JSONObject submoduloIds = new JSONObject();
                submoduloIds.put("submoduloDelete", String.valueOf(permiso.getSubModulosdelete().get(i)));
                subModuloArrayDelete.put(submoduloIds);
            }
        }
        request.setParametrosDatos("submoduloDelete", subModuloArrayDelete.toString());
        request.setParametrosDatos("id_permiso", String.valueOf(permiso.getID_PERMISO()));
        request.setParametrosDatos("fecha_actualizacion", fecha);
        URL = URL + auxiliarGeneral.getDeletePHP("Permiso");


        new AsyncTaskGenericAdeful(getContext(), this, URL, request, "Permiso", permiso, true, "o", true);
    }

    public void inicializarControles(String mensaje) {
        recyclerViewLoadPermiso();
        communicator.refreshDelete();
        dialogoAlerta.alertDialog.dismiss();
        Toast.makeText(
                getActivity(),
                mensaje,
                Toast.LENGTH_SHORT).show();
        Intent nav = new Intent(getActivity(), Navigation.class);
        nav.putExtra("usuario", usuarioCreador);
        startActivity(nav);
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