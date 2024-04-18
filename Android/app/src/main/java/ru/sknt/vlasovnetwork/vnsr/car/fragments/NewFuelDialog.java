package ru.sknt.vlasovnetwork.vnsr.car.fragments;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.car.models.Fuel;
import ru.sknt.vlasovnetwork.vnsr.car.models.FuelStation;

public class NewFuelDialog extends NewObjectDialog {
    private EditText mEdtxtName;
    private Spinner mSpnFuelStation;
    private List<FuelStation> mFuelStations;
    private String mName;
    private FuelStation mFuelStation;

    @Override
    protected void setObjectsLists() { mFuelStations = MainActivity.FuelStationDao.getAll(); }

    @Override
    protected int getLayoutCode() { return R.layout.car_new_fuel; }

    @Override
    protected void getDataViews() {
        mEdtxtName = mDialogView.findViewById(R.id.edtxtName);
        mSpnFuelStation = mDialogView.findViewById(R.id.spnFuelStation);
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<FuelStation> fuelStationAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mFuelStations);
        fuelStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnFuelStation.setAdapter(fuelStationAdapter);
    }

    @Override
    protected String getDialogMessageText() { return "Add a new fuel"; }

    @Override
    protected void setData() {
        mName = mEdtxtName.getText().toString();
        mFuelStation = mFuelStations.get(mSpnFuelStation.getSelectedItemPosition());
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if (mName.isEmpty()) { error = "Bad name"; }
        return error;
    }

    @Override
    protected void createObject() {
        Fuel newFuel = new Fuel(mName, mFuelStation);
        FuelsFragment callingFragment = (FuelsFragment) getActivity().getSupportFragmentManager().findFragmentByTag("fuels");
        callingFragment.createNewFuel(newFuel);
    }
}
