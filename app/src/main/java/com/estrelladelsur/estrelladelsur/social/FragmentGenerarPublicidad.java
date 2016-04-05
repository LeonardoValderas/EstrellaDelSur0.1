package com.estrelladelsur.estrelladelsur.social;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.UtilityImage;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoMenuLista;
import com.estrelladelsur.estrelladelsur.entidad.Foto;
import com.estrelladelsur.estrelladelsur.entidad.Publicidad;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;


public class FragmentGenerarPublicidad extends Fragment {

    private ControladorAdeful controladorAdeful;
    private ImageView imagePublicidad;
    private EditText tituloPublicidadEdit;
    private EditText otrosPublicidadEdit;
    private int CheckedPositionFragment;
    private ByteArrayOutputStream baos;
    private byte[] imagePublicidadByte = null;
    private boolean insertar = true;
    private Publicidad publicidad;
    private ArrayList<Publicidad> publicidadArray;
    private DialogoAlerta dialogoAlerta;
    private DialogoAlerta dialogoAlertaEditar;
    private DialogoMenuLista dialogoMenuLista;
    private boolean actualizar = false;
    private int idPublicidadExtra;
    private String nombre;
    private String usuario = "Administrador";
    private String fechaCreacion = null;
    private String fechaActualizacion = null;
    private AuxiliarGeneral auxiliarGeneral;
    private String GUARDAR_TOAST = "Publicidad cargada correctamente";
    private String ACTUALIZAR_TOAST = "Publicidad actualizada correctamente";
    private Communicator communicator;

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
        // PUBLICIDAD
        imagePublicidad = (ImageView) v.findViewById(R.id.imageFoto);
        // TITULO PUBLICIDAD
        tituloPublicidadEdit = (EditText) v
                .findViewById(R.id.tituloFotoEdit);
        // OTROS PUBLICIDAD
        otrosPublicidadEdit = (EditText) v
                .findViewById(R.id.otrosPublicidadEdit);
        otrosPublicidadEdit.setVisibility(View.VISIBLE);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        controladorAdeful = new ControladorAdeful(getActivity());
        communicator = (Communicator)getActivity();
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        usuario = "Administrador";
        fechaCreacion = controladorAdeful.getFechaOficial();
        fechaActualizacion = fechaCreacion;
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
            if (imagePublicidadByte != null) {
            Bitmap theImage = BitmapFactory.decodeByteArray(
                    imagePublicidadByte, 0,
                    imagePublicidadByte.length);
            theImage = Bitmap.createScaledBitmap(theImage, 150, 150, true);

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
            SeleccionarImagen(data);
        }
    }

    public static Bitmap createDrawableFromView(View view) {
        view.setDrawingCacheEnabled(true);
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }

    public void SeleccionarImagen(Intent data) {
        try {
            UtilityImage.uri = data.getData();
            if (UtilityImage.uri != null) {

                Cursor cursor = getActivity().getContentResolver().query(
                        UtilityImage.uri, null, null, null, null);

                cursor.moveToFirst();
                String document_id = cursor.getString(0);
                document_id = document_id.substring(document_id
                        .lastIndexOf(":") + 1);

                cursor = getActivity()
                        .getContentResolver()
                        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                null, MediaStore.Images.Media._ID + " = ? ",
                                new String[]{document_id}, null);
                cursor.moveToFirst();
                String path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();

                // Assign string path to File
                UtilityImage.Default_DIR = new File(path);

                // Create new dir MY_IMAGES_DIR if not created and copy image
                // into that dir and store that image path in valid_photo
                UtilityImage.Create_MY_IMAGES_DIR();

                // Copy your image
                UtilityImage.copyFile(UtilityImage.Default_DIR,
                        UtilityImage.MY_IMG_DIR);

                // Get new image path and decode it
                Bitmap b = UtilityImage
                        .decodeFile(UtilityImage.Paste_Target_Location);

                // use new copied path and use anywhere
                String valid_photo = UtilityImage.Paste_Target_Location
                        .toString();
                b = Bitmap.createScaledBitmap(b, 150, 150, true);

                // set your selected image in image view
                imagePublicidad.setImageBitmap(b);
                cursor.close();

                baos = new ByteArrayOutputStream();
                b.compress(CompressFormat.PNG, 0, baos);
                imagePublicidadByte = baos.toByteArray();

            } else {
                Toast toast = Toast.makeText(getActivity(),
                        "No se selecciono una publicidad.", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception e) {
            // you get this when you will not select any single image
            Log.e("onActivityResult", "" + e);
        }
    }
    public void setBotonGuardar(String mensaje){

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
        // menu.getItem(1).setVisible(false);//permiso
        // menu.getItem(2).setVisible(false);//lifuba
        menu.getItem(3).setVisible(false);// adeful
        menu.getItem(4).setVisible(false);// puesto
        // menu.getItem(5).setVisible(false);// posicion
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

         if (tituloPublicidadEdit.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Ingrese un titulo.",
                        Toast.LENGTH_SHORT).show();
         } else if(imagePublicidadByte == null) {
             Toast.makeText(getActivity(), "Seleccione una publicidad.",
                     Toast.LENGTH_SHORT).show();
         }else{
                if (insertar) {

                    publicidad = new Publicidad(0, tituloPublicidadEdit.getText()
                            .toString(), imagePublicidadByte, otrosPublicidadEdit.getText()
                            .toString(), usuario, fechaCreacion, usuario, fechaActualizacion);

                    if (controladorAdeful.insertPublicidadAdeful(publicidad)) {
                        setBotonGuardar(GUARDAR_TOAST);
                    } else {
                    auxiliarGeneral.errorDataBase(getActivity());
                    }

                } else {
                    publicidad = new Publicidad(idPublicidadExtra, tituloPublicidadEdit.getText()
                            .toString(), imagePublicidadByte,otrosPublicidadEdit.getText()
                            .toString(), null, null, usuario, fechaActualizacion);
                    if (controladorAdeful.actualizarPublicidadAdeful(publicidad)) {
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