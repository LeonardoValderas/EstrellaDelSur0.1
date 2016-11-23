package com.estrelladelsur.estrelladelsur.liga.administrador.adeful;

import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.UtilityImage;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerEquipo;
import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGeneric;
import com.estrelladelsur.estrelladelsur.webservice.Request;

public class FragmentEquipoAdeful extends Fragment implements MyAsyncTaskListener {
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
    private Typeface editTextFont;
    private AuxiliarGeneral auxiliarGeneral;
    private String nombreEscudoAnterior = null, usuario = null,
            url_escudo_equipo = null, encodedImage = null, URL = null;
    private Request request;
    private String url_nombre_escudo;
    private String nombreEquipo;
    private boolean isDelete = false;
    private boolean isInsert = true;
    private ImageButton rotateButton;

    public static FragmentEquipoAdeful newInstance() {
        FragmentEquipoAdeful fragment = new FragmentEquipoAdeful();
        return fragment;
    }

    public FragmentEquipoAdeful() {
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
        rotateButton = (ImageButton) v.findViewById(R.id.rotateButton);
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
                                gestion = 2;
                                isDelete = true;
                                url_nombre_escudo = equipoAdefulArray.get(position).getNOMBRE_ESCUDO();
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
                isInsert = false;
                imageEquipo.setImageResource(R.mipmap.ic_escudo_cris);
                nombreEscudoAnterior = equipoAdefulArray.get(position)
                        .getNOMBRE_ESCUDO();
                editTextNombre.setText(equipoAdefulArray.get(position).getNOMBRE_EQUIPO());
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

        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagenEscudo != null){
                    Bitmap theImage = auxiliarGeneral.setByteToBitmap(imagenEscudo, 150,
                            150);
                    theImage = auxiliarGeneral.RotateBitmap(theImage);
                    imageEquipo.setImageBitmap(theImage);
                    imagenEscudo = auxiliarGeneral.pasarBitmapByte(theImage);
                }
            }
        });
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

    @Override
    public void onPostExecuteConcluded(boolean result, String mensaje) {
        if (result) {
            if (isDelete) {
                isDelete = false;
                gestion = 0;
                inicializarControles(mensaje);
            } else {
                if (isInsert) {
                    inicializarControles(mensaje);
                } else {
                    isInsert = true;
                    gestion = 0;
                    inicializarControles(mensaje);
                }
            }
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
        URL = null;
        URL = auxiliarGeneral.getURL() + auxiliarGeneral.getURLEQUIPOADEFUL();
        if (ws != 2) {
            nombreEquipo = null;
            nombreEquipo = editTextNombre.getText()
                    .toString();
            if (imagenEscudo != null) {
                url_nombre_escudo = auxiliarGeneral.getFechaImagen() + auxiliarGeneral.removeAccents(nombreEquipo.replace(" ", "").trim()) + ".PNG";
                url_escudo_equipo = URL + auxiliarGeneral.getURLESCUDOEQUIPOADEFUL() +
                        url_nombre_escudo;
            }
        }
        equipoAdeful = new Equipo(id, nombreEquipo, url_nombre_escudo, imagenEscudo, url_escudo_equipo, usuario,
                auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());

        envioWebService(ws);
    }

    public void envioWebService(int tipo) {
        request = new Request();
        request.setMethod("POST");


        if (imagenEscudo != null) {
            encodedImage = null;
            encodedImage = Base64.encodeToString(imagenEscudo,
                    Base64.DEFAULT);

            request.setParametrosDatos("escudo_equipo", encodedImage);
            request.setParametrosDatos("url_escudo", url_escudo_equipo);
            request.setParametrosDatos("nombre_escudo", url_nombre_escudo);
        }
        //0 = insert // 1 = update // 2 = delete
        if (tipo == 0) {
            isInsert = true;
            request.setParametrosDatos("nombre_equipo", equipoAdeful.getNOMBRE_EQUIPO());
            request.setParametrosDatos("usuario_creador", equipoAdeful.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", equipoAdeful.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Equipo");
        } else if (tipo == 1) {
            isInsert = false;
            request.setParametrosDatos("nombre_equipo", equipoAdeful.getNOMBRE_EQUIPO());
            request.setParametrosDatos("id_equipo", String.valueOf(equipoAdeful.getID_EQUIPO()));
            request.setParametrosDatos("usuario_actualizacion", equipoAdeful.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", equipoAdeful.getFECHA_ACTUALIZACION());
            if(nombreEscudoAnterior != null)
            request.setParametrosDatos("nombre_escudo_anterior", nombreEscudoAnterior);
            URL = URL + auxiliarGeneral.getUpdatePHP("Equipo");
        } else {
            isDelete = true;
            request.setParametrosDatos("id_equipo", String.valueOf(equipoAdeful.getID_EQUIPO()));
            if (url_nombre_escudo != null)
                request.setParametrosDatos("nombre_escudo", url_nombre_escudo);
            request.setParametrosDatos("fecha_actualizacion", auxiliarGeneral.getFechaOficial());
            URL = URL + auxiliarGeneral.getDeletePHP("Equipo");
        }
        new AsyncTaskGeneric(getContext(), this, URL, request, "Equipo", equipoAdeful, isInsert, isDelete, equipoAdeful.getID_EQUIPO(), "o", false);

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
        menu.getItem(1).setVisible(false);// posicion
        menu.getItem(2).setVisible(false);// cargo
        // menu.getItem(3).setVisible(false);//cerrar
        // menu.getItem(4).setVisible(false);// guardar
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