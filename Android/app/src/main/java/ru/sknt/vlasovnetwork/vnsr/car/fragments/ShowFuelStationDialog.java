package ru.sknt.vlasovnetwork.vnsr.car.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.car.models.FuelStation;

public class ShowFuelStationDialog extends ShowObjectDialog {
    private final FuelStation mFuelStation;
    private TextView mTxtCompany;
    private TextView mTxtNumber;
    private TextView mTxtPhone;
    private TextView mTxtAddress;


    public ShowFuelStationDialog(FuelStation fuelStation) {
        mFuelStation = fuelStation;
    }

    @Override
    protected int getLayoutCode() {
        return R.layout.car_show_fuel_station;
    }

    @Override
    protected void getDataViews() {
        mTxtCompany = mDialogView.findViewById(R.id.txtCompany);
        mTxtNumber = mDialogView.findViewById(R.id.txtNumber);
        mTxtPhone = mDialogView.findViewById(R.id.txtPhone);
        mTxtAddress = mDialogView.findViewById(R.id.txtAddress);
    }

    @Override
    protected void setData() {
        mTxtCompany.setText(mFuelStation.getCompany());
        mTxtNumber.setText(mFuelStation.getNumber());
        mTxtPhone.setText(mFuelStation.getPhone());
        mTxtAddress.setText(mFuelStation.getAddress().getFull());
    }

    @Override
    protected String getDialogMessageText() {
        return getResources().getString(R.string.lbl_show_fuel_station);
    }

    @Override
    protected void deleteObject() {
        FuelStationsFragment callingFragment = (FuelStationsFragment) getActivity().getSupportFragmentManager().findFragmentByTag("fuel_stations");
        callingFragment.deleteFuelStation(mFuelStation);
    }
}