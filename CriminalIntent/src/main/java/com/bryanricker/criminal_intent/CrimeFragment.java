package com.bryanricker.criminal_intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;


public class CrimeFragment extends Fragment
{

    private Crime mCrime;
    private EditText mTitleField;


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

        mTitleField = (EditText)v.findViewById(R.id.crime_title_edit_text);
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
