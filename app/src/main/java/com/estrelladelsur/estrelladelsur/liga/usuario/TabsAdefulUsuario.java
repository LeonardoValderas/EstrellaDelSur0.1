package com.estrelladelsur.estrelladelsur.liga.usuario;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.estrelladelsur.estrelladelsur.R;
import com.estrelladelsur.estrelladelsur.auxiliar.AuxiliarGeneral;
import com.estrelladelsur.estrelladelsur.liga.adeful.FragmentCancha;
import com.estrelladelsur.estrelladelsur.liga.adeful.FragmentDivision;
import com.estrelladelsur.estrelladelsur.liga.adeful.FragmentEquipo;
import com.estrelladelsur.estrelladelsur.liga.adeful.FragmentTorneo;

public class TabsAdefulUsuario extends AppCompatActivity {

	private Toolbar toolbar;
	private ViewPager viewPager;
	private TabLayout tabLayout;
	private int restarMap = 0;
	private int viewpagerid;
	final int PAGE_COUNT = 3;
	private FragmentTransaction mCurTransaction;
	private static final String TAG = "FragmentPagerAdapter";
	private static final boolean DEBUG = false;
	private TextView txtToolBarTitulo;
	private Typeface titulos;
	private AuxiliarGeneral auxiliarGeneral;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_general);


		auxiliarGeneral = new AuxiliarGeneral(TabsAdefulUsuario.this);
		titulos = auxiliarGeneral.tituloFont(TabsAdefulUsuario.this);
		// Toolbar
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//		txtAbTitulo = (TextView) toolbar.findViewById(R.id.txtAbTitulo);
//		txtAbTitulo.setVisibility(View.GONE);

		txtToolBarTitulo = (TextView) findViewById(R.id.txtToolBarTitulo);
		txtToolBarTitulo.setText("ADEFUL");
	//	txtAbSubTitulo.setTypeface(titulos, Typeface.BOLD);

		if (savedInstanceState != null) {
			viewpagerid = savedInstanceState.getInt("viewpagerid", -1);
			viewPager = (ViewPager) findViewById(R.id.viewpager);
			viewPager.setOffscreenPageLimit(PAGE_COUNT - 1);
			if (viewpagerid != -1) {
				viewPager.setId(viewpagerid);
			} else {
				viewpagerid = viewPager.getId();
			}
			viewPager.setAdapter(new TabsAdefulAdapter(
					getSupportFragmentManager()));
		} else {
			viewPager = (ViewPager) findViewById(R.id.viewpager);
			viewPager.setOffscreenPageLimit(PAGE_COUNT - 1);
			viewPager.setAdapter(new TabsAdefulAdapter(
					getSupportFragmentManager()));
    	}
		tabLayout = (TabLayout) findViewById(R.id.appbartabs);
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
		tabLayout.setTabMode(TabLayout.MODE_FIXED);
		tabLayout.setupWithViewPager(viewPager);

		restarMap = getIntent().getIntExtra("restart", 0);
		init();
	}

	public void init() {

		if (restarMap == 1) {
			viewPager.setCurrentItem(3);
		}
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
	public class TabsAdefulAdapter extends FragmentPagerAdapter {
		private FragmentManager fm;

		private String tabTitles[] = new String[] { "EQUIPOS",
				"TORNEO ACTUAL", "CANCHAS" };

		public TabsAdefulAdapter(FragmentManager fm) {
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
				fragmentTab = FragmentEquipoUsuario.newInstance();
				break;
			case 1:
				fragmentTab = FragmentTorneoUsuario.newInstance();
				break;
			case 2:
				fragmentTab = FragmentCancha.newInstance();
				break;
			}
			return fragmentTab;
		}

		@Override
		public Object instantiateItem(View container, int position) {
			if (fm == null) {
				mCurTransaction = fm.beginTransaction();
			}
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
			return tabTitles[position];
		}
	}
}
