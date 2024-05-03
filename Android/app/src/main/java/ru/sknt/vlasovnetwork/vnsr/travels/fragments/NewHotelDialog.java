package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

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
import ru.sknt.vlasovnetwork.vnsr.travels.models.Hotel;

public class NewHotelDialog extends NewObjectDialog {
    private EditText mEdtxtName, mEdtxtState, mEdtxtCost;
    private Spinner mSpnAddress;
    private Button mBttnArrivalDateTime, mBttnDepartureDateTime;
    private List<Address> mAddresses;
    private String mName, mState;
    private Address mAddress;
    private FormatedDate mArrivalDateTime, mDepartureDateTime;
    private float mCost;

    @Override
    protected void setObjectsLists() { mAddresses = MainActivity.AddressDao.getAll(); }

    @Override
    protected int getLayoutCode() { return R.layout.travels_new_hotel; }

    @Override
    protected void getDataViews() {
        mEdtxtName = mDialogView.findViewById(R.id.edtxtName);
        mSpnAddress = mDialogView.findViewById(R.id.spnAddress);
        mBttnArrivalDateTime = mDialogView.findViewById(R.id.bttnArrivalDateTime);
        mBttnDepartureDateTime = mDialogView.findViewById(R.id.bttnDepartureDateTime);
        mEdtxtCost = mDialogView.findViewById(R.id.edtxtCost);
        mEdtxtState = mDialogView.findViewById(R.id.edtxtState);

        mBttnArrivalDateTime.setOnClickListener(this);
        mBttnDepartureDateTime.setOnClickListener(this);
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<Address> addressAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mAddresses);
        addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnAddress.setAdapter(addressAdapter);
    }

    @Override
    protected String getDialogMessageText() { return getResources().getString(R.string.lbl_new_hotel); }

    @Override
    protected void setData() {
        mName = mEdtxtName.getText().toString();
        mAddress = mAddresses.get(mSpnAddress.getSelectedItemPosition());
        mState = mEdtxtState.getText().toString();
        try { mCost = Float.parseFloat(mEdtxtCost.getText().toString()); }
        catch (NumberFormatException e) { mCost = 0f; }
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if      (mName.isEmpty()) { error = getResources().getString(R.string.err_bad_name); }
        else if (mArrivalDateTime == null) { error = getResources().getString(R.string.err_bad_date_time); }
        else if (mDepartureDateTime == null) { error = getResources().getString(R.string.err_bad_date_time); }
        else if (mCost < 0.01f) { error = getResources().getString(R.string.err_bad_cost); }
        return error;
    }

    @Override
    protected void createObject() {
        Hotel newHotel = new Hotel(mName, mAddress, mArrivalDateTime, mDepartureDateTime, mCost, mState);
        HotelsFragment callingFragment = (HotelsFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("hotels");
        assert callingFragment != null;
        callingFragment.createNewHotel(newHotel);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.bttnArrivalDateTime) {
            new DatePickerFragment("new_hotel_arrival").show(requireActivity().getSupportFragmentManager(), "datePicker");
        } else if (view.getId() == R.id.bttnDepartureDateTime) {
            new DatePickerFragment("new_hotel_departure").show(requireActivity().getSupportFragmentManager(), "datePicker");
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
