package com.estrelladelsur.estrelladelsur.institucion;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.Toast;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.UtilityImage;
import com.estrelladelsur.estrelladelsur.entidad.Cargo;
import com.estrelladelsur.estrelladelsur.entidad.Direccion;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerCargoComision;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoMenuLista;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FragmentGenerarDireccion extends Fragment {

    private int CheckedPositionFragment;
    private ImageView fotoImageDireccion;
    private EditText nombreEditDireccion;
    private Spinner puestoSpinnerDireccion;
    private Button desdeButtonDireccion;
    private Button hastaButtonDireccion;
    private ControladorAdeful controladorAdeful;
    private boolean insertar = true;
    private Cargo cargo;
    private Cargo cargoSpinner;
    private Communicator communicator;
    private boolean actualizar = false;
    private int idDireccionExtra;
    private ArrayList<Cargo> cargoArray;
    private AdapterSpinnerCargoComision adapterSpinnerCargoComision;
    private DialogoAlerta dialogoAlerta;
    private DialogoAlerta dialogoAlertaEditar;
    private DialogoMenuLista dialogoMenuLista;
    private ArrayAdapter<Cargo> adapterList;
    private byte[] imageDireccion;
    private ByteArrayOutputStream baos;
    private Direccion direccion;
    private ArrayList<Direccion> direccionArray;
    private SimpleDateFormat formate = new SimpleDateFormat(
            "dd-MM-yyyy");
    private Calendar calendar = Calendar.getInstance();
    private boolean botonFecha = false;
    private ArrayAdapter<String> adaptadorInicial;
    private String GUARDAR_USUARIO = "Integrante ingresado correctamente";
    private String ACTUALIZAR_USUARIO = "Integrante actualizado correctamente";
    private Typeface editTextFont;
    private Typeface textViewFont;
    private AuxiliarGeneral auxiliarGeneral;
    private TextView tituloTextPeriodo;

    public static FragmentGenerarDireccion newInstance() {
        FragmentGenerarDireccion fragment = new FragmentGenerarDireccion();
        return fragment;
    }
    public FragmentGenerarDireccion() {
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
        View v = inflater.inflate(R.layout.fragment_generar_comision_direccion, container, false);
        editTextFont = Typeface.createFromAsset(getActivity().getAssets(), "ATypewriterForMe.ttf");
        textViewFont = Typeface.createFromAsset(getActivity().getAssets(), "aspace_demo.otf");
        //FOTO INTEGRANTE
        fotoImageDireccion = (ImageView) v
                .findViewById(R.id.fotoImageComisionDireccion);
        //NOMBRE INTEGRANTE
        nombreEditDireccion = (EditText) v
                .findViewById(R.id.nombreEditComisionDireccion);
        nombreEditDireccion.setTypeface(editTextFont);
        //CARGO SPINNER INTEGRANTE
        puestoSpinnerDireccion = (Spinner) v
                .findViewById(R.id.puestoSpinnerComisionDireccion);
        //DESDE
        desdeButtonDireccion = (Button) v
                .findViewById(R.id.desdeEditComisionDireccion);
        desdeButtonDireccion.setTypeface(editTextFont,Typeface.BOLD);
        //HASTA
        hastaButtonDireccion = (Button) v
                .findViewById(R.id.hastaEditComisionDireccion);
        hastaButtonDireccion.setTypeface(editTextFont,Typeface.BOLD);
        tituloTextPeriodo = (TextView) v
                .findViewById(R.id.tituloTextPeriodo);
        tituloTextPeriodo.setTypeface(textViewFont);

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
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        // LOAD SPINNER
        loadSpinnerCargo();
        //Metodo Extra
        if (actualizar) {

            idDireccionExtra = getActivity().getIntent().getIntExtra("id_direccion", 0);

            // DIRECCION POR ID
            direccionArray = controladorAdeful.selectDireccionAdeful(idDireccionExtra);
            if (direccionArray != null) {
                nombreEditDireccion.setText(direccionArray.get(0).getNOMBRE_DIRECCION().toString());
                desdeButtonDireccion.setText(direccionArray.get(0).getPERIODO_DESDE().toString());
                hastaButtonDireccion.setText(direccionArray.get(0).getPERIODO_HASTA().toString());
                imageDireccion = direccionArray.get(0).getFOTO_DIRECCION();
                puestoSpinnerDireccion.setSelection(getPositionCargo(direccionArray.get(0).getID_CARGO()));

                if (imageDireccion != null) {
                    Bitmap theImage = BitmapFactory.decodeByteArray(imageDireccion, 0,
                            imageDireccion.length);
                    theImage = Bitmap.createScaledBitmap(theImage, 150, 150, true);
                    fotoImageDireccion.setImageBitmap(theImage);
                } else {
                    fotoImageDireccion.setImageResource(R.mipmap.ic_foto_galery);
                }

                insertar = false;
            } else {
                 auxiliarGeneral.errorDataBase(getActivity());
            }
        }
        // CLICK IMAGEN
        fotoImageDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Alerta galeria
                ImageDialogDireccion();
            }
        });

        desdeButtonDireccion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                botonFecha = true;
                setDate();
            }
        });
        hastaButtonDireccion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                botonFecha = false;
                setDate();
            }
        });
    }
    //Alerta galeria
    public void ImageDialogDireccion() {

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
    //get posicion en el spinner del cargo.
    private int getPositionCargo(int idCargo) {
        int index = 0;
        for (int i = 0; i < cargoArray.size(); i++) {
            if (cargoArray.get(i).getID_CARGO() == (idCargo)) {
                index = i;
            }
        }
        return index;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UtilityImage.GALLERY_PICTURE) {
            Bitmap b = auxiliarGeneral.SeleccionarImagen(data, getActivity());
            if (b != null)
                fotoImageDireccion.setImageBitmap(b);
            imageDireccion = auxiliarGeneral.pasarBitmapByte(b);
        }
    }

    public ArrayList<Cargo> selectCargoList() {
        // CARGO
        cargoArray = controladorAdeful.selectListaCargoAdeful();
        if (cargoArray == null)auxiliarGeneral.errorDataBase(getActivity());

        return cargoArray;
    }

    // POPULATION SPINNER
    public void loadSpinnerCargo() {
        if(selectCargoList().size()!=0){
            // CARGO SPINNER
            adapterSpinnerCargoComision = new AdapterSpinnerCargoComision(getActivity(),
                    R.layout.simple_spinner_dropdown_item, selectCargoList());
            puestoSpinnerDireccion.setAdapter(adapterSpinnerCargoComision);
        }else{
            //SPINNER HINT
            adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                    R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerCargo));
            puestoSpinnerDireccion.setAdapter(adaptadorInicial);
        }
    }

    // POPULATION LISTVIEW
    public void loadListViewMenu() {
        adapterList = new ArrayAdapter<Cargo>(getActivity(),
                R.layout.listview_item_dialogo, R.id.textViewGeneral, selectCargoList());
        dialogoMenuLista.listViewGeneral.setAdapter(adapterList);
    }

    public void dateDesde() {
        desdeButtonDireccion.setText(formate.format(calendar.getTime()));
    }
    public void dateHasta() {
        hastaButtonDireccion.setText(formate.format(calendar.getTime()));
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
    public void inicializarControles(String mensaje) {
        nombreEditDireccion.setText("");
        desdeButtonDireccion.setText("Desde");
        hastaButtonDireccion.setText("Hasta");
        imageDireccion = null;
        fotoImageDireccion.setImageResource(R.mipmap.ic_foto_galery);
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
        menu.getItem(1).setVisible(false);//permiso
        menu.getItem(2).setVisible(false);//lifuba
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

            String usuario = "Administrador";
            String fechaCreacion = controladorAdeful.getFechaOficial();
            String fechaActualizacion = fechaCreacion;

            if (nombreEditDireccion.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Ingrese el nombre del integrante de la Dirección.",
                        Toast.LENGTH_SHORT).show();
            }else if (puestoSpinnerDireccion.getSelectedItem().toString().equals(getResources().getStringArray(R.array.ceroSpinnerCargo))) {
                Toast.makeText(getActivity(), "Debe agregar un Cargo (Menu-Cargo).",
                        Toast.LENGTH_SHORT).show();
            } else if (desdeButtonDireccion.getText().toString().equals("Desde") || hastaButtonDireccion.getText().toString().equals("Hasta")) {
                Toast.makeText(getActivity(), "Complete el periodo del cargo.",
                        Toast.LENGTH_SHORT).show();
            } else if (insertar) {
                cargoSpinner = (Cargo) puestoSpinnerDireccion.getSelectedItem();
                direccion = new Direccion(0, nombreEditDireccion.getText().toString(),
                        imageDireccion, cargoSpinner.getID_CARGO(), null, desdeButtonDireccion.getText().toString(),
                        hastaButtonDireccion.getText().toString(), usuario, fechaCreacion, usuario, fechaActualizacion);
                if (controladorAdeful.insertDireccionAdeful(direccion)) {
                    inicializarControles(GUARDAR_USUARIO);
                } else {
                auxiliarGeneral.errorDataBase(getActivity());
                }
            } else { //DIRECCION ACTUALIZAR
                cargoSpinner = (Cargo) puestoSpinnerDireccion.getSelectedItem();
                direccion = new Direccion(idDireccionExtra, nombreEditDireccion.getText().toString(),
                        imageDireccion, cargoSpinner.getID_CARGO(), null, desdeButtonDireccion.getText().toString(),
                        hastaButtonDireccion.getText().toString(), usuario, fechaCreacion, usuario, fechaActualizacion);

                if (controladorAdeful.actualizarDireccionAdeful(direccion)) {
                    actualizar = false;
                    insertar = true;
                    inicializarControles(ACTUALIZAR_USUARIO);
                } else {
                    auxiliarGeneral.errorDataBase(getActivity());                }
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
                            dialogoAlerta.editTextUno.setHint("Ingrese un cargo ");
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
                                                if (controladorAdeful
                                                        .insertCargoAdeful(cargo)) {
                                                     //SPINNER
                                                    loadSpinnerCargo();

                                                    Toast.makeText(
                                                            getActivity(),
                                                            "Cargo generado correctamente.",
                                                            Toast.LENGTH_SHORT)
                                                            .show();
                                                    dialogoAlerta.alertDialog
                                                            .dismiss();
                                                } else {
                                                auxiliarGeneral.errorDataBase(getActivity());
                                                }
                                            } else {
                                                Toast.makeText(
                                                        getActivity(),
                                                        "Ingrese un cargo.",
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
                            dialogoMenuLista = new DialogoMenuLista(getActivity(),
                                    "CARGOS");

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
                                            if (!dialogoAlertaEditar.editTextUno.getText().toString().equals("")) {
                                                String usuario = "Administrador";
                                                cargo = new Cargo(
                                                        cargoArray.get(position).getID_CARGO(),
                                                        dialogoAlertaEditar.editTextUno.getText().toString(),
                                                        usuario, null, usuario, controladorAdeful.getFechaOficial());
                                                if (controladorAdeful
                                                        .actualizarCargoAdeful(cargo)) {
                                                    loadListViewMenu();
                                                    loadSpinnerCargo();
                                                    dialogoAlertaEditar.alertDialog.dismiss();
                                                    Toast.makeText(
                                                            getActivity(),
                                                            "Cargo actualizado correctamente.",
                                                            Toast.LENGTH_SHORT).show();
                                                } else {
                                                    auxiliarGeneral.errorDataBase(getActivity());
                                                }

                                            } else {
                                                Toast.makeText(
                                                        getActivity(),
                                                        "Ingrese un cargo.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
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