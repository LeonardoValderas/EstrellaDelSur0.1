package com.estrelladelsur.estrelladelsur.permiso;

import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerCrearPermiso;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerUsuario;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Permiso;
import com.estrelladelsur.estrelladelsur.entidad.SubModulo;
import com.estrelladelsur.estrelladelsur.entidad.Usuario;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.navegador.administrador.Navigation;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericAdeful;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentGenerarPermiso extends Fragment implements MyAsyncTaskListener {

    private TextView textViewLugar, textViewSubmodulos;
    private int CheckedPositionFragment;
    private Spinner spinnerUsuario;
    private Button buttonFechaEntrenamiento;
    private Button buttonHoraEntrenamiento;
    private ControladorGeneral controladorGeneral;
    private ArrayList<Usuario> usuarioArray;
    private AdapterSpinnerUsuario adapterSpinnerUsuario;
    private RecyclerView recycleViewGeneral;
    private ArrayList<SubModulo> submoduloArrayFalse;
    private ArrayList<SubModulo> submoduloArrayExtraTrue;
    private ArrayList<SubModulo> submoduloArrayExtraTotal;
    private ArrayList<SubModulo> id_sub_add_array = null, id_sub_delete_array = null;
    private AdaptadorRecyclerCrearPermiso adaptadorRecyclerCrearPermiso;
    private Usuario usuario;
    private ArrayAdapter<String> adaptadorInicial;
    private boolean actualizar = false;
    private int idPermisoExtra;
    private boolean insertar = true;
    private AuxiliarGeneral auxiliarGeneral;
    private Permiso permiso;
    private Communicator communicator;
    private Typeface titulo;
    private String usuarioCreador = null;
    private Request request;
    private String URL = null;

    public static FragmentGenerarPermiso newInstance() {
        FragmentGenerarPermiso fragment = new FragmentGenerarPermiso();
        return fragment;
    }

    public FragmentGenerarPermiso() {
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        communicator = (Communicator) getActivity();
        controladorGeneral = new ControladorGeneral(getActivity());
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
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        titulo = auxiliarGeneral.tituloFont(getActivity());
        // RECYCLER
        recycleViewGeneral = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
        // CANCHA
        spinnerUsuario = (Spinner) v
                .findViewById(R.id.spinnerLugarEntrenamiento);

        textViewLugar = (TextView) v.findViewById(R.id.textViewLugar);
        textViewLugar.setVisibility(View.GONE);
        textViewSubmodulos = (TextView) v.findViewById(R.id.textViewDivisionCitada);
        textViewSubmodulos.setText("Modulos");
        textViewSubmodulos.setTypeface(titulo, Typeface.BOLD);
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

    @Override
    public void onResume() {
        super.onResume();
        controladorGeneral = new ControladorGeneral(getActivity());
        communicator = (Communicator) getActivity();
        init();
    }

    private void init() {
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        usuarioCreador = auxiliarGeneral.getUsuarioPreferences(getActivity());
        // USUARIO
        usuarioArray = controladorGeneral.selectListaUsuario();
        int count = usuarioArray.size();
        usuarioArray.remove(count - 1);
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
        submoduloArrayExtraTotal = new ArrayList<>();
        //MODULOS FALSE
        submoduloArrayFalse = controladorGeneral
                .selectListaModuloSubModuloFalse();
        if (submoduloArrayFalse != null) {
            //EDITAR
            if (!insertar) {
                //MODULOS TRUE
                submoduloArrayExtraTrue = controladorGeneral
                        .selectListaSubModuloPermiso(idPermisoExtra);
                if (submoduloArrayExtraTrue != null) {
                    for (int i = 0; i < submoduloArrayExtraTrue.size(); i++) {
                        submoduloArrayExtraTotal.add(submoduloArrayExtraTrue.get(i));
                    }
                    for (int a = 0; a < submoduloArrayFalse.size(); a++) {
                        submoduloArrayExtraTotal.add(submoduloArrayFalse.get(a));
                    }
                    loadApterRecycler(submoduloArrayExtraTotal);
                } else {
                    auxiliarGeneral.errorDataBase(getActivity());
                }
            } else {
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

    public void loadApterRecycler(ArrayList<SubModulo> litaSubmodulo) {
        //ADAPTER
        adaptadorRecyclerCrearPermiso = new AdaptadorRecyclerCrearPermiso(
                litaSubmodulo, getActivity());
        recycleViewGeneral.setAdapter(adaptadorRecyclerCrearPermiso);
    }

    public void showMessage(String msg) {
        Toast.makeText(getActivity(),
                msg,
                Toast.LENGTH_SHORT).show();
    }

    public void cargarEntidad(int id, ArrayList<SubModulo> subModulosTrue, ArrayList<SubModulo> submoduloArrayFalse) throws JSONException {
        URL = null;
        URL = auxiliarGeneral.getURLPERMISOALL();

        permiso = new Permiso(id, usuario.getID_USUARIO(), subModulosTrue, submoduloArrayFalse,
                usuarioCreador, auxiliarGeneral.getFechaOficial(), usuarioCreador, auxiliarGeneral.getFechaOficial());


        envioWebService();
    }

    public void envioWebService() throws JSONException {

        JSONArray subModuloArrayTrue = new JSONArray();
        JSONArray subModuloArrayFalse = new JSONArray();
        request = new Request();
        request.setMethod("POST");

        if (permiso.getSubModulosTrue() != null) {
            for (int i = 0; i < permiso.getSubModulosTrue().size(); i++) {

                JSONObject submoduloIds = new JSONObject();
                submoduloIds.put("submoduloTrue", String.valueOf(permiso.getSubModulosTrue().get(i).getID_SUBMODULO()));
                subModuloArrayTrue.put(submoduloIds);
            }
        }
        request.setParametrosDatos("submoduloTrue", subModuloArrayTrue.toString());
        request.setParametrosDatos("usuario", String.valueOf(permiso.getID_USUARIO()));

        if (insertar) {
            request.setParametrosDatos("usuario_creador", permiso.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", permiso.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Permiso");
        } else {

            if (permiso.getSubmoduloArrayFalse() != null) {
                for (int i = 0; i < permiso.getSubmoduloArrayFalse().size(); i++) {
                    JSONObject submoduloIds = new JSONObject();
                    submoduloIds.put("permisoFalse", String.valueOf(permiso.getSubmoduloArrayFalse().get(i).getID_SUBMODULO()));
                    subModuloArrayFalse.put(submoduloIds);
                }
            }
            request.setParametrosDatos("permisoFalse", subModuloArrayFalse.toString());
            request.setParametrosDatos("id_permiso", String.valueOf(permiso.getID_PERMISO()));
            request.setParametrosDatos("usuario_actualizacion", permiso.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", permiso.getFECHA_ACTUALIZACION());
            URL = URL + auxiliarGeneral.getUpdatePHP("Permiso");
        }

        new AsyncTaskGenericAdeful(getContext(), this, URL, request, "Permiso", permiso, insertar, "o");
    }

    public void inicializarControles(String mensaje) {
        recyclerViewLoadModulos();
        communicator.refresh();
        showMessage(mensaje);
        if(!usuarioCreador.equals("ADM")) {
            Intent nav = new Intent(getActivity(), Navigation.class);
            nav.putExtra("usuario", usuarioCreador);
            startActivity(nav);
        }
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

            if (spinnerUsuario.getSelectedItem().toString().equals(getResources().
                    getString(R.string.ceroSpinnerUsuario))) {
                showMessage("Debe cargar un usuario.");
            } else {
                ArrayList<SubModulo> subModulosTrue = adaptadorRecyclerCrearPermiso
                        .getSubmoduloTrueArray();
                if (subModulosTrue.isEmpty()) {
                    showMessage("Seleccione un modulo.");
                } else {
                    usuario = (Usuario) spinnerUsuario.getSelectedItem();
                    if (insertar) {

                        int ispermiso = controladorGeneral.isPermiso(usuario.getID_USUARIO());
                        if (ispermiso == 0) {
                            showMessage("El usuario ya tiene permisos asignados.");
                        } else if (ispermiso == 2) {
                            auxiliarGeneral.errorDataBase(getActivity());
                        } else {

                            try {
                                cargarEntidad(0, subModulosTrue, null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        id_sub_delete_array = new ArrayList<>();
                        id_sub_add_array = new ArrayList<>();
                        boolean isAdd = false;
                        boolean isDelete = false;
                        for (int i = 0; i < subModulosTrue.size(); i++) {
                            for (int d = 0; d < submoduloArrayExtraTrue.size(); d++) {
                                if (subModulosTrue.get(i).getID_SUBMODULO() == submoduloArrayExtraTrue.get(d).getID_SUBMODULO()) {
                                    isAdd = false;
                                    break;
                                } else {
                                    isAdd = true;
                                }
                            }
                            if (isAdd)
                                id_sub_add_array.add(subModulosTrue.get(i));
                        }
                        for (int i = 0; i < submoduloArrayExtraTrue.size(); i++) {
                            for (int d = 0; d < subModulosTrue.size(); d++) {
                                if (submoduloArrayExtraTrue.get(i).getID_SUBMODULO() == subModulosTrue.get(d).getID_SUBMODULO()) {
                                    isDelete = false;
                                    break;
                                } else {
                                    isDelete = true;
                                }
                            }
                            if(isDelete)
                                id_sub_delete_array.add(submoduloArrayExtraTrue.get(i));
                        }
                        try {
                            cargarEntidad(idPermisoExtra, id_sub_add_array, id_sub_delete_array);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
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
                spinnerUsuario.setEnabled(true);
            }
            inicializarControles(mensaje);
        } else {
            auxiliarGeneral.errorWebService(getActivity(), mensaje);
        }
    }
}
