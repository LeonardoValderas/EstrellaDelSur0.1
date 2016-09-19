package com.estrelladelsur.estrelladelsur.miequipo.adeful;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoAlerta;
import com.estrelladelsur.estrelladelsur.dialogo.adeful_lifuba.DialogoMenuLista;

public class FragmentGenerarJugador extends Fragment {

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
    private String usuario = "Administrador";
    private String fechaCreacion = null;
    private String fechaActualizacion = null;
    private ArrayAdapter<Posicion> posicionAdapter;
    private ArrayAdapter<String> adaptadorInicial;
    private AuxiliarGeneral auxiliarGeneral;
    private String GUARDAR_USUARIO = "Jugador ingresado correctamente";
    private String ACTUALIZAR_USUARIO = "Jugador actualizado correctamente";
    private Typeface editTextFont;
    private Typeface textViewFont;

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
        usuario = "Administrador";

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
            jugadoresDivisionSpinner.setSelection(getActivity().getIntent()
                    .getIntExtra("divisionSpinner", 0) - 1);
            // TORNEO
            jugadoresPosicionSpinner.setSelection(getActivity().getIntent()
                    .getIntExtra("posicionSpinner", 0) - 1);
            //NOMBRE
            jugadoresNombreEdit.setText(getActivity().getIntent()
                    .getStringExtra("nombre"));
            //FOTO
            imageJugadorByte = getActivity().getIntent()
                    .getByteArrayExtra("foto");

            if (imageJugadorByte != null) {
                Bitmap theImage = BitmapFactory.decodeByteArray(imageJugadorByte, 0,
                        imageJugadorByte.length);
                theImage = Bitmap.createScaledBitmap(theImage, 150, 150, true);
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

            Bitmap b = auxiliarGeneral.SeleccionarImagen(data, getActivity(),true);
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
         if(selectPosicionList().size()!=0){
                // CARGO SPINNER
                adapterSpinnerPosicion = new AdapterSpinnerPosicion(getActivity(),
                        R.layout.simple_spinner_dropdown_item, posicionArray);
                jugadoresPosicionSpinner.setAdapter(adapterSpinnerPosicion);
            }else{
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_administrador_general, menu);
        // menu.getItem(0).setVisible(false);//usuario
        menu.getItem(1).setVisible(false);//permiso
        menu.getItem(2).setVisible(false);//lifuba
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
                if (insertar) {
                    jugador = new Jugador(0, jugadoresNombreEdit.getText()
                            .toString(), imageJugadorByte,
                            division.getID_DIVISION(),
                            posicion.getID_POSICION(), usuario, fechaCreacion, usuario, fechaActualizacion);

                    if (controladorAdeful.insertJugadorAdeful(jugador)) {
                       inicializarControles(GUARDAR_USUARIO);
                    } else {
                    auxiliarGeneral.errorDataBase(getActivity());
                    }

                } else {
                    jugador = new Jugador(idJugadorExtra, jugadoresNombreEdit.getText()
                            .toString(), imageJugadorByte,
                            division.getID_DIVISION(),
                            posicion.getID_POSICION(), null, null, usuario, fechaActualizacion);
                    if (controladorAdeful.actualizarJugadorAdeful(jugador)) {
                        actualizar = false;
                        insertar = true;
                        inicializarControles(ACTUALIZAR_USUARIO);
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

              posicion = new Posicion(0,dialogoAlerta.editTextUno.getText().toString(),
                      usuario, fechaCreacion, usuario, fechaActualizacion);
                   if (controladorAdeful
                           .insertPosicionAdeful(posicion)) {
                       loadSpinnerPosicion();
                       Toast.makeText(
                               getActivity(),
                               "Posición cargada correctamente.",
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
                                            String usuario = "Administrador";
                                            posicion = new Posicion(
                                                    posicionArray.get(position).getID_POSICION(),
                                                    dialogoAlertaEditar.editTextUno.getText().toString(),
                                                    null, null, usuario, auxiliarGeneral.getFechaOficial());

                                            if (controladorAdeful
                                                    .actualizarPosicionAdeful(posicion)) {
                                                loadListViewMenu();
                                                loadSpinnerPosicion();
                                                dialogoAlertaEditar.alertDialog.dismiss();
                                                Toast.makeText(
                                                        getActivity(),
                                                        "Posición actualizada correctamente.",
                                                        Toast.LENGTH_SHORT).show();
                                            } else {
                                            auxiliarGeneral.errorDataBase(getActivity());
                                            }
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

}