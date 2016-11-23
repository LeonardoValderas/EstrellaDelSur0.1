package com.estrelladelsur.estrelladelsur.institucion.administrador;

import android.graphics.Typeface;
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
import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGeneric;
import com.estrelladelsur.estrelladelsur.webservice.Request;


public class FragmentGenerarArticuloAdeful extends Fragment implements MyAsyncTaskListener {

    private int CheckedPositionFragment;
    private EditText articuloEditTituto;
    private EditText articuloEditArticulo;
    private ControladorAdeful controladorAdeful;
    private boolean insertar = true;
    private Articulo articulo;
    private CommunicatorAdeful communicator;
    private boolean actualizar = false;
    private int idArticuloExtra;
    private AuxiliarGeneral auxiliarGeneral;
    private Typeface editTextFont;
    private Request request = new Request();
    private String usuario = null;
    private String URL = null;

    public static FragmentGenerarArticuloAdeful newInstance() {
        FragmentGenerarArticuloAdeful fragment = new FragmentGenerarArticuloAdeful();
        return fragment;
    }

    public FragmentGenerarArticuloAdeful() {
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        communicator = (CommunicatorAdeful) getActivity();
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
        communicator.refreshAdeful();
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void cargarEntidad(int id) {
        articulo = new Articulo(id, articuloEditTituto.getText().toString(),
                articuloEditArticulo.getText().toString(),
                usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());
        URL = null;
        URL = auxiliarGeneral.getURLARTICULOADEFULALL();
        envioWebService();
    }

    public void envioWebService() {
        request.setMethod("POST");
        request.setParametrosDatos("titulo", articulo.getTITULO());
        request.setParametrosDatos("articulo", articulo.getARTICULO());

        if (insertar) {
            request.setParametrosDatos("usuario_creador", articulo.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", articulo.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Articulo");

        } else {
            request.setParametrosDatos("id_articulo", String.valueOf(articulo.getID_ARTICULO()));
            request.setParametrosDatos("usuario_actualizacion", articulo.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", articulo.getFECHA_ACTUALIZACION());
            URL = URL + auxiliarGeneral.getUpdatePHP("Articulo");
        }
        new AsyncTaskGeneric(getActivity(), this, URL, request, "Articulo", articulo, insertar, "o");
    }

    @Override
    public void onPostExecuteConcluded(boolean result, String mensaje) {
        if (result) {
            if (!insertar) {
                actualizar = false;
                insertar = true;
            }
            inicializarControles(mensaje);
        } else {
            auxiliarGeneral.errorWebService(getActivity(), mensaje);
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

            if (articuloEditTituto.getText().toString().equals("") || articuloEditArticulo.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Debe completar todos los campos.",
                        Toast.LENGTH_SHORT).show();
            } else if (insertar) {
                cargarEntidad(0);
            } else { //ACTUALIZAR ACTUALIZAR
                cargarEntidad(idArticuloExtra);
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