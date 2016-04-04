package com.estrelladelsur.estrelladelsur.social;


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
import com.estrelladelsur.estrelladelsur.entidad.Noticia;

public class FragmentGenerarNoticia extends Fragment {

    private int CheckedPositionFragment;
    private EditText noticiaEditTituto;
    private EditText noticiaEditArticulo;
    private EditText noticiaEditLink;
    private ControladorAdeful controladorAdeful;
    private boolean insertar = true;
    private Noticia noticia;
    private Communicator communicator;
    private boolean actualizar = false;
    private int idNoticianExtra;
    private String fechaCreacionExtra;
    private AuxiliarGeneral auxiliarGeneral;
    private String GUARDAR_TOAST = "Noticia cargada correctamente";
    private String ACTUALIZAR_TOAST = "Noticia actualizada correctamente";

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
        noticiaEditTituto = (EditText) v
                .findViewById(R.id.articuloEditTituto);
        // EDITTEXT NOTIFICACIO
        noticiaEditArticulo = (EditText) v
                .findViewById(R.id.articuloEditArticulo);
        noticiaEditArticulo.setHint("DESCRIPCION");
        // EDITTEXT LINK
        noticiaEditLink = (EditText) v
                .findViewById(R.id.noticiaEditLink);
        noticiaEditLink.setVisibility(View.VISIBLE);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        // VER DONDE EJECUCTAR ESTA LINEA
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        controladorAdeful = new ControladorAdeful(getActivity());
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
    public void setBotonGuardar(String mensaje){

        noticiaEditTituto.setText("");
        noticiaEditArticulo.setText("");
        noticiaEditLink.setText("");
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

            String usuario = "Administrador";
            String fechaCreacion = controladorAdeful.getFechaOficial();
            String fechaActualizacion = fechaCreacion;

            if(noticiaEditTituto.getText().toString().equals("")|| noticiaEditLink.getText().toString().equals("")){

                Toast.makeText(getActivity(), "Debe completar todos los campos.",
                        Toast.LENGTH_SHORT).show();
            }else if(insertar){
                noticia = new Noticia(0, noticiaEditTituto.getText().toString(),noticiaEditArticulo.getText().toString(),
                        noticiaEditLink.getText().toString(),
                        usuario, fechaCreacion,usuario,fechaActualizacion);
               if(controladorAdeful.insertNoticiaAdeful(noticia)) {
                   setBotonGuardar(GUARDAR_TOAST);
               }else {
                auxiliarGeneral.errorDataBase(getActivity());
                }
                }else{ //NOTIFICACION ACTUALIZAR
                noticia = new Noticia(idNoticianExtra, noticiaEditTituto.getText().toString(),noticiaEditArticulo.getText().toString(),
                        noticiaEditLink.getText().toString(),
                        usuario, fechaCreacionExtra, usuario,controladorAdeful.getFechaOficial());
                if(controladorAdeful.actualizarNoticiaAdeful(noticia)) {
                    setBotonGuardar(ACTUALIZAR_TOAST);
                actualizar = false;
                insertar = true;
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