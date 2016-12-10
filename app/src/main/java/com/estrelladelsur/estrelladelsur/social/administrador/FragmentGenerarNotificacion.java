package com.estrelladelsur.estrelladelsur.social.administrador;

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
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Notificacion;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericAdeful;
import com.estrelladelsur.estrelladelsur.webservice.Request;


public class FragmentGenerarNotificacion extends Fragment implements MyAsyncTaskListener {

    private int CheckedPositionFragment;
    private EditText notificacionEditTituto;
    private EditText notificacionEditArticulo;
    private boolean insertar = true;
    private Notificacion notificacion;
    private Communicator communicator;
    private boolean actualizar = false;
    private int idNotificacionExtra;
    private AuxiliarGeneral auxiliarGeneral;
    private Typeface editTextFont;
    private String URL = null, usuario = null;
    private Request request;

    public static FragmentGenerarNotificacion newInstance() {
        FragmentGenerarNotificacion fragment = new FragmentGenerarNotificacion();
        return fragment;
    }

    public FragmentGenerarNotificacion() {
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
        notificacionEditTituto = (EditText) v
                .findViewById(R.id.articuloEditTituto);
        notificacionEditTituto.setTypeface(editTextFont, Typeface.BOLD);
        // EDITTEXT NOTIFICACIO
        notificacionEditArticulo = (EditText) v
                .findViewById(R.id.articuloEditArticulo);
        notificacionEditArticulo.setHint("NOTIFICACION");
        notificacionEditArticulo.setTypeface(editTextFont);
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
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
    }

    private void init() {
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        //Metodo Extra
        if (actualizar) {

            idNotificacionExtra = getActivity().getIntent().getIntExtra("id_notificacion", 0);
            notificacionEditTituto.setText(getActivity().getIntent()
                    .getStringExtra("titulo"));
            notificacionEditArticulo.setText(getActivity().getIntent()
                    .getStringExtra("notificacion"));
            insertar = false;
        }
    }

    public void inicializarControles(String mensaje) {
        notificacionEditTituto.setText("");
        notificacionEditArticulo.setText("");
        communicator.refresh();
        showMessage(mensaje);
    }

    public void showMessage(String message) {
        Toast.makeText(getActivity(), message,
                Toast.LENGTH_SHORT).show();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void cargarEntidad(int id) {
        URL = null;
        URL = auxiliarGeneral.getURLNOTIFICACIONALL();

        notificacion = new Notificacion(id, notificacionEditTituto.getText().toString(),
                notificacionEditArticulo.getText().toString(),
                usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());

        envioWebService();
    }

    public void envioWebService() {
        request = new Request();
        request.setMethod("POST");
        request.setParametrosDatos("titulo", notificacion.getTITULO());
        request.setParametrosDatos("notificacion", notificacion.getNOTIFICACION());

        if (insertar) {
            request.setParametrosDatos("usuario_creador", notificacion.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", notificacion.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Notificacion");

        } else {
            request.setParametrosDatos("id_notificacion", String.valueOf(notificacion.getID_NOTIFICACION()));
            request.setParametrosDatos("usuario_actualizacion", notificacion.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", notificacion.getFECHA_ACTUALIZACION());
            URL = URL + auxiliarGeneral.getUpdatePHP("Notificacion");
        }

        new AsyncTaskGenericAdeful(getActivity(), this, URL, request, "Notificacion", notificacion, insertar, "a");
    }

    @Override
    public void onPostExecuteConcluded(boolean result, String mensaje) {
        if (result) {
            if (insertar) {
                inicializarControles(mensaje);
            } else {
                actualizar = false;
                insertar = true;
                inicializarControles(mensaje);
            }
        } else {
            auxiliarGeneral.errorWebService(getActivity(), mensaje);
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
        // noinspection SimplifiableIfStatement
        if (id == R.id.action_usuario) {
            auxiliarGeneral.goToUser(getActivity());
            return true;
        }
        if (id == R.id.action_cerrar) {
            auxiliarGeneral.close(getActivity());
        }

        if (id == R.id.action_guardar) {

            if (notificacionEditTituto.getText().toString().equals("") || notificacionEditArticulo.getText().toString().equals("")) {
                showMessage("Debe completar todos los campos.");
            } else if (insertar) {
                cargarEntidad(0);
            } else {
                cargarEntidad(idNotificacionExtra);
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