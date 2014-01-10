package com.bryanricker.criminal_intent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment
{

    private Date mDate;

    public static TimePickerFragment newInstance(Date mDate)
    {
        Bundle args = new Bundle();
        args.putSerializable(DateTimeChooserFragment.EXTRA_DATE, mDate);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        mDate = (Date)getArguments().getSerializable(DateTimeChooserFragment.EXTRA_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        int existingHour = calendar.get(Calendar.HOUR_OF_DAY);
        int existingMinute = calendar.get(Calendar.MINUTE);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

        TimePicker timePicker = (TimePicker)v.findViewById(R.id.dialog_time_TimePicker);
        timePicker.setCurrentHour(existingHour);
        timePicker.setCurrentMinute(existingMinute);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
        {
            public void onTimeChanged(TimePicker view, int hour, int minute)
            {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mDate);

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                mDate = new GregorianCalendar(year, month, day, hour, minute).getTime();
                getArguments().putSerializable(DateTimeChooserFragment.EXTRA_DATE, mDate);
            }
        });

        return new AlertDialog.Builder(getActivity())
            .setView(v)
            .setTitle(R.string.time_picker_title)
            .setPositiveButton(
                android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                }
           )
            .create();
    }

    private void sendResult(int resultCode)
    {
        if (getTargetFragment() == null) return;

        Intent i = new Intent();
        i.putExtra(DateTimeChooserFragment.EXTRA_DATE, mDate);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

}
