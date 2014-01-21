package com.bryanricker.criminal_intent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;


public class CrimeLab
{

    private static final String FILENAME = "crimes.json";
    private static CrimeLab sCrimeLab;

    private Context mAppContext;
    private ArrayList<Crime> mCrimes;
    private CriminalIntentJSONSerializer mSerializer;

    private CrimeLab(Context appContext)
    {
        mAppContext = appContext;
        mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);

        try
        {
            mCrimes = mSerializer.loadCrimes();
        } catch (Exception e) {
            mCrimes = new ArrayList<Crime>();
        }
    }


    public static CrimeLab get(Context c)
    {
        if (sCrimeLab == null)
        {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }

        return sCrimeLab;
    }


    public boolean saveCrimes()
    {
        try
        {
            mSerializer.saveCrimes(mCrimes);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Crime> getCrimes()
    {
        return mCrimes;
    }


    public Crime getCrime(UUID id)
    {
        for (Crime c : mCrimes)
        {
            if (c.getId().equals(id))
            {
                return c;
            }
        }

        return null;
    }


    public void addCrime(Crime c)
    {
        mCrimes.add(c);
    }

    public void deleteCrime(Crime c)
    {
        mCrimes.remove(c);
    }

}
