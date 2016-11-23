package com.estrelladelsur.estrelladelsur.social.administrador;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.UtilityImage;
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Publicidad;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGeneric;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import java.io.ByteArrayOutputStream;


public class FragmentGenerarPublicidad extends Fragment implements MyAsyncTaskListener {

    private ControladorGeneral controladorGeneral;
    private ImageView imagePublicidad;
    private EditText tituloPublicidadEdit;
    private EditText otrosPublicidadEdit;
    private int CheckedPositionFragment;
    private byte[] imagePublicidadByte = null;
    private boolean insertar = true;
    private Publicidad publicidad;
    private boolean actualizar = false;
    private int idPublicidadExtra;
    private AuxiliarGeneral auxiliarGeneral;
    private Communicator communicator;
    private Typeface editTextFont;
    private String URL = null, usuario = null, encodedImage = null, nombre_foto_anterior = null;
    private Request request = new Request();
    private ImageButton rotateButton;
    int width = 150;
    int height = 150;

    public static FragmentGenerarPublicidad newInstance() {
        FragmentGenerarPublicidad fragment = new FragmentGenerarPublicidad();
        return fragment;
    }

    public FragmentGenerarPublicidad() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        if (state != null) {
            CheckedPositionFragment = state.getInt("curChoice", 0);
        } else {
            init();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_generar_foto,
                container, false);
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        editTextFont = auxiliarGeneral.textFont(getActivity());
        rotateButton = (ImageButton) v.findViewById(R.id.rotateButton);
        // PUBLICIDAD
        imagePublicidad = (ImageView) v.findViewById(R.id.imageFoto);
        // TITULO PUBLICIDAD
        tituloPublicidadEdit = (EditText) v
                .findViewById(R.id.tituloFotoEdit);
        tituloPublicidadEdit.setTypeface(editTextFont, Typeface.BOLD);
        // OTROS PUBLICIDAD
        otrosPublicidadEdit = (EditText) v
                .findViewById(R.id.otrosPublicidadEdit);
        otrosPublicidadEdit.setVisibility(View.VISIBLE);
        otrosPublicidadEdit.setTypeface(editTextFont);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
        controladorGeneral = new ControladorGeneral(getActivity());
        communicator = (Communicator) getActivity();
        usuario = "Administrador";

        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        //Metodo Extra
        if (actualizar) {
            idPublicidadExtra = getActivity().getIntent().getIntExtra("id_publicidad", 0);
            //TITULO
            tituloPublicidadEdit.setText(getActivity().getIntent()
                    .getStringExtra("titulo"));
            //LOGO
            imagePublicidadByte = getActivity().getIntent()
                    .getByteArrayExtra("logo");
            nombre_foto_anterior = getActivity().getIntent()
                    .getStringExtra("nombre_foto");
            if (imagePublicidadByte != null) {
                Bitmap  theImage = auxiliarGeneral.setByteToBitmap(imagePublicidadByte, width, height);
                imagePublicidad.setImageBitmap(theImage);
            } else {
                imagePublicidad.setImageResource(R.mipmap.ic_foto);
            }
            //OTROS
            otrosPublicidadEdit.setText(getActivity().getIntent()
                    .getStringExtra("otros"));
            insertar = false;
        }
        imagePublicidad.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ImageDialogPublicidad();
            }
        });

        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagePublicidadByte != null){
                    Bitmap theImage = auxiliarGeneral.setByteToBitmap(imagePublicidadByte, width, height);
                    theImage = auxiliarGeneral.RotateBitmap(theImage);
                    imagePublicidad.setImageBitmap(theImage);
                    imagePublicidadByte = auxiliarGeneral.pasarBitmapByte(theImage);
                }
            }
        });
    }

    public void ImageDialogPublicidad() {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                getActivity());
        myAlertDialog.setTitle("Galeria");
        myAlertDialog.setMessage("Seleccione una publicidad.");

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
            String path = auxiliarGeneral.selectImageForData(data, getActivity());
            Bitmap b = setResizaInage(path, width, height);
            if (b != null) {
                imagePublicidadByte = auxiliarGeneral.pasarBitmapByte(b);
                b = auxiliarGeneral.setByteToBitmap(imagePublicidadByte, width, height);
                imagePublicidad.setImageBitmap(b);
            }
        }
    }
    public Bitmap setResizaInage(String photoPath, int w, int h) {
        return auxiliarGeneral.getResizedBitmap(photoPath, w, h);
    }
    public void cargarEntidad(int id) {
        URL = null;
        URL = auxiliarGeneral.getURLPUBLICIDADALL();

        String titulo = tituloPublicidadEdit.getText().toString();
        String url_foto = null;
        String url_nombre_foto = null;
        String fechaFoto = null;
        String nombre_foto = null;

        if (imagePublicidadByte != null) {
            url_nombre_foto = auxiliarGeneral.removeAccents(titulo.replace(" ", "").trim());
            fechaFoto = auxiliarGeneral.getFechaImagen();
            nombre_foto = fechaFoto + url_nombre_foto + ".PNG";
            url_foto = auxiliarGeneral.getURLPUBLICIDADFOLDER() + nombre_foto;
        }
        publicidad = new Publicidad(id, titulo, imagePublicidadByte, otrosPublicidadEdit.getText()
                .toString(), nombre_foto, url_foto, usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());


        envioWebService();
    }

    public void envioWebService() {
        request.setMethod("POST");
        request.setParametrosDatos("titulo", publicidad.getTITULO());
        if(publicidad.getOTROS() != null)
        request.setParametrosDatos("otros", publicidad.getOTROS());

        if (imagePublicidadByte != null) {
            encodedImage = Base64.encodeToString(imagePublicidadByte,
                    Base64.DEFAULT);

            request.setParametrosDatos("foto", encodedImage);
            request.setParametrosDatos("url_foto",
                    publicidad.getURL_FOTO());
            request.setParametrosDatos("nombre_foto", publicidad.getNOMBRE_FOTO());
        }
        if (insertar) {
            request.setParametrosDatos("usuario_creador", publicidad.getUSUARIO_CREACION());
            request.setParametrosDatos("fecha_creacion", publicidad.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Publicidad");

        } else {
            request.setParametrosDatos("id_publicidad", String.valueOf(publicidad.getID_PUBLICIDAD()));
            request.setParametrosDatos("usuario_actualizacion", publicidad.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", publicidad.getFECHA_ACTUALIZACION());
            if (nombre_foto_anterior != null)
                request.setParametrosDatos("nombre_foto_anterior", nombre_foto_anterior);
            URL = URL + auxiliarGeneral.getUpdatePHP("Publicidad");
        }

        new AsyncTaskGeneric(getActivity(), this, URL, request, "Publicidad", publicidad, insertar, "a");
    }

    public void inicializarControles(String mensaje) {

        tituloPublicidadEdit.setText("");
        otrosPublicidadEdit.setText("");
        if (imagePublicidadByte != null) {
            imagePublicidad
                    .setImageResource(R.mipmap.ic_foto);
            imagePublicidadByte = null;
        }
        imagePublicidadByte = null;
        communicator.refresh();
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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
            if (tituloPublicidadEdit.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Ingrese un titulo.",
                        Toast.LENGTH_SHORT).show();
            } else if (imagePublicidadByte == null) {
                Toast.makeText(getActivity(), "Seleccione una publicidad.",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (insertar) {
                    cargarEntidad(0);
                } else {
                    cargarEntidad(idPublicidadExtra);
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
            if (insertar) {
                inicializarControles(mensaje);
            } else {
                actualizar = false;
                insertar = true;
                inicializarControles(mensaje);
            }
        } else {
            auxiliarGeneral.errorWebService(getActivity(), mensaje);
        }
    }
}