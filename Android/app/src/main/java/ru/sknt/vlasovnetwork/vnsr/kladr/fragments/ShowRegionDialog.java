package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;

public class ShowRegionDialog extends ShowObjectDialog {
    private final Region mRegion;
    private TextView mTxtCode;
    private TextView mTxtName;

    public ShowRegionDialog (Region region) { mRegion = region; }

    @Override
    protected int getLayoutCode() {
        return R.layout.kladr_show_region;
    }

    @Override
    protected void getDataViews() {
        mTxtCode = mDialogView.findViewById(R.id.txtCode);
        mTxtName = mDialogView.findViewById(R.id.txtName);
    }

    @Override
    protected void setData() {
        mTxtCode.setText(mRegion.getCode());
        mTxtName.setText(mRegion.getName());
    }

    @Override
    protected String getDialogMessageText() {
        return "Your region";
    }

    @Override
    protected void deleteObject() {
        RegionsFragment callingFragment = (RegionsFragment) getActivity().getSupportFragmentManager().findFragmentByTag("regions");
        callingFragment.deleteRegion(mRegion);
    }
}