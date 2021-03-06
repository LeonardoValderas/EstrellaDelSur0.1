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
import com.estrelladelsur.estrelladelsur.database.administrador.lifuba.ControladorLifuba;
import com.estrelladelsur.estrelladelsur.liga.administrador.adeful.FragmentCanchaAdeful;
import com.estrelladelsur.estrelladelsur.liga.administrador.adeful.FragmentDivisionAdeful;
import com.estrelladelsur.estrelladelsur.liga.administrador.adeful.FragmentEquipoAdeful;
import com.estrelladelsur.estrelladelsur.liga.administrador.adeful.FragmentTorneoAdeful;

public class TabsAdeful extends AppCompatActivity {

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

		txtToolBarTitulo = (TextView) findViewById(R.id.txtToolBarTitulo);
		txtToolBarTitulo.setText("ADEFUL");


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

	@Override
	public void onResume() {
		super.onResume();
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
				fragmentTab = FragmentEquipoAdeful.newInstance();
				break;
			case 1:
				fragmentTab = FragmentDivisionAdeful.newInstance();
				break;
			case 2:
				fragmentTab = FragmentTorneoAdeful.newInstance();
				break;
			case 3:
				fragmentTab = FragmentCanchaAdeful.newInstance();
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
