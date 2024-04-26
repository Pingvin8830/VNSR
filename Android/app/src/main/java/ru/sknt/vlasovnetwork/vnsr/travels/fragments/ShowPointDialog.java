package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Point;

public class ShowPointDialog extends ShowObjectDialog {
    private final Point mPoint;
    private TextView mTxtTravel;
    private TextView mTxtAddress;
    private TextView mTxtDateTime;
    private TextView mTxtDoing;
    private TextView mTxtOdometer;

    public ShowPointDialog(Point point) {
        mPoint = point;
    }

    @Override
    protected int getLayoutCode() { return R.layout.travels_show_point; }

    @Override
    protected void getDataViews() {
        mTxtTravel = mDialogView.findViewById(R.id.txtTravel);
        mTxtAddress = mDialogView.findViewById(R.id.txtAddress);
        mTxtDateTime = mDialogView.findViewById(R.id.txtDateTime);
        mTxtDoing = mDialogView.findViewById(R.id.txtDoing);
        mTxtOdometer = mDialogView.findViewById(R.id.txtOdometer);
    }

    @Override
    protected void setData() {
        mTxtTravel.setText(mPoint.getTravel().getName());
        mTxtAddress.setText(mPoint.getAddress().getName());
        mTxtDateTime.setText(mPoint.getDateTime().toString());
        mTxtDoing.setText(mPoint.getDoing());
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