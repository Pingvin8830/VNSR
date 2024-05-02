package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.travels.models.TollRoad;

public class ShowTollRoadDialog extends ShowObjectDialog {
    private final TollRoad mTollRoad;
    private TextView mTxtTravel, mTxtName, mTxtStart, mTxtEnd, mTxtPrice;

    public ShowTollRoadDialog(TollRoad tollRoad) {
        mTollRoad = tollRoad;
    }

    @Override
    protected int getLayoutCode() { return R.layout.travels_show_toll_road; }

    @Override
    protected void getDataViews() {
        mTxtTravel = mDialogView.findViewById(R.id.txtTravel);
        mTxtName = mDialogView.findViewById(R.id.txtName);
        mTxtStart = mDialogView.findViewById(R.id.txtStart);
        mTxtEnd = mDialogView.findViewById(R.id.txtEnd);
        mTxtPrice = mDialogView.findViewById(R.id.txtPrice);
    }

    @Override
    protected void setData() {
        mTxtTravel.setText(mTollRoad.getTravel().getName());
        mTxtName.setText(mTollRoad.getName());
        mTxtStart.setText(mTollRoad.getStart());
        mTxtEnd.setText(mTollRoad.getEnd());
        mTxtPrice.setText(String.valueOf(mTollRoad.getPrice()));
    }

    @Override
    protected String getDialogMessageText() {
        return getResources().getString(R.string.lbl_show_toll_road);
    }

    @Override
    protected void deleteObject() {
        TollRoadsFragment callingFragment = (TollRoadsFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("toll_roads");
        assert callingFragment != null;
        callingFragment.deleteTollRoad(mTollRoad);
    }
}