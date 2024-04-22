package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;

public class ShowStreetTypeDialog extends ShowObjectDialog {
    private final StreetType mStreetType;
    private TextView mTxtName;
    private TextView mTxtShort;

    public ShowStreetTypeDialog (StreetType streetType) { mStreetType = streetType; }

    @Override
    protected int getLayoutCode() {
        return R.layout.kladr_show_street_type;
    }

    @Override
    protected void getDataViews() {
        mTxtName = mDialogView.findViewById(R.id.txtName);
        mTxtShort = mDialogView.findViewById(R.id.txtShort);
    }

    @Override
    protected void setData() {
        mTxtName.setText(mStreetType.getName());
        mTxtShort.setText(mStreetType.getShort());
    }

    @Override
    protected String getDialogMessageText() { return getResources().getString(R.string.lbl_show_street_type); }

    @Override
    protected void deleteObject() {
        StreetTypesFragment callingFragment = (StreetTypesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("street_types");
        callingFragment.deleteStreetType(mStreetType);
    }
}