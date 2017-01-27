package com.estrelladelsur.estrelladelsur.liga.administrador.lifuba;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdaptadorRecyclerEquipo;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.auxiliar.UtilityImage;
import com.estrelladelsur.estrelladelsur.database.administrador.lifuba.ControladorLifuba;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGenericLifuba;
import com.estrelladelsur.estrelladelsur.webservice.Request;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class FragmentEquipoLifuba extends Fragment implements MyAsyncTaskListener {

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
    private ControladorLifuba controladorLifuba;
    private Typeface editTextFont;
    private AuxiliarGeneral auxiliarGeneral;
    private String nombreEscudoAnterior = null, usuario = null,
            url_escudo_equipo = "", encodedImage = null, URL = null, nombre_equipo = "";
    private Request request;
    private String url_nombre_escudo;
    private String nombreEquipo;
    private boolean isDelete = false;
    private boolean isInsert = true;
    private static final int PERMISSION_REQUEST_CODE = 1;

    public static FragmentEquipoLifuba newInstance() {
        FragmentEquipoLifuba fragment = new FragmentEquipoLifuba();
        return fragment;
    }

    public FragmentEquipoLifuba() {
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        controladorLifuba = new ControladorLifuba(getActivity());
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

    @Override
    public void onResume() {
        super.onResume();
        controladorLifuba = new ControladorLifuba(getActivity());
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
        recyclerViewLoadEquipo();
    }

    private void init() {
        // imageButton que busca imagen del escudo del equipo en la memoria  interna
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());

        imageEquipo.setImageResource(R.mipmap.ic_escudo_cris);
        imageEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!auxiliarGeneral.checkPermission(getActivity()))
                    auxiliarGeneral.showDialogPermission(getActivity(), getActivity());
                else
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
                nombreEscudoAnterior = equipoAdefulArray.get(position)
                        .getNOMBRE_ESCUDO();
                nombre_equipo = equipoAdefulArray.get(position).getNOMBRE_EQUIPO();
                editTextNombre.setText(nombre_equipo);
                url_escudo_equipo = equipoAdefulArray.get(position).getURL_ESCUDO();

                if (!url_escudo_equipo.isEmpty())
                    Picasso.with(getActivity())
                            .load(url_escudo_equipo).fit()
                            .placeholder(R.mipmap.ic_escudo_cris)
                            .into(imageEquipo, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {
                                    imageEquipo.setImageResource(R.mipmap.ic_escudo_cris);
                                }
                            });
                else
                    Picasso.with(getActivity())
                            .load(R.mipmap.ic_escudo_cris).fit()
                            .placeholder(R.mipmap.ic_escudo_cris)
                            .into(imageEquipo);
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

        equipoAdefulArray = controladorLifuba.selectListaEquipoLifuba();
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

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UtilityImage.GALLERY_PICTURE) {
            //  if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(getActivity(), data);
            startCropImageActivity(imageUri);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK) {
                String p = auxiliarGeneral.compressImage(getActivity(), result.getUri().toString());
                Bitmap bitmap = auxiliarGeneral.asignateImage(p);
                asignateBitmap(bitmap);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                if (!result.getError().toString().contains("ENOENT"))
                    Toast.makeText(getActivity(), "Error al asignar imagen: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void asignateBitmap(Bitmap photoBitmap) {
        if (photoBitmap != null) {
            imageEquipo.setImageBitmap(photoBitmap);
            imagenEscudo = auxiliarGeneral.pasarBitmapByte(photoBitmap);
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(getContext(), FragmentEquipoLifuba.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_CODE) {
            return;
        }
        boolean isGranted = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
                break;
            }
        }
        if (!isGranted)
            auxiliarGeneral.showDialogPermission(getActivity(), getActivity());
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
        url_nombre_escudo = "";
        url_escudo_equipo = "";
        nombre_equipo = "";
        imageEquipo.setImageResource(R.mipmap.ic_escudo_cris);
        Toast.makeText(getActivity(),
                mensaje,
                Toast.LENGTH_SHORT).show();
        imagenEscudo = null;
    }

    public void cargarEntidad(int id, int ws) {
        URL = null;
        URL = auxiliarGeneral.getURLEQUIPOLIFUBAALL();
        if (ws != 2) {
            nombreEquipo = null;
            nombreEquipo = editTextNombre.getText()
                    .toString();
            if (imagenEscudo != null) setUrlFoto();
            else if (!url_escudo_equipo.isEmpty()) {
                if (nombre_equipo.compareTo(nombreEquipo) == 0)
                    nombreEscudoAnterior = null;
                else
                    setUrlFoto();
            }
        }
        equipoAdeful = new Equipo(id, nombreEquipo, url_nombre_escudo, imagenEscudo, url_escudo_equipo, usuario,
                auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());

        envioWebService(ws);
    }

    public void setUrlFoto() {
        url_nombre_escudo = auxiliarGeneral.getFechaImagen() + auxiliarGeneral.removeAccents(nombreEquipo.replace(" ", "").trim()) + ".PNG";
        url_escudo_equipo = URL + auxiliarGeneral.getURLESCUDOEQUIPO() +
                url_nombre_escudo;
    }
    public void envioWebService(int tipo) {
        String fecha = auxiliarGeneral.getFechaOficial();
        request = new Request();
        request.setMethod("POST");


        if (imagenEscudo != null) {
            encodedImage = null;
            encodedImage = Base64.encodeToString(imagenEscudo,
                    Base64.DEFAULT);

            request.setParametrosDatos("escudo_equipo", encodedImage);
            request.setParametrosDatos("url_escudo", url_escudo_equipo);
            request.setParametrosDatos("nombre_escudo", url_nombre_escudo);
        } else if(!url_escudo_equipo.isEmpty()){
            request.setParametrosDatos("escudo_equipo", "222");
            request.setParametrosDatos("url_escudo",
                    url_escudo_equipo);
            request.setParametrosDatos("nombre_escudo", url_nombre_escudo);
        } else{
            request.setParametrosDatos("escudo_equipo", "222");
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
            if (nombreEscudoAnterior != null)
                request.setParametrosDatos("nombre_escudo_anterior", nombreEscudoAnterior);
            URL = URL + auxiliarGeneral.getUpdatePHP("Equipo");
        } else {
            isDelete = true;
            request.setParametrosDatos("id_equipo", String.valueOf(equipoAdeful.getID_EQUIPO()));
            if (url_nombre_escudo != null)
                request.setParametrosDatos("nombre_escudo", url_nombre_escudo);
            request.setParametrosDatos("fecha_actualizacion", fecha);
            URL = URL + auxiliarGeneral.getDeletePHP("Equipo");
        }
        if (auxiliarGeneral.isNetworkAvailable(getActivity())) {
            if (isDelete)
                new AsyncTaskGenericLifuba(getContext(), this, URL, request, "Equipo", equipoAdeful, isInsert, isDelete, equipoAdeful.getID_EQUIPO(), "o", false, fecha);
            else
                new AsyncTaskGenericLifuba(getContext(), this, URL, request, "Equipo", equipoAdeful, isInsert, isDelete, equipoAdeful.getID_EQUIPO(), "o", false);

        } else
            auxiliarGeneral.errorWebService(getActivity(), getActivity().getResources().getString(R.string.error_without_internet));
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
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