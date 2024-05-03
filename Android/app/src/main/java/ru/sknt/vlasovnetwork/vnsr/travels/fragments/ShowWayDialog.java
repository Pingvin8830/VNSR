package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Way;

public class ShowWayDialog extends ShowObjectDialog {
    private final Way mWay;
    private TextView mTxtTravel;
    private TextView mTxtStartPoint;
    private TextView mTxtTargetPoint;
    private TextView mTxtDistance;

    public ShowWayDialog(Way way) {
        mWay = way;
    }

    @Override
    protected int getLayoutCode() { return R.layout.travels_show_way; }

    @Override
    protected void getDataViews() {
        mTxtTravel = mDialogView.findViewById(R.id.txtTravel);
        mTxtStartPoint = mDialogView.findViewById(R.id.txtStartPoint);
        mTxtTargetPoint = mDialogView.findViewById(R.id.txtTargetPoint);
        mTxtDistance = mDialogView.findViewById(R.id.txtDistance);
    }

    @Override
    protected void setData() {
        mTxtTravel.setText(mWay.getTravel().getName());
        mTxtStartPoint.setText(mWay.getStartPoint().getAddress().getName());
        mTxtTargetPoint.setText(mWay.getTargetPoint().getAddress().getName());
        mTxtDistance.setText(String.valueOf(mWay.getDistance()));
    }

    @Override
    protected String getDialogMessageText() {
        return getResources().getString(R.string.lbl_show_way);
    }

    @Override
    protected void deleteObject() {
        WaysFragment callingFragment = (WaysFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("ways");
        assert callingFragment != null;
        callingFragment.deleteWay(mWay);
    }
}