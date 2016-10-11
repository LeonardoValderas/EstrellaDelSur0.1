package com.estrelladelsur.estrelladelsur.liga.lifuba;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Cancha;
import com.estrelladelsur.estrelladelsur.liga.LocationAddress;
import com.estrelladelsur.estrelladelsur.liga.tabs_adm.TabsAdeful;
import com.estrelladelsur.estrelladelsur.miequipo.MyAsyncTaskListener;
import com.estrelladelsur.estrelladelsur.webservice.AsyncTaskGeneric;
import com.estrelladelsur.estrelladelsur.webservice.Request;
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

public class MapaCanchaLifuba extends AppCompatActivity implements OnMapReadyCallback, MyAsyncTaskListener {


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
    private String nombre = null, URL = null, usuario = null;
    private boolean insertar = true;
    private SupportMapFragment mapFragment;
    private Typeface titulos;
    private Typeface editTextFont;
    private Handler touchScreem;
    private AuxiliarGeneral auxiliarGeneral;
    private Request request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_cancha);


        auxiliarGeneral = new AuxiliarGeneral(MapaCanchaLifuba.this);
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTitulo = (TextView) toolbar.findViewById(R.id.txtToolBarTitulo);
        txtTitulo.setText("MAPA");
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
            id_extra = getIntent().getIntExtra("id", 0);
            longitud = getIntent().getStringExtra("longitud");
            latitud = getIntent().getStringExtra("latitud");
            longitudExtra = Double.valueOf(longitud);
            latitudExtra = Double.valueOf(latitud);
            locationAddress = getIntent().getStringExtra("direccion");
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
        usuario = auxiliarGeneral.getUsuarioPreferences(MapaCanchaLifuba.this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvAddress.setTypeface(editTextFont);
        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
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

    @Override
    public void onPostExecuteConcluded(boolean result, String mensaje) {
        if (result) {
            if (insertar) {
                inicializarControles(mensaje);
            } else {
                inicializarControles(mensaje);
            }
        } else {
            auxiliarGeneral.errorWebService(MapaCanchaLifuba.this, mensaje);
        }
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
        Toast.makeText(MapaCanchaLifuba.this,
                mensaje,
                Toast.LENGTH_SHORT).show();
    }

    public void cargarEntidad(int id) {
        URL = null;
        URL = auxiliarGeneral.getURLCANCHAADEFULALL();

        cancha = new Cancha(id, editTextNombre.getText().toString(),
                longitud, latitud, tvAddress.getText().toString(),
                usuario, auxiliarGeneral.getFechaOficial(), usuario, auxiliarGeneral.getFechaOficial());

        envioWebService();
    }

    public void envioWebService() {
        request = new Request();
        request.setMethod("POST");
        request.setParametrosDatos("nombre", cancha.getNOMBRE());
        request.setParametrosDatos("direccion", cancha.getDIRECCION());
        request.setParametrosDatos("latitud", cancha.getLATITUD());
        request.setParametrosDatos("longitud", cancha.getLONGITUD());

        //0 = insert // 1 = update //
        if (insertar) {
            request.setParametrosDatos("usuario_creador", cancha.getUSUARIO_CREADOR());
            request.setParametrosDatos("fecha_creacion", cancha.getFECHA_CREACION());
            URL = URL + auxiliarGeneral.getInsertPHP("Cancha");
        } else {
            request.setParametrosDatos("id_cancha", String.valueOf(cancha.getID_CANCHA()));
            request.setParametrosDatos("usuario_actualizacion", cancha.getUSUARIO_ACTUALIZACION());
            request.setParametrosDatos("fecha_actualizacion", cancha.getFECHA_ACTUALIZACION());
            URL = URL + auxiliarGeneral.getUpdatePHP("Cancha");
        }
        new AsyncTaskGeneric(MapaCanchaLifuba.this, this, URL, request, "Cancha", cancha, insertar, "a");
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
        menu.getItem(0).setVisible(false);// usuario
        menu.getItem(1).setVisible(false);// permiso
        menu.getItem(2).setVisible(false);// lifuba
        menu.getItem(3).setVisible(false);// adeful
        menu.getItem(4).setVisible(false);// puesto
        menu.getItem(5).setVisible(false);// id_extra
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
                    cargarEntidad(0);
                } else {
                    cargarEntidad(id_extra);
                }
            }
            return true;
        }

        if (id == android.R.id.home) {

            //NavUtils.navigateUpFromSameTask(this);

            //a.refreshs();
            //comm.refreshAdeful();
            volver();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void volver() {
        Intent i = new Intent(MapaCanchaLifuba.this, TabsAdeful.class);
        i.putExtra("restart", 1);
        startActivity(i);
    }

}
