package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.CityTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.City;

public class ShowCityDialog extends DialogFragment implements View.OnClickListener {
    private City mCity;
    private final CityTypeDao mCityTypeDao;

    public ShowCityDialog(CityTypeDao cityTypeDao) {
        mCityTypeDao = cityTypeDao;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.kladr_show_city, null);
        TextView txtType = dialogView.findViewById(R.id.txtType);
        TextView txtName = dialogView.findViewById(R.id.txtName);
        Button bttnBack = (Button) dialogView.findViewById(R.id.bttnBack);
        Button bttnDelete = (Button) dialogView.findViewById(R.id.bttnDelete);
        txtType.setText(mCity.getType(mCityTypeDao).getName());
        txtName.setText(mCity.getName());
        bttnBack.setOnClickListener(this);
        bttnDelete.setOnClickListener(this);
        builder.setView(dialogView).setMessage("Your city");
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bttnBack) { dismiss(); }
        else if (id == R.id.bttnDelete) {
            CityesFragment callingFragment = (CityesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("cityes");
            callingFragment.deleteCity(mCity);
            dismiss();
        }
    }

    // Получаем город из Activity
    public void sendCitySelected(City citySelected) {
        mCity = citySelected;
    }
}