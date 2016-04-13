package com.estrelladelsur.estrelladelsur.liga;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.DividerItemDecoration;
import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.UtilityImage;
import com.estrelladelsur.estrelladelsur.entidad.Equipo;
import com.estrelladelsur.estrelladelsur.adaptador.AdaptadorRecyclerEquipo;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.dialogo.DialogoAlerta;

public class FragmentEquipo extends Fragment {
    private byte[] imagenEscudo = null;
    private RecyclerView recycleViewEquipo;
    private EditText editTextNombre;
    private ImageView imageEquipo;
    private Equipo equipoAdeful;
    private ArrayList<Equipo> equipoAdefulArray;
    private AdaptadorRecyclerEquipo adaptador;
    private DialogoAlerta dialogoAlerta;
    private boolean insertar = true;
    private int posicion;
    private ControladorAdeful controladorAdeful;
    private int CheckedPositionFragment;
    private String GUARDAR_USUARIO = "Equipo cargado correctamente";
    private String ACTUALIZAR_USUARIO = "Equipo actualizado correctamente";
    private Typeface editTextFont;
    private AuxiliarGeneral auxiliarGeneral;

    public static FragmentEquipo newInstance() {
        FragmentEquipo fragment = new FragmentEquipo();
        return fragment;
    }

    public FragmentEquipo() {
        // Required empty public constructor
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
        View v = inflater.inflate(R.layout.fragment_general_liga, container,
                false);
        editTextFont = Typeface.createFromAsset(getActivity().getAssets(), "ATypewriterForMe.ttf");
        imageEquipo = (ImageView) v
                .findViewById(R.id.imageButtonEquipo_Cancha);
        editTextNombre = (EditText) v.findViewById(R.id.editTextDescripcion);
        editTextNombre.setTypeface(editTextFont);
        recycleViewEquipo = (RecyclerView) v
                .findViewById(R.id.recycleViewGeneral);
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", CheckedPositionFragment);
    }

