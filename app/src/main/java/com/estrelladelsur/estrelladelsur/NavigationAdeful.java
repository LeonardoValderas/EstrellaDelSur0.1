package com.estrelladelsur.estrelladelsur;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.estrelladelsur.estrelladelsur.institucion.TabsArticulo;
import com.estrelladelsur.estrelladelsur.institucion.TabsComision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NavigationAdeful extends AppCompatActivity{

private DrawerLayout drawerLayout;
private ScrimInsetsFrameLayout sifl;
private Toolbar toolbar;
private ActionBarDrawerToggle drawerToggle;
private String tituloClickFragment;
private ExpandableAdapter listAdapter;
private ExpandableListView expListView;
private List<String> listDataHeader;
private List<String> cargaGeneral;
private List<String> ligaDatos;
private List<String> social;
private HashMap<String, List<String>> listDataChild;
private List<String> institucionalList;
private TextView txtAbSubTitulo;
//private AlertsMenu alertMenu;
//private AlertPermisos alertPermisos;
//private ControladorAdeful controladorAdeful;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.navigation_drawer);
           // controladorAdeful = new ControladorAdeful(this);


           // loadSpinner();

            init();

            drawerLayout.openDrawer(GravityCompat.START);

        }



        public void init() {

            // Referencia al ScrimInsetsFrameLayout
            sifl = (ScrimInsetsFrameLayout) findViewById(R.id.scrimInsetsFrameLayout);

            // Toolbar
            toolbar = (Toolbar) findViewById(R.id.appbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            txtAbSubTitulo = (TextView) toolbar.findViewById(R.id.txtAbSubTitulo);

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
                                Intent estrella = new Intent(NavigationAdeful.this,TabsArticulo.class);
                                startActivity(estrella);
                                break;
                            case 1:
                                Intent comision  = new Intent(NavigationAdeful.this,TabsComision.class);
                                startActivity(comision);
                                tituloClickFragment = institucionalList.get(
                                        childPosition).toString();
                                break;

                            case 2:
                       //         Intent tecnica  = new Intent(NavigationDrawerAdeful.this,TabsTecnica.class);
                        //        startActivity(tecnica);
                                tituloClickFragment = institucionalList.get(
                                        childPosition).toString();
                                break;
                        }
                    } else if (groupPosition == 1) {
                        switch (childPosition) {
                            case 0:

                               // Intent fixture  = new Intent(NavigationDrawerAdeful.this,TabsFixture.class);
                               // startActivity(fixture);
                                tituloClickFragment = cargaGeneral.get(childPosition)
                                        .toString();
                                break;
                            case 1:
                              //  Intent resultado  = new Intent(NavigationDrawerAdeful.this,ActivityResultado.class);
                              //  startActivity(resultado);
                                tituloClickFragment = cargaGeneral.get(childPosition)
                                        .toString();
                                break;
                            case 2:
                               // Intent jugadores  = new Intent(NavigationDrawerAdeful.this,TabsJugadores.class);
                               // startActivity(jugadores);
                                tituloClickFragment = cargaGeneral.get(childPosition)
                                        .toString();
                                break;
                            case 3:
                             //   Intent entrenamiento  = new Intent(NavigationDrawerAdeful.this,TabsEntrenamiento.class);
                              //  startActivity(entrenamiento);
                                tituloClickFragment = cargaGeneral.get(childPosition)
                                        .toString();
                                break;
                        }

                    } else if (groupPosition == 2) {
                        switch (childPosition) {
                            case 0:
                            //    Intent equipo  = new Intent(NavigationDrawerAdeful.this,TabsAdeful.class);
                           //     startActivity(equipo);
                                tituloClickFragment = ligaDatos.get(childPosition)
                                        .toString();
                                break;
                        }
                    } else if (groupPosition == 3) {
                        switch (childPosition) {
                            case 0:
                              //  Intent notificacion  = new Intent(NavigationDrawerAdeful.this,TabsNotificacion.class);
                              //  startActivity(notificacion);
                                tituloClickFragment = social.get(childPosition)
                                        .toString();
                                break;
                            case 1:
                              //  Intent noticia  = new Intent(NavigationDrawerAdeful.this,TabsNoticia.class);
                              //  startActivity(noticia);
                                tituloClickFragment = social.get(childPosition)
                                        .toString();
                                break;
                            case 2:
                                //Intent foto  = new Intent(NavigationDrawerAdeful.this,TabsFoto.class);
                                //startActivity(foto);
                                tituloClickFragment = social.get(childPosition)
                                        .toString();
                                break;

                            case 3:
                               // Intent publicidad  = new Intent(NavigationDrawerAdeful.this,TabsPublicidad.class);
                              //  startActivity(publicidad);
                                tituloClickFragment = social.get(childPosition)
                                        .toString();
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

            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
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

            // Adding child data
            listDataHeader.add("INSTITUCION");
            listDataHeader.add("MI EQUIPO");
            listDataHeader.add("LIGA");
            listDataHeader.add("SOCIAL");

            // Adding child data
            institucionalList = new ArrayList<String>();

            for (int i = 0; i < getResources().getStringArray(
                    R.array.NavigationInstitucion).length; i++) {

                institucionalList.add(getResources().getStringArray(
                        R.array.NavigationInstitucion)[i]);
            }

            cargaGeneral = new ArrayList<String>();

            for (int i = 0; i < getResources().getStringArray(
                    R.array.NavigationCarga).length; i++) {

                cargaGeneral.add(getResources().getStringArray(
                        R.array.NavigationCarga)[i]);
            }

            ligaDatos = new ArrayList<String>();
            for (int i = 0; i < getResources().getStringArray(
                    R.array.NavigationLigaAdeful).length; i++) {

                ligaDatos.add(getResources().getStringArray(
                        R.array.NavigationLigaAdeful)[i]);
            }

            social = new ArrayList<String>();
            for (int i = 0; i < getResources().getStringArray(
                    R.array.NavigationSocial).length; i++) {

                social.add(getResources().getStringArray(R.array.NavigationSocial)[i]);
            }

            listDataChild.put(listDataHeader.get(0), institucionalList); // Header,
            // Child
            // data
            listDataChild.put(listDataHeader.get(1), cargaGeneral);
            listDataChild.put(listDataHeader.get(2), ligaDatos);
            listDataChild.put(listDataHeader.get(3), social);
        }

//	/**
//	 * metodo que pasa icono y string al adapter Recycler 15/08/2015
//	 *
//	 */
	public static List<InformationRecycler> getData(Context c) {

		List<InformationRecycler> dato = new ArrayList<>();

		// ver si voy a usar diferentes iconos
		int[] icons = { R.drawable.ic_pelota_futbol,
				R.drawable.ic_pelota_futbol, R.drawable.ic_pelota_futbol };
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
            if (id == R.id.action_permisos) {


               /* alertMenu = new AlertsMenu(this, "PERMISOS", "En esta opción puede agreagar o editar un Permiso","Ingrese nombre",null);
                alertMenu.btnAceptar.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        String[] comision = new String[] {"Presidente","Delegado"};
                        alertPermisos = new AlertPermisos(NavigationDrawerAdeful.this, "PERMISOS", getResources().getStringArray(R.array.NavigationInstitucion),getResources().getStringArray(R.array.NavigationCarga),comision);

                    }
                });*/


                return true;
            }

            return super.onOptionsItemSelected(item);
        }

}