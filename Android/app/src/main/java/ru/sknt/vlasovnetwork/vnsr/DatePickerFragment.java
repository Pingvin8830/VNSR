package ru.sknt.vlasovnetwork.vnsr;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

import ru.sknt.vlasovnetwork.vnsr.car.fragments.NewRefuelDialog;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private final String TAG;
    public DatePickerFragment(String tag) {
        super();
        TAG = tag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it.
        return new DatePickerDialog(getContext(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date the user picks.
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        switch (TAG) {
            case "new_refuel":
                NewRefuelDialog callingFragment = (NewRefuelDialog) fragmentManager.findFragmentByTag("new_refuel");
                callingFragment.setYear(year);
                callingFragment.setMonth(month);
                callingFragment.setDay(day);
                new TimePickerFragment(TAG).show(fragmentManager, "timePicker");
                break;
        }
    }
}
