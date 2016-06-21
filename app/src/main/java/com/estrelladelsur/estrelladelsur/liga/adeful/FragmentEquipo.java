package com.estrelladelsur.estrelladelsur.liga.adeful;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
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
import com.estrelladelsur.estrelladelsur.auxiliar.UtilityImage;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerEquipo;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.webservice.JsonParsing;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentEquipo extends Fragment {
    private byte[] imagenEscudo = null;
    private RecyclerView recycleViewEquipo;
    private EditText editTextNombre;
    private ImageView imageEquipo;
    private TextInputLayout editTextInputDescripcion;
    private Equipo equipoAdeful;
    private ArrayList<Equipo> equipoAdefulArray;
    private AdaptadorRecyclerEquipo adaptador;
    private DialogoAlerta dialogoAlerta;
    private int gestion = 0;//0-insert //1-update//2-delete
    private int posicion, CheckedPositionFragment;
    private ControladorAdeful controladorAdeful;
    private String GUARDAR = "Equipo cargado correctamente";
    private String ACTUALIZAR = "Equipo actualizado correctamente";
    private String ELIMINAR = "Equipo eliminado correctamente";
    private Typeface editTextFont;
    private AuxiliarGeneral auxiliarGeneral;
    private String nombreEquipoAnterior = null, usuario = null, mensaje = null,
            url_escudo_equipo = null, encodedImage = null, URL = null;
    private Request request = new Request();
    private ProgressDialog dialog;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private JsonParsing jsonParsing = new JsonParsing(getActivity());
    private static final String TAG_ID = "id";

    public static FragmentEquipo newInstance() {
        FragmentEquipo fragment = new FragmentEquipo();
        return fragment;
    }

    public FragmentEquipo() {
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
        imageEquipo = (ImageView) v
                .findViewById(R.id.imageButtonEquipo_Cancha);
        editTextNombre = (EditText) v.findViewById(R.id.editTextDescripcion);
        editTextNombre.setTypeface(editTextFont);
        editTextInputDescripcion = (TextInputLayout) v.findViewById(R.id.editTextInputDescripcion);
        recycleViewEquipo = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        // imageButton que busca imagen del escudo del equipo en la memoria  interna
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());

        imageEquipo.setImageResource(R.mipmap.ic_escudo_cris);
        imageEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageDialogEquipo();
            }
        });
        editTextInputDescripcion.setHint("Ingrese el equipo");

        recycleViewEquipo.addOnItemTouchListener(new RecyclerTouchListener(
                getActivity(), recycleViewEquipo, new ClickListener() {

            @Override
            public void onLongClick(View view, final int position) {

                dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                        "Desea eliminar el equipo?", null, null);
                dialogoAlerta.btnAceptar.setText("Aceptar");
                dialogoAlerta.btnCancelar.setText("Cancelar");

                dialogoAlerta.btnAceptar
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                nombreEquipoAnterior = equipoAdefulArray.get(position).getNOMBRE_EQUIPO();
                                cargarEntidad(equipoAdefulArray
                                        .get(position)
                                        .getID_EQUIPO(), 3);
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
                gestion = 1;
                imageEquipo.setImageResource(R.mipmap.ic_escudo_cris);
                nombreEquipoAnterior = equipoAdefulArray.get(position)
                        .getNOMBRE_EQUIPO();
                editTextNombre.setText(nombreEquipoAnterior);
                if (equipoAdefulArray.get(position).getESCUDO() != null) {
                    Bitmap theImage = BitmapFactory
                            .decodeByteArray(
                                    equipoAdefulArray.get(position)
                                            .getESCUDO(), 0,
                                    equipoAdefulArray.get(position)
                                            .getESCUDO().length);
                    theImage = Bitmap.createScaledBitmap(theImage, 150,
                            150, true);
                    imageEquipo.setImageBitmap(theImage);
                    imagenEscudo = equipoAdefulArray.get(position)
                            .getESCUDO();
                }
                posicion = position;
            }
        }));
        recyclerViewLoadEquipo();
    }

    public void recyclerViewLoadEquipo() {

        recycleViewEquipo.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewEquipo.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewEquipo.setItemAnimator(new DefaultItemAnimator());

        equipoAdefulArray = controladorAdeful.selectListaEquipoAdeful();
        if (equipoAdefulArray != null) {
            adaptador = new AdaptadorRecyclerEquipo(equipoAdefulArray, getActivity());
            recycleViewEquipo.setAdapter(adaptador);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    public void ImageDialogEquipo() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                getActivity());
        myAlertDialog.setTitle("Galeria");
        myAlertDialog.setMessage("Seleccione un escudo");

        myAlertDialog.setPositiveButton("Galeria",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        UtilityImage.pictureActionIntent = new Intent(
                                Intent.ACTION_GET_CONTENT, null);
                        UtilityImage.pictureActionIntent.setType("image/*");
                        UtilityImage.pictureActionIntent.putExtra(
                                "return-data", true);
                        startActivityForResult(
                                UtilityImage.pictureActionIntent,
                                UtilityImage.GALLERY_PICTURE);
                    }
                });
        myAlertDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UtilityImage.GALLERY_PICTURE) {
            Bitmap b = auxiliarGeneral.SeleccionarImagen(data, getActivity(), true);
            if (b != null)
                imageEquipo.setImageBitmap(b);
            imagenEscudo = auxiliarGeneral.pasarBitmapByte(b);
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

    public void inicializarControles(String mensaje) {

        recyclerViewLoadEquipo();
        editTextNombre.setText("");
        if (imagenEscudo != null) {
            imageEquipo.setImageResource(R.mipmap.ic_escudo_cris);
        }
        Toast.makeText(getActivity(),
                mensaje,
                Toast.LENGTH_SHORT).show();
        imagenEscudo = null;
    }

    public void cargarEntidad(int id, int ws) {
        String nombreEquipo = null;
        nombreEquipo = editTextNombre.getText()
                .toString();
        String url_nombre_escudo = auxiliarGeneral.removeAccents(nombreEquipo.replace(" ", "").trim());

        URL = null;
        URL = auxiliarGeneral.getURL() + auxiliarGeneral.getURLEQUIPOADEFUL();
        url_escudo_equipo = URL + auxiliarGeneral.getURLESCUDOEQUIPOADEFUL() +
                auxiliarGeneral.getFechaFoto() + url_nombre_escudo + ".PNG";

        equipoAdeful = new Equipo(id, nombreEquipo, imagenEscudo, usuario,
                auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());

        envioWebService(ws);
    }

    public void envioWebService(int tipo) {
        request.setMethod("POST");
        request.setParametrosDatos("nombre_equipo", equipoAdeful.getNOMBRE_EQUIPO());

        if (imagenEscudo != null) {
            encodedImage = Base64.encodeToString(imagenEscudo,
                    Base64.DEFAULT);

            request.setParametrosDatos("escudo_equipo", encodedImage);
            request.setParametrosDatos("url_escudo", url_escudo_equipo);
        }
        //0 = insert // 1 = update // 2 = delete
        if (tipo == 0) {
            request.setParametrosDatos("usuario_creador", equipoAdeful.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", equipoAdeful.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Equipo");
        } else if (tipo == 1) {
            //   requestUrl.setParametrosDatos("URL", URL +auxiliarGeneral.getUpdatePHP("Equipo"));
            request.setParametrosDatos("id_equipo", String.valueOf(equipoAdeful.getID_EQUIPO()));
            request.setParametrosDatos("usuario_actualizacion", equipoAdeful.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", equipoAdeful.getFECHA_ACTUALIZACION());

            nombreEquipoAnterior = nombreEquipoAnterior.equals(equipoAdeful.getNOMBRE_EQUIPO()) ? "" : nombreEquipoAnterior;
            request.setParametrosDatos("nombre_anterior", nombreEquipoAnterior);
            URL = URL + auxiliarGeneral.getUpdatePHP("Equipo");
        } else {
            // requestUrl.setParametrosDatos("URL", URL +auxiliarGeneral.getDeletePHP("Equipo"));
            request.setParametrosDatos("id_equipo", String.valueOf(equipoAdeful.getID_EQUIPO()));
            request.setParametrosDatos("nombre_anterior", nombreEquipoAnterior);
            request.setParametrosDatos("fecha_actualizacion", auxiliarGeneral.getFechaOficial());
            URL = URL + auxiliarGeneral.getDeletePHP("Equipo");
        }

        new TaskEquipo().execute(request);
    }


    // enviar/editar articulo

    public class TaskEquipo extends AsyncTask<Request, Boolean, Boolean> {
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
                // json = jsonParsing.parsingJsonObject(params[0], URL);
                json = jsonParsing.parsingJsonObject(params[0], URL);
                if (json != null) {
                    success = json.getInt(TAG_SUCCESS);
                    mensaje = json.getString(TAG_MESSAGE);
                    if (success == 0) {
                        if (gestion == 0) {
                            int id = json.getInt(TAG_ID);
                            if (id > 0) {
                                if (controladorAdeful.insertEquipoAdeful(id, equipoAdeful)) {
                                    precessOK = true;
                                } else {
                                    precessOK = false;
                                }
                            } else {
                                precessOK = false;
                            }
                        } else if (gestion == 1) {
                            if (controladorAdeful.actualizarEquipoAdeful(equipoAdeful)) {
                                precessOK = true;
                            } else {
                                precessOK = false;
                            }
                        } else {
                            if (controladorAdeful.eliminarArticuloAdeful(equipoAdeful.getID_EQUIPO())) {
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

    @Override
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

            if (editTextNombre.getText().toString().equals("")) {
                Toast.makeText(getActivity(),
                        "Ingrese el nombre del equipo.", Toast.LENGTH_SHORT)
                        .show();
            } else {
                if (gestion == 0) {
                    cargarEntidad(0, 0);
                } else if (gestion == 1) {
                    cargarEntidad(equipoAdefulArray.get(
                            posicion).getID_EQUIPO(), 1);
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
}