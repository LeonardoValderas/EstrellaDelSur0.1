package com.estrelladelsur.estrelladelsur.institucion.administrador;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.UtilityImage;
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Cargo;
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerCargoComision;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoMenuLista;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.navegador.usuario.SplashUsuario;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGeneric;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentGenerarComisionAdeful extends Fragment implements MyAsyncTaskListener {

    private int CheckedPositionFragment, idComisionExtra;
    private CircleImageView fotoImageComision;
    private EditText nombreEditComision;
    private Spinner puestoSpinnerComision;
    private Button desdeButtonComision, hastaButtonComision;
    private TextView tituloTextPeriodo, tituloTextCargo;
    private ControladorGeneral controladorGeneral;
    private boolean insertar = true, insertarCargo = true, actualizar = false, botonFecha = false;
    private Cargo cargo, cargoSpinner;
    private CommunicatorAdeful communicator;
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
    private ArrayAdapter<String> adaptadorInicial;
    private Typeface editTextFont, textViewFont;
    private AuxiliarGeneral auxiliarGeneral;
    private Request request = new Request();
    private boolean isComision = true;
    private String encodedImage = null, url_nombre_foto = null, usuario = null,
            URL = null, fechaFoto = null, nombre_foto = null, nombre_foto_anterior = null, url_foto_comision = null;
    private ImageButton rotateButton;

    public static FragmentGenerarComisionAdeful newInstance() {
        FragmentGenerarComisionAdeful fragment = new FragmentGenerarComisionAdeful();
        return fragment;
    }

    public FragmentGenerarComisionAdeful() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        communicator = (CommunicatorAdeful) getActivity();
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
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        editTextFont = auxiliarGeneral.textFont(getActivity());
        textViewFont = auxiliarGeneral.tituloFont(getActivity());
        rotateButton = (ImageButton) v.findViewById(R.id.rotateButton);
        //FOTO INTEGRANTE
        fotoImageComision = (CircleImageView) v
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
        desdeButtonComision.setTypeface(editTextFont, Typeface.BOLD);
        //HASTA
        hastaButtonComision = (Button) v
                .findViewById(R.id.hastaEditComisionDireccion);
        hastaButtonComision.setTypeface(editTextFont, Typeface.BOLD);

        tituloTextPeriodo = (TextView) v
                .findViewById(R.id.tituloTextPeriodo);
        tituloTextPeriodo.setTypeface(textViewFont);
        tituloTextPeriodo.setPaintFlags(tituloTextPeriodo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tituloTextCargo = (TextView) v
                .findViewById(R.id.tituloTextCargo);
        tituloTextCargo.setTypeface(textViewFont);
        tituloTextCargo.setPaintFlags(tituloTextCargo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        // VER DONDE EJECUCTAR ESTA LINEA
        controladorGeneral = new ControladorGeneral(getActivity());
        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
        // LOAD SPINNER
        loadSpinnerCargo();
        //Metodo Extra
        if (actualizar) {
            idComisionExtra = getActivity().getIntent().getIntExtra("id_comision", 0);
            // COMISION POR ID
            comisionArray = controladorGeneral.selectComision(idComisionExtra);
            if (comisionArray != null) {

                nombreEditComision.setText(comisionArray.get(0).getNOMBRE_COMISION().toString());
                desdeButtonComision.setText(comisionArray.get(0).getPERIODO_DESDE().toString());
                hastaButtonComision.setText(comisionArray.get(0).getPERIODO_HASTA().toString());
                imageComision = comisionArray.get(0).getFOTO_COMISION();
                nombre_foto_anterior = comisionArray.get(0).getNOMBRE_FOTO();

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

        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageComision != null) {
                    Bitmap theImage = auxiliarGeneral.setByteToBitmap(imageComision, 150,
                            150);
                    theImage = auxiliarGeneral.RotateBitmap(theImage);
                    fotoImageComision.setImageBitmap(theImage);
                    imageComision = auxiliarGeneral.pasarBitmapByte(theImage);
                }
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
            Bitmap bitmapWeb = auxiliarGeneral.SeleccionarImagen(data, getContext(), true);
            if (bitmapWeb != null) {
                fotoImageComision.setImageBitmap(bitmapWeb);
                baos = new ByteArrayOutputStream();
                bitmapWeb.compress(Bitmap.CompressFormat.PNG, 0, baos);
                imageComision = baos.toByteArray();
            }
        }
    }

    public ArrayList<Cargo> selectCargoList() {
        // CARGO
        cargoArray = controladorGeneral.selectListaCargo();
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
        communicator.refreshAdeful();
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void inicializarControlesCargo(String mensaje) {
        loadSpinnerCargo();
        if (insertarCargo) {
            dialogoAlerta.alertDialog
                    .dismiss();
        } else {
            loadListViewMenu();
            dialogoAlertaEditar.alertDialog.dismiss();
        }
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void cargarEntidad(int id, int ws) {

        String nombre = null;
        nombre = nombreEditComision.getText().toString();
        URL = null;
        url_foto_comision = null;
        // si la foto es null(default) no enviaremos la url.
        if (imageComision != null) {
            url_nombre_foto = auxiliarGeneral.removeAccents(nombre.replace(" ", "").trim());
            fechaFoto = auxiliarGeneral.getFechaImagen();
            nombre_foto = fechaFoto + url_nombre_foto + ".PNG";
            url_foto_comision = auxiliarGeneral.getURLFOTOCOMISIONADEFUL() + nombre_foto;
        }
        URL = auxiliarGeneral.getURLCOMISIONADEFULALL();

        comision = new Comision(id, nombre,
                imageComision, nombre_foto, cargoSpinner.getID_CARGO(), null, desdeButtonComision.getText().toString(),
                hastaButtonComision.getText().toString(), url_foto_comision, usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());

        envioWebService(ws);

    }

    public void cargarEntidadCargo(int id, int ws, String cargoEntidad) {
        URL = null;
        URL = auxiliarGeneral.getURLCARGOADEFULALL();

        cargo = new Cargo(id, cargoEntidad, usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());
        envioWebServiceCargo(ws);
    }

    public void envioWebServiceCargo(int tipo) {
        request.setMethod("POST");
        request.setParametrosDatos("cargo", cargo.getCARGO());
        if (tipo == 0) {
            request.setParametrosDatos("usuario_creador", cargo.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", cargo.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Cargo");
            insertarCargo = true;
        } else {
            request.setParametrosDatos("id_cargo", String.valueOf(cargo.getID_CARGO()));
            request.setParametrosDatos("usuario_actualizacion", cargo.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", cargo.getFECHA_ACTUALIZACION());
            URL = URL + auxiliarGeneral.getUpdatePHP("Cargo");
            insertarCargo = false;
        }
        isComision = false;
        new AsyncTaskGeneric(getActivity(), this, URL, request, "Cargo", cargo, insertarCargo, "o");
    }

    public void envioWebService(int tipo) {
        request.setMethod("POST");
        request.setParametrosDatos("nombre", comision.getNOMBRE_COMISION());
        request.setParametrosDatos("id_cargo", String.valueOf(comision.getID_CARGO()));
        request.setParametrosDatos("periodo_desde", comision.getPERIODO_DESDE());
        request.setParametrosDatos("periodo_hasta", comision.getPERIODO_HASTA());

        if (imageComision != null) {
            encodedImage = Base64.encodeToString(imageComision,
                    Base64.DEFAULT);

            request.setParametrosDatos("foto", encodedImage);
            request.setParametrosDatos("url_foto",
                    comision.getURL_COMISION());
            request.setParametrosDatos("nombre_foto", comision.getNOMBRE_FOTO());

        }
        if (tipo == 0) {
            //request.setQuery("SUBIR");
            request.setParametrosDatos("usuario_creador", comision.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", comision.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Comision");

        } else {
            //request.setQuery("EDITAR");
            request.setParametrosDatos("id_comision", String.valueOf(comision.getID_COMISION()));
            request.setParametrosDatos("usuario_actualizacion", comision.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", comision.getFECHA_ACTUALIZACION());
            if (nombre_foto_anterior != null)
                request.setParametrosDatos("nombre_foto_anterior", nombre_foto_anterior);

            URL = URL + auxiliarGeneral.getUpdatePHP("Comision");
        }
        isComision = true;
        new AsyncTaskGeneric(getActivity(), this, URL, request, "Comisión", comision, insertar, "a");
    }

    @Override
    public void onPostExecuteConcluded(boolean result, String mensaje) {
        if (isComision) {
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
        } else {
            dialogoAlerta.alertDialog.dismiss();
            if (result) {
                if (insertarCargo) {
                    inicializarControlesCargo(mensaje);
                } else {
                    inicializarControlesCargo(mensaje);
                }
            } else {
                auxiliarGeneral.errorWebService(getActivity(), mensaje);
            }
            isComision = true;
        }
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
        // menu.getItem(2).setVisible(false);// cargo
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

            if (nombreEditComision.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Ingrese el nombre del integrante de la comisión.",
                        Toast.LENGTH_SHORT).show();
            } else if (puestoSpinnerComision.getSelectedItem().toString().equals(getResources().getString(R.string.ceroSpinnerCargo))) {
                Toast.makeText(getActivity(), "Debe agregar un Cargo (Menu-Cargo).",
                        Toast.LENGTH_SHORT).show();
            } else if (desdeButtonComision.getText().toString().equals("Desde") || hastaButtonComision.getText().toString().equals("Hasta")) {
                Toast.makeText(getActivity(), "Complete el periodo del cargo.",
                        Toast.LENGTH_SHORT).show();
            } else if (insertar) {
                cargoSpinner = (Cargo) puestoSpinnerComision.getSelectedItem();
                cargarEntidad(0, 0);
            } else { //COMISION ACTUALIZAR
                cargoSpinner = (Cargo) puestoSpinnerComision.getSelectedItem();
                cargarEntidad(idComisionExtra, 1);
            }
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
                                            String cargoInsert = dialogoAlerta.editTextUno
                                                    .getText().toString();
                                            if (!cargoInsert
                                                    .equals("")) {
                                                cargarEntidadCargo(0, 0, cargoInsert);
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
                                            String cargoEdit = dialogoAlertaEditar.editTextUno.getText().toString();
                                            if (!cargoEdit.equals("")) {
                                                cargarEntidadCargo(cargoArray.get(position).getID_CARGO(), 1, cargoEdit);
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