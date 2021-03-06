package com.estrelladelsur.estrelladelsur.liga.administrador.adeful;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerCancha;
import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericAdeful;
import com.estrelladelsur.estrelladelsur.webservice.Request;

public class FragmentCanchaAdeful extends Fragment implements MyAsyncTaskListener {

    private DialogoAlerta dialogoAlerta;
    private RecyclerView recycleViewCancha;
    private EditText editTextNombre;
    private ImageView imageButtonCancha;
    private ArrayList<Cancha> canchaAdefulArray;
    private AdaptadorRecyclerCancha adaptadorCancha;
    private ControladorAdeful controladorAdeful;
    private int CheckedPositionFragment;
    private AuxiliarGeneral auxiliarGeneral;
    private String URL = null;
    private Request request;
    private int id_cancha;

    public static FragmentCanchaAdeful newInstance() {
        FragmentCanchaAdeful fragment = new FragmentCanchaAdeful();
        return fragment;
    }

    public FragmentCanchaAdeful() {
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
        View v = inflater.inflate(R.layout.fragment_general_liga, container,
                false);
        imageButtonCancha = (ImageView) v.findViewById(
                R.id.imageButtonEquipo_Cancha);
        editTextNombre = (EditText) v.findViewById(
                R.id.editTextDescripcion);
        editTextNombre.setVisibility(View.GONE);
        recycleViewCancha = (RecyclerView) v.findViewById(
                R.id.recycleViewGeneral);
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
        controladorAdeful = new ControladorAdeful(getActivity());
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        recyclerViewLoadCancha();
    }

    private void init() {
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        imageButtonCancha.setImageResource(R.mipmap.ic_mapa_icon);
        imageButtonCancha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mapa = new Intent(getActivity(), MapaCanchaAdeful.class);
                startActivity(mapa);
            }
        });
        recyclerViewLoadCancha();
        recycleViewCancha.addOnItemTouchListener(new RecyclerTouchListener(
                getActivity(), recycleViewCancha, new ClickListener() {
            @Override
            public void onLongClick(View view, final int position) {
                dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                        "Desea eliminar la cancha?", null, null);
                dialogoAlerta.btnAceptar.setText("Aceptar");
                dialogoAlerta.btnCancelar.setText("Cancelar");

                dialogoAlerta.btnAceptar
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {


                                envioWebService(canchaAdefulArray.get(position)
                                        .getID_CANCHA());
                                dialogoAlerta.alertDialog.dismiss();
                            }
                        });
                dialogoAlerta.btnCancelar
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                dialogoAlerta.alertDialog.dismiss();
                            }
                        });
            }

            @Override
            public void onClick(View view, int position) {
                Intent mapa = new Intent(getActivity(),
                        MapaCanchaAdeful.class);
                mapa.putExtra("actualizar", true);
                mapa.putExtra("id", canchaAdefulArray.get(position)
                        .getID_CANCHA());
                mapa.putExtra("nombre", canchaAdefulArray.get(position)
                        .getNOMBRE());
                mapa.putExtra("longitud",
                        canchaAdefulArray.get(position).getLONGITUD());
                mapa.putExtra("latitud", canchaAdefulArray
                        .get(position).getLATITUD());
                mapa.putExtra("direccion",
                        canchaAdefulArray.get(position).getDIRECCION());
                startActivity(mapa);
            }
        }));
    }

    public void inicializarControles(String mensaje) {
        recyclerViewLoadCancha();
        Toast.makeText(
                getActivity(),
                mensaje,
                Toast.LENGTH_SHORT).show();

    }

    public void recyclerViewLoadCancha() {
        recycleViewCancha.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewCancha.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewCancha.setItemAnimator(new DefaultItemAnimator());

        canchaAdefulArray = controladorAdeful.selectListaCanchaAdeful();
        if (canchaAdefulArray != null) {
            adaptadorCancha = new AdaptadorRecyclerCancha(canchaAdefulArray, getActivity());
            recycleViewCancha.setAdapter(adaptadorCancha);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    public void envioWebService(int id) {
        String fecha = auxiliarGeneral.getFechaOficial();
        URL = null;
        URL = auxiliarGeneral.getURLCANCHAADEFULALL();
        id_cancha = id;
        request = new Request();
        request.setMethod("POST");
        request.setParametrosDatos("id_cancha", String.valueOf(id_cancha));
        request.setParametrosDatos("fecha_actualizacion", fecha);
        URL = URL + auxiliarGeneral.getDeletePHP("Cancha");

        if (auxiliarGeneral.isNetworkAvailable(getActivity()))
            new AsyncTaskGenericAdeful(getActivity(), this, URL, request, "Cancha", true, id_cancha, "a", fecha);
        else
            auxiliarGeneral.errorWebService(getActivity(), getActivity().getResources().getString(R.string.error_without_internet));


    }

    @Override
    public void onPostExecuteConcluded(boolean result, String mensaje) {
        if (result) {
            inicializarControles(mensaje);
        } else {
            auxiliarGeneral.errorWebService(getActivity(), mensaje);
        }
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_administrador_general, menu);
        menu.getItem(1).setVisible(false);// posicion
        menu.getItem(2).setVisible(false);// cargo
        menu.getItem(4).setVisible(false);// guardar
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
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(getActivity());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
