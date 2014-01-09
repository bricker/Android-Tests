package com.bryanricker.criminal_intent;

import java.util.ArrayList;
import java.util.UUID;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;


public class CrimePagerActivity extends FragmentActivity
{

    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mCrimes = CrimeLab.get(this).getCrimes();

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm)
        {
            @Override
            public int getCount()
            {
                return mCrimes.size();
            }

            @Override
            public Fragment getItem(int position)
            {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int pos, float posOffset, int posOffsetPx) {}

            public void onPageSelected(int pos)
            {
                Crime crime = mCrimes.get(pos);
                if (crime.getTitle() != null)
                {
                    setTitle(crime.getTitle());
                }
            }
        });

        UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        for (int i = 0; i < mCrimes.size(); i++)
        {
            if (mCrimes.get(i).getId().equals(crimeId))
            {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

}
