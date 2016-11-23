package com.estrelladelsur.estrelladelsur.liga.administrador.tabs_adm;

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
import com.estrelladelsur.estrelladelsur.liga.administrador.lifuba.FragmentCanchaLifuba;
import com.estrelladelsur.estrelladelsur.liga.administrador.lifuba.FragmentDivisionLifuba;
import com.estrelladelsur.estrelladelsur.liga.administrador.lifuba.FragmentEquipoLifuba;
import com.estrelladelsur.estrelladelsur.liga.administrador.lifuba.FragmentTorneoLifuba;

public class TabsLifuba extends AppCompatActivity {

	private Toolbar toolbar;
	private ViewPager viewPager;
	private TabLayout tabLayout;
	private int restarMap = 0;
	private int viewpagerid;
	final int PAGE_COUNT = 4;
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


		auxiliarGeneral = new AuxiliarGeneral(TabsLifuba.this);
		titulos = auxiliarGeneral.tituloFont(TabsLifuba.this);
		// Toolbar
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		txtToolBarTitulo = (TextView) findViewById(R.id.txtToolBarTitulo);
		txtToolBarTitulo.setText("LIFUBA");

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

		private String tabTitles[] = new String[] { "EQUIPO", "DIVISION",
				"TORNEO", "CANCHA" };

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
				fragmentTab = FragmentEquipoLifuba.newInstance();
				break;
			case 1:
				fragmentTab = FragmentDivisionLifuba.newInstance();
				break;
			case 2:
				fragmentTab = FragmentTorneoLifuba.newInstance();
				break;
			case 3:
				fragmentTab = FragmentCanchaLifuba.newInstance();
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
