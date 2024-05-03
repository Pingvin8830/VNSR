package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.models.TollRoad;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Travel;

public class NewTollRoadDialog extends NewObjectDialog {
    private Spinner mSpnTravel;
    private EditText mEdtxtName, mEdtxtStart, mEdtxtEnd, mEdtxtPrice;
    private List<Travel> mTravels;
    private Travel mTravel;
    private String mName, mStart, mEnd;
    private float mPrice;

    @Override
    protected void setObjectsLists() {
        mTravels = MainActivity.TravelDao.getAll();
    }

    @Override
    protected int getLayoutCode() { return R.layout.travels_new_toll_road; }

    @Override
    protected void getDataViews() {
        mSpnTravel = mDialogView.findViewById(R.id.spnTravel);
        mEdtxtName = mDialogView.findViewById(R.id.edtxtName);
        mEdtxtStart = mDialogView.findViewById(R.id.edtxtStart);
        mEdtxtEnd = mDialogView.findViewById(R.id.edtxtEnd);
        mEdtxtPrice = mDialogView.findViewById(R.id.edtxtPrice);
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<Travel> travelAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mTravels);
        travelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnTravel.setAdapter(travelAdapter);
    }

    @Override
    protected String getDialogMessageText() { return getResources().getString(R.string.lbl_new_toll_road); }

    @Override
    protected void setData() {
        mTravel = mTravels.get(mSpnTravel.getSelectedItemPosition());
        mName = mEdtxtName.getText().toString();
        mStart = mEdtxtStart.getText().toString();
        mEnd = mEdtxtEnd.getText().toString();
        try { mPrice = Float.parseFloat(mEdtxtPrice.getText().toString()); }
        catch (NumberFormatException e) { mPrice = 0f; }
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if      (mName.isEmpty()) { error = getResources().getString(R.string.err_bad_name); }
        else if (mStart.isEmpty()) { error = getResources().getString(R.string.err_bad_start); }
        else if (mEnd.isEmpty()) { error = getResources().getString(R.string.err_bad_end); }
        else if (mPrice < 0.01f) { error = getResources().getString(R.string.err_bad_price); }
        return error;
    }

    @Override
    protected void createObject() {
        TollRoad newTollRoad = new TollRoad(mTravel, mName, mStart, mEnd, mPrice);
        TollRoadsFragment callingFragment = (TollRoadsFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("toll_roads");
        assert callingFragment != null;
        callingFragment.createNewTollRoad(newTollRoad);
    }
}
