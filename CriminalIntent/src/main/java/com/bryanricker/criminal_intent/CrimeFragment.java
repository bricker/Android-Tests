package com.bryanricker.criminal_intent;

import java.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.text.TextWatcher;
import android.text.Editable;

public class CrimeFragment extends Fragment
{

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }


    @Override
    public View onCreateView(
    LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        mTitleField     = (EditText)v.findViewById(R.id.crime_title_edit_text);
        mDateButton     = (Button)v.findViewById(R.id.crime_date_button);
        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved_check_box);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        mDateButton.setText(sdf.format(mCrime.getDate()));
        mDateButton.setEnabled(false);

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

}
