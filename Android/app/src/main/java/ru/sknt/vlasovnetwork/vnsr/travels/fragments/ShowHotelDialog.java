package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Hotel;

public class ShowHotelDialog extends ShowObjectDialog {
    private final Hotel mHotel;
    private TextView mTxtName, mTxtAddress, mTxtArrivalDateTime, mTxtDepartureDateTime, mTxtState;

    public ShowHotelDialog(Hotel hotel) {
        mHotel = hotel;
    }

    @Override
    protected int getLayoutCode() { return R.layout.travels_show_hotel; }

    @Override
    protected void getDataViews() {
        mTxtName = mDialogView.findViewById(R.id.txtName);
        mTxtAddress = mDialogView.findViewById(R.id.txtAddress);
        mTxtArrivalDateTime = mDialogView.findViewById(R.id.txtArrivalDateTime);
        mTxtDepartureDateTime = mDialogView.findViewById(R.id.txtDepartureDateTime);
        mTxtState = mDialogView.findViewById(R.id.txtState);
    }

    @Override
    protected void setData() {
        mTxtName.setText(mHotel.getName());
        mTxtAddress.setText(mHotel.getAddress().getFull());
        mTxtArrivalDateTime.setText(mHotel.getArrival().toString());
        mTxtDepartureDateTime.setText(mHotel.getDeparture().toString());
        mTxtState.setText(mHotel.getState());
    }

    @Override
    protected String getDialogMessageText() {
        return getResources().getString(R.string.lbl_show_hotel);
    }

    @Override
    protected void deleteObject() {
        HotelsFragment callingFragment = (HotelsFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("hotels");
        assert callingFragment != null;
        callingFragment.deleteHotel(mHotel);
    }
}