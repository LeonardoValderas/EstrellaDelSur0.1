package com.estrelladelsur.estrelladelsur.social;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.UtilityImage;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.database.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Foto;
import java.io.ByteArrayOutputStream;


public class FragmentGenerarFoto extends Fragment {

    private ControladorGeneral controladorGeneral;
    private ImageView imageFoto;
    private EditText tituloFotoEdit;
    private int CheckedPositionFragment;
    private ByteArrayOutputStream baos;
    private byte[] imageFotoByte = null;
    private boolean insertar = true;
    private Foto foto;
    private boolean actualizar = false;
    private int idFotoExtra;
    private String usuario = "Administrador";
    private AuxiliarGeneral auxiliarGeneral;
    static int w = 250;
    static int h = 250;
    private String GUARDAR_TOAST = "Foto cargada correctamente";
    private String ACTUALIZAR_TOAST = "Foto actualizada correctamente";
    private Communicator communicator;
    private Typeface editTextFont;

    public static FragmentGenerarFoto newInstance() {
        FragmentGenerarFoto fragment = new FragmentGenerarFoto();
        return fragment;
    }
    public FragmentGenerarFoto() {
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
        controladorGeneral = new ControladorGeneral(getActivity());
        communicator = (Communicator)getActivity();
        usuario = "Administrador";

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
            if (imageFotoByte != null) {
            Bitmap theImage = BitmapFactory.decodeByteArray(
                    imageFotoByte, 0,
                    imageFotoByte.length);
            theImage = Bitmap.createScaledBitmap(theImage, 150, 150, true);

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

            Bitmap b = auxiliarGeneral.SeleccionarImagen(data, getActivity(),false);
            if (b != null)
                imageFoto.setImageBitmap(b);
            imageFotoByte = auxiliarGeneral.pasarBitmapByte(b);
        }
    }

    public void setBotonGuardar(String mensaje){

        tituloFotoEdit.setText("");
        if (imageFotoByte != null) {
            imageFoto
                    .setImageResource(R.mipmap.ic_foto);
            imageFotoByte = null;
        }
        imageFotoByte = null;
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
        //menu.getItem(0).setVisible(false);//usuario
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

//            Intent usuario = new Intent(getActivity(),
//                    NavigationDrawerUsuario.class);
//            startActivity(usuario);
            return true;
        }

        if (id == R.id.action_permisos) {
            return true;
        }

        if (id == R.id.action_guardar) {

         if (tituloFotoEdit.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Ingrese un titulo.",
                        Toast.LENGTH_SHORT).show();
         } else if(imageFotoByte == null) {
             Toast.makeText(getActivity(), "Seleccione una foto.",
                     Toast.LENGTH_SHORT).show();
         }else{
                if (insertar) {

                    foto = new Foto(0, tituloFotoEdit.getText()
                            .toString(), imageFotoByte,
                            usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());

                    if (controladorGeneral.insertFoto(foto)) {
                        setBotonGuardar(GUARDAR_TOAST);
                    } else {
                    auxiliarGeneral.errorDataBase(getActivity());
                    }

                } else {
                    foto = new Foto(idFotoExtra, tituloFotoEdit.getText()
                            .toString(), imageFotoByte,
                           null, null, usuario, auxiliarGeneral.getFechaOficial());
                    if (controladorGeneral.actualizarFoto(foto)) {
                        setBotonGuardar(ACTUALIZAR_TOAST);
                        actualizar = false;
                        insertar = true;
                    } else {
                        auxiliarGeneral.errorDataBase(getActivity());
                    }
                }
            }
            return true;
        }
        if (id == R.id.action_lifuba) {

            return true;
        }
        if (id == android.R.id.home) {

            NavUtils.navigateUpFromSameTask(getActivity());

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}