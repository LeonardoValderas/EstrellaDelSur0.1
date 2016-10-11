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
import com.estrelladelsur.estrelladelsur.database.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Usuario;
import com.estrelladelsur.estrelladelsur.institucion.administrador.CommunicatorAdeful;

import java.util.ArrayList;

public class FragmentGenerarUsuario extends Fragment {

    private int CheckedPositionFragment;
    private EditText usuarioEditUser;
    private EditText usuarioEditPass;
    private ControladorGeneral controladorGeneral;
    private boolean insertar = true;
    private Usuario usuario;
    private CommunicatorAdeful communicator;
    private boolean actualizar = false;
    private int idUsuarioExtra;
    private String fechaCreacionExtra;
    private String GUARDAR_USUARIO = "Usuario cargado correctamente";
    private String ACTUALIZAR_USUARIO = "Usuario actualizado correctamente";
    private AuxiliarGeneral auxiliarGeneral;
    private ArrayList<Usuario> arrayUsuario;

    public static FragmentGenerarUsuario newInstance() {
        FragmentGenerarUsuario fragment = new FragmentGenerarUsuario();
        return fragment;
    }

    public FragmentGenerarUsuario() {
        // Required empty public constructor
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

    private void init() {
        // VER DONDE EJECUCTAR ESTA LINEA
        controladorGeneral = new ControladorGeneral(getActivity());
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
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
                }else{
                    isUsuario = false;
                }
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }

        return isUsuario;
    }
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

            String usuarios = "Administrador";
           if (usuarioEditUser.getText().toString().equals("") || usuarioEditPass.getText().toString().equals("")) {

                Toast.makeText(getActivity(), "Debe completar todos los campos.",
                        Toast.LENGTH_SHORT).show();
            } else if(validarUsuario(usuarioEditUser.getText().toString())){
                Toast.makeText(getActivity(), "El usuario ya existe.",
                        Toast.LENGTH_SHORT).show();
            }else if (insertar) {
                usuario = new Usuario(0, usuarioEditUser.getText().toString(),
                        usuarioEditPass.getText().toString(),true,
                        usuarios, auxiliarGeneral.getFechaOficial(), usuarios, auxiliarGeneral.getFechaOficial());
                if (controladorGeneral.insertUsuario(usuario)) {
                    inicializarControles(GUARDAR_USUARIO);
                } else {
                    auxiliarGeneral.errorDataBase(getActivity());
                }
            } else { //USUARIO ACTUALIZAR
                usuario = new Usuario(idUsuarioExtra, usuarioEditUser.getText().toString(),
                        usuarioEditPass.getText().toString(),true,
                        null, null, usuarios, auxiliarGeneral.getFechaOficial());
                if (controladorGeneral.actualizarUsuario(usuario)) {
                    actualizar = false;
                    insertar = true;
                    communicator.refreshAdeful();
                    inicializarControles(ACTUALIZAR_USUARIO);
                } else {
                    auxiliarGeneral.errorDataBase(getActivity());
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