package com.estrelladelsur.estrelladelsur.institucion;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.UtilityImage;
import com.estrelladelsur.estrelladelsur.abstracta.Cargo;
import com.estrelladelsur.estrelladelsur.abstracta.Comision;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerCargoComision;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoMenuLista;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class FragmentGenerarComision extends Fragment {

    private int CheckedPositionFragment;
    private ImageView fotoImageComision;
    private EditText nombreEditComision;
    private Spinner puestoSpinnerComision;
    private Button desdeButtonComision;
    private Button hastaButtonComision;
    private ControladorAdeful controladorAdeful;
    private boolean insertar = true;
    private Cargo cargo;
    private Cargo cargoSpinner;
    private Communicator communicator;
    private boolean actualizar = false;
    private int idComisionExtra;
    private String fechaCreacionExtra;
    private ArrayList<Cargo> cargoArray;
    private AdapterSpinnerCargoComision adapterSpinnerCargoComision;
    private DialogoAlerta dialogoAlerta;
    private DialogoAlerta dialogoAlertaEditar;
    private DialogoMenuLista dialogoMenuLista;
    private ArrayAdapter<Cargo> adapterList;
    private byte[] imageComision;
    private ByteArrayOutputStream baos;
    private Comision comision;
    private ArrayList<Comision> comisionArray;
    private DateFormat formate = DateFormat.getDateInstance();
    private Calendar calendar = Calendar.getInstance();
    private boolean botonFecha = false;

    public static FragmentGenerarComision newInstance() {
        FragmentGenerarComision fragment = new FragmentGenerarComision();
        return fragment;
    }

    public FragmentGenerarComision() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

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
        View v = inflater.inflate(R.layout.fragment_generar_comision, container, false);
        //FOTO INTEGRANTE
        fotoImageComision = (ImageView) v
                .findViewById(R.id.fotoImageComision);
        //NOMBRE INTEGRANTE
        nombreEditComision = (EditText) v
                .findViewById(R.id.nombreEditComision);
        //CARGO SPINNER INTEGRANTE
        puestoSpinnerComision = (Spinner) v
                .findViewById(R.id.puestoSpinnerComision);
        //DESDE
        desdeButtonComision = (Button) v
                .findViewById(R.id.desdeEditComision);
        //HASTA
        hastaButtonComision = (Button) v
                .findViewById(R.id.hastaEditComision);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        // VER DONDE EJECUCTAR ESTA LINEA
        controladorAdeful = new ControladorAdeful(getActivity());

        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        //Metodo Extra
        if (actualizar) {

            idComisionExtra = getActivity().getIntent().getIntExtra("id_comision", 0);
   /*         nombreEditComision.setText(getActivity().getIntent()
                    .getStringExtra("nombre"));
            desdeButtonComision.setText(getActivity().getIntent()
                    .getStringExtra("desde"));
            hastaButtonComision.setText(getActivity().getIntent()
                    .getStringExtra("hasta"));*/

            // COMISION POR ID
            controladorAdeful.abrirBaseDeDatos();
            comisionArray = controladorAdeful.selectComisionAdeful(idComisionExtra);
            controladorAdeful.cerrarBaseDeDatos();

            nombreEditComision.setText(comisionArray.get(0).getNOMBRE_COMISION().toString());
            desdeButtonComision.setText(comisionArray.get(0).getPERIODO_DESDE().toString());
            hastaButtonComision.setText(comisionArray.get(0).getPERIODO_HASTA().toString());
            imageComision = comisionArray.get(0).getFOTO_COMISION();

            if (imageComision != null) {
                Bitmap theImage = BitmapFactory.decodeByteArray(imageComision, 0,
                        imageComision.length);
                theImage = Bitmap.createScaledBitmap(theImage, 150, 150, true);
                fotoImageComision.setImageBitmap(theImage);
            } else {
                 fotoImageComision.setImageResource(android.R.drawable.ic_menu_my_calendar);
            }

            insertar = false;
        }
        // LOAD SPINNER
        loadSpinnerCargo();

        // CLICK IMAGEN
        fotoImageComision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Alerta galeria
                ImageDialogComision();

            }
        });

        desdeButtonComision.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                botonFecha = true;
                setDate();

            }
        });
        hastaButtonComision.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                botonFecha = false;
                setDate();

            }
        });
    }

    //Alerta galeria
    public void ImageDialogComision() {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                getActivity());
        myAlertDialog.setTitle("Galeria");
        myAlertDialog.setMessage("Seleccione una Foto.");

        myAlertDialog.setPositiveButton("Gallery",
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
            // data contains result
            // Do some task
            SeleccionarImagen(data);
        }

    }

    public static Bitmap createDrawableFromView(View view) {

        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
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
                        .query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
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

                // set bitmap a imagenView
                fotoImageComision.setImageBitmap(b);
                cursor.close();

                //Pasar bitmap a byte[]
                baos = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 0, baos);
                imageComision = baos.toByteArray();

            } else {
                Toast toast = Toast.makeText(getActivity(),
                        "No se selecciono una foto.", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception e) {
            // you get this when you will not select any single image
            Log.e("onActivityResult", "" + e);

        }
    }

    public ArrayList<Cargo> selectCargoList() {

        // CARGO
        controladorAdeful.abrirBaseDeDatos();
        cargoArray = controladorAdeful.selectListaCargoAdeful();
        controladorAdeful.cerrarBaseDeDatos();
        return cargoArray;
    }

    // POPULATION SPINNER
    public void loadSpinnerCargo() {

        // CARGO SPINNER
        adapterSpinnerCargoComision = new AdapterSpinnerCargoComision(getActivity(),
                R.layout.simple_spinner_dropdown_item, selectCargoList());
        puestoSpinnerComision.setAdapter(adapterSpinnerCargoComision);

    }

    // POPULATION LISTVIEW
    public void loadListViewMenu() {

        adapterList = new ArrayAdapter<Cargo>(getActivity(),
                R.layout.listview_item_dialogo, R.id.textViewGeneral, selectCargoList());
        dialogoMenuLista.listViewGeneral.setAdapter(adapterList);
    }

    public void dateDesde() {

        desdeButtonComision.setText(formate.format(calendar.getTime()));

    }

    public void dateHasta() {

        hastaButtonComision.setText(formate.format(calendar.getTime()));

    }

    public void setDate() {

        new DatePickerDialog(getActivity(), d, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            if (botonFecha) {
                dateDesde();
            } else {

                dateHasta();
            }
        }
    };

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
        menu.getItem(5).setVisible(false);// posicion
        // menu.getItem(6).setVisible(false);// cargo
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

            cargoSpinner = (Cargo) puestoSpinnerComision.getSelectedItem();
            String usuario = "Administrador";
            String fechaCreacion = controladorAdeful.getFechaOficial();
            String fechaActualizacion = fechaCreacion;

            if (puestoSpinnerComision.getSelectedItem() == null) {
                Toast.makeText(getActivity(), "Debe agregar un Cargo(Menu-Cargo).",
                        Toast.LENGTH_SHORT).show();

            } else if (nombreEditComision.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Ingrese el nombre del integrante de la comisión.",
                        Toast.LENGTH_SHORT).show();
            } else if (desdeButtonComision.getText().toString().equals("Desde") || hastaButtonComision.getText().toString().equals("Hasta")) {
                Toast.makeText(getActivity(), "Complete el periodo del cargo.",
                        Toast.LENGTH_SHORT).show();
            } else if (insertar) {

                comision = new Comision(0, nombreEditComision.getText().toString(),
                        imageComision, cargoSpinner.getID_CARGO(),null, desdeButtonComision.getText().toString(),
                        hastaButtonComision.getText().toString(), usuario, fechaCreacion, usuario, fechaActualizacion);

                controladorAdeful.abrirBaseDeDatos();
                if (controladorAdeful.insertComisionAdeful(comision)) {

                    controladorAdeful.cerrarBaseDeDatos();
                    nombreEditComision.setText("");
                    desdeButtonComision.setText("Desde");
                    hastaButtonComision.setText("Hasta");
                    imageComision = null;
                    fotoImageComision.setImageResource(android.R.drawable.ic_menu_my_calendar);
                   // communicator.refresh();
                    Toast.makeText(getActivity(), "Integrante ingresado correctamente",
                            Toast.LENGTH_SHORT).show();
                } else {
                    controladorAdeful.cerrarBaseDeDatos();
                    Toast.makeText(getActivity(), "Error en la base de datos interna, vuelva a intentar." +
                                    "\n Si el error persiste comuniquese con soporte.",
                            Toast.LENGTH_SHORT).show();

                }
            } else { //COMISION ACTUALIZAR


                comision = new Comision(idComisionExtra, nombreEditComision.getText().toString(),
                        imageComision, cargoSpinner.getID_CARGO(), null, desdeButtonComision.getText().toString(),
                        hastaButtonComision.getText().toString(), usuario, fechaCreacion, usuario, fechaActualizacion);

                controladorAdeful.abrirBaseDeDatos();
                if(controladorAdeful.actualizarComisionAdeful(comision)){
                controladorAdeful.cerrarBaseDeDatos();

                nombreEditComision.setText("");
                desdeButtonComision.setText("Desde");
                hastaButtonComision.setText("Hasta");
                imageComision = null;
                fotoImageComision.setImageResource(android.R.drawable.ic_menu_my_calendar);

                actualizar = false;
                insertar = true;
                Toast.makeText(getActivity(), "Integrante actualizado correctamente.",
                        Toast.LENGTH_SHORT).show();
                 }else {

                    controladorAdeful.cerrarBaseDeDatos();
                    Toast.makeText(getActivity(), "Error en la base de datos interna, vuelva a intentar." +
                                    "\n Si el error persiste comuniquese con soporte.",
                            Toast.LENGTH_SHORT).show();
                }
            }
                return true;
            }

            if (id == R.id.action_lifuba) {

                return true;
            }

            if (id == R.id.action_cargo) {

                dialogoAlerta = new DialogoAlerta(
                        getActivity(),
                        "CARGO",
                        "En esta opción puedes agregar o editar un cargo directivo.",
                        null, null);
                dialogoAlerta.btnAceptar.setText("Crear");
                dialogoAlerta.btnCancelar.setText("Editar");
                dialogoAlerta.btnAceptar
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                dialogoAlerta.mensaje.setVisibility(View.GONE);
                                dialogoAlerta.editTextUno.setVisibility(View.VISIBLE);
                                dialogoAlerta.btnAceptar.setText("Aceptar");
                                dialogoAlerta.btnCancelar.setText("Cancelar");

                                // Crear la Cargo
                                dialogoAlerta.btnAceptar
                                        .setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                // TODO Auto-generated method stub
                                                if (!dialogoAlerta.editTextUno
                                                        .getText().toString()
                                                        .equals("")) {

                                                    String usuario = "Administrador";
                                                    String fechaCreacion = controladorAdeful.getFechaOficial();
                                                    String fechaActualizacion = fechaCreacion;

                                                    cargo = new Cargo(0,
                                                            dialogoAlerta.editTextUno
                                                                    .getText()
                                                                    .toString(), usuario, fechaCreacion, usuario, fechaActualizacion);

                                                    controladorAdeful
                                                            .abrirBaseDeDatos();
                                                    controladorAdeful
                                                            .insertCargoAdeful(cargo);
                                                    controladorAdeful
                                                            .cerrarBaseDeDatos();
                                                    //SPINNER
                                                    loadSpinnerCargo();

                                                    Toast.makeText(
                                                            getActivity(),
                                                            "Cargo Generado Correctamente.",
                                                            Toast.LENGTH_SHORT)
                                                            .show();
                                                    dialogoAlerta.alertDialog
                                                            .dismiss();

                                                } else {
                                                    Toast.makeText(
                                                            getActivity(),
                                                            "Ingrese un Cargo.",
                                                            Toast.LENGTH_SHORT)
                                                            .show();
                                                }
                                            }
                                        });

                                dialogoAlerta.btnCancelar
                                        .setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                // TODO Auto-generated method stub

                                                dialogoAlerta.alertDialog.dismiss();

                                            }
                                        });
                            }
                        });

                //EDITAR UN CARGO
                dialogoAlerta.btnCancelar
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub

                                dialogoMenuLista = new DialogoMenuLista(getActivity(),
                                        "CARGOS", cargoArray);

                                dialogoMenuLista.btnAceptar.setText("Aceptar");
                                dialogoMenuLista.btnCancelar.setText("Cancelar");


                                loadListViewMenu();


                                dialogoMenuLista.listViewGeneral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

                                        dialogoAlertaEditar = new DialogoAlerta(
                                                getActivity(),
                                                "CARGO",
                                                null,
                                                null, null);

                                        dialogoAlertaEditar.editTextUno.setVisibility(View.VISIBLE);
                                        dialogoAlertaEditar.editTextUno.setText(parent.getItemAtPosition(position).toString());
                                        dialogoAlertaEditar.btnAceptar.setText("Aceptar");
                                        dialogoAlertaEditar.btnCancelar.setText("Cancelar");

                                        dialogoAlertaEditar.btnAceptar.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                // TODO Auto-generated method stub

                                                String usuario = "Administrador";
                                                cargo = new Cargo(
                                                        cargoArray.get(position).getID_CARGO(),
                                                        dialogoAlertaEditar.editTextUno.getText().toString(),
                                                        usuario, null, usuario, controladorAdeful.getFechaOficial());

                                                controladorAdeful
                                                        .abrirBaseDeDatos();
                                                controladorAdeful
                                                        .actualizarCargoAdeful(cargo);
                                                controladorAdeful
                                                        .cerrarBaseDeDatos();
                                                loadListViewMenu();
                                                loadSpinnerCargo();
                                                dialogoAlertaEditar.alertDialog.dismiss();
                                                Toast.makeText(
                                                        getActivity(),
                                                        "Cargo Actualizado Correctamente.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                        dialogoAlertaEditar.btnCancelar.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                // TODO Auto-generated method stub


                                                dialogoAlertaEditar.alertDialog.dismiss();

                                            }
                                        });
                                    }

                                });


                                // Editar Cargo
                                dialogoMenuLista.btnAceptar
                                        .setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                // TODO Auto-generated method stub

                                                dialogoMenuLista.alertDialog.dismiss();
                                                dialogoAlerta.alertDialog.dismiss();
                                            }
                                        });

                                dialogoMenuLista.btnCancelar.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub

                                        dialogoMenuLista.alertDialog.dismiss();
                                        dialogoAlerta.alertDialog.dismiss();
                                    }
                                });
                            }
                        });

                return true;
            }

            if (id == android.R.id.home) {

                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }