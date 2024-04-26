package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import ru.sknt.vlasovnetwork.vnsr.MainActivity;
import ru.sknt.vlasovnetwork.vnsr.R;
import ru.sknt.vlasovnetwork.vnsr.ShowObjectDialog;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Point;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Travel;

public class ShowTravelDialog extends ShowObjectDialog {
    private final Travel mTravel;
    private TextView mTxtTravelName, mTxtTravelState, mTxtBeginning, mTxtFinishing, mTxtParticipants, mTxtTravelCost, mTxtFuelConsumption, mTxtFuelPrice;
    private TextView mTxtWayDistanceSum, mTxtWayFuelSum, mTxtWayFuelCostSum;
    private TextView mTxtToolRoadsCostSum;
    private TextView mTxtHotelsCostSum;
    private TableLayout mTblPoints, mTblWays, mTblToolRoads, mTblHotels;
    private TableRow mTrPointsHeader, mTrPointsSum, mTrWaysHeader, mTrWaysSum, mTrToolRoadsHeader, mTrToolRoadsSum, mTrHotelsHeader, mTrHotelsSum;
    private List<Point> mPoints;
    private final TableRow.LayoutParams mFirstLayoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private final TableRow.LayoutParams mSecondLayoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    int mMargin;

    public ShowTravelDialog(Travel travel) {
        mTravel = travel;
        mPoints = MainActivity.PointDao.filter(travel.getId());
    }

    @Override
    protected int getLayoutCode() { return R.layout.travels_show_travel; }

