package com.estrelladelsur.estrelladelsur.liga.usuario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.liga.usuario.tabs_user.TabsAdefulUsuario;
import com.estrelladelsur.estrelladelsur.webservice.JsonParsing;
import com.estrelladelsur.estrelladelsur.webservice.Request;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapaCanchaUsuario extends AppCompatActivity implements OnMapReadyCallback {

    private ArrayList<Cancha> canchaAdefulArray;
    private TextView tvAddress;
    private GoogleMap mapa;
    public static double latBache = -41.139755445793554;
    public static double lonBache = -71.296729134343434;
    private Toolbar toolbar;
    private TextView txtTitulo;
    private String locationAddress;
    private String longitud;
    private String latitud;
    private double longitudExtra;
    private double latitudExtra;
    private Cancha cancha;
    private EditText editTextNombre;
    private boolean actualizar = false;
    private int id_extra;
    private String nombre = null, URL = null, usuario = null, mensaje = null;
    private boolean insertar = true;
    private ControladorAdeful controladorAdeful;
    private SupportMapFragment mapFragment;
    private Typeface titulos;
    private Typeface editTextFont;
    private Handler touchScreem;
    private AuxiliarGeneral auxiliarGeneral;
    private String GUARDAR = "Cancha cargada correctamente";
    private String ACTUALIZAR = "Cancha actualizada correctamente";
    private Request request;
    private JsonParsing jsonParsing = new JsonParsing(MapaCanchaUsuario.this);
    private ProgressDialog dialog;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_ID = "id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_cancha);

        //   controladorAdeful = new ControladorAdeful(this);
        auxiliarGeneral = new AuxiliarGeneral(MapaCanchaUsuario.this);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTitulo = (TextView) toolbar.findViewById(R.id.txtToolBarTitulo);
        txtTitulo.setText("MAPA");
        txtTitulo.setTypeface(titulos);

        // actualizar = getIntent().getBooleanExtra("actualizar", false);
        init();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapa = googleMap;
        touchScreem = new Handler();
        touchScreem.postDelayed(new Runnable() {
            @Override
            public void run() {
                mapa.getUiSettings().setAllGesturesEnabled(true);
            }
        }, 3000);
        mapa.getUiSettings().setAllGesturesEnabled(false);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latBache, lonBache)).zoom(15).build();
        CameraUpdate cu = CameraUpdateFactory
                .newCameraPosition(cameraPosition);
        mapa.animateCamera(cu);

        longitud = getIntent().getStringExtra("longitud");
        latitud = getIntent().getStringExtra("latitud");
        longitudExtra = Double.valueOf(longitud);
        latitudExtra = Double.valueOf(latitud);
        locationAddress = getIntent().getStringExtra("direccion");
        nombre = getIntent().getStringExtra("nombre");
        txtTitulo.setText(nombre);
        mapa.addMarker(new MarkerOptions().position(
                new LatLng(latitudExtra, longitudExtra)).snippet(locationAddress).title(nombre));

        tvAddress.setText(locationAddress);
    }

    public void init() {
        usuario = auxiliarGeneral.getUsuarioPreferences(MapaCanchaUsuario.this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvAddress.setTypeface(editTextFont);
        tvAddress.setTextSize(25);
        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextNombre.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        volver();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_administrador_general, menu);
        menu.getItem(0).setVisible(false);//usuario
        menu.getItem(1).setVisible(false);// posicion
        menu.getItem(2).setVisible(false);// cargo
        menu.getItem(3).setVisible(false);//cerrar
        menu.getItem(4).setVisible(false);// guardar
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_guardar) {
//
//            if (editTextNombre.getText().toString().equals("")) {
//                Toast.makeText(this, "Ingrese el nombre de la cancha.",
//                        Toast.LENGTH_SHORT).show();
//
//            } else if (tvAddress.getText().toString().equals("")) {
//                Toast.makeText(this, "Seleccione un punto en el mapa.",
//                        Toast.LENGTH_SHORT).show();
//            } else if (tvAddress.getText().toString()
//                    .equals(getResources().getString(R.string.error_internet))) {
//                Toast.makeText(this, "Verifique su conexión de Internet.",
//                        Toast.LENGTH_SHORT).show();
//            } else {
//                if (insertar) {
//                    cargarEntidad(0);
//                } else {
//                    cargarEntidad(id_extra);
//                }
//            }
//            return true;
//        }

        if (id == android.R.id.home) {

            //    NavUtils.navigateUpFromSameTask(this);

            //a.refreshs();
            //comm.refreshAdeful();
            volver();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void volver() {
        Intent i = new Intent(MapaCanchaUsuario.this, TabsAdefulUsuario.class);
        i.putExtra("restart", 1);
        startActivity(i);
    }

}
