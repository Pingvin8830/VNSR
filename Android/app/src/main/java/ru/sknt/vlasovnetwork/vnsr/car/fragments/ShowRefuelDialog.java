package ru.sknt.vlasovnetwork.vnsr.car.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.car.models.Refuel;

public class ShowRefuelDialog extends ShowObjectDialog {
    private final Refuel mRefuel;
    private TextView mTxtFuelStation;
    private TextView mTxtCheckNumber;
    private TextView mTxtDateTime;
    private TextView mTxtTrk;
    private TextView mTxtFuel;
    private TextView mTxtCount;
    private TextView mTxtPrice;
    private TextView mTxtCost;
    private TextView mTxtDistanceReserve;
    private TextView mTxtFuelConsumptionAvg;
    private TextView mTxtOdometer;
    private TextView mTxtDistance;
    private TextView mTxtFuelConsumption;
    private TextView mTxtTimeDelta;

    public ShowRefuelDialog(Refuel refuel) {
        mRefuel = refuel;
    }

    @Override
    protected int getLayoutCode() {
        return R.layout.car_show_refuel;
    }

    @Override
    protected void getDataViews() {
        mTxtFuelStation = mDialogView.findViewById(R.id.txtFuelStation);
        mTxtCheckNumber = mDialogView.findViewById(R.id.txtCheckNumber);
        mTxtDateTime = mDialogView.findViewById(R.id.txtDateTime);
        mTxtTrk = mDialogView.findViewById(R.id.txtTrk);
        mTxtFuel = mDialogView.findViewById(R.id.txtFuel);
        mTxtCount = mDialogView.findViewById(R.id.txtCount);
        mTxtPrice = mDialogView.findViewById(R.id.txtPrice);
        mTxtCost = mDialogView.findViewById(R.id.txtCost);
        mTxtDistanceReserve = mDialogView.findViewById(R.id.txtDistanceReserve);
        mTxtFuelConsumptionAvg = mDialogView.findViewById(R.id.txtFuelConsumptionAvg);
        mTxtOdometer = mDialogView.findViewById(R.id.txtOdometer);
        mTxtDistance = mDialogView.findViewById(R.id.txtDistance);
        mTxtFuelConsumption = mDialogView.findViewById(R.id.txtFuelConsumption);
        mTxtTimeDelta = mDialogView.findViewById(R.id.txtTimeDelta);
    }

    @Override
    protected void setData() {
        mTxtFuelStation.setText(mRefuel.getFuelStation().toString());
        mTxtCheckNumber.setText(String.valueOf(mRefuel.getCheckNumber()));
        mTxtDateTime.setText(String.valueOf(mRefuel.getDateTime()));
        mTxtTrk.setText(String.valueOf(mRefuel.getTrk()));
        mTxtFuel.setText(mRefuel.getFuel().getName());
        mTxtCount.setText(String.valueOf(mRefuel.getCount()));
        mTxtPrice.setText(String.valueOf(mRefuel.getPrice()));
        mTxtCost.setText(String.valueOf(mRefuel.getCost()));
        mTxtDistanceReserve.setText(String.valueOf(mRefuel.getDistanceReserve()));
        mTxtFuelConsumptionAvg.setText(String.valueOf(mRefuel.getFuelConsumptionAvg()));
        mTxtOdometer.setText(String.valueOf(mRefuel.getOdometer()));
        mTxtDistance.setText(String.valueOf(mRefuel.getDistance()));
        mTxtFuelConsumption.setText(String.valueOf(mRefuel.getFuelConsumption()));
        mTxtTimeDelta.setText(mRefuel.getTimedelta());
    }

    @Override
    protected String getDialogMessageText() {
        return "Your refuel";
    }

    @Override
    protected void deleteObject() {
        RefuelsFragment callingFragment = (RefuelsFragment) getActivity().getSupportFragmentManager().findFragmentByTag("refuels");
        callingFragment.deleteRefuel(mRefuel);
    }
}