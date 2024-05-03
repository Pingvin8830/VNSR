package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.NewObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Point;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Travel;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Way;

public class NewWayDialog extends NewObjectDialog {
    private Spinner mSpnTravel;
    private Spinner mSpnStartPoint;
    private Spinner mSpnTargetPoint;
    private EditText mEdtxtDistance;
    private List<Travel> mTravels;
    private List<Point> mPoints;
    private Travel mTravel;
    private Point mStartPoint;
    private Point mTargetPoint;
    private int mDistance;

    @Override
    protected void setObjectsLists() {
        mTravels = MainActivity.TravelDao.getAll();
        mPoints = MainActivity.PointDao.getAll();
    }

    @Override
    protected int getLayoutCode() { return R.layout.travels_new_way; }

    @Override
    protected void getDataViews() {
        mSpnTravel = mDialogView.findViewById(R.id.spnTravel);
        mSpnStartPoint = mDialogView.findViewById(R.id.spnStartPoint);
        mSpnTargetPoint = mDialogView.findViewById(R.id.spnTargetPoint);
        mEdtxtDistance = mDialogView.findViewById(R.id.edtxtDistance);
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<Travel> travelAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mTravels);
        ArrayAdapter<Point> startPointsAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mPoints);
        ArrayAdapter<Point> targetPointsAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mPoints);

        travelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startPointsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        targetPointsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpnTravel.setAdapter(travelAdapter);
        mSpnStartPoint.setAdapter(startPointsAdapter);
        mSpnTargetPoint.setAdapter(targetPointsAdapter);
    }

    @Override
    protected String getDialogMessageText() { return getResources().getString(R.string.lbl_new_way); }

    @Override
    protected void setData() {
        mTravel = mTravels.get(mSpnTravel.getSelectedItemPosition());
        mStartPoint = mPoints.get(mSpnStartPoint.getSelectedItemPosition());
        mTargetPoint = mPoints.get(mSpnTargetPoint.getSelectedItemPosition());
        try { mDistance = Integer.parseInt(mEdtxtDistance.getText().toString()); }
        catch (NumberFormatException e) { mDistance = 0; }
    }

    @Override
    protected String getErrorText() {
        String error = "";
        if (mStartPoint.equals(mTargetPoint)) { error = getResources().getString(R.string.err_bad_points); }
        return error;
    }

    @Override
    protected void createObject() {
        Way newWay = new Way(mTravel, mStartPoint, mTargetPoint, mDistance);
        WaysFragment callingFragment = (WaysFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("ways");
        assert callingFragment != null;
        callingFragment.createNewWay(newWay);
    }
}
