package ru.sknt.vlasovnetwork.vnsr.car.fragments;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.DatePickerFragment;
import ru.sknt.vlasovnetwork.vnsr.FormatedDate;
import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.car.models.Fuel;
import ru.sknt.vlasovnetwork.vnsr.car.models.FuelStation;
import ru.sknt.vlasovnetwork.vnsr.car.models.Refuel;

public class NewRefuelDialog extends NewObjectDialog {
    private EditText mEdtxtCheckNumber;
    private Button mBttnDateTime;
    private EditText mEdtxtTrk;
    private EditText mEdtxtCount;
    private EditText mEdtxtPrice;
    private EditText mEdtxtCost;
    private Spinner mSpnFuel;
    private Spinner mSpnFuelStation;
    private EditText mEdtxtDistance;
    private EditText mEdtxtDistanceReserve;
    private EditText mEdtxtFuelConsumption;
    private EditText mEdtxtFuelConsumptionAvg;
    private EditText mEdtxtOdometer;
    private EditText mEdtxtTimeDelta;
    private List<Fuel> mFuels;
    private List<FuelStation> mFuelStations;
    private int mCheckNumber;
    private final FormatedDate mDateTime = new FormatedDate(0L);
    private int mTrk;
    private float mCount;
    private float mPrice;
    private float mCost;
    private Fuel mFuel;
    private FuelStation mFuelStation;
    private float mDistance;
    private int mDistanceReserve;
    private float mFuelConsumption;
    private float mFuelConsumptionAvg;
    private int mOdometer;
    private String mTimeDelta;

    public void setYear(int year) { mDateTime.setYear(year); }
    public void setMonth(int month) { mDateTime.setMonth(month); }
    public void setDay(int day) { mDateTime.setDate(day); }
    public void setHour(int hour) { mDateTime.setHours(hour); }
    public void setMinute(int minute) { mDateTime.setMinutes(minute); mDateTime.setSeconds(0); }

    public void setDateTimeButtonText() {
        mBttnDateTime.setText(mDateTime.toString());
    }
    @Override
    protected void setObjectsLists() {
        mFuels = MainActivity.FuelDao.getAll();
        mFuelStations = MainActivity.FuelStationDao.getAll();
    }

    @Override
    protected int getLayoutCode() { return R.layout.car_new_refuel; }

