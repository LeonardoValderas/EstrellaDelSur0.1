package com.estrelladelsur.estrelladelsur.institucion.adeful;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Toast;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerComision;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.webservice.JsonParsing;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentEditarComision extends Fragment {

    private int CheckedPositionFragment,posicion = 0;
    private RecyclerView recyclerComision;
    private ControladorAdeful controladorAdeful;
    private ArrayList<Comision> comisionArray;
    private AdaptadorRecyclerComision adaptadorRecyclerComision;
    private DialogoAlerta dialogoAlerta;
    private AuxiliarGeneral auxiliarGeneral;
    private ProgressDialog dialog;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String ELIMINAR_COMISION = "Integrante eliminado correctamente";
    private JsonParsing jsonParsing = new JsonParsing(getActivity());
    private String nombre_foto = null, URL = null, mensaje = null;
    private Request request = new Request();

    public static FragmentEditarComision newInstance() {
        FragmentEditarComision fragment = new FragmentEditarComision();
        return fragment;
    }
    public FragmentEditarComision() {
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

        View v = inflater.inflate(R.layout.fragment_editar_recyclerview, container, false);

        // RECYCLER ARTICULO
        recyclerComision = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);

        return v;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }
    private void init() {
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        recyclerComision.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerComision.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerComision.setItemAnimator(new DefaultItemAnimator());

        recyclerViewLoadComision();

        recyclerComision.addOnItemTouchListener(new
                RecyclerTouchListener(getActivity(),
                recyclerComision, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                // TODO Auto-generated method stub

                Intent editarComision = new Intent(getActivity(),
                        TabsComision.class);
                editarComision.putExtra("actualizar", true);
                editarComision.putExtra("id_comision",
                        comisionArray.get(position).getID_COMISION());
                startActivity(editarComision);
            }

            @Override
            public void onLongClick(View view, final int position) {
                // TODO Auto-generated method stub


                dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                        "Desea eliminar el integrante?", null, null);
                dialogoAlerta.btnAceptar.setText("Aceptar");
                dialogoAlerta.btnCancelar.setText("Cancelar");

                dialogoAlerta.btnAceptar
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                posicion = comisionArray.get(position)
                                        .getID_COMISION();
                                nombre_foto = comisionArray.get(position)
                                        .getNOMBRE_FOTO();
                                envioWebService();
//                                if (controladorAdeful.eliminarComisionAdeful(comisionArray.get(position)
//                                        .getID_COMISION())) {
//                                    recyclerViewLoadComision();
//
//                                    Toast.makeText(
//                                            getActivity(),
//                                            "Integrante eliminado correctamente",
//                                            Toast.LENGTH_SHORT).show();
//
//                                    dialogoAlerta.alertDialog.dismiss();
//                                } else {
//                        auxiliarGeneral.errorDataBase(getActivity());
//                                }
                            }
                        });
                dialogoAlerta.btnCancelar
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                dialogoAlerta.alertDialog.dismiss();
                            }
                        });
            }
        }));
    }
    public void envioWebService() {
        request.setMethod("POST");
        request.setParametrosDatos("id_comision", String.valueOf(posicion));
        request.setParametrosDatos("nombre_foto", nombre_foto);
        request.setParametrosDatos("fecha_actualizacion", auxiliarGeneral.getFechaOficial());
        URL = null;
        URL = auxiliarGeneral.getURLCOMISIONADEFULALL();
        URL = URL + auxiliarGeneral.getDeletePHP("Comision");

        new TaskComision().execute(request);
    }
    public void inicializarControles(String mensaje) {
        recyclerViewLoadComision();
        posicion = 0;
        nombre_foto.isEmpty();
        dialogoAlerta.alertDialog.dismiss();
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }
    public class TaskComision extends AsyncTask<Request, Boolean, Boolean> {
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Procesando...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Request... params) {
            int success;
            JSONObject json = null;
            boolean precessOK = true;
            try {
                json = jsonParsing.parsingJsonObject(params[0],URL);
                if (json != null) {
                    success = json.getInt(TAG_SUCCESS);
                    mensaje =json.getString(TAG_MESSAGE);
                    if (success == 0) {
                        if (controladorAdeful.eliminarComisionAdeful(posicion)) {
                            precessOK = true;
                        } else {
                            precessOK = false;
                        }
                    } else {
                        precessOK = false;
                    }
                }else {
                    precessOK = false;
                    mensaje = "Error(4). Por favor comuniquese con el administrador.";
                }
            } catch (JSONException e) {
                precessOK = false;
                mensaje = "Error(5). Por favor comuniquese con el administrador.";
            }
            return precessOK;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();

            if (result) {
                inicializarControles(ELIMINAR_COMISION);
            } else {
                auxiliarGeneral.errorWebService(getActivity(), mensaje);
            }
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public void recyclerViewLoadComision() {
        comisionArray = controladorAdeful.selectListaComisionAdeful();
        if(comisionArray != null) {
            adaptadorRecyclerComision = new AdaptadorRecyclerComision(comisionArray,getActivity());
            recyclerComision.setAdapter(adaptadorRecyclerComision);
        }else{
           auxiliarGeneral.errorDataBase(getActivity());
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
        menu.getItem(8).setVisible(false);// guardar
        menu.getItem(9).setVisible(false);// Subir
        menu.getItem(10).setVisible(false); // eliminar
        menu.getItem(11).setVisible(false); // consultar
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        // noinspection SimplifiableIfStatement
        if (id == R.id.action_usuario) {

            /*Intent usuario = new Intent(getActivity(),
                    NavigationDrawerUsuario.class);
            startActivity(usuario);*/

            return true;
        }

        if (id == R.id.action_permisos) {
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

