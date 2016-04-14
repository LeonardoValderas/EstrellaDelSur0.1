package com.estrelladelsur.estrelladelsur.social;

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
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Notificacion;


public class FragmentGenerarNotificacion extends Fragment {

    private int CheckedPositionFragment;
    private EditText notificacionEditTituto;
    private EditText notificacionEditArticulo;
    private ControladorAdeful controladorAdeful;
    private boolean insertar = true;
    private Notificacion notificacion;
    private Communicator communicator;
    private boolean actualizar = false;
    private int idNotificacionExtra;
    private AuxiliarGeneral auxiliarGeneral;
    private String GUARDAR_USUARIO = "Notificación cargada correctamente";
    private String ACTUALIZAR_USUARIO= "Notificación actualizada correctamente";
    private Typeface editTextFont;

    public static FragmentGenerarNotificacion newInstance() {
        FragmentGenerarNotificacion fragment = new FragmentGenerarNotificacion();
        return fragment;
    }

    public FragmentGenerarNotificacion() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        communicator= (Communicator)getActivity();
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
        notificacionEditTituto.setTypeface(editTextFont,Typeface.BOLD);
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

    private void init() {
         controladorAdeful = new ControladorAdeful(getActivity());
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
    public void inicializarControles(String mensaje){
        notificacionEditTituto.setText("");
        notificacionEditArticulo.setText("");
        communicator.refresh();
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
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

            String usuario = "Administrador";

            if(notificacionEditTituto.getText().toString().equals("")|| notificacionEditArticulo.getText().toString().equals("")){

                Toast.makeText(getActivity(), "Debe completar todos los campos.",
                        Toast.LENGTH_SHORT).show();
            }else if(insertar){
                notificacion = new Notificacion(0, notificacionEditTituto.getText().toString(),
                        notificacionEditArticulo.getText().toString(),
                        usuario, auxiliarGeneral.getFechaOficial(),usuario,auxiliarGeneral.getFechaOficial());
               if(controladorAdeful.insertNotificacionAdeful(notificacion)) {
                  inicializarControles(GUARDAR_USUARIO);
               }else {
                auxiliarGeneral.errorDataBase(getActivity());
                }
                }else{ //NOTIFICACION ACTUALIZAR
                notificacion = new Notificacion(idNotificacionExtra, notificacionEditTituto.getText().toString(),
                        notificacionEditArticulo.getText().toString(),
                        null, null, usuario,auxiliarGeneral.getFechaOficial());
                if(controladorAdeful.actualizarNotificacionAdeful(notificacion)){
                actualizar = false;
                insertar = true;
                inicializarControles(ACTUALIZAR_USUARIO);
                }else {
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