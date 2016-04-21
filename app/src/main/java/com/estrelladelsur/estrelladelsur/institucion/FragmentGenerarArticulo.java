package com.estrelladelsur.estrelladelsur.institucion;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Articulo;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.webservice.JsonParsing;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentGenerarArticulo extends Fragment {

    private int CheckedPositionFragment;
    private EditText articuloEditTituto;
    private EditText articuloEditArticulo;
    private ControladorAdeful controladorAdeful;
    private boolean insertar = true;
    private Articulo articulo;
    private Communicator communicator;
    private boolean actualizar = false;
    private int idArticuloExtra;
    private AuxiliarGeneral auxiliarGeneral;
    private String GUARDAR_USUARIO = "Articulo cargado correctamente";
    private String ACTUALIZAR_USUARIO = "Articulo actualizado correctamente";
    private Typeface editTextFont;
    private Request request = new Request();
    private ProgressDialog dialog;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private JsonParsing jsonParsing = new JsonParsing();
    private static final String TAG_ID = "id";
    private String usuario = null;
    private String mensaje = null;

    public static FragmentGenerarArticulo newInstance() {
        FragmentGenerarArticulo fragment = new FragmentGenerarArticulo();
        return fragment;
    }

    public FragmentGenerarArticulo() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

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
        View v = inflater.inflate(R.layout.fragment_articulo, container, false);
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        editTextFont = auxiliarGeneral.textFont(getActivity());
        // EDITTEXT TITULO
        articuloEditTituto = (EditText) v
                .findViewById(R.id.articuloEditTituto);
        articuloEditTituto.setTypeface(editTextFont, Typeface.BOLD);
        // EDITTEXT ARTICULO
        articuloEditArticulo = (EditText) v
                .findViewById(R.id.articuloEditArticulo);
        articuloEditArticulo.setTypeface(editTextFont);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        // VER DONDE EJECUCTAR ESTA LINEA
        controladorAdeful = new ControladorAdeful(getActivity());
        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
        //Metodo Extra
        if (actualizar) {
            idArticuloExtra = getActivity().getIntent().getIntExtra("id_articulo", 0);
            articuloEditTituto.setText(getActivity().getIntent()
                    .getStringExtra("titulo"));
            articuloEditArticulo.setText(getActivity().getIntent()
                    .getStringExtra("articulo"));
            insertar = false;
        }
    }

    public void inicializarControles(String mensaje) {
        articuloEditTituto.setText("");
        articuloEditArticulo.setText("");
        communicator.refresh();
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void envioWebService(int tipo) {
        request.setMethod("POST");
            request.setParametrosDatos("titulo", articulo.getTITULO());
            request.setParametrosDatos("articulo", articulo.getARTICULO());
            request.setParametrosDatos("fecha_actualizacion", articulo.getFECHA_ACTUALIZACION());

            if (tipo == 0) {
                request.setQuery("SUBIR");
                request.setParametrosDatos("usuario_creador", articulo.getUSUARIO_CREADOR());
                request.setParametrosDatos("fecha_creacion", articulo.getFECHA_CREACION());
            }else{
                request.setQuery("EDITAR");
                request.setParametrosDatos("id_articulo", String.valueOf(articulo.getID_ARTICULO()));
                request.setParametrosDatos("usuario_actualizacion", articulo.getUSUARIO_ACTUALIZACION());
            }
            new TaskArticulo().execute(request);
    }

    // enviar/editar articulo

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
                    mensaje =json.getString(TAG_MESSAGE);
                    if (success == 0) {
                        if (insertar) {
                            int id = json.getInt(TAG_ID);
                            if (id > 0) {
                                if (controladorAdeful.insertArticuloAdeful(id, articulo)) {
                                    precessOK = true;
                                } else {
                                    precessOK = false;
                                }
                            } else {
                                precessOK = false;
                            }
                        } else {
                            if (controladorAdeful.actualizarArticuloAdeful(articulo)) {
                                precessOK = true;
                            }else{
                                precessOK = false;
                            }
                        }
                        precessOK = true;
                    } else {
                        precessOK = false;
                    }
                } else {
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
                if (insertar) {
                    inicializarControles(GUARDAR_USUARIO);
                } else {
                    actualizar = false;
                    insertar = true;
                    inicializarControles(ACTUALIZAR_USUARIO);
                }
            } else {
                auxiliarGeneral.errorWebService(getActivity(), mensaje);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        // menu.getItem(8).setVisible(false);// guardar
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

        if (id == R.id.action_guardar) {

            if (articuloEditTituto.getText().toString().equals("") || articuloEditArticulo.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Debe completar todos los campos.",
                        Toast.LENGTH_SHORT).show();
            } else if (insertar) {
                articulo = new Articulo(0, articuloEditTituto.getText().toString(),
                        articuloEditArticulo.getText().toString(),
                        usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());
                envioWebService(0);
            } else { //ACTUALIZAR ACTUALIZAR
                articulo = new Articulo(idArticuloExtra, articuloEditTituto.getText().toString(),
                        articuloEditArticulo.getText().toString(),
                        null, null, usuario, auxiliarGeneral.getFechaOficial());
                envioWebService(1);
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