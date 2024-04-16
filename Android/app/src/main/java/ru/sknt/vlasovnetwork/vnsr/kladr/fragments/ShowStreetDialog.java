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
import ru.sknt.vlasovnetwork.vnsr.kladr.daos.StreetTypeDao;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;

public class ShowStreetDialog extends DialogFragment implements View.OnClickListener {
    private Street mStreet;
    private final StreetTypeDao mStreetTypeDao;

    public ShowStreetDialog(StreetTypeDao streetTypeDao) {
        mStreetTypeDao = streetTypeDao;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.kladr_show_street, null);
        TextView txtType = dialogView.findViewById(R.id.txtType);
        TextView txtName = dialogView.findViewById(R.id.txtName);
        Button bttnBack = (Button) dialogView.findViewById(R.id.bttnBack);
        Button bttnDelete = (Button) dialogView.findViewById(R.id.bttnDelete);
        txtType.setText(mStreet.getType(mStreetTypeDao).getName());
        txtName.setText(mStreet.getName());
        bttnBack.setOnClickListener(this);
        bttnDelete.setOnClickListener(this);
        builder.setView(dialogView).setMessage("Your street");
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bttnBack) { dismiss(); }
        else if (id == R.id.bttnDelete) {
            StreetsFragment callingFragment = (StreetsFragment) getActivity().getSupportFragmentManager().findFragmentByTag("streets");
            callingFragment.deleteStreet(mStreet);
            dismiss();
        }
    }

    // Получаем улицу из Activity
    public void sendStreetSelected(Street streetSelected) {
        mStreet = streetSelected;
    }
}