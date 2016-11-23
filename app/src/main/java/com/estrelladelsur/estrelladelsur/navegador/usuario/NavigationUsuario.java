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
import com.estrelladelsur.estrelladelsur.database.administrador.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.database.usuario.ControladorUsuarioGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.Mes;
import com.estrelladelsur.estrelladelsur.entidad.Modulo;
import com.estrelladelsur.estrelladelsur.entidad.SubModulo;
import com.estrelladelsur.estrelladelsur.entidad.Usuario;
import com.estrelladelsur.estrelladelsur.institucion.usuario.ArticuloUsuario;
import com.estrelladelsur.estrelladelsur.institucion.usuario.ComisionUsuario;
import com.estrelladelsur.estrelladelsur.institucion.usuario.DireccionUsuario;
import com.estrelladelsur.estrelladelsur.liga.usuario.tabs_user.TabsAdefulUsuario;
import com.estrelladelsur.estrelladelsur.login.Login;
import com.estrelladelsur.estrelladelsur.miequipo.usuario.tabs_user.JugadorUsuario;
import com.estrelladelsur.estrelladelsur.miequipo.usuario.tabs_user.TabsEntrenamientoUsuario;
import com.estrelladelsur.estrelladelsur.miequipo.usuario.tabs_user.TabsFixtureUsuario;
import com.estrelladelsur.estrelladelsur.miequipo.usuario.tabs_user.TabsSancionUsuario;
import com.estrelladelsur.estrelladelsur.permiso.TabsPermiso;
import com.estrelladelsur.estrelladelsur.permiso.TabsUsuario;
import com.estrelladelsur.estrelladelsur.social.administrador.tabs.TabsFoto;
import com.estrelladelsur.estrelladelsur.social.administrador.tabs.TabsNoticia;
import com.estrelladelsur.estrelladelsur.social.administrador.tabs.TabsPublicidad;
import com.estrelladelsur.estrelladelsur.social.usuario.FotoUsuario;
import com.estrelladelsur.estrelladelsur.social.usuario.NoticiaUsuario;
import com.estrelladelsur.estrelladelsur.social.usuario.NotificacionUsuario;
import com.estrelladelsur.estrelladelsur.social.usuario.PublicidadUsuario;

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
    private ControladorGeneral controladorGeneral;
    private ControladorUsuarioGeneral controladorUsuarioGeneral;
    private Typeface titulos;
    private Typeface adeful;
    private AuxiliarGeneral auxiliarGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        controladorGeneral = new ControladorGeneral(this);
        controladorUsuarioGeneral = new ControladorUsuarioGeneral(this);
        auxiliarGeneral = new AuxiliarGeneral(NavigationUsuario.this);

        titulos = auxiliarGeneral.tituloFont(NavigationUsuario.this);
        adeful = auxiliarGeneral.ligaFont(NavigationUsuario.this);
        init();
        drawerLayout.openDrawer(GravityCompat.START);
        inicializarDatosGenerales();
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
                            Intent jugadores = new Intent(NavigationUsuario.this, JugadorUsuario.class);
                            startActivity(jugadores);

//                            Intent resultado = new Intent(NavigationUsuario.this, ActivityResultadoAdeful.class);
//                            startActivity(resultado);
                            break;
                        case 2:
                            Intent entrenamiento = new Intent(NavigationUsuario.this, TabsEntrenamientoUsuario.class);
                            startActivity(entrenamiento);
//                            tituloClickFragment = mi_equipoChild.get(childPosition)
//                                    .toString();
                            break;
                        case 3:
                            Intent sanciones = new Intent(NavigationUsuario.this, TabsSancionUsuario.class);
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
                            Intent notificacion = new Intent(NavigationUsuario.this, NotificacionUsuario.class);
                            startActivity(notificacion);
//                            tituloClickFragment = ligaChild.get(childPosition)
//                                    .toString();
                            break;
                        case 1:
                            Intent noticia = new Intent(NavigationUsuario.this, NoticiaUsuario.class);
                            startActivity(noticia);
//                            tituloClickFragment = ligaChild.get(childPosition)
//                                    .toString();
                            break;
                        case 2:
                            Intent foto = new Intent(NavigationUsuario.this, FotoUsuario.class);
                            startActivity(foto);
                            //      tituloClickFragment = ligaChild.get(childPosition)
                            //            .toString();
                            break;

                        case 3:
                            Intent publicidad = new Intent(NavigationUsuario.this, PublicidadUsuario.class);
                            startActivity(publicidad);
