package ru.sknt.vlasovnetwork.vnsr.car.fragments;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.car.models.FuelStation;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;

public class NewFuelStationDialog extends NewObjectDialog {
    private EditText mEdtxtCompany;
    private EditText mEdtxtNumber;
    private EditText mEdtxtPhone;
    private Spinner mSpnAddress;
    private List<Address> mAddresses;
    private String mCompany;
    private String mNumber;
    private String mPhone;
    private Address mAddress;

    @Override
    protected void setObjectsLists() { mAddresses = MainActivity.AddressDao.getAll(); }

    @Override
    protected int getLayoutCode() { return R.layout.car_new_fuel_station; }

    @Override
    protected void getDataViews() {
        mEdtxtCompany = mDialogView.findViewById(R.id.edtxtCompany);
        mEdtxtNumber = mDialogView.findViewById(R.id.edtxtNumber);
        mEdtxtPhone = mDialogView.findViewById(R.id.edtxtPhone);
        mSpnAddress = mDialogView.findViewById(R.id.spnAddress);
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<Address> addressAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mAddresses);
        addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnAddress.setAdapter(addressAdapter);
    }

    @Override
    protected String getDialogMessageText() { return getResources().getString(R.string.lbl_new_fuel_station); }

    @Override
    protected void setData() {
        mCompany = mEdtxtCompany.getText().toString();
        mNumber = mEdtxtNumber.getText().toString();
        mPhone = mEdtxtPhone.getText().toString();
        mAddress = mAddresses.get(mSpnAddress.getSelectedItemPosition());
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if (mCompany.isEmpty()) { error = getResources().getString(R.string.err_bad_company); }
        return error;
    }

    @Override
    protected void createObject() {
        FuelStation newFuelStation = new FuelStation(mCompany, mNumber, mPhone, mAddress);
        FuelStationsFragment callingFragment = (FuelStationsFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("fuel_stations");
        assert callingFragment != null;
        callingFragment.createNewFuelStation(newFuelStation);
    }
}
