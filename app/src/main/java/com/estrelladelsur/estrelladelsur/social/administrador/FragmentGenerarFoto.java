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
import com.estrelladelsur.estrelladelsur.entidad.Foto;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGeneric;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import java.io.ByteArrayOutputStream;


public class FragmentGenerarFoto extends Fragment implements MyAsyncTaskListener {

    private ControladorGeneral controladorGeneral;
    private ImageView imageFoto;
    private EditText tituloFotoEdit;
    private int CheckedPositionFragment;
    private byte[] imageFotoByte = null;
    private boolean insertar = true;
    private Foto foto;
    private boolean actualizar = false;
    private int idFotoExtra;
    private AuxiliarGeneral auxiliarGeneral;
    private Communicator communicator;
    private Typeface editTextFont;
    private String URL = null, usuario = null, encodedImage = null, nombre_foto_anterior = null;
    private Request request = new Request();
    private ImageButton rotateButton;
    int width = 150;
    int height = 150;

    public static FragmentGenerarFoto newInstance() {
        FragmentGenerarFoto fragment = new FragmentGenerarFoto();
        return fragment;
    }

    public FragmentGenerarFoto() {
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
        // FOTO
        imageFoto = (ImageView) v.findViewById(R.id.imageFoto);
        // TITULO FOTO
        tituloFotoEdit = (EditText) v
                .findViewById(R.id.tituloFotoEdit);
        tituloFotoEdit.setTypeface(editTextFont, Typeface.BOLD);
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
        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        //Metodo Extra
        if (actualizar) {
            idFotoExtra = getActivity().getIntent().getIntExtra("id_foto", 0);
            //TITULO
            tituloFotoEdit.setText(getActivity().getIntent()
                    .getStringExtra("titulo"));
            //FOTO
            imageFotoByte = getActivity().getIntent()
                    .getByteArrayExtra("foto");
            nombre_foto_anterior = getActivity().getIntent()
                    .getStringExtra("nombre_foto");
            if (imageFotoByte != null) {
                Bitmap theImage = auxiliarGeneral.setByteToBitmap(imageFotoByte, width, height);
                imageFoto.setImageBitmap(theImage);
            } else {
                imageFoto.setImageResource(R.mipmap.ic_foto);
            }
            insertar = false;
        }
        imageFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageDialogFoto();
            }
        });

        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFotoByte != null) {
                    Bitmap theImage = auxiliarGeneral.setByteToBitmap(imageFotoByte, width, height);
                    theImage = auxiliarGeneral.RotateBitmap(theImage);
                    imageFoto.setImageBitmap(theImage);
                    imageFotoByte = auxiliarGeneral.pasarBitmapByte(theImage);
                }
            }
        });
    }

    public void ImageDialogFoto() {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                getActivity());
        myAlertDialog.setTitle("Galeria");
        myAlertDialog.setMessage("Seleccione una Foto.");

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
                imageFotoByte = auxiliarGeneral.pasarBitmapByte(b);
                b = auxiliarGeneral.setByteToBitmap(imageFotoByte, width, height);
                imageFoto.setImageBitmap(b);
            }

        }
    }

    public Bitmap setResizaInage(String photoPath, int w, int h) {
        return auxiliarGeneral.getResizedBitmap(photoPath, w, h);
    }

    public void inicializarControles(String mensaje) {

        tituloFotoEdit.setText("");
        if (imageFotoByte != null) {
            imageFoto
                    .setImageResource(R.mipmap.ic_foto);
            imageFotoByte = null;
        }
        imageFotoByte = null;
        communicator.refresh();
        showMessage(mensaje);
    }

    public void cargarEntidad(int id) {
        URL = null;
        URL = auxiliarGeneral.getURLFOTOALL();

        String titulo = tituloFotoEdit.getText().toString();
        String url_foto = null;
        String url_nombre_foto = null;
        String fechaFoto = null;
        String nombre_foto = null;
        if (imageFotoByte != null) {
            url_nombre_foto = auxiliarGeneral.removeAccents(titulo.replace(" ", "").trim());
            fechaFoto = auxiliarGeneral.getFechaImagen();
            nombre_foto = fechaFoto + url_nombre_foto + ".PNG";
            url_foto = auxiliarGeneral.getURLFOTOFOLDER() + nombre_foto;
        }
        foto = new Foto(id, titulo, imageFotoByte, nombre_foto, url_foto,
                usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());

        envioWebService();
    }

    public void envioWebService() {
        request.setMethod("POST");
        request.setParametrosDatos("titulo", foto.getTITULO());

        if (imageFotoByte != null) {
            encodedImage = Base64.encodeToString(imageFotoByte,
                    Base64.DEFAULT);

            request.setParametrosDatos("foto", encodedImage);
            request.setParametrosDatos("url_foto",
                    foto.getURL_FOTO());
            request.setParametrosDatos("nombre_foto", foto.getNOMBRE_FOTO());

        }
        if (insertar) {
            request.setParametrosDatos("usuario_creador", foto.getUSUARIO_CREACION());
            request.setParametrosDatos("fecha_creacion", foto.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Foto");

        } else {
            request.setParametrosDatos("id_foto", String.valueOf(foto.getID_FOTO()));
            request.setParametrosDatos("usuario_actualizacion", foto.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", foto.getFECHA_ACTUALIZACION());
            if (nombre_foto_anterior != null)
                request.setParametrosDatos("nombre_foto_anterior", nombre_foto_anterior);
            URL = URL + auxiliarGeneral.getUpdatePHP("Foto");
        }

        new AsyncTaskGeneric(getActivity(), this, URL, request, "Foto", foto, insertar, "a");
    }

    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg,
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

            if (tituloFotoEdit.getText().toString().equals("")) {
                showMessage("Ingrese un titulo.");
            } else if (imageFotoByte == null) {
                showMessage("Seleccione una foto.");
            } else {
                if (insertar) {
                    cargarEntidad(0);
                } else {
                    cargarEntidad(idFotoExtra);
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