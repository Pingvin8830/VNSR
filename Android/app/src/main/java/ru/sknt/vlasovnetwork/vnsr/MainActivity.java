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

import ru.sknt.vlasovnetwork.vnsr.kladr.KladrActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTxtError;
    private Animation mAnimError;
    private EditText mEdtxtLogin;
    private EditText mEdtxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                mTxtError.setText("Bad creds");
                mTxtError.startAnimation(mAnimError);
            }
        } else if (v.getId() == R.id.bttnLogOut) { finish(); }
//        else if (v.getId() == R.id.bttnCar)      { startActivity(new Intent(this, CarActivity.class)); }
//        else if (v.getId() == R.id.bttnTravels) { startActivity(new Intent(this, TravelsActivity.class)); }
        else if (v.getId() == R.id.bttnKladr)   { startActivity(new Intent(this, KladrActivity.class)); }
//        else if (v.getId() == R.id.bttnSync)    { startActivity(new Intent(this, SyncActivity.class)); }
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
        Button bttnSync = findViewById(R.id.bttnSync);
        Button bttnLogOut = findViewById(R.id.bttnLogOut);

        bttnCar.setOnClickListener(this);
        bttnTravels.setOnClickListener(this);
        bttnKladr.setOnClickListener(this);
        bttnSync.setOnClickListener(this);
        bttnLogOut.setOnClickListener(this);
    }
}