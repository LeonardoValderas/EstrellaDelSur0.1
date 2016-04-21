package com.estrelladelsur.estrelladelsur.institucion;

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
import com.estrelladelsur.estrelladelsur.entidad.Articulo;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerArticulo;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.webservice.JsonParsing;
import com.estrelladelsur.estrelladelsur.webservice.Request;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class FragmentEditarArticulo extends Fragment {

    private int CheckedPositionFragment;
    private RecyclerView recyclerArticulo;
    private ControladorAdeful controladorAdeful;
    private ArrayList<Articulo> articuloArray;
    private AdaptadorRecyclerArticulo adaptadorRecyclerArticulo;
    private DialogoAlerta dialogoAlerta;
    private AuxiliarGeneral auxiliarGeneral;
    private ProgressDialog dialog;
    private JsonParsing jsonParsing = new JsonParsing();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String mensaje = null;
    private String ELIMINAR_ARTICULO = "Articulo eliminado correctamente";
    private int posicion = 0;
    private Request request = new Request();

    public static FragmentEditarArticulo newInstance() {
        FragmentEditarArticulo fragment = new FragmentEditarArticulo();
        return fragment;
    }

    public FragmentEditarArticulo() {
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
        recyclerArticulo = (RecyclerView) v
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
        recyclerArticulo.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerArticulo.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerArticulo.setItemAnimator(new DefaultItemAnimator());

        recyclerViewLoadArticulo();

        recyclerArticulo.addOnItemTouchListener(new
                RecyclerTouchListener(getActivity(),
                recyclerArticulo, new ClickListener() {

            @Override
            public void onClick(View view, int position) {

                Intent editarArticulo = new Intent(getActivity(),
                        TabsArticulo.class);
                editarArticulo.putExtra("actualizar", true);
                editarArticulo.putExtra("id_articulo",
                        articuloArray.get(position).getID_ARTICULO());
                editarArticulo.putExtra("titulo", articuloArray.get(position).getTITULO());
                editarArticulo.putExtra("articulo", articuloArray.get(position).getARTICULO());
                editarArticulo.putExtra("creador", articuloArray.get(position).getUSUARIO_CREADOR());
                editarArticulo.putExtra("fecha_creacion", articuloArray.get(position).getFECHA_CREACION());
                editarArticulo.putExtra("fecha_actualizacion", articuloArray.get(position).getFECHA_ACTUALIZACION());

                startActivity(editarArticulo);
            }

            @Override
            public void onLongClick(View view, final int position) {

                dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                        "Desea eliminar el articulo?", null, null);
                dialogoAlerta.btnAceptar.setText("Aceptar");
                dialogoAlerta.btnCancelar.setText("Cancelar");

                dialogoAlerta.btnAceptar
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                posicion = articuloArray.get(position)
                                        .getID_ARTICULO();
                                envioWebService();
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void recyclerViewLoadArticulo() {
        articuloArray = controladorAdeful.selectListaArticuloAdeful();
        if(articuloArray!= null) {
            adaptadorRecyclerArticulo = new AdaptadorRecyclerArticulo(articuloArray,getActivity());
            recyclerArticulo.setAdapter(adaptadorRecyclerArticulo);
        }else{
        auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    public void inicializarControles(String mensaje) {
        recyclerViewLoadArticulo();
        posicion = 0;
        dialogoAlerta.alertDialog.dismiss();
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void envioWebService() {
        request.setMethod("POST");
        request.setQuery("ELIMINAR");
        request.setParametrosDatos("id_articulo", String.valueOf(posicion));
        new TaskArticulo().execute(request);
    }
    // enviar/editar/eliminar articulo

    public class TaskArticulo extends AsyncTask<Request, Boolean, Boolean> {
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
                json = jsonParsing.parsingArticulo(params[0]);
                if (json != null) {
                    success = json.getInt(TAG_SUCCESS);
                    mensaje = json.getString(TAG_MESSAGE);
                    if (success == 0) {
                        if (controladorAdeful.eliminarArticuloAdeful(posicion)) {
                         precessOK = true;
                        } else {
                            precessOK = false;
                        }
                    } else {
                        precessOK = false;
                    }
                }else {
                    precessOK = false;
                    mensaje = "Error(4). Por favor comuniquese con soporte.";
                }
            } catch (JSONException e) {
                precessOK = false;
                mensaje = "Error(5). Por favor comuniquese con soporte.";
            }
            return precessOK;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();

            if (result) {
            inicializarControles(ELIMINAR_ARTICULO);
            } else {
                auxiliarGeneral.errorWebService(getActivity(), mensaje);
            }
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
            // TODO Auto-generated method stub

        }

    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_administrador_general, menu);
        // menu.getItem(0).setVisible(false);//usuario
        menu.getItem(1).setVisible(false);//permiso
        menu.getItem(2).setVisible(false);//lifuba
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

