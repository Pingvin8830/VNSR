package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.DatePickerFragment;
import ru.sknt.vlasovnetwork.vnsr.FormatedDate;
import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Point;

public class NewPointDialog extends NewObjectDialog {
    private Spinner mSpnAddress;
    private Button mBttnArrivalDateTime;
    private Button mBttnDepartureDateTime;
    private Spinner mSpnDoing;
    private EditText mEdtxtOdometer;
    private List<Address> mAddresses;
    private List<String> mDoings;
    private List<String> mDoingCodes;
    private Address mAddress;
    private FormatedDate mArrivalDateTime;
    private FormatedDate mDepartureDateTime;
    private String mDoing;
    private int mOdometer;

    @Override
    protected void setObjectsLists() {
        mAddresses = MainActivity.AddressDao.getAll();
        mDoingCodes = new ArrayList<>();
        mDoings = new ArrayList<>();
        for (String[] doingsList : Point.DOINGS) {
            if (doingsList[0].equals("U")) { continue; }
            mDoingCodes.add(doingsList[0]);
            mDoings.add(doingsList[1]);
        }
    }

    @Override
    protected int getLayoutCode() { return R.layout.travels_new_point; }

    @Override
    protected void getDataViews() {
        mSpnAddress = mDialogView.findViewById(R.id.spnAddress);
        mBttnArrivalDateTime = mDialogView.findViewById(R.id.bttnArrivalDateTime);
        mBttnDepartureDateTime = mDialogView.findViewById(R.id.bttnDepartureDateTime);
        mSpnDoing = mDialogView.findViewById(R.id.spnDoing);
        mEdtxtOdometer = mDialogView.findViewById(R.id.edtxtOdometer);

        mBttnArrivalDateTime.setOnClickListener(this);
        mBttnDepartureDateTime.setOnClickListener(this);
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<Address> addressAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mAddresses);
        ArrayAdapter<String> doingAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mDoings);

        addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpnAddress.setAdapter(addressAdapter);
        mSpnDoing.setAdapter(doingAdapter);
    }

    @Override
    protected String getDialogMessageText() { return getResources().getString(R.string.lbl_new_point); }

    @Override
    protected void setData() {
        mAddress = mAddresses.get(mSpnAddress.getSelectedItemPosition());
        mDoing = mDoingCodes.get(mSpnDoing.getSelectedItemPosition());
        try { mOdometer = Integer.parseInt(mEdtxtOdometer.getText().toString()); }
        catch (NumberFormatException e) { mOdometer = 0; }
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if      (mArrivalDateTime == null && mDepartureDateTime == null) { error = getResources().getString(R.string.err_bad_date_time); }
        else if (mOdometer < 1) { error = getResources().getString(R.string.err_bad_odometer); }
        return error;
    }

    @Override
    protected void createObject() {
        Point newPoint = new Point(mAddress, mArrivalDateTime, mDepartureDateTime, mDoing, mOdometer);
        PointsFragment callingFragment = (PointsFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("points");
        assert callingFragment != null;
        callingFragment.createNewPoint(newPoint);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.bttnArrivalDateTime) {
            new DatePickerFragment("new_point_arrival").show(requireActivity().getSupportFragmentManager(), "datePicker");
        } else if (view.getId() == R.id.bttnDepartureDateTime) {
            new DatePickerFragment("new_point_departure").show(requireActivity().getSupportFragmentManager(), "datePicker");
        }
    }

    public void setArrivalDateTime(FormatedDate arrivalDateTime) {
        mArrivalDateTime = arrivalDateTime;
        mBttnArrivalDateTime.setText(mArrivalDateTime.toString());
    }
    public void setDepartureDateTime(FormatedDate departureDateTime) {
        mDepartureDateTime = departureDateTime;
        mBttnDepartureDateTime.setText(mDepartureDateTime.toString());
    }
}
