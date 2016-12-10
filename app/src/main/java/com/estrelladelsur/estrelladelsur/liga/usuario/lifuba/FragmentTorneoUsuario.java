package com.estrelladelsur.estrelladelsur.liga.usuario.lifuba;

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
import com.estrelladelsur.estrelladelsur.database.usuario.lifuba.ControladorUsuarioLifuba;

public class FragmentTorneoUsuario extends Fragment {

    private ControladorUsuarioLifuba controladorUsuarioLifuba;
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
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        controladorUsuarioLifuba = new ControladorUsuarioLifuba(getActivity());
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

    @Override
    public void onResume() {
        super.onResume();
        controladorUsuarioLifuba = new ControladorUsuarioLifuba(getActivity());
        init();
    }

    private void init() {
        String torneo = controladorUsuarioLifuba.selectTorneoUsuarioLifuba();
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
}