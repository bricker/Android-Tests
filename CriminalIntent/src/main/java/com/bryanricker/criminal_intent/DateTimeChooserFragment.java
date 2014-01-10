package com.bryanricker.criminal_intent;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import java.util.Date;

public class DateTimeChooserFragment extends DialogFragment
{

    public static final String EXTRA_DATE = "com.bryanricker.criminal_intent.date";
    public static final String DIALOG_DATE = "date";
    public static final String DIALOG_TIME = "time";

    private Date mDate;


    public static DateTimeChooserFragment newInstance(Date date)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);

        DateTimeChooserFragment fragment = new DateTimeChooserFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        mDate = (Date)getArguments().getSerializable(DateTimeChooserFragment.EXTRA_DATE);
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date_time_chooser, null);

        Button dateButton = (Button)v.findViewById(R.id.date_time_chooser_date_Button);
        Button timeButton = (Button)v.findViewById(R.id.date_time_chooser_time_Button);

        dateButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mDate);
                dialog.setTargetFragment(DateTimeChooserFragment.this, CrimeFragment.REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mDate);
                dialog.setTargetFragment(DateTimeChooserFragment.this, CrimeFragment.REQUEST_DATE);
                dialog.show(fm, DIALOG_TIME);
            }
        });

        return new AlertDialog.Builder(getActivity())
            .setView(v)
            .setTitle(R.string.date_time_chooser_title)
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == CrimeFragment.REQUEST_DATE)
        {
            mDate = (Date)data.getSerializableExtra(DateTimeChooserFragment.EXTRA_DATE);
        }
    }


    private void sendResult(int resultCode)
    {
        if (getTargetFragment() == null) return;

        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

}
