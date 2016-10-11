package com.estrelladelsur.estrelladelsur.navegador.administrador;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.auxiliar.ScrimInsetsFrameLayout;
import com.estrelladelsur.estrelladelsur.database.adeful.ControladorAdeful;
import com.estrelladelsur.estrelladelsur.database.general.ControladorGeneral;
import com.estrelladelsur.estrelladelsur.entidad.Anio;
import com.estrelladelsur.estrelladelsur.entidad.Fecha;
import com.estrelladelsur.estrelladelsur.entidad.Mes;
import com.estrelladelsur.estrelladelsur.entidad.Modulo;
import com.estrelladelsur.estrelladelsur.entidad.Permiso;
import com.estrelladelsur.estrelladelsur.entidad.SubModulo;
import com.estrelladelsur.estrelladelsur.institucion.tabs_adm.TabsArticulo;
import com.estrelladelsur.estrelladelsur.institucion.tabs_adm.TabsComision;
import com.estrelladelsur.estrelladelsur.institucion.tabs_adm.TabsDireccion;
import com.estrelladelsur.estrelladelsur.liga.tabs_adm.TabsAdeful;
import com.estrelladelsur.estrelladelsur.liga.tabs_adm.TabsLifuba;
import com.estrelladelsur.estrelladelsur.miequipo.tabs_general.TabsEntrenamiento;
import com.estrelladelsur.estrelladelsur.miequipo.tabs_adm.TabsFixture;
import com.estrelladelsur.estrelladelsur.miequipo.tabs_general.TabsJugador;
import com.estrelladelsur.estrelladelsur.miequipo.tabs_adm.TabsResultado;
import com.estrelladelsur.estrelladelsur.miequipo.tabs_general.TabsSancion;
import com.estrelladelsur.estrelladelsur.navegador.usuario.NavigationUsuario;
import com.estrelladelsur.estrelladelsur.permiso.TabsPermiso;
import com.estrelladelsur.estrelladelsur.permiso.TabsUsuario;
import com.estrelladelsur.estrelladelsur.social.TabsFoto;
import com.estrelladelsur.estrelladelsur.social.TabsNoticia;
import com.estrelladelsur.estrelladelsur.social.TabsNotificacion;
import com.estrelladelsur.estrelladelsur.social.TabsPublicidad;

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
    private TextView txtAbSubTitulo, txtAbTitulo, textViewLiga;
    private ControladorGeneral controladorGeneral;
    private ControladorAdeful controladorAdeful;
    private Typeface titulos;
    private Typeface adeful;
    private AuxiliarGeneral auxiliarGeneral;
    private String usuarioAdministrador = null;
    private int idUsuarioAdministrador;
    private String clickGrup = null;
    String clickChild = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        controladorGeneral = new ControladorGeneral(this);
        controladorAdeful = new ControladorAdeful(this);
        auxiliarGeneral = new AuxiliarGeneral(Navigation.this);
        titulos = auxiliarGeneral.tituloFont(Navigation.this);
        adeful = auxiliarGeneral.ligaFont(Navigation.this);

        //  new TaskNavigation().execute();

        //usuario adminsitrador
        usuarioAdminitrador();
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
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                clickGrup = parent.getExpandableListAdapter().getGroup(groupPosition).toString();
                clickChild = parent.getExpandableListAdapter().getChild(groupPosition, childPosition).toString();

