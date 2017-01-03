package com.estrelladelsur.estrelladelsur.permiso;

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
import com.estrelladelsur.estrelladelsur.entidad.Usuario;
import com.estrelladelsur.estrelladelsur.institucion.administrador.CommunicatorAdeful;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericAdeful;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import java.util.ArrayList;

public class FragmentGenerarUsuario extends Fragment implements MyAsyncTaskListener {

    private int CheckedPositionFragment;
    private EditText usuarioEditUser;
    private EditText usuarioEditPass;
    private ControladorGeneral controladorGeneral;
    private boolean insertar = true;
    private Usuario usuario;
    private CommunicatorAdeful communicator;
    private boolean actualizar = false;
    private int idUsuarioExtra;
    private AuxiliarGeneral auxiliarGeneral;
    private ArrayList<Usuario> arrayUsuario;
    private Request request;
    private String usuarioCreador = null;
    private String URL = null;

    public static FragmentGenerarUsuario newInstance() {
        FragmentGenerarUsuario fragment = new FragmentGenerarUsuario();
        return fragment;
    }

    public FragmentGenerarUsuario() {
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
        View v = inflater.inflate(R.layout.fragment_usuario, container, false);

        // EDITTEXT USER
        usuarioEditUser = (EditText) v
                .findViewById(R.id.usuarioEditUser);

        // EDITTEXT PASS
        usuarioEditPass = (EditText) v
                .findViewById(R.id.usuarioEditPass);

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
        init();
    }

    private void init() {
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        usuarioCreador = auxiliarGeneral.getUsuarioPreferences(getActivity());
        controladorGeneral = new ControladorGeneral(getActivity());
        arrayUsuario = controladorGeneral.selectListaUsuario();
        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        //Metodo Extra
        if (actualizar) {

            idUsuarioExtra = getActivity().getIntent().getIntExtra("id_usuario", 0);
            usuarioEditUser.setText(getActivity().getIntent()
                    .getStringExtra("user"));
            usuarioEditPass.setText(getActivity().getIntent()
                    .getStringExtra("pass"));
            insertar = false;
        }
    }

    public void inicializarControles(String mensaje) {
        usuarioEditUser.setText("");
        usuarioEditPass.setText("");
        communicator.refreshAdeful();
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public boolean validarUsuario(String usuario) {
        boolean isUsuario = false;
        if (arrayUsuario != null) {
            for (int i = 0; i < arrayUsuario.size(); i++) {
                if (arrayUsuario.get(i).getUSUARIO().equals(usuario)) {
                    isUsuario = true;
                    break;
                } else {
                    isUsuario = false;
                }
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        return isUsuario;
    }

    public void cargarEntidad(int id) {

        URL = null;
        URL = auxiliarGeneral.getURLUSUARIOALL();

        usuario = new Usuario(id, usuarioEditUser.getText().toString(),
                usuarioEditPass.getText().toString(),
                usuarioCreador, auxiliarGeneral.getFechaOficial(), usuarioCreador, auxiliarGeneral.getFechaOficial());

        envioWebService();
    }

    public void envioWebService() {
        request = new Request();
        request.setMethod("POST");
        request.setParametrosDatos("usuario", usuario.getUSUARIO());
        request.setParametrosDatos("pass", usuario.getPASSWORD());

        if (insertar) {
            request.setParametrosDatos("usuario_creador", usuario.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", usuario.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Usuario");

        } else {
            request.setParametrosDatos("id_usuario", String.valueOf(usuario.getID_USUARIO()));
            request.setParametrosDatos("usuario_actualizacion", usuario.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", usuario.getFECHA_ACTUALIZACION());
            URL = URL + auxiliarGeneral.getUpdatePHP("Usuario");
        }
        if (auxiliarGeneral.isNetworkAvailable(getActivity()))
            new AsyncTaskGenericAdeful(getActivity(), this, URL, request, "Usuario", usuario, insertar, "o");
        else
            auxiliarGeneral.errorWebService(getActivity(), getActivity().getResources().getString(R.string.error_without_internet));

    }

    public void showMesaje(String mensaje) {
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_administrador_general, menu);
        menu.getItem(1).setVisible(false);// posicion
        menu.getItem(2).setVisible(false);// cargo
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
            if (usuarioEditUser.getText().toString().equals("") || usuarioEditPass.getText().toString().equals("")) {
                showMesaje("Debe completar todos los campos.");
            } else if (validarUsuario(usuarioEditUser.getText().toString())) {
                showMesaje("El usuario ya existe.");
            } else if (insertar) {
                cargarEntidad(0);
            } else { //USUARIO ACTUALIZAR
                cargarEntidad(idUsuarioExtra);
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
            if (!insertar) {
                actualizar = false;
                insertar = true;
            }
            inicializarControles(mensaje);
        } else {
            auxiliarGeneral.errorWebService(getActivity(), mensaje);
        }
    }
}