    @Override
    protected void getDataViews() {
        mEdtxtCheckNumber = mDialogView.findViewById(R.id.edtxtCheckNumber);
        mBttnDateTime = mDialogView.findViewById(R.id.bttnDateTime);
        mEdtxtTrk = mDialogView.findViewById(R.id.edtxtTrk);
        mEdtxtCount = mDialogView.findViewById(R.id.edtxtCount);
        mEdtxtPrice = mDialogView.findViewById(R.id.edtxtPrice);
        mEdtxtCost = mDialogView.findViewById(R.id.edtxtCost);
        mSpnFuel = mDialogView.findViewById(R.id.spnFuel);
        mSpnFuelStation = mDialogView.findViewById(R.id.spnFuelStation);
        mEdtxtDistance = mDialogView.findViewById(R.id.edtxtDistance);
        mEdtxtDistanceReserve = mDialogView.findViewById(R.id.edtxtDistanceReserve);
        mEdtxtFuelConsumption = mDialogView.findViewById(R.id.edtxtFuelConsumption);
        mEdtxtFuelConsumptionAvg = mDialogView.findViewById(R.id.edtxtFuelConsumptionAvg);
        mEdtxtOdometer = mDialogView.findViewById(R.id.edtxtOdometer);
        mEdtxtTimeDelta = mDialogView.findViewById(R.id.edtxtTimeDelta);
        mBttnDateTime.setOnClickListener(this);
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<Fuel> fuelAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mFuels);
        ArrayAdapter<FuelStation> fuelStationAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mFuelStations);

        fuelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpnFuel.setAdapter(fuelAdapter);
        mSpnFuelStation.setAdapter(fuelStationAdapter);
    }

    @Override
    protected String getDialogMessageText() { return getResources().getString(R.string.lbl_new_refuel); }

    @Override
    protected void setData() {
        String temp;
        try { mCheckNumber = Integer.parseInt(mEdtxtCheckNumber.getText().toString()); }
        catch (NumberFormatException exception) { mCheckNumber = 0; }

        try { mTrk = Integer.parseInt(mEdtxtTrk.getText().toString()); }
        catch (NumberFormatException exception){ mTrk = 0; }

        temp = mEdtxtCount.getText().toString();
        if (!temp.isEmpty()) { mCount = Float.parseFloat(mEdtxtCount.getText().toString()); }
        else { mCount = 0f; }

        temp = mEdtxtPrice.getText().toString();
        if (!temp.isEmpty()) { mPrice = Float.parseFloat(mEdtxtPrice.getText().toString()); }
        else { mPrice = 0f; }

        temp = mEdtxtCost.getText().toString();
        if (!temp.isEmpty()) { mCost = Float.parseFloat(mEdtxtCost.getText().toString()); }
        else { mCost = 0f; }

        mFuel = mFuels.get(mSpnFuel.getSelectedItemPosition());

        mFuelStation = mFuelStations.get(mSpnFuelStation.getSelectedItemPosition());

        temp = mEdtxtDistance.getText().toString();
        if (!temp.isEmpty()) { mDistance = Float.parseFloat(mEdtxtDistance.getText().toString()); }
        else { mDistance = 0f; }

        try { mDistanceReserve = Integer.parseInt(mEdtxtDistanceReserve.getText().toString()); }
        catch (NumberFormatException exception) { mDistanceReserve = 0; }

        temp = mEdtxtFuelConsumption.getText().toString();
        if (!temp.isEmpty()) { mFuelConsumption = Float.parseFloat(mEdtxtFuelConsumption.getText().toString()); }
        else { mFuelConsumption = 0f; }

        temp = mEdtxtFuelConsumptionAvg.getText().toString();
        if (!temp.isEmpty()) { mFuelConsumptionAvg = Float.parseFloat(mEdtxtFuelConsumptionAvg.getText().toString()); }
        else { mFuelConsumptionAvg = 0f; }

        try { mOdometer = Integer.parseInt(mEdtxtOdometer.getText().toString()); }
        catch (NumberFormatException exception) { mOdometer = 0; }

        mTimeDelta = mEdtxtTimeDelta.getText().toString();
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if (mBttnDateTime.getText().toString().equals(getResources().getString(R.string.bttn_case_date_time))) { error = getResources().getString(R.string.err_bad_date_time); }
        else if (mCount < 0.1f) { error = getResources().getString(R.string.err_bad_count); }
        else if (mPrice < 0.01f) { error = getResources().getString(R.string.err_bad_price); }
        else if ((mCost < (mCount * mPrice) - 1f) || (mCost > (mCount * mPrice) + 1f)) { error = getResources().getString(R.string.err_bad_cost); }
        else if (mTrk < 1) { error = getResources().getString(R.string.err_bad_trk); }
        else if (mDistanceReserve < 1) { error = getResources().getString(R.string.err_bad_distance_reserve); }
        else if (mFuelConsumptionAvg < 0.1f) { error = getResources().getString(R.string.err_bad_fuel_consumption_avg); }
        else if (mOdometer < 1) { error = getResources().getString(R.string.err_bad_odometer); }
        else if (mDistance < 0.1f) { error = getResources().getString(R.string.err_bad_distance); }
        else if (mFuelConsumption < 0.1f) { error = getResources().getString(R.string.err_bad_fuel_consumption); }
        else if (mTimeDelta.isEmpty()) { error = getResources().getString(R.string.err_bad_time_delta); }
        return error;
    }

    @Override
    protected void createObject() {
        Refuel newRefuel = new Refuel(mFuelStation, mCheckNumber, mDateTime, mTrk, mFuel, mCount, mPrice, mCost, mDistanceReserve, mFuelConsumptionAvg, mOdometer, mDistance, mFuelConsumption, mTimeDelta);
        RefuelsFragment callingFragment = (RefuelsFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("refuels");
        assert callingFragment != null;
        callingFragment.createNewRefuel(newRefuel);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.bttnDateTime) {
            new DatePickerFragment("new_refuel").show(requireActivity().getSupportFragmentManager(), "datePicker");
        }
    }
}
