package ru.sknt.vlasovnetwork.vnsr.sync;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.sync.fragments.HomeFragment;
import ru.sknt.vlasovnetwork.vnsr.sync.fragments.UploadFragment;

public class SyncActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawer;
    public String getServerUrl() { return MainActivity.mPrefs.getString("server_url", "Server url no set"); }
    public void setServerUrl(String url) {
        MainActivity.mEditor.putString("server_url", url);
        MainActivity.mEditor.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);


        mDrawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentHolder, new HomeFragment(), "home");
        transaction.commit();
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

        if      (id == R.id.nav_sync_home)      { transaction.replace(R.id.fragmentHolder, new HomeFragment(), "home"); }
        else if (id == R.id.nav_sync_to_server) { transaction.replace(R.id.fragmentHolder, new UploadFragment(), "upload"); }
        else if (id == R.id.nav_back) { finish(); }

        // Просим Android запоминать, какие пункты меню выбрал пользователь
        //transaction.addToBackStack(null);

        // Реализуем изменение
        transaction.commit();

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}