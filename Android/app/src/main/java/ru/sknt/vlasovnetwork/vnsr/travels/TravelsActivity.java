package ru.sknt.vlasovnetwork.vnsr.travels;

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

        if      (id == R.id.nav_travels_travels)       { transaction.replace(R.id.fragmentHolder, new TravelsFragment(),      "travels"); }
        else if (id == R.id.nav_travels_points)        { transaction.replace(R.id.fragmentHolder, new PointsFragment(),       "points" ); }
        else if (id == R.id.nav_travels_ways)          { transaction.replace(R.id.fragmentHolder, new WaysFragment(),         "ways"); }
        else if (id == R.id.nav_travels_toolroads)     { transaction.replace(R.id.fragmentHolder, new ToolroadsFragment(),    "toolroads"); }
        else if (id == R.id.nav_travels_hotels)        { transaction.replace(R.id.fragmentHolder, new HotelsFragment(),       "hotels"); }
        else if (id == R.id.nav_travels_travel_states) { transaction.replace(R.id.fragmentHolder, new TravelStatesFragment(), "travel_states"); }
        else if (id == R.id.nav_back) { finish(); }

        // Просим Android запоминать, какие пункты меню выбрал пользователь
        //transaction.addToBackStack(null);

        // Реализуем изменение
        transaction.commit();

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}