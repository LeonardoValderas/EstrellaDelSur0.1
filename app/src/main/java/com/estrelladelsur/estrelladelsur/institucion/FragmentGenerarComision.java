package com.estrelladelsur.estrelladelsur.institucion;

import android.app.DatePickerDialog;
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
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import com.estrelladelsur.estrelladelsur.adaptador.AdapterSpinnerCargoComision;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoMenuLista;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FragmentGenerarComision extends Fragment {

    private int CheckedPositionFragment;
    private ImageView fotoImageComision;
    private EditText nombreEditComision;
    private Spinner puestoSpinnerComision;
    private Button desdeButtonComision;
    private Button hastaButtonComision;
    private TextView tituloTextPeriodo;
    private ControladorAdeful controladorAdeful;
    private boolean insertar = true;
    private Cargo cargo;
    private Cargo cargoSpinner;
    private Communicator communicator;
    private boolean actualizar = false;
    private int idComisionExtra;
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
    private SimpleDateFormat formate = new SimpleDateFormat(
            "dd-MM-yyyy");
    private Calendar calendar = Calendar.getInstance();
    private boolean botonFecha = false;
    private ArrayAdapter<String> adaptadorInicial;
    private Typeface editTextFont;
    private Typeface textViewFont;
    private AuxiliarGeneral auxiliarGeneral;
    private String GUARDAR_USUARIO = "Integrante ingresado correctamente";
    private String ACTUALIZAR_USUARIO = "Integrante actualizado correctamente";

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
        View v = inflater.inflate(R.layout.fragment_generar_comision_direccion, container, false);
        editTextFont = Typeface.createFromAsset(getActivity().getAssets(), "ATypewriterForMe.ttf");
        textViewFont = Typeface.createFromAsset(getActivity().getAssets(), "aspace_demo.otf");
        //FOTO INTEGRANTE
        fotoImageComision = (ImageView) v
                .findViewById(R.id.fotoImageComisionDireccion);
        //NOMBRE INTEGRANTE
        nombreEditComision = (EditText) v
                .findViewById(R.id.nombreEditComisionDireccion);
        nombreEditComision.setTypeface(editTextFont);
        //CARGO SPINNER INTEGRANTE
        puestoSpinnerComision = (Spinner) v
                .findViewById(R.id.puestoSpinnerComisionDireccion);
        //DESDE
        desdeButtonComision = (Button) v
                .findViewById(R.id.desdeEditComisionDireccion);
        desdeButtonComision.setTypeface(editTextFont,Typeface.BOLD);
        //HASTA
        hastaButtonComision = (Button) v
                .findViewById(R.id.hastaEditComisionDireccion);
        hastaButtonComision.setTypeface(editTextFont,Typeface.BOLD);

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
            idComisionExtra = getActivity().getIntent().getIntExtra("id_comision", 0);
            // COMISION POR ID
            comisionArray = controladorAdeful.selectComisionAdeful(idComisionExtra);
            if (comisionArray != null) {

                nombreEditComision.setText(comisionArray.get(0).getNOMBRE_COMISION().toString());
                desdeButtonComision.setText(comisionArray.get(0).getPERIODO_DESDE().toString());
                hastaButtonComision.setText(comisionArray.get(0).getPERIODO_HASTA().toString());
                imageComision = comisionArray.get(0).getFOTO_COMISION();

                puestoSpinnerComision.setSelection(getPositionCargo(comisionArray.get(0).getID_CARGO()));

                if (imageComision != null) {
                    Bitmap theImage = BitmapFactory.decodeByteArray(imageComision, 0,
                            imageComision.length);
                    // theImage=getRoundedShape(theImage);
                    theImage = Bitmap.createScaledBitmap(theImage, 150, 150, true);
                    fotoImageComision.setImageBitmap(theImage);
                } else {
                    fotoImageComision.setImageResource(R.mipmap.ic_foto_galery);
                }
                insertar = false;
            } else {
                auxiliarGeneral.errorDataBase(getActivity());
            }
        }
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
        myAlertDialog.setMessage("Seleccione una foto.");

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
            // data contains result
            // Do some task
            Bitmap b = auxiliarGeneral.SeleccionarImagen(data, getActivity());
            if (b != null)
                fotoImageComision.setImageBitmap(b);
            imageComision = auxiliarGeneral.pasarBitmapByte(b);
        }
    }

    public ArrayList<Cargo> selectCargoList() {
        // CARGO
        cargoArray = controladorAdeful.selectListaCargoAdeful();
        if (cargoArray == null)
        auxiliarGeneral.errorDataBase(getActivity());

        return cargoArray;
    }

    // POPULATION SPINNER
    public void loadSpinnerCargo() {
        if (selectCargoList().size() != 0) {
            // CARGO SPINNER
            adapterSpinnerCargoComision = new AdapterSpinnerCargoComision(getActivity(),
                    R.layout.simple_spinner_dropdown_item, selectCargoList());
            puestoSpinnerComision.setAdapter(adapterSpinnerCargoComision);
        } else {
            //SPINNER HINT
            adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                    R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerCargo));
            puestoSpinnerComision.setAdapter(adaptadorInicial);
        }
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
    public void inicializarControles(String mensaje) {
        nombreEditComision.setText("");
        desdeButtonComision.setText("Desde");
        hastaButtonComision.setText("Hasta");
        imageComision = null;
        fotoImageComision.setImageResource(R.mipmap.ic_foto_galery);
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

            if (nombreEditComision.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Ingrese el nombre del integrante de la comisión.",
                        Toast.LENGTH_SHORT).show();
            } else if (puestoSpinnerComision.getSelectedItem().toString().equals(getResources().getStringArray(R.array.ceroSpinnerCargo))) {
                Toast.makeText(getActivity(), "Debe agregar un Cargo (Menu-Cargo).",
                        Toast.LENGTH_SHORT).show();
            } else if (desdeButtonComision.getText().toString().equals("Desde") || hastaButtonComision.getText().toString().equals("Hasta")) {
                Toast.makeText(getActivity(), "Complete el periodo del cargo.",
                        Toast.LENGTH_SHORT).show();
            } else if (insertar) {
                cargoSpinner = (Cargo) puestoSpinnerComision.getSelectedItem();
                comision = new Comision(0, nombreEditComision.getText().toString(),
                        imageComision, cargoSpinner.getID_CARGO(), null, desdeButtonComision.getText().toString(),
                        hastaButtonComision.getText().toString(), usuario, fechaCreacion, usuario, fechaActualizacion);

                if (controladorAdeful.insertComisionAdeful(comision)) {
                    inicializarControles(GUARDAR_USUARIO);
                } else {
                    auxiliarGeneral.errorDataBase(getActivity());
                }
            } else { //COMISION ACTUALIZAR

                cargoSpinner = (Cargo) puestoSpinnerComision.getSelectedItem();
                comision = new Comision(idComisionExtra, nombreEditComision.getText().toString(),
                        imageComision, cargoSpinner.getID_CARGO(), null, desdeButtonComision.getText().toString(),
                        hastaButtonComision.getText().toString(), usuario, fechaCreacion, usuario, fechaActualizacion);

                if (controladorAdeful.actualizarComisionAdeful(comision)) {
                    actualizar = false;
                    insertar = true;
                    inicializarControles(ACTUALIZAR_USUARIO);
                } else {
                    auxiliarGeneral.errorDataBase(getActivity());
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
                            dialogoAlerta.editTextUno.setHint("Ingrese un cargo.");
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
                                            // TODO Auto-generated method stub
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
                                            }else{
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