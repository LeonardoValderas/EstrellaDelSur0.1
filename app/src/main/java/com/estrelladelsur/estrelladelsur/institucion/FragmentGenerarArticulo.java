package com.estrelladelsur.estrelladelsur.institucion;


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
import com.estrelladelsur.estrelladelsur.abstracta.Articulo;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;


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
    private String fechaCreacionExtra;

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
//
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

        // EDITTEXT TITULO
        articuloEditTituto = (EditText) v
                .findViewById(R.id.articuloEditTituto);

        // EDITTEXT ARTICULO
        articuloEditArticulo = (EditText) v
                .findViewById(R.id.articuloEditArticulo);

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
        //Metodo Extra
        if (actualizar) {

            idArticuloExtra = getActivity().getIntent().getIntExtra("id_articulo", 0);
            articuloEditTituto.setText(getActivity().getIntent()
                    .getStringExtra("titulo"));
            articuloEditArticulo.setText(getActivity().getIntent()
                    .getStringExtra("articulo"));
            fechaCreacionExtra = getActivity().getIntent()
                    .getStringExtra("fecha_creacion");
            insertar = false;

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // if (drawerToggle.onOptionsItemSelected(item)) {
        // return true;
        // }

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
            String fechaCreacion = controladorAdeful.getFechaOficial();
            String fechaActualizacion = fechaCreacion;

            if(articuloEditTituto.getText().toString().equals("")|| articuloEditArticulo.getText().toString().equals("")){

                Toast.makeText(getActivity(), "Debe completar todos los campos.",
                        Toast.LENGTH_SHORT).show();
            }else if(insertar){
                articulo = new Articulo(0, articuloEditTituto.getText().toString(),
                        articuloEditArticulo.getText().toString(),
                        usuario, fechaCreacion,usuario,fechaActualizacion);

                controladorAdeful.abrirBaseDeDatos();
                controladorAdeful.insertArticuloAdeful(articulo);
                controladorAdeful.cerrarBaseDeDatos();

                articuloEditTituto.setText("");
                articuloEditArticulo.setText("");
                communicator.refresh();
                Toast.makeText(getActivity(), "Articulo Cargado Correctamente",
                        Toast.LENGTH_SHORT).show();

            }else{ //FIXTURE ACTUALIZAR


                articulo = new Articulo(idArticuloExtra, articuloEditTituto.getText().toString(),
                        articuloEditArticulo.getText().toString(),
                        usuario, fechaCreacionExtra, usuario,controladorAdeful.getFechaOficial());

                controladorAdeful.abrirBaseDeDatos();
                controladorAdeful.actualizarArticuloAdeful(articulo);
                controladorAdeful.cerrarBaseDeDatos();

                articuloEditTituto.setText("");
                articuloEditArticulo.setText("");

                actualizar = false;
                insertar = true;
                Toast.makeText(getActivity(), "Articulo Actualizado Correctamente",
                        Toast.LENGTH_SHORT).show();
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