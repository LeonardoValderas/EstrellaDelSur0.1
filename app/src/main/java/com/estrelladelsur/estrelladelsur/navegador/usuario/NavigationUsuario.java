package com.estrelladelsur.estrelladelsur.navegador.usuario;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.ScrimInsetsFrameLayout;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.Mes;
import com.estrelladelsur.estrelladelsur.entidad.Modulo;
import com.estrelladelsur.estrelladelsur.entidad.SubModulo;
import com.estrelladelsur.estrelladelsur.entidad.Usuario;
import com.estrelladelsur.estrelladelsur.institucion.usuario.ArticuloUsuario;
import com.estrelladelsur.estrelladelsur.institucion.usuario.ComisionUsuario;
import com.estrelladelsur.estrelladelsur.institucion.usuario.DireccionUsuario;
import com.estrelladelsur.estrelladelsur.liga.usuario.TabsAdefulUsuario;
import com.estrelladelsur.estrelladelsur.login.Login;
import com.estrelladelsur.estrelladelsur.miequipo.adeful.TabsEntrenamiento;
import com.estrelladelsur.estrelladelsur.miequipo.adeful.TabsJugador;
import com.estrelladelsur.estrelladelsur.miequipo.adeful.TabsSancion;
import com.estrelladelsur.estrelladelsur.miequipo.usuario.TabsFixtureUsuario;
import com.estrelladelsur.estrelladelsur.permiso.TabsPermiso;
import com.estrelladelsur.estrelladelsur.permiso.TabsUsuario;
import com.estrelladelsur.estrelladelsur.social.TabsFoto;
import com.estrelladelsur.estrelladelsur.social.TabsNoticia;
import com.estrelladelsur.estrelladelsur.social.TabsNotificacion;
import com.estrelladelsur.estrelladelsur.social.TabsPublicidad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NavigationUsuario extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ScrimInsetsFrameLayout sifl;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private String tituloClickFragment;
    private ExpandableAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private List<String> mi_equipoChild;
    private List<String> ligaChild;
    private List<String> socialChild;
    private List<String> permisoChild;
    private HashMap<String, List<String>> listDataChild;
    private List<String> institucionalChild;
    private TextView txtAbSubTitulo, txtAbTitulo, textViewLiga;
    private ControladorAdeful controladorAdeful;
    private Typeface titulos;
    private Typeface adeful;
    private AuxiliarGeneral auxiliarGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        controladorAdeful = new ControladorAdeful(this);
        auxiliarGeneral = new AuxiliarGeneral(NavigationUsuario.this);

        titulos = auxiliarGeneral.tituloFont(NavigationUsuario.this);
        adeful = auxiliarGeneral.ligaFont(NavigationUsuario.this);
        init();
        drawerLayout.openDrawer(GravityCompat.START);
        inicializarDatosGenerales();
        insertUsuarioAdm();

    }


    public void init() {

        // Referencia al ScrimInsetsFrameLayout
        sifl = (ScrimInsetsFrameLayout) findViewById(R.id.scrimInsetsFrameLayout);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        txtAbTitulo = (TextView) toolbar.findViewById(R.id.txtAbTitulo);
        txtAbTitulo.setTypeface(titulos);

//        txtAbSubTitulo = (TextView) findViewById(R.id.txtAbSubTitulo);
//        txtAbSubTitulo.setTypeface(titulos);

        textViewLiga = (TextView) findViewById(R.id.textViewLiga);
        textViewLiga.setTypeface(adeful, Typeface.BOLD);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        // preparing list data
        prepareListData();

        listAdapter = new ExpandableAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Fragment fragment = null;
                if (groupPosition == 0) {
                    switch (childPosition) {
                        case 0:
                            Intent estrella = new Intent(NavigationUsuario.this, ArticuloUsuario.class);
                            startActivity(estrella);
//                            tituloClickFragment = institucionalChild.get(
//                                    childPosition).toString();
                            break;
                        case 1:
                            Intent comision = new Intent(NavigationUsuario.this, ComisionUsuario.class);
                            startActivity(comision);
//                            tituloClickFragment = institucionalChild.get(
//                                    childPosition).toString();
                            break;

                        case 2:
                            Intent direccion = new Intent(NavigationUsuario.this, DireccionUsuario.class);
                            startActivity(direccion);
//                            tituloClickFragment = institucionalChild.get(
//                                    childPosition).toString();
                            break;
                    }
                } else if (groupPosition == 1) {
                    switch (childPosition) {
                        case 0:
                            Intent fixture = new Intent(NavigationUsuario.this, TabsFixtureUsuario.class);
                            startActivity(fixture);
//                            tituloClickFragment = mi_equipoChild.get(childPosition)
//                                    .toString();
                            break;
                        case 1:
                            Intent jugadores = new Intent(NavigationUsuario.this, TabsJugador.class);
                            startActivity(jugadores);

//                            Intent resultado = new Intent(NavigationUsuario.this, ActivityResultado.class);
//                            startActivity(resultado);
                            break;
                        case 2:
                            Intent entrenamiento = new Intent(NavigationUsuario.this, TabsEntrenamiento.class);
                            startActivity(entrenamiento);
//                            tituloClickFragment = mi_equipoChild.get(childPosition)
//                                    .toString();
                            break;
                        case 3:
                            Intent sanciones = new Intent(NavigationUsuario.this, TabsSancion.class);
                            startActivity(sanciones);
                            break;
//                        case 4:
//                            Intent sanciones = new Intent(NavigationUsuario.this, TabsSancion.class);
//                            startActivity(sanciones);
////                            tituloClickFragment = mi_equipoChild.get(childPosition)
////                                    .toString();
//                            break;
                    }

                } else if (groupPosition == 2) {
                    switch (childPosition) {
                        case 0:
                            Intent liga = new Intent(NavigationUsuario.this, TabsAdefulUsuario.class);
                            startActivity(liga);
//                            tituloClickFragment = ligaChild.get(childPosition)
//                                    .toString();
                            break;
                    }
                } else if (groupPosition == 3) {
                    switch (childPosition) {
                        case 0:
                            Intent notificacion = new Intent(NavigationUsuario.this, TabsNotificacion.class);
                            startActivity(notificacion);
//                            tituloClickFragment = ligaChild.get(childPosition)
//                                    .toString();
                            break;
                        case 1:
                            Intent noticia = new Intent(NavigationUsuario.this, TabsNoticia.class);
                            startActivity(noticia);
//                            tituloClickFragment = ligaChild.get(childPosition)
//                                    .toString();
                            break;
                        case 2:
                            Intent foto = new Intent(NavigationUsuario.this, TabsFoto.class);
                            startActivity(foto);
                            //      tituloClickFragment = ligaChild.get(childPosition)
                            //            .toString();
                            break;

                        case 3:
                            Intent publicidad = new Intent(NavigationUsuario.this, TabsPublicidad.class);
                            startActivity(publicidad);
//                            tituloClickFragment = socialChild.get(childPosition)
//                                    .toString();
                            break;
                    }
                } else if (groupPosition == 4) {
                    switch (childPosition) {
                        case 0:
                            Intent usuario = new Intent(NavigationUsuario.this, TabsUsuario.class);
                            startActivity(usuario);
//                            tituloClickFragment = permisoChild.get(childPosition)
//                                    .toString();
                            break;
                        case 1:
                            Intent permiso = new Intent(NavigationUsuario.this, TabsPermiso.class);
                            startActivity(permiso);
//                            tituloClickFragment = permisoChild.get(childPosition)
//                                    .toString();
                            break;
                    }
                }
                expListView.setItemChecked(childPosition, true);
                drawerLayout.openDrawer(sifl);

                return false;
            }
        });


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(
                R.color.color_primary_dark));

        drawerToggle = new ActionBarDrawerToggle(NavigationUsuario.this, drawerLayout, toolbar,
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
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        for (int i = 0; i < getResources().getStringArray(R.array.moduloArrayUsuario).length; i++) {

            listDataHeader.add(getResources().getStringArray(R.array.moduloArrayUsuario)[i]);

        }

        // Adding child data
//            listDataHeader.add("INSTITUCION");
//            listDataHeader.add("MI EQUIPO");
//            listDataHeader.add("LIGA");
//            listDataHeader.add("SOCIAL");

        // Adding child data
        institucionalChild = new ArrayList<String>();

        for (int i = 0; i < getResources().getStringArray(
                R.array.NavigationInstitucion).length; i++) {

            institucionalChild.add(getResources().getStringArray(
                    R.array.NavigationInstitucion)[i]);
        }

        mi_equipoChild = new ArrayList<String>();

        for (int i = 0; i < getResources().getStringArray(
                R.array.NavigationCargaUsuario).length; i++) {

            mi_equipoChild.add(getResources().getStringArray(
                    R.array.NavigationCargaUsuario)[i]);
        }

        ligaChild = new ArrayList<String>();
        for (int i = 0; i < getResources().getStringArray(
                R.array.NavigationLigaAdeful).length; i++) {

            ligaChild.add(getResources().getStringArray(
                    R.array.NavigationLigaAdeful)[i]);
        }

        socialChild = new ArrayList<String>();
        for (int i = 0; i < getResources().getStringArray(
                R.array.NavigationSocial).length; i++) {

            socialChild.add(getResources().getStringArray(R.array.NavigationSocial)[i]);
        }
        permisoChild = new ArrayList<String>();

//        for (int i = 0; i < getResources().getStringArray(
//                R.array.NavigationPermiso).length; i++) {
//
//            permisoChild.add(getResources().getStringArray(R.array.NavigationPermiso)[i]);
//        }

        listDataChild.put(listDataHeader.get(0), institucionalChild);
        listDataChild.put(listDataHeader.get(1), mi_equipoChild);
        listDataChild.put(listDataHeader.get(2), ligaChild);
        listDataChild.put(listDataHeader.get(3), socialChild);
      //  listDataChild.put(listDataHeader.get(4), permisoChild);
    }

    //	/**
