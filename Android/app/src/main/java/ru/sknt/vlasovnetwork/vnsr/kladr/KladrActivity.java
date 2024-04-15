package ru.sknt.vlasovnetwork.vnsr.kladr;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.google.android.material.navigation.NavigationView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.VNSRDatabase;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.AddressesFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.CityTypesFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.CityesFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.RegionsFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.StreetTypesFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.StreetsFragment;

public class KladrActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawer;
    private VNSRDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDb = Room.databaseBuilder(getApplicationContext(), VNSRDatabase.class, "vnsr-database").allowMainThreadQueries().build();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kladr);

        mDrawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Обработка щелчков элемента представления навигации здесь

        // Создаём транзакцию
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        int id = item.getItemId();

        if      (id == R.id.nav_kladr_regions)      { transaction.replace(R.id.fragmentHolder, new RegionsFragment(mDb.regionDao()), "regions"); }
        else if (id == R.id.nav_kladr_cityes)       { transaction.replace(R.id.fragmentHolder, new CityesFragment (mDb.cityDao(), mDb.cityTypeDao()), "cityes" ); }
        else if (id == R.id.nav_kladr_streets)      { transaction.replace(R.id.fragmentHolder, new StreetsFragment()); }
        else if (id == R.id.nav_kladr_addresses)    { transaction.replace(R.id.fragmentHolder, new AddressesFragment()); }
        else if (id == R.id.nav_kladr_city_types)   { transaction.replace(R.id.fragmentHolder, new CityTypesFragment  (mDb.cityTypeDao  ()), "city_types"  ); }
        else if (id == R.id.nav_kladr_street_types) { transaction.replace(R.id.fragmentHolder, new StreetTypesFragment(mDb.streetTypeDao()), "street_types"); }
        else if (id == R.id.nav_back) { finish(); }

        // Просим Android запоминать, какие пункты меню выбрал пользователь
        //transaction.addToBackStack(null);

        // Реализуем изменение
        transaction.commit();

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}