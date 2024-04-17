package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.widget.EditText;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Region;

public class NewRegionDialog extends NewObjectDialog {
    private List<Region> mRegions;
    private EditText mEdtxtName;
    private EditText mEdtxtCode;
    private String mCode;
    private String mName;

    @Override
    protected void setObjectsLists() {
        mRegions = MainActivity.RegionDao.getAll();
    }

    @Override
    protected int getLayoutCode() {
        return R.layout.kladr_new_region;
    }

    @Override
    protected void getDataViews() {
        mEdtxtCode = mDialogView.findViewById(R.id.edtxtCode);
        mEdtxtName = mDialogView.findViewById(R.id.edtxtName);
    }

    @Override
    protected void setAdapters() {}

    @Override
    protected String getDialogMessageText() {
        return "Add a new region";
    }

    @Override
    protected void setData() {
        mCode = mEdtxtCode.getText().toString();
        mName = mEdtxtName.getText().toString();
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if ((mCode.length() > 3) || (mCode.isEmpty())) { error = "Bad code"; }
        else if (mName.isEmpty()) { error = "Bad name"; }
        return error;
    }

    @Override
    protected void createObject() {
        Region region = new Region(mCode, mName);
        RegionsFragment callingFragment = (RegionsFragment) getActivity().getSupportFragmentManager().findFragmentByTag("regions");
        callingFragment.createNewRegion(region);
    }
}