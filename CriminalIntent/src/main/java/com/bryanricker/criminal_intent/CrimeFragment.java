package com.bryanricker.criminal_intent;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    private static final String TAG = "CrimeFragment";
    public static final String EXTRA_CRIME_ID = "com.bryanricker.criminal_intent.crime_id";
    public static final String DIALOG_DATE_TIME_CHOOSER = "date_time_chooser";
    public static final int REQUEST_DATE = 0;

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
        setHasOptionsMenu(true);

        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }


    @TargetApi(11)
    @Override
    public View onCreateView(
    LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            if (hasParentActivity())
            {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        mTitleField     = (EditText)v.findViewById(R.id.crime_title_edit_text);
        mDateButton     = (Button)v.findViewById(R.id.crime_date_button);
        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved_check_box);

        mTitleField.setText(mCrime.getTitle());
        mSolvedCheckBox.setChecked(mCrime.isSolved());

        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DateTimeChooserFragment dialog = DateTimeChooserFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE_TIME_CHOOSER);
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
            Date date = (Date)data.getSerializableExtra(DateTimeChooserFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
    }


    private void updateDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy, HH:mm:ss");
        mDateButton.setText(sdf.format(mCrime.getDate()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                if (hasParentActivity())
                {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onPause()
    {
        super.onPause();
        CrimeLab.get(getActivity()).saveCrimes();
    }

    private boolean hasParentActivity()
    {
        return NavUtils.getParentActivityName(getActivity()) != null;
    }
}
