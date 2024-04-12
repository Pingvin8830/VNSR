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
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;

public class ShowRegionDialog extends DialogFragment implements View.OnClickListener {
    private Region mRegion;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.kladr_show_region, null);
        TextView txtCode = dialogView.findViewById(R.id.txtCode);
        TextView txtName = dialogView.findViewById(R.id.txtName);
        Button bttnBack = (Button) dialogView.findViewById(R.id.bttnBack);
        txtCode.setText(mRegion.getCode());
        txtName.setText(mRegion.getName());
        bttnBack.setOnClickListener(this);
        builder.setView(dialogView).setMessage("Your region");
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bttnBack) { dismiss(); }
    }

    // Получаем регион из Activity
    public void sendRegionSelected(Region regionSelected) {
        mRegion = regionSelected;
    }
}
