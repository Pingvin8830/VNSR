package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.widget.EditText;

import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.StreetType;

public class NewStreetTypeDialog extends NewObjectDialog {
    private EditText mEdtxtName;
    private EditText mEdtxtShort;
    private String mName;
    private String mShort;

    @Override
    protected void setObjectsLists() {}

    @Override
    protected int getLayoutCode() {
        return R.layout.kladr_new_street_type;
    }

    @Override
    protected void getDataViews() {
        mEdtxtName = mDialogView.findViewById(R.id.edtxtName);
        mEdtxtShort = mDialogView.findViewById(R.id.edtxtShort);
    }

    @Override
    protected void setAdapters() {}

    @Override
    protected String getDialogMessageText() {
        return "Add a new street type";
    }

    @Override
    protected void setData() {
        mName = mEdtxtName.getText().toString();
        mShort = mEdtxtShort.getText().toString();
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if ((mShort.length() > 3) || (mShort.isEmpty())) { error = "Bad short name"; }
        else if (mName.isEmpty()) { error = "Bad name"; }
        return error;
    }

    @Override
    protected void createObject() {
        StreetType streetType = new StreetType(mName, mShort);
        StreetTypesFragment callingFragment = (StreetTypesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("street_types");
        callingFragment.createNewStreetType(streetType);
    }
}