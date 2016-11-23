package com.estrelladelsur.estrelladelsur.miequipo.administrador.general;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.UtilityImage;
import com.estrelladelsur.estrelladelsur.entidad.Division;
import com.estrelladelsur.estrelladelsur.entidad.Jugador;
import com.estrelladelsur.estrelladelsur.entidad.Posicion;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerDivision;
import com.estrelladelsur.estrelladelsur.adaptador.adeful_lifuba.AdapterSpinnerPosicion;
import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoMenuLista;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGeneric;
import com.estrelladelsur.estrelladelsur.webservice.Request;

public class FragmentGenerarJugador extends Fragment implements MyAsyncTaskListener{

    private Spinner jugadoresDivisionSpinner;
    private Spinner jugadoresPosicionSpinner;
    private ControladorAdeful controladorAdeful;
    private ImageView imageJugador;
    private EditText jugadoresNombreEdit;
    private int CheckedPositionFragment;
    private ByteArrayOutputStream baos;
    private byte[] imageJugadorByte = null;
    private boolean insertar = true;
    private Jugador jugador;
    private Division division;
    private AdapterSpinnerDivision adapterSpinnerDivision;
    private AdapterSpinnerPosicion adapterSpinnerPosicion;
    private ArrayList<Division> divisionArray;
    private ArrayList<Posicion> posicionArray;
    private Posicion posicion;
    private DialogoAlerta dialogoAlerta;
    private DialogoAlerta dialogoAlertaEditar;
    private DialogoMenuLista dialogoMenuLista;
    private boolean actualizar = false;
    private int idJugadorExtra;
    private String usuario;
    private String URL = null, encodedImage = null, nombre_foto_anterior = null,
            url_foto_jugador = null, fechaFoto = null, nombre_foto = null, url_nombre_foto = null;
    private ArrayAdapter<Posicion> posicionAdapter;
    private ArrayAdapter<String> adaptadorInicial;
    private AuxiliarGeneral auxiliarGeneral;
    private Typeface editTextFont;
    private Typeface textViewFont;
    private Request request = new Request();
    private boolean isJugador = true, insertarPosicion = true;
    private ImageButton rotateButton;

    public static FragmentGenerarJugador newInstance() {
        FragmentGenerarJugador fragment = new FragmentGenerarJugador();
        return fragment;
    }

