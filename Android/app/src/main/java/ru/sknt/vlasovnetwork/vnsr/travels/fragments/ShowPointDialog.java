package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Point;

public class ShowPointDialog extends ShowObjectDialog {
    private final Point mPoint;
    private TextView mTxtAddress;
    private TextView mTxtArrivalDateTime;
    private TextView mTxtDepartureDateTime;
    private TextView mTxtDoing;
    private TextView mTxtOdometer;

    public ShowPointDialog(Point point) {
        mPoint = point;
    }

    @Override
    protected int getLayoutCode() { return R.layout.travels_show_point; }

    @Override
    protected void getDataViews() {
        mTxtAddress = mDialogView.findViewById(R.id.txtAddress);
        mTxtArrivalDateTime = mDialogView.findViewById(R.id.txtArrivalDateTime);
        mTxtDepartureDateTime = mDialogView.findViewById(R.id.txtDepartureDateTime);
        mTxtDoing = mDialogView.findViewById(R.id.txtDoing);
        mTxtOdometer = mDialogView.findViewById(R.id.txtOdometer);
    }

    @Override
    protected void setData() {
        mTxtAddress.setText(mPoint.getAddress().getName());
        if (mPoint.getArrivalDateTime() != null) { mTxtArrivalDateTime.setText(mPoint.getArrivalDateTime().toString()); }
        else { mTxtArrivalDateTime.setText("-"); }
        if (mPoint.getDepartureDateTime() != null) { mTxtDepartureDateTime.setText(mPoint.getDepartureDateTime().toString()); }
        else { mTxtDepartureDateTime.setText("-"); }
        mTxtDoing.setText(mPoint.getDoingText());
        mTxtOdometer.setText(String.valueOf(mPoint.getOdometer()));
    }

    @Override
    protected String getDialogMessageText() {
        return getResources().getString(R.string.lbl_show_point);
    }

    @Override
    protected void deleteObject() {
        PointsFragment callingFragment = (PointsFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("points");
        assert callingFragment != null;
        callingFragment.deletePoint(mPoint);
    }
}