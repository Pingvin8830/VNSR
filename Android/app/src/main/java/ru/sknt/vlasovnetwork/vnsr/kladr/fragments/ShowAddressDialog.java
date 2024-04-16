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
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;

public class ShowAddressDialog extends DialogFragment implements View.OnClickListener {
    private final Address mAddress;

    public ShowAddressDialog(Address address) {
        mAddress = address;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.kladr_show_address, null);
        TextView txtName = dialogView.findViewById(R.id.txtName);
        TextView txtRegion = dialogView.findViewById(R.id.txtRegion);
        TextView txtCity = dialogView.findViewById(R.id.txtCity);
        TextView txtStreet = dialogView.findViewById(R.id.txtStreet);
        TextView txtHome = dialogView.findViewById(R.id.txtHome);
        TextView txtBuilding = dialogView.findViewById(R.id.txtBuilding);
        TextView txtFlat = dialogView.findViewById(R.id.txtFlat);
        Button bttnBack = (Button) dialogView.findViewById(R.id.bttnBack);
        Button bttnDelete = (Button) dialogView.findViewById(R.id.bttnDelete);
        txtName.setText(mAddress.getName());
        txtRegion.setText(mAddress.getRegion().toString());
        txtCity.setText(mAddress.getCity().toString());
        txtStreet.setText(mAddress.getStreet().toString());
        txtHome.setText(mAddress.getHouse());
        txtBuilding.setText(mAddress.getBuilding());
        txtFlat.setText(String.valueOf(mAddress.getFlat()));
        bttnBack.setOnClickListener(this);
        bttnDelete.setOnClickListener(this);
        builder.setView(dialogView).setMessage("Your address");
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bttnBack) { dismiss(); }
        else if (id == R.id.bttnDelete) {
            AddressesFragment callingFragment = (AddressesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("addresses");
            callingFragment.deleteAddress(mAddress);
            dismiss();
        }
    }
}
