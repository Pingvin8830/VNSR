package ru.sknt.vlasovnetwork.vnsr.travels;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.AddressesFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.CityTypesFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.CityesFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.RegionsFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.StreetTypesFragment;
import ru.sknt.vlasovnetwork.vnsr.kladr.fragments.StreetsFragment;
import ru.sknt.vlasovnetwork.vnsr.travels.fragments.HotelsFragment;
import ru.sknt.vlasovnetwork.vnsr.travels.fragments.PointsFragment;
import ru.sknt.vlasovnetwork.vnsr.travels.fragments.ToolroadsFragment;
import ru.sknt.vlasovnetwork.vnsr.travels.fragments.TravelStatesFragment;
import ru.sknt.vlasovnetwork.vnsr.travels.fragments.TravelsFragment;
import ru.sknt.vlasovnetwork.vnsr.travels.fragments.WaysFragment;

public class TravelsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travels);

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

        if      (id == R.id.nav_travels_travels)       { transaction.replace(R.id.fragmentHolder, new TravelsFragment()); }
        else if (id == R.id.nav_travels_points)        { transaction.replace(R.id.fragmentHolder, new PointsFragment()); }
        else if (id == R.id.nav_travels_ways)          { transaction.replace(R.id.fragmentHolder, new WaysFragment()); }
        else if (id == R.id.nav_travels_toolroads)     { transaction.replace(R.id.fragmentHolder, new ToolroadsFragment()); }
        else if (id == R.id.nav_travels_hotels)        { transaction.replace(R.id.fragmentHolder, new HotelsFragment()); }
        else if (id == R.id.nav_travels_travel_states) { transaction.replace(R.id.fragmentHolder, new TravelStatesFragment()); }
        else if (id == R.id.nav_back) { finish(); }

        // Просим Android запоминать, какие пункты меню выбрал пользователь
        //transaction.addToBackStack(null);

        // Реализуем изменение
        transaction.commit();

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}