package ru.sknt.vlasovnetwork.vnsr;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

import ru.sknt.vlasovnetwork.vnsr.car.fragments.NewRefuelDialog;
import ru.sknt.vlasovnetwork.vnsr.travels.fragments.NewHotelDialog;
import ru.sknt.vlasovnetwork.vnsr.travels.fragments.NewPointDialog;
import ru.sknt.vlasovnetwork.vnsr.travels.fragments.NewTravelDialog;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private final String TAG;
    private final FormatedDate mRes;

    public TimePickerFragment(String tag, FormatedDate res) {
        super();
        TAG = tag;
        mRes = res;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker.
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it.
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time the user picks.
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        mRes.setHours(hourOfDay);
        mRes.setMinutes(minute);
        mRes.setSeconds(0);
        switch (TAG) {
            case "new_refuel":
                NewRefuelDialog refuelDialog = (NewRefuelDialog) fragmentManager.findFragmentByTag(TAG);
                assert refuelDialog != null;
                refuelDialog.setDateTime(mRes);
                break;
            case "new_point_arrival":
                NewPointDialog pointDialog = (NewPointDialog) fragmentManager.findFragmentByTag("new_point");
                assert pointDialog != null;
                pointDialog.setArrivalDateTime(mRes);
                break;
            case "new_point_departure":
                NewPointDialog pointDialog1 = (NewPointDialog) fragmentManager.findFragmentByTag("new_point");
                assert pointDialog1 != null;
                pointDialog1.setDepartureDateTime(mRes);
                break;
            case "new_travel_start":
                NewTravelDialog travelDialog = (NewTravelDialog) fragmentManager.findFragmentByTag("new_travel");
                assert travelDialog != null;
                travelDialog.setStartDateTime(mRes);
                break;
            case "new_travel_end":
                NewTravelDialog travelDialog1 = (NewTravelDialog) fragmentManager.findFragmentByTag("new_travel");
                assert travelDialog1 != null;
                travelDialog1.setEndDateTime(mRes);
                break;
            case "new_hotel_arrival":
                NewHotelDialog hotelDialog = (NewHotelDialog) fragmentManager.findFragmentByTag("new_hotel");
                assert hotelDialog != null;
                hotelDialog.setArrivalDateTime(mRes);
                break;
            case "new_hotel_departure":
                NewHotelDialog hotelDialog1 = (NewHotelDialog) fragmentManager.findFragmentByTag("new_hotel");
                assert hotelDialog1 != null;
                hotelDialog1.setDepartureDateTime(mRes);
                break;
        }
    }
}