    private void init() {
        auxiliarGeneral = new AuxiliarGeneral(getActivity());
        // imageButton que busca imagen del escudo del equipo en la memoria  interna
        imageEquipo.setImageResource(R.mipmap.ic_escudo_cris);
        imageEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageDialogEquipo();
            }
        });
        editTextNombre.setHintTextColor(Color.GRAY);

        recycleViewEquipo.addOnItemTouchListener(new RecyclerTouchListener(
                getActivity(), recycleViewEquipo, new ClickListener() {

            @Override
            public void onLongClick(View view, final int position) {

                dialogoAlerta = new DialogoAlerta(getActivity(), "ALERTA",
                        "Desea eliminar el equipo?", null, null);
                dialogoAlerta.btnAceptar.setText("Aceptar");
                dialogoAlerta.btnCancelar.setText("Cancelar");

                dialogoAlerta.btnAceptar
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                if (controladorAdeful
                                        .eliminarEquipoAdeful(equipoAdefulArray
                                                .get(position)
                                                .getID_EQUIPO())) {
                                    recyclerViewLoadEquipo();
                                    Toast.makeText(
                                            getActivity(),
                                            "Equipo eliminado correctamente",
                                            Toast.LENGTH_SHORT).show();
                                    imageEquipo.setImageResource(R.mipmap.ic_escudo_cris);
                                    editTextNombre.setText("");
                                    insertar = true;
                                    dialogoAlerta.alertDialog.dismiss();
                                } else {
                                    auxiliarGeneral.errorDataBase(getActivity());
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

            @Override
            public void onClick(View view, int position) {
                insertar = false;
                imageEquipo.setImageResource(R.mipmap.ic_escudo_cris);
                editTextNombre.setText(equipoAdefulArray.get(position)
                        .getNOMBRE_EQUIPO());
                if (equipoAdefulArray.get(position).getESCUDO() != null) {
                    Bitmap theImage = BitmapFactory
                            .decodeByteArray(
                                    equipoAdefulArray.get(position)
                                            .getESCUDO(), 0,
                                    equipoAdefulArray.get(position)
                                            .getESCUDO().length);
                    theImage = Bitmap.createScaledBitmap(theImage, 150,
                            150, true);
                    imageEquipo.setImageBitmap(theImage);
                    imagenEscudo = equipoAdefulArray.get(position)
                            .getESCUDO();
                }
                posicion = position;
            }
        }));
        recyclerViewLoadEquipo();
    }

    public void recyclerViewLoadEquipo() {

        recycleViewEquipo.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewEquipo.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recycleViewEquipo.setItemAnimator(new DefaultItemAnimator());

        equipoAdefulArray = controladorAdeful.selectListaEquipoAdeful();
        if (equipoAdefulArray != null) {
            adaptador = new AdaptadorRecyclerEquipo(equipoAdefulArray,getActivity());
            recycleViewEquipo.setAdapter(adaptador);
        } else {
            auxiliarGeneral.errorDataBase(getActivity());
        }
    }

    public void ImageDialogEquipo() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                getActivity());
        myAlertDialog.setTitle("Galeria");
        myAlertDialog.setMessage("Seleccione un escudo");

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
            Bitmap b = auxiliarGeneral.SeleccionarImagen(data, getActivity());
            if (b != null)
                imageEquipo.setImageBitmap(b);
            imagenEscudo = auxiliarGeneral.pasarBitmapByte(b);
        }
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements
            RecyclerView.OnItemTouchListener {
        private GestureDetector detector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context,
                                     final RecyclerView recyclerView,
                                     final ClickListener clickListener) {
            this.clickListener = clickListener;
            detector = new GestureDetector(context,
                    new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            return true;
                        }

                        @Override
                        public void onLongPress(MotionEvent e) {
                            View child = recyclerView.findChildViewUnder(
                                    e.getX(), e.getY());
                            if (child != null && clickListener != null) {
                                clickListener.onLongClick(child,
                                        recyclerView.getChildPosition(child));
                            }
                        }
                    });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            // TODO Auto-generated method stub
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null
                    && detector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean arg0) {
            // TODO Auto-generated method stub
        }
    }

    public void inicializarControles(String mensaje) {

        recyclerViewLoadEquipo();
        editTextNombre.setText("");
        if (imagenEscudo != null) {
            imageEquipo.setImageResource(R.mipmap.ic_escudo_cris);
        }
        Toast.makeText(getActivity(),
                mensaje,
                Toast.LENGTH_SHORT).show();
        imagenEscudo = null;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_administrador_general, menu);
        // menu.getItem(0).setVisible(false);//usuario
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

            /*Intent usuario = new Intent(getActivity(),
                    NavigationDrawerUsuario.class);
            startActivity(usuario);*/

            return true;
        }

        if (id == R.id.action_permisos) {
            return true;
        }

        if (id == R.id.action_guardar) {

            if (editTextNombre.getText().toString().equals("")) {
                Toast.makeText(getActivity(),
                        "Ingrese el nombre del equipo.", Toast.LENGTH_SHORT)
                        .show();
            } else {
                if (insertar) {

                    String usuario = "Administrador";
                    String fechaCreacion = controladorAdeful.getFechaOficial();
                    String fechaActualizacion = fechaCreacion;

                    equipoAdeful = new Equipo(0, editTextNombre.getText()
                            .toString(), imagenEscudo, usuario,
                            fechaCreacion, usuario, fechaActualizacion);

                    if (controladorAdeful.insertEquipoAdeful(equipoAdeful)) {
                        inicializarControles(GUARDAR_USUARIO);
                    } else {
                        auxiliarGeneral.errorDataBase(getActivity());
                    }

                } else {
                    String usuario = "Administrador";
                    String fechaActualizacion = controladorAdeful
                            .getFechaOficial();

                    equipoAdeful = new Equipo(equipoAdefulArray.get(
                            posicion).getID_EQUIPO(), editTextNombre
                            .getText().toString(), imagenEscudo, null,
                            null, usuario, fechaActualizacion);

                    if (controladorAdeful.actualizarEquipoAdeful(equipoAdeful)) {
                        insertar = true;
                        inicializarControles(ACTUALIZAR_USUARIO);
                    } else {
                        auxiliarGeneral.errorDataBase(getActivity());
                    }
                }
            }
            return true;
        }
        if (id == android.R.id.home) {

            NavUtils.navigateUpFromSameTask(getActivity());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}