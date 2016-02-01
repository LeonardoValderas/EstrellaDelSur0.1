package com.estrelladelsur.estrelladelsur.miequipo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;

public class TabsSancion extends AppCompatActivity {

	private Toolbar toolbar;
	private ActionBarDrawerToggle drawerToggle;
	private ViewPager viewPager;
	private TabLayout tabLayout;
	private int viewpagerid;
	final int PAGE_COUNT = 2;
	private FragmentTransaction mCurTransaction;
	private static final String TAG = "FragmentPagerAdapter";
	private static final boolean DEBUG = false;
	private TextView txtAbTitulo;
	private TextView txtAbSubTitulo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_general);

		// Toolbar
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		txtAbTitulo = (TextView) toolbar.findViewById(R.id.txtAbTitulo);
		txtAbTitulo.setVisibility(View.GONE);

		txtAbSubTitulo = (TextView) findViewById(R.id.txtAbSubTitulo);
		txtAbSubTitulo.setText("SANCION");


		if (savedInstanceState != null) {
			viewpagerid = savedInstanceState.getInt("viewpagerid", -1);
			viewPager = (ViewPager) findViewById(R.id.viewpager);
			viewPager.setOffscreenPageLimit(PAGE_COUNT - 1);
			if (viewpagerid != -1) {
				viewPager.setId(viewpagerid);
			} else {
				viewpagerid = viewPager.getId();
			}

			viewPager.setAdapter(new TabsFixtureAdapter(
					getSupportFragmentManager()));
		} else {
			viewPager = (ViewPager) findViewById(R.id.viewpager);
			viewPager.setOffscreenPageLimit(PAGE_COUNT - 1);
			viewPager = (ViewPager) findViewById(R.id.viewpager);

			viewPager.setAdapter(new TabsFixtureAdapter(
					getSupportFragmentManager()));
		}
		tabLayout = (TabLayout) findViewById(R.id.appbartabs);
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
		tabLayout.setTabMode(TabLayout.MODE_FIXED);
		tabLayout.setupWithViewPager(viewPager);

		init();

	}

	public void init() {

		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				invalidateOptionsMenu();
			}
			@Override
			public void onPageSelected(int position) {
			}
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});

	}

	public class TabsFixtureAdapter extends FragmentPagerAdapter {

		private FragmentManager fm;
		private String tabTitles[] = new String[] { "CREAR SANCION",
				"EDITAR SANCION" };

		public TabsFixtureAdapter(FragmentManager fm) {
			super(fm);
			this.fm = fm;
		}

		@Override
		public int getCount() {
			return PAGE_COUNT;
		}

		@Override
		public Fragment getItem(int position) {

			Fragment fragmentTab = fm.findFragmentByTag("android:switcher:"
					+ viewPager.getId() + ":" + getItemId(position));

			if (fragmentTab != null) {
				return fragmentTab;
			}

			switch (position) {
			case 0:
				fragmentTab = FragmentGenerarSancion.newInstance();
				break;
			case 1:
				fragmentTab = FragmentEditarFixture.newInstance();
				break;
			}
			return fragmentTab;
		}

		@Override
		public Object instantiateItem(View container, int position) {

			if (fm == null) {
				mCurTransaction = fm.beginTransaction();
			}

			// Do we already have this fragment?
			String name = makeFragmentName(container.getId(), position);
			Fragment fragment = fm.findFragmentByTag(name);
			if (fragment != null) {
				if (DEBUG)
					Log.v(TAG, "Attaching item #" + position + ": f="
							+ fragment);
				mCurTransaction.attach(fragment);
			} else {
				fragment = getItem(position);
				if (DEBUG)
					Log.v(TAG, "Adding item #" + position + ": f=" + fragment);
				mCurTransaction.add(container.getId(), fragment,
						makeFragmentName(container.getId(), position));
			}

			return fragment;
		}

		private String makeFragmentName(int viewId, int index) {
			return "android:switcher:" + viewId + ":" + index;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// Generate title based on item position
			return tabTitles[position];
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.menu_administrador_general, menu);
	// if (viewPager.getCurrentItem() == 0) {
	// // menu.getItem(0).setVisible(false);//usuario
	// // menu.getItem(1).setVisible(false);//permiso
	// // menu.getItem(2).setVisible(false);//lifuba
	// menu.getItem(3).setVisible(false);// adeful
	// menu.getItem(4).setVisible(false);// puesto
	// menu.getItem(5).setVisible(false);// posicion
	// menu.getItem(6).setVisible(false);// cargo
	// // menu.getItem(7).setVisible(false);//cerrar
	// //menu.getItem(8).setVisible(false);// guardar
	// menu.getItem(9).setVisible(false);// Subir
	// menu.getItem(10).setVisible(false); // eliminar
	// menu.getItem(11).setVisible(false); // consultar
	// } else if (viewPager.getCurrentItem() == 1) {
	// // menu.getItem(0).setVisible(false);//usuario
	// // menu.getItem(1).setVisible(false);//permiso
	// // menu.getItem(2).setVisible(false);//lifuba
	// menu.getItem(3).setVisible(false);// adeful
	// menu.getItem(4).setVisible(false);// puesto
	// menu.getItem(5).setVisible(false);// posicion
	// menu.getItem(6).setVisible(false);// cargo
	// // menu.getItem(7).setVisible(false);//cerrar
	// menu.getItem(8).setVisible(false);// guardar
	// menu.getItem(9).setVisible(false);// Subir
	// menu.getItem(10).setVisible(false); // eliminar
	// menu.getItem(11).setVisible(false); // consultar
	// }
	// return super.onCreateOptionsMenu(menu);
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	//
	// // if (drawerToggle.onOptionsItemSelected(item)) {
	// // return true;
	// // }
	//
	// int id = item.getItemId();
	// // noinspection SimplifiableIfStatement
	// if (id == R.id.action_usuario) {
	//
	// Intent usuario = new Intent(TabsFixture.this,
	// NavigationDrawerUsuario.class);
	// startActivity(usuario);
	//
	// return true;
	// }
	//
	// if (id == R.id.action_permisos) {
	// return true;
	// }
	//
	// if (id == R.id.action_guardar) {
	//
	//
	// // Toast.makeText(
	// // this,
	// // "Torneo Eliminado Correctamente",
	// // Toast.LENGTH_SHORT).show();
	//
	// return true;
	// }
	//
	// if (id == R.id.action_subir) {
	//
	// return true;
	// }
	//
	// if (id == R.id.action_eliminar) {
	//
	// return true;
	// }
	// if (id == R.id.action_adeful) {
	//
	// return true;
	// }
	//
	// if (id == R.id.action_lifuba) {
	//
	// return true;
	// }
	//
	// if (id == R.id.action_puesto) {
	//
	// return true;
	// }
	//
	// if (id == R.id.action_cargo) {
	//
	// return true;
	// }
	//
	// if (id == android.R.id.home) {
	//
	// NavUtils.navigateUpFromSameTask(this);
	//
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }

}
