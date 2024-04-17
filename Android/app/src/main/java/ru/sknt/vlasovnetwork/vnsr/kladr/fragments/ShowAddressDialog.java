package ru.sknt.vlasovnetwork.vnsr.kladr.fragments;

import android.widget.TextView;

import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.kladr.models.Address;

public class ShowAddressDialog extends ShowObjectDialog {
    private final Address mAddress;
    private TextView mTxtName;
    private TextView mTxtRegion;
    private TextView mTxtCity;
    private TextView mTxtStreet;
    private TextView mTxtHome;
    private TextView mTxtBuilding;
    private TextView mTxtFlat;

    public ShowAddressDialog(Address address) {
        mAddress = address;
    }

    @Override
    protected int getLayoutCode() {
        return R.layout.kladr_show_address;
    }

    @Override
    protected void getDataViews() {
        mTxtName = mDialogView.findViewById(R.id.txtName);
        mTxtRegion = mDialogView.findViewById(R.id.txtRegion);
        mTxtCity = mDialogView.findViewById(R.id.txtCity);
        mTxtStreet = mDialogView.findViewById(R.id.txtStreet);
        mTxtHome = mDialogView.findViewById(R.id.txtHome);
        mTxtBuilding = mDialogView.findViewById(R.id.txtBuilding);
        mTxtFlat = mDialogView.findViewById(R.id.txtFlat);
    }

    @Override
    protected void setData() {
        mTxtName.setText(mAddress.getName());
        mTxtRegion.setText(mAddress.getRegion().toString());
        mTxtCity.setText(mAddress.getCity().toString());
        mTxtStreet.setText(mAddress.getStreet().toString());
        mTxtHome.setText(mAddress.getHouse());
        mTxtBuilding.setText(mAddress.getBuilding());
        mTxtFlat.setText(String.valueOf(mAddress.getFlat()));
    }

    @Override
    protected String getDialogMessageText() {
        return "Your address";
    }

    @Override
    protected void deleteObject() {
        AddressesFragment callingFragment = (AddressesFragment) getActivity().getSupportFragmentManager().findFragmentByTag("addresses");
        callingFragment.deleteAddress(mAddress);
    }
}