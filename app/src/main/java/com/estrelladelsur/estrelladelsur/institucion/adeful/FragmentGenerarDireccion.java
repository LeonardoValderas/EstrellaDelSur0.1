package com.estrelladelsur.estrelladelsur.institucion.adeful;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.UtilityImage;
import com.estrelladelsur.estrelladelsur.entidad.Cargo;
import com.estrelladelsur.estrelladelsur.entidad.Direccion;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerCargoComision;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoMenuLista;
import com.estrelladelsur.estrelladelsur.webservice.JsonParsing;
import com.estrelladelsur.estrelladelsur.webservice.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentGenerarDireccion extends Fragment {

    private CircleImageView fotoImageDireccion;
    private EditText nombreEditDireccion;
    private Spinner puestoSpinnerDireccion;
    private Button desdeButtonDireccion, hastaButtonDireccion;
    private ControladorAdeful controladorAdeful;
    private boolean insertar = true, insertarCargo = true, actualizar = false;
    private Cargo cargo, cargoSpinner;
    private Communicator communicator;
    private int idDireccionExtra, CheckedPositionFragment;
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
    private String GUARDAR_DIRECCION = "Integrante ingresado correctamente";
    private String ACTUALIZAR_DIRECCION = "Integrante actualizado correctamente";
    private String GUARDAR_CARGO = "Cargo generado correctamente";
    private String ACTUALIZAR_CARGO = "Cargo actualizado correctamente";
    private Typeface editTextFont, textViewFont;
    private AuxiliarGeneral auxiliarGeneral;
    private TextView tituloTextPeriodo, tituloTextCargo;
    private Request request = new Request();
    private ProgressDialog dialog;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private JsonParsing jsonParsing = new JsonParsing(getActivity());
    private static final String TAG_ID = "id";
    private String mensaje = null, fechaFoto = null, nombre_foto = null, url_foto_direccion = null,
            URL = null, usuario = null, url_nombre_foto = null, encodedImage = null;

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
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        editTextFont = auxiliarGeneral.textFont(getActivity());
        textViewFont = auxiliarGeneral.tituloFont(getActivity());
        //FOTO INTEGRANTE
        fotoImageDireccion = (CircleImageView) v
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
        desdeButtonDireccion.setTypeface(editTextFont, Typeface.BOLD);
        //HASTA
        hastaButtonDireccion = (Button) v
                .findViewById(R.id.hastaEditComisionDireccion);
        hastaButtonDireccion.setTypeface(editTextFont, Typeface.BOLD);
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
        controladorAdeful = new ControladorAdeful(getActivity());
        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
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
            Bitmap bitmapWeb = auxiliarGeneral.SeleccionarImagen(data, getContext(), true);
            if (bitmapWeb != null)
                fotoImageDireccion.setImageBitmap(bitmapWeb);
            baos = new ByteArrayOutputStream();
            bitmapWeb.compress(Bitmap.CompressFormat.PNG, 0, baos);
            imageDireccion = baos.toByteArray();
        }
    }

    public ArrayList<Cargo> selectCargoList() {
        // CARGO
        cargoArray = controladorAdeful.selectListaCargoAdeful();
        if (cargoArray == null) auxiliarGeneral.errorDataBase(getActivity());
        return cargoArray;
    }

    // POPULATION SPINNER
    public void loadSpinnerCargo() {
        if (selectCargoList().size() != 0) {
            // CARGO SPINNER
            adapterSpinnerCargoComision = new AdapterSpinnerCargoComision(getActivity(),
                    R.layout.simple_spinner_dropdown_item, selectCargoList());
            puestoSpinnerDireccion.setAdapter(adapterSpinnerCargoComision);
        } else {
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
        nombre = nombreEditDireccion.getText().toString();
        url_nombre_foto = auxiliarGeneral.removeAccents(nombre.replace(" ", "").trim());
        URL = null;
        URL = auxiliarGeneral.getURLDIRECCIONADEFULALL();
        fechaFoto = auxiliarGeneral.getFechaFoto();
        nombre_foto = fechaFoto + url_nombre_foto + ".PNG";
        url_foto_direccion = auxiliarGeneral.getURLFOTODIRECCIONADEFUL() + nombre_foto;


        direccion = new Direccion(id, nombre,
                imageDireccion, nombre_foto, cargoSpinner.getID_CARGO(), null, desdeButtonDireccion.getText().toString(),
                hastaButtonDireccion.getText().toString(), url_foto_direccion, usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());

        envioWebService(ws);
    }

    public void cargarEntidadCargo(int id, int ws, String cargoEntidad) {
        URL = null;
        URL = auxiliarGeneral.getURLCARGOADEFULALL();

        cargo = new Cargo(id, cargoEntidad, usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());
        envioWebServiceCargo(ws);
    }

    public void envioWebService(int tipo) {
        request.setMethod("POST");
        request.setParametrosDatos("nombre", direccion.getNOMBRE_DIRECCION());
        request.setParametrosDatos("nombre_foto", direccion.getNOMBRE_FOTO());
        request.setParametrosDatos("id_cargo", String.valueOf(direccion.getID_CARGO()));
        request.setParametrosDatos("periodo_desde", direccion.getPERIODO_DESDE());
        request.setParametrosDatos("periodo_hasta", direccion.getPERIODO_HASTA());

        if (imageDireccion != null) {
            encodedImage = Base64.encodeToString(imageDireccion,
                    Base64.DEFAULT);

            request.setParametrosDatos("foto", encodedImage);
            request.setParametrosDatos("url_foto",
                    direccion.getURL_DIRECCION());
        }
        if (tipo == 0) {
            request.setParametrosDatos("usuario_creador", direccion.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", direccion.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Direccion");
        } else {
            request.setParametrosDatos("id_direccion", String.valueOf(direccion.getID_DIRECCION()));
            request.setParametrosDatos("usuario_actualizacion", direccion.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", direccion.getFECHA_ACTUALIZACION());
            URL = URL + auxiliarGeneral.getUpdatePHP("Direccion");
        }
        new TaskDireccion().execute(request);
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
        new TaskCargo().execute(request);
    }

    public class TaskDireccion extends AsyncTask<Request, Boolean, Boolean> {
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Procesando...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Request... params) {
            int success;
            JSONObject json = null;
            boolean precessOK = true;
            try {
                json = jsonParsing.parsingJsonObject(params[0], URL);
                if (json != null) {
                    success = json.getInt(TAG_SUCCESS);
                    mensaje = json.getString(TAG_MESSAGE);
                    if (success == 0) {
                        if (insertar) {
                            int id = json.getInt(TAG_ID);
                            if (id > 0) {
                                if (controladorAdeful.insertDireccionAdeful(id, direccion)) {
                                    precessOK = true;
                                } else {
                                    precessOK = false;
                                }
                            } else {
                                precessOK = false;
                            }
                        } else {
                            if (controladorAdeful.actualizarDireccionAdeful(direccion)) {
                                precessOK = true;
                            } else {
                                precessOK = false;
                            }
                        }
                        precessOK = true;
                    } else {
                        precessOK = false;
                    }
                } else {
                    precessOK = false;
                    mensaje = "Error(4). Por favor comuniquese con el administrador.";
                }
            } catch (JSONException e) {
                precessOK = false;
                mensaje = "Error(5). Por favor comuniquese con el administrador.";
            }
            return precessOK;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();

            if (result) {
                if (insertar) {
                    inicializarControles(GUARDAR_DIRECCION);
                } else {
                    actualizar = false;
                    insertar = true;
                    inicializarControles(ACTUALIZAR_DIRECCION);
                }
            } else {
                auxiliarGeneral.errorWebService(getActivity(), mensaje);
            }
        }
    }

    public class TaskCargo extends AsyncTask<Request, Boolean, Boolean> {
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Procesando...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Request... params) {
            int success;
            JSONObject json = null;
            boolean precessOK = true;
            try {
                json = jsonParsing.parsingJsonObject(params[0], URL);
                if (json != null) {
                    success = json.getInt(TAG_SUCCESS);
                    mensaje = json.getString(TAG_MESSAGE);
                    if (success == 0) {
                        if (insertarCargo) {
                            int id = json.getInt(TAG_ID);
                            if (id > 0) {
                                if (controladorAdeful.insertCargoAdeful(id, cargo)) {
                                    precessOK = true;
                                } else {
                                    precessOK = false;
                                }
                            } else {
                                precessOK = false;
                            }
                        } else {
                            if (controladorAdeful.actualizarCargoAdeful(cargo)) {
                                precessOK = true;
                            } else {
                                precessOK = false;
                            }
                        }
                        precessOK = true;
                    } else {
                        precessOK = false;
                    }
                } else {
                    precessOK = false;
                    mensaje = "Error(4). Por favor comuniquese con el administrador.";
                }
            } catch (JSONException e) {
                precessOK = false;
                mensaje = "Error(5). Por favor comuniquese con el administrador.";
            }
            return precessOK;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();

            if (result) {
                if (insertarCargo) {
                    inicializarControlesCargo(GUARDAR_CARGO);
                } else {
                    inicializarControlesCargo(ACTUALIZAR_CARGO);
                }
            } else {
                auxiliarGeneral.errorWebService(getActivity(), mensaje);
            }
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

            if (nombreEditDireccion.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Ingrese el nombre del integrante de la Dirección.",
                        Toast.LENGTH_SHORT).show();
            } else if (puestoSpinnerDireccion.getSelectedItem().toString().equals(getResources().getString(R.string.ceroSpinnerCargo))) {
                Toast.makeText(getActivity(), "Debe agregar un Cargo (Menu-Cargo).",
                        Toast.LENGTH_SHORT).show();
            } else if (desdeButtonDireccion.getText().toString().equals("Desde") || hastaButtonDireccion.getText().toString().equals("Hasta")) {
                Toast.makeText(getActivity(), "Complete el periodo del cargo.",
                        Toast.LENGTH_SHORT).show();
            } else if (insertar) {
                cargoSpinner = (Cargo) puestoSpinnerDireccion.getSelectedItem();
                cargarEntidad(0, 0);

            } else { //DIRECCION ACTUALIZAR
                cargoSpinner = (Cargo) puestoSpinnerDireccion.getSelectedItem();
                cargarEntidad(idDireccionExtra, 1);
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