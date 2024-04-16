package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;

public class NewCityDialog extends DialogFragment implements View.OnClickListener {
    private TextView mTxtError;
    private Animation mAnimError;
    private EditText mEdtxtName;
    private Spinner mSpnType;
    private final List<CityType> mCityTypes;

    public NewCityDialog(CityTypeDao dao) {
        mCityTypes = dao.getAll();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mAnimError = AnimationUtils.loadAnimation(getContext(),R.anim.error);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.kladr_new_city, null);
        mTxtError = dialogView.findViewById(R.id.txtError);
        mEdtxtName = dialogView.findViewById(R.id.edtxtName);
        mSpnType = dialogView.findViewById(R.id.spnType);
        Button bttnAdd = dialogView.findViewById(R.id.bttnAdd);
        Button bttnCancel = dialogView.findViewById(R.id.bttnCancel);

        ArrayAdapter<CityType> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mCityTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnType.setAdapter(adapter);

        mTxtError.setVisibility(View.INVISIBLE);
        bttnCancel.setOnClickListener(this);
        bttnAdd.setOnClickListener(this);

        builder.setView(dialogView).setMessage("Add a new city");
        return builder.create();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.bttnCancel) { dismiss(); }
        else if (v.getId() == R.id.bttnAdd) {
            // Создаём новый город
            String name = mEdtxtName.getText().toString();
            CityType cityType = mCityTypes.get(mSpnType.getSelectedItemPosition());

            if (name.isEmpty()) {
                mTxtError.setText("Bad name");
                mTxtError.startAnimation(mAnimError);
            } else {
                City newCity = new City(name, cityType);

                // Получаем ссылку на Fragment
                CityesFragment callingFragment = (CityesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("cityes");

                // Передаём newCity обратно в Fragment
                callingFragment.createNewCity(newCity);

                // Закрываем диалог
                dismiss();
            }
        }
    }
}
