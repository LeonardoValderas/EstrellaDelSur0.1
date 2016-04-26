package com.estrelladelsur.estrelladelsur.permiso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerEditarPermiso;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Permiso;
import java.util.ArrayList;


public class FragmentEditarPermiso extends Fragment {

    private Spinner entrenamientoAnioSpinner;
    private Spinner entrenamientoMesSpinner;
    private ControladorAdeful controladorAdeful;
    private RecyclerView recycleViewGeneral;
    private int CheckedPositionFragment;
    private FloatingActionButton botonFloating;
    private ArrayList<Permiso> permisoArray;
    private AdaptadorRecyclerEditarPermiso adaptadorPermiso;
    private DialogoAlerta dialogoAlerta;
    private AuxiliarGeneral auxiliarGeneral;
    private Communicator communicator;

    public static FragmentEditarPermiso newInstance() {
        FragmentEditarPermiso fragment = new FragmentEditarPermiso();
        return fragment;
    }

    public FragmentEditarPermiso() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        controladorAdeful = new ControladorAdeful(getActivity());
        communicator = (Communicator) getActivity();
        if (state != null) {
            CheckedPositionFragment = state.getInt("curChoice", 0);
        } else {
            init();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_editar_entrenamiento,
                container, false);
        // RECYCLER
        recycleViewGeneral = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
        // CANCHA
        entrenamientoAnioSpinner = (Spinner) v
                .findViewById(R.id.entrenamientoAnioSpinner);
        entrenamientoAnioSpinner.setVisibility(View.GONE);
        // DIA
        entrenamientoMesSpinner = (Spinner) v
                .findViewById(R.id.entrenamientoMesSpinner);
        entrenamientoMesSpinner.setVisibility(View.GONE);
        //BOTON FLOATING
        botonFloating = (FloatingActionButton) v
                .findViewById(R.id.botonFloating);
        botonFloating.setVisibility(View.GONE);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {

        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        // RECYCLER VIEW
        recycleViewGeneral.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewGeneral.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewGeneral.setItemAnimator(new DefaultItemAnimator());
        recyclerViewLoadPermiso();


        recycleViewGeneral.addOnItemTouchListener(new RecyclerTouchListener(
                getActivity(), recycleViewGeneral, new ClickListener() {

            @Override
            public void onLongClick(View view, final int position) {

                dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                        "Desea eliminar el permiso?", null, null);
                dialogoAlerta.btnAceptar.setText("Aceptar");
                dialogoAlerta.btnCancelar.setText("Cancelar");

                dialogoAlerta.btnAceptar
                        .setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View v) {
                                                    boolean isSalving = true;
                                                    int id_permiso = permisoArray.get(position).getID_PERMISO();
                                                    ArrayList<Integer> idSubmodulo = controladorAdeful.selectListaIdModulosAdefulId(id_permiso);

                                                    if(idSubmodulo == null){
                                                        auxiliarGeneral.errorDataBase(getActivity());
                                                    }else {
                                                        for (int i = 0; i < idSubmodulo.size(); i++) {

                                                            if (!controladorAdeful.actualizarSubModuloSelectedFalseAdeful(idSubmodulo.get(i))) {
                                                                auxiliarGeneral.errorDataBase(getActivity());
                                                                isSalving = false;
                                                                break;
                                                            }

                                                        }
                                                    }
                                                    if(isSalving) {
                                                    if (controladorAdeful.eliminarPermisoAdeful(id_permiso)) {
                                                        recyclerViewLoadPermiso();
                                                        communicator.refreshDelete();
                                                        Toast.makeText(
                                                                getActivity(),
                                                                "Permiso eliminado Correctamente",
                                                                Toast.LENGTH_SHORT).show();

                                                        dialogoAlerta.alertDialog.dismiss();
                                                        }else{
                                                        for (int i = 0; i < idSubmodulo.size(); i++) {

                                                            if (!controladorAdeful.actualizarSubModuloSelectedTrueAdeful(idSubmodulo.get(i))) {
                                                                auxiliarGeneral.errorDataBase(getActivity());
                                                                break;
                                                            }
                                                        }
                                                        auxiliarGeneral.errorDataBase(getActivity());
                                                    }
                                                    } else {
                                                        auxiliarGeneral.errorDataBase(getActivity());
                                                    }
                                                }
                                            }
                        );
                dialogoAlerta.btnCancelar
                        .setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    // TODO Auto-generated method stub
                                                    dialogoAlerta.alertDialog.dismiss();
                                                }
                                            }
                        );
            }

            @Override
            public void onClick(View view, int position) {
                // TODO Auto-generated method stub

                Intent editarPermiso = new Intent(getActivity(),
                        TabsPermiso.class);
                editarPermiso.putExtra("actualizar", true);
                editarPermiso.putExtra("id_usuario",
                        permisoArray.get(position).getID_USUARIO());
                editarPermiso.putExtra("id_permiso",
                        permisoArray.get(position).getID_PERMISO());
                startActivity(editarPermiso);
            }
        }
        ));
    }

    public void recyclerViewLoadPermiso() {
        permisoArray = controladorAdeful.selectListaPermisoAdeful();
        if (permisoArray != null) {
            adaptadorPermiso = new AdaptadorRecyclerEditarPermiso(permisoArray, getActivity());
            recycleViewGeneral.setAdapter(adaptadorPermiso);
        } else {
        auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static interface ClickListener {
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements
            RecyclerView.OnItemTouchListener {

        private GestureDetector detector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context,
                                     final RecyclerView recyclerView,
                                     final ClickListener clickListener) {
            this.clickListener = clickListener;
            detector = new GestureDetector(context,
                    new GestureDetector.SimpleOnGestureListener() {

                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public void onLongPress(MotionEvent e) {
                            View child = recyclerView.findChildViewUnder(
                                    e.getX(), e.getY());
                            if (child != null && clickListener != null) {
                                clickListener.onLongClick(child,
                                        recyclerView.getChildPosition(child));
                            }
                        }
                    });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            // TODO Auto-generated method stub
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null
                    && detector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }
        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }
        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean arg0) {
        }
    }
}