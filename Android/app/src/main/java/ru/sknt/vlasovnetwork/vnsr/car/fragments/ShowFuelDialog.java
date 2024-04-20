package ru.sknt.vlasovnetwork.vnsr.car.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.car.models.Fuel;

public class ShowFuelDialog extends ShowObjectDialog {
    private final Fuel mFuel;
    private TextView mTxtName;

    public ShowFuelDialog(Fuel fuel) {
        mFuel = fuel;
    }

    @Override
    protected int getLayoutCode() {
        return R.layout.car_show_fuel;
    }

    @Override
    protected void getDataViews() { mTxtName = mDialogView.findViewById(R.id.txtName); }

    @Override
    protected void setData() { mTxtName.setText(mFuel.getName()); }

    @Override
    protected String getDialogMessageText() {
        return getResources().getString(R.string.lbl_show_fuel);
    }

    @Override
    protected void deleteObject() {
        FuelsFragment callingFragment = (FuelsFragment) getActivity().getSupportFragmentManager().findFragmentByTag("fuels");
        callingFragment.deleteFuel(mFuel);
    }
}