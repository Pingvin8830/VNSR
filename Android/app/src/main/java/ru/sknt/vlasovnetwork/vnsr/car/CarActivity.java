package ru.sknt.vlasovnetwork.vnsr.car;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.car.fragments.FuelStationsFragment;
import ru.sknt.vlasovnetwork.vnsr.car.fragments.FuelsFragment;
import ru.sknt.vlasovnetwork.vnsr.car.fragments.RefuelsFragment;

public class CarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

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

        if      (id == R.id.nav_car_refuels)       { transaction.replace(R.id.fragmentHolder, new RefuelsFragment     (), "refuels"); }
        else if (id == R.id.nav_car_fuel_stations) { transaction.replace(R.id.fragmentHolder, new FuelStationsFragment(), "fuel_stations" ); }
        else if (id == R.id.nav_car_fuels)         { transaction.replace(R.id.fragmentHolder, new FuelsFragment       (), "fuels"); }
        else if (id == R.id.nav_back) { finish(); }

        // Просим Android запоминать, какие пункты меню выбрал пользователь
        //transaction.addToBackStack(null);

        // Реализуем изменение
        transaction.commit();

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}