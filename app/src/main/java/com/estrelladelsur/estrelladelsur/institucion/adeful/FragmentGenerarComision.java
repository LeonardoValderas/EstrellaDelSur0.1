package com.estrelladelsur.estrelladelsur.institucion.adeful;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.estrelladelsur.estrelladelsur.entidad.Comision;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerCargoComision;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoMenuLista;
import com.estrelladelsur.estrelladelsur.navegador.usuario.SplashUsuario;
import com.estrelladelsur.estrelladelsur.webservice.JsonParsing;
import com.estrelladelsur.estrelladelsur.webservice.Request;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    private String usuario = null;
    private String mensaje = null;
    private Request request = new Request();
    private ProgressDialog dialog;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private JsonParsing jsonParsing = new JsonParsing(getActivity());
    private static final String TAG_ID = "id";
    private String encodedImage = null;
    private String url_nombre_foto = null;
    private Request requestUrl = new Request();
    private String URL = null;
    private String fechaFoto = null;
    private String nombre_foto = null;
    private String url_foto_comision =null;

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
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        editTextFont = auxiliarGeneral.textFont(getActivity());
        textViewFont = auxiliarGeneral.tituloFont(getActivity());
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
        desdeButtonComision.setTypeface(editTextFont, Typeface.BOLD);
        //HASTA
        hastaButtonComision = (Button) v
                .findViewById(R.id.hastaEditComisionDireccion);
        hastaButtonComision.setTypeface(editTextFont, Typeface.BOLD);

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
        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());
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
            Bitmap bitmapWeb = auxiliarGeneral.SeleccionarImagen(data, getContext(), true);
            Bitmap bitmapImage = auxiliarGeneral.getRoundedBitmap(bitmapWeb);
            if (bitmapImage != null )
            fotoImageComision.setImageBitmap(bitmapImage);

            baos = new ByteArrayOutputStream();
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 0, baos);
            imageComision = baos.toByteArray();
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
    public void cargarEntidad(int id, int ws) {
        url_nombre_foto = auxiliarGeneral.removeAccents(nombreEditComision.getText().toString().replace(" ", "").trim());

        fechaFoto = auxiliarGeneral.getFechaFoto();
        nombre_foto =  fechaFoto + url_nombre_foto+".PNG";
        url_foto_comision = auxiliarGeneral.getURL() + auxiliarGeneral.getURLFOTOCOMISIONADEFUL() +
                nombre_foto;

        comision = new Comision(id, nombreEditComision.getText().toString(),
                imageComision, nombre_foto, cargoSpinner.getID_CARGO(), null, desdeButtonComision.getText().toString(),
                hastaButtonComision.getText().toString(), url_foto_comision, usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());
        URL = null;
        URL = auxiliarGeneral.getURL()+auxiliarGeneral.getURLCOMISION();

        envioWebService(ws);

    }
    public void envioWebService(int tipo) {
        request.setMethod("POST");
        request.setParametrosDatos("nombre", comision.getNOMBRE_COMISION());
        request.setParametrosDatos("nombre_foto",comision.getNOMBRE_FOTO());
        request.setParametrosDatos("id_cargo", String.valueOf(comision.getID_CARGO()));
        request.setParametrosDatos("periodo_desde", comision.getPERIODO_DESDE());
        request.setParametrosDatos("periodo_hasta", comision.getPERIODO_HASTA());

        if (imageComision != null) {
            encodedImage = Base64.encodeToString(imageComision,
                    Base64.DEFAULT);

            request.setParametrosDatos("foto", encodedImage);
            request.setParametrosDatos("url_foto",
                    comision.getURL_COMISION());
        }
        if (tipo == 0) {
            request.setQuery("SUBIR");
            request.setParametrosDatos("usuario_creador", comision.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", comision.getFECHA_CREACION());
            //requestUrl.setParametrosDatos("URL",URL+auxiliarGeneral.getInsertPHP("Comision"));
            URL = URL+auxiliarGeneral.getInsertPHP("Comision");

        }else{
            request.setQuery("EDITAR");
            request.setParametrosDatos("id_comision", String.valueOf(comision.getID_COMISION()));
            request.setParametrosDatos("usuario_actualizacion", comision.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", comision.getFECHA_ACTUALIZACION());
            //requestUrl.setParametrosDatos("URL",URL+auxiliarGeneral.getUpdatePHP("Comision"));
            URL = URL+auxiliarGeneral.getUpdatePHP("Comision");
        }
        new TaskComision().execute(request);
    }
    // enviar/editar articulo
    public class TaskComision extends AsyncTask<Request, Boolean, Boolean> {
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
            //String UrlParsing = null;
            try {
                //UrlParsing = params[1].getParametros().get("URL");
                json = jsonParsing.parsingJsonObject(params[0],URL);
                if (json != null) {
                    success = json.getInt(TAG_SUCCESS);
                    mensaje =json.getString(TAG_MESSAGE);
                    if (success == 0) {
                        if (insertar) {
                            int id = json.getInt(TAG_ID);
                            if (id > 0) {
                                if (controladorAdeful.insertComisionAdeful(id, comision)) {
                                    precessOK = true;
                                } else {
                                    precessOK = false;
                                }
                            } else {
                                precessOK = false;
                            }
                        } else {
                            if (controladorAdeful.actualizarComisionAdeful(comision)) {
                                precessOK = true;
                            }else{
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
                    inicializarControles(GUARDAR_USUARIO);
                } else {
                    actualizar = false;
                    insertar = true;
                    inicializarControles(ACTUALIZAR_USUARIO);
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

            Intent slash = new Intent(getActivity(),
                    SplashUsuario.class);
            startActivity(slash);

            return true;
        }

        if (id == R.id.action_permisos) {
            return true;
        }

        if (id == R.id.action_guardar) {

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
                cargarEntidad(0,0);
            } else { //COMISION ACTUALIZAR
                cargoSpinner = (Cargo) puestoSpinnerComision.getSelectedItem();
                cargarEntidad(idComisionExtra,1);
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

                                                cargo = new Cargo(0,
                                                        dialogoAlerta.editTextUno
                                                                .getText()
                                                                .toString(), usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());
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
                                                        null, null, usuario, auxiliarGeneral.getFechaOficial());
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