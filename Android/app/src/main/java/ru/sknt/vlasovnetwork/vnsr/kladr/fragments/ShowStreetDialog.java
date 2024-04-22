package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Street;

public class ShowStreetDialog extends ShowObjectDialog {
    private final Street mStreet;
    private TextView mTxtName;
    private TextView mTxtType;

    public ShowStreetDialog (Street street) { mStreet = street; }

    @Override
    protected int getLayoutCode() {
        return R.layout.kladr_show_street;
    }

    @Override
    protected void getDataViews() {
        mTxtName = mDialogView.findViewById(R.id.txtName);
        mTxtType = mDialogView.findViewById(R.id.txtType);
    }

    @Override
    protected void setData() {
        mTxtName.setText(mStreet.getName());
        mTxtType.setText(mStreet.getStreetType().getName());
    }

    @Override
    protected String getDialogMessageText() {
        return getResources().getString(R.string.lbl_show_street);
    }

    @Override
    protected void deleteObject() {
        StreetsFragment callingFragment = (StreetsFragment) getActivity().getSupportFragmentManager().findFragmentByTag("streets");
        callingFragment.deleteStreet(mStreet);
    }
}