    public FragmentGenerarJugador() {
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        controladorAdeful = new ControladorAdeful(getActivity());
        if (state != null) {
            CheckedPositionFragment = state.getInt("curChoice", 0);

        } else {
            init();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_generar_jugador,
                container, false);
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        editTextFont = auxiliarGeneral.textFont(getActivity());
        textViewFont = auxiliarGeneral.tituloFont(getActivity());

        rotateButton = (ImageButton) v.findViewById(R.id.rotateButton);
        // FOTO JUGADOR
        imageJugador = (ImageView) v.findViewById(R.id.imageJugador);
        // NOMBRE JUGADOR
        jugadoresNombreEdit = (EditText) v
                .findViewById(R.id.jugadoresNombreEdit);
        jugadoresNombreEdit.setTypeface(editTextFont);
        // DIVISION
        jugadoresDivisionSpinner = (Spinner) v
                .findViewById(R.id.jugadoresDivisionSpinner);
        // PUESTO
        jugadoresPosicionSpinner = (Spinner) v
                .findViewById(R.id.jugadoresPosicionSpinner);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        usuario = auxiliarGeneral.getUsuarioPreferences(getActivity());

        // DIVISION
        divisionArray = controladorAdeful.selectListaDivisionAdeful();
        if (divisionArray != null) {
            // DIVSION SPINNER
            if (divisionArray.size() != 0) {
                adapterSpinnerDivision = new AdapterSpinnerDivision(getActivity(),
                        R.layout.simple_spinner_dropdown_item, divisionArray);
                jugadoresDivisionSpinner.setAdapter(adapterSpinnerDivision);
            } else {
                //SPINNER HINT
                adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                        R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerDivision));
                jugadoresDivisionSpinner.setAdapter(adaptadorInicial);
            }
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        // POSICION
        loadSpinnerPosicion();
        actualizar = getActivity().getIntent().getBooleanExtra("actualizar",
                false);
        //Metodo Extra
        if (actualizar) {
            idJugadorExtra = getActivity().getIntent().getIntExtra("id_jugador", 0);
            //DIVISION
            jugadoresDivisionSpinner.setSelection(getPositionSpinner(getActivity().getIntent()
                    .getIntExtra("divisionSpinner", 0), 0));
            // POSICION
            jugadoresPosicionSpinner.setSelection(getPositionSpinner(getActivity().getIntent()
                    .getIntExtra("posicionSpinner", 0), 1));
            //NOMBRE
            jugadoresNombreEdit.setText(getActivity().getIntent()
                    .getStringExtra("nombre"));

            nombre_foto_anterior = getActivity().getIntent()
                    .getStringExtra("nombre_foto");
            //FOTO
            imageJugadorByte = getActivity().getIntent()
                    .getByteArrayExtra("foto");

            if (imageJugadorByte != null) {
                Bitmap theImage = auxiliarGeneral.setByteToBitmap(imageJugadorByte, 150,
                        150);
                imageJugador.setImageBitmap(theImage);
            } else {
                imageJugador.setImageResource(R.mipmap.ic_foto_galery);
            }
            insertar = false;
        }
        imageJugador.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ImageDialogjugador();
            }
        });

        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageJugadorByte != null){
                    Bitmap theImage = auxiliarGeneral.setByteToBitmap(imageJugadorByte, 150,
                            150);
                    theImage = auxiliarGeneral.RotateBitmap(theImage);
                    imageJugador.setImageBitmap(theImage);
                    imageJugadorByte = auxiliarGeneral.pasarBitmapByte(theImage);
                }
            }
        });
    }

    public void ImageDialogjugador() {

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

            Bitmap b = auxiliarGeneral.SeleccionarImagen(data, getActivity(), true);
            if (b != null)
                imageJugador.setImageBitmap(b);
            imageJugadorByte = auxiliarGeneral.pasarBitmapByte(b);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void loadSpinnerPosicion() {
        if (selectPosicionList().size() != 0) {
            // CARGO SPINNER
            adapterSpinnerPosicion = new AdapterSpinnerPosicion(getActivity(),
                    R.layout.simple_spinner_dropdown_item, posicionArray);
            jugadoresPosicionSpinner.setAdapter(adapterSpinnerPosicion);
        } else {
            //SPINNER HINT
            adaptadorInicial = new ArrayAdapter<String>(getActivity(),
                    R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ceroSpinnerPosicion));
            jugadoresPosicionSpinner.setAdapter(adaptadorInicial);
        }
    }

    public ArrayList<Posicion> selectPosicionList() {
        //POSICION
        posicionArray = controladorAdeful.selectListaPosicionAdeful();
        if (posicionArray != null) {
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
        return posicionArray;
    }

    // POPULATION LISTVIEW
    public void loadListViewMenu() {
        posicionAdapter = new ArrayAdapter<Posicion>(getActivity(),
                R.layout.listview_item_dialogo, R.id.textViewGeneral, selectPosicionList());
        dialogoMenuLista.listViewGeneral.setAdapter(posicionAdapter);
    }

    public void inicializarControles(String mensaje) {
        if (imageJugadorByte != null) {
            imageJugador
                    .setImageResource(R.mipmap.ic_foto_galery);
            imageJugadorByte = null;
        }
        jugadoresNombreEdit.setText("");
        imageJugadorByte = null;
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void inicializarControlesPosicion(String mensaje) {
        loadSpinnerPosicion();
        if (insertarPosicion) {
            dialogoAlerta.alertDialog
                    .dismiss();
        } else {
            loadListViewMenu();
            dialogoAlertaEditar.alertDialog.dismiss();
        }
        Toast.makeText(getActivity(), mensaje,
                Toast.LENGTH_SHORT).show();
    }
    public void cargarEntidadPosicion(int id, int ws, String divisionEntidad) {
        URL = null;
        URL = auxiliarGeneral.getURLPOSICIONADEFULALL();

        posicion = new Posicion(id, divisionEntidad,
                usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());

        envioWebServicePosicion(ws);
    }

    public void cargarEntidad(int id, int ws) {

        String nombre = null;
        nombre = jugadoresNombreEdit.getText().toString();
        URL = null;
        url_foto_jugador = null;
        // si la foto es null(default) no enviaremos la url.
        if (imageJugadorByte != null) {
            url_nombre_foto = auxiliarGeneral.removeAccents(nombre.replace(" ", "").trim());
            fechaFoto = auxiliarGeneral.getFechaImagen();
            nombre_foto = fechaFoto + url_nombre_foto + ".PNG";
            url_foto_jugador = auxiliarGeneral.getURLFOTOJUGADORADEFUL() + nombre_foto;
        }
        URL = auxiliarGeneral.getURLJUGADORADEFULAll();

        jugador = new Jugador(id, nombre, imageJugadorByte, nombre_foto, url_foto_jugador,
                division.getID_DIVISION(), posicion.getID_POSICION(), usuario,
                auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());

        envioWebService(ws);
    }

    public void envioWebServicePosicion(int tipo) {
        request.setMethod("POST");
        request.setParametrosDatos("posicion", posicion.getDESCRIPCION());
        if (tipo == 0) {
            request.setParametrosDatos("usuario_creador", posicion.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", posicion.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Posicion");
            insertarPosicion = true;
        } else {
            request.setParametrosDatos("id_posicion", String.valueOf(posicion.getID_POSICION()));
            request.setParametrosDatos("usuario_actualizacion", posicion.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", posicion.getFECHA_ACTUALIZACION());
            URL = URL + auxiliarGeneral.getUpdatePHP("Posicion");
            insertarPosicion = false;
        }
        isJugador = false;
        new AsyncTaskGeneric(getActivity(), this, URL, request, "Posicion", posicion, insertarPosicion, "a");
    }
    public void envioWebService(int tipo) {
        request.setMethod("POST");
        request.setParametrosDatos("nombre", jugador.getNOMBRE_JUGADOR());
        request.setParametrosDatos("id_division", String.valueOf(division.getID_DIVISION()));
        request.setParametrosDatos("id_posicion", String.valueOf(posicion.getID_POSICION()));

        if (imageJugadorByte != null) {
            encodedImage = Base64.encodeToString(imageJugadorByte,
                    Base64.DEFAULT);

            request.setParametrosDatos("foto", encodedImage);
            request.setParametrosDatos("url_foto",
                    jugador.getURL_JUGADOR());
            request.setParametrosDatos("nombre_foto", jugador.getNOMBRE_FOTO());

        }
        if (tipo == 0) {
            //request.setQuery("SUBIR");
            request.setParametrosDatos("usuario_creador", jugador.getUSUARIO_CREACION());
            request.setParametrosDatos("fecha_creacion", jugador.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Jugador");

        } else {
            //request.setQuery("EDITAR");
            request.setParametrosDatos("id_jugador", String.valueOf(jugador.getID_JUGADOR()));
            request.setParametrosDatos("usuario_actualizacion", jugador.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", jugador.getFECHA_ACTUALIZACION());
            if (nombre_foto_anterior != null)
                request.setParametrosDatos("nombre_foto_anterior", nombre_foto_anterior);

            URL = URL + auxiliarGeneral.getUpdatePHP("Jugador");
        }
        isJugador = true;
        new AsyncTaskGeneric(getActivity(), this, URL, request, "Jugador", jugador, insertar, "o");
    }

    private int getPositionSpinner(int idSpinner, int spinner) {

        int index = 0;
        switch (spinner) {
            //DIVISION 0
            case 0:
                for (int i = 0; i < divisionArray.size(); i++) {
                    if (divisionArray.get(i).getID_DIVISION() == (idSpinner)) {
                        index = i;
                    }
                }
                break;
            // POSICION 1
            case 1:
                for (int i = 0; i < posicionArray.size(); i++) {
                    if (posicionArray.get(i).getID_POSICION() == (idSpinner)) {
                        index = i;
                    }
                }
                break;
         }

        return index;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_administrador_general, menu);
        // menu.getItem(0).setVisible(false);//usuario
        //menu.getItem(1).setVisible(false);// posicion
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

            if (jugadoresDivisionSpinner.getSelectedItem().toString().equals(getResources().getString(R.string.ceroSpinnerDivision))) {
                Toast.makeText(getActivity(), "Debe Cargar una División.(Liga)",
                        Toast.LENGTH_SHORT).show();
            } else if (jugadoresPosicionSpinner.getSelectedItem().toString().equals(getResources().getString(R.string.ceroSpinnerPosicion))) {
                Toast.makeText(getActivity(), "Debe Cargar una Posición.(Menú-Posición)",
                        Toast.LENGTH_SHORT).show();
            } else if (jugadoresNombreEdit.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Ingrese el Nombre del Jugador.",
                        Toast.LENGTH_SHORT).show();
            } else {
                division = (Division) jugadoresDivisionSpinner.getSelectedItem();
                posicion = (Posicion) jugadoresPosicionSpinner.getSelectedItem();
                if(insertar)
                    cargarEntidad(0, 0);
                else
                    cargarEntidad(idJugadorExtra, 1);
            }
            return true;
        }

        if (id == R.id.action_posicion) {

            dialogoAlerta = new DialogoAlerta(
                    getActivity(),
                    "POSICION",
                    "En esta opción puede agreagar o editar una posición de juego",
                    "Ingrese posición", null);
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

                            // Crear la Posicion
                            dialogoAlerta.btnAceptar
                                    .setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            // TODO Auto-generated method stub
                                            if (!dialogoAlerta.editTextUno.getText().toString().equals("")) {
                                                cargarEntidadPosicion(0, 0, dialogoAlerta.editTextUno.getText().toString());
                                            } else {
                                                Toast.makeText(
                                                        getActivity(),
                                                        "Ingrese una posición.",
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
            dialogoAlerta.btnCancelar
                    .setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub

                            dialogoMenuLista = new DialogoMenuLista(getActivity(),
                                    "POSICION");

                            dialogoMenuLista.btnAceptar.setText("Aceptar");
                            dialogoMenuLista.btnCancelar.setText("Cancelar");
                            loadListViewMenu();
                            dialogoMenuLista.listViewGeneral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

                                    dialogoAlertaEditar = new DialogoAlerta(
                                            getActivity(),
                                            "POSICION",
                                            null,
                                            null, null);

                                    dialogoAlertaEditar.editTextUno.setVisibility(View.VISIBLE);
                                    dialogoAlertaEditar.editTextUno.setText(parent.getItemAtPosition(position).toString());
                                    dialogoAlertaEditar.btnAceptar.setText("Aceptar");
                                    dialogoAlertaEditar.btnCancelar.setText("Cancelar");

                                    dialogoAlertaEditar.btnAceptar.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            if (!dialogoAlerta.editTextUno.getText().toString().equals("")) {
                                                cargarEntidadPosicion(posicionArray.get(position).getID_POSICION(), 1, dialogoAlerta.editTextUno.getText().toString());
                                            } else {
                                                Toast.makeText(
                                                        getActivity(),
                                                        "Ingrese una posición.",
                                                        Toast.LENGTH_SHORT)
                                                        .show();
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
                            // Editar Posicion
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

    @Override
    public void onPostExecuteConcluded(boolean result, String mensaje) {
        if (isJugador) {
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
                if (insertarPosicion) {
                    inicializarControlesPosicion(mensaje);
                } else {
                    inicializarControlesPosicion(mensaje);
                }
            } else {
                auxiliarGeneral.errorWebService(getActivity(), mensaje);
            }
            isJugador = true;
        }
    }
}