package com.estrelladelsur.estrelladelsur.liga.adeful;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerDivision;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.webservice.JsonParsing;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentDivision extends Fragment {

    private DialogoAlerta dialogoAlerta;
    private RecyclerView recycleViewDivision;
    private ArrayList<Division> divisionArray;
    private Division division;
    private AdaptadorRecyclerDivision adaptadorDivision;
    private EditText editTextDivision;
    private boolean insertar = true;
    private int gestion = 0;//0-insert //1-update//2-delete
    private int posicion;
    private String URL = null, divisionText = null, usuario = null, mensaje = null;
    private ControladorAdeful controladorAdeful;
    private int CheckedPositionFragment;
    private ImageView imageButtonEquipo;
    private TextInputLayout editTextInputDescripcion;
    private String GUARDAR = "División cargada correctamente";
    private String ACTUALIZAR = "División actualizada correctamente";
    private String ELIMINAR = "División eliminada correctamente";
    private Typeface editTextFont;
    private AuxiliarGeneral auxiliarGeneral;
    private Request request;
    private JsonParsing jsonParsing = new JsonParsing(getActivity());
    private ProgressDialog dialog;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_ID = "id";

    public static FragmentDivision newInstance() {
        FragmentDivision fragment = new FragmentDivision();
        return fragment;
    }

    public FragmentDivision() {
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
        View v = inflater.inflate(R.layout.fragment_general_liga, container,
                false);
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        editTextFont = auxiliarGeneral.textFont(getActivity());
        editTextDivision = (EditText) v.findViewById(
                R.id.editTextDescripcion);
        editTextDivision.setTypeface(editTextFont);

        editTextInputDescripcion = (TextInputLayout) v.findViewById(R.id.editTextInputDescripcion);

        imageButtonEquipo = (ImageView) v.findViewById(
                R.id.imageButtonEquipo_Cancha);
        imageButtonEquipo.setVisibility(View.GONE);
        recycleViewDivision = (RecyclerView) v.findViewById(
                R.id.recycleViewGeneral);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }


    private void init() {

        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
        editTextInputDescripcion.setHint("Ingrese una división");
        recyclerViewLoadDivision();
        recycleViewDivision.addOnItemTouchListener(new RecyclerTouchListener(
                getActivity(), recycleViewDivision, new ClickListener() {
            @Override
            public void onLongClick(View view, final int position) {

                dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                        "Desea eliminar la división?", null, null);
                dialogoAlerta.btnAceptar.setText("Aceptar");
                dialogoAlerta.btnCancelar.setText("Cancelar");

                dialogoAlerta.btnAceptar
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                gestion = 2;
                                cargarEntidad(divisionArray
                                        .get(position)
                                        .getID_DIVISION(), 3);
                                dialogoAlerta.alertDialog.dismiss();

//                                if (controladorAdeful.eliminarDivisionAdeful(divisionArray.get(position)
//                                        .getID_DIVISION())) {
//                                    recyclerViewLoadDivision();
//                                    Toast.makeText(
//                                            getActivity(),
//                                            "División eliminada correctamente",
//                                            Toast.LENGTH_SHORT).show();
//                                    dialogoAlerta.alertDialog.dismiss();
//
//                                } else {
//                                    auxiliarGeneral.errorDataBase(getActivity());
//                                }
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
                gestion = 1;
              //  insertar = false;
                editTextDivision.setText(divisionArray.get(position)
                        .getDESCRIPCION());
                posicion = position;
            }
        }));
    }

    public void recyclerViewLoadDivision() {
        recycleViewDivision.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewDivision.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewDivision.setItemAnimator(new DefaultItemAnimator());

        divisionArray = controladorAdeful.selectListaDivisionAdeful();
        if (divisionArray != null) {
            adaptadorDivision = new AdaptadorRecyclerDivision(divisionArray, getActivity());
            recycleViewDivision.setAdapter(adaptadorDivision);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
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

    public void inicializarControles(String mensaje) {
        recyclerViewLoadDivision();
        editTextDivision.setText("");
        Toast.makeText(getActivity(),
                mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void cargarEntidad(int id, int ws) {
        URL = null;
        URL = auxiliarGeneral.getURL() + auxiliarGeneral.getURLDIVISIONADEFUL();
        if (ws != 3) {
            division = null;
            divisionText = editTextDivision.getText()
                    .toString();
        }
        division = new Division(id, divisionText, usuario, auxiliarGeneral.getFechaOficial(),
							usuario, auxiliarGeneral.getFechaOficial());
        envioWebService(ws);
    }
    public void envioWebService(int tipo) {
        request = new Request();
        request.setMethod("POST");
        request.setParametrosDatos("division", division.getDESCRIPCION());
        //0 = insert // 1 = update // 2 = delete
        if (tipo == 0) {
            request.setParametrosDatos("usuario_creador", division.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", division.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Division");
        } else if (tipo == 1) {
            request.setParametrosDatos("id_division", String.valueOf(division.getID_DIVISION()));
            request.setParametrosDatos("usuario_actualizacion", division.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", division.getFECHA_ACTUALIZACION());
            URL = URL + auxiliarGeneral.getUpdatePHP("Division");
        } else {
            request.setParametrosDatos("id_division", String.valueOf(division.getID_DIVISION()));
            request.setParametrosDatos("fecha_actualizacion", auxiliarGeneral.getFechaOficial());
            URL = URL + auxiliarGeneral.getDeletePHP("Division");
        }

        new TaskDivision().execute(request);
    }


    // enviar/editar articulo

    public class TaskDivision extends AsyncTask<Request, Boolean, Boolean> {
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Procesando...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Request... params) {
            int success;
            JSONObject json = null;
            boolean precessOK = true;
            //String UrlParsing = null;
            try {
                json = jsonParsing.parsingJsonObject(params[0], URL);
                if (json != null) {
                    success = json.getInt(TAG_SUCCESS);
                    mensaje = json.getString(TAG_MESSAGE);
                    if (success == 0) {
                        if (gestion == 0) {
                            int id = json.getInt(TAG_ID);
                            if (id > 0) {
                                if (controladorAdeful.insertDivisionAdeful(id, division)) {
                                    precessOK = true;
                                } else {
                                    precessOK = false;
                                }
                            } else {
                                precessOK = false;
                            }
                        } else if (gestion == 1) {
                            if (controladorAdeful.actualizarDivisionAdeful(division)) {
                                precessOK = true;
                            } else {
                                precessOK = false;
                            }
                        } else {
                            if (controladorAdeful.eliminarDivisionAdeful(division.getID_DIVISION())) {
                                precessOK = true;
                            } else {
                                precessOK = false;
                            }
                        }
                        precessOK = true;
                    } else {
                        precessOK = false;
                    }
                } else {
                    precessOK = false;
                    mensaje = "Error(4). Por favor comuniquese con el administrador.";
                }
            } catch (JSONException e) {
                precessOK = false;
                mensaje = "Error(5). Por favor comuniquese con el administrador.";
            }
            return precessOK;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();

            if (result) {
                if (gestion == 0) {
                    inicializarControles(GUARDAR);
                } else if (gestion == 1) {
                    gestion = 0;
                    inicializarControles(ACTUALIZAR);
                } else {
                    gestion = 0;
                    inicializarControles(ELIMINAR);
                }
            } else {
                auxiliarGeneral.errorWebService(getActivity(), mensaje);
            }
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
            if (editTextDivision.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Ingrese una división.",
                        Toast.LENGTH_SHORT).show();
            } else {

                if (gestion == 0) {
                    cargarEntidad(0, 0);
                } else if (gestion == 1) {
                    cargarEntidad(divisionArray.get(
                            posicion).getID_DIVISION(), 1);
                }
//				if (insertar) {
//
//					String usuario = "Administrador";
//			    	division = new Division(0, editTextDivision.getText()
//							.toString(), usuario, auxiliarGeneral.getFechaOficial(),
//							usuario, auxiliarGeneral.getFechaOficial());
//
//					if(controladorAdeful.insertDivisionAdeful(division)) {
//				    inicializarControles(GUARDAR_USUARIO);
//					}else{
//		            auxiliarGeneral.errorDataBase(getActivity());
//					}
//				} else {
//
//					String usuario = "Administrador";
//
//					division = new Division(divisionArray.get(posicion)
//							.getID_DIVISION(), editTextDivision.getText()
//							.toString(), null, null, usuario, auxiliarGeneral.getFechaOficial());
//
//					if(controladorAdeful.actualizarDivisionAdeful(division)) {
//						insertar = true;
//						inicializarControles(ACTUALIZAR_USUARIO);
//					}else{
//				    auxiliarGeneral.errorDataBase(getActivity());
//					}
//				}
            }
            return true;
        }
        if (id == android.R.id.home) {

            NavUtils.navigateUpFromSameTask(getActivity());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}