//                if (groupPosition == 0) {
                if (clickGrup.equals("INSTITUCION")) {
                    //switch (childPosition) {
                    switch (clickChild) {
                        case "Articulos":
                            Intent estrella = new Intent(Navigation.this, TabsArticulo.class);
                            startActivity(estrella);
//                            tituloClickFragment = institucionalChild.get(
//                                    childPosition).toString();
                            break;
                        case "Comisión Directiva":
                            Intent comision = new Intent(Navigation.this, TabsComision.class);
                            startActivity(comision);
//                            tituloClickFragment = institucionalChild.get(
//                                    childPosition).toString();
                            break;

                        case "Dirección Técnica":
                            Intent direccion = new Intent(Navigation.this, TabsDireccion.class);
                            startActivity(direccion);
//                            tituloClickFragment = institucionalChild.get(
//                                    childPosition).toString();
                            break;
                    }
                    //  }
                    //} else if (groupPosition == 1) {
                } else if (clickGrup.equals("MI EQUIPO")) {
                    switch (clickChild) {
                        case "Fixture":
                            Intent fixture = new Intent(Navigation.this, TabsFixture.class);
                            startActivity(fixture);
//                            tituloClickFragment = mi_equipoChild.get(childPosition)
//                                    .toString();
                            break;
                        case "Resultados":
                            Intent resultado = new Intent(Navigation.this, TabsResultado.class);
                            startActivity(resultado);
//                            tituloClickFragment = mi_equipoChild.get(childPosition)
//                                    .toString();
                            break;
                        case "Jugadores":
                            Intent jugadores = new Intent(Navigation.this, TabsJugador.class);
                            startActivity(jugadores);
//                            tituloClickFragment = mi_equipoChild.get(childPosition)
//                                    .toString();
                            break;
                        case "Entrenamientos":
                            Intent entrenamiento = new Intent(Navigation.this, TabsEntrenamiento.class);
                            startActivity(entrenamiento);
//                            tituloClickFragment = mi_equipoChild.get(childPosition)
//                                    .toString();
                            break;
                        case "Sancionados":
                            Intent sanciones = new Intent(Navigation.this, TabsSancion.class);
                            startActivity(sanciones);
//                            tituloClickFragment = mi_equipoChild.get(childPosition)
//                                    .toString();
                            break;
                    }

                } else if (clickGrup.equals("LIGA")) {
                    switch (clickChild) {
                        case "Adeful":
                            Intent ligaAdeful = new Intent(Navigation.this, TabsAdeful.class);
                            startActivity(ligaAdeful);
                            break;
                        case "Lifuba":
                            Intent ligaLifuba = new Intent(Navigation.this, TabsLifuba.class);
                            startActivity(ligaLifuba);
                            break;
                    }
                } else if (clickGrup.equals("SOCIAL")) {
                    switch (clickChild) {
                        case "Notificación":
                            Intent notificacion = new Intent(Navigation.this, TabsNotificacion.class);
                            startActivity(notificacion);
//                            tituloClickFragment = ligaChild.get(childPosition)
//                                    .toString();
                            break;
                        case "Noticias":
                            Intent noticia = new Intent(Navigation.this, TabsNoticia.class);
                            startActivity(noticia);
//                            tituloClickFragment = ligaChild.get(childPosition)
//                                    .toString();
                            break;
                        case "Fotos":
                            Intent foto = new Intent(Navigation.this, TabsFoto.class);
                            startActivity(foto);
                            //      tituloClickFragment = ligaChild.get(childPosition)
                            //            .toString();
                            break;

                        case "Publicidad":
                            Intent publicidad = new Intent(Navigation.this, TabsPublicidad.class);
                            startActivity(publicidad);
//                            tituloClickFragment = socialChild.get(childPosition)
//                                    .toString();
                            break;
                    }
                } else if (clickGrup.equals("PERMISO")) {
                    switch (clickChild) {
                        case "Usuarios":
                            Intent usuario = new Intent(Navigation.this, TabsUsuario.class);
                            startActivity(usuario);
//                            tituloClickFragment = permisoChild.get(childPosition)
//                                    .toString();
                            break;
                        case "Permisos":
                            Intent permiso = new Intent(Navigation.this, TabsPermiso.class);
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


    public class TaskNavigation extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Navigation.this);
            dialog.setMessage("¨Procesando...");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            usuarioAdminitrador();
            init();
            drawerLayout.openDrawer(GravityCompat.START);
            inicializarDatosGenerales();
            dialog.dismiss();
        }
    }

    private void prepareListData() {
        ArrayList<SubModulo> subModuloArray = new ArrayList<SubModulo>();
        ArrayList<Permiso> subModuloArrayPermiso = new ArrayList<Permiso>();
        String ADM = "ADM";
        listDataHeader = new ArrayList<String>();
        listDataHeaderAux = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        institucionalChild = new ArrayList<String>();
        mi_equipoChild = new ArrayList<String>();
        ligaChild = new ArrayList<String>();
        socialChild = new ArrayList<String>();
        permisoChild = new ArrayList<String>();

        usuarioAdministrador = getUsuarioPreferences();

        if (usuarioAdministrador != null) {

            if (usuarioAdministrador.equals(ADM)) {

                subModuloArray = controladorGeneral.selectListaModuloSubModuloFalse();
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
                    auxiliarGeneral.errorDataBase(Navigation.this);
                }
            }
        } else {
            auxiliarGeneral.errorDataBase(Navigation.this);
        }
        // Modulo unicos
        HashSet<String> uniqueModulo = new HashSet<String>(listDataHeaderAux);
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
        iniciarModulos();
        iniciarFecha();
        iniciarAnio();
        iniciarMes();
    }

    public void iniciarFecha() {
        ArrayList<Fecha> fechaArray = new ArrayList<>();
        ArrayList<Fecha> fechaArrayAdeful = new ArrayList<>();
        fechaArray = controladorGeneral.selectListaFecha();
        fechaArrayAdeful = controladorAdeful.selectListaFecha();
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
        } else {
            auxiliarGeneral.errorDataBase(Navigation.this);
        }
    }





    public void iniciarAnio() {
        ArrayList<Anio> anioArray = new ArrayList<>();
        ArrayList<Anio> anioArrayAdeful = new ArrayList<>();
        anioArray = controladorGeneral.selectListaAnio();
        anioArrayAdeful = controladorAdeful.selectListaAnio();
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

    public void iniciarModulos() {

        ArrayList<Modulo> arrayModulo = new ArrayList<>();
        ArrayList<SubModulo> arraySubModulo = new ArrayList<>();
        arrayModulo = controladorGeneral.selectListaModulo();
        arraySubModulo = controladorGeneral.selectListaSubModulo();

        if (arrayModulo != null) {
            if (arrayModulo.isEmpty()) {
                for (int i = 0; i < getResources().getStringArray(R.array.moduloArray).length; i++) {
                    Modulo modulo = new Modulo(0, getResources().getStringArray(R.array.moduloArray)[i]);
                    controladorGeneral.insertModulo(modulo);
                }
            }
        } else {
            auxiliarGeneral.errorDataBase(Navigation.this);
        }
        if (arraySubModulo != null) {
            if (arraySubModulo.isEmpty()) {

                for (int i = 0; i < getResources().getStringArray(R.array.subModuloArray).length; i++) {
                    int key = i + 1;
                    if (key >= 1 && key <= 3) {
                        SubModulo submodulo = new SubModulo(0, getResources().getStringArray(R.array.subModuloArray)[i], 1);
                        controladorGeneral.insertSubModulo(submodulo);
                    }
                    if (key >= 4 && key <= 8) {
                        SubModulo submodulo = new SubModulo(0, getResources().getStringArray(R.array.subModuloArray)[i], 2);
                        controladorGeneral.insertSubModulo(submodulo);
                    }
                    if (key == 9) {
                        SubModulo submodulo = new SubModulo(0, getResources().getStringArray(R.array.subModuloArray)[i], 3);
                        controladorGeneral.insertSubModulo(submodulo);
                    }
                    if (key >= 10 && key <= 13) {
                        SubModulo submodulo = new SubModulo(0, getResources().getStringArray(R.array.subModuloArray)[i], 4);
                        controladorGeneral.insertSubModulo(submodulo);
                    }
                    if (key >= 14 && key <= 15) {
                        SubModulo submodulo = new SubModulo(0, getResources().getStringArray(R.array.subModuloArray)[i], 5);
                        controladorGeneral.insertSubModulo(submodulo);
                    }
                }
            }
        } else {
            auxiliarGeneral.errorDataBase(Navigation.this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.action_administrador) {

            Intent i = new Intent(this, NavigationUsuario.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}