    @Override
    protected void getDataViews() {
        mTxtTravelName = mDialogView.findViewById(R.id.txtTravelName);
        mTxtTravelState = mDialogView.findViewById(R.id.txtTravelState);
        mTxtBeginning = mDialogView.findViewById(R.id.txtBeginning);
        mTxtFinishing = mDialogView.findViewById(R.id.txtFinishing);
        mTxtParticipants = mDialogView.findViewById(R.id.txtParticipants);
        mTxtTravelCost = mDialogView.findViewById(R.id.txtTravelCost);
        mTxtFuelConsumption = mDialogView.findViewById(R.id.txtFuelConsumption);
        mTxtFuelPrice = mDialogView.findViewById(R.id.txtFuelPrice);
        mTxtWayDistanceSum = mDialogView.findViewById(R.id.txtWayDistanceSum);
        mTxtWayFuelSum = mDialogView.findViewById(R.id.txtWayFuelSum);
        mTxtWayFuelCostSum = mDialogView.findViewById(R.id.txtWayFuelCostSum);
        mTxtToolRoadsCostSum = mDialogView.findViewById(R.id.txtToolRoadCostSum);
        mTxtHotelsCostSum = mDialogView.findViewById(R.id.txtHotelCostSum);

        mTblPoints = mDialogView.findViewById(R.id.tblPoints);
        mTblWays = mDialogView.findViewById(R.id.tblWays);
        mTblToolRoads = mDialogView.findViewById(R.id.tblToolRoads);
        mTblHotels = mDialogView.findViewById(R.id.tblHotels);

        mTrPointsHeader = mDialogView.findViewById(R.id.trPointsHeader);
        mTrPointsSum = mDialogView.findViewById(R.id.trPointsSum);
        mTrWaysHeader = mDialogView.findViewById(R.id.trWaysHeader);
        mTrWaysSum = mDialogView.findViewById(R.id.trWaysSum);
        mTrToolRoadsHeader = mDialogView.findViewById(R.id.trToolRoadsHeader);
        mTrToolRoadsSum = mDialogView.findViewById(R.id.trToolRoadSum);
        mTrHotelsHeader = mDialogView.findViewById(R.id.trHotelsHeader);
        mTrHotelsSum = mDialogView.findViewById(R.id.trHotelSum);

        TextView txtControl = mDialogView.findViewById(R.id.txtPointDateTimeLabel);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) txtControl.getLayoutParams();
        mMargin = marginLayoutParams.getMarginStart();
        mFirstLayoutParams.setMargins(mMargin, 0, mMargin, 0);
        mSecondLayoutParams.setMargins(0, 0, mMargin, 0);
    }

    @Override
    protected void setData() {
        mTxtTravelName.setText(mTravel.getName());
        mTxtTravelState.setText(mTravel.getState().getName());
        try { mTxtBeginning.setText(mTravel.getStartDateTime().toString()); }
        catch (NullPointerException e) { mTxtBeginning.setText(R.string.lbl_unknown); }
        try { mTxtFinishing.setText(mTravel.getEndDateTime().toString()); }
        catch (NullPointerException e) { mTxtFinishing.setText(R.string.lbl_unknown); }
        mTxtParticipants.setText(mTravel.getParticipants());
//        mTxtTravelCost = mDialogView.findViewById(R.id.txtTravelCost);
        mTxtFuelConsumption.setText(String.valueOf(mTravel.getFuelConsumption()));
        mTxtFuelPrice.setText(String.valueOf(mTravel.getFuelPrice()));
//        mTxtWayDistanceSum = mDialogView.findViewById(R.id.txtWayDistanceSum);
//        mTxtWayFuelSum = mDialogView.findViewById(R.id.txtWayFuelSum);
//        mTxtWayFuelCostSum = mDialogView.findViewById(R.id.txtWayFuelCostSum);
//        mTxtToolRoadsCostSum = mDialogView.findViewById(R.id.txtToolRoadCostSum);
//        mTxtHotelsCostSum = mDialogView.findViewById(R.id.txtHotelCostSum);

//        mTrPointsHeader = mDialogView.findViewById(R.id.trPointsHeader);
        mTrPointsSum = mDialogView.findViewById(R.id.trPointsSum);
//        mTrWaysHeader = mDialogView.findViewById(R.id.trWaysHeader);
//        mTrWaysSum = mDialogView.findViewById(R.id.trWaysSum);
//        mTrToolRoadsHeader = mDialogView.findViewById(R.id.trToolRoadsHeader);
//        mTrToolRoadsSum = mDialogView.findViewById(R.id.trToolRoadSum);
//        mTrHotelsHeader = mDialogView.findViewById(R.id.trHotelsHeader);
//        mTrHotelsSum = mDialogView.findViewById(R.id.trHotelSum);

        mTblPoints = mDialogView.findViewById(R.id.tblPoints);
//        mTblWays = mDialogView.findViewById(R.id.tblWays);
//        mTblToolRoads = mDialogView.findViewById(R.id.tblToolRoads);
//        mTblHotels = mDialogView.findViewById(R.id.tblHotels);

        createPointsTable();
    }

    @Override
    protected String getDialogMessageText() {
        return getResources().getString(R.string.lbl_show_travel);
    }

    @Override
    protected void deleteObject() {
        TravelsFragment callingFragment = (TravelsFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("travels");
        assert callingFragment != null;
        callingFragment.deleteTravel(mTravel);
    }

    private void createPointsTable() {
        mTblPoints.removeView(mTrPointsSum);

        for (Point point : mPoints) {
            TableRow tableRow = new TableRow(requireContext());

            TextView txtPointDateTime = new TextView(requireContext());
            TextView txtPointCity = new TextView(requireContext());
            TextView txtPointName = new TextView(requireContext());
            TextView txtPointDoing = new TextView(requireContext());

            txtPointDateTime.setText(point.getDateTime().toString());
            txtPointCity.setText(point.getAddress().getCity().toString());
            txtPointName.setText(point.getAddress().getName());
            txtPointDoing.setText(point.getDoing());

            txtPointDateTime.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
            txtPointCity.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
            txtPointName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
            txtPointDoing.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);

            txtPointDateTime.setBackgroundColor(Color.WHITE);
            txtPointCity.setBackgroundColor(Color.WHITE);
            txtPointName.setBackgroundColor(Color.WHITE);
            txtPointDoing.setBackgroundColor(Color.WHITE);

            txtPointDateTime.setLayoutParams(mFirstLayoutParams);
            txtPointCity.setLayoutParams(mSecondLayoutParams);
            txtPointName.setLayoutParams(mSecondLayoutParams);
            txtPointDoing.setLayoutParams(mSecondLayoutParams);

            tableRow.addView(txtPointDateTime);
            tableRow.addView(txtPointCity);
            tableRow.addView(txtPointName);
            tableRow.addView(txtPointDoing);

            mTblPoints.addView(tableRow);
        }
        mTblPoints.addView(mTrPointsSum);
    }
}