package ru.sknt.vlasovnetwork.vnsr;

import android.content.Intent;
import android.content.SharedPreferences;
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
import ru.sknt.vlasovnetwork.vnsr.migrations.Migration_2_3;
import ru.sknt.vlasovnetwork.vnsr.migrations.Migration_3_4;
import ru.sknt.vlasovnetwork.vnsr.migrations.Migration_4_5;
import ru.sknt.vlasovnetwork.vnsr.migrations.Migration_5_6;
import ru.sknt.vlasovnetwork.vnsr.migrations.Migration_6_7;
import ru.sknt.vlasovnetwork.vnsr.travels.daos.HotelDao;
import ru.sknt.vlasovnetwork.vnsr.travels.daos.PointDao;
import ru.sknt.vlasovnetwork.vnsr.travels.daos.TollRoadDao;
import ru.sknt.vlasovnetwork.vnsr.travels.daos.TravelDao;
import ru.sknt.vlasovnetwork.vnsr.travels.daos.WayDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.KladrActivity;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.AddressDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.RegionDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetTypeDao;
import ru.sknt.vlasovnetwork.vnsr.migrations.Migration_1_2;
import ru.sknt.vlasovnetwork.vnsr.sync.SyncActivity;
import ru.sknt.vlasovnetwork.vnsr.sync.daos.TaskDao;
import ru.sknt.vlasovnetwork.vnsr.travels.TravelsActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTxtError;
    private Animation mAnimError;
    private EditText mEdtxtLogin;
    private EditText mEdtxtPassword;
    public static SharedPreferences mPrefs;
    public static SharedPreferences.Editor mEditor;
    public static AddressDao AddressDao;
    public static RegionDao RegionDao;
    public static CityDao CityDao;
    public static CityTypeDao CityTypeDao;
    public static StreetDao StreetDao;
    public static StreetTypeDao StreetTypeDao;
    public static FuelStationDao FuelStationDao;
    public static FuelDao FuelDao;
    public static RefuelDao RefuelDao;
    public static TravelDao TravelDao;
    public static PointDao PointDao;
    public static WayDao WayDao;
    public static TollRoadDao TollRoadDao;
    public static HotelDao HotelDao;
    public static TaskDao TaskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VNSRDatabase db = createDatabase();

        MainActivity.AddressDao = db.addressDao();
        MainActivity.RegionDao = db.regionDao();
        MainActivity.CityDao = db.cityDao();
        MainActivity.CityTypeDao = db.cityTypeDao();
        MainActivity.StreetDao = db.streetDao();
        MainActivity.StreetTypeDao = db.streetTypeDao();
        MainActivity.FuelStationDao = db.fuelStationDao();
        MainActivity.FuelDao = db.fuelDao();
        MainActivity.RefuelDao = db.refuelDao();
        MainActivity.TaskDao = db.taskDao();
        MainActivity.TravelDao = db.travelDao();
        MainActivity.PointDao = db.pointDao();
        MainActivity.WayDao = db.wayDao();
        MainActivity.TollRoadDao = db.toolRoadDao();
        MainActivity.HotelDao = db.hotelDao();

        MainActivity.mPrefs = getSharedPreferences("vnsr", MODE_PRIVATE);
        MainActivity.mEditor = mPrefs.edit();

        String login = mPrefs.getString("login", null);
        String password = mPrefs.getString("password", null);

        if ((login == null) || (password == null)) { setContentView(R.layout.registration); }
        else { setContentView(R.layout.login); }

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

        Button bttnLoginRegistration;

        if ((login == null) || (password == null)) { bttnLoginRegistration = findViewById(R.id.bttnRegistration); }
        else { bttnLoginRegistration = findViewById(R.id.bttnLogin); }

        bttnLoginRegistration.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bttnLogin) {
            String login = mEdtxtLogin.getText().toString();
            String password = mEdtxtPassword.getText().toString();
            String correctLogin = MainActivity.mPrefs.getString("login", null);
            String correctPassword = MainActivity.mPrefs.getString("password", null);
            if (login.equals(correctLogin) && password.equals(correctPassword)) {
                startMainMenu();
            } else {
                mTxtError.setText(R.string.err_bad_creds);
                mTxtError.startAnimation(mAnimError);
            }
        } else if (v.getId() == R.id.bttnRegistration) {
            String newLogin = mEdtxtLogin.getText().toString();
            String newPassword = mEdtxtPassword.getText().toString();
            if ((!newLogin.isEmpty()) && (!newPassword.isEmpty())) {
                MainActivity.mEditor.putString("login", newLogin);
                MainActivity.mEditor.putString("password", newPassword);
                MainActivity.mEditor.commit();
                startMainMenu();
            } else {
                mTxtError.setText(R.string.err_bad_creds);
                mTxtError.startAnimation(mAnimError);
            }
        }
        else if (v.getId() == R.id.bttnLogOut) { finish(); }
        else if (v.getId() == R.id.bttnCar)      { startActivity(new Intent(this, CarActivity.class)); }
        else if (v.getId() == R.id.bttnTravels)  { startActivity(new Intent(this, TravelsActivity.class)); }
        else if (v.getId() == R.id.bttnKladr)    { startActivity(new Intent(this, KladrActivity.class)); }
        else if (v.getId() == R.id.bttnSync)     { startActivity(new Intent(this, SyncActivity.class)); }
        else {
            Button bttn = (Button) v;
            Toast.makeText(this, "Pressed button " + bttn.getText().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private VNSRDatabase createDatabase() {
        Migration_1_2 migration_1_2 = new Migration_1_2(1, 2);
        Migration_2_3 migration_2_3 = new Migration_2_3(2, 3);
        Migration_3_4 migration_3_4 = new Migration_3_4(3, 4);
        Migration_4_5 migration_4_5 = new Migration_4_5(4, 5);
        Migration_5_6 migration_5_6 = new Migration_5_6(5, 6);
        Migration_6_7 migration_6_7 = new Migration_6_7(6, 7);

        return Room.databaseBuilder(getApplicationContext(), VNSRDatabase.class, "vnsr-database")
                .addMigrations(migration_1_2)
                .addMigrations(migration_2_3)
                .addMigrations(migration_3_4)
                .addMigrations(migration_4_5)
                .addMigrations(migration_5_6)
                .addMigrations(migration_6_7)
                .allowMainThreadQueries()
                .build();
    }
    private void startMainMenu() {
        setContentView(R.layout.main_menu);
        Button bttnCar = findViewById(R.id.bttnCar);
        Button bttnTravels = findViewById(R.id.bttnTravels);
        Button bttnKladr = findViewById(R.id.bttnKladr);
        Button bttnSync = findViewById(R.id.bttnSync);
        Button bttnLogOut = findViewById(R.id.bttnLogOut);

        bttnCar.setOnClickListener(this);
        bttnTravels.setOnClickListener(this);
        bttnKladr.setOnClickListener(this);
        bttnSync.setOnClickListener(this);
        bttnLogOut.setOnClickListener(this);
    }
}