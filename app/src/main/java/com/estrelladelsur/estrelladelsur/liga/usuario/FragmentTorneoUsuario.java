package com.estrelladelsur.estrelladelsur.liga.usuario;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.usuario.ControladorUsuarioAdeful;

public class FragmentTorneoUsuario extends Fragment {

    private ControladorUsuarioAdeful controladorUsuarioAdeful;
    private int CheckedPositionFragment;
    private Typeface editTextFont;
    private AuxiliarGeneral auxiliarGeneral;
    private TextView torneoActualtext;
    private LinearLayout linearLayout;
    private RecyclerView recycleViewTorneo;

    public static FragmentTorneoUsuario newInstance() {
        FragmentTorneoUsuario fragment = new FragmentTorneoUsuario();
        return fragment;
    }

    public FragmentTorneoUsuario() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        controladorUsuarioAdeful = new ControladorUsuarioAdeful(getActivity());
        if (state != null) {
            CheckedPositionFragment = state.getInt("curChoice", 0);
        } else {
            init();
            recyclerViewLoadTorneo();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_general_liga_usuario, container,
                false);
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        linearLayout = (LinearLayout) v.findViewById(R.id.linearTorneoActual);
        linearLayout.setVisibility(View.VISIBLE);
        torneoActualtext = (TextView) v.findViewById(
                R.id.torneoActual);
        torneoActualtext.setTypeface(editTextFont, Typeface.BOLD);
        recycleViewTorneo = (RecyclerView) v.findViewById(R.id.recycleViewGeneral);
        recycleViewTorneo.setVisibility(View.GONE);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        String torneo = controladorUsuarioAdeful.selectTorneoUsuario();
        if (torneo != null) {
            torneoActualtext.setText(torneo);
        }
    }

    public void recyclerViewLoadTorneo() {
        recycleViewTorneo.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewTorneo.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewTorneo.setItemAnimator(new DefaultItemAnimator());
    }
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        inflater.inflate(R.menu.menu_administrador_general, menu);
//        // menu.getItem(0).setVisible(false);//usuario
//        menu.getItem(1).setVisible(false);//permiso
//        menu.getItem(2).setVisible(false);//lifuba
//        menu.getItem(3).setVisible(false);// adeful
//        menu.getItem(4).setVisible(false);// puesto
//        menu.getItem(5).setVisible(false);// posicion
//        menu.getItem(6).setVisible(false);// cargo
//        // menu.getItem(7).setVisible(false);//cerrar
//        // menu.getItem(8).setVisible(false);// guardar
//        menu.getItem(9).setVisible(false);// Subir
//        menu.getItem(10).setVisible(false); // eliminar
//        menu.getItem(11).setVisible(false); // consultar
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if (id == R.id.action_usuario) {
//
//            /*Intent usuario = new Intent(getActivity(),
//                    NavigationDrawerUsuario.class);
//            startActivity(usuario);*/
//
//            return true;
//        }
//
//        if (id == R.id.action_permisos) {
//            return true;
//        }
//
//        if (id == R.id.action_guardar) {
//
//            if (editTextTorneo.getText().toString().equals("")) {
//                Toast.makeText(getActivity(),
//                        "Ingrese el nombre del torneo.", Toast.LENGTH_SHORT)
//                        .show();
//            } else if (checkboxTorneoActual.isChecked()) {
//                if (spinnerAnioTorneoActual.getSelectedItem().toString().equals(getResources().
//                        getString(R.string.ceroSpinnerAnio))) {
//                    Toast.makeText(getActivity(), "Debe agregar un año.",
//                            Toast.LENGTH_SHORT).show();
//                } else {
//                    if (insertar) {
//                        guardarTorneo();
//                    } else {
//                        actualizarTorneo();
//                    }
//                }
//            } else {
//
//                if (insertar) {
//                    guardarTorneo();
//                } else {
//                    actualizarTorneo();
//                }
//            }
//
//            return true;
//        }
//        if (id == android.R.id.home) {
//            NavUtils.navigateUpFromSameTask(getActivity());
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}