//	 * metodo que pasa icono y string al adapter Recycler 15/08/2015
//	 *
//	 */
    public static List<InformationRecycler> getData(Context c) {

        List<InformationRecycler> dato = new ArrayList<>();

        // ver si voy a usar diferentes iconos
        int[] icons = {R.drawable.ic_pelota_futbol,
                R.drawable.ic_pelota_futbol, R.drawable.ic_pelota_futbol};
        String[] stringArray = c.getResources().getStringArray(
                R.array.NavigationCarga);

        for (int i = 0; i < stringArray.length; i++) {

            InformationRecycler current = new InformationRecycler();
            // current.iconId = icons[i];
            current.title = stringArray[i];
            dato.add(current);
        }
        return dato;

    }

    public void inicializarDatosGenerales() {
        iniciarModulos();
        iniciarFecha();
        iniciarAnio();
        iniciarMes();
    }

    public void iniciarFecha() {
        ArrayList<Fecha> fechaArray = new ArrayList<Fecha>();
        fechaArray = controladorAdeful.selectListaFecha();
        if (fechaArray != null) {
            if (fechaArray.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.fechaArray).length; i++) {
                    Fecha fecha = new Fecha(i, getResources().getStringArray(
                            R.array.fechaArray)[i]);
                    controladorAdeful.insertFecha(fecha);
                }
            }
        } else {
            auxiliarGeneral.errorDataBase(NavigationUsuario.this);
        }
    }

    public void iniciarAnio() {
        ArrayList<Anio> anioArray = new ArrayList<Anio>();
        anioArray = controladorAdeful.selectListaAnio();
        if (anioArray != null) {
            if (anioArray.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.anioArray).length; i++) {
                    Anio anio = new Anio(i,
                            getResources().getStringArray(R.array.anioArray)[i]);
                    controladorAdeful.insertAnio(anio);
                }
            }

        } else {
            auxiliarGeneral.errorDataBase(NavigationUsuario.this);
        }
    }

    public void iniciarMes() {
        ArrayList<Mes> mesArray = new ArrayList<Mes>();
        mesArray = controladorAdeful.selectListaMes();
        if (mesArray != null) {
            if (mesArray.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.mesArray).length; i++) {
                    Mes mes = new Mes(i,
                            getResources().getStringArray(R.array.mesArray)[i]);
                    controladorAdeful.insertMes(mes);
                }
            }
        } else {
            auxiliarGeneral.errorDataBase(NavigationUsuario.this);
        }
    }
