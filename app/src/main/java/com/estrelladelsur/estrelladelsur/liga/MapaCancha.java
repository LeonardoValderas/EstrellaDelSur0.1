package com.estrelladelsur.estrelladelsur.liga;

import java.util.ArrayList;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.database.ControladorAdeful;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
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
    GoogleMap mapa;
    private AppLocationService appLocationService;
    public static double latBache = -41.139755445793554;
    public static double lonBache = -71.296729134343434;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private TextView txtTitulo;
    private TextView txtAbSubTitulo;
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
    private TabsAdeful a;


    SupportMapFragment mapFragment;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //private Communicator comm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_cancha);

//		try {
//			MapsInitializer.initialize(this);
//
//		} catch (GooglePlayServicesNotAvailableException e){
//			e.printStackTrace();
//		}

        controladorAdeful = new ControladorAdeful(this);
        //	comm= (Communicator)MapaCancha.this;
        a = new TabsAdeful();
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTitulo = (TextView) toolbar.findViewById(R.id.txtAbTitulo);
        txtTitulo.setText("MAPA CANCHA");

        //txtAbSubTitulo = (TextView) toolbar.findViewById(R.id.txtAbSubTitulo);
        //txtAbSubTitulo.setVisibility(View.INVISIBLE);

        actualizar = getIntent().getBooleanExtra("actualizar", false);

//		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
//		mapFragment.getMapAsync(MapaCancha.this);


        init();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;

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
    }

    public void init() {

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        controladorAdeful.abrirBaseDeDatos();
        canchaAdefulArray = controladorAdeful.selectListaCanchaAdeful();
        if (canchaAdefulArray != null) {
            controladorAdeful.cerrarBaseDeDatos();
        } else {
            controladorAdeful.cerrarBaseDeDatos();
            Toast.makeText(this, "Error en la base de datos interna, vuelva a intentar." +
                            "\n Si el error persiste comuniquese con soporte.",
                    Toast.LENGTH_SHORT).show();
        }

        tvAddress = (TextView) findViewById(R.id.tvAddress);
        editTextNombre = (EditText) findViewById(R.id.editTextNombre);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // if (drawerToggle.onOptionsItemSelected(item)) {
        // return true;
        // }

        int id = item.getItemId();
        // noinspection SimplifiableIfStatement
        if (id == R.id.action_guardar) {

            if (editTextNombre.getText().toString().equals("")) {
                Toast.makeText(this, "Ingrese el nombre de la cancha.",
                        Toast.LENGTH_SHORT).show();

            } else if (tvAddress.getText().toString().equals("")) {

                Toast.makeText(this, "Seleccione un punto en el mapa.",
                        Toast.LENGTH_SHORT).show();

            } else if (editTextNombre.getText().toString()
                    .equals(getResources().getString(R.string.error_internet))) {

                Toast.makeText(this, "Verifique su conexión de Internet.",
                        Toast.LENGTH_SHORT).show();
            } else {

                if (insertar) {
                    String usuario = "Administrador";
                    String fechaCreacion = controladorAdeful.getFechaOficial();
                    String fechaActualizacion = fechaCreacion;

                    cancha = new Cancha(0, editTextNombre.getText().toString(),
                            longitud, latitud, tvAddress.getText().toString(),
                            usuario, fechaCreacion, usuario, fechaActualizacion);

                    controladorAdeful.abrirBaseDeDatos();
                    if (controladorAdeful.insertCanchaAdeful(cancha)) {
                        controladorAdeful.cerrarBaseDeDatos();
                        mapa.clear();
                        editTextNombre.setText("");
                        tvAddress.setText("");
                        Toast.makeText(this, "Cancha guardada correctamente",
                                Toast.LENGTH_SHORT).show();
                        volver();
                    } else {
                        controladorAdeful.cerrarBaseDeDatos();
                        Toast.makeText(this, "Error en la base de datos interna, vuelva a intentar." +
                                        "\n Si el error persiste comuniquese con soporte.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String usuario = "Administrador";
                    String fechaActualizacion = controladorAdeful.getFechaOficial();


                    cancha = new Cancha(canchaAdefulArray.get(posicion)
                            .getID_CANCHA(), editTextNombre.getText()
                            .toString(), longitud, latitud, tvAddress.getText()
                            .toString(), null, null, usuario, fechaActualizacion);

                    controladorAdeful.abrirBaseDeDatos();
                    if (controladorAdeful.actualizarCanchaAdeful(cancha)) {
                        controladorAdeful.cerrarBaseDeDatos();
                        mapa.clear();
                        editTextNombre.setText("");
                        tvAddress.setText("");
                        insertar = true;

                        Toast.makeText(this, "Cancha actualizada correctamente.",
                                Toast.LENGTH_SHORT).show();
                        volver();
                    } else {
                        controladorAdeful.cerrarBaseDeDatos();
                        Toast.makeText(this, "Error en la base de datos interna, vuelva a intentar." +
                                        "\n Si el error persiste comuniquese con soporte.",
                                Toast.LENGTH_SHORT).show();
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
