package ru.sknt.vlasovnetwork.vnsr.car.fragments;

import android.widget.EditText;


import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.car.models.Fuel;

public class NewFuelDialog extends NewObjectDialog {
    private EditText mEdtxtName;
    private String mName;

    @Override
    protected void setObjectsLists() {}

    @Override
    protected int getLayoutCode() { return R.layout.car_new_fuel; }

    @Override
    protected void getDataViews() { mEdtxtName = mDialogView.findViewById(R.id.edtxtName); }

    @Override
    protected void setAdapters() {}

    @Override
    protected String getDialogMessageText() { return getResources().getString(R.string.lbl_new_fuel); }

    @Override
    protected void setData() { mName = mEdtxtName.getText().toString(); }

    @Override
    protected String getErrorText() {
        String error = "";
        if (mName.isEmpty()) { error = getResources().getString(R.string.err_bad_name); }
        return error;
    }

    @Override
    protected void createObject() {
        Fuel newFuel = new Fuel(mName);
        FuelsFragment callingFragment = (FuelsFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("fuels");
        assert callingFragment != null;
        callingFragment.createNewFuel(newFuel);
    }
}
