package com.bryanricker.criminal_intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class CrimeFragment extends Fragment
{

    public static final String EXTRA_CRIME_ID = "com.bryanricker.criminal_intent.crime_id";
    public static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;


    public static CrimeFragment newInstance(UUID crimeId)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }


    @Override
    public View onCreateView(
    LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        mTitleField     = (EditText)v.findViewById(R.id.crime_title_edit_text);
        mDateButton     = (Button)v.findViewById(R.id.crime_date_button);
        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved_check_box);

        mTitleField.setText(mCrime.getTitle());
        mSolvedCheckBox.setChecked(mCrime.isSolved());

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        mDateButton.setText(sdf.format(mCrime.getDate()));

        mDateButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            public void onCheckedChanged(
            CompoundButton buttonView, boolean isChecked)
            {
                mCrime.setIsSolved(isChecked);
            }
        });

        mTitleField.addTextChangedListener(new TextWatcher()
        {
            public void onTextChanged(
            CharSequence c, int start, int before, int count)
            {
                mCrime.setTitle(c.toString());
            }

            public void beforeTextChanged(
            CharSequence c, int start, int before, int count)
            {
                // Do nothing.
            }

            public void afterTextChanged(Editable c)
            {
                // Do nothing.
            }
        });

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_DATE)
        {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            mDateButton.setText(mCrime.getDate.toString());
        }
    }
}
