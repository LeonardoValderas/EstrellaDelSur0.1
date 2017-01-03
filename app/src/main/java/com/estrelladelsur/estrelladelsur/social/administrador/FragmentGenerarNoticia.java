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
import com.estrelladelsur.estrelladelsur.entidad.Noticia;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericAdeful;
import com.estrelladelsur.estrelladelsur.webservice.Request;

public class FragmentGenerarNoticia extends Fragment implements MyAsyncTaskListener {

    private int CheckedPositionFragment;
    private EditText noticiaEditTituto;
    private EditText noticiaEditArticulo;
    private EditText noticiaEditLink;
    private boolean insertar = true;
    private Noticia noticia;
    private Communicator communicator;
    private boolean actualizar = false;
    private int idNoticianExtra;
    private AuxiliarGeneral auxiliarGeneral;
    private Typeface editTextFont;
    private String URL = null, usuario = null;
    private Request request;

    public static FragmentGenerarNoticia newInstance() {
        FragmentGenerarNoticia fragment = new FragmentGenerarNoticia();
        return fragment;
    }

    public FragmentGenerarNoticia() {
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
        noticiaEditTituto = (EditText) v
                .findViewById(R.id.articuloEditTituto);
        noticiaEditTituto.setTypeface(editTextFont, Typeface.BOLD);
        // EDITTEXT NOTIFICACIO
        noticiaEditArticulo = (EditText) v
                .findViewById(R.id.articuloEditArticulo);
        noticiaEditArticulo.setHint("DESCRIPCION");
        noticiaEditArticulo.setTypeface(editTextFont);
        // EDITTEXT LINK
        noticiaEditLink = (EditText) v
                .findViewById(R.id.noticiaEditLink);
        noticiaEditLink.setVisibility(View.VISIBLE);
        noticiaEditLink.setTypeface(editTextFont);

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

            idNoticianExtra = getActivity().getIntent().getIntExtra("id_noticia", 0);
            noticiaEditTituto.setText(getActivity().getIntent()
                    .getStringExtra("titulo"));
            noticiaEditArticulo.setText(getActivity().getIntent()
                    .getStringExtra("descripcion"));
            noticiaEditLink.setText(getActivity().getIntent()
                    .getStringExtra("link"));
            insertar = false;
        }
    }

    public void inicializarControles(String mensaje) {
        noticiaEditTituto.setText("");
        noticiaEditArticulo.setText("");
        noticiaEditLink.setText("");
        communicator.refresh();
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void cargarEntidad(int id) {
        URL = null;
        URL = auxiliarGeneral.getURLNOTICIAALL();

        noticia = new Noticia(id, noticiaEditTituto.getText().toString(), noticiaEditArticulo.getText().toString(),
                noticiaEditLink.getText().toString(),
                usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());

        envioWebService();
    }

    public void envioWebService() {
        request = new Request();
        request.setMethod("POST");
        request.setParametrosDatos("titulo", noticia.getTITULO());
        request.setParametrosDatos("descripcion", noticia.getDESCRIPCION());
        request.setParametrosDatos("link", noticia.getLINK());
        if (insertar) {
            request.setParametrosDatos("usuario_creador", noticia.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", noticia.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Noticia");

        } else {
            request.setParametrosDatos("id_noticia", String.valueOf(noticia.getID_NOTICIA()));
            request.setParametrosDatos("usuario_actualizacion", noticia.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", noticia.getFECHA_ACTUALIZACION());
            URL = URL + auxiliarGeneral.getUpdatePHP("Noticia");
        }

        if (auxiliarGeneral.isNetworkAvailable(getActivity()))
            new AsyncTaskGenericAdeful(getActivity(), this, URL, request, "Noticia", noticia, insertar, "a");
        else
            auxiliarGeneral.errorWebService(getActivity(), getActivity().getResources().getString(R.string.error_without_internet));
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

            if (noticiaEditTituto.getText().toString().equals("") || noticiaEditArticulo.getText().toString().equals("") || noticiaEditLink.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Debe completar todos los campos.",
                        Toast.LENGTH_SHORT).show();
            } else if (insertar) {
                cargarEntidad(0);
            } else { //NOTICIA ACTUALIZAR
                cargarEntidad(idNoticianExtra);
            }
            return true;
        }

        if (id == android.R.id.home) {

            NavUtils.navigateUpFromSameTask(getActivity());
            return true;
        }
        return super.onOptionsItemSelected(item);
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
}