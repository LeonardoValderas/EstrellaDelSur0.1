package com.estrelladelsur.estrelladelsur.institucion.adeful;

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

public class TabsDireccion extends AppCompatActivity implements Communicator{

    private Toolbar toolbar;
    private ViewPager viewPager;
    private int viewpagerid;
    final int PAGE_COUNT = 2;
    private TabLayout tabLayout;
    private FragmentTransaction mCurTransaction;
    private static final String TAG = "FragmentPagerAdapter";
    private static final boolean DEBUG = false;
    private TextView txtAbTitulo, txtAbSubTitulo;
    private Typeface titulos;
    private AuxiliarGeneral auxiliarGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_general);

        auxiliarGeneral = new AuxiliarGeneral(TabsDireccion.this);
        titulos = auxiliarGeneral.tituloFont(TabsDireccion.this);

        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtAbTitulo = (TextView) toolbar.findViewById(R.id.txtAbTitulo);
        txtAbTitulo.setVisibility(View.GONE);

        txtAbSubTitulo = (TextView) findViewById(R.id.txtAbSubTitulo);
        txtAbSubTitulo.setText("DIRECCION");
        txtAbSubTitulo.setTypeface(titulos, Typeface.BOLD);

        if (savedInstanceState != null) {
            viewpagerid = savedInstanceState.getInt("viewpagerid", -1);
            viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPager.setOffscreenPageLimit(PAGE_COUNT - 1);
            if (viewpagerid != -1) {
                viewPager.setId(viewpagerid);
            } else {
                viewpagerid = viewPager.getId();
            }
            viewPager.setAdapter(new TabsDireccionAdapter(
                    getSupportFragmentManager()));
        } else {
            viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPager.setOffscreenPageLimit(PAGE_COUNT - 1);
            viewPager = (ViewPager) findViewById(R.id.viewpager);

            viewPager.setAdapter(new TabsDireccionAdapter(
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

    public class TabsDireccionAdapter extends FragmentPagerAdapter {

        private FragmentManager fm;
        private String tabTitles[] =
                new String[]{"CREAR DIRECCION", "EDITAR DIRECCION"};

        public TabsDireccionAdapter(FragmentManager fm) {
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
                    fragmentTab = FragmentGenerarDireccion.newInstance();

                    break;
                case 1:
                    fragmentTab = FragmentEditarDireccion.newInstance();

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
    public void refresh() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentEditarDireccion fragment = (FragmentEditarDireccion) manager
                .findFragmentByTag("android:switcher:" + viewPager.getId()
                        + ":" + 1);
        fragment.recyclerViewLoadDireccion();
    }
}