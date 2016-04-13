package com.estrelladelsur.estrelladelsur.permiso;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerCrearPermiso;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerUsuario;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoListCheck;
import com.estrelladelsur.estrelladelsur.entidad.Permiso;
import com.estrelladelsur.estrelladelsur.entidad.SubModulo;
import com.estrelladelsur.estrelladelsur.entidad.Usuario;
import com.estrelladelsur.estrelladelsur.institucion.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FragmentGenerarPermiso extends Fragment {

    private ImageButton imageButton1;
    private TextView textViewFecha, textViewLugar, textViewSubmodulos;
    private DialogoListCheck dialogoListCheck;
    private ListView entrenamientolistView;
    private int CheckedPositionFragment;
    private SimpleDateFormat formate = new SimpleDateFormat(
            "dd-MM-yyyy");
    private Spinner spinnerUsuario;
    private Button buttonFechaEntrenamiento;
    private Button buttonHoraEntrenamiento;
    private ControladorAdeful controladorAdeful;
    private ArrayList<Usuario> usuarioArray;
    private AdapterSpinnerUsuario adapterSpinnerUsuario;
    private RecyclerView recycleViewGeneral;
    private ArrayList<SubModulo> submoduloArrayFalse;
    private ArrayList<SubModulo> submoduloArrayExtraTrue;
    private ArrayList<SubModulo> submoduloArrayExtraTotal;
    private AdaptadorRecyclerCrearPermiso adaptadorRecyclerCrearPermiso;
    private Usuario usuario;
    private boolean bandera = true;
    private ArrayAdapter<String> adaptadorInicial;
    private boolean actualizar = false;
    private int idPermisoExtra;
    private boolean insertar = true;
    private ArrayList<Integer> id_permiso_array = null;
    private AuxiliarGeneral auxiliarGeneral;
    private Permiso permiso;
    private Communicator communicator;

    public static FragmentGenerarPermiso newInstance() {
        FragmentGenerarPermiso fragment = new FragmentGenerarPermiso();
        return fragment;
    }
    public FragmentGenerarPermiso() {
        // Required empty public constructor
    }
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        communicator = (Communicator) getActivity();
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
        View v = inflater.inflate(R.layout.fragment_generar_entrenamiento,
                container, false);
        // RECYCLER
        recycleViewGeneral = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
        // CANCHA
        spinnerUsuario = (Spinner) v
                .findViewById(R.id.spinnerLugarEntrenamiento);


        textViewLugar =(TextView)v.findViewById(R.id.textViewLugar);
        textViewLugar.setVisibility(View.GONE);
        textViewSubmodulos =(TextView)v.findViewById(R.id.textViewDivisionCitada);
        textViewSubmodulos.setText("MODULOS");
        // DIA
        buttonFechaEntrenamiento = (Button) v
                .findViewById(R.id.buttonFechaEntrenamiento);
        buttonFechaEntrenamiento.setVisibility(View.GONE);
        // HORA
        buttonHoraEntrenamiento = (Button) v
                .findViewById(R.id.buttonHoraEntrenamiento);
        buttonHoraEntrenamiento.setVisibility(View.GONE);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }
    private void init() {
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        // USUARIO
        usuarioArray = controladorAdeful.selectListaUsuarioAdeful();
        if (usuarioArray != null) {
            if (usuarioArray.size() != 0) {
                // USUARIO SPINNER
                adapterSpinnerUsuario = new AdapterSpinnerUsuario(getActivity(),
                        R.layout.simple_spinner_dropdown_item, usuarioArray);
                spinnerUsuario.setAdapter(adapterSpinnerUsuario);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerUsuario));
                spinnerUsuario.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        //Metodo Extra
        if (actualizar) {

            idPermisoExtra = getActivity().getIntent().getIntExtra("id_permiso", 0);
            // SPINNER
            spinnerUsuario.setSelection(getPositionUsuario(getActivity().getIntent()
                    .getIntExtra("id_usuario", 0)));
            spinnerUsuario.setEnabled(false);

            insertar = false;
        }

        // RECLYCLER
        recycleViewGeneral.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewGeneral.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewGeneral.setItemAnimator(new DefaultItemAnimator());

        recyclerViewLoadModulos();
    }
    public void recyclerViewLoadModulos() {
        boolean isADD = false;
        int position = 0;
        submoduloArrayExtraTotal = new ArrayList<SubModulo>();
        //MODULOS FALSE
        submoduloArrayFalse = controladorAdeful
                .selectListaModuloSubModuloFalseAdeful();
        if (submoduloArrayFalse != null) {
            //EDITAR
            if (!insertar) {
                //MODULOS TRUE
                submoduloArrayExtraTrue = controladorAdeful
                        .selectListaSubModuloPermisoAdeful(idPermisoExtra);
               if (submoduloArrayExtraTrue != null) {
                   for (int i = 0; i < submoduloArrayExtraTrue.size(); i++) {
                       submoduloArrayExtraTotal.add(submoduloArrayExtraTrue.get(i));
                   }
                   for (int a = 0; a < submoduloArrayFalse.size(); a++) {
                       submoduloArrayExtraTotal.add(submoduloArrayFalse.get(a));
                   }
               loadApterRecycler(submoduloArrayExtraTotal);
               }else {
                  auxiliarGeneral.errorDataBase(getActivity());
               }
            }else {
                //INICIO
                loadApterRecycler(submoduloArrayFalse);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    private int getPositionUsuario(int usuario) {
        int index = 0;
        for (int i = 0; i < usuarioArray.size(); i++) {
            if (usuarioArray.get(i).getID_USUARIO() == (usuario)) {
                index = i;
            }
        }
        return index;
    }

       public void loadApterRecycler(ArrayList<SubModulo> litaSubmodulo){
        //ADAPTER
        adaptadorRecyclerCrearPermiso = new AdaptadorRecyclerCrearPermiso(
                litaSubmodulo);
        recycleViewGeneral.setAdapter(adaptadorRecyclerCrearPermiso);
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

        if (id == R.id.action_usuario) {

            //*Intent usuario = new Intent(getActivity(),
            //		NavigationUsuario.class);
            //	startActivity(usuario);

            return true;
        }

        if (id == R.id.action_permisos) {
            return true;
        }

        if (id == R.id.action_guardar) {

//            ArrayList<SubModulo> permisoList = adaptadorRecyclerCrearPermiso
//                    .getPermisoList();

            if (spinnerUsuario.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerUsuario))) {
                Toast.makeText(getActivity(),
                        "Debe cargar un usuario.",
                        Toast.LENGTH_SHORT).show();
            }else {
                ArrayList<SubModulo> subModulosTrue = adaptadorRecyclerCrearPermiso
                        .getSubmoduloTrueArray();
                    if (subModulosTrue.isEmpty()) {
                        Toast.makeText(getActivity(),
                                "Seleccione un modulo.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        String usuarioa = "Administrador";
                        String fechaCreacion = controladorAdeful.getFechaOficial();
                        String fechaActualizacion = fechaCreacion;
                        boolean salving = true;
                        if (insertar) {
                            usuario = (Usuario) spinnerUsuario
                                    .getSelectedItem();
                            int ispermiso = controladorAdeful.isPermiso(usuario.getID_USUARIO());

                            if(ispermiso == 0) {
                                Toast.makeText(getActivity(),
                                        "El usuario ya tiene permisos asignados.",
                                        Toast.LENGTH_SHORT).show();
                            }else if(ispermiso == 2){
                                    auxiliarGeneral.errorDataBase(getActivity());
                            }else {

                                permiso = new Permiso(0, usuario.getID_USUARIO(),
                                        usuarioa, fechaCreacion, usuarioa, fechaActualizacion);

                                int id_permiso = controladorAdeful
                                        .insertPermisosAdeful(permiso);

                                if (id_permiso > 0) {
                                    for (int i = 0; i < subModulosTrue.size(); i++) {

                                        permiso = new Permiso(0,
                                                id_permiso,
                                                subModulosTrue.get(i).getID_MODULO(),
                                                subModulosTrue.get(i).getID_SUBMODULO());

                                        if (controladorAdeful
                                                .insertPermisoModuloAdeful(permiso)) {

                                            if (controladorAdeful
                                                    .actualizarSubModuloSelectedTrueAdeful(subModulosTrue.get(i).getID_SUBMODULO())) {
                                                salving = true;
                                            } else {
                                                auxiliarGeneral.errorDataBase(getActivity());
                                                salving = false;
                                                break;
                                            }

                                        } else {
                                            auxiliarGeneral.errorDataBase(getActivity());
                                            salving = false;
                                            break;
                                        }
                                    }
                                    if (salving) {

                                        recyclerViewLoadModulos();
                                        communicator.refresh();
                                        Toast.makeText(
                                                getActivity(),
                                                "Permiso cargado correctamente.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    auxiliarGeneral.errorDataBase(getActivity());
                                }
                            }
                        } else {
                        //EDITAR PERMISO
                         permiso = new Permiso(idPermisoExtra,
                                    0, null, null, usuarioa, fechaActualizacion);
                         // actualizo fecha permiso
                         if (controladorAdeful
                                    .actualizarPermisosAdeful(permiso)) {

                               for (int i = 0; i < submoduloArrayExtraTrue.size(); i++) {

                                  if(!submoduloArrayExtraTrue.get(i).ISSELECTED()){
                                      if (controladorAdeful
                                                   .eliminarPermisoModuloAdeful(submoduloArrayExtraTrue.get(i).getID_PERMISO_MODULO())) {
                                           } else {
                                               auxiliarGeneral.errorDataBase(getActivity());
                                           }
                                  }
                               }
                            for (int i = 0; i < submoduloArrayFalse.size(); i++) {
                                if(submoduloArrayFalse.get(i).ISSELECTED()){

                                    permiso = new Permiso(
                                            0, idPermisoExtra, submoduloArrayFalse.get(i).getID_MODULO()
                                            ,submoduloArrayFalse.get(i).getID_SUBMODULO());
                                    if (controladorAdeful
                                            .insertPermisoModuloAdeful(permiso)) {
                                    } else {
                                        auxiliarGeneral.errorDataBase(getActivity());
                                    }
                                }
                            }
                                insertar = true;
                                recyclerViewLoadModulos();
                                communicator.refresh();
                                Toast.makeText(
                                        getActivity(),
                                        "Permiso actualizado correctamente.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                            auxiliarGeneral.errorDataBase(getActivity());
                            }
                                }
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
