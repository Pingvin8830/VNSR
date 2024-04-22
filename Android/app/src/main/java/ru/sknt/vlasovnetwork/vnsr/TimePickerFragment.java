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

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private final String TAG;

    public TimePickerFragment (String tag) {
        super();
        TAG = tag;
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
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        switch (TAG) {
            case "new_refuel":
                NewRefuelDialog callingFragment = (NewRefuelDialog) fragmentManager.findFragmentByTag("new_refuel");
                callingFragment.setHour(hourOfDay);
                callingFragment.setMinute(minute);
                callingFragment.setDateTimeButtonText();
                break;
        }
    }
}
