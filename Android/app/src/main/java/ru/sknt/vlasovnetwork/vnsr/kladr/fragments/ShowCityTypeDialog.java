package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.CityType;

public class ShowCityTypeDialog extends DialogFragment implements View.OnClickListener {
    private CityType mCityType;

    public ShowCityTypeDialog (CityType cityType) { this.mCityType = cityType; }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.kladr_show_city_type, null);
        TextView txtShort = dialogView.findViewById(R.id.txtShort);
        TextView txtName = dialogView.findViewById(R.id.txtName);
        Button bttnBack = (Button) dialogView.findViewById(R.id.bttnBack);
        Button bttnDelete = (Button) dialogView.findViewById(R.id.bttnDelete);
        txtShort.setText(mCityType.getShort());
        txtName.setText(mCityType.getName());
        bttnBack.setOnClickListener(this);
        bttnDelete.setOnClickListener(this);
        builder.setView(dialogView).setMessage("Your city type");
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bttnBack) { dismiss(); }
        else if (id == R.id.bttnDelete) {
            CityTypesFragment callingFragment = (CityTypesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("city_types");
            callingFragment.deleteCityType(mCityType);
            dismiss();
        }
    }
}
