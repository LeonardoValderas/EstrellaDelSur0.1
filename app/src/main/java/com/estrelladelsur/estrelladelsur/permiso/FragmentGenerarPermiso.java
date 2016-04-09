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
import android.widget.EditText;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerDivisionEntrenamiento;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerPermiso;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.estrelladelsur.estrelladelsur.entidad.Permiso;
import com.estrelladelsur.estrelladelsur.entidad.Sancion;
import com.estrelladelsur.estrelladelsur.entidad.Torneo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FragmentGenerarPermiso extends Fragment {

    private EditText editTextUsuario;
    private EditText editTextPassword;
    private RecyclerView recyclerViewPermiso;
    private SimpleDateFormat formate = new SimpleDateFormat(
            "dd-MM-yyyy");
    private int CheckedPositionFragment;
    private ControladorAdeful controladorAdeful;
    private boolean actualizar = false;
    private boolean insertar = true;
    private int idPermisoExtra;
    private AuxiliarGeneral auxiliarGeneral;
    private AdaptadorRecyclerPermiso adaptadorRecyclerPermiso;
    private ArrayList<Permiso> permisoArray;
    private Permiso permiso;

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
        View v = inflater.inflate(R.layout.fragment_general_permiso, container,
                false);

        // USUARIO
        editTextUsuario = (EditText) v
                .findViewById(R.id.editTextUsuario);
        // PASS
        editTextPassword = (EditText) v
                .findViewById(R.id.editTextPassword);
        // RecyclerView
        recyclerViewPermiso = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
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
        //Metodo Extra
        if (actualizar) {

            idPermisoExtra = getActivity().getIntent().getIntExtra("id_permiso", 0);
            //USUARIO
            editTextUsuario.setText(getActivity().getIntent()
                    .getStringExtra("usuario"));
            //PASS
            editTextPassword.setText(getActivity().getIntent()
                    .getStringExtra("password"));

            insertar = false;
        }

        // RECLYCLER
        recyclerViewPermiso.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewPermiso.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerViewPermiso.setItemAnimator(new DefaultItemAnimator());

        recyclerViewLoadPermiso();
    }

    public void recyclerViewLoadPermiso() {




//        permisoArray = controladorAdeful
//                .selectListaDivisionEntrenamientoAdeful();
//        if (divisionArray != null) {
//            //EDITAR
//            if (!insertar) {
//                divisionArrayExtra = controladorAdeful
//                        .selectListaDivisionEntrenamientoAdefulId(idEntrenamientoExtra);
//                if (divisionArrayExtra != null) {
//                    for (int extra = 0; extra < divisionArrayExtra.size(); extra++) {
//                        for (int div = 0; div < divisionArray.size(); div++) {
//                            if (divisionArrayExtra.get(extra).getID_DIVISION() == divisionArray.get(div).getID_DIVISION()) {
//                                divisionArray.get(div).setSelected(true);
//                                break;
//                            }
//                        }
//                    }
//                } else {
//                    auxiliarGeneral.errorDataBase(getActivity());
//                }
            }
            //ADAPTER
//            adaptadorRecyclerPermiso = new AdaptadorRecyclerPermiso(
//                    divisionArray);
//            recyclerViewPermiso.setAdapter(adaptadorRecyclerPermiso);
//        } else {
//            auxiliarGeneral.errorDataBase(getActivity());
//        }
   // }

    public ArrayList<Permiso> setModulos(){

        for (int i = 0; i < getResources().getStringArray(R.array.moduloArray).length ; i++) {


        }

        return  permisoArray;
    }

    public void insertModuloSubmodulo(){

        for (int i = 0; i < getResources().getStringArray(R.array.moduloArray).length ; i++) {


        }

     //   return  permisoArray;
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


//            if (sancionDivisionSpinner.getSelectedItem().toString().equals(getResources().
//                    getString(R.string.ceroSpinnerDivision))) {
//                Toast.makeText(getActivity(), "Debe agregar un division (Liga).",
//                        Toast.LENGTH_SHORT).show();
//            } else if (sancionJugadorSpinner.getSelectedItem().toString().equals(getResources().
//                    getString(R.string.ceroSpinnerJugador))) {
//                Toast.makeText(getActivity(), "Debe agregar un jugador.",
//                               Toast.LENGTH_SHORT).show();
//



//            } else if (sancionAmarillaSpinner.getSelectedItem().toString().equals(getResources().
//                    getString(R.string.ceroSpinnerFecha))) {
//                Toast.makeText(getActivity(), "Debe agregar una fecha.",
//                        Toast.LENGTH_SHORT).show();
//            } else if (sancionRojaSpinner.getSelectedItem().toString().equals(getResources().
//                    getString(R.string.ceroSpinnerAnio))) {
//                Toast.makeText(getActivity(), "Debe agregar un año.",
//                        Toast.LENGTH_SHORT).show();
            } else {
                String usuario = "Administrador";
                String fechaCreacion = controladorAdeful.getFechaOficial();
                String fechaActualizacion = fechaCreacion;

//                division = (Division) sancionDivisionSpinner.getSelectedItem();
//                torneo = (Torneo) sancionTorneoSpinner.getSelectedItem();
//                anio = (Anio) sancionAnioSpinner.getSelectedItem();
//                jugadorRecycler = (Jugador) sancionJugadorSpinner.getSelectedItem();

                //SANCION NVO
                if (insertar) {

//                    sancion = new Sancion(0, jugadorRecycler.getID_JUGADOR(), torneo.getID_TORNEO(), anio.getID_ANIO(), sancionAmarillaSpinner.getSelectedItemPosition(),
//                            sancionRojaSpinner.getSelectedItemPosition(), cantidadFechasSpinner.getSelectedItemPosition(), observacionesSancion.getText().toString(), usuario, fechaCreacion,
//                            usuario, fechaActualizacion);

//                    if (controladorAdeful.insertSancionAdeful(sancion)) {
//                        Toast.makeText(getActivity(), "Sanción cargada correctamente",
//                                Toast.LENGTH_SHORT).show();
//                    } else {
//                        auxiliarGeneral.errorDataBase(getActivity());
//                    }
                } else { //SANCION ACTUALIZAR

//                    sancion = new Sancion(idPermisoExtra, jugadorRecycler.getID_JUGADOR(), torneo.getID_TORNEO(), anio.getID_ANIO(), sancionAmarillaSpinner.getSelectedItemPosition(),
//                            sancionRojaSpinner.getSelectedItemPosition(), cantidadFechasSpinner.getSelectedItemPosition(), observacionesSancion.getText().toString(), null, null,
//                            usuario, fechaActualizacion);

//                    if (controladorAdeful.actualizarSancionAdeful(sancion)) {
//
//                        actualizar = false;
//                        insertar = true;
//                        Toast.makeText(getActivity(), "Sanción actualizada correctamente",
//                                Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        auxiliarGeneral.errorDataBase(getActivity());
//                    }
//                }
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