package ru.sknt.vlasovnetwork.vnsr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import ru.sknt.vlasovnetwork.vnsr.car.CarActivity;
import ru.sknt.vlasovnetwork.vnsr.car.daos.FuelStationDao;
import ru.sknt.vlasovnetwork.vnsr.car.daos.FuelDao;
import ru.sknt.vlasovnetwork.vnsr.car.daos.RefuelDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.KladrActivity;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.AddressDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.RegionDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetTypeDao;
import ru.sknt.vlasovnetwork.vnsr.travels.TravelsActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTxtError;
    private Animation mAnimError;
    private EditText mEdtxtLogin;
    private EditText mEdtxtPassword;
    public static AddressDao AddressDao;
    public static RegionDao RegionDao;
    public static CityDao CityDao;
    public static CityTypeDao CityTypeDao;
    public static StreetDao StreetDao;
    public static StreetTypeDao StreetTypeDao;
    public static FuelStationDao FuelStationDao;
    public static FuelDao FuelDao;
    public static RefuelDao RefuelDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VNSRDatabase db = Room.databaseBuilder(getApplicationContext(), VNSRDatabase.class, "vnsr-database").allowMainThreadQueries().build();
        MainActivity.AddressDao = db.addressDao();
        MainActivity.RegionDao = db.regionDao();
        MainActivity.CityDao = db.cityDao();
        MainActivity.CityTypeDao = db.cityTypeDao();
        MainActivity.StreetDao = db.streetDao();
        MainActivity.StreetTypeDao = db.streetTypeDao();
        MainActivity.FuelStationDao = db.fuelStationDao();
        MainActivity.FuelDao = db.fuelDao();
        MainActivity.RefuelDao = db.refuelDao();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAnimError = AnimationUtils.loadAnimation(this,R.anim.error);
        mAnimError.setAnimationListener(
                new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { mTxtError.setVisibility(View.VISIBLE); }
                    @Override
                    public void onAnimationEnd(Animation animation) { mTxtError.setVisibility(View.INVISIBLE); }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                }
        );

        mTxtError = findViewById(R.id.txtError);
        mEdtxtLogin = findViewById(R.id.edtxtLogin);
        mEdtxtPassword = findViewById(R.id.edtxtPassword);

        Button bttnLogIn = findViewById(R.id.bttnLogin);
        bttnLogIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bttnLogin) {
            String login = mEdtxtLogin.getText().toString();
            String password = mEdtxtPassword.getText().toString();
            if (login.equals("admin") && password.equals("admin")) {
                startMainMenu();
            } else {
                mTxtError.setText(R.string.err_bad_creds);
                mTxtError.startAnimation(mAnimError);
            }
        } else if (v.getId() == R.id.bttnLogOut) { finish(); }
        else if (v.getId() == R.id.bttnCar)      { startActivity(new Intent(this, CarActivity.class)); }
        else if (v.getId() == R.id.bttnTravels)  { startActivity(new Intent(this, TravelsActivity.class)); }
        else if (v.getId() == R.id.bttnKladr)    { startActivity(new Intent(this, KladrActivity.class)); }
        else if (v.getId() == R.id.bttnTruncate) { truncateAll(); }
//        else if (v.getId() == R.id.bttnSync)     { startActivity(new Intent(this, SyncActivity.class)); }
        else {
            Button bttn = (Button) v;
            Toast.makeText(this, "Pressed button " + bttn.getText().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void startMainMenu() {
        setContentView(R.layout.main_menu);
        Button bttnCar = findViewById(R.id.bttnCar);
        Button bttnTravels = findViewById(R.id.bttnTravels);
        Button bttnKladr = findViewById(R.id.bttnKladr);
        //Button bttnSync = findViewById(R.id.bttnSync);
        Button bttnTruncate = findViewById(R.id.bttnTruncate);
        Button bttnLogOut = findViewById(R.id.bttnLogOut);

        bttnCar.setOnClickListener(this);
        bttnTravels.setOnClickListener(this);
        bttnKladr.setOnClickListener(this);
        //bttnSync.setOnClickListener(this);
        bttnTruncate.setOnClickListener(this);
        bttnLogOut.setOnClickListener(this);
    }

    private void truncateAll() {
        MainActivity.RefuelDao.truncate();
        MainActivity.FuelDao.truncate();
        MainActivity.FuelStationDao.truncate();
        MainActivity.AddressDao.truncate();
        MainActivity.StreetDao.truncate();
        MainActivity.StreetTypeDao.truncate();
        MainActivity.CityDao.truncate();
        MainActivity.CityTypeDao.truncate();
        MainActivity.RegionDao.truncate();
        Toast.makeText(getApplicationContext(), "Truncated", Toast.LENGTH_SHORT).show();
    }
}