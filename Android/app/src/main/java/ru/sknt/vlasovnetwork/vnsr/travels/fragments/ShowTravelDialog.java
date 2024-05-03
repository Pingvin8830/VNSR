package ru.sknt.vlasovnetwork.vnsr.travels.fragments;

import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
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
import ru.sknt.vlasovnetwork.vnsr.travels.models.Hotel;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Point;
import ru.sknt.vlasovnetwork.vnsr.travels.models.TollRoad;
import ru.sknt.vlasovnetwork.vnsr.travels.models.Travel;

public class ShowTravelDialog extends ShowObjectDialog {
    private final Travel mTravel;
    private List<Point> mPoints;
    private TextView mTxtTravelName, mTxtTravelState, mTxtBeginning, mTxtFinishing, mTxtParticipants, mTxtTravelCost, mTxtFuelConsumption, mTxtFuelPrice;
    private TextView mTxtWayDistanceSum, mTxtWayFuelSum, mTxtWayFuelCostSum;
    private TextView mTxtTollRoadsCostSum;
    private TextView mTxtHotelsCostSum;
    private TableLayout mTblPoints, mTblWays, mTblTollRoads, mTblHotels;
    private TableRow mTrPointsSum, mTrWaysSum, mTrTollRoadsSum, mTrHotelsSum;
    private final TableRow.LayoutParams mFirstLayoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private final TableRow.LayoutParams mSecondLayoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    int mMargin;

