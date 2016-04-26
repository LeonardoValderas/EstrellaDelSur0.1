package com.estrelladelsur.estrelladelsur.liga;

import java.util.ArrayList;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.graphics.Point;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.Toast;

public class MapaCancha extends AppCompatActivity implements OnMapReadyCallback {

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
    private int posicion;
    private String nombre = null;
    private boolean insertar = true;
    private ControladorAdeful controladorAdeful;
    private SupportMapFragment mapFragment;
    private Typeface titulos;
    private Typeface editTextFont;
    private Handler touchScreem;
    private AuxiliarGeneral auxiliarGeneral;
    private String GUARDAR_USUARIO = "Cancha cargada correctamente";
    private String ACTUALIZAR_USUARIO = "Cancha actualizada correctamente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_cancha);

        titulos = auxiliarGeneral.tituloFont(MapaCancha.this);
        editTextFont = auxiliarGeneral.textFont(MapaCancha.this);

        controladorAdeful = new ControladorAdeful(this);
        auxiliarGeneral = new AuxiliarGeneral(MapaCancha.this);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTitulo = (TextView) toolbar.findViewById(R.id.txtAbTitulo);
        txtTitulo.setText("MAPA CANCHA");
        txtTitulo.setTypeface(titulos);

        actualizar = getIntent().getBooleanExtra("actualizar", false);
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

        mapa.setOnMapClickListener(new OnMapClickListener() {
            public void onMapClick(LatLng point) {
                Projection proj = mapa.getProjection();
                Point coord = proj.toScreenLocation(point);

                mapa.clear();
                tvAddress.setText("");
                mapa.addMarker(new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude)).title(
                        "Dirección: \n" + tvAddress.getText().toString()));

                LocationAddress locationAddress = new LocationAddress();
                locationAddress.getAddressFromLocation(point.latitude,
                        point.longitude, getApplicationContext(),
                        new GeocoderHandler());
            }
        });

        mapa.setOnMapLongClickListener(new OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mapa.clear();
                tvAddress.setText("");
            }
        });

        if (actualizar) {
            longitud = getIntent().getStringExtra("longitud");
            latitud = getIntent().getStringExtra("latitud");
            longitudExtra = Double.valueOf(longitud);
            latitudExtra = Double.valueOf(latitud);
            locationAddress = getIntent().getStringExtra("direccion");
            posicion = getIntent().getIntExtra("posicion", 0);
            nombre = getIntent().getStringExtra("nombre");
            editTextNombre.setText(nombre);

            mapa.addMarker(new MarkerOptions().position(
                    new LatLng(latitudExtra, longitudExtra)).title(
                    "Dirección: \n" + locationAddress));

            tvAddress.setText(locationAddress);
            insertar = false;
        }
    }

    public void init() {

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        canchaAdefulArray = controladorAdeful.selectListaCanchaAdeful();
        if (canchaAdefulArray == null)
         auxiliarGeneral.errorDataBase(MapaCancha.this);

        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvAddress.setTypeface(editTextFont);
        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextNombre.setTypeface(editTextFont,Typeface.BOLD);
    }

    public boolean isGpsActivo() {

        boolean gps = true;
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || !locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            gps = false;
        }
        return gps;
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    longitud = String.valueOf(bundle.getDouble("longitud"));
                    latitud = String.valueOf(bundle.getDouble("latitud"));
                    break;
                default:
                    locationAddress = null;
            }
            tvAddress.setText(locationAddress);
        }
    }


    public void inicializarControles(String mensaje) {
        mapa.clear();
        editTextNombre.setText("");
        tvAddress.setText("");
        volver();
        Toast.makeText(MapaCancha.this,
                mensaje,
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {

        volver();

//		Intent i = new Intent(MapaCancha.this, TabsAdeful.class);
//		i.putExtra("restart", 1);
//		startActivity(i);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_administrador_general, menu);
        menu.getItem(0).setVisible(false);// usuario
        menu.getItem(1).setVisible(false);// permiso
        menu.getItem(2).setVisible(false);// lifuba
        menu.getItem(3).setVisible(false);// adeful
        menu.getItem(4).setVisible(false);// puesto
        menu.getItem(5).setVisible(false);// posicion
        menu.getItem(6).setVisible(false);// cargo
        menu.getItem(7).setVisible(false);// cerrar
        // menu.getItem(8).setVisible(false);//gudar
        menu.getItem(9).setVisible(false);// Subir
        menu.getItem(10).setVisible(false); // eliminar
        menu.getItem(11).setVisible(false); // consultar
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // noinspection SimplifiableIfStatement
        if (id == R.id.action_guardar) {

            if (editTextNombre.getText().toString().equals("")) {
                Toast.makeText(this, "Ingrese el nombre de la cancha.",
                        Toast.LENGTH_SHORT).show();

            } else if (tvAddress.getText().toString().equals("")) {
                Toast.makeText(this, "Seleccione un punto en el mapa.",
                        Toast.LENGTH_SHORT).show();
            } else if (tvAddress.getText().toString()
                    .equals(getResources().getString(R.string.error_internet))) {
                Toast.makeText(this, "Verifique su conexión de Internet.",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (insertar) {
                    String usuario = "Administrador";
                    cancha = new Cancha(0, editTextNombre.getText().toString(),
                            longitud, latitud, tvAddress.getText().toString(),
                            usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());
                if (controladorAdeful.insertCanchaAdeful(cancha)) {
                       inicializarControles(GUARDAR_USUARIO);
                    } else {
                    auxiliarGeneral.errorDataBase(MapaCancha.this);
                    }
                } else {
                    String usuario = "Administrador";

                    cancha = new Cancha(canchaAdefulArray.get(posicion)
                            .getID_CANCHA(), editTextNombre.getText()
                            .toString(), longitud, latitud, tvAddress.getText()
                            .toString(), null, null, usuario, auxiliarGeneral.getFechaOficial());

                    if (controladorAdeful.actualizarCanchaAdeful(cancha)) {
                        insertar = true;
                     inicializarControles(ACTUALIZAR_USUARIO);
                    } else {
                   auxiliarGeneral.errorDataBase(MapaCancha.this);
                    }
                }
            }
            return true;
        }

        if (id == android.R.id.home) {

            // NavUtils.navigateUpFromSameTask(this);

            //a.refreshs();
            //comm.refresh();
            volver();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void volver() {
        Intent i = new Intent(MapaCancha.this, TabsAdeful.class);
        i.putExtra("restart", 1);
        startActivity(i);
    }

}
