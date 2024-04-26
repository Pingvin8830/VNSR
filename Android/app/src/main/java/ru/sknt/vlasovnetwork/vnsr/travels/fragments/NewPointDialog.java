package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.util.Log;
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
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Point;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Travel;

public class NewPointDialog extends NewObjectDialog {
    private Spinner mSpnTravel;
    private Button mBttnDateTime;
    private Spinner mSpnAddress;
    private EditText mEdtxtDoing;
    private EditText mEdtxtOdometer;
    private List<Travel> mTravels;
    private List<Address> mAddresses;
    private Travel mTravel;
    private final FormatedDate mDateTime = new FormatedDate(0L);
    private Address mAddress;
    private String mDoing;
    private int mOdometer;

    public void setYear(int year) {

        Log.i("VNSR DEBUG", "year: " + year);

        mDateTime.setYear(year); }
    public void setMonth(int month) { mDateTime.setMonth(month); }
    public void setDay(int day) { mDateTime.setDate(day); }
    public void setHour(int hour) { mDateTime.setHours(hour); }
    public void setMinute(int minute) { mDateTime.setMinutes(minute); mDateTime.setSeconds(0); }
    public void setDateTimeButtonText() {
        mBttnDateTime.setText(mDateTime.toString());
    }

    @Override
    protected void setObjectsLists() {
        mTravels = MainActivity.TravelDao.getAll();
        mAddresses = MainActivity.AddressDao.getAll();
    }

    @Override
    protected int getLayoutCode() { return R.layout.travels_new_point; }

    @Override
    protected void getDataViews() {
        mSpnTravel = mDialogView.findViewById(R.id.spnTravel);
        mBttnDateTime = mDialogView.findViewById(R.id.bttnDateTime);
        mSpnAddress = mDialogView.findViewById(R.id.spnAddress);
        mEdtxtDoing = mDialogView.findViewById(R.id.edtxtDoing);
        mEdtxtOdometer = mDialogView.findViewById(R.id.edtxtOdometer);
        mBttnDateTime.setOnClickListener(this);
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<Travel> travelAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mTravels);
        ArrayAdapter<Address> addressAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mAddresses);

        travelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpnTravel.setAdapter(travelAdapter);
        mSpnAddress.setAdapter(addressAdapter);
    }

    @Override
    protected String getDialogMessageText() { return getResources().getString(R.string.lbl_new_point); }

    @Override
    protected void setData() {
        mTravel = mTravels.get(mSpnTravel.getSelectedItemPosition());
        mAddress = mAddresses.get(mSpnAddress.getSelectedItemPosition());
        mDoing = mEdtxtDoing.getText().toString();
        try { mOdometer = Integer.parseInt(mEdtxtOdometer.getText().toString()); }
        catch (NumberFormatException e) { mOdometer = 0; }
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if      (mBttnDateTime.getText().toString().equals(getResources().getString(R.string.bttn_case_date_time))) { error = getResources().getString(R.string.err_bad_date_time); }
        else if (mDoing.isEmpty()) { error = getResources().getString(R.string.err_bad_doing); }
        else if (mOdometer < 1) { error = getResources().getString(R.string.err_bad_odometer); }
        return error;
    }

    @Override
    protected void createObject() {
        Point newPoint = new Point(mTravel, mAddress, mDateTime, mDoing, mOdometer);
        PointsFragment callingFragment = (PointsFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("points");
        assert callingFragment != null;
        callingFragment.createNewPoint(newPoint);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.bttnDateTime) {
            new DatePickerFragment("new_point").show(requireActivity().getSupportFragmentManager(), "datePicker");
        }
    }

}