    public ShowTravelDialog(Travel travel) {
        mTravel = travel;
        mPoints = travel.getPoints();
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
        mTxtTollRoadsCostSum = mDialogView.findViewById(R.id.txtTollRoadCostSum);
        mTxtHotelsCostSum = mDialogView.findViewById(R.id.txtHotelCostSum);

        mTblPoints = mDialogView.findViewById(R.id.tblPoints);
        mTblWays = mDialogView.findViewById(R.id.tblWays);
        mTblTollRoads = mDialogView.findViewById(R.id.tblTollRoads);
        mTblHotels = mDialogView.findViewById(R.id.tblHotels);

        mTrPointsSum = mDialogView.findViewById(R.id.trPointsSum);
        mTrWaysSum = mDialogView.findViewById(R.id.trWaysSum);
        mTrTollRoadsSum = mDialogView.findViewById(R.id.trTollRoadSum);
        mTrHotelsSum = mDialogView.findViewById(R.id.trHotelSum);

        TextView txtControl = mDialogView.findViewById(R.id.txtPointCityLabel);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) txtControl.getLayoutParams();
        mMargin = marginLayoutParams.getMarginStart();
        mFirstLayoutParams.setMargins(mMargin, 0, mMargin, 0);
        mSecondLayoutParams.setMargins(0, 0, mMargin, 0);
    }

    @Override
    protected void setData() {
        mTxtTravelName.setText(mTravel.getName());
        mTxtTravelState.setText(mTravel.getStateText());
        try { mTxtBeginning.setText(mTravel.getStartDateTime().toString()); }
        catch (NullPointerException e) { mTxtBeginning.setText(R.string.lbl_unknown); }
        try { mTxtFinishing.setText(mTravel.getEndDateTime().toString()); }
        catch (NullPointerException e) { mTxtFinishing.setText(R.string.lbl_unknown); }
        mTxtParticipants.setText(mTravel.getParticipants());
        mTxtTravelCost.setText(String.valueOf(mTravel.getCost()));
        mTxtFuelConsumption.setText(String.valueOf(mTravel.getFuelConsumption()));
        mTxtFuelPrice.setText(String.valueOf(mTravel.getFuelPrice()));
        mTxtWayDistanceSum.setText(String.valueOf(mTravel.getDistance()));
        mTxtWayFuelSum.setText(String.valueOf(mTravel.getFuelCount()));
        mTxtWayFuelCostSum.setText(String.valueOf(mTravel.getFuelCost()));
        mTxtTollRoadsCostSum.setText(String.valueOf(mTravel.getTollRoadsCost()));
        mTxtHotelsCostSum.setText(String.valueOf(mTravel.getHotelsCost()));

        createPointsTable();
        createWaysTable();
        createTollRoadsTable();
        createHotelsTable();
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

            TextView txtPointCity = new TextView(requireContext());
            TextView txtPointName = new TextView(requireContext());
            TextView txtPointArrival = new TextView(requireContext());
            TextView txtPointDeparture = new TextView(requireContext());

            txtPointCity.setText(point.getAddress().getCity().toString());
            txtPointName.setText(point.getAddress().getName());
            if (point.getArrivalDateTime().getTime() != 0) { txtPointArrival.setText(point.getArrivalDateTime().toString()); }
            if (point.getDepartureDateTime().getTime() != 0) { txtPointDeparture.setText(point.getDepartureDateTime().toString()); }

            txtPointCity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            txtPointName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            txtPointArrival.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            txtPointDeparture.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            txtPointCity.setBackgroundColor(Color.WHITE);
            txtPointName.setBackgroundColor(Color.WHITE);
            txtPointArrival.setBackgroundColor(Color.WHITE);
            txtPointDeparture.setBackgroundColor(Color.WHITE);

            txtPointCity.setLayoutParams(mFirstLayoutParams);
            txtPointName.setLayoutParams(mSecondLayoutParams);
            txtPointArrival.setLayoutParams(mSecondLayoutParams);
            txtPointDeparture.setLayoutParams(mSecondLayoutParams);

            tableRow.addView(txtPointCity);
            tableRow.addView(txtPointName);
            tableRow.addView(txtPointArrival);
            tableRow.addView(txtPointDeparture);

            mTblPoints.addView(tableRow);
        }
        mTblPoints.addView(mTrPointsSum);
    }
    private void createWaysTable() {
        mTblWays.removeView(mTrWaysSum);

        for (int i=0; i<mPoints.size()-1; i++) {
            Point currentPoint = mPoints.get(i);
            Point nextPoint = mPoints.get(i+1);
            float distance = nextPoint.getOdometer() - currentPoint.getOdometer();
            float fuelCount = distance / 100f * mTravel.getFuelConsumption();
            float fuelCost = fuelCount * mTravel.getFuelPrice();

            TableRow tableRow = new TableRow(requireContext());

            TextView txtStart = new TextView(requireContext());
            TextView txtTarget = new TextView(requireContext());
            TextView txtDistance = new TextView(requireContext());
            TextView txtFuelCount = new TextView(requireContext());
            TextView txtFuelCost = new TextView(requireContext());

            txtStart.setText(currentPoint.getAddress().getCity().getName());
            txtTarget.setText(nextPoint.getAddress().getCity().getName());
            txtDistance.setText(String.valueOf(distance));
            txtFuelCount.setText(String.valueOf(fuelCount));
            txtFuelCost.setText(String.valueOf(fuelCost));

            txtStart.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            txtTarget.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            txtDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            txtFuelCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            txtFuelCost.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            txtDistance.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            txtFuelCount.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            txtFuelCost.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

            txtStart.setBackgroundColor(Color.WHITE);
            txtTarget.setBackgroundColor(Color.WHITE);
            txtDistance.setBackgroundColor(Color.WHITE);
            txtFuelCount.setBackgroundColor(Color.WHITE);
            txtFuelCost.setBackgroundColor(Color.WHITE);

            txtStart.setLayoutParams(mFirstLayoutParams);
            txtTarget.setLayoutParams(mSecondLayoutParams);
            txtDistance.setLayoutParams(mSecondLayoutParams);
            txtFuelCount.setLayoutParams(mSecondLayoutParams);
            txtFuelCost.setLayoutParams(mSecondLayoutParams);

            tableRow.addView(txtStart);
            tableRow.addView(txtTarget);
            tableRow.addView(txtDistance);
            tableRow.addView(txtFuelCount);
            tableRow.addView(txtFuelCost);

            mTblWays.addView(tableRow);
        }
        mTblWays.addView(mTrWaysSum);
    }
    private void createTollRoadsTable() {
        mTblTollRoads.removeView(mTrTollRoadsSum);

        for (TollRoad tollRoad : mTravel.getTollRoads()) {
            TableRow tableRow = new TableRow(requireContext());

            TextView txtName = new TextView(requireContext());
            TextView txtStart = new TextView(requireContext());
            TextView txtEnd = new TextView(requireContext());
            TextView txtPrice = new TextView(requireContext());

            txtName.setText(tollRoad.getName());
            txtStart.setText(tollRoad.getStart());
            txtEnd.setText(tollRoad.getEnd());
            txtPrice.setText(String.valueOf(tollRoad.getPrice()));

            txtName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            txtStart.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            txtEnd.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            txtPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            txtPrice.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

            txtName.setBackgroundColor(Color.WHITE);
            txtStart.setBackgroundColor(Color.WHITE);
            txtEnd.setBackgroundColor(Color.WHITE);
            txtPrice.setBackgroundColor(Color.WHITE);

            txtName.setLayoutParams(mFirstLayoutParams);
            txtStart.setLayoutParams(mSecondLayoutParams);
            txtEnd.setLayoutParams(mSecondLayoutParams);
            txtPrice.setLayoutParams(mSecondLayoutParams);

            tableRow.addView(txtName);
            tableRow.addView(txtStart);
            tableRow.addView(txtEnd);
            tableRow.addView(txtPrice);

            mTblTollRoads.addView(tableRow);
        }
        mTblTollRoads.addView(mTrTollRoadsSum);
    }
    private void createHotelsTable() {
        mTblHotels.removeView(mTrHotelsSum);

        for (Hotel hotel : mTravel.getHotels()) {
            TableRow tableRow = new TableRow(requireContext());

            TextView txtName = new TextView(requireContext());
            TextView txtCity = new TextView(requireContext());
            TextView txtCost = new TextView(requireContext());
            TextView txtState = new TextView(requireContext());

            txtName.setText(hotel.getName());
            txtCity.setText(hotel.getAddress().getCity().toString());
            txtCost.setText(String.valueOf(hotel.getCost()));
            txtState.setText(hotel.getState());

            txtName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            txtCity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            txtCost.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            txtState.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            txtCost.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

            txtName.setBackgroundColor(Color.WHITE);
            txtCity.setBackgroundColor(Color.WHITE);
            txtCost.setBackgroundColor(Color.WHITE);
            txtState.setBackgroundColor(Color.WHITE);

            txtName.setLayoutParams(mFirstLayoutParams);
            txtCity.setLayoutParams(mSecondLayoutParams);
            txtCost.setLayoutParams(mSecondLayoutParams);
            txtState.setLayoutParams(mSecondLayoutParams);

            tableRow.addView(txtName);
            tableRow.addView(txtCity);
            tableRow.addView(txtCost);
            tableRow.addView(txtState);

            mTblHotels.addView(tableRow);
        }
        mTblHotels.addView(mTrHotelsSum);
    }
}