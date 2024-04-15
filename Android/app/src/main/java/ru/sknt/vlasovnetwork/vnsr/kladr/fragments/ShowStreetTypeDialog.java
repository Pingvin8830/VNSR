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
import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;

public class ShowStreetTypeDialog extends DialogFragment implements View.OnClickListener {
    private StreetType mStreetType;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.kladr_show_street_type, null);
        TextView txtShort = dialogView.findViewById(R.id.txtShort);
        TextView txtName = dialogView.findViewById(R.id.txtName);
        Button bttnBack = (Button) dialogView.findViewById(R.id.bttnBack);
        Button bttnDelete = (Button) dialogView.findViewById(R.id.bttnDelete);
        txtShort.setText(mStreetType.getShort());
        txtName.setText(mStreetType.getName());
        bttnBack.setOnClickListener(this);
        bttnDelete.setOnClickListener(this);
        builder.setView(dialogView).setMessage("Your street type");
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bttnBack) { dismiss(); }
        else if (id == R.id.bttnDelete) {
            StreetTypesFragment callingFragment = (StreetTypesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("street_types");
            callingFragment.deleteStreetType(mStreetType);
            dismiss();
        }
    }

    // Получаем тип улицы из Activity
    public void sendStreetTypeSelected(StreetType streetTypeSelected) {
        mStreetType = streetTypeSelected;
    }
}
