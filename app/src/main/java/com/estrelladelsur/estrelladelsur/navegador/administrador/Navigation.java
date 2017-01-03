package com.estrelladelsur.estrelladelsur.navegador.administrador;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.ScrimInsetsFrameLayout;
import com.estrelladelsur.estrelladelsur.database.administrador.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.database.administrador.lifuba.ControladorLifuba;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.Mes;
import com.estrelladelsur.estrelladelsur.entidad.Permiso;
import com.estrelladelsur.estrelladelsur.entidad.SubModulo;
import com.estrelladelsur.estrelladelsur.institucion.administrador.tabs_adm.TabsArticulo;
import com.estrelladelsur.estrelladelsur.institucion.administrador.tabs_adm.TabsComision;
import com.estrelladelsur.estrelladelsur.institucion.administrador.tabs_adm.TabsDireccion;
import com.estrelladelsur.estrelladelsur.liga.administrador.tabs_adm.TabsAdeful;
import com.estrelladelsur.estrelladelsur.liga.administrador.tabs_adm.TabsLifuba;
import com.estrelladelsur.estrelladelsur.miequipo.administrador.tabs_general.TabsEntrenamiento;
import com.estrelladelsur.estrelladelsur.miequipo.administrador.tabs_adm.TabsFixture;
import com.estrelladelsur.estrelladelsur.miequipo.administrador.tabs_general.TabsJugador;
import com.estrelladelsur.estrelladelsur.miequipo.administrador.tabs_adm.TabsResultado;
import com.estrelladelsur.estrelladelsur.miequipo.administrador.tabs_adm.TabsSancion;
import com.estrelladelsur.estrelladelsur.permiso.TabsPermiso;
import com.estrelladelsur.estrelladelsur.permiso.TabsUsuario;
import com.estrelladelsur.estrelladelsur.social.administrador.tabs.TabsFoto;
import com.estrelladelsur.estrelladelsur.social.administrador.tabs.TabsNoticia;
import com.estrelladelsur.estrelladelsur.social.administrador.tabs.TabsNotificacion;
import com.estrelladelsur.estrelladelsur.social.administrador.tabs.TabsPublicidad;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Navigation extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ScrimInsetsFrameLayout sifl;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private ExpandableAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private List<String> listDataHeaderAux;
    private List<String> mi_equipoChild;
    private List<String> ligaChild;
    private List<String> socialChild;
    private List<String> permisoChild;
    private HashMap<String, List<String>> listDataChild;
    private List<String> institucionalChild;
    private TextView txtAbTitulo;
    private ControladorGeneral controladorGeneral;
    private ControladorAdeful controladorAdeful;
    private ControladorLifuba controladorLifuba;
    private Typeface titulos;
    private Typeface adeful;
    private AuxiliarGeneral auxiliarGeneral;
    private String usuarioAdministrador = null;
    private int idUsuarioAdministrador;
    private String clickGrup = null;
    private String clickChild = null;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;
    private int conteo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        initAll();
        if (!auxiliarGeneral.checkPermission(Navigation.this))
            auxiliarGeneral.showDialogPermission(Navigation.this, this);

    }
    public void initAll(){
        InterstitialAd();
        controladorGeneral = new ControladorGeneral(this);
        controladorAdeful = new ControladorAdeful(this);
        controladorLifuba = new ControladorLifuba(this);
        auxiliarGeneral = new AuxiliarGeneral(Navigation.this);
        titulos = auxiliarGeneral.tituloFont(Navigation.this);
        adeful = auxiliarGeneral.ligaFont(Navigation.this);
        usuarioAdminitrador();
        init();
        drawerLayout.openDrawer(GravityCompat.START);
        inicializarDatosGenerales();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_CODE) {
            return;
        }
        boolean isGranted = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
                break;
            }
        }

        if (!isGranted)
            auxiliarGeneral.showDialogPermission(Navigation.this, this);
    }

    public void init() {
        // Referencia al ScrimInsetsFrameLayout
        sifl = (ScrimInsetsFrameLayout) findViewById(R.id.scrimInsetsFrameLayout);
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        txtAbTitulo = (TextView) toolbar.findViewById(R.id.txtAbTitulo);
        txtAbTitulo.setTypeface(titulos);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        prepareListData();
        listAdapter = new ExpandableAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                clickGrup = parent.getExpandableListAdapter().getGroup(groupPosition).toString();
                clickChild = parent.getExpandableListAdapter().getChild(groupPosition, childPosition).toString();

                if (clickGrup.equals("INSTITUCION")) {
                    switch (clickChild) {
                        case "Articulos":
                            Intent estrella = new Intent(Navigation.this, TabsArticulo.class);
                            startActivity(estrella);
                            conteoClick();
                            break;
                        case "Comisión Directiva":
                            Intent comision = new Intent(Navigation.this, TabsComision.class);
                            startActivity(comision);
                            conteoClick();
                            break;

                        case "Dirección Técnica":
                            Intent direccion = new Intent(Navigation.this, TabsDireccion.class);
                            startActivity(direccion);
                            conteoClick();
                            break;
                    }
                } else if (clickGrup.equals("MI EQUIPO")) {
                    switch (clickChild) {
                        case "Fixture":
                            Intent fixture = new Intent(Navigation.this, TabsFixture.class);
                            startActivity(fixture);
                            conteoClick();
                            break;
                        case "Resultados":
                            Intent resultado = new Intent(Navigation.this, TabsResultado.class);
                            startActivity(resultado);
                            conteoClick();
                            break;
                        case "Jugadores":
                            Intent jugadores = new Intent(Navigation.this, TabsJugador.class);
                            startActivity(jugadores);
                            conteoClick();
                            break;
                        case "Entrenamientos":
                            Intent entrenamiento = new Intent(Navigation.this, TabsEntrenamiento.class);
                            startActivity(entrenamiento);
                            conteoClick();
                            break;
                        case "Sancionados":
                            Intent sanciones = new Intent(Navigation.this, TabsSancion.class);
                            startActivity(sanciones);
                            conteoClick();
                            break;
                    }

                } else if (clickGrup.equals("LIGA")) {
                    switch (clickChild) {
                        case "Adeful":
                            Intent ligaAdeful = new Intent(Navigation.this, TabsAdeful.class);
                            startActivity(ligaAdeful);
                            conteoClick();
                            break;
                        case "Lifuba":
                            Intent ligaLifuba = new Intent(Navigation.this, TabsLifuba.class);
                            startActivity(ligaLifuba);
                            conteoClick();
                            break;
                    }
                } else if (clickGrup.equals("SOCIAL")) {
                    switch (clickChild) {
                        case "Notificación":
                            Intent notificacion = new Intent(Navigation.this, TabsNotificacion.class);
                            startActivity(notificacion);
                            conteoClick();
                            break;
                        case "Noticias":
                            Intent noticia = new Intent(Navigation.this, TabsNoticia.class);
                            startActivity(noticia);
                            conteoClick();
                            break;
                        case "Fotos":
                            Intent foto = new Intent(Navigation.this, TabsFoto.class);
                            startActivity(foto);
                            conteoClick();
                            break;

                        case "Publicidad":
                            Intent publicidad = new Intent(Navigation.this, TabsPublicidad.class);
                            startActivity(publicidad);
                            conteoClick();
                            break;
                    }
                } else if (clickGrup.equals("PERMISO")) {
                    switch (clickChild) {
                        case "Usuarios":
                            Intent usuario = new Intent(Navigation.this, TabsUsuario.class);
                            startActivity(usuario);
                            conteoClick();
                            break;
                        case "Permisos":
                            Intent permiso = new Intent(Navigation.this, TabsPermiso.class);
                            startActivity(permiso);
                            conteoClick();
                            break;
                    }
                }
                expListView.setItemChecked(childPosition, true);
                drawerLayout.openDrawer(sifl);

                return false;
            }
        });

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousItem)
                    expListView.collapseGroup(previousItem);
                previousItem = groupPosition;
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(
                R.color.color_primary_dark));

        drawerToggle = new ActionBarDrawerToggle(Navigation.this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void usuarioAdminitrador() {
        usuarioAdministrador = Navigation.this.getIntent().getStringExtra("usuario");
        idUsuarioAdministrador = Navigation.this.getIntent().getIntExtra("id_usuario", 0);
        if (usuarioAdministrador != null) {
            SharedPreferences.Editor editor = getSharedPreferences("UsuarioLog", MODE_PRIVATE).edit();
            editor.putString("usuario", usuarioAdministrador);
            editor.putInt("id_usuario", idUsuarioAdministrador);
            editor.commit();
        }
    }

    public String getUsuarioPreferences() {
        SharedPreferences getUser = getSharedPreferences("UsuarioLog", MODE_PRIVATE);
        return getUser.getString("usuario", null);
    }

    public int getIdUsuarioPreferences() {
        SharedPreferences getUser = getSharedPreferences("UsuarioLog", MODE_PRIVATE);
        return getUser.getInt("id_usuario", 0);
    }

    public void InterstitialAd() {
        mInterstitialAd = new InterstitialAd(Navigation.this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_usuario));

        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("B52960D9E6A2A5833E82FEA8ACD4B80C")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public void conteoClick() {
        if (conteo == 6) {
            showInterstitial();
            conteo = 0;
        } else {
            conteo++;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void prepareListData() {
        ArrayList<SubModulo> subModuloArray = new ArrayList<>();
        ArrayList<Permiso> subModuloArrayPermiso = new ArrayList<>();
        String ADM = "ADM";
        listDataHeader = new ArrayList<>();
        listDataHeaderAux = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();
        institucionalChild = new ArrayList<>();
        mi_equipoChild = new ArrayList<>();
        ligaChild = new ArrayList<>();
        socialChild = new ArrayList<>();
        permisoChild = new ArrayList<>();

        usuarioAdministrador = getUsuarioPreferences();

        if (usuarioAdministrador != null) {

            if (usuarioAdministrador.equals(ADM)) {
                subModuloArray = controladorGeneral.selectListaModuloSubModuloADM();
                if (subModuloArray != null) {
                    loadModuloView(subModuloArray, null, true);
                } else {
                    auxiliarGeneral.errorDataBase(Navigation.this);
                }
            } else {

                int id = controladorGeneral.selectIdPermisoIdUser(getIdUsuarioPreferences());
                if (id != 0) {
                    subModuloArrayPermiso = controladorGeneral.selectListaPermisoId(id);
                    if (subModuloArrayPermiso != null)
                        loadModuloView(null, subModuloArrayPermiso, false);
                } else {
                    Toast.makeText(Navigation.this, "Usuario sin permisos asignados.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            auxiliarGeneral.errorDataBase(Navigation.this);
        }
        // Modulo unicos
        HashSet<String> uniqueModulo = new HashSet<>(listDataHeaderAux);
        for (String value : uniqueModulo) {
            listDataHeader.add(value);
        }
        Collections.sort(listDataHeader);

        for (int i = 0; i < listDataHeader.size(); i++) {
            switch (listDataHeader.get(i)) {

                case "INSTITUCION":
                    listDataChild.put(listDataHeader.get(i), institucionalChild);
                    break;
                case "MI EQUIPO":
                    listDataChild.put(listDataHeader.get(i), mi_equipoChild);
                    break;
                case "LIGA":
                    listDataChild.put(listDataHeader.get(i), ligaChild);
                    break;
                case "SOCIAL":
                    listDataChild.put(listDataHeader.get(i), socialChild);
                    break;
                case "PERMISO":
                    listDataChild.put(listDataHeader.get(i), permisoChild);
                    break;
            }
        }
    }

    public List<String> loadModuloView(ArrayList<SubModulo> subModuloArray, ArrayList<Permiso> subModuloArrayPermiso, boolean ADM) {
        if (ADM) {
            if (!subModuloArray.isEmpty()) {
                for (int i = 0; i < subModuloArray.size(); i++) {

                    switch (subModuloArray.get(i).getID_MODULO()) {

                        case 1:
                            institucionalChild.add(subModuloArray.get(i).getSUBMODULO());
                            break;
                        case 2:
                            mi_equipoChild.add(subModuloArray.get(i).getSUBMODULO());
                            break;
                        case 3:
                            ligaChild.add(subModuloArray.get(i).getSUBMODULO());
                            break;
                        case 4:
                            socialChild.add(subModuloArray.get(i).getSUBMODULO());
                            break;
                        case 5:
                            permisoChild.add(subModuloArray.get(i).getSUBMODULO());
                            break;
                    }
                    listDataHeaderAux.add(subModuloArray.get(i).getMODULO());
                }
            }
        } else {
            if (!subModuloArrayPermiso.isEmpty()) {
                for (int i = 0; i < subModuloArrayPermiso.size(); i++) {

                    switch (subModuloArrayPermiso.get(i).getID_MODULO()) {

                        case 1:
                            institucionalChild.add(subModuloArrayPermiso.get(i).getSUBMODULO());
                            break;
                        case 2:
                            mi_equipoChild.add(subModuloArrayPermiso.get(i).getSUBMODULO());
                            break;
                        case 3:
                            ligaChild.add(subModuloArrayPermiso.get(i).getSUBMODULO());
                            break;
                        case 4:
                            socialChild.add(subModuloArrayPermiso.get(i).getSUBMODULO());
                            break;
                        case 5:
                            permisoChild.add(subModuloArrayPermiso.get(i).getSUBMODULO());
                            break;
                    }
                    listDataHeaderAux.add(subModuloArrayPermiso.get(i).getMODULO());
                }
            }
        }
        return listDataHeaderAux;
    }

    public void inicializarDatosGenerales() {
        iniciarFecha();
        iniciarAnio();
        iniciarMes();
    }

    public void iniciarFecha() {
        ArrayList<Fecha> fechaArray = new ArrayList<>();
        ArrayList<Fecha> fechaArrayAdeful = new ArrayList<>();
        ArrayList<Fecha> fechaArrayLifuba = new ArrayList<>();
        fechaArray = controladorGeneral.selectListaFecha();
        fechaArrayAdeful = controladorAdeful.selectListaFecha();
        fechaArrayLifuba = controladorLifuba.selectListaFecha();
        if (fechaArray != null || fechaArrayAdeful != null) {
            if (fechaArray.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.fechaArray).length; i++) {
                    Fecha fecha = new Fecha(i, getResources().getStringArray(
                            R.array.fechaArray)[i]);
                    controladorGeneral.insertFecha(fecha);
                }
            }
            if (fechaArrayAdeful.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.fechaArray).length; i++) {
                    Fecha fecha = new Fecha(i, getResources().getStringArray(
                            R.array.fechaArray)[i]);
                    controladorAdeful.insertFecha(fecha);
                }
            }
            if (fechaArrayLifuba.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.fechaArray).length; i++) {
                    Fecha fecha = new Fecha(i, getResources().getStringArray(
                            R.array.fechaArray)[i]);
                    controladorLifuba.insertFecha(fecha);
                }
            }
        } else {
            auxiliarGeneral.errorDataBase(Navigation.this);
        }
    }

    public void iniciarAnio() {
        ArrayList<Anio> anioArray = new ArrayList<>();
        ArrayList<Anio> anioArrayAdeful = new ArrayList<>();
        ArrayList<Anio> anioArrayLifuba = new ArrayList<>();
        anioArray = controladorGeneral.selectListaAnio();
        anioArrayAdeful = controladorAdeful.selectListaAnio();
        anioArrayLifuba = controladorLifuba.selectListaAnio();
        if (anioArray != null || anioArrayAdeful != null) {
            if (anioArray.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.anioArray).length; i++) {
                    Anio anio = new Anio(i,
                            getResources().getStringArray(R.array.anioArray)[i]);
                    controladorGeneral.insertAnio(anio);
                }
            }
            if (anioArrayAdeful.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.anioArray).length; i++) {
                    Anio anio = new Anio(i,
                            getResources().getStringArray(R.array.anioArray)[i]);
                    controladorAdeful.insertAnio(anio);
                }
            }
            if (anioArrayLifuba.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.anioArray).length; i++) {
                    Anio anio = new Anio(i,
                            getResources().getStringArray(R.array.anioArray)[i]);
                    controladorLifuba.insertAnio(anio);
                }
            }
        } else {
            auxiliarGeneral.errorDataBase(Navigation.this);
        }
    }

    public void iniciarMes() {
        ArrayList<Mes> mesArray = new ArrayList<>();
        ArrayList<Mes> mesArrayAdeful = new ArrayList<>();
        mesArray = controladorGeneral.selectListaMes();
        mesArrayAdeful = controladorAdeful.selectListaMes();
        if (mesArray != null || mesArrayAdeful != null) {
            if (mesArray.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.mesArray).length; i++) {
                    Mes mes = new Mes(i,
                            getResources().getStringArray(R.array.mesArray)[i]);
                    controladorGeneral.insertMes(mes);
                }
            }
            if (mesArrayAdeful.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.mesArray).length; i++) {
                    Mes mes = new Mes(i,
                            getResources().getStringArray(R.array.mesArray)[i]);
                    controladorAdeful.insertMes(mes);
                }
            }
        } else {
            auxiliarGeneral.errorDataBase(Navigation.this);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.getItem(0).setVisible(false);//adm

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.action_usuario) {
            auxiliarGeneral.goToUser(Navigation.this);
            return true;
        }
        if (id == R.id.action_cerrar) {
            auxiliarGeneral.close(Navigation.this);
        }
        return super.onOptionsItemSelected(item);
    }
}