//                            tituloClickFragment = socialChild.get(childPosition)
//                                    .toString();
                            break;
                    }
                }


//                else if (groupPosition == 4) {
//                    switch (childPosition) {
//                        case 0:
//                            Intent usuario = new Intent(NavigationUsuario.this, TabsUsuario.class);
//                            startActivity(usuario);
////                            tituloClickFragment = permisoChild.get(childPosition)
////                                    .toString();
//                            break;
//                        case 1:
//                            Intent permiso = new Intent(NavigationUsuario.this, TabsPermiso.class);
//                            startActivity(permiso);
////                            tituloClickFragment = permisoChild.get(childPosition)
////                                    .toString();
//                            break;
//                    }
//                }
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
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();

        List<Modulo> modulos = new ArrayList<>();
        List<SubModulo> submodulos = new ArrayList<>();
        modulos = controladorUsuarioGeneral.selectListaModuloUsuario();
        submodulos = controladorUsuarioGeneral.selectListaSubModulo();
        if(modulos != null ) {
            for (int i = 0; i < modulos.size()-1; i++) {
             listDataHeader.add(modulos.get(i).getMODULO());
            }
        }

        // Adding child data
        institucionalChild = new ArrayList<>();
        mi_equipoChild = new ArrayList<>();
        ligaChild = new ArrayList<>();
        socialChild = new ArrayList<>();
        if(submodulos != null) {
            for (int i = 0; i < submodulos.size()-2; i++) {

                switch (submodulos.get(i).getID_MODULO()){

                    case 1:
                        institucionalChild.add(submodulos.get(i).getSUBMODULO());
                        break;

                    case 2:
                        if(submodulos.get(i).getID_SUBMODULO() != 5)
                        mi_equipoChild.add(submodulos.get(i).getSUBMODULO());
                        break;

                    case 3:
                        ligaChild.add(submodulos.get(i).getSUBMODULO());
                        break;

                    case 4:
                        socialChild.add(submodulos.get(i).getSUBMODULO());
                        break;
                }
            }
        }

        listDataChild.put(listDataHeader.get(0), institucionalChild);
        listDataChild.put(listDataHeader.get(1), mi_equipoChild);
        listDataChild.put(listDataHeader.get(2), ligaChild);
        listDataChild.put(listDataHeader.get(3), socialChild);
    }

     public void inicializarDatosGenerales() {
        iniciarFecha();
        iniciarAnio();
        iniciarMes();
    }

    public void iniciarFecha() {
        ArrayList<Fecha> fechaArray = new ArrayList<>();
        fechaArray = controladorGeneral.selectListaFecha();
        if (fechaArray != null) {
            if (fechaArray.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.fechaArray).length; i++) {
                    Fecha fecha = new Fecha(i, getResources().getStringArray(
                            R.array.fechaArray)[i]);
                    controladorGeneral.insertFecha(fecha);
                }
            }
        } else {
            auxiliarGeneral.errorDataBase(NavigationUsuario.this);
        }
    }

    public void iniciarAnio() {
        ArrayList<Anio> anioArray = new ArrayList<>();
        anioArray = controladorGeneral.selectListaAnio();
        if (anioArray != null) {
            if (anioArray.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.anioArray).length; i++) {
                    Anio anio = new Anio(i,
                            getResources().getStringArray(R.array.anioArray)[i]);
                    controladorGeneral.insertAnio(anio);
                }
            }

        } else {
            auxiliarGeneral.errorDataBase(NavigationUsuario.this);
        }
    }

    public void iniciarMes() {
        ArrayList<Mes> mesArray = new ArrayList<>();
        mesArray = controladorGeneral.selectListaMes();
        if (mesArray != null) {
            if (mesArray.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.mesArray).length; i++) {
                    Mes mes = new Mes(i,
                            getResources().getStringArray(R.array.mesArray)[i]);
                    controladorGeneral.insertMes(mes);
                }
            }
        } else {
            auxiliarGeneral.errorDataBase(NavigationUsuario.this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        //menu.getItem(0).setVisible(false);//adm
        menu.getItem(1).setVisible(false);// user
       // menu.getItem(2).setVisible(false);// cerrar
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        if (id == R.id.action_administrador) {
            auxiliarGeneral.goToAdm(NavigationUsuario.this);
            return true;
        }
        if (id == R.id.action_cerrar) {
            auxiliarGeneral.close(NavigationUsuario.this);
        }
        return super.onOptionsItemSelected(item);
    }

}