public void insertUsuarioAdm(){

    Usuario u = new Usuario(0,"ADM","123",true,"","","","");
    controladorAdeful.insertUsuarioAdeful(u);
    }
    public void iniciarModulos() {

        ArrayList<Modulo> arrayModulo = new ArrayList<Modulo>();
        ArrayList<SubModulo> arraySubModulo = new ArrayList<SubModulo>();
        arrayModulo = controladorAdeful.selectListaModuloAdeful();
        arraySubModulo = controladorAdeful.selectListaSubModuloAdeful();

        if (arrayModulo != null) {
            if (arrayModulo.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.moduloArray).length; i++) {
                    Modulo modulo = new Modulo(0, getResources().getStringArray(R.array.moduloArray)[i]);
                    controladorAdeful.insertModuloAdeful(modulo);
                }
            }
        } else {
            auxiliarGeneral.errorDataBase(NavigationUsuario.this);
        }
        if (arraySubModulo != null) {
            if (arraySubModulo.isEmpty()) {

                for (int i = 0; i < getResources().getStringArray(R.array.subModuloArray).length; i++) {
                    int key = i + 1;
                    if (key >= 1 && key <= 3) {
                        SubModulo submodulo = new SubModulo(0, getResources().getStringArray(R.array.subModuloArray)[i], 1);
                        controladorAdeful.insertSubModuloAdeful(submodulo);
                    }
                    if (key >= 4 && key <= 8) {
                        SubModulo submodulo = new SubModulo(0, getResources().getStringArray(R.array.subModuloArray)[i], 2);
                        controladorAdeful.insertSubModuloAdeful(submodulo);
                    }
                    if (key == 9) {
                        SubModulo submodulo = new SubModulo(0, getResources().getStringArray(R.array.subModuloArray)[i], 3);
                        controladorAdeful.insertSubModuloAdeful(submodulo);
                    }
                    if (key >= 10 && key <= 13) {
                        SubModulo submodulo = new SubModulo(0, getResources().getStringArray(R.array.subModuloArray)[i], 4);
                        controladorAdeful.insertSubModuloAdeful(submodulo);
                    }
                    if (key >= 14 && key <= 15) {
                        SubModulo submodulo = new SubModulo(0, getResources().getStringArray(R.array.subModuloArray)[i], 5);
                        controladorAdeful.insertSubModuloAdeful(submodulo);
                    }

                }
            }
        } else {
            auxiliarGeneral.errorDataBase(NavigationUsuario.this);
        }
    }


     /*   public void loadSpinner(){

            // Fecha ver donde implementar
            for (int i = 0; i < getResources().getStringArray(R.array.fechaArray).length; i++) {

                Fecha fecha = new Fecha(i, getResources().getStringArray(
                        R.array.fechaArray)[i]);
                controladorAdeful.abrirBaseDeDatos();
                controladorAdeful.insertFecha(fecha);
                controladorAdeful.cerrarBaseDeDatos();
                // BL.getBl().insertarFecha(fecha);
            }
            // Anio ver donde implementar
            for (int i = 0; i < getResources().getStringArray(R.array.anioArray).length; i++) {

                Anio anio = new Anio(i,
                        getResources().getStringArray(R.array.anioArray)[i]);

                controladorAdeful.abrirBaseDeDatos();
                controladorAdeful.insertAnio(anio);
                controladorAdeful.cerrarBaseDeDatos();
                // BL.getBl().insertarAnio(anio);
            }

            for (int i = 0; i < getResources().getStringArray(R.array.mesArray).length; i++) {

                Mes mes = new Mes(i,
                        getResources().getStringArray(R.array.mesArray)[i]);

                controladorAdeful.abrirBaseDeDatos();
                controladorAdeful.insertMes(mes);
                controladorAdeful.cerrarBaseDeDatos();

            }
        }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_administrador) {
        Intent login = new Intent(NavigationUsuario.this, Login.class);
        startActivity(login);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}